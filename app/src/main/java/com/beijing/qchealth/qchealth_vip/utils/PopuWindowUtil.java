package com.beijing.qchealth.qchealth_vip.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by ocean on 2017/5/5.
 */
public class PopuWindowUtil {
    private Activity mContext;
    private View mContentView;
    private int  mWidth =0;
    private int  mHight =0;
    private View mAttachView;
    public static final  int SHOW_WINDOW_BOTTOM =1;
    public static final  int SHOW_WINDOW_CENTER =2;
    public static final  int SHOW_WINDOW_DOWN =3;
    public static final  int SHOW_VIEW_TOP =4;
    private PopupWindow mPopuWindow;

    public PopuWindowUtil(Activity context, int layoutId, int width, int hight, View view) {
        mContext = context;
        mContentView = View.inflate(context, layoutId, null);
        mWidth = width;
        mHight = hight;
        mAttachView = view;
    }

    public View showPopuWindow(int showLocationType){
        mPopuWindow = new PopupWindow(mContentView, mWidth, mHight, true);
        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置PopupWindow的弹出和消失效果
        // mTwoPopu.setAnimationStyle(R.style.AlertChooserAnimation);
        mPopuWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        backgroundAlpha(0.6f);
        mPopuWindow.setOnDismissListener(new popupDismissListener());
        // 点击其他地方消失
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        switch(showLocationType){
            case SHOW_WINDOW_DOWN:
                mPopuWindow.showAsDropDown(mAttachView);
                break;
            case SHOW_WINDOW_BOTTOM:
                mPopuWindow.showAtLocation(mAttachView, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case SHOW_WINDOW_CENTER:
                mPopuWindow.showAtLocation(mAttachView, Gravity.CENTER, 0, 0);
                 break;
            case SHOW_VIEW_TOP:
                int[] location = new int[2];
                mAttachView.getLocationOnScreen(location);
                mPopuWindow.showAtLocation(mAttachView, Gravity.NO_GRAVITY, (location[0]+mAttachView.getWidth()/2)-mWidth/2,
                        location[1]-mHight);
                break;

        }

        return mContentView;
    }

    public PopupWindow getPopuWindow(){

        return mPopuWindow;
    }




    public View showPopuWindowNoBackground(int showLocationType){
        mPopuWindow = new PopupWindow(mContentView, mWidth, mHight, true);
        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置PopupWindow的弹出和消失效果
        // mTwoPopu.setAnimationStyle(R.style.AlertChooserAnimation);
        mPopuWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopuWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        backgroundAlpha(1f);
        mPopuWindow.setOnDismissListener(new popupDismissListener());
        // 点击其他地方消失
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        switch(showLocationType){
            case SHOW_WINDOW_DOWN:
                mPopuWindow.showAsDropDown(mAttachView);
                break;
            case SHOW_WINDOW_BOTTOM:
                mPopuWindow.showAtLocation(mAttachView, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case SHOW_WINDOW_CENTER:
                mPopuWindow.showAtLocation(mAttachView, Gravity.CENTER, 0, 0);
                 break;
            case SHOW_VIEW_TOP:
                int[] location = new int[2];
                mAttachView.getLocationOnScreen(location);
                mPopuWindow.showAtLocation(mAttachView, Gravity.NO_GRAVITY, (location[0]+mAttachView.getWidth()/2)-mWidth/2,
                        location[1]-mHight);
                break;

        }

        return mContentView;
    }

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
            if(listener!=null){
                listener.onDismiss();
            }

        }
    }

    private OtherPlacePopupDismissListener listener;

    public OtherPlacePopupDismissListener getOtherPlacePopupDismissListener() {
        return listener;
    }

    public void setOtherPlacePopupDismissListener(OtherPlacePopupDismissListener listener) {
        this.listener = listener;
    }

    public  interface OtherPlacePopupDismissListener{

        public  void onDismiss();
    }




    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
    }

    public void dismissPopuWindow(){
        if(mPopuWindow.isShowing()){
            mPopuWindow.dismiss();

    }}


}
