package com.beijing.qchealth.qchealth_vip.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.activity.ContainerActivity;
import com.beijing.qchealth.qchealth_vip.application.MyApplication;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.db.DbOpenHelper;
import com.beijing.qchealth.qchealth_vip.db.DbUtils;
import com.beijing.qchealth.qchealth_vip.fragment.home.MessagNotifySetFragment;
import com.beijing.qchealth.qchealth_vip.view.MyDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhy on 2017/7/21.
 */

public class SettingFragment extends BaseFragment {
    
    @Bind(R.id.tv_left)
    TextView mTvLeft;
    @Bind(R.id.img_left)
    ImageView mImgLeft;
    @Bind(R.id.title_content)
    TextView mTitileContent;
    @Bind(R.id.img_right)
    ImageView mImgRight;
    @Bind(R.id.tv_right)
    TextView mTvRight;
    @Bind(R.id.head_continer)
    RelativeLayout mHeadContiner;
    @Bind(R.id.rl_right_container)
    RelativeLayout rlRightContainer;
    @Bind(R.id.rl_left_container)
    RelativeLayout rlLeftContainer;
    @Bind(R.id.tv_login_out)
    TextView loginOut;
    
    
    @Override
    public View initRootView() {
        mRootView=View.inflate(mContext, R.layout.setting_fragment,null);
        return mRootView;
    }

    @Override
    public void setViews() {
        
        setTitleStyle();

    }

    private void setTitleStyle() {
        
        mImgLeft.setImageResource(R.drawable.back_second);
        mTitileContent.setText(R.string.title_setting);
        rlLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        
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
    
    @OnClick({R.id.tv_login_out,R.id.ll_notify})
    public  void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_login_out:

                final MyDialog exitsDialog = new MyDialog(mContext, R.style.load_dialog);
                View exitsView = View.inflate(mContext, R.layout.exits_view, null);
                TextView confirmTv = (TextView) exitsView.findViewById(R.id.confirm_tv);
                TextView cancelTv = (TextView) exitsView.findViewById(R.id.cancel_tv);

                exitsDialog.setContentView(exitsView);
                confirmTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitsDialog.dismiss();
                        loginOutHx();
                    }
                });
                cancelTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitsDialog.dismiss();
                    }
                });
                exitsDialog.show();
                exitsDialog.setCancelable(false);
                
                break;
            
            case R.id.ll_notify:

                ContainerActivity.startActivity(mContext, MessagNotifySetFragment.class,null);
                
                break;
        }
    }

    private void loginOutHx() {

        MyApplication.getInstance().saveUser(null);
        DbUtils utils=new DbUtils(mContext);
        utils.deleteTableData(DbOpenHelper.USER_TABLE);
        getActivity().finish();
        
//        Intent intent1 = new Intent(mContext, MainActivity.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        getActivity().overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
//        mContext.startActivity(intent1);
    }

}
