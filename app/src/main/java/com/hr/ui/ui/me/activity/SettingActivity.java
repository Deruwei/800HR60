package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.NoticeBean;
import com.hr.ui.bean.NoticeData;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.activity.CompanyRegisterActivity;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.hr.ui.ui.me.contract.SettingContract;
import com.hr.ui.ui.me.model.SettingModel;
import com.hr.ui.ui.me.presenter.SettingPresenter;
import com.hr.ui.utils.PushAliasString;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/16.
 */

public class SettingActivity extends BaseActivity<SettingPresenter,SettingModel> implements SettingContract.View {
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
    private boolean isCheck;
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
        tvToolbarTitle.setText(R.string.setting);
        mPresenter.getNotice(PushAliasString.getDeviceId(this));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivHideResume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NoticeData noticeData=new NoticeData();
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
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_settingMessageNotification,R.id.rl_settingCompanyDes, R.id.rl_settingShieldCompany, R.id.rl_settingResetPsw, R.id.rl_settingChangeTel, R.id.rl_settingEvaluateMine, R.id.rl_settingVersionDes, R.id.rl_settingChangeAccount})
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
        try{
            Uri uri = Uri.parse("market://details?id="+getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(Exception e){
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
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(this);
        sUtils.setIntValue(Constants.ISAUTOLOGIN,0);
        AppManager.getAppManager().finishAllActivity();
        SplashActivity.startAction(this,1);

    }

    @Override
    public void getImsSuccess(NoticeBean.NoticeInfoBean noticeInfoBean) {
        if("1".equals(noticeInfoBean.getInvite_state())||"1".equals(noticeInfoBean.getRushjob_state())){
            ivHideResume.setChecked(true);
        }else{
            isCheck=true;
            ivHideResume.setChecked(false);
        }
    }

    @Override
    public void setNoticeSuccess() {
        isCheck=true;
        if(ivHideResume.isChecked()) {
            ToastUitl.showShort("设置成功");
        }else{
            ToastUitl.showShort("取消成功");
        }
    }

}
