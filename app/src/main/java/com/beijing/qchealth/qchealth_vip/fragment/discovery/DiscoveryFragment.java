package com.beijing.qchealth.qchealth_vip.fragment.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.MainActivity;
import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.adapter.pageAdapter.TabPagerAdapter;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.event.UpDataMessEvent;
import com.beijing.qchealth.qchealth_vip.utils.HxMessUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * Created by lhy on 2017/7/25.
 */

public class DiscoveryFragment extends BaseFragment {
    
    List<BaseFragment>  mFragments=new ArrayList<>();
    @Bind(R.id.my_tabs)
    SlidingTabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    List<String> tabNames=new ArrayList<>();


    @Bind(R.id.img_mess)
    ImageView imgMess;
    @Bind(R.id.img_hint)
    TextView imgHint;
    @Bind(R.id.rl_mess)
    RelativeLayout relativeLayout;
    
    @Override
    public View initRootView() {
        mRootView=View.inflate(mContext, R.layout.fragment_discovery,null);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        return mRootView;
    }

    @Override
    public void setViews() {

        
        if (tabNames!=null&&tabNames.size()==0){
            tabNames.add("内容");
            tabNames.add("推荐");
        }
        if (mFragments!=null&&mFragments.size()==0){
            mFragments.add(new DiscoveryContentFragment());
            mFragments.add(new DiscoveryRecommendFragment());
        }

        TabPagerAdapter coursePagerAdapter=new TabPagerAdapter(getChildFragmentManager(),mFragments,tabNames);
        mViewPager.setAdapter(coursePagerAdapter);
        mTabLayout.setViewPager(mViewPager);  
        

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
