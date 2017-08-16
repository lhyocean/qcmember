
package com.beijing.qchealth.qchealth_vip.http;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.beijing.qchealth.qchealth_vip.application.MyApplication;
import com.beijing.qchealth.qchealth_vip.utils.Common;
import com.beijing.qchealth.qchealth_vip.utils.JsonFormatUtils;
import com.beijing.qchealth.qchealth_vip.utils.Tools;
import com.beijing.qchealth.qchealth_vip.utils.VersionUtil;
import com.beijing.qchealth.qchealth_vip.utils.http.OKhttpManager;
import com.beijing.qchealth.qchealth_vip.utils.http.ProgressRequestListener;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.NetWorkUtil;
import org.haitao.common.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Description http 请求
 * Author by wangHaitao(a758277560@gmail.com)
 * Created  on 2015/12/3
 * Version 1.0
 */
public class HttpUtil {
    /**
     * 请求失败:服务器请求 返回 不是json
     */
    public static final int ERROR_DATA = 333;
    /**
     * 请求失败:网络不可用
     */
    public static final int ERROR_NETWORK = 444;
    private static String tag = "HttpUtil";


    private static void get(final Context context, final String url, Map params, final NetCallBack callback, final boolean isList, @SuppressWarnings("rawtypes") final Class type) {
        // 网络是否可用
        if (checkNet(callback)) {

            if (params == null) {
                params = new HashMap();
            }

            params.put("device_id", VersionUtil.getDeviceId()+"");  //         设备唯一标志
            params.put("app_id", "2");// android
            params.put("app_build_code", Build.VERSION.SDK_INT + "");//操作系统版本号
            params.put("app_version", VersionUtil.getVersion(context));// 客户端版本号
            params.put("deviceInfo", Common.DEVICEINFO);
            params.put("channel", Common.CHANNEL);
            params.put("sign", "");
            
            
            final Map tmepparams = params;

            OKhttpManager.get(context, AppHttpUrl.getBaseUrl() + url, params, new OKhttpManager.DataCallBack() {
                @Override
                public void requestFailure(Call call, IOException e) {
                    Log.e("==========", "");
                }

                @Override
                public void requestSuccess(String result) {

                    handleSuccess(result, AppHttpUrl.getBaseUrl() + url, tmepparams, callback, type, isList, context);
                }

            });

        }

    }


