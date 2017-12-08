package com.hr.ui.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.activity.LoginActivity;
import com.hr.ui.ui.login.activity.RegisterActivity;
import com.hr.ui.ui.main.contract.SplashContract;
import com.hr.ui.ui.main.modle.SplashModel;
import com.hr.ui.ui.main.presenter.SplashPresenter;
import com.hr.ui.utils.AnimationUtil;
import com.hr.ui.utils.LoadingTip;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/11/21.
 */

public class SplashActivity extends BaseActivity<SplashPresenter, SplashModel> implements SplashContract.View {
    @BindView(R.id.lt_splash)
    LoadingTip ltSplash;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.rl_register)
    RelativeLayout rlRegister;
    public int requestCode=1000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.getConnect(this);
        setViewVisible();
    }

    private void setViewVisible() {
        final SharedPreferencesUtils sUtils = new SharedPreferencesUtils(this);
        //添加动画属性
        if (sUtils.getBooleanValue(Constants.IS_GUIDE, false) == false) {
            WelcomeActivity.startAction(SplashActivity.this,requestCode);
        } else {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    /**
                     * 第一次跳转到导航页
                     */

                          /*  MainActivity.startAction(SplashActivity.this);*/
                    rlLogin.post(new Runnable() {
                        @Override
                        public void run() {
                            AnimationUtil.showAndHiddenAnimation(rlRegister, AnimationUtil.AnimationState.STATE_SHOW, 2000);
                        }
                    });

                }

            };
            timer.schedule(task, 1000);
            rlRegister.post(new Runnable() {
                @Override
                public void run() {
                    AnimationUtil.showAndHiddenAnimation(rlLogin, AnimationUtil.AnimationState.STATE_SHOW, 1000);
                }
            });
        }
    }

    @Override
    public void showLoading(String title) {
        ltSplash.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        ltSplash.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        ltSplash.setLoadingTip(LoadingTip.LoadStatus.error);
    }

    @Override
    public void SendConnectSuccess() {

    }

    @OnClick({R.id.rl_login, R.id.rl_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_login:
                LoginActivity.startAction(this);
                break;
            case R.id.rl_register:
                RegisterActivity.startAction(this);
                break;
        }
    }
    //读取返回信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case  1000:
                if (resultCode==WelcomeActivity.ResultCode){
                    setViewVisible();
                }
                break;
        }
    }
}
