package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.MainContract;
import com.hr.ui.ui.main.fragment.Fragment1;
import com.hr.ui.ui.main.fragment.HomeFragment;
import com.hr.ui.ui.main.fragment.JobSearchFragment;
import com.hr.ui.ui.main.fragment.MessageFragment;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.ui.main.modle.MainModel;
import com.hr.ui.ui.main.presenter.MainPresenter;
import com.hr.ui.ui.me.activity.CollectionActivity;
import com.hr.ui.ui.me.activity.FeedBackActivity;
import com.hr.ui.ui.me.activity.ScanHistoryActivity;
import com.hr.ui.ui.me.activity.SettingActivity;
import com.hr.ui.ui.message.fragment.DeliverFeedbackFragment;
import com.hr.ui.utils.DownloadSignatureServic;
import com.hr.ui.utils.NetWorkUtilsDNs;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.MyDrawLayout2;
import com.hr.ui.view.OnBottomNavigationItemClickListener;
import com.hr.ui.view.PopupWindowComment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter,MainModel> implements MainContract.View {
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.view_main)
    View viewMain;
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
    private int userId;
    private ArrayList<Fragment> fragments;
    public int REQUEST_CODE = 0x1007;
    public boolean isHome = true;
    private int mIndex;
    JobSearchFragment jobSearchFragment;
    public static MainActivity instance;
    private SharedPreferencesUtils sUtis;
    private String personImage;
    private PopupWindow popupWindowGiveComment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //创建手势检测
        instance=this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, int userId) {
        Intent intent = new Intent(activity, MainActivity.class);
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
        }
    }
    private void initFragment() {
        fragments=new ArrayList<>();
        HomeFragment homeFragment = HomeFragment.newInstance("home");
        MessageFragment messageFragment = MessageFragment.newInstance("message");
        ResumeFragment resumeFragment= ResumeFragment.newInstance("resume");
        //添加到数组
       fragments.add(homeFragment);
       fragments.add(messageFragment);
       fragments.add(resumeFragment);
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.ll_main, homeFragment).commit();
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
        } else {
            ft.show(fragments.get(index));
            if(index==1){
                MessageFragment.instance.getDate(false);
            }
        }
        ft.commit();
        //再次赋值
        mIndex = index;

    }
    private void downLoadApp(){
        Bundle bundle = new Bundle();
        bundle.putString("signatureurl", "");/*电子签名下载地址*/
        Intent it = new Intent().setClass(this, DownloadSignatureServic.class).putExtras(bundle);
        startService(it);
    }
    private void setRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_home:
                        if (isHome == true) {
                            idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
                            rlLeftPage.setBackgroundResource(R.color.bg_homeTitle);
                            isHome = true;
                            setIndexSelected(0);
                        } else {
                            idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_LOCKED_CLOSED);
                            rlLeftPage.setBackgroundResource(R.color.view_f0f0f0);
                            isHome = false;
                            setIndexSelected(3);
                        }
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
        mPresenter.getNotice("96","794,796,797,798");
        userId = getIntent().getIntExtra("userId", 0);
        initFragment();
        setRadioGroupListener();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                //PopupWindowComment popupWindowComment=new PopupWindowComment(popupWindowGiveComment,this,idMenu);
                ToastUitl.showShort("再点一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
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
        JobSerchActivity.startAction2(this, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == JobSerchActivity.instance.RESULT_CODE) {
            JobSearchBean jobSearchBean1 = (JobSearchBean) data.getSerializableExtra("jobSearch");
            //Log.i("当前的数据",jobSearchBean1.toString());
            if (isHome == false) {
                idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_LOCKED_CLOSED);
                /*rlFragmentTitle.setVisibility(View.GONE);*/
                rlLeftPage.setBackgroundResource(R.color.view_f0f0f0);
                if (fragments.size() == 4) {
                    fragments.remove(3);
                }
                jobSearchFragment = JobSearchFragment.newInstance(jobSearchBean1);
                //Log.i("传到",fragments.get(3).toString());
                fragments.add(jobSearchFragment);
                //Log.i("传到", fragments.get(3).toString());
                setIndexSelected(3);
            } else {
                if (fragments.size() == 4) {
                    fragments.remove(3);
                }
                setIndexSelected(0);
            }
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
    public void getNoticeSuccess(List<FindBean.ListBean> listBean) {

    }
}
