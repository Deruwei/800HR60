package com.hr.ui.ui.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.ui.message.adapter.MyFragmentPagerAdapter;
import com.hr.ui.ui.message.fragment.DeliverFeedbackFragment;
import com.hr.ui.ui.message.fragment.EmploymentGuidanceFragment;
import com.hr.ui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/16.
 */

public class EmploymentGuidanceActivity extends BaseNoConnectNetworkAcitivty {
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
    private List<Fragment> fragments=new ArrayList<>();
    private MyFragmentPagerAdapter mAdapter;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, EmploymentGuidanceActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_deliverfeedback;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.employmentGuidance);
        tlDeliverFeedback.setTabMode(TabLayout.MODE_SCROLLABLE);
        titles.add("求职宝典");
        titles.add("简历指南");
        titles.add("职业诊断");
        titles.add("职场八卦");
        titles.add("创业指南");
        titles.add("面试秘籍");
        titles.add("劳动模范");
        titles.add("人际关系");
        titles.add("薪酬行情");
        for(int i=0;i<titles.size();i++){
          /*  bgId.add(R.drawable.rv_bg_red);*/
            fragments.add(EmploymentGuidanceFragment.newInstance(i));
        }
        mAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),this,fragments,titles);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
