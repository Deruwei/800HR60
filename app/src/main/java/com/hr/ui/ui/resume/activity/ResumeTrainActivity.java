package com.hr.ui.ui.resume.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ResumeTrainBean;
import com.hr.ui.bean.TrainExpData;
import com.hr.ui.ui.resume.contract.ResumeTrainExpContract;
import com.hr.ui.ui.resume.model.ResumeTrainModel;
import com.hr.ui.ui.resume.presenter.ResumeTrainExpPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.MyStartAndEndTimeCustomDatePicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeTrainActivity extends BaseActivity<ResumeTrainExpPresenter, ResumeTrainModel> implements ResumeTrainExpContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_resumeTrainExpTrainOrgTag)
    TextView tvResumeTrainExpTrainOrgTag;
    @BindView(R.id.et_resumeTrainExpTrainOrg)
    EditText etResumeTrainExpTrainOrg;
    @BindView(R.id.iv_resumeTrainExpTrainOrgDelete)
    ImageView ivResumeTrainExpTrainOrgDelete;
    @BindView(R.id.tv_resumeTrainExpTrainClassTag)
    TextView tvResumeTrainExpTrainClassTag;
    @BindView(R.id.et_resumeTrainExpTrainClass)
    EditText etResumeTrainExpTrainClass;
    @BindView(R.id.iv_resumeWorkTrainExpClassDelete)
    ImageView ivResumeWorkTrainExpClassDelete;
    @BindView(R.id.tv_resumeTrainExpStartEndTimeTag)
    TextView tvResumeTrainExpStartEndTimeTag;
    @BindView(R.id.et_resumeTrainExpStartEndTime)
    TextView etResumeTrainExpStartEndTime;
    @BindView(R.id.iv_resumeTrainExpStartEndTimeSelect)
    ImageView ivResumeTrainExpStartEndTimeSelect;
    @BindView(R.id.tv_resumeTrainExpTrainDesTag)
    TextView tvResumeTrainExpTrainDesTag;
    @BindView(R.id.et_resumeTrainExpTrainDes)
    TextView etResumeTrainExpTrainDes;
    @BindView(R.id.iv_resumeTrainExpTrainDesDelete)
    ImageView ivResumeTrainExpTrainDesDelete;
    @BindView(R.id.btn_resumeTrainExpOK)
    Button btnResumeTrainExpOK;
    @BindView(R.id.tv_resumeTrainExpDelete)
    TextView tvResumeTrainExpDelete;
    @BindView(R.id.rl_resumeTrainExpStartEndTime)
    RelativeLayout rlResumeTrainExpStartEndTime;
    @BindView(R.id.rl_resumeTrainExpTrainDes)
    RelativeLayout rlResumeTrainExpTrainDes;
    private String startTimes, endTimes;
    private MyStartAndEndTimeCustomDatePicker datePickerTime;
    private String trainId;
   public static ResumeTrainActivity instance;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumeTrainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,String trainId) {
        Intent intent = new Intent(activity,ResumeTrainActivity.class);
        intent.putExtra("trainId",trainId);
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
    public void getTrainSuccess(ResumeTrainBean.PlantListBean trainBean) {
        initUI(trainBean);
    }

    private void initUI(ResumeTrainBean.PlantListBean trainBean) {
        etResumeTrainExpTrainOrg.setText(trainBean.getInstitution());
        etResumeTrainExpTrainClass.setText(trainBean.getCourse());
        startTimes=trainBean.getFromyear()+"-"+trainBean.getFrommonth();
        endTimes=trainBean.getToyear()+"-"+trainBean.getTomonth();
        etResumeTrainExpStartEndTime.setText(startTimes+"  至  "+endTimes);
        etResumeTrainExpTrainDes.setText(trainBean.getTraindetail());
        ivResumeWorkTrainExpClassDelete.setVisibility(View.GONE);
        ivResumeWorkTrainExpClassDelete.setVisibility(View.GONE);
    }

    @Override
    public void deleteTrainSuccess() {
        ToastUitl.showShort(R.string.deleteSuccess);
        finish();
    }

    @Override
    public void addOrReplaceSucess() {
        ToastUitl.showShort(R.string.saveSuccess);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resumetrainexp;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        instance=this;
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        trainId = getIntent().getStringExtra("trainId");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.workExp);
        ivResumeTrainExpTrainOrgDelete.setVisibility(View.GONE);
        ivResumeWorkTrainExpClassDelete.setVisibility(View.GONE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(!"".equals(trainId)&&trainId!=null){
            mPresenter.getTrainExpData(trainId);
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
                endTimes = endTime;
                startTimes = startTime;
                etResumeTrainExpStartEndTime.setText(startTime + "  至  " + endTimes);
            }
        });
    }

    private void textChanged() {
        etResumeTrainExpTrainOrg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeTrainExpTrainOrgDelete.setVisibility(View.GONE);
                } else {
                    if (etResumeTrainExpTrainOrg.getText().toString() != null && !"".equals(etResumeTrainExpTrainOrg.getText().toString())) {
                        ivResumeTrainExpTrainOrgDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeTrainExpTrainOrg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeTrainExpTrainOrgDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeTrainExpTrainOrgDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResumeTrainExpTrainClass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeWorkTrainExpClassDelete.setVisibility(View.GONE);
                } else {
                    if (etResumeTrainExpTrainClass.getText().toString() != null && !"".equals(etResumeTrainExpTrainClass.getText().toString())) {
                        ivResumeWorkTrainExpClassDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        etResumeTrainExpTrainClass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeWorkTrainExpClassDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeWorkTrainExpClassDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_resumeWorkTrainExpClassDelete, R.id.iv_resumeTrainExpTrainOrgDelete,R.id.rl_resumeTrainExpStartEndTime, R.id.rl_resumeTrainExpTrainDes, R.id.btn_resumeTrainExpOK, R.id.tv_resumeTrainExpDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_resumeWorkTrainExpClassDelete:
                etResumeTrainExpTrainOrg.setText("");
                break;
            case R.id.iv_resumeTrainExpTrainOrgDelete:
                etResumeTrainExpTrainClass.setText("");
                break;
            case R.id.rl_resumeTrainExpStartEndTime:
                datePickerTime.show(startTimes,endTimes);
                break;
            case R.id.rl_resumeTrainExpTrainDes:
                if(etResumeTrainExpTrainDes.getText().toString()==null||"".equals(etResumeTrainExpTrainDes.getText().toString())) {
                    ProjectContentActivity.startAction(this, 1);
                }else{
                    ProjectContentActivity.startAction(this,etResumeTrainExpTrainDes.getText().toString(), 1);
                }
                break;
            case R.id.btn_resumeTrainExpOK:
                doAddOrReplaceTrainData();
                break;
            case R.id.tv_resumeTrainExpDelete:
                mPresenter.deleteExpDate(trainId);
                break;
        }
    }
    public void setTrainDes(String content){
        etResumeTrainExpTrainDes.setText(content);
    }
    private void doAddOrReplaceTrainData() {
        if(etResumeTrainExpTrainOrg.getText().toString()==null&&"".equals(etResumeTrainExpTrainOrg.getText().toString())){
            ToastUitl.showShort("请填写培训机构");
            return;
        }
        if(etResumeTrainExpTrainClass.getText().toString()==null||"".equals(etResumeTrainExpTrainClass.getText().toString())){
            ToastUitl.showShort("请填写培训课程");
            return;
        }
        if("".equals(etResumeTrainExpStartEndTime.getText().toString())||etResumeTrainExpStartEndTime.getText().toString()==null){
            ToastUitl.showShort("请选择起止时间");
            return;
        }
        if("".equals(etResumeTrainExpTrainDes.getText().toString())||etResumeTrainExpTrainDes.getText().toString()==null){
            ToastUitl.showShort("请填写培训描述");
            return;
        }
        TrainExpData trainExpData=new TrainExpData();
        if(!"".equals(trainId)&&trainId!=null) {
            trainExpData.setTrainId(trainId);
        }
        trainExpData.setEndTime(endTimes);
        trainExpData.setStartTime(startTimes);
        trainExpData.setTrainClass(etResumeTrainExpTrainClass.getText().toString());
        trainExpData.setTrainDes(etResumeTrainExpTrainDes.getText().toString());
        trainExpData.setTrainInstruction(etResumeTrainExpTrainOrg.getText().toString());
        mPresenter.addOrReplaceData(trainExpData);
    }
}
