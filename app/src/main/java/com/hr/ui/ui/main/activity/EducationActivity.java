package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.EducationData;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.EducationContract;
import com.hr.ui.ui.main.modle.EducationModel;
import com.hr.ui.ui.main.presenter.EducationPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyDialog;
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
    @BindView(R.id.btn_nextEdu)
    Button btnNextEdu;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.iv_schoolDelete)
    ImageView ivSchoolDelete;
    @BindView(R.id.rl_profession)
    RelativeLayout rlProfession;
    @BindView(R.id.rl_education)
    RelativeLayout rlEducation;
    @BindView(R.id.rl_startAndEndTime)
    RelativeLayout rlStartAndEndTime;
    @BindView(R.id.cl_education)
    ConstraintLayout clEducation;
    private String degreeId, startTimes = "", endTimes = "";
    private CustomDatePicker datePickerDegree;
    private MyStartAndEndTimeCustomDatePicker datePickerSE;
    private SharedPreferencesUtils sUtils;
    private int stopType,startType;
    private MyDialog myDialog;

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
        if (stopType == 2) {
            MainActivity.startAction(this, 0);
            AppManager.getAppManager().finishAllActivity();
        } else {
            WorkExpActivity.startAction(this);
        }
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
        sUtils = new SharedPreferencesUtils(HRApplication.getAppContext());
        stopType = sUtils.getIntValue(Constants.RESUME_STOPTYPE, 0);
        startType=sUtils.getIntValue(Constants.RESUME_STARTTYPE,0);
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
                exitOrFinishActivity();
            }
        });
        tvEducationTag.setTitleWidth(tvStartAndEndTimeTag);
        tvProfessionTag.setTitleWidth(tvStartAndEndTimeTag);
        tvSchoolTag.setTitleWidth(tvStartAndEndTimeTag);
        tvStartAndEndTimeTag.setTitleWidth(tvStartAndEndTimeTag);
        if (stopType == 2) {
            btnNextEdu.setText(R.string.complete);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initDialog();
        initListener();
    }

    private void initListener() {
        ivSchoolDelete.setVisibility(View.GONE);
        tvProfessionSelect.setVisibility(View.GONE);
        etSchool.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    ivSchoolDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivSchoolDelete.setVisibility(View.VISIBLE);
                } else {
                    ivSchoolDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvProfession.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvProfessionSelect.setVisibility(View.VISIBLE);
                } else {
                    tvProfessionSelect.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvEducation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvEducationSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    tvEducationSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvStartAndEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvStartAndEndTimeSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    tvStartAndEndTimeSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvProfession.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    tvProfessionSelect.setVisibility(View.GONE);
                }else{
                    if(tvProfession.getText().toString()!=null&&!"".equals(tvProfession.getText().toString())){
                        tvProfessionSelect.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etSchool.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    ivSchoolDelete.setVisibility(View.GONE);
                }else{
                    if(etSchool.getText().toString()!=null&&!"".equals(etSchool.getText().toString())){
                        ivSchoolDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void initDialog() {
        datePickerDegree = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvEducation.setText(time);
                degreeId = ResumeInfoIDToString.getEducationDegreeId(HRApplication.getAppContext(), time);
            }
        }, getResources().getStringArray(R.array.array_degree_zh));
        datePickerSE = new MyStartAndEndTimeCustomDatePicker(this, new MyStartAndEndTimeCustomDatePicker.ResultHandler() {

            @Override
            public void handle(String startTime, String endTime) {
                tvStartAndEndTime.setText(startTime + "  至  " + endTime);
                startTimes = startTime;
                endTimes = endTime;
            }
        });
    }

    private void setFocus() {
        clEducation.setFocusableInTouchMode(true);
        clEducation.setFocusable(true);
        clEducation.requestFocus();
        clEducation.findFocus();
    }

    @OnClick({R.id.iv_schoolDelete, R.id.tv_professionSelect, R.id.rl_education, R.id.rl_startAndEndTime, R.id.btn_nextEdu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_schoolDelete:
                etSchool.setText("");
                break;
            case R.id.tv_professionSelect:
                tvProfession.setText("");
                break;
            case R.id.rl_education:
                setFocus();
                datePickerDegree.show(tvEducation.getText().toString());
                break;
            case R.id.rl_startAndEndTime:
                setFocus();
                datePickerSE.show(startTimes, endTimes);
                break;
            case R.id.btn_nextEdu:
                //WorkExpActivity.startAction(this);
                doSendEducation();
                break;
        }
    }

    private void doSendEducation() {
        if (etSchool.getText().toString() == null || "".equals(etSchool.getText().toString())) {
            ToastUitl.showShort("请填写学校");
            return;
        }
        if ("".equals(tvProfession.getText().toString()) || tvProfession.getText().toString() == null) {
            ToastUitl.showShort("请填写专业");
            return;
        }
        if (degreeId == null || "".equals(degreeId)) {
            ToastUitl.showShort("请选择学历");
            return;
        }
        if ("".equals(startTimes) || "".equals(endTimes) || endTimes == null || startTimes == null) {
            ToastUitl.showShort("请选择起止时间");
        }
        EducationData educationData = new EducationData();
        educationData.setSchoolName(etSchool.getText().toString());
        educationData.setDegree(degreeId);
        educationData.setProfession(tvProfession.getText().toString());
        educationData.setStartTime(startTimes);
        educationData.setEndTime(endTimes);
        mPresenter.sendEducationToResume(educationData);
    }

    private void exitOrFinishActivity() {
        if (startType == 2) {
            myDialog = new MyDialog(this, 2);
            myDialog.setMessage(getString(R.string.exitWarning));
            myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    myDialog.dismiss();
                    SplashActivity.startAction(EducationActivity.this,1);
                    SharedPreferencesUtils sUtils = new SharedPreferencesUtils(HRApplication.getAppContext());
                    sUtils.setIntValue(Constants.ISAUTOLOGIN, 0);
                    AppManager.getAppManager().finishAllActivity();
                }
            });
            myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    myDialog.dismiss();
                }
            });
            myDialog.show();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            exitOrFinishActivity();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
