package com.hr.ui.ui.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.fragment.Fragment1;
import com.hr.ui.ui.me.activity.ScanHistoryActivity;
import com.hr.ui.ui.me.activity.SettingActivity;
import com.hr.ui.ui.message.fragment.DeliverFeedbackFragment;
import com.hr.ui.ui.main.fragment.HomeFragment;
import com.hr.ui.ui.main.fragment.JobSearchFragment;
import com.hr.ui.ui.main.fragment.MessageFragment;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.ui.me.activity.CollectionActivity;
import com.hr.ui.ui.me.activity.FeedBackActivity;
import com.hr.ui.utils.BottomNavigationViewHelper;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.MyDrawLayout2;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseNoConnectNetworkAcitivty {

    @BindView(R.id.one)
    ImageView one;
    @BindView(R.id.two)
    ImageView two;
    @BindView(R.id.three)
    ImageView three;
    @BindView(R.id.five)
    ImageView five;
    @BindView(R.id.iv_ResumePersonPhoto)
    CircleImageView ivMainPersonImg;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;
    @BindView(R.id.id_menu)
    MyDrawLayout2 idMenu;
    @BindView(R.id.iv_personImage_left)
    ImageView ivPersonImageLeft;
    @BindView(R.id.rl_rightPage)
    RelativeLayout rlRightPage;
    @BindView(R.id.rl_leftPage)
    RelativeLayout rlLeftPage;
    @BindView(R.id.rl_fragmentTitle)
    RelativeLayout rlFragmentTitle;
    @BindView(R.id.iv_mainSearch)
    ImageView ivMainSearch;
    @BindView(R.id.rl_mainSearch)
    RelativeLayout rlMainSearch;
    @BindView(R.id.tv_fragmentMessage)
    TextView tvFragmentMessage;
    private HomeFragment mHomeFragment;
    private Fragment1 fragment1;
    private DeliverFeedbackFragment fragment2;
    private ResumeFragment fragment3, fragment4;
    private MenuItem menuItem;
    private PopupWindow popupWindow;
    private int userId;
    private ArrayList<Fragment> fragments;
    public   int REQUEST_CODE=0x1007;
    public  boolean isHome=true;
    private JobSearchBean jobSearchBean;
    public static MainActivity instance;
    private SharedPreferencesUtils sUtis;
    private String personImage;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //创建手势检测
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sUtis=new SharedPreferencesUtils(this);
        setImage();
    }
    public void setImage(){
        personImage=sUtis.getStringValue(Constants.PERSONIMAGE,"");
        if(!"".equals(personImage)&&personImage!=null) {
            Glide.with(this).load(Constants.IMAGE_BASEPATH+personImage).centerCrop().into(ivMainPersonImg);
            Glide.with(this).load(Constants.IMAGE_BASEPATH+personImage).centerCrop().into(ivPersonImageLeft);
        }
    }
    @Override
    public void initView() {
        instance=this;
        userId = getIntent().getIntExtra("userId", 0);
        //设置监听
        ivMainPersonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        //setDrawerRightEdgeSize(idMenu,this,0.6f);
        //setDrawerRightEdgeSize(idMenu,this,0.6f);
        fragments = new ArrayList<>();
        addFragment();
        switchFragment(0);
        idMenu.setDrawerListener(new MyDrawLayout2.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // 导航图标渐变效果
                ivMainPersonImg.setAlpha(1 - slideOffset);
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
        @SuppressLint("ResourceType") ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.drawable.bottomnavigation_textcolor);
        bnvMain.setItemTextColor(csl);
        bnvMain.setItemIconTintList(csl);
        BottomNavigationViewHelper.disableShiftMode(bnvMain);
        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.home:
                        if (menuItem != null) {
                            menuItem.setChecked(false);
                        } else {
                            bnvMain.getMenu().getItem(0).setChecked(false);
                        }
                        menuItem = bnvMain.getMenu().getItem(0);
                        menuItem.setChecked(true);
                        if(isHome==true) {
                            idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
                            rlLeftPage.setBackgroundResource(R.color.resumeContent_bg);
                            rlFragmentTitle.setVisibility(View.VISIBLE);
                            rlFragmentTitle.setBackgroundResource(R.color.resumeContent_bg);
                            rlMainSearch.setVisibility(View.VISIBLE);
                            tvFragmentMessage.setVisibility(View.GONE);
                            isHome=true;
                            switchFragment(0);
                        }else{
                            idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_LOCKED_CLOSED);
                            rlFragmentTitle.setVisibility(View.GONE);
                            rlLeftPage.setBackgroundResource(R.color.view_f0f0f0);

                            isHome=false;
                            switchFragment(3);
                        }
                        break;
                    case R.id.message:
                        idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
                        rlLeftPage.setBackgroundResource(R.color.white);
                        rlFragmentTitle.setVisibility(View.VISIBLE);
                        rlFragmentTitle.setBackgroundResource(R.color.white);
                        tvFragmentMessage.setVisibility(View.VISIBLE);
                        rlMainSearch.setVisibility(View.GONE);
                        if (menuItem != null) {
                            menuItem.setChecked(false);
                        } else {
                            bnvMain.getMenu().getItem(0).setChecked(false);
                        }
                        menuItem = bnvMain.getMenu().getItem(1);
                        menuItem.setChecked(true);
                        switchFragment(1);

                        break;
                    case R.id.resume:
                        idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_LOCKED_CLOSED);
                        rlLeftPage.setBackgroundResource(R.drawable.resume_title_bg);
                        rlFragmentTitle.setVisibility(View.GONE);
                        if (menuItem != null) {
                            menuItem.setChecked(false);
                        } else {
                            bnvMain.getMenu().getItem(0).setChecked(false);
                        }
                        menuItem = bnvMain.getMenu().getItem(2);
                        menuItem.setChecked(true);
                        switchFragment(2);
                        break;
                }
                transaction.commit();
                return false;
            }
        });
        // setCurrentFragment();
    }

    /**
     * 点击切换fragment
     *
     * @param position
     */
    public void switchFragment(int position) {
        //开启事务
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (position == 0) {
            idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
            rlLeftPage.setBackgroundResource(R.color.resumeContent_bg);
            rlFragmentTitle.setVisibility(View.VISIBLE);
            rlFragmentTitle.setBackgroundResource(R.color.resumeContent_bg);
            rlMainSearch.setVisibility(View.VISIBLE);
            tvFragmentMessage.setVisibility(View.GONE);
        }
        //遍历集合
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == position) {
                //显示fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,显示
                    fragmentTransaction.show(fragment);
                } else {
                    //如果这个fragment没有被事务添加过,添加
                    fragmentTransaction.add(R.id.ll_main, fragment);
                }
            } else {
                //隐藏fragment
                if (fragment.isAdded()) {
                    //如果这个fragment已经被事务添加,隐藏
                    fragmentTransaction.hide(fragment);
                }
            }
        }
        //提交事务
        fragmentTransaction.commit();
    }

    public void addFragment() {
        fragments.add(HomeFragment.newInstance(getString(R.string.navigation_navigation_bar)));
        fragments.add(MessageFragment.newInstance(getString(R.string.navigation_navigation_bar)));
        fragments.add(ResumeFragment.newInstance(getString(R.string.navigation_navigation_bar)));
    }

    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
    private void toggle() {
        int drawerLockMode = idMenu.getDrawerLockMode(GravityCompat.START);
        if (idMenu.isDrawerVisible(GravityCompat.START)
                && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            idMenu.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            idMenu.openDrawer(GravityCompat.START);
        }
    }

    @OnClick(R.id.rl_mainSearch)
    public void onViewClicked() {
        JobSerchActivity.startAction(this,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE&&resultCode==JobSerchActivity.instance.RESULT_CODE){
            JobSearchBean jobSearchBean1= (JobSearchBean) data.getSerializableExtra("jobSearch");
            //Log.i("当前的数据",jobSearchBean1.toString());
            if(isHome==false) {
                idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_LOCKED_CLOSED);
                rlFragmentTitle.setVisibility(View.GONE);
                rlLeftPage.setBackgroundResource(R.color.view_f0f0f0);
                if (fragments.contains(JobSearchFragment.instance)) {
                    fragments.remove(JobSearchFragment.instance);
                }
                fragments.add(JobSearchFragment.newInstance(jobSearchBean1));
                switchFragment(3);
            }else{
                switchFragment(0);
            }
           // Log.i("数据",jobSearchBean.toString());
           /* fragments[3].instance.setData(jobSearchBean);*/
         /*  switchFragment(3);
            Log.i("数据",jobSearchBean.toString());
           JobSearchFragment.instance.initData(jobSearchBean);*/
        }
    }
}
