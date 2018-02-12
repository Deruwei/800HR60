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
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.WorkExpContract;
import com.hr.ui.ui.main.modle.WorkExpModel;
import com.hr.ui.ui.main.presenter.WorkExpPresenter;
import com.hr.ui.utils.ClickUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyStartAndEndTimeCustomDatePicker;
import com.hr.ui.view.MyTextView;
import com.umeng.analytics.MobclickAgent;

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
    @BindView(R.id.tv_workExpTitle)
    TextView tvWorkExpTitle;
    @BindView(R.id.btn_nextEdu)
    Button btnNextEdu;
    @BindView(R.id.iv_companyNameDelete)
    ImageView ivCompanyNameDelete;
    @BindView(R.id.iv_grossPayDelete)
    ImageView ivGrossPayDelete;
    @BindView(R.id.rl_workExpStartAndEndTime)
    RelativeLayout rlWorkExpStartAndEndTime;
    @BindView(R.id.rl_responsibilityDes)
    RelativeLayout rlResponsibilityDes;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.rl_position)
    RelativeLayout rlPosition;
    @BindView(R.id.cl_workExp)
    ConstraintLayout clWorkExp;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_noInternshipExp)
    TextView tvNoInternshipExp;
    private String expId;
    private String endTimes, startTimes, cityId, responbilityDes;
    private MyStartAndEndTimeCustomDatePicker datePickerSE;
    public static final String TAG = WorkExpActivity.class.getSimpleName();
    public static WorkExpActivity instance;
    private String type;//简历类型
    private SharedPreferencesUtils sUtis;
    private MyDialog myDialog;
    private int stopType, startType;

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
    public int getLayoutId() {
        return R.layout.acitvity_workexperience;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sUtis = new SharedPreferencesUtils(this);
        type = sUtis.getStringValue(Constants.RESUME_TYPE, "");
        stopType = sUtis.getIntValue(Constants.RESUME_STOPTYPE, 0);
        startType = sUtis.getIntValue(Constants.RESUME_STARTTYPE, 0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        if (!"".equals(type) && type != null) {
            if ("1".equals(type)) {
                tvWorkExpTitle.setText(R.string.workExperience);
                tvWorkPlaceTag.setText(R.string.workPlace);
                tvNoInternshipExp.setVisibility(View.GONE);
            } else if ("2".equals(type)) {
                tvWorkExpTitle.setText(R.string.internshipExperience);
                tvWorkPlaceTag.setText(R.string.internshipPlace);
            }
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitOrFinishActivity();
            }
        });
        tvCompanyNameTag.setTitleWidth(tvCompanyNameTag);
        tvPositionTag.setTitleWidth(tvCompanyNameTag);
        tvGrossPayTag.setTitleWidth(tvCompanyNameTag);
        tvResponsibilityDesTag.setTitleWidth(tvCompanyNameTag);
        tvWorkPlaceTag.setTitleWidth(tvCompanyNameTag);
        tvWorkExpStartAndEndTimeTag.setTitleWidth(tvCompanyNameTag);
        if (stopType == 3) {
            btnNextEdu.setText(R.string.complete);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance = this;
        initDialog();
        initListener();
    }

    private void initListener() {
        tvPositionSelect.setVisibility(View.GONE);
        ivCompanyNameDelete.setVisibility(View.GONE);
        ivGrossPayDelete.setVisibility(View.GONE);
        etCompanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivCompanyNameDelete.setVisibility(View.VISIBLE);
                } else {
                    ivCompanyNameDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etGrossPay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivGrossPayDelete.setVisibility(View.VISIBLE);
                } else {
                    ivGrossPayDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvPositionSelect.setVisibility(View.VISIBLE);
                } else {
                    tvPositionSelect.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvWorkExpStartAndEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvWorkExpStartAndEndTimeSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    tvWorkExpStartAndEndTimeSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvWorkPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvWorkPlaceSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    tvWorkPlaceSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvResponsibilityDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvResponsibilityDesSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    tvResponsibilityDesSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etGrossPay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivGrossPayDelete.setVisibility(View.GONE);
                } else {
                    if (etGrossPay.getText().toString() != null && !"".equals(etGrossPay.getText().toString())) {
                        ivGrossPayDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etCompanyName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivCompanyNameDelete.setVisibility(View.GONE);
                } else {
                    if (etCompanyName.getText().toString() != null && !"".equals(etCompanyName.getText().toString())) {
                        ivCompanyNameDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tvPosition.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    tvPositionSelect.setVisibility(View.GONE);
                } else {
                    if (tvPosition.getText().toString() != null && !"".equals(tvPosition.getText().toString())) {
                        tvPositionSelect.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void initDialog() {
        datePickerSE = new MyStartAndEndTimeCustomDatePicker(this, new MyStartAndEndTimeCustomDatePicker.ResultHandler() {
            @Override
            public void handle(String startTime, String endTime) {
                tvWorkExpStartAndEndTime.setText(startTime + "  至  " + endTime);
                startTimes = startTime;
                endTimes = endTime;
            }
        },2);
    }

    @OnClick({R.id.iv_companyNameDelete,R.id.tv_noInternshipExp, R.id.rl_workPlace, R.id.tv_positionSelect, R.id.iv_grossPayDelete, R.id.rl_position, R.id.rl_workExpStartAndEndTime, R.id.btn_nextEdu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_noInternshipExp:
                JobOrderActivity.startAction(this);
                break;
            case R.id.iv_companyNameDelete:
                etCompanyName.setText("");
                break;
            case R.id.iv_grossPayDelete:
                etGrossPay.setText("");
                break;
            case R.id.tv_positionSelect:
                tvPosition.setText("");
            case R.id.rl_position:
                break;
            case R.id.rl_workExpStartAndEndTime:
                setFocus();
                datePickerSE.show(startTimes, endTimes);
                break;
            case R.id.btn_nextEdu:
                if (!ClickUtils.isFastClick()) {
                    doSendWorkExp();
                }
                //JobOrderActivity.startAction(this);
                break;
            case R.id.rl_workPlace:
                setFocus();
                SelectCityActivity.startAction(this, 1, TAG);
                break;
        }
    }

    private void setFocus() {
        clWorkExp.setFocusableInTouchMode(true);
        clWorkExp.setFocusable(true);
        clWorkExp.requestFocus();
        clWorkExp.findFocus();
    }

    private void doSendWorkExp() {
        if (etCompanyName.getText().toString() == null || "".equals(etCompanyName.getText().toString())) {
            ToastUitl.showShort("请填写公司名称");
            return;
        }
        if (tvPosition.getText().toString() == null || "".equals(tvPosition.getText().toString())) {
            ToastUitl.showShort("请填写所任职位");
            return;
        }
        if (cityId == null || "".equals(cityId)) {
            if ("1".equals(type)) {
                ToastUitl.showShort("请选择工作地点");
            }else{
                ToastUitl.showShort("请选择实习地点");
            }
            return;
        }
        if (etGrossPay.getText().toString() == null || "".equals(etGrossPay.getText().toString())) {
            ToastUitl.showShort("请填写税前月薪");
            return;
        }
        if(Integer.parseInt(etGrossPay.getText().toString())==0){
            ToastUitl.showShort("税前月薪必须大于0");
            return;
        }
        if (startTimes == null || endTimes == null || "".equals(startTimes) || "".equals(endTimes)) {
            ToastUitl.showShort("请选择起止时间");
            return;
        }
        if (tvResponsibilityDes.getText().toString() == null || "".equals(tvResponsibilityDes.getText().toString())) {
            ToastUitl.showShort("请填写职责描述");
            return;
        }
        WorkExpData workExpData = new WorkExpData();
        workExpData.setCompany(etCompanyName.getText().toString());
        workExpData.setPosition(tvPosition.getText().toString());
        workExpData.setWorkPlace(cityId);
        workExpData.setGrossPay(etGrossPay.getText().toString());

        if(!"至今".equals(endTimes)) {
            workExpData.setEndTime(endTimes);
        }else{
            workExpData.setEndTime("0-0");
        }
        workExpData.setStartTime(startTimes);
        expId=sUtis.getStringValue(Constants.WORKEXP_ID,"");
        if(expId!=null&&!"".equals(expId)){
            workExpData.setExperienceId(expId);
        }else{
            workExpData.setExperienceId("");
        }
        workExpData.setResponsibilityDescription(responbilityDes);
        mPresenter.sendWorkExpToResume(workExpData);
    }

    public void setSelectCity(CityBean cityBean) {
        if (tvWorkPlace != null) {
            tvWorkPlace.setText(cityBean.getName());
        }
        cityId = cityBean.getId();
    }

    public void setTvResponsibilityDes(String content) {
        tvResponsibilityDes.setText(content);
        responbilityDes = content;
    }

    @OnClick(R.id.rl_responsibilityDes)
    public void onViewClicked() {
        if ("".equals(tvResponsibilityDes.getText().toString()) || tvResponsibilityDes.getText().toString() == null) {
            ContentActivity.startAction(this, TAG);
        } else {
            ContentActivity.startAction(this, tvResponsibilityDes.getText().toString(), TAG);
        }
    }

    private void exitOrFinishActivity() {
        if (startType == 3) {
            myDialog = new MyDialog(this, 2);
            myDialog.setMessage(getString(R.string.exitWarning));
            myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    myDialog.dismiss();
                    SplashActivity.startAction(WorkExpActivity.this, 1);
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

    @Override
    public void sendWorkExpSuccess(String expId) {
        MobclickAgent.onEvent(this,"v6_edit_resumeWorkExp");
        this.expId=expId;
        sUtis.setStringValue(Constants.WORKEXP_ID,expId);
        if (stopType == 3) {
            MobclickAgent.onEvent(this,"v6_resume_complete");
            MainActivity.startAction(this, 0);
            AppManager.getAppManager().finishAllActivity();
        } else {
            JobOrderActivity.startAction(this);
        }
    }
}
