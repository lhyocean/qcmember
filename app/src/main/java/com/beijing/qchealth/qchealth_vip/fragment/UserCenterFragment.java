package com.beijing.qchealth.qchealth_vip.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.MainActivity;
import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.activity.ContainerActivity;
import com.beijing.qchealth.qchealth_vip.activity.LoginActivity;
import com.beijing.qchealth.qchealth_vip.application.MyApplication;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.db.User;
import com.beijing.qchealth.qchealth_vip.event.UpDataMessEvent;
import com.beijing.qchealth.qchealth_vip.fragment.user.SettingFragment;
import com.beijing.qchealth.qchealth_vip.view.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by lhy on 2017/6/30.
 */

public class UserCenterFragment extends BaseFragment {
    
   
    @Bind(R.id.ll_count)
    LinearLayout llCount;
    @Bind(R.id.ll_service_record)
    LinearLayout llServiceRecord;
    @Bind(R.id.ll_patient_evaluate)
    LinearLayout llPatientEvaluate;
    @Bind(R.id.ll_app_share)
    LinearLayout llAppShare;
    @Bind(R.id.ll_suggest_back)
    LinearLayout llSuggestBack;
    @Bind(R.id.ll_online_service)
    LinearLayout llOnlineService;

    @Bind(R.id.img_mess)
    ImageView imgMess;
    @Bind(R.id.img_hint)
    TextView imgHint;
    @Bind(R.id.rl_mess)
    RelativeLayout relativeLayout;
    @Bind(R.id.user_avatar)
    CircleImageView imgAvatar;
    
    @Bind(R.id.ll_setting)
    LinearLayout llSetting;
    @Bind(R.id.user_name)
    TextView useName;
    
    @Override
    public View initRootView() {
        mRootView=View.inflate(mContext, R.layout.fragment_user_center,null);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        return mRootView;
    }

    @Override
    public void setViews() {
         
         
    }

    @Override
    public void initData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void onEventMainThread(UpDataMessEvent event){

        switch (event.getState()) {
            case UpDataMessEvent.MESSAGE_COUNT:
                if (event.getMessage() instanceof Integer){
                    Integer count= (Integer) event.getMessage();
                    updataMessCount(count);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        
      initUser();
        
//        int count= HxMessUtil.getHxUnreadMessCount();
//        updataMessCount(count);
    }

    private void initUser() {
        
         if (MyApplication.getInstance().isLogin()){
             User user=MyApplication.getInstance().user;
             Log.e("---", "initUser: "+user.toString() );
             
         }else {
             
         }
        
    }

    private void updataMessCount(int count) {
//        if (imgHint==null){
//            return;
//        }
//        if (count>0){
//            imgHint.setVisibility(View.VISIBLE);
//        }else {
//            imgHint.setVisibility(View.GONE);
//        }
    }

    @OnClick({R.id.img_mess,R.id.rl_mess,R.id.ll_setting,R.id.ll_online_service,R.id.ll_consult,R.id.user_avatar})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.rl_mess:
            case R.id.img_mess:
                Intent intent=new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                break;
            
            case R.id.ll_setting:
                
                ContainerActivity.startActivity(mContext, SettingFragment.class,null);
                
                break;
            
            case R.id.ll_online_service:
                
//                intent=new Intent(mContext, ChatActivity.class);
//                intent.putExtra("userId","15011585275");
//                startActivity(intent);
                
                break;
            
            case R.id.ll_consult:

//                intent=new Intent(mContext, ChatActivity.class);
//                intent.putExtra("userId","lhyvip");
//                startActivity(intent);
                break;
            
            case R.id.user_avatar:
                 Intent intent1=new Intent(mContext, LoginActivity.class);
                 mContext.startActivity(intent1);
                
                break;
        }
    }
    
    
    
}
