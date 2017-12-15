package com.hr.ui.ui.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.MutableInt;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ResumeData;
import com.hr.ui.db.ResumeDataUtils;
import com.hr.ui.ui.main.contract.MainContract;
import com.hr.ui.ui.main.fragment.Fragment1;
import com.hr.ui.ui.main.fragment.Fragment2;
import com.hr.ui.ui.main.fragment.Fragment3;
import com.hr.ui.ui.main.fragment.HomeFragment;
import com.hr.ui.ui.main.modle.MainModel;
import com.hr.ui.ui.main.presenter.MainPresenter;
import com.hr.ui.utils.BottomNavigationViewHelper;
import com.hr.ui.view.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.id_menu)
    SlidingMenu idMenu;
    @BindView(R.id.iv_mainPersonImg)
    ImageView ivMainPersonImg;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;
    private HomeFragment mHomeFragment;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3, fragment4;
    private MenuItem menuItem;
    private PopupWindow popupWindow;
    private int userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    private void setCurrentFragment() {
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idMenu.closeMenu();
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment1 = Fragment1.newInstance(getString(R.string.navigation_navigation_bar));
        transaction.replace(R.id.ll_main, fragment1).commit();
    }

    public void toggleMenu(View view) {
        idMenu.toggle();
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,int userId) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("userId",userId);
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
        idMenu.toggle();
        transaction.commit();
    }
    @Override
    public void initView() {
        userId=getIntent().getIntExtra("userId",0);
        @SuppressLint("ResourceType") ColorStateList csl=(ColorStateList)getResources().getColorStateList(R.drawable.bottomnavigation_textcolor);
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
                        if (fragment1 == null) {
                            fragment1 = Fragment1.newInstance(getString(R.string.navigation_navigation_bar));
                        }
                        transaction.replace(R.id.ll_main, fragment1);
                        break;
                    case R.id.message:
                        if (menuItem != null) {
                            menuItem.setChecked(false);
                        } else {
                            bnvMain.getMenu().getItem(0).setChecked(false);
                        }
                        menuItem = bnvMain.getMenu().getItem(1);
                        menuItem.setChecked(true);
                        if (fragment2 == null) {
                            fragment2 = Fragment2.newInstance(getString(R.string.navigation_navigation_bar));
                        }
                        transaction.replace(R.id.ll_main, fragment2);
                        break;
                    case R.id.resume:
                        if (menuItem != null) {
                            menuItem.setChecked(false);
                        } else {
                            bnvMain.getMenu().getItem(0).setChecked(false);
                        }
                        menuItem = bnvMain.getMenu().getItem(2);
                        menuItem.setChecked(true);
                        if (fragment3 == null) {
                            fragment3 = Fragment3.newInstance(getString(R.string.navigation_navigation_bar));
                        }
                        transaction.replace(R.id.ll_main, fragment3);
                        break;
                }
                transaction.commit();
                return false;
            }
        });
        setCurrentFragment();
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
}
