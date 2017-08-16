package com.beijing.qchealth.qchealth_vip.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lhy on 2017/7/27.
 */

public class PreferencesUtil {

    public static String Pre_Forever = "forever";// 用于一直存放变量，除非应用被卸载
    public static String Pre_Name ="hejwp";

    public static String Key_UUID = "uuid";// 系统的唯一标识，只生成一次
    public static String Key_BuildCode = "buildcode";
    public static String Key_Version = "version";
    public static String Key_DeviceInfo = "deviceinfo";

    public static String Key_Uid = "uid";
    public static String Key_Phone = "phone";

    public static void saveHejwpString(Context context, String pre_name, String name, String value){
        SharedPreferences sp = context.getSharedPreferences(pre_name, Context.MODE_PRIVATE);
        sp.edit().putString(name,value).commit();
    }
    public static String getHejwpString(Context context, String pre_name, String name, String def){
        SharedPreferences sp = context.getSharedPreferences(pre_name, Context.MODE_PRIVATE);
        return sp.getString(name,def);
    }

    public static void clearPartData(Context context){
        PreferencesUtil.saveHejwpString(context, Pre_Name, Key_Uid, "");
    }
   
    
    
    
}
