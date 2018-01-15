package com.hr.ui.ui.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.message.fragment.DeliverFeedbackFragment;
import com.hr.ui.ui.message.adapter.MyFragmentPagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/15.
 */

public class DeliverFeedbackActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tl_deliverFeedback)
    TabLayout tlDeliverFeedback;
    @BindView(R.id.vp_deliverFeedback)
    ViewPager vpDeliverFeedback;
    private List<String> titles=new ArrayList<>();
    private List<Integer> bgId=new ArrayList<>();
    private List<Fragment> fragments=new ArrayList<>();
    private MyFragmentPagerAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_deliverfeedback;
    }
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, DeliverFeedbackActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.deliverFeedBack);
        titles.add(getString(R.string.allReadyDeliver));
        titles.add(getString(R.string.Seem));
        for(int i=0;i<titles.size();i++){
            bgId.add(R.drawable.rv_bg_red);
            fragments.add(DeliverFeedbackFragment.newInstance(i));
        }
        mAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),this,fragments,titles,bgId);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vpDeliverFeedback.setAdapter(mAdapter);
        //tablayout加载viewpager
        tlDeliverFeedback.setupWithViewPager(vpDeliverFeedback);
        for (int i = 0; i < tlDeliverFeedback.getTabCount(); i++) {
            TabLayout.Tab tab = tlDeliverFeedback.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mAdapter.getTabView(i));

            }
        }
        setIndicator(tlDeliverFeedback,50,50);
    }
    public void setIndicator (TabLayout tabs,int leftDip,int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
