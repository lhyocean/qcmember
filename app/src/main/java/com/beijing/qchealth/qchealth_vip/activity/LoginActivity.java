package com.beijing.qchealth.qchealth_vip.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beijing.qchealth.qchealth_vip.MainActivity;
import com.beijing.qchealth.qchealth_vip.R;
import com.beijing.qchealth.qchealth_vip.db.User;
import com.beijing.qchealth.qchealth_vip.fragment.RegisterFragment;
import com.beijing.qchealth.qchealth_vip.http.AppNetUtil;
import com.beijing.qchealth.qchealth_vip.http.NetCallBack;
import com.beijing.qchealth.qchealth_vip.http.ResultModel;
import com.beijing.qchealth.qchealth_vip.utils.Common;
import com.beijing.qchealth.qchealth_vip.utils.MyFilterUtil;
import com.beijing.qchealth.qchealth_vip.utils.PhoneUtil;
import com.beijing.qchealth.qchealth_vip.utils.StatusBarUtil;
import com.beijing.qchealth.qchealth_vip.utils.Tools;

import org.haitao.common.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends FragmentActivity implements TextWatcher {
    @Bind(R.id.rl_login)
    RelativeLayout rlLogin;

    @Bind(R.id.input_pass)
    EditText edPass;
    @Bind(R.id.input_phone)
    EditText edPhone;
    @Bind(R.id.ll_img_delete)
    LinearLayout llDelete;
    @Bind(R.id.ll_img_container)
    LinearLayout llImgPassVisible;
    @Bind(R.id.img_visible_icon)
    ImageView imgVisibleIcon;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_forget_pass)
    TextView tvForgetPass;
    @Bind(R.id.tv_hint)
    TextView tvHint;


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


    boolean isPassVisibla = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtil.StatusBarLightMode(this);


        setView();

        setTitleStyle();

    }

    private void setTitleStyle() {

        lineView.setVisibility(View.GONE);
        imgLeft.setImageResource(R.drawable.login_close);
        tvRight.setText(R.string.title_regist);

    }

    private void setView() {

        edPass.setFilters(new InputFilter[]{MyFilterUtil.mInputFilter, new InputFilter.LengthFilter(20)});
        edPhone.addTextChangedListener(this);
        edPass.addTextChangedListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

//        loginreal();

    }

    @OnClick({R.id.rl_login, R.id.tv_login, R.id.tv_hint, R.id.tv_forget_pass, R.id.ll_img_delete, R.id.ll_img_container, R.id.rl_right_container, R.id.rl_left_container})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_login:


                break;
            case R.id.tv_forget_pass:
                ToastUtil.shortShowToast("找回密码");

                Bundle bundle1 = new Bundle();
                bundle1.putString(Common.LOGIN_TYPE, Common.LOGIN_FIND_PASS);
                ContainerActivity.startActivity(this, RegisterFragment.class, bundle1);

                break;
            case R.id.tv_hint:
                ToastUtil.shortShowToast("用户协议页面");

                break;
            case R.id.tv_login:
                String phone = edPhone.getText().toString().trim();
                String pass = edPass.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.shortShowToast("请输入手机号");
                    return;
                }
                if (!PhoneUtil.checkCellphone(phone)) {
                    ToastUtil.shortShowToast("手机号不合法");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    ToastUtil.shortShowToast("请输入密码");
                    return;
                }

                if (pass.length() < 6) {
                    ToastUtil.shortShowToast("密码长度不够");
                    return;
                }

                loginreal(phone, pass);

                break;
            case R.id.ll_img_container:
                if (isPassVisibla) {
                    edPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    imgVisibleIcon.setImageResource(R.drawable.login_pass_invisible);
                } else {
                    edPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    imgVisibleIcon.setImageResource(R.drawable.login_pass_visible);
                }
                isPassVisibla = !isPassVisibla;
                Editable etable = edPass.getText();
                Selection.setSelection(etable, etable.length());

                break;
            case R.id.ll_img_delete:
                edPhone.setText("");
                break;

            case R.id.rl_right_container:
                Bundle bundle = new Bundle();
                bundle.putString(Common.LOGIN_TYPE, Common.REGISTER);
                ContainerActivity.startActivity(this, RegisterFragment.class, bundle);
                LoginActivity.this.finish();
                break;
            case R.id.rl_left_container:

                LoginActivity.this.finish();
                break;


        }
    }

    private void loginreal(String phone, String pass) {
        Tools.showDialog(this);
        AppNetUtil.getInstance(this).userLogin(phone, pass, new NetCallBack() {
            @Override
            public void onSuccess(String mssage, ResultModel model) {
                
            }

            @Override
            public void onFail(boolean dataError, int errorCode, String mssage) {

            }
        }, User.class);

//        loginHx(phone, pass);
    }

    private void loginHx(String phone, String pass) {
        Intent intent = new Intent(LoginActivity.this,
                MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        finish();

    }
    

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String phone = edPhone.getText().toString().trim();
        String strpass = edPass.getText().toString().trim();

        if (strpass.length() >= 6 && phone.length() == 11) {
            tvLogin.setBackgroundResource(R.drawable.login_bg_correct);
        } else {
            tvLogin.setBackgroundResource(R.drawable.login_bg_error);
        }

        if (phone.length() > 0) {
            llDelete.setVisibility(View.VISIBLE);
        } else {
            llDelete.setVisibility(View.GONE);
        }

    }
}
