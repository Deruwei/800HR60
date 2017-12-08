package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.adapter.WelComePagerAdapter;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/11/21.
 */

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.vp_welcome)
    ViewPager vpWelcome;
    @BindView(R.id.ll_welcome)
    LinearLayout llWelcome;
    @BindView(R.id.btn_welcomeGoToMain)
    Button btnWelcomeGoToMain;
    @BindView(R.id.cl_bg)
    ConstraintLayout clBg;
    //view的集合 装载各页面的视图
    private List<View> vList;
    private int[] imageId = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3, R.mipmap.guide4};
    //装载导航小圆点
    private ImageView[] ivs;
    public static int ResultCode = 1001;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void initView() {
        clBg.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this,R.color.guide1));
        vList = new ArrayList<>();
        for (int i = 0; i < imageId.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(imageId[i]);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            vList.add(iv);
        }
        addPoints();
        WelComePagerAdapter mAdapter = new WelComePagerAdapter(vList);
        vpWelcome.setAdapter(mAdapter);
        // getSupportActionBar().hide();
        vpWelcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    clBg.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this,R.color.guide1));
                }
                if(position==1){
                    clBg.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this,R.color.guide2));
                }
                if(position==2){
                    clBg.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this,R.color.guide3));
                }
                if(position==3){
                    clBg.setBackgroundColor(ContextCompat.getColor(WelcomeActivity.this,R.color.guide4));
                }
                vpWelcome.setCurrentItem(position);
                for (int i = 0; i < ivs.length; i++) {
                    if (i == position) {
                        ivs[i].setImageDrawable(getResources().getDrawable(
                                R.mipmap.dot_black));
                    } else {
                        ivs[i].setImageDrawable(getResources().getDrawable(
                                R.mipmap.dot_white));
                    }
                }
                if (position + 1 == ivs.length) {
                    btnWelcomeGoToMain.setVisibility(View.VISIBLE);
                } else {
                    btnWelcomeGoToMain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initPresenter() {

    }

    //添加导航圆点
    private void addPoints() {
        ImageView pointImgView;
        ivs = new ImageView[vList.size()]; //确定小圆点的个数

        //动态添加小圆点
        for (int i = 0; i < vList.size(); i++) {
            pointImgView = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
            lp.setMargins(12, 0, 12, 0);
            pointImgView.setLayoutParams(lp);
            pointImgView.setPadding(5, 0, 5, 0);
            ivs[i] = pointImgView;

            // 默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if (i == 0) {
                ivs[i].setImageDrawable(getResources().getDrawable(
                        R.mipmap.dot_black));
            } else {
                ivs[i].setImageDrawable(getResources().getDrawable(
                        R.mipmap.dot_white));
            }
            // 将imageviews添加到小圆点视图组
            llWelcome.addView(ivs[i]);
        }
    }

    @OnClick(R.id.btn_welcomeGoToMain)
    public void onViewClicked() {
        SharedPreferencesUtils sUtils = new SharedPreferencesUtils(this);
        sUtils.setBooleanValue(Constants.IS_GUIDE, true);
        Intent intent = new Intent();
        setResult(ResultCode, intent);
        finish();
    }
}
