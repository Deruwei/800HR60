package com.hr.ui.ui.main.activity;

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
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.contract.JobOrderContract;
import com.hr.ui.ui.main.modle.JobOrderModel;
import com.hr.ui.ui.main.presenter.JobOrderPresenter;
import com.hr.ui.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/13.
 */

public class JobOrderActivity extends BaseActivity<JobOrderPresenter, JobOrderModel> implements JobOrderContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_jobTypeTag)
    MyTextView tvJobTypeTag;
    @BindView(R.id.tv_jobType)
    TextView tvJobType;
    @BindView(R.id.iv_jobTypeSelect)
    ImageView ivJobTypeSelect;
    @BindView(R.id.tv_expectedFieldTag)
    MyTextView tvExpectedFieldTag;
    @BindView(R.id.tv_expectedField)
    TextView tvExpectedField;
    @BindView(R.id.tv_expectedFieldSelect)
    ImageView tvExpectedFieldSelect;
    @BindView(R.id.tv_expectedPositionTag)
    MyTextView tvExpectedPositionTag;
    @BindView(R.id.tv_expectedPosition)
    TextView tvExpectedPosition;
    @BindView(R.id.tv_expectedPositionSelect)
    ImageView tvExpectedPositionSelect;
    @BindView(R.id.tv_expectSalaryTag)
    MyTextView tvExpectSalaryTag;
    @BindView(R.id.tv_expectSalary)
    TextView tvExpectSalary;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    public  static JobOrderActivity instance;
    private List<CityBean> selectPositonList=new ArrayList<>();
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, JobOrderActivity.class);
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
    public void sendJobOrderSuccess() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.acitvity_orderintention;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvExpectedFieldTag.setTitleWidth(tvExpectedFieldTag);
        tvExpectedPositionTag.setTitleWidth(tvExpectedFieldTag);
        tvJobTypeTag.setTitleWidth(tvExpectedFieldTag);
        tvExpectSalaryTag.setTitleWidth(tvExpectedFieldTag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance=this;
    }

    @OnClick({R.id.rl_jobType, R.id.rl_expectedField, R.id.rl_expectedPosition, R.id.tv_expectSalarySelect, R.id.btn_nextEdu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_jobType:
                break;
            case R.id.rl_expectedField:
                break;
            case R.id.rl_expectedPosition:
                SelectPositionActivity.startAction(this,"11",selectPositonList);
                break;
            case R.id.tv_expectSalarySelect:
                break;
            case R.id.btn_nextEdu:
                break;
        }
    }
   public void setPositionList(List<CityBean> positionList){
        selectPositonList.clear();
        selectPositonList=positionList;
        StringBuffer sb=new StringBuffer();
        StringBuffer sbName=new StringBuffer();
        for(int i=0;i<positionList.size();i++){
            sb.append(","+positionList.get(i).getId());
            sbName.append(","+positionList.get(i).getName());
        }
        sb.deleteCharAt(0);
        sbName.deleteCharAt(0);
        tvExpectedPosition.setText(sbName);
    }
}
