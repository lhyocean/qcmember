package com.beijing.qchealth.qchealth_vip.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lyq on 2016/5/30.
 */
public class PhoneUtil {
    static boolean flag = false;
    // 用于校验输入昵称   ，只能是汉字字母和数字组合，长度1-10位
    public static final String NICKNAME_TEST="^[\\u4e00-\\u9fa5_a-zA-Z0-9]{2,8}$";
    //校验密码 ，只能是6位以上的数字字母组合
    public static final String PASSWORD_TEST="^[A-Za-z0-9]{6,16}$";
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((19[0-9])|(13[0-9])|(14[5-7])|(15([0-9]))|(17([0-9]))|(18[0-9]))\\d{8}$";
        return check(cellphone, regex);
    }

    public static boolean check(String str, String regex) {
        try {
            Pattern e = Pattern.compile(regex);
            Matcher matcher = e.matcher(str);
            flag = matcher.matches();
        } catch (Exception var4) {
            flag = false;
        }

        return flag;
    }

    public static  boolean checkMerchantName(String merchantName){
        return checkMerchantName(merchantName,NICKNAME_TEST);

    }

    private static boolean checkMerchantName(String merchantName, String regex) {
        try {
            Pattern e = Pattern.compile(regex);
            Matcher matcher = e.matcher(merchantName);
            flag = matcher.matches();
        } catch (Exception var4) {
            flag = false;
        }

        return flag;
    }
}
