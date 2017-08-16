package com.beijing.qchealth.qchealth_vip.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by ocean on 2017/5/31.
 */
public abstract class BaseFragment  extends Fragment {
    /**
     * 根元素
     */
    protected View mRootView;
    /**
     * 布局渲染工具
     */
    protected Context mContext;
    private String TAG = getClass().getSimpleName();

    public View getRootView() {
        return mRootView;
    }
    /**
     * 初始化View,加载布局
     *
     * @return 布局
     */
    public abstract View initRootView();


    /**
     * 初始化数据
     */
    public abstract void setViews();

    /**
     * 加载网络数据
     */
    public abstract void initData();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setViews();
        initData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = initRootView();
        ButterKnife.bind(this, mRootView);
        return mRootView;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        
        if (EventBus.getDefault().isRegistered(getActivity())){
            EventBus.getDefault().unregister(getActivity());
        }
        
    }
    
    
    
}
