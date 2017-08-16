package com.beijing.qchealth.qchealth_vip.fragment.discovery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.adapter.DiscoveryRecommendAdapter;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.entity.TestBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by lhy on 2017/7/25.
 */

public class DiscoveryRecommendFragment extends BaseFragment {

    List<TestBean> mBeanlist=new ArrayList<>();

    @Bind(R.id.rec_special_skill)
    RecyclerView recSkill;
    @Bind(R.id.rec_special_food)
    RecyclerView recFood;
    
    @Override
    public View initRootView() {
        mRootView=View.inflate(mContext, R.layout.fragment_recommended,null);
        return mRootView;
    }

    @Override
    public void setViews() {


        DiscoveryRecommendAdapter adapter=new DiscoveryRecommendAdapter(mContext,mBeanlist);
        GridLayoutManager manager=new GridLayoutManager(mContext, 1){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        GridLayoutManager managerskill=new GridLayoutManager(mContext, 1){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        
        recFood.setLayoutManager(manager);
        recSkill.setLayoutManager(managerskill);
        recFood.setAdapter(adapter);
        recSkill.setAdapter(adapter);
        
        

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
    
}
