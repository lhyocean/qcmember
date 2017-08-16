package com.beijing.qchealth.qchealth_vip.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.view.swipleft.SwipeBackActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.haitao.common.utils.AppLog;




/**
 * Created by ocean on 2017/5/31.
 */
public class BaseSwipeActivity extends SwipeBackActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        setSysTemBarHint();
//        StatusBarUtil.StatusBarLightMode(this);
    }

    public void setSysTemBarHint() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.white);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String name=this.getClass().getSimpleName();

        AppLog.e("className","当前Activity====="+name);
        // umeng

    }
}
