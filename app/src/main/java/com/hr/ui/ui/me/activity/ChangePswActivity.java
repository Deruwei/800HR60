package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.ui.me.contract.ChangePswContract;
import com.hr.ui.ui.me.model.ChangePswModel;
import com.hr.ui.ui.me.presenter.ChangePswPresenter;
import com.hr.ui.utils.ToastUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/18.
 */

public class ChangePswActivity extends BaseActivity<ChangePswPresenter, ChangePswModel> implements ChangePswContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_changePswNewOldIcon)
    ImageView ivChangePswNewOldIcon;
    @BindView(R.id.et_changePswNewOld)
    EditText etChangePswNewOld;
    @BindView(R.id.iv_changePswNewIcon)
    ImageView ivChangePswNewIcon;
    @BindView(R.id.et_changePswNew)
    EditText etChangePswNew;
    @BindView(R.id.view_getValidCode2)
    View viewGetValidCode2;
    @BindView(R.id.btn_changePswOK)
    Button btnChangePswOK;
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ChangePswActivity.class);
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
    public void getChangePswSuccess() {
        ToastUitl.showShort("修改密码成功");
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_changepsw;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.resetPsw);
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

    @OnClick(R.id.btn_changePswOK)
    public void onViewClicked() {
        if(etChangePswNewOld.getText().toString()==null||"".equals(etChangePswNewOld.getText().toString())){
            ToastUitl.showShort("请填写旧密码");
            return;
        }
        if(etChangePswNew.getText().toString()==null||"".equals(etChangePswNew.getText().toString())){
            ToastUitl.showShort("请填写新密码");
            return;
        }
        if(etChangePswNewOld.getText().toString().equals(etChangePswNew.getText().toString())){
            ToastUitl.showShort("新旧密码不能相同");
            return;
        }
        mPresenter.getChangePsw(etChangePswNewOld.getText().toString(),etChangePswNew.getText().toString());
    }
}
