package com.hr.ui.ui.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.GuidanceInfoBean;
import com.hr.ui.ui.message.contract.GuidanceInfoContract;
import com.hr.ui.ui.message.model.GuidanceInfoModel;
import com.hr.ui.ui.message.presenter.GuidanceInfoPresenter;
import com.hr.ui.utils.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/16.
 */

public class GuidanceInfoActivity extends BaseActivity<GuidanceInfoPresenter, GuidanceInfoModel> implements GuidanceInfoContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_guidanceInfoTitle)
    TextView tvGuidanceInfoTitle;
    @BindView(R.id.tv_guidanceInfoScanNum)
    TextView tvGuidanceInfoScanNum;
    @BindView(R.id.tv_guidanceInfoTime)
    TextView tvGuidanceInfoTime;
    @BindView(R.id.tv_guidanceInfoType)
    TextView tvGuidanceInfoType;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private int position;
    private String guidanceId;
    private GuidanceInfoBean.TitleContentListBean titleContentListBean;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, int position, String guidanceId) {
        Intent intent = new Intent(activity, GuidanceInfoActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("guidanceId", guidanceId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
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
    public int getLayoutId() {
        return R.layout.activity_guidanceinfo;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        guidanceId = getIntent().getStringExtra("guidanceId");
        position = getIntent().getIntExtra("position", 0);
        mPresenter.getGuidanceInfo(guidanceId);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(Utils.getGuidanceTitle(position));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    public void getGuidanceInfoSuccess(List<GuidanceInfoBean.TitleContentListBean> titleContentListBeans) {
        MobclickAgent.onEvent(this,"v6_scan_article");
        if (titleContentListBeans != null && !"".equals(titleContentListBeans)) {
            titleContentListBean = titleContentListBeans.get(0);
            initUI();
        }
    }

    private void initUI() {
        tvGuidanceInfoTitle.setText(titleContentListBean.getTitle());
        tvGuidanceInfoTime.setText(titleContentListBean.getInputtime());
        tvGuidanceInfoScanNum.setText(titleContentListBean.getViews());
        tvGuidanceInfoType.setText(titleContentListBean.getCopyfrom());
        tvContent.setText(titleContentListBean.getContent());
    }
}
