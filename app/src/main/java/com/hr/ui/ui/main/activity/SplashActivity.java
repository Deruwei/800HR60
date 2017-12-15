package com.hr.ui.ui.main.activity;

import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hr.ui.R;
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
    @BindView(R.id.lt_splash)
    LoadingTip ltSplash;
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
        if(multipleResumeBean.getResume_list()==null||"".equals(multipleResumeBean.getResume_list())||multipleResumeBean.getResume_list().size()==0){
            RobotActivity.startAction(this,userId);
        }else{
            if(multipleResumeBean.getResume_list().size()==1){
                mPresenter.getResumeData(multipleResumeBean.getResume_list().get(0).getResume_id());
            }else{
                ResumeDataUtils.deleteAll();
                List<MultipleResumeBean.ResumeListBean> resumeListBeanList=multipleResumeBean.getResume_list();
                Log.i("niham,s,s,",resumeListBeanList.toString());
                for(int j=0;j<resumeListBeanList.size();j++){
                    ResumeData resumeData=new ResumeData();
                    resumeData.setResumeId(resumeListBeanList.get(j).getResume_id());
                    resumeData.setTitle(resumeListBeanList.get(j).getTitle());
                    resumeData.setComplete(resumeListBeanList.get(j).getFill_scale());
                    resumeData.setImageId(imageIds[j]);
                    ResumeDataUtils.insertResumeData(resumeData);
                }
                MultipleResumeActivity.startAction(this,userId);
            }
        }
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        titles=new ArrayList<>();
        if(resumeBean.getResume_info().getBase_info()==null||"".equals(resumeBean.getResume_info().getBase_info())){
            titles.add("基本信息");
        }
        if(resumeBean.getResume_info().getOrder_info()==null||"".equals(resumeBean.getResume_info().getOrder_info())){
            titles.add("求职意向");
        }
        if(resumeBean.getResume_info().getEducation_list()==null||"".equals(resumeBean.getResume_info().getEducation_list())||resumeBean.getResume_info().getEducation_list().size()==0){
            titles.add("教育背景");
        }
        if(resumeBean.getResume_info().getExperience_list()==null||"".equals(resumeBean.getResume_info().getEducation_list())||resumeBean.getResume_info().getExperience_list().size()==0){
            titles.add("工作经验");
        }
        if(titles!=null&&!"".equals(titles)&&titles.size()!=0) {
            RobotActivity.startAction(this, titles);
        }else{
            MainActivity.startAction(this,Integer.parseInt(resumeBean.getResume_info().getAssess_info().getUser_id()));
        }
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
