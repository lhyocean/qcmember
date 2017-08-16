package com.beijing.qchealth.qchealth_vip.fragment.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.view.EaseSwitchButton;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lhy on 2017/7/28.
 */

public class MessagNotifySetFragment extends BaseFragment {


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
    
    @Bind(R.id.rl_switch_vibrate)
    RelativeLayout rl_switch_vibrate;
    @Bind(R.id.rl_switch_sound)
    RelativeLayout rl_switch_sound;
    @Bind(R.id.rl_switch_notification)
    RelativeLayout rl_switch_notification;
    
    @Bind(R.id.switch_notification)
    EaseSwitchButton notifySwitch;
    @Bind(R.id.switch_sound)
    EaseSwitchButton soundSwitch;
    @Bind(R.id.switch_vibrate)
    EaseSwitchButton vibrateSwitch;
  
    
    @Override
    public View initRootView() {
        mRootView=View.inflate(mContext, R.layout.fragment_message_notify,null);
        return mRootView;
    }

    @Override
    public void setViews() {
        setTitleStyle();
       
        
        
        
    }

    private void setTitleStyle() {
        imgLeft.setImageResource(R.drawable.arrow_left_back);
        titleContent.setText(R.string.title_message_notify);
    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.rl_left_container,R.id.rl_switch_vibrate,R.id.rl_switch_sound,R.id.switch_notification})
    public void onClick(View view){

        switch (view.getId()) {
            
            case R.id.rl_left_container:
                 getActivity().finish();
                break;
            
            case R.id.rl_switch_vibrate:
                if (vibrateSwitch.isSwitchOpen()) {
                    vibrateSwitch.closeSwitch();

                } else {
                    vibrateSwitch.openSwitch();

                }
                break;
            case R.id.rl_switch_sound:
                if (soundSwitch.isSwitchOpen()) {
                    soundSwitch.closeSwitch();

                } else {
                    soundSwitch.openSwitch();
       
                }
                
                break;
            case R.id.switch_notification:
                if (notifySwitch.isSwitchOpen()) {
                    notifySwitch.closeSwitch();
                    rl_switch_sound.setVisibility(View.GONE);
                    rl_switch_vibrate.setVisibility(View.GONE);
            
                } else {
                    notifySwitch.openSwitch();
                    rl_switch_sound.setVisibility(View.VISIBLE);
                    rl_switch_vibrate.setVisibility(View.VISIBLE);
                  
                }
            
            break;
        }
        
        
    }    
    
    
}
