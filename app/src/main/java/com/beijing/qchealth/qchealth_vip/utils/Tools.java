package com.beijing.qchealth.qchealth_vip.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.application.MyApplication;

import org.haitao.common.utils.AppLog;

import java.util.Random;

public class Tools {
    private static Context context;
    private static Dialog ad;
    private static TextView tv;
    private static Dialog progressDialog;
    private static ProgressBar proBar;
    private static TextView progressTv;
    private static String tag = "TOOLS";


    /**
     * @param context
     * @param view
     * @param x           显示的x坐标
     * @param y           显示的y坐标
     * @param width       dialog的宽度
     * @param height      dialog的高度
     * @param animation   显示和消失的动画效果
     * @param cancleAble  是否可以cancle掉
     * @param isAnimation
     * @return
     */
    private static Dialog showDialog(Context context, View view, int x, int y, int width, int height, int animation, boolean cancleAble, boolean isAnimation) {
        Dialog ad;
        if (Build.VERSION.SDK_INT >= 11) {
            ad = new AlertDialog.Builder(context, R.style.alert_dialog).create();
        } else {
            ad = new Dialog(context, R.style.alert_dialog);
        }
        ad.setCancelable(cancleAble);
        LayoutParams lp = ad.getWindow().getAttributes();
        lp.x = x;
        lp.y = y;
        ad.show();
        ad.setContentView(view);
        ad.getWindow().setLayout(width, height);
    /*	if (isAnimation) {
            if (animation != 0)
				ad.getWindow().setWindowAnimations(animation);
			else
				ad.getWindow().setWindowAnimations(R.style.head_in_out);
		}*/
        ad.getWindow().clearFlags(LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        return ad;
    }


    /**
     * 弹出提示用户等待的dialog
     */
    public static Dialog showDialog(final Context context) {
        showWaitDialog(context, "请稍等", false);
        return ad;
    }

    /**
     * 弹出提示用户等待的dialog
     */
    public static void showWaitDialog(final Context context, String str) {
        showWaitDialog(context, str, false);
    }

    /**
     * 弹出提示用户等待的dialog
     */
    public static void showWaitDialog(final Context context, String str, boolean canCancle) {
        if (ad == null || !ad.isShowing()) {
            View vv = View.inflate(context, R.layout.dialog_wait, null);
            tv = (TextView) vv.findViewById(R.id.tv);
            if (!Tools.isEmptyStr(str)) {
                tv.setText(str);
            }
            ad = showDialog(context, vv, 0, 0, (int) (Tools.getsx(context) / 1.8), (int) (Tools.getsy(context) / 6.5), 0, canCancle, false);
        } else {
            if (!Tools.isEmptyStr(str)) {
                tv.setText(str);
            }
            ad.setCancelable(canCancle);
        }
    }

    public static boolean isEmptyStr(String str) {
        return (str == null || str.trim().length() < 1);
    }

    public static int getsx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getsy(Context context) {
        int px = Resources.getSystem().getDimensionPixelSize(Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
        return context.getResources().getDisplayMetrics().heightPixels - px;
    }

    /**
     * 取消等待的dialog
     */
    public static void dismissWaitDialog() {
        if (ad != null)
            ad.dismiss();
    }

    /**
     * 获取视频缩略图  可能为null 用前先判断
     *
     * @param videoPath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MICRO_KIND);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public static Context getContext() {
        return MyApplication.getInstance().applicationContext;
    }

    /**
     * 获取资源文件
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * @param stringArrayId 传递在xml中定义字符串数组id
     * @return 通过id获取到的字符串数组
     */
    public static String[] getStringArray(int stringArrayId) {
        return getResources().getStringArray(stringArrayId);
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                Runnable r = (Runnable) msg.obj;
                r.run();
            }
        }

        ;
    };

    /**
     * 主线程回到使用
     */
    public static void runOnUI(Runnable run) {
        Message me = handler.obtainMessage();
        me.obj = run;
        handler.sendMessage(me);
    }

    /**
     * 启动一个新的Activity
     *
     * @param from 起始Activity
     * @param to   跳转Activity
     */
    public static void goActivity(Context from, Class<? extends Activity> to, String[] keys, String[] values) {
        Intent in = new Intent(from, to);
        if (keys != null && values != null)
            for (int i = 0; i < keys.length; i++)
                in.putExtra(keys[i], values[i]);
        from.startActivity(in);
    }

    /**
     * 获取随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    private static Runnable run;
    private static int delaytime;

    public static void startTask(Runnable _run, int _delaytime) {
        run = _run;
        delaytime = _delaytime;
        handler2.postDelayed(runnable, 0);
        // 开始Timer
    }

    public static void finishTask() {
        handler2.removeCallbacks(runnable);           //停止Timer
        run = null;// 提醒回收
    }

    private static Handler handler2 = new Handler();

    private static Runnable runnable = new Runnable() {

        public void run() {
            if (run != null) {
                run.run();
            }
            handler2.postDelayed(this, delaytime);     //postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
        }
    };

    /**
     * 根据包名判断是否安装该应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);
            // problem happened
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void onPrpgress(int bytesWritten, int totalSize) {
        AppLog.i(tag,"onPrpgress:"+bytesWritten+" totalSize:"+totalSize);
        if (progressDialog!=null&&progressDialog.isShowing()){
            if (proBar!=null){
                proBar.setMax(totalSize);
                proBar.setProgress(bytesWritten);
            }
            if (progressTv!=null){

                if (totalSize!=0){
                    int progress=bytesWritten*100/totalSize;
                    progressTv.setText("上传图片进度： "+progress+"%");
                }
            }

        }
    }

    public static Dialog showProgress(Context context) {

        if (progressDialog == null || (!progressDialog.isShowing())) {
            View vv = View.inflate(context, R.layout.progress_layout, null);
            progressTv = (TextView) vv.findViewById(R.id.tv_progress);
            proBar= (ProgressBar) vv.findViewById(R.id.progress_update);
            proBar.setProgress(0);
            progressTv.setText("上传图片进度： "+"0%");
            progressDialog = showDialog(context, vv, 0, 0, (int) (Tools.getsx(context) / 1.2), (int) (Tools.getsy(context) / 5), 0, false, false);
        } else {
            if(progressDialog!=null){
                progressDialog.show();
                progressDialog.setCancelable(false);
            }


        }

        return  progressDialog;
    }

    public static void dismissProgress() {
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
