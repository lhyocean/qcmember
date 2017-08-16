package com.beijing.qchealth.qchealth_vip.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.adapter.HomeConsultAdapter;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lhy on 2017/7/4.
 */

public class HomeConsultListFragment extends BaseFragment {
    @Bind(R.id.rec)
    RecyclerView mRec;
    
    @Override
    public View initRootView() {
        mRootView=View.inflate(mContext, R.layout.common_list,null);
        return mRootView;
    }

    @Override
    public void setViews() {
        mRec.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        HomeConsultAdapter mAdapter=new HomeConsultAdapter(mContext,null);
        mRec.setAdapter(mAdapter); 
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
