package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.ui.login.contract.FindUserPasswordContract;
import com.hr.ui.ui.login.model.FindUserPasswordModel;
import com.hr.ui.ui.login.presenter.FindUserPasswordPresenter;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/7.
 */

public class FindUserPasswordActivity extends BaseActivity<FindUserPasswordPresenter, FindUserPasswordModel> implements FindUserPasswordContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.et_findUserPswEmailNumber)
    EditText etFindUserPswEmailNumber;
    @BindView(R.id.et_findUserPswUserName)
    EditText etFindUserPswUserName;
    private MyDialog myDialog;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, FindUserPasswordActivity.class);
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
    public void resetUserPswSuccess() {
        initDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_finduserpsw;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }
    private  void initDialog(){
        myDialog=new MyDialog(this,1);
        myDialog.setTitle(getString(R.string.submitSuccess));
        myDialog.setMessage(getString(R.string.submitSuccessMsg));
        myDialog.setNoGone();
        myDialog.setYesOnclickListener(getString(R.string.alright), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.findAccountPsw);
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

    @OnClick({R.id.btn_findUserPswOK, R.id.tv_findUserPswToUserPhoneFindPsw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_findUserPswOK:
                doFindUserPsw();
                break;
            case R.id.tv_findUserPswToUserPhoneFindPsw:
                FindPhonePasswordActivity.startAction(this);
                break;
        }
    }

    private void doFindUserPsw() {
        String email=etFindUserPswEmailNumber.getText().toString();
        String userName=etFindUserPswUserName.getText().toString();
        if("".equals(email)||email==null){
            ToastUitl.showShort("请输入邮箱");
            return;
        }
        if(RegularExpression.isEmail(email)==false){
            ToastUitl.showShort("请输入正确的邮箱");
            return;
        }
        if("".equals(userName)||userName==null){
            ToastUitl.showShort("请输入用户名");
            return;
        }
        mPresenter.resetUserPsw(email,userName);
    }
}
