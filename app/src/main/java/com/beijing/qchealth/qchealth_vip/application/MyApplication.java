package com.beijing.qchealth.qchealth_vip.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.beijing.qchealth.qchealth_vip.db.DbUtils;
import com.beijing.qchealth.qchealth_vip.db.User;
import com.beijing.qchealth.qchealth_vip.utils.Common;
import com.beijing.qchealth.qchealth_vip.utils.PreferencesUtil;
import com.beijing.qchealth.qchealth_vip.utils.VersionUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.youzan.sdk.YouzanSDK;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by lhy on 2017/8/4.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static MyApplication instance;
    public User user;
    public static Context applicationContext;
    private boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;
        UMShareAPI.get(this);
        
        initPublicParams();
        init();
//        if (isDebug) {
//            CrashHandler crashHandler = CrashHandler.getInstance();
//            crashHandler.init(getApplicationContext());
//        }
        
    }

    private void initPublicParams() {

        Common.DEVICEINFO += "-" + Build.BRAND + "-" + Build.MODEL + "-" + Build.VERSION.RELEASE;
        String deviceInfo = PreferencesUtil.getHejwpString(instance, PreferencesUtil.Pre_Forever, PreferencesUtil.Key_DeviceInfo, "");
        if (TextUtils.isEmpty(deviceInfo)) {
            PreferencesUtil.saveHejwpString(instance, PreferencesUtil.Pre_Forever, PreferencesUtil.Key_DeviceInfo, Common.DEVICEINFO);
        }
        
        // 版本号
        int versionCode = 0;
        String versionName = "";
        try {
            versionCode = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
            versionName = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
          
        }
        Common.VERSION = versionCode + "";
        Common.VERSION_NAME = versionName;
        Common.UUID= VersionUtil.getUuid();
        Common.DEVICEID=VersionUtil.getDeviceId();
        AppLog.e("info","version=="+Common.VERSION+"\nname="+Common.VERSION_NAME+"\nuuid="+Common.UUID+"\ndecice_id="+Common.DEVICEID+"\ndeviceinfo=="+Common.DEVICEINFO);        
        

    }

    public boolean isDebug() {
        return isDebug;
    }
    /**
     * 保存用户
     *
     * @param user
     */
    public void saveUser(User user) {
        this.user = user;
    }

    /**
     * 清除数据
     * 退出登录
     */
    public void logout() {
        saveUser(null);
    }

    public boolean isLogin() {
        return user == null ? false : true;
    }
    private void init() {
        MultiDex.install(this);
        ToastUtil.init(this);
        AppLog.setDedug(isDebug);
        initImageLoader();

        initUser();
//        init demo helper
        /**
         * 初始化SDK.
         *
         * @param Context application Context
         * @param clientId 需向有赞申请获取.
         */
//        YouzanSDK.init(getApplicationContext(), "XXXX");
        
        YouzanSDK.init(getApplicationContext(), "86c08809597ab507f0");

    }
    private void initUser() {
        DbUtils databaseUtil = new DbUtils(this);
        User userLocal = null;
        List<User> users = databaseUtil.queryAllUser();
        if(users!=null && users.size()>0){
            userLocal =  users.get(0);
            user = userLocal;
        }
    }
    private void initImageLoader() {

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.NONE).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(applicationContext).threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new UsingFreqLimitedMemoryCache((int) (Runtime.getRuntime().maxMemory() / 8)))
                .memoryCacheSizePercentage(13) // default
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        ImageLoader.getInstance().init(config);
    }

    public static MyApplication getInstance() {
        return instance;
    }
    
     
    {   
        PlatformConfig.setWeixin("wx442e14d05249eccb", "5955feee6662a55a008c29ee000ed0d4");
        PlatformConfig.setQQZone("1106253039", "aBRqXQGluWuunrkA");
        PlatformConfig.setSinaWeibo("2484656428", "c3e07e3e99dd3ebf7783a5c60ef15eef", "https://sns.whalecloud.com/sina2/callback");
    }
    
    
    
    
}
