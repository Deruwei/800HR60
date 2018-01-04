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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.ui.main.fragment.Fragment1;
import com.hr.ui.ui.main.fragment.Fragment2;
import com.hr.ui.ui.main.fragment.HomeFragment;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.utils.BottomNavigationViewHelper;
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
    ImageView ivMainPersonImg;
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
    private HomeFragment mHomeFragment;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private ResumeFragment fragment3, fragment4;
    private MenuItem menuItem;
    private PopupWindow popupWindow;
    private int userId;
    private ArrayList<Fragment> fragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


 /*   private void setCurrentFragment() {
      *//*  llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMenu.closeMenu();
            }
        });*//*
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment1 = Fragment1.newInstance(getString(R.string.navigation_navigation_bar));
        transaction.replace(R.id.ll_main, fragment1).commit();
    }*/

   /* public void toggleMenu(View view) {
        idMenu.toggle();
    }*/

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

    @OnClick({R.id.one, R.id.two, R.id.three, R.id.five})
    public void onViewClicked(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.one:
                break;
            case R.id.two:
                break;
            case R.id.three:
                break;
            case R.id.five:
                break;
        }
       /* idMenu.toggle();*/
        transaction.commit();
    }

    @Override
    public void initView() {
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
                        idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
                        rlLeftPage.setBackgroundResource(R.color.white);
                        ivMainPersonImg.setVisibility(View.VISIBLE);
                        if (menuItem != null) {
                            menuItem.setChecked(false);
                        } else {
                            bnvMain.getMenu().getItem(0).setChecked(false);
                        }
                        menuItem = bnvMain.getMenu().getItem(0);
                        menuItem.setChecked(true);
                        switchFragment(0);
                        break;
                    case R.id.message:
                        idMenu.setDrawerLockMode(MyDrawLayout2.LOCK_MODE_UNLOCKED);
                        rlLeftPage.setBackgroundResource(R.color.white);
                        ivMainPersonImg.setVisibility(View.VISIBLE);
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
        fragments.add(Fragment1.newInstance(getString(R.string.navigation_navigation_bar)));
        fragments.add(Fragment2.newInstance(getString(R.string.navigation_navigation_bar)));
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
}
