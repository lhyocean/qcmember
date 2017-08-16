package com.beijing.qchealth.qchealth_vip.fragment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by lhy on 2017/8/9.
 */

public class ImageContainerFragment extends BaseFragment{
   
    @Bind(R.id.rl_left_container)
    RelativeLayout rlLeftContainer;
    @Bind(R.id.tv_left)
    TextView tvLeft;
    @Bind(R.id.img_left)
    ImageView imgLeft;
    @Bind(R.id.title_content)
    TextView titleContent;
    @Bind(R.id.rl_right_container)
    RelativeLayout rlRightContainer;
    @Bind(R.id.img_right)
    ImageView imgRight;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.line_view)
    View lineView;
    
    @Bind(R.id.img_show)
    ImageView imgPublic;
    
    @Override
    public View initRootView() {
        mRootView=View.inflate(mContext, R.layout.fragment_img_container,null);
        return mRootView;
    }

    @Override
    public void setViews() {
         if (getArguments()!=null){
             Bundle bundle=getArguments();
             String title=bundle.getString("title");
             int resId=bundle.getInt("resId");
             titleContent.setText(title==null?"":title);
             imgPublic.setImageResource(resId);
         }
        
         imgLeft.setImageResource(R.drawable.arrow_left_back);
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
    
    
}
