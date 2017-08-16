package com.beijing.qchealth.qchealth_vip.fragment.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lhy on 2017/7/28.
 */

public class FreeConsultFragment extends BaseFragment {

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

  
    @Bind(R.id.img_check_public)
    ImageView imgPublic;


    boolean isPublic = false;  // 开关量 是否公开
    boolean isCheckDoctor = false; // 开关量，是否指定医生


    @Override
    public View initRootView() {
        mRootView = View.inflate(mContext, R.layout.fragment_free_consult, null);
        return mRootView;
    }

    @Override
    public void setViews() {
        setTitleStyle();
    }

    private void setTitleStyle() {
        imgLeft.setImageResource(R.drawable.arrow_left_back);
        tvRight.setText("提问");
        tvRight.setTextColor(getResources().getColor(R.color.color_00ba6b));
        titleContent.setText(R.string.title_free_consult);

    }

    @Override
    public void initData() {

    }


    @Override
    public void onResume() {
        super.onResume();

        if (isPublic) {
              imgPublic.setImageResource(R.drawable.check_on);
        } else {
            imgPublic.setImageResource(R.drawable.check_off);
        }

    }

    @OnClick({R.id.rl_left_container, R.id.rl_right_container,  R.id.img_check_public})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_left_container:

                getActivity().finish();

                break;
            case R.id.rl_right_container:


                break;


            case R.id.img_check_public:
                 isPublic=!isPublic;
                if (isPublic) {
                    imgPublic.setImageResource(R.drawable.check_on);
                } else {
                    imgPublic.setImageResource(R.drawable.check_off);
                }
                break;


        }

    }
}
