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
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.WorkExpBean;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.activity.ContentActivity;
import com.hr.ui.ui.main.activity.SelectCityActivity;
import com.hr.ui.ui.main.activity.WorkExpActivity;
import com.hr.ui.ui.resume.contract.ResumeWorkExpContract;
import com.hr.ui.ui.resume.model.ResumeWorkExpModel;
import com.hr.ui.ui.resume.presenter.ResumeWorkExpPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.ToolUtils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyStartAndEndTimeCustomDatePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/29.
 */

public class ResumeWorkExpActivity extends BaseActivity<ResumeWorkExpPresenter, ResumeWorkExpModel> implements ResumeWorkExpContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_resumeWorkExpCompanyTag)
    TextView tvResumeWorkExpCompanyTag;
    @BindView(R.id.et_resumeWorkExpCompany)
    EditText etResumeWorkExpCompany;
    @BindView(R.id.iv_resumeWorkExpCompanyDelete)
    ImageView ivResumeWorkExpCompanyDelete;
    @BindView(R.id.tv_resumeWorkExpPosition)
    TextView tvResumeWorkExpPosition;
    @BindView(R.id.et_resumeWorkExpPosition)
    EditText etResumeWorkExpPosition;
    @BindView(R.id.iv_resumeWorkExpPositionDelete)
    ImageView ivResumeWorkExpPositionDelete;
    @BindView(R.id.tv_resumeWorkExpStartAndTimeTag)
    TextView tvResumeWorkExpStartAndTimeTag;
    @BindView(R.id.tv_resumeWorkExpStartAndTime)
    TextView tvResumeWorkExpStartAndTime;
    @BindView(R.id.iv_resumeWorkExpStartAndTimeSelect)
    ImageView ivResumeWorkExpStartAndTimeSelect;
    @BindView(R.id.tv_resumeWorkExpJobDescriptionTag)
    TextView tvResumeWorkExpJobDescriptionTag;
    @BindView(R.id.tv_resumeWorkExpJobDescription)
    TextView tvResumeWorkExpJobDescription;
    @BindView(R.id.iv_resumeWorkExpJobDescriptionDelete)
    ImageView ivResumeWorkExpJobDescriptionDelete;
    @BindView(R.id.tv_resumeWorkExpWorkPlaceTag)
    TextView tvResumeWorkExpWorkPlaceTag;
    @BindView(R.id.tv_resumeWorkExpWorkPlace)
    TextView tvResumeWorkExpWorkPlace;
    @BindView(R.id.iv_resumeWorkExpWorkPlaceDelete)
    ImageView ivResumeWorkExpWorkPlaceDelete;
    @BindView(R.id.tv_resumeWorkExpGrossPayTag)
    TextView tvResumeWorkExpGrossPayTag;
    @BindView(R.id.tv_resumeWorkExpGrossPay)
    TextView tvResumeWorkExpGrossPay;
    @BindView(R.id.iv_resumeWorkExpGrossPaySelect)
    ImageView ivResumeWorkExpGrossPaySelect;
    @BindView(R.id.btn_resumeWorkExpOK)
    Button btnResumeWorkExpOK;
    @BindView(R.id.tv_resumeWorkExpDelete)
    TextView tvResumeWorkExpDelete;
    @BindView(R.id.cl_workExp)
    ConstraintLayout clWorkExp;
    @BindView(R.id.rl_resumeWorkExpStartAndTime)
    RelativeLayout rlResumeWorkExpStartAndTime;
    @BindView(R.id.rl_resumeWorkExpJobDescription)
    RelativeLayout rlResumeWorkExpJobDescription;
    @BindView(R.id.rl_resumeWorkExpWorkPlace)
    RelativeLayout rlResumeWorkExpWorkPlace;
    @BindView(R.id.rl_resumeWorkExpGrossPay)
    RelativeLayout rlResumeWorkExpGrossPay;
    private String resumeType;
    public static String TAG=ResumeWorkExpActivity.class.getSimpleName();
    private String experienceId,startTimes,endTimes,cityid;
    private MyStartAndEndTimeCustomDatePicker datePickerTime;
    public static ResumeWorkExpActivity instance;
    private SharedPreferencesUtils sUtils;
    private int num=0;
    private MyDialog dialog;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumeWorkExpActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,String experienceId,int num) {
        Intent intent = new Intent(activity,ResumeWorkExpActivity.class);
        intent.putExtra("experienceId",experienceId);
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
    public void getWorkExpinfo(WorkExpBean.ExperienceListBean workExpBean) {
        setUI(workExpBean);
    }

    private void setUI(WorkExpBean.ExperienceListBean workExpBean) {
        etResumeWorkExpPosition.setText(workExpBean.getPosition());
        etResumeWorkExpCompany.setText(workExpBean.getCompany());
        startTimes=workExpBean.getFromyear()+"-"+workExpBean.getFrommonth();
        endTimes=workExpBean.getToyear()+"-"+workExpBean.getTomonth();
        if("0-0".equals(endTimes)){
            endTimes="至今";
        }
        tvResumeWorkExpStartAndTime.setText(startTimes+"  至  "+endTimes );
        tvResumeWorkExpGrossPay.setText(workExpBean.getSalary());
        tvResumeWorkExpJobDescription.setText(workExpBean.getResponsiblity());
        tvResumeWorkExpWorkPlace.setText(FromStringToArrayList.getInstance().getCityListName(workExpBean.getCompanyaddress()));
        cityid=workExpBean.getCompanyaddress();
        ivResumeWorkExpPositionDelete.setVisibility(View.GONE);
        ivResumeWorkExpCompanyDelete.setVisibility(View.GONE);
        ivResumeWorkExpGrossPaySelect.setVisibility(View.GONE);
    }

    @Override
    public void deleteSuccess() {
        ToastUitl.showShort(R.string.deleteSuccess);
        sUtils.setBooleanValue(Constants.IS_FERSH,true);
        finish();
    }

    @Override
    public void addOrUpdateWorkExp() {
        ToastUitl.showShort(R.string.saveSuccess);
        sUtils.setBooleanValue(Constants.IS_FERSH,true);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resumeworkexp;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        instance=this;
        setSupportActionBar(toolBar);
        sUtils=new SharedPreferencesUtils(this);
        num=getIntent().getIntExtra("num",0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        experienceId = getIntent().getStringExtra("experienceId");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        resumeType=sUtils.getStringValue(Constants.RESUME_TYPE,"");
        if("1".equals(resumeType)) {
            tvToolbarTitle.setText(R.string.workExp);
            tvResumeWorkExpWorkPlaceTag.setText(R.string.workPlace);
        }else{
            tvToolbarTitle.setText(R.string.internshipExperience);
            tvResumeWorkExpWorkPlaceTag.setText(R.string.internshipPlace);
        }
        ivResumeWorkExpCompanyDelete.setVisibility(View.GONE);
        ivResumeWorkExpPositionDelete.setVisibility(View.GONE);
        ivResumeWorkExpGrossPaySelect.setVisibility(View.GONE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(experienceId!=null&&!"".equals(experienceId)){
            if(num>1) {
                mPresenter.getWorkExpInfo(experienceId);
            }else{
                mPresenter.getWorkExpInfo(experienceId);
                tvResumeWorkExpDelete.setVisibility(View.GONE);
            }
        }
        else{
            tvResumeWorkExpDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initDialog();
        initTextChange();
    }

    private void initTextChange() {
        etResumeWorkExpCompany.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeWorkExpCompanyDelete.setVisibility(View.GONE);
                } else {
                    if (etResumeWorkExpCompany.getText().toString() != null && !"".equals(etResumeWorkExpCompany.getText().toString())) {
                        ivResumeWorkExpCompanyDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tvResumeWorkExpGrossPay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivResumeWorkExpGrossPaySelect.setVisibility(View.GONE);
                }else{
                    if(tvResumeWorkExpGrossPay.getText().toString()!=null&&!"".equals(tvResumeWorkExpGrossPay.getText().toString())){
                        ivResumeWorkExpGrossPaySelect.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeWorkExpPosition.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeWorkExpPositionDelete.setVisibility(View.GONE);
                } else {
                    if (etResumeWorkExpPosition.getText().toString() != null && !"".equals(etResumeWorkExpPosition.getText().toString())) {
                        ivResumeWorkExpPositionDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeWorkExpPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeWorkExpPositionDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeWorkExpPositionDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResumeWorkExpCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeWorkExpCompanyDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeWorkExpCompanyDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvResumeWorkExpGrossPay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    ivResumeWorkExpGrossPaySelect.setVisibility(View.VISIBLE);
                }else{
                    ivResumeWorkExpGrossPaySelect.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initDialog() {
        datePickerTime=new MyStartAndEndTimeCustomDatePicker(this, new MyStartAndEndTimeCustomDatePicker.ResultHandler() {
            @Override
            public void handle(String startTime, String endTime) {
                startTimes=startTime;
                endTimes=endTime;
                tvResumeWorkExpStartAndTime.setText(startTime+"  至  "+endTime);
            }
        },2);
    }

    @OnClick({R.id.iv_resumeWorkExpCompanyDelete,R.id.iv_resumeWorkExpGrossPaySelect, R.id.iv_resumeWorkExpPositionDelete, R.id.rl_resumeWorkExpStartAndTime, R.id.rl_resumeWorkExpJobDescription, R.id.rl_resumeWorkExpWorkPlace, R.id.rl_resumeWorkExpGrossPay, R.id.btn_resumeWorkExpOK, R.id.tv_resumeWorkExpDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_resumeWorkExpCompanyDelete:
                etResumeWorkExpCompany.setText("");
                break;
            case R.id.iv_resumeWorkExpPositionDelete:
                etResumeWorkExpPosition.setText("");
                break;
            case R.id.rl_resumeWorkExpStartAndTime:
                setFocus();
                datePickerTime.show(startTimes,endTimes);
                break;
            case R.id.rl_resumeWorkExpJobDescription:
                setFocus();
                if ("".equals(tvResumeWorkExpJobDescription.getText().toString()) || tvResumeWorkExpJobDescription.getText().toString() == null) {
                    ContentActivity.startAction(this,TAG);
                } else {
                    ContentActivity.startAction(this, tvResumeWorkExpJobDescription.getText().toString(),TAG);
                }
                break;
            case R.id.rl_resumeWorkExpWorkPlace:
                setFocus();
                SelectCityActivity.startAction(this,1,TAG);
                break;
            case R.id.iv_resumeWorkExpGrossPaySelect:
                tvResumeWorkExpGrossPay.setText("");
                break;
            case R.id.btn_resumeWorkExpOK:
                doAddOrPlaceWorkExp();
                break;
            case R.id.tv_resumeWorkExpDelete:
                doDelete();
                break;
        }
    }

    private void doDelete() {
        dialog=new MyDialog(this,2);
        if("1".equals(resumeType)) {
            dialog.setMessage(getString(R.string.sureDeleteWorkExp));
        }else{
            dialog.setMessage(getString(R.string.sureDeleteInternShipExp));
        }
        dialog.setYesOnclickListener(getString(R.string.sure), new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                mPresenter.deleteWorkExp(experienceId);
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

    public void setTvResponsibilityDes(String content) {
        tvResumeWorkExpJobDescription.setText(content);
    }
    private void doAddOrPlaceWorkExp() {
        if(etResumeWorkExpCompany.getText().toString()==null||"".equals(etResumeWorkExpCompany.getText().toString())){
            ToastUitl.showShort("请填写公司名称");
            return;
        }
        if(etResumeWorkExpPosition.getText().toString()==null||"".equals(etResumeWorkExpPosition.getText().toString())){
            ToastUitl.showShort("请填写所任职位");
            return;
        }
        if("".equals(tvResumeWorkExpStartAndTime.getText().toString())||tvResumeWorkExpStartAndTime.getText().toString()==null){
            ToastUitl.showShort("请选择起止时间");
            return;
        }
        if("".equals(tvResumeWorkExpJobDescription.getText().toString())||tvResumeWorkExpJobDescription.getText().toString()==null){
            ToastUitl.showShort("请填写职责描述");
            return;
        }
        if(cityid==null||"".equals(cityid)){
            if("1".equals(resumeType)) {
                ToastUitl.showShort("请选择工作地点");
            }else{
                ToastUitl.showShort("请选择实习地点");
            }
            return;
        }
        if("".equals(tvResumeWorkExpGrossPay.getText().toString())||tvResumeWorkExpGrossPay.getText().toString()==null){
            ToastUitl.showShort("请填写税前月薪");
            return;
        }
        if(Integer.parseInt(tvResumeWorkExpGrossPay.getText().toString())==0){
            ToastUitl.showShort("税前月薪必须大于0");
            return;
        }
        WorkExpData workExpData=new WorkExpData();
        if(!"".equals(experienceId)&&experienceId!=null) {
            workExpData.setExperienceId(experienceId);
        }
        workExpData.setResponsibilityDescription(tvResumeWorkExpJobDescription.getText().toString());
        if("至今".equals(endTimes)){
            workExpData.setEndTime("0-0");
        }else {
            workExpData.setEndTime(endTimes);
        }
        workExpData.setStartTime(startTimes);
        workExpData.setGrossPay(tvResumeWorkExpGrossPay.getText().toString());
        workExpData.setWorkPlace(cityid);
        workExpData.setCompany(etResumeWorkExpCompany.getText().toString());
        workExpData.setPosition(etResumeWorkExpPosition.getText().toString());
        mPresenter.addOrUpdateWorkExp(workExpData);
    }
    private void setFocus() {
        clWorkExp.setFocusable(true);
        clWorkExp.setFocusableInTouchMode(true);
        clWorkExp.requestFocus();
        clWorkExp.findFocus();
    }
    public void setSelectCity(CityBean cityBean) {
        if(cityBean!=null) {
            if (tvResumeWorkExpWorkPlace != null) {
                tvResumeWorkExpWorkPlace.setText(cityBean.getName());
            }
            cityid = cityBean.getId();
        }
    }
}
