package com.beijing.qchealth.qchealth_vip;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beijing.qchealth.qchealth_vip.activity.StoreActivity;
import com.beijing.qchealth.qchealth_vip.base.BaseActivity;
import com.beijing.qchealth.qchealth_vip.fragment.FoodMedicalFragment;
import com.beijing.qchealth.qchealth_vip.fragment.HomeFragment;
import com.beijing.qchealth.qchealth_vip.fragment.UserCenterFragment;
import com.beijing.qchealth.qchealth_vip.runtimepermissions.PermissionsManager;
import com.beijing.qchealth.qchealth_vip.runtimepermissions.PermissionsResultAction;

import org.haitao.common.utils.AppLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    List<Fragment> mFragmentList=new ArrayList<>();
    private TextView[] mTabs;
    
    private long lastTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.beijing.qchealth.qchealth_vip.R.layout.activity_main);
        ButterKnife.bind(this);
        requestPermissions();
        
        initView();
        initFragment();
        
    }

    private void initFragment() {
        if (mFragmentList!=null&&mFragmentList.size()==0){
            mFragmentList.add(new HomeFragment());
            mFragmentList.add(new FoodMedicalFragment());
            mFragmentList.add(new UserCenterFragment());
        }
        if (mFragmentList!=null&&mFragmentList.size()>0){
            this.switchFragment(mFragmentList.get(0));
        }
    }

    private void initView() {
        mTabs = new TextView[3];
        mTabs[0] = (TextView) findViewById(R.id.btn_home_tab);
        mTabs[1] = (TextView) findViewById(R.id.btn_mess);
        mTabs[2] = (TextView) findViewById(R.id.btn_profile);
        mTabs[0].setSelected(true);
    }
    @OnClick({R.id.btn_home_tab, R.id.btn_mess,R.id.btn_profile})
    public void onClick(View view) {
        switch (view.getId()) {

            case com.beijing.qchealth.qchealth_vip.R.id.btn_home_tab:
                switchFragment(mFragmentList.get(0));
                break;
//            case com.beijing.qchealth.qchealth_vip.R.id.btn_share:
//                switchFragment(mFragmentList.get(1));
//                break;
            case com.beijing.qchealth.qchealth_vip.R.id.btn_mess:
                
//                switchFragment(mFragmentList.get(1));
                Intent intent=new Intent(MainActivity.this, StoreActivity.class);
                startActivity(intent);
                
                break;
            case com.beijing.qchealth.qchealth_vip.R.id.btn_profile:
                switchFragment(mFragmentList.get(2));
                break;
//            case com.beijing.qchealth.qchealth_vip.R.id.btn_discover:
//                switchFragment(mFragmentList.get(3));
//                break;

        }
    }
    public void setCurrentTabSelected(int index) {
        int length=mTabs.length;
        for (int i = 0; i < length; i++) {
            if (i == index) {
                mTabs[i].setSelected(true);
            } else {
                mTabs[i].setSelected(false);
            }
        }

    }
    public void switchFragment(Fragment currentFragment) {

        AppLog.i("tag", "currentFragment 名称 " + currentFragment.getClass().getSimpleName());
        String fragmentName = currentFragment.getClass().getSimpleName();
        if (fragmentName != null) {
            if (fragmentName.equals("HomeFragment")) {
                setCurrentTabSelected(0);
            } else if (fragmentName.equals("FoodMedicalFragment")) {
                setCurrentTabSelected(1);
            } /*else if (fragmentName.equals("ServiceFragment")) {
                setCurrentTabSelected(2);
            }*/ else if (fragmentName.equals("UserCenterFragment")) {
                setCurrentTabSelected(2);
            }/*else if (fragmentName.equals("DiscoveryFragment")){
                setCurrentTabSelected(3);
            }*/
        }

        try {
            FragmentTransaction transaction = this.getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(com.beijing.qchealth.qchealth_vip.R.id.fragment_container,
                    currentFragment, currentFragment.getClass().getSimpleName());
            transaction.addToBackStack(currentFragment.getClass().getSimpleName());

            transaction.commitAllowingStateLoss();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String currentFragmet = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            
            
            if ((("HomeFragment".equals(currentFragmet)) || "FoodMedicalFragment".equals(currentFragmet)
                     || "UserCenterFragment".equals(currentFragmet))) {

                long time = System.currentTimeMillis();//当前点击按键时间
                if ((time - lastTime) > 3000) {
                    Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT).show();

                } else {
                    //小于1.5s可以退出应用程序
                    finish();
//                    System.exit(0);
                }
                lastTime = time;
                return true;
            } else {

                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                } else {

                    finish();

                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}
