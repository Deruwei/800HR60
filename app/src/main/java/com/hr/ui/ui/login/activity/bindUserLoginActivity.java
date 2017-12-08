package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.ui.login.model.LoginModel;
import com.hr.ui.ui.login.presenter.LoginPresenter;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.utils.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/8.
 */

public class bindUserLoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_bindUserLoginNumber)
    EditText etBindUserLoginNumber;
    @BindView(R.id.et_bindUserLoginPsw)
    EditText etBindUserLoginPsw;
    @BindView(R.id.iv_bindUserLoginHiddenPsw)
    ImageView ivBindUserLoginHiddenPsw;
    private boolean isHidden = true;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, bindUserLoginActivity.class);
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
        MainActivity.startAction(this, userId);
        finish();
    }

    @Override
    public void thirdPartLoginSuccess() {

    }

    @Override
    public void thirdPartLoginGoToBind() {

    }

    @Override
    public void bindingSuccess() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_binduser;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.bindUserAccount);
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
    }

    @OnClick({R.id.rl_bindUserLoginHiddenPsw, R.id.btn_bindUserLoginOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bindUserLoginHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivBindUserLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etBindUserLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivBindUserLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etBindUserLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
               etBindUserLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etBindUserLoginPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_bindUserLoginOK:
                doLogin();
                break;
        }
    }
    private void doLogin() {
        String userNum = etBindUserLoginNumber.getText().toString();
        String psw = etBindUserLoginPsw.getText().toString();
        if ("".equals(userNum) || userNum == null) {
            ToastUitl.showShort("请输入手机号码");
            return;
        }
        if ("".equals(psw) || psw == null) {
            ToastUitl.showShort("请输入密码");
            return;
        }
        if (psw.length() < 6 || psw.length() > 16) {
            ToastUitl.showShort("请输入6-16位长度密码");
            return;
        }
        mPresenter.getLogin(userNum, psw, 2);
    }
}
