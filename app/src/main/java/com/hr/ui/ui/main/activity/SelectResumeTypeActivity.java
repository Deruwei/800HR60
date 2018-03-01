package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.ResumeTypeContract;
import com.hr.ui.ui.main.modle.ResumeTypeModel;
import com.hr.ui.ui.main.presenter.ResumeTypePresenter;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/18.
 */

public class SelectResumeTypeActivity extends BaseActivity<ResumeTypePresenter, ResumeTypeModel> implements ResumeTypeContract.View {
    private SharedPreferencesUtils sUtils;
    private String type;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SelectResumeTypeActivity.class);
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
    public void sendResumeId(int resumeId) {
        sUtils.setIntValue(Constants.RESUME_ID, resumeId);
      // mPresenter.setDefaultResume(resumeId+"","1");
        PersonalInformationActivity.startAction(this);
    }

    @Override
    public void setDefaultResumeSuccess() {
        PersonalInformationActivity.startAction(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_selectresumetype;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtils = new SharedPreferencesUtils(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_selectStudent, R.id.rl_selectHasJob})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_selectStudent:
                type="2";
                sUtils.setStringValue(Constants.RESUME_TYPE,type);
                PersonalInformationActivity.startAction(this,type);
                break;
            case R.id.rl_selectHasJob:
                type="1";
                sUtils.setStringValue(Constants.RESUME_TYPE,type);
                PersonalInformationActivity.startAction(this,type);
                break;
        }
    }

    private void doSetResumeType() {
        mPresenter.setResumeType(type);
    }
}
