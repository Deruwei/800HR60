package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.hr.ui.BuildConfig;
import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.NoticeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.activity.CompanyRegisterActivity;
import com.hr.ui.ui.login.activity.PhoneLoginActivity;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.hr.ui.ui.me.contract.SettingContract;
import com.hr.ui.ui.me.model.SettingModel;
import com.hr.ui.ui.me.presenter.SettingPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/16.
 */

public class SettingActivity extends BaseActivity<SettingPresenter, SettingModel> implements SettingContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_hideResume)
    Switch ivHideResume;
    @BindView(R.id.cv_settings)
    CardView cvSettings;
    @BindView(R.id.iv_hideMessageNotification)
    Switch ivHideMessageNotification;
    @BindView(R.id.rl_settingMessageNotification)
    RelativeLayout rlSettingMessageNotification;
    @BindView(R.id.rl_settingHideResume)
    RelativeLayout rlSettingHideResume;
    @BindView(R.id.rl_settingShieldCompany)
    RelativeLayout rlSettingShieldCompany;
    @BindView(R.id.rl_settingResetPsw)
    RelativeLayout rlSettingResetPsw;
    @BindView(R.id.rl_settingChangeTel)
    RelativeLayout rlSettingChangeTel;
    @BindView(R.id.rl_settingEvaluateMine)
    RelativeLayout rlSettingEvaluateMine;
    @BindView(R.id.tv_settingsVersionDes)
    TextView tvSettingsVersionDes;
    @BindView(R.id.rl_settingVersionDes)
    RelativeLayout rlSettingVersionDes;
    @BindView(R.id.rl_settingCompanyDes)
    RelativeLayout rlSettingCompanyDes;
    @BindView(R.id.rl_settingChangeAccount)
    RelativeLayout rlSettingChangeAccount;
    @BindView(R.id.cv_settingChangeAccount)
    CardView cvSettingChangeAccount;
    private boolean isCheck;
    private String isOpen;
    private SharedPreferencesUtils sUtils;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setttings;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtils = new SharedPreferencesUtils(this);
        isOpen = sUtils.getStringValue(Constants.RESUME_OPENTYPE, "");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.setting);
        if ("2".equals(isOpen)) {
            ivHideResume.setChecked(true);
        } else if ("0".equals(isOpen)) {
            ivHideResume.setChecked(false);
        }
        tvSettingsVersionDes.setText(getString(R.string.versionUpdate)+ "（"+BuildConfig.VERSION_NAME+"）");
        // Log.i("现在的数据",isOpen);
        //mPresenter.getNotice(PushAliasString.getDeviceId(this));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivHideResume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPresenter.setHide("2");
                    isOpen = "2";
                } else {
                    isCheck = false;
                    mPresenter.setHide("0");
                    isOpen = "0";
                }
              /*  NoticeData noticeData=new NoticeData();
                noticeData.setBaidu_channel_id(null);
                noticeData.setBaidu_user_id(null);
                noticeData.setNotice_bgntime("9:00");
                noticeData.setNotice_endtime("21:00");
                noticeData.setSound_state("1");
                noticeData.setPhonecode(PushAliasString.getDeviceId(SettingActivity.this));
                noticeData.setPush_way("2");
                if(isChecked){
                    noticeData.setRushjob_state("1");
                    noticeData.setInvite_state("1");
                    if(isCheck==true) {
                        mPresenter.setNotice(noticeData);
                    }
                }else{
                    noticeData.setRushjob_state("0");
                    noticeData.setInvite_state("0");
                    mPresenter.setNotice(noticeData);
                }*/
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_settingMessageNotification, R.id.rl_settingCompanyDes, R.id.rl_settingShieldCompany, R.id.rl_settingResetPsw, R.id.rl_settingChangeTel, R.id.rl_settingEvaluateMine, R.id.rl_settingVersionDes, R.id.rl_settingChangeAccount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_settingMessageNotification:
                break;
            case R.id.rl_settingShieldCompany:
                ShieldCompanyActivity.startAction(this);
                break;
            case R.id.rl_settingResetPsw:
                ChangePswActivity.startAction(this);
                break;
            case R.id.rl_settingChangeTel:

                ChangePhoneActivity.startAction(this);
                break;
            case R.id.rl_settingEvaluateMine:
                doElaluateMine();
                break;
            case R.id.rl_settingVersionDes:
                VersionActivity.startAction(this);
                break;
            case R.id.rl_settingChangeAccount:
                mPresenter.getLoginOut();
                break;
            case R.id.rl_settingCompanyDes:
                CompanyRegisterActivity.startAction(this);
        }
    }

    private void doElaluateMine() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            ToastUitl.showShort("您的手机没有安装Android应用市场");
            e.printStackTrace();
        }

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
    public void getLoginOutSuccess() {
        ToastUitl.showShort("退出成功");
        SharedPreferencesUtils sUtils = new SharedPreferencesUtils(this);
        sUtils.setIntValue(Constants.ISAUTOLOGIN, 0);
        sUtils.setStringValue(Constants.PERSONIMAGE, "");
        AppManager.getAppManager().finishAllActivity();
        PhoneLoginActivity.startAction(this);

    }

    @Override
    public void setHideSuccess() {
        //Log.i("现在的数据",isOpen);
        sUtils.setStringValue(Constants.RESUME_OPENTYPE, isOpen);
        if (ivHideResume.isChecked()) {
            ToastUitl.showShort("简历已保密");
            MobclickAgent.onEvent(this, "v6_hideResume");
        } else {
            ToastUitl.showShort("简历已公开");
            MobclickAgent.onEvent(this, "v6_openResume");
        }
    }

    @Override
    public void getImsSuccess(NoticeBean.NoticeInfoBean noticeInfoBean) {
        if ("1".equals(noticeInfoBean.getInvite_state()) || "1".equals(noticeInfoBean.getRushjob_state())) {
            ivHideResume.setChecked(true);
        } else {
            isCheck = true;
            ivHideResume.setChecked(false);
        }
    }

    @Override
    public void setNoticeSuccess() {
        isCheck = true;
        if (ivHideResume.isChecked()) {
            ToastUitl.showShort("设置成功");
        } else {
            ToastUitl.showShort("取消成功");
        }
    }

}
