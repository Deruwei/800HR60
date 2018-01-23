package com.hr.ui.ui.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.iv_findUserPswEmailIcon)
    ImageView ivFindUserPswEmailIcon;
    @BindView(R.id.iv_findUserPswEmailDelete)
    ImageView ivFindUserPswEmailDelete;
    @BindView(R.id.iv_findUserPswUserNameIcon)
    ImageView ivFindUserPswUserNameIcon;
    @BindView(R.id.iv_findUserPswNameDelete)
    ImageView ivFindUserPswNameDelete;
    @BindView(R.id.btn_findUserPswOK)
    Button btnFindUserPswOK;
    @BindView(R.id.tv_findUserPswToUserPhoneFindPsw)
    TextView tvFindUserPswToUserPhoneFindPsw;
    @BindView(R.id.ll_findUserPsw_middle)
    LinearLayout llFindUserPswMiddle;
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

    private void initDialog() {
        myDialog = new MyDialog(this, 1);
        myDialog.setTitle(getString(R.string.submitSuccess));
        myDialog.setMessage(getString(R.string.submitSuccessMsg));
        myDialog.setNoGone();
        myDialog.setYesOnclickListener(getString(R.string.sure), new MyDialog.onYesOnclickListener() {
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
        onEditViewTextChangeAndFocusChange();
    }

    private void onEditViewTextChangeAndFocusChange() {
        etFindUserPswUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivFindUserPswNameDelete.setVisibility(View.GONE);
                }else{
                    if(etFindUserPswUserName.getText().toString()!=null&&!"".equals(etFindUserPswUserName.getText().toString())){
                        ivFindUserPswNameDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etFindUserPswEmailNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivFindUserPswEmailDelete.setVisibility(View.GONE);
                }else{
                    if(etFindUserPswEmailNumber.getText().toString()!=null&&!"".equals(etFindUserPswEmailNumber.getText().toString())){
                        ivFindUserPswEmailDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etFindUserPswUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivFindUserPswNameDelete.setVisibility(View.VISIBLE);
                }else{
                    ivFindUserPswNameDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFindUserPswEmailNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivFindUserPswEmailDelete.setVisibility(View.VISIBLE);
                }else{
                    ivFindUserPswEmailDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.btn_findUserPswOK,R.id.iv_findUserPswEmailDelete,R.id.iv_findUserPswNameDelete, R.id.tv_findUserPswToUserPhoneFindPsw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_findUserPswEmailDelete:
               etFindUserPswEmailNumber.setText("");
                break;
            case R.id.iv_findUserPswNameDelete:
                etFindUserPswUserName.setText("");
                break;
            case R.id.btn_findUserPswOK:
                doFindUserPsw();
                break;
            case R.id.tv_findUserPswToUserPhoneFindPsw:
                FindPhonePasswordActivity.startAction(this);
                break;
        }
    }

    private void doFindUserPsw() {
        String email = etFindUserPswEmailNumber.getText().toString();
        String userName = etFindUserPswUserName.getText().toString();
        if ("".equals(email) || email == null) {
            ToastUitl.showShort("请输入邮箱");
            return;
        }
        if (RegularExpression.isEmail(email) == false) {
            ToastUitl.showShort("请输入正确的邮箱");
            return;
        }
        if ("".equals(userName) || userName == null) {
            ToastUitl.showShort("请输入用户名");
            return;
        }
        mPresenter.resetUserPsw(email, userName);
    }
}
