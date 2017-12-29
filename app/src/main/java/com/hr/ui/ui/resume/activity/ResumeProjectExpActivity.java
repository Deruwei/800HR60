package com.hr.ui.ui.resume.activity;

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
import com.hr.ui.ui.resume.contract.ResumeProjectExpContract;
import com.hr.ui.ui.resume.model.ResumeProjectExpModel;
import com.hr.ui.ui.resume.presenter.ResumeProjectExpPresenter;
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
    @BindView(R.id.tv_resumeProjectExpName)
    TextView tvResumeProjectExpName;
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
    public void getProjectInfoSuccess() {

    }

    @Override
    public void deleteProjectSuccess() {

    }

    @Override
    public void addOrUpdateProjectInfo() {

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
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.projectExp);
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
                break;
            case R.id.rl_resumeProjectExpDes:
                setFocus();
                break;
            case R.id.rl_resumeProjectExpResponsibility:
                setFocus();
                break;
            case R.id.btn_resumeProjectExpOK:
                break;
            case R.id.tv_resumeProjectExpDelete:
                mPresenter.deleteProjectInfo(projectId);
                break;
        }
    }
    private void setFocus() {
        clProjectExp.setFocusable(true);
        clProjectExp.setFocusableInTouchMode(true);
        clProjectExp.requestFocus();
        clProjectExp.findFocus();
    }

}
