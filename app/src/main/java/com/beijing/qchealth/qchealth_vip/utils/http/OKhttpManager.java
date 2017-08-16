package com.beijing.qchealth.qchealth_vip.utils.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import org.haitao.common.utils.AppLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKhttpManager {
    private OkHttpClient client;
    private static OKhttpManager oKhttpManager;
    private Handler mHandler;
    private String TAG = this.getClass().getSimpleName();

    /**
     * 单例获取 OKhttpManager实例
     */
    private static OKhttpManager getInstance(Context context) {
        if (oKhttpManager == null) {
            oKhttpManager = new OKhttpManager(context);
        }
        return oKhttpManager;
    }

    private OKhttpManager(Context mContext) {
        mHandler = new Handler(Looper.getMainLooper());

        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        final InputStream inputStream;
        try {
            inputStream = mContext.getAssets().open("sserver.cer"); // 得到证书的输入流
            try {

                trustManager = trustManagerForCertificates(inputStream);//以流的方式读入证书
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager}, null);
                sslSocketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
            client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .connectTimeout(30, TimeUnit.SECONDS)//连接超时限制
                    .writeTimeout(30, TimeUnit.SECONDS)//写入超时
                    .readTimeout(30, TimeUnit.SECONDS)//读取超时
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        char[] password = "123456".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }


    /**
     * 添加password
     *
     * @param password
     * @return
     * @throws GeneralSecurityException
     */
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType()); // 这里添加自定义的密码，默认
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }


    /**
     * 数据分发的方法
     */
    private void deliverDataFailure(final Call call, final IOException e, final DataCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailure(call, e);
                }
            }
        });
    }

    private void deliverDataSuccess(final String result, final DataCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestSuccess(result);
                }
            }
        });
    }



    public static void post(Context context, String url, Map<String, String> params, DataCallBack callBack) {
        getInstance(context).postAsyn(url, params, callBack);//异步POST请求
    }

    public static void get(Context context, String url, Map<String, String> params, DataCallBack callBack) {
        getInstance(context).getAsyn(url, params, callBack);//异步GET请求
    }

    public static void uploadFiles(Context context, String url, String path, DataCallBack callBack, ProgressRequestListener progressRequestListener) {
        getInstance(context).uploadFilesReal(url, path, callBack,progressRequestListener);//异步POST请求
    }


    /**
     * 数据回调接口
     */
    public interface DataCallBack {
        void requestFailure(Call call, IOException e);

        void requestSuccess(String result);
    }


    /**
     * 一般的get请求 对于一般的请求，我们希望给个url，然后取的返回的String。
     */

    private void getAsyn(String url, Map params, final DataCallBack callBack) {
        StringBuilder sb = new StringBuilder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry> entrySet = params.entrySet();
            sb.append("?");
            for (Map.Entry entry : entrySet) {
                sb.append(entry.getKey());
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(entry.getValue() + "", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        Request.Builder builder = new Request.Builder();

//        if (MyApplication.getInstance().user != null) {
//            builder.addHeader("Authorization", MyApplication.getInstance().user.getAuthorization() == null ? "" : MyApplication.getInstance().user.getAuthorization());
//        }

        AppLog.e(TAG, "getAsyn: ,"+url+sb.toString() );
        Request request = builder.url(url + sb.toString()).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(call, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                } catch (IOException e) {
                    deliverDataFailure(call, e, callBack);
                }
            }
        });
    }


    /* 一般的post请求 对于一般的请求，我们希望给个url和封装参数的Map，然后取的返回的String。
          */
    private void postAsyn(String url, Map params, final DataCallBack callBack) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            Set<Map.Entry> entrySet = params.entrySet();
            for (Map.Entry entry : entrySet) {
                formBodyBuilder.add(entry.getKey() + "", entry.getValue() + "");
            }
        }

        Request.Builder builder = new Request.Builder();

//        if (MyApplication.getInstance().user != null) {
//            builder.addHeader("Authorization", MyApplication.getInstance().user.getAuthorization() == null ? "" : MyApplication.getInstance().user.getAuthorization());
//        }
        Request request = builder.url(url).post(formBodyBuilder.build()).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverDataFailure(call, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result, callBack);
                } catch (IOException e) {
                    deliverDataFailure(call, e, callBack);
                }
            }
        });


    }


    private void uploadFilesReal(final String url, final String compressPath, final DataCallBack callBack, final ProgressRequestListener listener) {



//                User user = MyApplication.getInstance().user;
//                if (user != null) {
                    String suffix = compressPath.substring(compressPath.lastIndexOf("/") + 1, compressPath.length());
                    AppLog.i("suffix--", "image--->" + suffix);
                    File file = new File(compressPath);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

  /* form的分割线,自己定义 */
                    String boundary = "----meidada--------------------------------------------------------------meidada----";


                    MultipartBody.Builder builder1 = new MultipartBody.Builder(boundary).setType(MultipartBody.FORM);
                    builder1.addFormDataPart("files", suffix, fileBody)
//                            .addFormDataPart("userid", user.getId())
                            .build();
                    Request.Builder builder = new Request.Builder();

//                    builder.addHeader("Authorization", user.getAuthorization() == null ? "" : user.getAuthorization());

                         builder.url(url)
                                  .post(new ProgressRequestBody(builder1.build()) {
                                      @Override
                                      public void loading( final  long current, final long total,final boolean done) {
                                          AppLog.i(TAG,"LOADING...current:"+current+"  total:"+total+"    done:"+done);
                                          if(mHandler!=null){
                                              mHandler.post(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      listener.onResponseProgress(current,total,done);
                                                  }
                                              });
                                          }



                                      }
                                  });

                    Request request = builder.build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            deliverDataFailure(call, e, callBack);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                String result = response.body().string();
                                deliverDataSuccess(result, callBack);
                            } catch (IOException e) {
                                deliverDataFailure(call, e, callBack);
                            }
                        }
                    });
//                }
            }

    private void downLoadFile(String downLoadUrl, final  ProgressResponseListener progressResponseListener){

        client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(
                        new ProgressResponseBody(originalResponse.body(), progressResponseListener))
                        .build();
            }
        });

        Request request = new Request.Builder()
                //下载地址
                .url(downLoadUrl)
                .build();
    }




}