package com.hr.ui.ui.main.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.EventHomeBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.MainContract;
import com.hr.ui.ui.main.fragment.HomeFragment;
import com.hr.ui.ui.main.fragment.MessageFragment;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.ui.main.modle.MainModel;
import com.hr.ui.ui.main.presenter.MainPresenter;
import com.hr.ui.ui.me.activity.CollectionActivity;
import com.hr.ui.ui.me.activity.FeedBackActivity;
import com.hr.ui.ui.me.activity.ScanHistoryActivity;
import com.hr.ui.ui.me.activity.SettingActivity;
import com.hr.ui.utils.CodeTimer;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.EventBusAction;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.MyDrawLayout2;
import com.hr.ui.view.PopupWindowAd;
import com.hr.ui.view.PopupWindowComment;
import com.hr.ui.view.PopupWindowWarm;
import com.service.CodeTimerService;
import com.service.MyJobService;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_message)
    RadioButton rbMessage;
    @BindView(R.id.rb_resume)
    RadioButton rbResume;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rl_leftPage)
    RelativeLayout rlLeftPage;
    @BindView(R.id.iv_personImage_left)
    CircleImageView ivPersonImageLeft;
    @BindView(R.id.one)
    ImageView one;
    @BindView(R.id.rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.two)
    ImageView two;
    @BindView(R.id.rl_history)
    RelativeLayout rlHistory;
    @BindView(R.id.three)
    ImageView three;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.five)
    ImageView five;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.rl_rightPage)
    RelativeLayout rlRightPage;
    @BindView(R.id.id_menu)
    MyDrawLayout2 idMenu;
    private PopupWindow popupWindow;
    private ArrayList<Fragment> fragments;
    /* public boolean isHome = true;*/
    private int mIndex;
    public static MainActivity instance;
    private SharedPreferencesUtils sUtis;
    private String personImage, contentWarn, imageUrl, adUrl;
    private List<FindBean.ListBean> listBeanList = new ArrayList<>();
    private PopupWindow popupWindowGiveComment;
    public RadioButton rbResume1;
    private boolean giveComment, hasAds, hasWarm;
    private PopupWindow popupWindowTips;
    private ComponentName mServieComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //创建手势检测
        instance = this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, int userId) {
        Intent intent = new Intent(activity, MainActivity.class);
        if (userId == 0) {
            MobclickAgent.onEvent(HRApplication.getAppContext(), "v6_resume_complete");
        }
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @OnClick({R.id.rl_collection, R.id.rl_history, R.id.rl_feedback, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_collection:
                CollectionActivity.startAction(this);
                break;
            case R.id.rl_history:
                ScanHistoryActivity.startAction(this);
                break;
            case R.id.rl_feedback:
                FeedBackActivity.startAction(this);
                break;
            case R.id.rl_setting:
                SettingActivity.startAction(this);
                break;
        }
       /* idMenu.toggle();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        sUtis = new SharedPreferencesUtils(this);
        setImage();
    }

    public void setImage() {
        personImage = sUtis.getStringValue(Constants.PERSONIMAGE, "");
        if (!"".equals(personImage) && personImage != null) {
           /* Glide.with(this).load(Constants.IMAGE_BASEPATH + personImage).centerCrop().into(ivResumePersonPhoto);*/
            Glide.with(this).load(Constants.IMAGE_BASEPATH + personImage).fitCenter().into(ivPersonImageLeft);
        } else {
            ivPersonImageLeft.setImageResource(R.mipmap.persondefault);
        }
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        HomeFragment homeFragment = HomeFragment.newInstance("home");
        MessageFragment messageFragment = MessageFragment.newInstance("message");
        ResumeFragment resumeFragment = ResumeFragment.newInstance("resume");
        //添加到数组
        fragments.add(homeFragment);
        fragments.add(messageFragment);
        fragments.add(resumeFragment);
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.ll_main, homeFragment).commit();
        rlLeftPage.setBackgroundResource(R.color.bg_homeTitle);
        //默认设置为第0个
        setIndexSelected(0);
    }

    public void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏
        ft.hide(fragments.get(mIndex));
        //判断是否添加
        if (!fragments.get(index).isAdded()) {
            ft.add(R.id.ll_main, fragments.get(index)).show(fragments.get(index));
            if (index == 0) {
                MobclickAgent.onEvent(this, "v6_scan_main");
            }
            if (index == 2) {
                MobclickAgent.onEvent(this, "v6_scan_resume");
                idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_LOCKED_CLOSED);
            }
            if (index == 1) {
                MobclickAgent.onEvent(this, "v6_scan_message");
            }
        } else {
            ft.show(fragments.get(index));
            if (index == 0) {
                MobclickAgent.onEvent(this, "v6_scan_main");
                idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
            }
            if (index == 1) {
                MobclickAgent.onEvent(this, "v6_scan_message");
                MobclickAgent.onEvent(this, "v6_fresh_message");
               // MessageFragment.instance.getDate(false);
                EventBus.getDefault().post(new EventHomeBean(EventBusAction.MESSAGEFRAGMENT_REFLESH));
            }
            if (index == 2) {
                MobclickAgent.onEvent(this, "v6_scan_resume");
                idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_LOCKED_CLOSED);
            }
        }
        ft.commitAllowingStateLoss();
        //再次赋值
        mIndex = index;

    }

    private void setRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
                        rlLeftPage.setBackgroundResource(R.color.bg_homeTitle);
                        setIndexSelected(0);
                        break;
                    case R.id.rb_message:
                        idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
                        rlLeftPage.setBackgroundResource(R.color.white);
                        setIndexSelected(1);
                        break;
                    case R.id.rb_resume:
                        idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_LOCKED_CLOSED);
                        rlLeftPage.setBackgroundResource(R.drawable.resume_title_bg);
                        setIndexSelected(2);
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {
        instance = this;
        EventBus.getDefault().register(this);
        mPresenter.getNotice("96", "794,796,797,798");
        /*userId = getIntent().getIntExtra("userId", 0);*/
        rbResume1 = rbResume;
        sUtis = new SharedPreferencesUtils(this);
        initFragment();
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
//获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("WangJ", "状态栏-方法1:" + statusBarHeight1);
        setRadioGroupListener();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mServieComponent = new ComponentName(this, MyJobService.class);
            Intent startServiceIntent = new Intent(this, MyJobService.class);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                startForegroundService(startServiceIntent);
            } else {
                startService(startServiceIntent);
            }
            pollServer();
        }
        if (!sUtis.getBooleanValue(Constants.VALID_TYPE, true)) {
            //customAnimationDialog();
            ValidPhoneFirstActivity.startAction(this,sUtis.getStringValue(Constants.USERPHONE,""));
        }
        idMenu.setDrawerListener(new MyDrawLayout2.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
              /*  // 导航图标渐变效果
                ivResumePersonPhoto.setAlpha(1 - slideOffset);*/
                // 判断是否左菜单并设置移动(如果不这样设置,则主页面的内容不会向右移动)
                if (drawerView.getTag().equals("left")) {
                    View content = idMenu.getChildAt(0);
                    int offset = (int) (drawerView.getWidth() * slideOffset);
                    content.setTranslationX(offset);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    private long exitTime = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initTips();
                    break;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void pollServer() {
        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(1, mServieComponent)
                    /*
                    * 定时任务
                    * */
                .setPeriodic(1000 * 60 * 18)
                    /*
                    * 这将设置你的工作期限。即使是无法满足其他要求，你的任务将约在规定的时间已经过去时开始执行
                    * */
              /*  .setOverrideDeadline(60000)*/
                    /*
                    * 只有在设备处于一种特定的网络中时，它才启动
                    * 默认值是JobInfo.NETWORK_TYPE_NONE :无论是否有网络连接，该任务均可以运行
                    * JobInfo.NETWORK_TYPE_ANY，这需要某种类型的网络连接可用，工作才可以运行
                    * JobInfo.NETWORK_TYPE_UNMETERED，这就要求设备在非蜂窝网络中
                    * */
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        scheduler.schedule(jobInfo);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (giveComment == true) {
                PopupWindowComment popupWindowComment = new PopupWindowComment(new PopupWindow(this), this, idMenu);
            } else {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    //弹出提示，可以有多种方式
                    //PopupWindowComment popupWindowComment=new PopupWindowComment(popupWindowGiveComment,this,idMenu);
                    ToastUitl.showShort("再点一次退出程序");
                    exitTime = System.currentTimeMillis();
                } else {
                    AppManager.getAppManager().exitApp();
                }
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 自定义NavigationIcon设置关联DrawerLayout
     */
    public void toggle() {
        int drawerLockMode = idMenu.getDrawerLockMode(GravityCompat.START);
        if (idMenu.isDrawerVisible(GravityCompat.START)
                && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            idMenu.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            idMenu.openDrawer(GravityCompat.START);
        }
    }

    public void goToSearch2() {
        JobSerchActivity.startAction2(this);
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
    public void getNoticeSuccess(List<FindBean.ListBean> listBean) {
        boolean isFirstInto = sUtis.getBooleanValue(Constants.ISFIRSTINTO, false);
        if (isFirstInto == true) {
            sUtis.setBooleanValue(Constants.ISFIRSTINTO, false);
            handler.sendEmptyMessageDelayed(0, 10);

        }
        listBeanList.clear();
        listBeanList.addAll(listBean);
        for (int i = 0; i < listBean.size(); i++) {
            if ("798".equals(listBean.get(i).getA_id())) {
                hasWarm = true;
                contentWarn = listBean.get(i).getAd_txt();
            }
            if ("794".equals(listBean.get(i).getA_id())) {
                hasAds = true;
                imageUrl = listBean.get(i).getPic_s_path();
                adUrl = listBean.get(i).getTopic_url();
            }
            if ("796".equals(listBean.get(i).getA_id())) {
                giveComment = true;
            }
        }
        initPop();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMethod(EventHomeBean eventHomeBean){
        switch (eventHomeBean.getType()){
            case 1:
                setImage();
                break;
        }
    }
    private void initPop() {
        if (hasWarm == true) {
            PopupWindowWarm popupWindowWarm = new PopupWindowWarm(new PopupWindow(this), contentWarn, idMenu, this);
        } else {
            if (hasAds == true) {
                PopupWindowAd popupWindowAd = new PopupWindowAd(this, new PopupWindow(this), idMenu, adUrl, imageUrl);
            }
        }
    }

    private void initTips() {
        View viewTips = getLayoutInflater().inflate(R.layout.layout_tips, null);
        popupWindowTips = new PopupWindow(viewTips, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView ivTips = viewTips.findViewById(R.id.iv_tip);
        ivTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowTips.dismiss();
            }
        });
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindowTips.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        popupWindowTips.setFocusable(true);
        popupWindowTips.setOutsideTouchable(false);
        popupWindowTips.setAnimationStyle(R.style.style_pop_animation2);
        popupWindowTips.showAtLocation(idMenu, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        if (popupWindowTips != null) {
            popupWindowTips.dismiss();
        }
        if (popupWindowGiveComment != null) {
            popupWindowGiveComment.dismiss();
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        if(instance!=null){
            instance=null;
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
