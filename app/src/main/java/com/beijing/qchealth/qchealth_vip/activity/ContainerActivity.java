package com.beijing.qchealth.qchealth_vip.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.base.BaseSwipeActivity;

import org.haitao.common.utils.AppLog;


/**
 * Created by lyq on 2016/12/2.
 */
public class ContainerActivity<T> extends BaseSwipeActivity {
    public static final String EXTRA_FRAGMENT_CLASS_NAME = "class_name";
    private static String TAG = "ContainerActivity";
    private BaseFragment mFrg;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        Bundle bundle = getIntent().getExtras();
        String className = bundle.getString(EXTRA_FRAGMENT_CLASS_NAME);
        String tag = className.substring(className.lastIndexOf(".") + 1);

        setContentView(R.layout.container_activity);
        
       

        try {
            if (className != null) {
                Class<?> fragmentC = Class.forName(className);
                mFrg = (BaseFragment) fragmentC.newInstance();
                mFrg.setArguments(bundle);
            }
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fl, mFrg);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.commit();
    }
    

    /**
     * 打开容器activity
     *
     * @param context
     * @param frgClass

     */
    public static void startActivity(Context context, Class<? extends BaseFragment> frgClass, Bundle extras  ) {


        AppLog.i(TAG,"当前fragment:"+frgClass.getSimpleName());
        Intent i = new Intent(context, ContainerActivity.class);
        i.putExtra(EXTRA_FRAGMENT_CLASS_NAME, frgClass.getName());

        if (extras != null)
            i.putExtras(extras);

        context.startActivity(i);
    }

}
