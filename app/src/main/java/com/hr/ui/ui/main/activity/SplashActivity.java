package com.hr.ui.ui.main.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.BuildConfig;
import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.base.Base3Activity;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.VersionBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.ui.login.activity.PhoneLoginActivity;
import com.hr.ui.ui.main.contract.SplashContract;
import com.hr.ui.ui.main.modle.SplashModel;
import com.hr.ui.ui.main.presenter.SplashPresenter;
import com.hr.ui.utils.BaiDuLocationUtils;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.PopWindowUpdate;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wdr on 2017/11/21.
 */

public class SplashActivity extends Base3Activity<SplashPresenter, SplashModel> implements SplashContract.View {
    public int REQUEST_CODE = 1000;
    @BindView(R.id.rl_loginOrRegister)
    LinearLayout rlLoginOrRegister;
    @BindView(R.id.cl_splash)
    ConstraintLayout clSplash;
    @BindView(R.id.iv_splashLogo)
    ImageView ivSplashLogo;
    @BindView(R.id.iv_splashLogoBottom)
    ImageView ivSplashLogoBottom;
    @BindView(R.id.rl_splash)
    FrameLayout rlSplash;
    private SharedPreferencesUtils sUtils;
    private int isAutoLogin, autoLoginType;
    public static SplashActivity instance;
    private LoginBean loginBean;
    private String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private List<String> mPermissionList = new ArrayList<>();
    private int[] imageIds = {R.mipmap.resume1, R.mipmap.resume2, R.mipmap.resume3, R.mipmap.resume4, R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId;
    private PopWindowUpdate popWindowUpdate;
    public boolean isAllreadyInstance = true,isCanReflesh=true, isConnect = true, isGotoSettings,isGetAllPermission;//1.是否到了版本更新界面  2.是否要再次连接服务器   3，网络是否连接成功  4.是否要去权限设置页
    private MyDialog myDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

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
        instance = this;
        sUtils = new SharedPreferencesUtils(this);
        isAutoLogin = sUtils.getIntValue(Constants.ISAUTOLOGIN, 0);
        autoLoginType = sUtils.getIntValue(Constants.AUTOLOGINTYPE, 5);
        if (sUtils.getBooleanValue(Constants.IS_GUIDE, false) == false) {
            MobclickAgent.onEvent(this, "v6_firstStartApp");
            sUtils.setBooleanValue(Constants.ISFIRSTINTO, true);
            WelcomeActivity.startAction(SplashActivity.this, REQUEST_CODE);
        } else {
            doAnimator();
        }
    }
    private  void doAnimator(){
        Animator castleAni = getCastleShowingAnimator();
        AnimatorSet ser = new AnimatorSet();
        ser.play(castleAni);
        ser.setDuration(2000);
        ser.start();
        ivSplashLogoBottom.setVisibility(View.VISIBLE);
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(ivSplashLogoBottom, "translationX", -700f, 0f);
        moveIn.setDuration(1000);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(ivSplashLogoBottom, "alpha", 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(moveIn).with(fadeInOut);
        animSet.setInterpolator(new AnticipateOvershootInterpolator());
        animSet.setDuration(2000);
        animSet.start();
    }
    private Animator getCastleShowingAnimator() {
        PropertyValuesHolder castleX1 = PropertyValuesHolder.ofFloat("y", -1.0f, 0f);

        PropertyValuesHolder castleScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1.0f);
        PropertyValuesHolder castleScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1.0f);

