package com.beijing.qchealth.qchealth_vip.adapter.pageAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.beijing.qchealth.qchealth_vip.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ocean on 2017/5/18.
 *
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;
    private FragmentManager fm;
    private List<String> tabNames=new ArrayList<>();


    public TabPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> list) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        this.tabNames=list;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }



    @Override
    public CharSequence getPageTitle(int position) {
       if (tabNames!=null&&tabNames.size()>0){
           if (position<tabNames.size()){
               return tabNames.get(position);
           }else {
               return "";
           }
       }else {
           return "";
       }
    }
}
