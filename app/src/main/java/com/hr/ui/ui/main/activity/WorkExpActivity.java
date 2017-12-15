package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.ui.main.contract.WorkExpContract;
import com.hr.ui.ui.main.modle.WorkExpModel;
import com.hr.ui.ui.main.presenter.WorkExpPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.MyStartAndEndTimeCustomDatePicker;
import com.hr.ui.view.MyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/13.
 */

public class WorkExpActivity extends BaseActivity<WorkExpPresenter, WorkExpModel> implements WorkExpContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_companyNameTag)
    MyTextView tvCompanyNameTag;
    @BindView(R.id.et_companyName)
    EditText etCompanyName;
    @BindView(R.id.tv_positionTag)
    MyTextView tvPositionTag;
    @BindView(R.id.tv_workPlaceTag)
    MyTextView tvWorkPlaceTag;
    @BindView(R.id.tv_workPlace)
    TextView tvWorkPlace;
    @BindView(R.id.tv_workPlaceSelect)
    ImageView tvWorkPlaceSelect;
    @BindView(R.id.rl_workPlace)
    RelativeLayout rlWorkPlace;
    @BindView(R.id.tv_grossPayTag)
    MyTextView tvGrossPayTag;
    @BindView(R.id.et_grossPay)
    EditText etGrossPay;
    @BindView(R.id.tv_position)
    EditText tvPosition;
    @BindView(R.id.tv_positionSelect)
    ImageView tvPositionSelect;
    @BindView(R.id.tv_workExpStartAndEndTimeTag)
    MyTextView tvWorkExpStartAndEndTimeTag;
    @BindView(R.id.tv_workExpStartAndEndTime)
    TextView tvWorkExpStartAndEndTime;
    @BindView(R.id.tv_workExpStartAndEndTimeSelect)
    ImageView tvWorkExpStartAndEndTimeSelect;
    @BindView(R.id.tv_responsibilityDesTag)
    MyTextView tvResponsibilityDesTag;
    @BindView(R.id.tv_responsibilityDes)
    TextView tvResponsibilityDes;
    @BindView(R.id.tv_responsibilityDesSelect)
    ImageView tvResponsibilityDesSelect;
    private String endTimes,startTimes,cityId,responbilityDes;
    private MyStartAndEndTimeCustomDatePicker datePickerSE;
    public static final String TAG=WorkExpActivity.class.getSimpleName();
    public static  WorkExpActivity instance;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, WorkExpActivity.class);
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
    public void sendWorkExpSuccess() {
        JobOrderActivity.startAction(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.acitvity_workexperience;
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
        tvCompanyNameTag.setTitleWidth(tvCompanyNameTag);
        tvPositionTag.setTitleWidth(tvCompanyNameTag);
        tvGrossPayTag.setTitleWidth(tvCompanyNameTag);
        tvResponsibilityDesTag.setTitleWidth(tvCompanyNameTag);
        tvWorkPlaceTag.setTitleWidth(tvCompanyNameTag);
        tvWorkExpStartAndEndTimeTag.setTitleWidth(tvCompanyNameTag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance=this;
        initDialog();
    }

    private void initDialog() {
        datePickerSE=new MyStartAndEndTimeCustomDatePicker(this, new MyStartAndEndTimeCustomDatePicker.ResultHandler() {
            @Override
            public void handle(String startTime, String endTime) {
                tvWorkExpStartAndEndTime.setText(startTime+"  至  "+endTime);
                startTimes=startTime;
                endTimes=endTime;
            }
        });
    }

    @OnClick({R.id.iv_companyNameDelete,R.id.rl_workPlace, R.id.iv_grossPayDelete, R.id.rl_position, R.id.rl_workExpStartAndEndTime, R.id.btn_nextEdu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_companyNameDelete:
                break;
            case R.id.iv_grossPayDelete:
                break;
            case R.id.rl_position:
                break;
            case R.id.rl_workExpStartAndEndTime:
                datePickerSE.show(startTimes,endTimes);
                break;
            case R.id.btn_nextEdu:
                 //doSendWorkExp();
                JobOrderActivity.startAction(this);
                break;
            case R.id.rl_workPlace:
                SelectCityActivity.startAction(this,1,TAG);
                break;
        }
    }

    private void doSendWorkExp() {
        if(etCompanyName.getText().toString()==null||"".equals(etCompanyName.getText().toString())){
            ToastUitl.showShort("请填写公司名称");
            return;
        }
        if(tvPosition.getText().toString()==null||"".equals(tvPosition.getText().toString())){
            ToastUitl.showShort("请填写所任职位");
            return;
        }
        if(cityId==null||"".equals(cityId)){
            ToastUitl.showShort("请选择工作地点");
            return;
        }
        if(etGrossPay.getText().toString()==null||"".equals(etGrossPay.getText().toString())){
            ToastUitl.showShort("请填写税前月薪");
            return;
        }
        if(startTimes==null||endTimes==null||"".equals(startTimes)||"".equals(endTimes)){
            ToastUitl.showShort("请选择起止时间");
            return;
        }
        if(tvResponsibilityDes.getText().toString()==null||"".equals(tvResponsibilityDes.getText().toString())){
            ToastUitl.showShort("请填写职位描述");
            return;
        }
        WorkExpData workExpData=new WorkExpData();
        workExpData.setCompany(etCompanyName.getText().toString());
        workExpData.setPosition(tvPosition.getText().toString());
        workExpData.setWorkPlace(cityId);
        workExpData.setGrossPay(etGrossPay.getText().toString());
        workExpData.setStartTime(startTimes);
        workExpData.setEndTime(endTimes);
        workExpData.setResponsibilityDescription(responbilityDes);
        mPresenter.sendWorkExpToResume(workExpData);
    }
    public void setSelectCity(CityBean cityBean){
        if(tvWorkPlace!=null) {
            tvWorkPlace.setText(cityBean.getName());
        }
        cityId=cityBean.getId();
    }
    public void setTvResponsibilityDes(String content){
        tvResponsibilityDes.setText(content);
        responbilityDes=content;
    }
    @OnClick(R.id.rl_responsibilityDes)
    public void onViewClicked() {
        ContentActivity.startAction(this);
    }
}
