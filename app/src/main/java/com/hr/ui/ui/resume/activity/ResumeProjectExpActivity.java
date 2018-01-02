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
import com.hr.ui.bean.ProjectBean;
import com.hr.ui.bean.ProjectExpData;
import com.hr.ui.ui.main.activity.ContentActivity;
import com.hr.ui.ui.resume.contract.ResumeProjectExpContract;
import com.hr.ui.ui.resume.model.ResumeProjectExpModel;
import com.hr.ui.ui.resume.presenter.ResumeProjectExpPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.MyStartAndEndTimeCustomDatePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/29.
 */

public class ResumeProjectExpActivity extends BaseActivity<ResumeProjectExpPresenter, ResumeProjectExpModel> implements ResumeProjectExpContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_resumeProjectExpNameTag)
    TextView tvResumeProjectExpNameTag;
    @BindView(R.id.et_resumeProjectExpName)
    EditText etResumeProjectExpName;
    @BindView(R.id.iv_resumeProjectExpNameDelete)
    ImageView ivResumeProjectExpNameDelete;
    @BindView(R.id.tv_resumeProjectExpPositionTag)
    TextView tvResumeProjectExpPositionTag;
    @BindView(R.id.et_resumeProjectExpPosition)
    EditText etResumeProjectExpPosition;
    @BindView(R.id.iv_resumeProjectExpPositionDelete)
    ImageView ivResumeProjectExpPositionDelete;
    @BindView(R.id.tv_resumeProjectExpTimeTag)
    TextView tvResumeProjectExpTimeTag;
    @BindView(R.id.tv_resumeProjectExpTime)
    TextView tvResumeProjectExpTime;
    @BindView(R.id.iv_resumeProjectExpTimeSelect)
    ImageView ivResumeProjectExpTimeSelect;
    @BindView(R.id.tv_resumeProjectExpDesTag)
    TextView tvResumeProjectExpDesTag;
    @BindView(R.id.tv_resumeProjectExpDes)
    TextView tvResumeProjectExpDes;
    @BindView(R.id.iv_resumeProjectExpDesSelect)
    ImageView ivResumeProjectExpDesSelect;
    @BindView(R.id.tv_resumeProjectExpResponsibilityTag)
    TextView tvResumeProjectExpResponsibilityTag;
    @BindView(R.id.tv_resumeProjectExpResponsibility)
    TextView tvResumeProjectExpResponsibility;
    @BindView(R.id.iv_resumeProjectExpResponsibilitySelect)
    ImageView ivResumeProjectExpResponsibilitySelect;
    @BindView(R.id.btn_resumeProjectExpOK)
    Button btnResumeProjectExpOK;
    @BindView(R.id.tv_resumeProjectExpDelete)
    TextView tvResumeProjectExpDelete;
    @BindView(R.id.rl_resumeProjectExpTime)
    RelativeLayout rlResumeProjectExpTime;
    @BindView(R.id.rl_resumeProjectExpDes)
    RelativeLayout rlResumeProjectExpDes;
    @BindView(R.id.rl_resumeProjectExpResponsibility)
    RelativeLayout rlResumeProjectExpResponsibility;
    @BindView(R.id.cl_projectExp)
    ConstraintLayout clProjectExp;
    private String startTimes, endTimes;
    private MyStartAndEndTimeCustomDatePicker datePickerTime;
    private String projectId;
    public static ResumeProjectExpActivity instance;
    public static String TAG=ResumeProjectExpActivity.class.getSimpleName();
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumeProjectExpActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,String projectId) {
        Intent intent = new Intent(activity,ResumeProjectExpActivity.class);
        intent.putExtra("projectId",projectId);
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
    public void getProjectInfoSuccess(ProjectBean.ProjectListBean projectBean) {
        initUI(projectBean);
    }

    private void initUI(ProjectBean.ProjectListBean projectBean) {
        etResumeProjectExpPosition.setText(projectBean.getPosition());
        etResumeProjectExpName.setText(projectBean.getProjectname());
        tvResumeProjectExpDes.setText(projectBean.getProjectdesc());
        tvResumeProjectExpTime.setText(projectBean.getFromyear()+"-"+projectBean.getFrommonth()+"  至  "+projectBean.getToyear()+"-"+projectBean.getTomonth());
        startTimes=projectBean.getFromyear()+"-"+projectBean.getFrommonth();
        endTimes=projectBean.getToyear()+"-"+projectBean.getTomonth();
        tvResumeProjectExpResponsibility.setText(projectBean.getResponsibility());
        ivResumeProjectExpNameDelete.setVisibility(View.GONE);
        ivResumeProjectExpPositionDelete.setVisibility(View.GONE);
    }

    @Override
    public void deleteProjectSuccess() {
        ToastUitl.showShort(R.string.deleteSuccess);
    }

    @Override
    public void addOrUpdateProjectInfo() {
        ToastUitl.showShort(R.string.saveSuccess);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resumeprojectexp;
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
        projectId=getIntent().getStringExtra("projectId");
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.projectExp);
        instance=this;
        ivResumeProjectExpNameDelete.setVisibility(View.GONE);
        ivResumeProjectExpPositionDelete.setVisibility(View.GONE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(!"".equals(projectId)&&projectId!=null){
            mPresenter.getProjectInfo(projectId);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        textChanged();
        initDialog();
    }

    private void initDialog() {
        datePickerTime = new MyStartAndEndTimeCustomDatePicker(this, new MyStartAndEndTimeCustomDatePicker.ResultHandler() {
            @Override
            public void handle(String startTime, String endTime) {
                startTimes = startTime;
                endTimes = endTime;
                tvResumeProjectExpTime.setText(startTimes + "  至  " + endTimes);
            }
        });
    }

    private void textChanged() {
        etResumeProjectExpName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeProjectExpNameDelete.setVisibility(View.GONE);
                } else {
                    if (etResumeProjectExpName.getText().toString() != null && !"".equals(etResumeProjectExpName.getText().toString())) {
                        ivResumeProjectExpNameDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeProjectExpName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeProjectExpNameDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeProjectExpNameDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResumeProjectExpPosition.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeProjectExpPositionDelete.setVisibility(View.GONE);
                } else {
                    if (etResumeProjectExpPosition.getText().toString() != null && !"".equals(etResumeProjectExpPosition.getText().toString())) {
                        ivResumeProjectExpPositionDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeProjectExpPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeProjectExpPositionDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeProjectExpPositionDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_resumeProjectExpNameDelete, R.id.iv_resumeProjectExpPositionDelete, R.id.rl_resumeProjectExpTime, R.id.rl_resumeProjectExpDes, R.id.rl_resumeProjectExpResponsibility, R.id.btn_resumeProjectExpOK, R.id.tv_resumeProjectExpDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_resumeProjectExpNameDelete:
                etResumeProjectExpName.setText("");
                break;
            case R.id.iv_resumeProjectExpPositionDelete:
                etResumeProjectExpPosition.setText("");
                break;
            case R.id.rl_resumeProjectExpTime:
                setFocus();
                datePickerTime.show(startTimes,endTimes);
                break;
            case R.id.rl_resumeProjectExpDes:
                setFocus();
                if(tvResumeProjectExpDes.getText().toString()==null||"".equals(tvResumeProjectExpDes.getText().toString())) {
                    ProjectContentActivity.startAction(this, "1");
                }else{
                    ProjectContentActivity.startAction(this,tvResumeProjectExpDes.getText().toString(),"1");
                }
                break;
            case R.id.rl_resumeProjectExpResponsibility:
                if(tvResumeProjectExpResponsibility.getText().toString()==null||"".equals(tvResumeProjectExpResponsibility.getText().toString())) {
                    ProjectContentActivity.startAction(this, "2");
                }else{
                    ProjectContentActivity.startAction(this,tvResumeProjectExpResponsibility.getText().toString(),"2");
                }
                setFocus();
                break;
            case R.id.btn_resumeProjectExpOK:
                doAddOrReplaceProjectExp();
                break;
            case R.id.tv_resumeProjectExpDelete:
                mPresenter.deleteProjectInfo(projectId);
                break;
        }
    }

    private void doAddOrReplaceProjectExp() {
        if("".equals(etResumeProjectExpName.getText().toString())&&etResumeProjectExpName.getText().toString()==null){
            ToastUitl.showShort("请填写项目名称");
            return;
        }
        if("".equals(etResumeProjectExpPosition.getText().toString())||etResumeProjectExpPosition.getText().toString()==null){
            ToastUitl.showShort("请填写项目职务");
            return;
        }
        if("".equals(tvResumeProjectExpTime.getText().toString())||tvResumeProjectExpTime.getText().toString()==null){
            ToastUitl.showShort("请选择项目时间");
            return;
        }
        if("".equals(tvResumeProjectExpDes.getText().toString())||tvResumeProjectExpDes.getText().toString()==null){
            ToastUitl.showShort("请填写项目描述");
            return;
        }
        if("".equals(tvResumeProjectExpResponsibility.getText().toString())||tvResumeProjectExpResponsibility.getText().toString()==null){
            ToastUitl.showShort("请填写项目职责");
            return;
        }
        ProjectExpData projectExpData=new ProjectExpData();
        if(projectId!=null&&!"".equals(projectExpData)) {
            projectExpData.setProjectId(projectId);
        }
        projectExpData.setEndTime(endTimes);
        projectExpData.setStartTime(startTimes);
        projectExpData.setProjectPosition(etResumeProjectExpPosition.getText().toString());
        projectExpData.setProjectDes(tvResumeProjectExpDes.getText().toString());
        projectExpData.setProjectName(etResumeProjectExpName.getText().toString());
        projectExpData.setProjectResponsibility(tvResumeProjectExpResponsibility.getText().toString());
        mPresenter.addOrUpdateProjectInfo(projectExpData);
    }

    private void setFocus() {
        clProjectExp.setFocusable(true);
        clProjectExp.setFocusableInTouchMode(true);
        clProjectExp.requestFocus();
        clProjectExp.findFocus();
    }
    public void setDes(String content){
        tvResumeProjectExpDes.setText(content);
    }
    public void setRes(String content){
        tvResumeProjectExpResponsibility.setText(content);
    }
}
