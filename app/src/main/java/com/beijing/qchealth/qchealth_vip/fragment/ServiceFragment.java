package com.beijing.qchealth.qchealth_vip.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.MainActivity;
import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.event.UpDataMessEvent;
import com.beijing.qchealth.qchealth_vip.utils.HxMessUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by lhy on 2017/6/30.
 */

public class ServiceFragment extends BaseFragment {

    @Bind(R.id.img_mess)
    ImageView imgMess;
    @Bind(R.id.img_hint)
    TextView imgHint;
    @Bind(R.id.rl_mess)
    RelativeLayout relativeLayout;
    @Override
    public View initRootView() {
        
        mRootView=View.inflate(mContext, R.layout.fragment_service,null);
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
        int count= HxMessUtil.getHxUnreadMessCount();
        updataMessCount(count);
    }
    private void updataMessCount(int count) {
        if (imgHint==null){
            return;
        }
        if (count>0){
            imgHint.setVisibility(View.VISIBLE);
        }else {
            imgHint.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_mess,R.id.rl_mess})
    public void onClick(View view){

        switch (view.getId()) {
            case R.id.img_mess:
            case R.id.rl_mess:
                Intent intent=new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }
}
