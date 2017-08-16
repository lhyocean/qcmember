package com.beijing.qchealth.qchealth_vip.http;

/**
 * Created by ocean on 2017/5/31.
 */
public class AppHttpUrl {
    
    // 图片base  拼接 http://images.elitecedu.org/upload/201706/06/201706061516292784.jpg

    public static final String BASE_IMG="http://images.elitecedu.org";
    
    //         刘立帅测试服务器
    private static String BASE_URL="http://192.168.1.155:8222";
    
    
//    private static String BASE_URL="http://tomcat.qingchen.org.cn";
    

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    // 注册用户
    public static final String POST_REGIST_USER="/user/regist";
    // 用户获取验证码
    public static final String POST_SEND_VERTIFY_CODE="/user/sendcode";
    // 找回密码
    public static final String POST_FIND_LOGIN_PASS="/user/findpassword";
    // 修改密码
    public static final String POST_MIDIFY_PASS="/user/editPassword";
    //  完善信息
    public static final String POST_SET_USER_INFO="/userinfo/updateUserInfo";
    //  手机号码登录
    public static final String POST_USER_LOGIN="/user/login";
    // 用户退出登录
    public static final String POST_USER_LOGIN_OUT="/user/exit";
    
    
    //测试支付
    public static final String GET_ALI_PAY="/pay/aliPay";
    
    // 图片验证码
    public static final String GET_IMG_VERTIFYCATION="/user/captcha";
    
    
    
   
    
}
