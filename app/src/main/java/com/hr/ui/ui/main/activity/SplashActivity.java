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
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
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
import com.hr.ui.view.ViewUtil;
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
    private SharedPreferencesUtils sUtils;
    private int isAutoLogin, autoLoginType;
    public static SplashActivity instance;
    private LoginBean loginBean;
    private String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private List<String> mPermissionList = new ArrayList<>();
    private int[] imageIds = {R.mipmap.resume1, R.mipmap.resume2, R.mipmap.resume3, R.mipmap.resume4, R.mipmap.resume5};
    private ArrayList<String> titles;
    private int userId;
    private int type;
    private PopupWindow popupWindow;
    public boolean isAllreadyInstance = true, isConnect = true, isGotoSettings;
    private MyDialog myDialog;

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
        /* MobclickAgent.onEvent(this,"v6_test");*/
        type = getIntent().getIntExtra("type", 0);
        sUtils = new SharedPreferencesUtils(this);
        /*sUtils.setIntValue("code",0);*/
        isAutoLogin = sUtils.getIntValue(Constants.ISAUTOLOGIN, 0);
        autoLoginType = sUtils.getIntValue(Constants.AUTOLOGINTYPE, 5);
       /* if(Utils.isWifiProxy(this)){
            ToastUitl.showShort(getString(R.string.proxy));
        }*/
        if (sUtils.getBooleanValue(Constants.IS_GUIDE, false) == false) {
            MobclickAgent.onEvent(this, "v6_firstStartApp");
            sUtils.setBooleanValue(Constants.ISFIRSTINTO, true);
            WelcomeActivity.startAction(SplashActivity.this, REQUEST_CODE);
        } else {
            if (type != 1) {
                checkPermissionReadPhoneState();
            } else {
                setViewVisible();
            }
        }
       // ivSplashLogo.setVisibility(View.VISIBLE);
      /*  AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ivSplashLogo, "rotation", 0, 360);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivSplashLogo, "scaleX", 0, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivSplashLogo, "scaleY", 0, 1f);
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY).with(objectAnimator1);   //两个动画同时开始
        animatorSet.start();*/

        Animator castleAni = getCastleShowingAnimator();
        AnimatorSet ser=new AnimatorSet();
        ser.play(castleAni);
        ser.setDuration(2000);
        ser.start();

        ivSplashLogoBottom.setVisibility(View.VISIBLE);
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(ivSplashLogoBottom, "translationX", -700f, 0f);
        moveIn.setDuration(1000);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(ivSplashLogoBottom, "alpha",  0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(moveIn).with(fadeInOut);
        animSet.setInterpolator(new AnticipateOvershootInterpolator());
        animSet.setDuration(2000);
        animSet.start();

       /* int screenWidth = getWindowManager().getDefaultDisplay().getWidth();//真实分辨率 宽
         int screenHeight = getWindowManager().getDefaultDisplay().getHeight();//真实分辨率 高*/
        /* setViewVisible();*/
        /* DisplayMetrics dm = new DisplayMetrics();
         dm = getResources().getDisplayMetrics();
         int densityDPI = dm.densityDpi;     // 屏幕密度（每寸像素：120(ldpi)/160(mdpi)/213(tvdpi)/240(hdpi)/320(xhdpi)）
        Toast.makeText(this, "真实分辨率："+screenWidth+"*"+screenHeight+"  每英寸:"+densityDPI, Toast.LENGTH_LONG).show();*/
    }
    private Animator getCastleShowingAnimator() {
        float castleSY = ViewUtil.getScreenHeight(this) + ViewUtil.dp2Px(this, 240);
        float castleDY =
                ViewUtil.getScreenHeight(this) - ViewUtil.dp2Px(this, 240) - ViewUtil.dp2Px(this, 30);
        PropertyValuesHolder castleX1 = PropertyValuesHolder.ofFloat("y", -700, 0f);

        PropertyValuesHolder castleScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1.0f);
        PropertyValuesHolder castleScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1.0f);

        ObjectAnimator castleAni = ObjectAnimator
                .ofPropertyValuesHolder(ivSplashLogo, castleX1, castleScaleX, castleScaleY);
        castleAni.addListener(new ViewShowListener(ivSplashLogo));
        castleAni.setInterpolator(new AnticipateOvershootInterpolator());
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
                mPresenter.getConnect(this);
            } else {
                showDialog();
            }
        }
        if (isAllreadyInstance == false) {
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
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            //* setViewVisible();*//*
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
                    mPresenter.getConnect(this);
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
           handler.sendEmptyMessageDelayed(0, 2000);

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(SplashActivity.this, PhoneLoginActivity.class);
                    ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            SplashActivity.this,
                            new Pair<View, String>(ivSplashLogo,
                                    PhoneLoginActivity.VIEW_LOGO));
                    // ActivityCompat是android支持库中用来适应不同android版本的
                    ActivityCompat.startActivity(SplashActivity.this, intent, activityOptions.toBundle());
                    finish();
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
        mPresenter.getVersion(BuildConfig.VERSION_NAME);
        BaiDuLocationUtils.getInstance().initData();
    }

    @Override
    public void phoneLoginSuccess(int userId) {
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
        setViewVisible();
    }

    @Override
    public void getVersion(VersionBean.AndroidBean androidBean) {
        String version = androidBean.getVer();
        String version1 = BuildConfig.VERSION_NAME;
        if (Utils.checkVersion(version, version1) == true) {
            popupWindow = new PopupWindow(this);
            PopWindowUpdate popWindowUpdate = new PopWindowUpdate(this, popupWindow, androidBean, clSplash);
        }
        if (popupWindow == null) {
            doAutoLogin();
        }
    }

    public void doAutoLogin() {
        if (autoLoginType != 5) {
            try {
                loginBean = LoginDBUtils.queryDataById(autoLoginType + "");
            } catch (Exception e) {
                setViewVisible();
            }
            if (isAutoLogin == 0) {
                setViewVisible();
            } else {
                if (loginBean == null || "".equals(loginBean)) {
                    setViewVisible();
                } else {
                    handler.sendEmptyMessageDelayed(2,2000);
                }
            }
        } else {
            setViewVisible();
        }
    }

    //读取返回信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1000:
                if (resultCode == WelcomeActivity.ResultCode) {
                    //checkPermissionReadPhoneState();
                }
                break;
        }
    }

    private void checkPermissionReadPhoneState() {
        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
                mPresenter.getConnect(this);
            } /*else {//请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
            }*/
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
    protected void onDestroy() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        if (instance != null) {
            instance = null;
        }
        BaiDuLocationUtils.getInstance().stopLocation();
        super.onDestroy();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        // Log.i("type类型",type.name()+"------");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(mContext, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            /**
             * 判断是否为空
             */
            if (mPermissionList.size() == 0) {//未授予的权限为空，表示都授予了
                if (isConnect == false) {
                    mPresenter.getConnect(this);
                }
            } else {//请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
            }
        }
    }

    @Override
    protected void onNetworkDisConnected() {
        isConnect = false;
    }
}
