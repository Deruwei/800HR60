package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuPresenter;
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
import com.hr.ui.bean.ThirdPartBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.DBThirdPartService;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.ui.login.model.LoginModel;
import com.hr.ui.ui.login.presenter.LoginPresenter;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ThirdPartLoginUtils;
import com.hr.ui.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/8.
 */

public class BindPhoneLoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_bindPhoneLoginNumber)
    EditText etBindPhoneLoginNumber;
    @BindView(R.id.et_bindPhoneLoginPsw)
    EditText etBindPhoneLoginPsw;
    @BindView(R.id.iv_bindPhoneLoginHiddenPsw)
    ImageView ivBindPhoneLoginHiddenPsw;
    private boolean isHidden = true;
    private ThirdPartBean thirdPartBean;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, BindPhoneLoginActivity.class);
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
        thirdPartBean=new ThirdPartBean();
        List<ThirdPartBean> thirdPartBeanList=new DBThirdPartService().queryDataByType();
        for(int i=0;i<thirdPartBeanList.size();i++){
            if(thirdPartBeanList.get(i).getType().equals(Constants.TYPE_THIRDPARTLOGIN)){
                thirdPartBean=thirdPartBeanList.get(i);
                thirdPartBean.setSUId(userId+"");
                break;
            }
        }
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
        return R.layout.activity_bindphone;
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
        tvToolbarTitle.setText(R.string.bindPhoneAccount);
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

    @OnClick({R.id.rl_bindPhoneLoginHiddenPsw, R.id.btn_bindPhoneLoginOK, R.id.tv_bindPhoneToFindUser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bindPhoneLoginHiddenPsw:
                if (isHidden) {
                    //设置EditText文本为可见的
                    ivBindPhoneLoginHiddenPsw.setImageResource(R.mipmap.see);
                    etBindPhoneLoginPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    ivBindPhoneLoginHiddenPsw.setImageResource(R.mipmap.hidden);
                    etBindPhoneLoginPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etBindPhoneLoginPsw.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etBindPhoneLoginPsw.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.btn_bindPhoneLoginOK:
                doLogin();
                break;
            case R.id.tv_bindPhoneToFindUser:
                bindUserLoginActivity.startAction(this);
                break;
        }
    }
    /**
     * 登录
     */
    private void doLogin() {
        String phoneNum = etBindPhoneLoginNumber.getText().toString();
        String psw = etBindPhoneLoginPsw.getText().toString();
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
        if (psw.length() < 6 || psw.length() > 16) {
            ToastUitl.showShort("请输入6-16位长度密码");
            return;
        }
        mPresenter.getLogin(phoneNum, psw, 1);
    }
}
