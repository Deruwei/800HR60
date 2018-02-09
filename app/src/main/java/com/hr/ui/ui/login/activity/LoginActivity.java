package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.ui.login.model.LoginModel;
import com.hr.ui.ui.login.presenter.LoginPresenter;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ThirdPartLoginUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/6.
 */

public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {

    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_phoneLoginNumber)
    EditText etPhoneLoginNumber;
    @BindView(R.id.et_phoneLoginPsw)
    EditText etPhoneLoginPsw;
    @BindView(R.id.rl_phoneLoginHiddenPsw)
    RelativeLayout rlPhoneLoginHiddenPsw;
    @BindView(R.id.iv_phoneLoginHiddenPsw)
    ImageView ivPhoneLoginHiddenPsw;
    @BindView(R.id.tv_phoneLoginFindPsw)
    TextView tvPhoneLoginFindPsw;
    @BindView(R.id.ll_phoneLogin_middle)
    LinearLayout llPhoneLoginMiddle;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.iv_phoneLoginQQ)
    ImageView ivPhoneLoginQQ;
    @BindView(R.id.iv_phoneLoginWeChat)
    ImageView ivPhoneLoginWeChat;
    @BindView(R.id.rl_phoneLoginThirdPart)
    RelativeLayout rlPhoneLoginThirdPart;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.iv_phoneLoginNumberIcon)
    ImageView ivPhoneLoginNumberIcon;
    @BindView(R.id.iv_phoneLoginPhoneDelete)
    ImageView ivPhoneLoginPhoneDelete;
    @BindView(R.id.iv_phoneLoginPswIcon)
    ImageView ivPhoneLoginPswIcon;
    @BindView(R.id.iv_phoneLoginPswDelete)
    ImageView ivPhoneLoginPswDelete;
    @BindView(R.id.btn_phoneLoginOK)
    Button btnPhoneLoginOK;
    @BindView(R.id.tv_phoneLoginAccountLogin)
    TextView tvPhoneLoginAccountLogin;
    private boolean isHidden = true;
    private String phoneNum, psw;
    private SharedPreferencesUtils sUtils;
    private int[] imageIds = {R.mipmap.resume1, R.mipmap.resume2, R.mipmap.resume3, R.mipmap.resume4, R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void sendLoginSuccess(int userId) {
        MobclickAgent.onEvent(mContext,"v6_login_user");
        MobclickAgent.onProfileSignIn(userId+"");
        this.userId = userId;
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        sUtils.setIntValue(Constants.AUTOLOGINTYPE, 0);
        sUtils.setStringValue(Constants.USERPHONE, phoneNum);
        mPresenter.getResumeList();
    }

    @Override
    public void thirdPartLoginSuccess(int userId) {
        this.userId = userId;
        MobclickAgent.onProfileSignIn("WB",userId+"");
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        if ("QQ".equals(Constants.TYPE_THIRDPARTLOGIN)) {
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 2);
        } else {
            sUtils.setIntValue(Constants.AUTOLOGINTYPE, 3);
        }
        mPresenter.getResumeList();
    }

    @Override
    public void thirdPartLoginGoToBind() {
        BindNewAccountAcitvity.startAction(this);
    }

    @Override
    public void bindingSuccess(int userId) {

    }


    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        ToolUtils.getInstance().judgeResumeMultipleOrOne(this, multipleResumeBean, userId, imageIds, mPresenter);
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        // Log.i("ok","你好");
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean, this, titles);
        sUtils.setIntValue(Constants.RESUME_ID, Integer.parseInt(resumeBean.getResume_info().getTitle_info().get(0).getResume_id()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtils = new SharedPreferencesUtils(this);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.phoneLogin);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setFocusChangeAndEditViewTextChange();
    }

    private void setFocusChangeAndEditViewTextChange() {
        etPhoneLoginPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivPhoneLoginPswDelete.setVisibility(View.VISIBLE);
                }else{
                    ivPhoneLoginPswDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPhoneLoginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivPhoneLoginPhoneDelete.setVisibility(View.VISIBLE);
                }else{
                    ivPhoneLoginPhoneDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPhoneLoginNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivPhoneLoginPhoneDelete.setVisibility(View.GONE);
                }else{
                    if(etPhoneLoginNumber.getText().toString()!=null&&!"".equals(etPhoneLoginNumber.getText().toString())){
                        ivPhoneLoginPhoneDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etPhoneLoginPsw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivPhoneLoginPswDelete.setVisibility(View.GONE);
                }else{
                    if(etPhoneLoginPsw.getText().toString()!=null&&!"".equals(etPhoneLoginPsw.getText().toString())){
                        ivPhoneLoginPswDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @OnClick({R.id.rl_phoneLoginHiddenPsw,R.id.iv_phoneLoginPhoneDelete,R.id.iv_phoneLoginPswDelete, R.id.btn_phoneLoginOK, R.id.tv_phoneLoginAccountLogin, R.id.tv_phoneLoginFindPsw, R.id.iv_phoneLoginQQ, R.id.iv_phoneLoginWeChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_phoneLoginHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivPhoneLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etPhoneLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivPhoneLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etPhoneLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etPhoneLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etPhoneLoginPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_phoneLoginOK:
                doLogin();
                //MainActivity.startAction(this,0);
                break;
            case R.id.iv_phoneLoginPhoneDelete:
                etPhoneLoginNumber.setText("");
                break;
            case R.id.iv_phoneLoginPswDelete:
                etPhoneLoginPsw.setText("");
                break;
            case R.id.tv_phoneLoginAccountLogin:
                UserLoginActivity.startAction(this);
                break;
            case R.id.tv_phoneLoginFindPsw:
                FindPhonePasswordActivity.startAction(this);
                break;
            case R.id.iv_phoneLoginQQ:
                ThirdPartLoginUtils.LoginQQ(mPresenter);
                break;
            case R.id.iv_phoneLoginWeChat:
                ThirdPartLoginUtils.LoginWeChat(mPresenter);
                break;
        }
    }

    /**
     * 登录
     */
    private void doLogin() {
        phoneNum = etPhoneLoginNumber.getText().toString();
        psw = etPhoneLoginPsw.getText().toString();
        if ("".equals(phoneNum) || phoneNum == null) {
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if (RegularExpression.isCellphone(phoneNum) == false) {
            ToastUitl.showShort("请输入正确的手机号码");
            return;
        }
        if ("".equals(psw) || psw == null) {
            ToastUitl.showShort("请输入密码");
            return;
        }
      /*  if (psw.length() < 6 || psw.length() > 25) {
            ToastUitl.showShort("请输入6-25位长度密码");
            return;
        }*/
        mPresenter.getLogin(phoneNum, psw, 1);
    }
}