    private static void post(final Context context, final String url, Map params, final NetCallBack callback, @SuppressWarnings("rawtypes") final Class type, final boolean isList, String requestJson) {

        // 网络是否可用
        if (checkNet(callback)) {

            if (params == null) {
                params = new HashMap();
            }

            params.put("device_id", VersionUtil.getDeviceId()+"");  //          设备唯一标志
            params.put("app_id", "2");// android
            params.put("app_build_code", Build.VERSION.SDK_INT + "");//操作系统版本号
            params.put("app_version", VersionUtil.getVersion(context));// 客户端版本号
            params.put("deviceInfo", Common.DEVICEINFO);
            params.put("channel", Common.CHANNEL);
            params.put("sign", "");

            final Map tmepparams = params;

            if (requestJson == null) {


                OKhttpManager.post(context, AppHttpUrl.getBaseUrl() + url, params, new OKhttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Call call, IOException e) {

                    }

                    @Override
                    public void requestSuccess(String result) {

                        handleSuccess(result, AppHttpUrl.getBaseUrl() + url, tmepparams, callback, type, isList, context);
                    }

                });
            }
        }
    }


    private static void updateFile(final Context context, final String url, final String path, Map params, final NetCallBack callback, @SuppressWarnings("rawtypes") final Class type, final boolean isList, String requestJson, final ProgressRequestListener progressRequestListener) {

        // 网络是否可用
        if (checkNet(callback)) {

            if (params == null) {
                params = new HashMap();
            }
           

//            User user = MyApplication.getInstance().user;
//
//            if (user != null) {
//                params.put("userid", user.getId());
//            }


            final Map tmepparams = params;

            if (requestJson == null) {

                OKhttpManager.uploadFiles(context, AppHttpUrl.getBaseUrl() + url, path, new OKhttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Call call, IOException e) {

                    }

                    @Override
                    public void requestSuccess(String result) {

                        handleSuccess(result, AppHttpUrl.getBaseUrl() + url, tmepparams, callback, type, isList, context);
                    }

                }, progressRequestListener);
            }
        }
    }

    /**
     * 请求前判断网络
     *
     * @param callback
     * @return
     */
    private static boolean checkNet(final NetCallBack callback) {
        if (!NetWorkUtil.isNetworkAvailable(MyApplication.getInstance())) {
            ToastUtil.shortShowToast("当前网络不可用！");
            if (callback != null) {
                callback.onFail(false, ERROR_NETWORK, "当前网络不可用。");
            }
            return false;
        }
        return true;
    }


    /**
     * 处理失败
     */
    private static void handleFail(final NetCallBack callback, final int error_code) {
        Tools.dismissWaitDialog();
        String error = "其它未知错误!";


        if (error_code == 0) {
            AppLog.e("ser not response");
            error = "连接超时，请稍候重试。";
        } else if (error_code == 404) {
            AppLog.e("url not found");
            //error = "请求地址不存在，请稍候重试。";
            error = "资源或对象不存在。";
        } else if (error_code == 500) {
            //error = "服务器出错误了，请稍候重试。";
            AppLog.e("server error");
            error = "服务器内部异常。";
        } else if (error_code == 400) {
            //AppLog.e("server error");
            error = "网络请求失败，请稍候重试。";
        } else if (error_code == 401) {
            error = "未授权或授权已过期。";
        } else if (error_code == 601) {
            error = "授权非法。";
        } else {
            //error = "其它未知错误，请稍候重试。";
            error = "网络请求失败，请稍候重试。";
        }


        AppLog.i(tag, "error_code :" + error_code);

        if (callback != null) {

            ToastUtil.shortShowToast(error);
            callback.onFail(false, error_code, "");
        }
    }

    /**
     * 请求结果统一解析
     *
     * @param responseBt
     * @param url
     * @param callback
     * @param type
     * @param isList
     */
    @SuppressWarnings("unchecked")
    public static void handleSuccess(String responseBt, final String url, Map params, final NetCallBack callback, @SuppressWarnings("rawtypes") final Class type, final boolean isList, final Context context) {
        Tools.dismissWaitDialog();
//        String jsonString = null;
//        jsonString = new String(responseBt);
        Log.i("--", "hh" + responseBt);
        Log.i("结果", "结果: url:\t" + url + "\n" + "params \n" + params + "\n" + JsonFormatUtils.formatJson(responseBt));
        //// TODO: 2016/9/21 必须去掉
        if (callback != null) {
            JSONObject json = null;
            int code = 0;
            String message = null;
            String response = null;
            int total = 0;
            try {
                json = JSON.parseObject(responseBt);


                if (json != null && json.containsKey("code")) {
                    code = json.getIntValue("code");
                }

                if (json != null && json.containsKey("message")) {
                    message = json.getString("message");
                }
                if (json != null && json.containsKey("data")) {
                    response = json.getString("data");
                }

            } catch (JSONException e) {
                e.printStackTrace();


                if (callback != null) {
                    ToastUtil.shortShowToast("数据解析错误，请稍候重试");
                    callback.onFail(true, code, message);

                }
                // 后面不要执行
                return;
            }
            // 响应成功
            if (code == 0) {
                // json 解析 有数据
                if (type != null) {
                    if (response != null && !"".equals(response) && !"null".equals(response)) {
                        ResultModel body = new ResultModel();
                        if ("no".equals(response) || "yes".equals(response)) {
                            body.setModel(response);
                            if (callback != null) {

                                callback.onSuccess(message, body);
                            }

                            return;
                        }

                        body.setCount(total);
                        try {
                            if (isList) {
                                List<?> ls = JSON.parseArray(response, type);
                                body = new ResultModel();
                                body.setModelList(ls);
                                body.setCount(total);
                            } else {
                                if (response.contains("{") || response.contains("[")) {
                                    body.setModel(JSON.parseObject(response, type));

                                } else {
                                    body.setModel(response);
                                }


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.shortShowToast("数据解析错误，请稍候重试");
                            if (callback != null) {
                                callback.onFail(true, ERROR_DATA, "");
                                return;
                            }
                        }
                        // 不该 try 的地方不要try  下面的代码不应该try
                        if (callback != null) {
                            callback.onSuccess(message, body);
                        }
                    } else {
                        // 没有model
                        if (callback != null) {
                            final ResultModel body = new ResultModel();
                            body.setCount(total);

                            callback.onSuccess(message, body);

                        }
                    }
                } else {

                    // 没有model
                    if (callback != null) {
                        final ResultModel body = new ResultModel();
                        body.setCount(total);
                        if (response != null && !"".equals(response) && !"null".equals(response)) {
                            if (!response.contains("{") && !response.contains("[")) {
                                body.setModel(response);
                            }
                        }

                        callback.onSuccess(message, body);

                    }
                }
            } else {


                if (401 == code || 601 == code) {
                    loginOut(context);
                    Tools.dismissWaitDialog();// 很重要！！！！
                } else {

                    if (code == 599) {
                        Tools.dismissWaitDialog();

                        return;
                    }

                    if (callback != null) {
                        callback.onFail(true, code, message);

                    }

                }

            }

        }

    }

    private static void loginOut(Context c) {


//            EMClient.getInstance().logout(false, new EMCallBack() {
//
//                @Override
//                public void onSuccess() {
//                    AppLog.i(tag, "退出环信聊天");
//
//                }
//
//                @Override
//                public void onProgress(int progress, String status) {
//
//                }
//
//                @Override
//                public void onError(int code, String message) {
//                    AppLog.i(tag, "退出环信聊天失败" + code + " " + message);
//
//                }
//            });


    }

    public static void updateFile(Context context, String url, String path, Map params, final NetCallBack callback, final Class type, final ProgressRequestListener progressRequestListener) {
        updateFile(context, url, path, params, callback, type, true, null, progressRequestListener);
    }


    public static void post(Context context, String url, Map params, final NetCallBack callback, final Class type) {
        post(context, url, params, callback, type, false, null);
    }


    public static void postList(Context context, String url, Map params, final NetCallBack callback, final Class type) {
        post(context, url, params, callback, type, true, null);
    }

    public static void get(Context context, String url, Map params, final NetCallBack callback, final Class type) {
        get(context, url, params, callback, false, type);
    }

    public static void getList(Context context, String url, Map params, final NetCallBack callback, final Class type) {
        get(context, url, params, callback, true, type);
    }


}