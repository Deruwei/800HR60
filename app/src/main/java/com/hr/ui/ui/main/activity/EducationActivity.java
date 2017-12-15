package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.EducationData;
import com.hr.ui.ui.main.contract.EducationContract;
import com.hr.ui.ui.main.modle.EducationModel;
import com.hr.ui.ui.main.presenter.EducationPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyStartAndEndTimeCustomDatePicker;
import com.hr.ui.view.MyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/13.
 */

public class EducationActivity extends BaseActivity<EducationPresenter, EducationModel> implements EducationContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_schoolTag)
    MyTextView tvSchoolTag;
    @BindView(R.id.et_school)
    EditText etSchool;
    @BindView(R.id.tv_professionTag)
    MyTextView tvProfessionTag;
    @BindView(R.id.tv_profession)
    TextView tvProfession;
    @BindView(R.id.tv_professionSelect)
    ImageView tvProfessionSelect;
    @BindView(R.id.tv_educationTag)
    MyTextView tvEducationTag;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_educationSelect)
    ImageView tvEducationSelect;
    @BindView(R.id.tv_startAndEndTimeTag)
    MyTextView tvStartAndEndTimeTag;
    @BindView(R.id.tv_startAndEndTime)
    TextView tvStartAndEndTime;
    @BindView(R.id.tv_startAndEndTimeSelect)
    ImageView tvStartAndEndTimeSelect;
    private String degreeId,startTimes="",endTimes="";
    private CustomDatePicker datePickerDegree;
    private MyStartAndEndTimeCustomDatePicker datePickerSE;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, EducationActivity.class);
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
    public void sendEducationSuccess() {
        WorkExpActivity.startAction(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_education;
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
        tvEducationTag.setTitleWidth(tvStartAndEndTimeTag);
        tvProfessionTag.setTitleWidth(tvStartAndEndTimeTag);
        tvSchoolTag.setTitleWidth(tvStartAndEndTimeTag);
        tvStartAndEndTimeTag.setTitleWidth(tvStartAndEndTimeTag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initDialog();
    }

    private void initDialog() {
        datePickerDegree=new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvEducation.setText(time);
                degreeId= ResumeInfoIDToString.getEducationDegreeId(HRApplication.getAppContext(),time);
            }
        },getResources().getStringArray(R.array.array_degree_zh));
        datePickerSE=new MyStartAndEndTimeCustomDatePicker(this, new MyStartAndEndTimeCustomDatePicker.ResultHandler() {

            @Override
            public void handle(String startTime, String endTime) {
                tvStartAndEndTime.setText(startTime+"  至  "+endTime);
               startTimes=startTime;
               endTimes=endTime;
            }
        });
    }

    @OnClick({R.id.iv_schoolDelete, R.id.rl_profession, R.id.rl_education, R.id.rl_startAndEndTime, R.id.btn_nextEdu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_schoolDelete:
                break;
            case R.id.rl_profession:
                break;
            case R.id.rl_education:
                datePickerDegree.show(tvEducation.getText().toString());
                break;
            case R.id.rl_startAndEndTime:
                datePickerSE.show(startTimes,endTimes);
                break;
            case R.id.btn_nextEdu:
                WorkExpActivity.startAction(this);
                //doSendEducation();
                break;
        }
    }

    private void doSendEducation() {
        if(etSchool.getText().toString()==null||"".equals(etSchool.getText().toString())){
            ToastUitl.showShort("请填写学校");
            return;
        }
        if("".equals(tvProfession.getText().toString())||tvProfession.getText().toString()==null){
            ToastUitl.showShort("请填写专业");
            return;
        }
        if(degreeId==null||"".equals(degreeId)){
            ToastUitl.showShort("请选择学历");
            return;
        }
        if("".equals(startTimes)||"".equals(endTimes)||endTimes==null||startTimes==null){
            ToastUitl.showShort("请选择起止时间");
        }
        EducationData educationData=new EducationData();
        educationData.setSchoolName(etSchool.getText().toString());
        educationData.setDegree(degreeId);
        educationData.setProfession(tvProfession.getText().toString());
        educationData.setStartTime(startTimes);
        educationData.setEndTime(endTimes);
        mPresenter.sendEducationToResume(educationData);
    }
}
