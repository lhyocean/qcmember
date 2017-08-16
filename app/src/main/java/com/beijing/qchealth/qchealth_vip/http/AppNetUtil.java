package com.beijing.qchealth.qchealth_vip.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lyq on 2016/5/30.
 */
public class AppNetUtil {
    private Context context;

    private static AppNetUtil single = null;

    private AppNetUtil(Context context) {
        this.context = context;

    }

    // 静态工厂方法
    public static AppNetUtil getInstance(Context context) {
        if (single == null) {
            single = new AppNetUtil(context);
        }
        return single;
    }


//    private void updateFile(String url, String path, final NetCallBack callback, final Class classType, final ProgressRequestListener progressRequestListener) {
//        HttpUtil.updateFile(context,url,path, null, callback, classType,progressRequestListener);
//    }
    private void norPost(String url, Map params, final NetCallBack callback, final Class classType) {
        HttpUtil.post(context, url, params, callback, classType);
    }

    private void norGet(String url, Map params, final NetCallBack callback, final Class classType) {
        HttpUtil.get(context, url, params, callback, classType);
    }

    private void norGetList(String url, Map params, final NetCallBack callback, final Class classType) {
        HttpUtil.getList(context, url, params, callback, classType);
    }

    private void norPostList(String url, Map params, final NetCallBack callback, final Class classType) {
        HttpUtil.postList(context, url, params, callback, classType);
    }


    /**
     *         注册          
     * 
     * @param callback
     * @param classType
     */
    public void userRegister(String phone,String pass,String code,final NetCallBack callback, final Class classType) {

        Map params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", pass);
        params.put("code", code);
        norPost(AppHttpUrl.POST_REGIST_USER, params, callback, classType);

    }

    /**
     *        用户登录
     * @param phone
     * @param pass
     * @param callback
     * @param classType
     */
    public void userLogin(String phone,String pass,final NetCallBack callback, final Class classType) {
        
        Map params = new HashMap<>();
        params.put("mobile", phone);
        params.put("password", pass);
        norPost(AppHttpUrl.POST_USER_LOGIN, params, callback, classType);

    }

    /**
     *   获取验证码
     * @param phone
     * @param type
     * @param callback
     * @param classType
     */
    public void sendCode(String phone,String type,String imgcode,final NetCallBack callback, final Class classType){
        
        Map params = new HashMap<>();
        params.put("mobile", phone);
        params.put("text",imgcode);
        params.put("sendCodeType", type);
        norPost(AppHttpUrl.POST_SEND_VERTIFY_CODE, params, callback, classType);
        
    }
    
    public void alipay(String total_amount,NetCallBack callback,Class classType){
        Map params = new HashMap<>();
        params.put("total_amount", total_amount);
        norGet(AppHttpUrl.GET_ALI_PAY, params, callback, classType);
        
    }
    
    
    public void getImgVercode(NetCallBack callBack,Class classType){
        norGet(AppHttpUrl.GET_IMG_VERTIFYCATION, null, callBack, classType);
    }
    
    
    
    
    
    
    
    
    
    

}
