package com.beijing.qchealth.qchealth_vip.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.application.MyApplication;
import com.beijing.qchealth.qchealth_vip.base.BaseFragment;
import com.beijing.qchealth.qchealth_vip.db.DbOpenHelper;
import com.beijing.qchealth.qchealth_vip.db.DbUtils;
import com.beijing.qchealth.qchealth_vip.db.User;
import com.beijing.qchealth.qchealth_vip.http.AppNetUtil;
import com.beijing.qchealth.qchealth_vip.http.NetCallBack;
import com.beijing.qchealth.qchealth_vip.http.ResultModel;
import com.beijing.qchealth.qchealth_vip.utils.Common;
import com.beijing.qchealth.qchealth_vip.utils.ImageUtil;
import com.beijing.qchealth.qchealth_vip.utils.MyFilterUtil;
import com.beijing.qchealth.qchealth_vip.utils.PhoneUtil;
import com.beijing.qchealth.qchealth_vip.view.MyDialog;

import org.haitao.common.utils.AppLog;
import org.haitao.common.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lhy on 2017/7/28.
 */

public class RegisterFragment extends BaseFragment implements TextWatcher {

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

    private String type = "";

    @Bind(R.id.input_phone)
    EditText inputPhone;
    @Bind(R.id.input_pass)
    EditText inputPass;
    @Bind(R.id.input_code)
    EditText inputCode;

    @Bind(R.id.tv_get_code)
    TextView tvGetCode;
    @Bind(R.id.tv_login)
    TextView tvLogin;

    CountDownTimer mTimer;
    private boolean isOnTimerTick = false;

    @Override
    public View initRootView() {
        mRootView = View.inflate(mContext, R.layout.fragment_register, null);
        return mRootView;
    }

    @Override
    public void setViews() {
        if (getArguments() != null) {
            type = getArguments().getString(Common.LOGIN_TYPE, "");
        }
        setTitleStyle();
    }

    private void setTitleStyle() {
        lineView.setVisibility(View.GONE);
        if (type != null && type.equals(Common.LOGIN_FIND_PASS)) {
            titleContent.setText(R.string.title_find_pass);
        } else if (type != null && type.equals(Common.REGISTER)) {
            titleContent.setText(R.string.title_regist);
        }
        imgLeft.setImageResource(R.drawable.arrow_left_back);
        inputPass.setFilters(new InputFilter[]{MyFilterUtil.mInputFilter, MyFilterUtil.myPassInputFilter});
        inputPass.addTextChangedListener(this);
        inputCode.addTextChangedListener(this);
        inputPhone.addTextChangedListener(this);
        
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


    @OnClick({R.id.rl_left_container, R.id.tv_login, R.id.tv_get_code,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_left_container:
                getActivity().finish();
                break;
            case R.id.tv_login:
                registnow();
                break;
            case R.id.tv_get_code:
                getCode();
                break;


        }
    }

    private void registnow() {
        String phone = inputPhone.getText().toString().trim();
        String pass = inputPass.getText().toString().trim();
        String code = inputCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.shortShowToast("请输入手机号");
            return;
        }

        if (!PhoneUtil.checkCellphone(phone)) {
            ToastUtil.shortShowToast("请输入合法手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.shortShowToast("请输入验证码");
            return;
        }

        if (TextUtils.isEmpty(pass) || pass.length() < 8) {
            ToastUtil.shortShowToast("密码长度不够");
            return;
        }
        
        registReal(phone, pass, code);

    }

    private void registReal(String phone, String pass, String code) {
        AppNetUtil.getInstance(mContext).userRegister(phone, pass, code, new NetCallBack() {
            @Override
            public void onSuccess(String mssage, ResultModel model) {
                ToastUtil.shortShowToast(mssage);
                if (model!=null&&model.getModel()!=null){
                    User u=model.getModel();
                    if (u!=null){
                        DbUtils utils=new DbUtils(mContext);
                        utils.deleteTableData(DbOpenHelper.USER_TABLE);
                        long  ret=utils.insertUser(u);
                        if (ret!=-1){
                            MyApplication.getInstance().saveUser(u);
                            getActivity().finish();
                        }else {
                            AppLog.e("插入数据库失败");
                        }
                    }
                }
            }

            @Override
            public void onFail(boolean dataError, int errorCode, String mssage) {
                ToastUtil.shortShowToast(mssage);
            }
        }, User.class);
    }

    private void getCode() {
        if (isOnTimerTick) {
            return;
        }

        String phone = inputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.shortShowToast("请输入手机号");
            return;
        }

        if (!PhoneUtil.checkCellphone(phone)) {
            ToastUtil.shortShowToast("请输入合法手机号");
            return;
        }
        
//        getImageCode();

        showImgVerifyDialog();

    }
    

    private void getVerificationCode(String imgcode, String phone) {
        AppNetUtil.getInstance(mContext).sendCode(phone, "1", imgcode, new NetCallBack() {
            @Override
            public void onSuccess(String mssage, ResultModel model) {
                ToastUtil.shortShowToast(mssage);
            }

            @Override
            public void onFail(boolean dataError, int errorCode, String mssage) {
                ToastUtil.shortShowToast(mssage);
            }
        }, null);

    }

    private void startTimer() {
        mTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isOnTimerTick = true;
                if (tvGetCode != null) {
                    tvGetCode.setClickable(false);
                    tvGetCode.setText("" + millisUntilFinished / 1000 + "s后重新获取");
                }
            }

            @Override
            public void onFinish() {
                isOnTimerTick = false;
                if (tvGetCode != null) {
                    tvGetCode.setClickable(true);
                    tvGetCode.setText("获取验证码");
                }
            }
        };
        mTimer.start();

    }

    private void showImgVerifyDialog() {

        final MyDialog exitsDialog = new MyDialog(mContext, R.style.load_dialog);
        View exitsView = View.inflate(mContext, R.layout.pop_img_verify, null);
        TextView confirmTv = (TextView) exitsView.findViewById(R.id.tv_sure);
        RelativeLayout cancelTv = (RelativeLayout) exitsView.findViewById(R.id.rl_close);
        final ImageView img = (ImageView) exitsView.findViewById(R.id.img_code);
        final EditText ed = (EditText) exitsView.findViewById(R.id.edit_img_code);
        ImageUtil.loadImgCode(mContext, img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtil.loadImgCode(mContext, img);
            }
        });

        exitsDialog.setContentView(exitsView);
        confirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = ed.getText().toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    exitsDialog.dismiss();
                    startTimer();
                    String phone = inputPhone.getText().toString().trim();
                    getVerificationCode(str, phone);

                } else {
                    ToastUtil.shortShowToast("请输入图片验证码");
                }

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


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String phone = inputPhone.getText().toString().trim();
        String pass = inputPass.getText().toString().trim();
        String code = inputCode.getText().toString().trim();
        
        if (!TextUtils.isEmpty(phone) && phone.length() == 11 && !TextUtils.isEmpty(pass) && pass.length() >= 8 && !TextUtils.isEmpty(code) && code.length() >= 6) {
            tvLogin.setBackgroundResource(R.drawable.login_bg_correct);
        } else {
            tvLogin.setBackgroundResource(R.drawable.login_bg_error);
        }
    }
}
