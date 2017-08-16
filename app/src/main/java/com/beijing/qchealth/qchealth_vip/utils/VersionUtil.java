package com.beijing.qchealth.qchealth_vip.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.beijing.qchealth.qchealth_vip.application.MyApplication.instance;


/**
 * Created by lyq on 2016/5/24.
 */
public class VersionUtil {

    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return "未发现版本";
    }
    }
    
    public static String getDeviceId(Context context){

        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return szImei+"";
    }
    
    
    public static String getUuid(){
        String uuid = PreferencesUtil.getHejwpString(instance, PreferencesUtil.Pre_Forever, PreferencesUtil.Key_UUID, "");
        if (TextUtils.isEmpty(uuid)) {
            PreferencesUtil.saveHejwpString(instance, PreferencesUtil.Pre_Forever, PreferencesUtil.Key_UUID, UUID.randomUUID().toString()
                    .replace("-", ""));
        }
            uuid = PreferencesUtil.getHejwpString(instance, PreferencesUtil.Pre_Forever, PreferencesUtil.Key_UUID, "");
           return uuid;
    }
    
    public static String getDeviceId(){

         return   PreferencesUtil.getHejwpString(instance, PreferencesUtil.Pre_Forever, PreferencesUtil.Key_UUID, "");

    }
    
}
