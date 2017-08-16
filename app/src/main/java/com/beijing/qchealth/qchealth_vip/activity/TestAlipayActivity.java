package com.beijing.qchealth.qchealth_vip.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.alipay.AuthResult;
import com.beijing.qchealth.qchealth_vip.alipay.PayResult;
import com.beijing.qchealth.qchealth_vip.base.BaseActivity;
import com.beijing.qchealth.qchealth_vip.entity.TestBean;
import com.beijing.qchealth.qchealth_vip.http.AppNetUtil;
import com.beijing.qchealth.qchealth_vip.http.NetCallBack;
import com.beijing.qchealth.qchealth_vip.http.ResultModel;

import java.util.Map;

public class TestAlipayActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {

                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(TestAlipayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                     
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        
                         Log.e("reas---",resultStatus+payResult.toString());
                        Toast.makeText(TestAlipayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(TestAlipayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(TestAlipayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_alipay);
        
        findViewById(R.id.tv_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                realALiPay(0.01+"");
            }
        });
        
    }

    private void realALiPay(String s) {

        AppNetUtil.getInstance(this).alipay(s, new NetCallBack() {
            @Override
            public void onSuccess(String mssage, ResultModel model) {
                 if (model!=null&&model.getModel()!=null){
                     TestBean bean=model.getModel();
                     
                     if (!TextUtils.isEmpty(bean.getPayPara())){
                         alipay(bean.getPayPara());
                     }
                 }
            }

            @Override
            public void onFail(boolean dataError, int errorCode, String mssage) {

            }
        }, TestBean.class);
        
    }

    private void alipay(final String payPara) {


        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                PayTask alipay = new PayTask(TestAlipayActivity.this);
                Map<String, String> result = alipay.payV2(payPara, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);

            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
        
        
    }
}
