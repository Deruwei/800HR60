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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.JobOrderData;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.JobOrderContract;
import com.hr.ui.ui.main.modle.JobOrderModel;
import com.hr.ui.ui.main.presenter.JobOrderPresenter;
import com.hr.ui.utils.ClickUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyDialog;
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
    EditText tvExpectSalary;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.tv_expectedAddressTag)
    MyTextView tvExpectedAddressTag;
    @BindView(R.id.tv_expectedAddress)
    TextView tvExpectedAddress;
    @BindView(R.id.tv_expectedAddressSelect)
    ImageView tvExpectedAddressSelect;
    @BindView(R.id.btn_nextEdu)
    RelativeLayout btnNextEdu;
    @BindView(R.id.cl_jobOrder)
    ConstraintLayout clJobOrder;
    private int userId;
    public static JobOrderActivity instance;
    @BindView(R.id.tv_expectSalarySelect)
    ImageView tvExpectSalarySelect;
    private List<CityBean> selectPositonList = new ArrayList<>();
    private List<CityBean> selectFunctionList = new ArrayList<>();
    private List<CityBean> selectPlaceList = new ArrayList<>();
    private String industryId, jobTypeId, functionId, positionId, placeId;
    private CustomDatePicker datePickerJobType;
    private SharedPreferencesUtils sUtils;
    public static final String TAG = JobOrderActivity.class.getSimpleName();
    private int stopType,startType;
    private MyDialog myDialog;

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
        int resumeId = sUtils.getIntValue(Constants.RESUME_ID, 0);
        mPresenter.setDefaultResume(resumeId + "", "1");
    }

    @Override
    public void setDefaultResumeSuccess() {
        MainActivity.startAction(this, userId);
        AppManager.getAppManager().finishAllActivity();
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
        sUtils = new SharedPreferencesUtils(this);
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
        tvExpectedFieldTag.setTitleWidth(tvExpectedFieldTag);
        tvExpectedPositionTag.setTitleWidth(tvExpectedFieldTag);
        tvJobTypeTag.setTitleWidth(tvExpectedFieldTag);
        tvExpectSalaryTag.setTitleWidth(tvExpectedFieldTag);
        tvExpectedAddressTag.setTitleWidth(tvExpectedFieldTag);
        textChangeMethod();
    }

    /**
     * 控件文本发生改变触发页面的变化
     */
    private void textChangeMethod() {
        tvExpectSalarySelect.setVisibility(View.GONE);
        tvJobType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivJobTypeSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    ivJobTypeSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvExpectedPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvExpectedPositionSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    tvExpectedPositionSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvExpectedField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvExpectedFieldSelect.setImageResource(R.mipmap.right_arrow);
                } else {
                    tvExpectedFieldSelect.setImageResource(R.mipmap.arrowright);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvExpectSalary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvExpectSalarySelect.setVisibility(View.VISIBLE);
                } else {
                    tvExpectSalarySelect.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvExpectedAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    tvExpectedAddressSelect.setVisibility(View.VISIBLE);
                } else {
                    tvExpectedAddressSelect.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvExpectSalary.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    tvExpectSalarySelect.setVisibility(View.GONE);
                }else{
                    if(tvExpectSalary.getText().toString()!=null&&!"".equals(tvExpectSalary.getText().toString())){
                        tvExpectSalary.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void setFocus() {
        clJobOrder.setFocusableInTouchMode(true);
        clJobOrder.setFocusable(true);
        clJobOrder.requestFocus();
        clJobOrder.findFocus();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance = this;
        initDialog();
    }

    private void initDialog() {
        datePickerJobType = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                jobTypeId = ResumeInfoIDToString.getWorkTypeId(HRApplication.getAppContext(), time, true);
                tvJobType.setText(time);
            }
        }, getResources().getStringArray(R.array.array_jobtype_zh));
    }

    @OnClick({R.id.rl_jobType, R.id.rl_expectedField, R.id.rl_expectedPosition, R.id.tv_expectSalarySelect, R.id.btn_nextEdu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_jobType:
                setFocus();
                datePickerJobType.show(tvJobType.getText().toString());
                break;
            case R.id.rl_expectedField:
                setFocus();
                SelectFunctionActivity.startAction(this, industryId, selectFunctionList,TAG);
                break;
            case R.id.rl_expectedPosition:
                setFocus();
                if (industryId != null && !"".equals(industryId)) {
                    SelectPositionActivity.startAction(this, industryId, selectPositonList,TAG);
                } else {
                    ToastUitl.showShort("请选择期望领域");
                }
                break;
            case R.id.tv_expectSalarySelect:
                tvExpectSalary.setText("");
                break;
            case R.id.btn_nextEdu:
                if(!ClickUtils.isFastClick()) {
                    doSendJobOrderToResume();
                }
                break;
        }
    }

    private void doSendJobOrderToResume() {
        if (jobTypeId == null || "".equals(jobTypeId)) {
            ToastUitl.showShort("请选择工作性质");
            return;
        }
        if ("期望行业".equals(tvExpectedFieldTag.getText().toString())) {

        } else {
            if (functionId == null || "".equals(functionId)) {
                ToastUitl.showShort("请选择期望领域");
                return;
            }
        }
        if (positionId == null && "".equals(positionId)) {
            ToastUitl.showShort("请选择期望职位");
            return;
        }
        if ("".equals(tvExpectSalary.getText().toString()) || tvExpectSalary.getText().toString() == null) {
            ToastUitl.showShort("请填写期望月薪");
            return;
        }
        JobOrderData jobOrderData = new JobOrderData();
        jobOrderData.setExpectArea(functionId);
        jobOrderData.setSalary(tvExpectSalary.getText().toString());
        jobOrderData.setIndustry(industryId);
        jobOrderData.setExpectPosition(positionId);
        jobOrderData.setAddress(placeId);
        jobOrderData.setWorkType(jobTypeId);
        mPresenter.sendJobOrderToResume(jobOrderData);
    }

    /**
     * 选择职位页面传递过来的参数
     *
     * @param positionList
     */
    public void setPositionList(List<CityBean> positionList) {
        selectPositonList.clear();
        selectPositonList = positionList;
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        for (int i = 0; i < positionList.size(); i++) {
            sb.append("," + positionList.get(i).getId());
            sbName.append("，" + positionList.get(i).getName());
        }
        sb.deleteCharAt(0);
        positionId = sb.toString();
        sbName.deleteCharAt(0);
        tvExpectedPosition.setText(sbName);
    }

    /**
     * 选择领域页面传递过来的参数
     *
     * @param industryId
     * @param selectFunctionList
     */
    public void setFunctionList(String industryId, List<CityBean> selectFunctionList) {
        this.selectFunctionList.clear();
        if (!industryId.equals(this.industryId)) {
            tvExpectedPosition.setText("");
            selectPositonList.clear();
        }
        this.industryId = industryId;
        if (selectFunctionList != null && !"".equals(selectFunctionList) && selectFunctionList.size() != 0) {
            this.selectFunctionList = selectFunctionList;
            //Log.i("选择",selectFunctionList.toString());
            StringBuffer sb = new StringBuffer();
            StringBuffer sbName = new StringBuffer();
            for (int i = 0; i < selectFunctionList.size(); i++) {
                sb.append("," + selectFunctionList.get(i).getId());
                sbName.append("，" + selectFunctionList.get(i).getName());
            }
            sb.deleteCharAt(0);
            sbName.deleteCharAt(0);
            tvExpectedFieldTag.setText(R.string.expectedField);
            functionId = sb.toString();
            tvExpectedField.setText("[" + ResumeInfoIDToString.getIndustry(this, industryId, true) + "]" + sbName);
        } else {
            tvExpectedFieldTag.setText(R.string.expectedIndustry);
            tvExpectedField.setText("[" + ResumeInfoIDToString.getIndustry(HRApplication.getAppContext(), industryId, true) + "]");
        }
    }

    public void setAddress(List<CityBean> selectCityList) {
        this.selectPlaceList.clear();
        this.selectPlaceList = selectCityList;
        //Log.i("选择",selectFunctionList.toString());
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        for (int i = 0; i < selectPlaceList.size(); i++) {
            sb.append("," + selectPlaceList.get(i).getId());
            sbName.append("，" + selectPlaceList.get(i).getName());
        }
        sb.deleteCharAt(0);
        sbName.deleteCharAt(0);
        placeId = sb.toString();
        tvExpectedAddress.setText(sbName);
    }

    @OnClick(R.id.rl_expectedAddress)
    public void onViewClicked() {
        setFocus();
        SelectCityActivity.startAction(this, 2, TAG, selectPlaceList);
    }

    private void exitOrFinishActivity() {
        if (startType == 4) {
            myDialog = new MyDialog(this, 2);
            myDialog.setMessage(getString(R.string.exitWarning));
            myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    myDialog.dismiss();
                    SplashActivity.startAction(JobOrderActivity.this,1);
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
