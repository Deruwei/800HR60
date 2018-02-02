package com.hr.ui.ui.resume.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.EducationBean;
import com.hr.ui.bean.EducationData;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.resume.contract.ResumeEducationContract;
import com.hr.ui.ui.resume.model.ResumeEducationModel;
import com.hr.ui.ui.resume.presenter.ResumeEducationPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyStartAndEndTimeCustomDatePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/29.
 */

public class ResumeEducationActivity extends BaseActivity<ResumeEducationPresenter, ResumeEducationModel> implements ResumeEducationContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_resumeEduBGSchoolTag)
    TextView tvResumeEduBGSchoolTag;
    @BindView(R.id.et_resumeEduBDSchool)
    EditText etResumeEduBDSchool;
    @BindView(R.id.iv_resumeEduBDSchoolDelete)
    ImageView ivResumeEduBDSchoolDelete;
    @BindView(R.id.tv_resumeEduBGProfessionTag)
    TextView tvResumeEduBGProfessionTag;
    @BindView(R.id.et_resumeEduBDProfession)
    EditText etResumeEduBDProfession;
    @BindView(R.id.iv_resumeEduBDProfessionDelete)
    ImageView ivResumeEduBDProfessionDelete;
    @BindView(R.id.tv_resumeEduBGStartEndTimeTag)
    TextView tvResumeEduBGStartEndTimeTag;
    @BindView(R.id.tv_resumeEduBDStartEndTime)
    TextView tvResumeEduBDStartEndTime;
    @BindView(R.id.iv_resumeEduBDStartEndTimeSelect)
    ImageView ivResumeEduBDStartEndTimeSelect;
    @BindView(R.id.tv_resumeEduBGEducationTag)
    TextView tvResumeEduBGEducationTag;
    @BindView(R.id.tv_resumeEduBDEducation)
    TextView tvResumeEduBDEducation;
    @BindView(R.id.iv_resumeEduBDEducationSelect)
    ImageView ivResumeEduBDEducationSelect;
    @BindView(R.id.btn_resumeSave)
    Button btnResumeSave;
    @BindView(R.id.tv_resumeEduBGDelete)
    TextView tvResumeEduBGDelete;
    public static ResumeEducationActivity instance;
    @BindView(R.id.cl_resumeEducation)
    ConstraintLayout clResumeEducation;
    @BindView(R.id.rl_resumeEduBDStartEndTime)
    RelativeLayout rlResumeEduBDStartEndTime;
    @BindView(R.id.rl_resumeEduBDEducation)
    RelativeLayout rlResumeEduBDEducation;
    private MyStartAndEndTimeCustomDatePicker datePickerTime;
    private CustomDatePicker datePickerDegree;
    private int num;
    private String startTimes,endTimes,degreeId,educationId;
    private SharedPreferencesUtils sUtils;
    private MyDialog dialog;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumeEducationActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,String educationId,int num) {
        Intent intent = new Intent(activity,ResumeEducationActivity.class);
        intent.putExtra("educationId",educationId);
        intent.putExtra("num",num);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
    public void getEducationInfoSuccess(EducationBean.EducationListBean  educationBean) {
        setUI(educationBean);
    }

    private void setUI(EducationBean.EducationListBean  educationBean) {
        tvResumeEduBDStartEndTime.setText(educationBean.getFromyear()+"-"+educationBean.getFrommonth()+"  至  "+educationBean.getToyear()+"-"+educationBean.getTomonth());
        startTimes=educationBean.getFromyear()+"-"+educationBean.getFrommonth();
        endTimes=educationBean.getToyear()+"-"+educationBean.getTomonth();
        tvResumeEduBDEducation.setText(ResumeInfoIDToString.getEducationDegree(this,educationBean.getDegree(),true));
        degreeId=educationBean.getDegree();
        etResumeEduBDSchool.setText(educationBean.getSchoolname());
        educationId=educationBean.getEducation_id();
        etResumeEduBDProfession.setText(educationBean.getMoremajor());
        ivResumeEduBDSchoolDelete.setVisibility(View.GONE);
        ivResumeEduBDProfessionDelete.setVisibility(View.GONE);
    }

    @Override
    public void addOrUpdateEducationSuccess() {
        sUtils.setBooleanValue(Constants.IS_FERSH,true);
        if(educationId==null||"".equals(educationId)){
            ToastUitl.showShort(R.string.addSuccess);
        }else{
            ToastUitl.showShort(R.string.saveSuccess);
        }

        finish();
    }

    @Override
    public void deleteEducationSuccess() {
        sUtils.setBooleanValue(Constants.IS_FERSH,true);
        ToastUitl.showShort(R.string.deleteSuccess);
        finish();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_resumeeducationbackground;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        instance = this;
        sUtils=new SharedPreferencesUtils(this);
        num=getIntent().getIntExtra("num",0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        educationId=getIntent().getStringExtra("educationId");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.educationBackground);
        ivResumeEduBDProfessionDelete.setVisibility(View.GONE);
        ivResumeEduBDSchoolDelete.setVisibility(View.GONE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(educationId!=null&&!"".equals(educationId)) {
            if(num>1) {
                mPresenter.getEducationInfo(educationId);
            }else{
                mPresenter.getEducationInfo(educationId);
                tvResumeEduBGDelete.setVisibility(View.GONE);
            }
        }else{
            tvResumeEduBGDelete.setVisibility(View.GONE);
        }
    }

    private void setFocus() {
        clResumeEducation.setFocusable(true);
        clResumeEducation.setFocusableInTouchMode(true);
        clResumeEducation.requestFocus();
        clResumeEducation.findFocus();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        textChangedListener();
        initDialog();
    }

    private void initDialog() {
        datePickerTime=new MyStartAndEndTimeCustomDatePicker(this, new MyStartAndEndTimeCustomDatePicker.ResultHandler() {
            @Override
            public void handle(String startTime, String endTime) {
                tvResumeEduBDStartEndTime.setText(startTime+"  至  "+endTime);
                startTimes=startTime;
                endTimes=endTime;
            }
        },1);
        datePickerDegree=new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvResumeEduBDEducation.setText(time);
                degreeId= ResumeInfoIDToString.getEducationDegreeId(ResumeEducationActivity.this,time);
            }
        },getResources().getStringArray(R.array.array_degree_zh));
    }

    private void textChangedListener() {
        etResumeEduBDProfession.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivResumeEduBDProfessionDelete.setVisibility(View.GONE);
                }else{
                    if(etResumeEduBDProfession.getText().toString()!=null&&!"".equals(etResumeEduBDProfession.getText().toString())){
                        ivResumeEduBDProfessionDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeEduBDProfession.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivResumeEduBDProfessionDelete.setVisibility(View.VISIBLE);
                }else{
                    ivResumeEduBDProfessionDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResumeEduBDSchool.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivResumeEduBDSchoolDelete.setVisibility(View.GONE);
                }else{
                    if(etResumeEduBDSchool.getText().toString()!=null&&!"".equals(etResumeEduBDSchool.getText().toString())){
                        ivResumeEduBDSchoolDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeEduBDSchool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivResumeEduBDSchoolDelete.setVisibility(View.VISIBLE);
                }else{
                    ivResumeEduBDSchoolDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_resumeEduBDSchoolDelete, R.id.iv_resumeEduBDProfessionDelete, R.id.rl_resumeEduBDStartEndTime, R.id.rl_resumeEduBDEducation, R.id.btn_resumeSave, R.id.tv_resumeEduBGDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_resumeEduBDSchoolDelete:
                etResumeEduBDSchool.setText("");
                break;
            case R.id.iv_resumeEduBDProfessionDelete:
                etResumeEduBDProfession.setText("");
                break;
            case R.id.rl_resumeEduBDStartEndTime:
                setFocus();
                datePickerTime.show(startTimes,endTimes);
                break;
            case R.id.rl_resumeEduBDEducation:
                setFocus();
                datePickerDegree.show(tvResumeEduBDEducation.getText().toString());
                break;
            case R.id.btn_resumeSave:
                doSaveOrUpdateEducation();
                break;
            case R.id.tv_resumeEduBGDelete:
               doDelete();
                break;
        }
    }
    private void doDelete() {
        dialog=new MyDialog(this,2);
        dialog.setMessage(getString(R.string.sureDeleteEdu));
        dialog.setYesOnclickListener(getString(R.string.sure), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                mPresenter.deleteEducation(educationId);
                dialog.dismiss();
            }
        });
        dialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            dialog.dismiss();
        }
    }
    private void doSaveOrUpdateEducation() {
        if(etResumeEduBDSchool.getText().toString()==null||"".equals(etResumeEduBDSchool.getText().toString())){
            ToastUitl.showShort("请填写学校");
            return;
        }
        if(etResumeEduBDProfession.getText().toString()==null||"".equals(etResumeEduBDProfession.getText().toString())){
            ToastUitl.showShort("请填写专业");
            return;
        }
        if(tvResumeEduBDStartEndTime.getText().toString()==null||"".equals(tvResumeEduBDStartEndTime.getText().toString())){
            ToastUitl.showShort("请选择起止时间");
            return;
        }
        if("".equals(degreeId)||degreeId==null){
            ToastUitl.showShort("请选择学历");
            return;
        }
        EducationData educationData=new EducationData();
        if(educationId!=null&&!"".equals(educationId)) {
            educationData.setEducationId(educationId);
        }
        educationData.setStartTime(startTimes);
        if("至今".equals(endTimes)){
            endTimes="0-0";
        }
        educationData.setEndTime(endTimes);
        educationData.setProfession(etResumeEduBDProfession.getText().toString());
        educationData.setSchoolName(etResumeEduBDSchool.getText().toString());
        educationData.setDegree(degreeId);
        mPresenter.addOrUpdateEducation(educationData);
    }
}
