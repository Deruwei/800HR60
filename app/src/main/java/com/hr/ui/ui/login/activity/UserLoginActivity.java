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
import com.hr.ui.utils.ThirdPartLoginUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.nio.channels.NonReadableChannelException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/6.
 */

public class UserLoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_userLoginNumber)
    EditText etUserLoginNumber;
    @BindView(R.id.et_userLoginPsw)
    EditText etUserLoginPsw;
    @BindView(R.id.iv_userLoginHiddenPsw)
    ImageView ivUserLoginHiddenPsw;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.iv_userLoginNumberIcon)
    ImageView ivUserLoginNumberIcon;
    @BindView(R.id.iv_userLoginNumberDelete)
    ImageView ivUserLoginNumberDelete;
    @BindView(R.id.iv_userLoginPswIcon)
    ImageView ivUserLoginPswIcon;
    @BindView(R.id.iv_userLoginPswDelete)
    ImageView ivUserLoginPswDelete;
    @BindView(R.id.rl_userLoginHiddenPsw)
    RelativeLayout rlUserLoginHiddenPsw;
    @BindView(R.id.btn_userLoginOK)
    Button btnUserLoginOK;
    @BindView(R.id.tv_userLoginFindPsw)
    TextView tvUserLoginFindPsw;
    @BindView(R.id.ll_userLogin_middle)
    LinearLayout llUserLoginMiddle;
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.iv_userLoginQQ)
    ImageView ivUserLoginQQ;
    @BindView(R.id.iv_userLoginWeChat)
    ImageView ivUserLoginWeChat;
    @BindView(R.id.rl_userLoginThirdPart)
    RelativeLayout rlUserLoginThirdPart;
    private boolean isHidden = true;
    private String userNum, psw;
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
        Intent intent = new Intent(activity, UserLoginActivity.class);
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
        MobclickAgent.onEvent(this,"v6_login_user");
        MobclickAgent.onProfileSignIn(userId+"");
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        sUtils.setIntValue(Constants.AUTOLOGINTYPE, 1);
        this.userId = userId;
        mPresenter.getResumeList();
    }

    @Override
    public void thirdPartLoginSuccess(int userId) {
        MobclickAgent.onProfileSignIn("WB",userId+"");
        MobclickAgent.onEvent(this,"v6_login_thirdPart");
        this.userId = userId;
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 1);
        if ("qq".equals(Constants.TYPE_THIRDPARTLOGIN)) {
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
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean, this, titles);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_userlogin;
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
        tvToolbarTitle.setText(R.string.accountLogin);
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
        onEditViewTextChangeAndFocusChange();
    }

    private void onEditViewTextChangeAndFocusChange() {
        etUserLoginPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivUserLoginPswDelete.setVisibility(View.VISIBLE);
                }else{
                    ivUserLoginPswDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etUserLoginNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivUserLoginNumberDelete.setVisibility(View.VISIBLE);
                }else{
                    ivUserLoginNumberDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etUserLoginNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivUserLoginNumberDelete.setVisibility(View.GONE);
                }else{
                    if(etUserLoginNumber.getText().toString()!=null&&!"".equals(etUserLoginNumber.getText().toString())){
                        ivUserLoginNumberDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etUserLoginPsw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivUserLoginPswDelete.setVisibility(View.GONE);
                }else{
                    if(etUserLoginPsw.getText().toString()!=null&&!"".equals(etUserLoginPsw.getText().toString())){
                        ivUserLoginPswDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @OnClick({R.id.rl_userLoginHiddenPsw,R.id.iv_userLoginNumberDelete,R.id.iv_userLoginPswDelete, R.id.btn_userLoginOK, R.id.tv_userLoginFindPsw, R.id.iv_userLoginQQ, R.id.iv_userLoginWeChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_userLoginNumberDelete:
                etUserLoginNumber.setText("");
                break;
            case R.id.iv_userLoginPswDelete:
                etUserLoginPsw.setText("");
                break;
            case R.id.rl_userLoginHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivUserLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etUserLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivUserLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etUserLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etUserLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etUserLoginPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_userLoginOK:
                doLogin();
                break;
            case R.id.tv_userLoginFindPsw:
                FindUserPasswordActivity.startAction(this);
                break;
            case R.id.iv_userLoginQQ:
                ThirdPartLoginUtils.LoginQQ(mPresenter);
                break;
            case R.id.iv_userLoginWeChat:
                ThirdPartLoginUtils.LoginWeChat(mPresenter);
                break;
        }
    }

    private void doLogin() {
        userNum = etUserLoginNumber.getText().toString();
        psw = etUserLoginPsw.getText().toString();
        if ("".equals(userNum) || userNum == null) {
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if ("".equals(psw) || psw == null) {
            ToastUitl.showShort("请输入密码");
            return;
        }
        mPresenter.getLogin(userNum, psw, 2);
    }
}
