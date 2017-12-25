package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ResumeData;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.db.ResumeDataUtils;
import com.hr.ui.ui.login.activity.LoginActivity;
import com.hr.ui.ui.login.activity.RegisterActivity;
import com.hr.ui.ui.main.contract.SplashContract;
import com.hr.ui.ui.main.modle.SplashModel;
import com.hr.ui.ui.main.presenter.SplashPresenter;
import com.hr.ui.utils.AnimationUtil;
import com.hr.ui.utils.LoadingTip;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/11/21.
 */

public class SplashActivity extends BaseActivity<SplashPresenter, SplashModel> implements SplashContract.View {
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.rl_register)
    RelativeLayout rlRegister;
    public int requestCode=1000;
    private SharedPreferencesUtils sUtils;
    private int isAutoLogin,autoLoginType;
    private LoginBean loginBean;
    private int[] imageIds={R.mipmap.resume1,R.mipmap.resume2,R.mipmap.resume3,R.mipmap.resume4,R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId;
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
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
        sUtils=new SharedPreferencesUtils(this);
        isAutoLogin=sUtils.getIntValue(Constants.ISAUTOLOGIN,0);
        autoLoginType=sUtils.getIntValue(Constants.AUTOLOGINTYPE,5);
        mPresenter.getConnect(this);
        setViewVisible();
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();//真实分辨率 宽
         int screenHeight = getWindowManager().getDefaultDisplay().getHeight();//真实分辨率 高

        /* DisplayMetrics dm = new DisplayMetrics();
         dm = getResources().getDisplayMetrics();
         int densityDPI = dm.densityDpi;     // 屏幕密度（每寸像素：120(ldpi)/160(mdpi)/213(tvdpi)/240(hdpi)/320(xhdpi)）
        Toast.makeText(this, "真实分辨率："+screenWidth+"*"+screenHeight+"  每英寸:"+densityDPI, Toast.LENGTH_LONG).show();*/
    }

    private void setViewVisible() {
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
    public void SendConnectSuccess() {

        if(autoLoginType!=5) {
            loginBean = LoginDBUtils.queryDataById(autoLoginType + "");
            System.out.println("auto"+loginBean.toString());
            if (isAutoLogin == 0) {

            } else {
                if (autoLoginType == 0) {
                    mPresenter.getAutoPhoneLogin(loginBean.getName(), loginBean.getPassword(), 1);
                } else if (autoLoginType == 1) {
                    mPresenter.getAutoPhoneLogin(loginBean.getName(), loginBean.getPassword(), 2);
                } else {
                    mPresenter.getThirdBindingLogin(loginBean);
                }
            }
        }
    }

    @Override
    public void phoneLoginSuccess(int userId) {
        this.userId=userId;
       mPresenter.getResumeList();
    }

    @Override
    public void thirdBindingLoginSuccess(int userId) {
        this.userId=userId;
        mPresenter.getResumeList();
    }

    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        ToolUtils.getInstance().judgeResumeMultipleOrOne3(this, multipleResumeBean,userId,imageIds,mPresenter);
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean,this,titles);
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

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
