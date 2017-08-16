package com.beijing.qchealth.qchealth_vip.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


/**
 * Created by lhy on 2017/7/25.
 */

public class ColorFilterImageView extends ImageView  {
    public ColorFilterImageView(Context context) {
        this(context,null,0);
     
    }

    public ColorFilterImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
       
    }

    public ColorFilterImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:  // 按下时图像变灰
                setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                break;
            case MotionEvent.ACTION_UP:   // 手指离开或取消操作时恢复原色
            case MotionEvent.ACTION_CANCEL:
                clearColorFilter();
                break;
            default:

                break;
        }
        return false;
    }
}
