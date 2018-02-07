package com.hr.ui.ui.main.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.ui.login.activity.CompanyRegisterActivity;
import com.hr.ui.ui.login.activity.LoginActivity;
import com.hr.ui.ui.login.activity.RegisterActivity;
import com.hr.ui.ui.main.contract.SplashContract;
import com.hr.ui.ui.main.modle.SplashModel;
import com.hr.ui.ui.main.presenter.SplashPresenter;
import com.hr.ui.utils.AnimationUtil;
import com.hr.ui.utils.LongRunningService;
import com.hr.ui.utils.NetWorkUtilsDNs;
import com.hr.ui.utils.ToastUitl;
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
    public int requestCode = 1000;
    @BindView(R.id.iv_welComeBack)
    ImageView ivWelComeBack;
    @BindView(R.id.tv_loginText)
    TextView tvLoginText;
    @BindView(R.id.tv_registerText)
    TextView tvRegisterText;
    @BindView(R.id.rl_loginOrRegister)
    LinearLayout rlLoginOrRegister;
    @BindView(R.id.rl_companySplash)
    RelativeLayout rlCompanySplash;
    private SharedPreferencesUtils sUtils;
    private int isAutoLogin, autoLoginType;
    private LoginBean loginBean;
    String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
    List<String> mPermissionList = new ArrayList<>();
    private int[] imageIds = {R.mipmap.resume1, R.mipmap.resume2, R.mipmap.resume3, R.mipmap.resume4, R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId;
    private int type;

    public static void startAction(Activity activity, int type) {
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        type = getIntent().getIntExtra("type", 0);
        sUtils = new SharedPreferencesUtils(this);
        /*sUtils.setIntValue("code",0);*/
        isAutoLogin = sUtils.getIntValue(Constants.ISAUTOLOGIN, 0);
        autoLoginType = sUtils.getIntValue(Constants.AUTOLOGINTYPE, 5);
        if (sUtils.getBooleanValue(Constants.IS_GUIDE, false) == false) {
            WelcomeActivity.startAction(SplashActivity.this, requestCode);
        }else{
            mPresenter.getConnect(this);
        }

       /* int screenWidth = getWindowManager().getDefaultDisplay().getWidth();//真实分辨率 宽
         int screenHeight = getWindowManager().getDefaultDisplay().getHeight();//真实分辨率 高*/
           /* setViewVisible();*/
        /* DisplayMetrics dm = new DisplayMetrics();
         dm = getResources().getDisplayMetrics();
         int densityDPI = dm.densityDpi;     // 屏幕密度（每寸像素：120(ldpi)/160(mdpi)/213(tvdpi)/240(hdpi)/320(xhdpi)）
        Toast.makeText(this, "真实分辨率："+screenWidth+"*"+screenHeight+"  每英寸:"+densityDPI, Toast.LENGTH_LONG).show();*/
    }

    private void getPermission() {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
           /* setViewVisible();*/
            //delayEntryPage();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
        }
    }

    boolean mShowRequestPermission = true;//用户是否禁止权限

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissions[i]);
                        if (showRequestPermission) {//
                            getPermission();
                            return;
                        } else {
                            mShowRequestPermission = false;//已经禁止
                        }
                    }
                }
                //  delayEntryPage();
                break;
            default:
                break;
        }
    }

    private void setViewVisible() {
        rlCompanySplash.setVisibility(View.VISIBLE);
        //添加动画属性
        if (sUtils.getBooleanValue(Constants.IS_GUIDE, false) == false) {
          /*  WelcomeActivity.startAction(SplashActivity.this, requestCode);*/
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
            timer.schedule(task, 800);
            rlRegister.post(new Runnable() {
                @Override
                public void run() {
                    AnimationUtil.showAndHiddenAnimation(rlLogin, AnimationUtil.AnimationState.STATE_SHOW, 800);
                    getPermission();
                }
            });
        }
    }

    @Override
    public void SendConnectSuccess() {
        Intent intent = new Intent(HRApplication.getAppContext(), LongRunningService.class);
        startService(intent);
        if (autoLoginType != 5) {
            loginBean = LoginDBUtils.queryDataById(autoLoginType + "");
            // System.out.println("auto"+loginBean.toString());
            if (isAutoLogin == 0) {
                setViewVisible();
            } else {
                if (autoLoginType == 0) {
                    mPresenter.getAutoPhoneLogin(loginBean.getName(), loginBean.getPassword(), 1);
                } else if (autoLoginType == 1) {
                    mPresenter.getAutoPhoneLogin(loginBean.getName(), loginBean.getPassword(), 2);
                } else {
                    mPresenter.getThirdBindingLogin(loginBean);
                }
            }
        }else{
            setViewVisible();
        }
    }

    @Override
    public void phoneLoginSuccess(int userId) {
        this.userId = userId;
        //Log.i("现在的时候","好吧");
        mPresenter.getResumeList();
    }

    @Override
    public void thirdBindingLoginSuccess(int userId) {
        this.userId = userId;
        mPresenter.getResumeList();
    }

    @Override
    public void getResumeListSuccess(MultipleResumeBean multipleResumeBean) {
        // Log.i("简历详情",multipleResumeBean.toString());
        ToolUtils.getInstance().judgeResumeMultipleOrOne3(this, multipleResumeBean, userId, imageIds, mPresenter);
    }

    @Override
    public void getResumeDataSuccess(ResumeBean resumeBean) {
        ToolUtils.getInstance().judgeResumeIsComplete(resumeBean, this, titles);
    }

    @Override
    public void onConnectError() {
        setViewVisible();
    }

    @OnClick({R.id.rl_login, R.id.rl_register,R.id.rl_companySplash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_login:
                LoginActivity.startAction(this);
                break;
            case R.id.rl_companySplash:
                CompanyRegisterActivity.startAction(this);
                break;
            case R.id.rl_register:
                RegisterActivity.startAction(this);
                break;
        }
    }

    //读取返回信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                if (resultCode == WelcomeActivity.ResultCode) {
                    mPresenter.getConnect(this);
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