        ObjectAnimator castleAni = ObjectAnimator
                .ofPropertyValuesHolder(ivSplashLogo, castleX1, castleScaleX, castleScaleY);
        castleAni.addListener(new ViewShowListener(ivSplashLogo));
        castleAni.setInterpolator(new AccelerateDecelerateInterpolator());
        return castleAni;
    }

    class ViewShowListener implements Animator.AnimatorListener {
        private View view;

        public ViewShowListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animator animator) {
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animator) {
               // view.setVisibility(View.GONE);
            checkPermissionReadPhoneState();
          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    rlSplash.setVisibility(View.GONE);
                }
            },300);*/
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGotoSettings) {
            isGotoSettings = false;
            if (Utils.checkPermissionIsAllRequest(permissions, mPermissionList, this)) {
                getConnect();
            } else {
                showDialog();
            }
        }
        if (isAllreadyInstance == false) {
            //Log.i("现在的数据","here");
            isAllreadyInstance = true;
            doAutoLogin();
        }
    }

    private void getPermission() {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         判断是否为空
         **/
        if (!mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
        }
    }

    boolean mShowRequestPermission = true;//用户是否禁止权限

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isAllRequest = true;
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        isAllRequest = false;
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissions[i]);
                        if (showRequestPermission) {//
                            getPermission();
                            return;
                        } else {
                            mShowRequestPermission = false;//已经禁止
                            showDialog();
                        }
                    }
                }
                if (isAllRequest && grantResults.length != 0) {
                    //Log.i("到这里了","===============");
                   checkPermissionReadPhoneState();
                }
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        if (myDialog != null) {
            myDialog.show();
        } else {
            myDialog = new MyDialog(this, 2);
            myDialog.setTitle("提示信息");
            myDialog.setMessage("请进入设置-应用中获取手机号码、IMEI、IMSI-定位-照片、媒体内容和文件-拍摄照片和录制视频权限,以确保软件能够正常运行");
            myDialog.setYesOnclickListener("进入", new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    isGotoSettings = true;
                    myDialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    myDialog.dismiss();
                    AppManager.getAppManager().exitApp();
                }
            });
            myDialog.show();
        }
    }

    private void setViewVisible() {
        //添加动画属性
        if (sUtils.getBooleanValue(Constants.IS_GUIDE, false) == false) {
            /*  WelcomeActivity.startAction(SplashActivity.this, requestCode);*/
        } else {
            handler.sendEmptyMessageDelayed(1, 0);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(SplashActivity.this, PhoneLoginActivity.class);
                    ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            SplashActivity.this);
                    // ActivityCompat是android支持库中用来适应不同android版本的
                    ActivityCompat.startActivity(SplashActivity.this, intent, activityOptions.toBundle());
                    break;
                case 2:
                    if (autoLoginType == 0) {
                        mPresenter.getAutoPhoneLogin(loginBean.getName(), loginBean.getPassword(), 1, true);
                    } else if (autoLoginType == 1) {
                        mPresenter.getAutoPhoneLogin(loginBean.getName(), loginBean.getPassword(), 2, true);
                    } else {
                        mPresenter.getThirdBindingLogin(loginBean, true);
                    }
                    break;
            }
        }
    };

    @Override
    public void SendConnectSuccess() {
        // getPermission();
        isCanReflesh=false;
        mPresenter.getVersion(BuildConfig.VERSION_NAME);
        BaiDuLocationUtils.getInstance().initData();
    }

    @Override
    public void phoneLoginSuccess(int userId) {
        sUtils.setStringValue(Constants.USERID, userId + "");
        if (autoLoginType == 0) {
            MobclickAgent.onEvent(this, "v6_login_phone");
            MobclickAgent.onProfileSignIn(userId + "");
        } else {
            MobclickAgent.onEvent(this, "v6_login_user");
            MobclickAgent.onProfileSignIn(userId + "");
        }
        this.userId = userId;
        //Log.i("现在的时候","好吧");
        mPresenter.getResumeList();
    }

    @Override
    public void thirdBindingLoginSuccess(int userId) {
        this.userId = userId;
        sUtils.setStringValue(Constants.USERID, userId + "");
        MobclickAgent.onEvent(this, "v6_login_thirdPart");
        MobclickAgent.onProfileSignIn("WB", userId + "");
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
        rlSplash.setVisibility(View.GONE);
        isCanReflesh=true;
        setViewVisible();
    }

    @Override
    public void getVersion(VersionBean.AndroidBean androidBean) {
        String version = androidBean.getVer();
        String version1 = BuildConfig.VERSION_NAME;
        if (Utils.checkVersion(version, version1) == true) {
           popWindowUpdate = new PopWindowUpdate(this, androidBean, clSplash);
           popWindowUpdate.show();
        }
        if(popWindowUpdate==null||!popWindowUpdate.isShowing()){
            rlSplash.setVisibility(View.GONE);
            doAutoLogin();
        }
    }
    private void getConnect(){
        mPresenter.getConnect(this);
    }
    public void doAutoLogin() {
        if (autoLoginType != 5) {
            try {
                loginBean = LoginDBUtils.queryDataById(autoLoginType + "");
            } catch (Exception e) {
                // mPresenter.getConnect(this);
                setViewVisible();
            }
            if (isAutoLogin != 0) {
                if (loginBean != null && !"".equals(loginBean)) {
                    handler.sendEmptyMessageDelayed(2, 0);
                }else{
                    setViewVisible();
                }
            }else{
                setViewVisible();
            }
        }else{
            setViewVisible();
        }
    }

    //读取返回信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                if (resultCode == WelcomeActivity.ResultCode) {
                    doAnimator();
                }
                break;
        }
    }

    private void checkPermissionReadPhoneState() {
        //检查版本是否大于M
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {*/
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            /**
             * 判断是否为空
             */
            if (mPermissionList.isEmpty() ) {//未授予的权限为空，表示都授予了
                mPresenter.getConnect(this);
               // Log.i("到这里了","===============2");
            } else {//请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
            }
        /*}*/
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
    protected void onDestroy() {
       if(popWindowUpdate!=null){
           popWindowUpdate.dismiss();
       }
        if (instance != null) {
            instance = null;
        }
        if(myDialog!=null){
            myDialog.dismiss();
        }
        BaiDuLocationUtils.getInstance().stopLocation();
        super.onDestroy();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        // Log.i("type类型",type.name()+"------");
            if(!isConnect&&isCanReflesh==true){
                getConnect();
                isConnect=true;
            }
         /*   mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            *//**
             * 判断是否为空
             *//*
            if (mPermissionList.size() == 0) {//未授予的权限为空，表示都授予了
                if (isConnect == false&&isCanReflesh==true) {
                   // Log.i("带这里了","-------");
                    getConnect();
                }
            } else {//请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
            }*/
    }

    @Override
    protected void onNetworkDisConnected() {
        isConnect = false;
    }
}
