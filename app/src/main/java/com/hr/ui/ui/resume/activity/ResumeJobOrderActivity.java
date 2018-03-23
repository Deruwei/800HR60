package com.hr.ui.ui.resume.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.JobOrderData;
import com.hr.ui.bean.ResumeOrderInfoBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.activity.JobOrderActivity;
import com.hr.ui.ui.main.activity.SelectCityActivity;
import com.hr.ui.ui.main.activity.SelectOptionsActivity;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.hr.ui.ui.resume.contract.ResumeJobOrderContract;
import com.hr.ui.ui.resume.model.ResumeJobOrderModel;
import com.hr.ui.ui.resume.presenter.ResumeJobOrderPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyFlowLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Func1;

/**
 * Created by wdr on 2017/12/26.
 */

public class ResumeJobOrderActivity extends BaseActivity<ResumeJobOrderPresenter, ResumeJobOrderModel> implements ResumeJobOrderContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_resumeJobOrderJobTypeTag)
    TextView tvResumeJobOrderJobTypeTag;
    @BindView(R.id.tv_resumeJobOrderJobType)
    TextView tvResumeJobOrderJobType;
    @BindView(R.id.iv_resumeJobOrderJobTypeSelect)
    ImageView ivResumeJobOrderJobTypeSelect;
    @BindView(R.id.tv_resumeJobOrderJobStyleTag)
    TextView tvResumeJobOrderJobStyleTag;
    @BindView(R.id.tv_resumeJobOrderJobStyle)
    TextView tvResumeJobOrderJobStyle;
    @BindView(R.id.iv_resumeJobOrderJobStyleSelect)
    ImageView ivResumeJobOrderJobStyleSelect;
    @BindView(R.id.tv_resumeJobOrderExpectedSalaryTag)
    TextView tvResumeJobOrderExpectedSalaryTag;
    @BindView(R.id.tv_resumeJobOrderExpectedSalary)
    EditText tvResumeJobOrderExpectedSalary;
    @BindView(R.id.iv_resumeJobOrderExpectedSalaryDelete)
    ImageView ivResumeJobOrderExpectedSalaryDelete;
    @BindView(R.id.tv_resumeJobOrderWorkCityTag)
    TextView tvResumeJobOrderWorkCityTag;
    @BindView(R.id.tv_resumeJobOrderWorkCity)
    TextView tvResumeJobOrderWorkCity;
    @BindView(R.id.iv_resumeJobOrderWorkCitySelect)
    ImageView ivResumeJobOrderWorkCitySelect;
    @BindView(R.id.rl_resumeJobOrderIndustry)
    LinearLayout rlResumeJobOrderIndustry;
    @BindView(R.id.btn_resumeJobOrderOK)
    Button btnResumeJobOrderOK;
    public static ResumeJobOrderActivity instance;
    @BindView(R.id.cl_resumeJobOrder)
    ConstraintLayout clResumeJobOrder;
    @BindView(R.id.rl_resumeJobOrderJobType)
    RelativeLayout rlResumeJobOrderJobType;
    @BindView(R.id.rl_resumeJobOrderJobStyle)
    RelativeLayout rlResumeJobOrderJobStyle;
    @BindView(R.id.rl_resumeJobOrderWorkCity)
    RelativeLayout rlResumeJobOrderWorkCity;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    private CustomDatePicker datePickerJobType, datePickerJobStyle;
    private String jobType, jobStyle, cityId,positionId,funId;
    private List<CityBean> selectCityBean = new ArrayList<>();
    private List<ResumeOrderInfoBean.OrderInfoBean.OrderIndustryBean> industryBeanList = new ArrayList<>();
    public static String TAG = ResumeJobOrderActivity.class.getSimpleName();
    private List<CityBean> selectPositionList = new ArrayList<>();
    private List<CityBean> selectFuncList = new ArrayList<>();
    private String industryId;
    private List<String> industryIds = new ArrayList<>();
    private ResumeOrderInfoBean.OrderInfoBean orderInfoBean;
    private List<List<CityBean>> selectFunctionLists=new ArrayList<>();
    private List<List<CityBean>> selectPositionLists=new ArrayList<>();
    private String mode="0";
    private SharedPreferencesUtils sUtils;
    private MyDialog myDialog;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ResumeJobOrderActivity.class);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void getJobOrderSuccess(ResumeOrderInfoBean.OrderInfoBean orderInfoBean) {
        if (orderInfoBean != null) {
            this.orderInfoBean = orderInfoBean;
            initUI();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initUI() {
        tvResumeJobOrderJobType.setText(ResumeInfoIDToString.getWorkType(this, orderInfoBean.getJobtype(), true));
        jobType = orderInfoBean.getJobtype();
        tvResumeJobOrderJobStyle.setText(ResumeInfoIDToString.getCurrentState(this, orderInfoBean.getCurrent_workstate(), true));
        jobStyle = orderInfoBean.getCurrent_workstate();
        tvResumeJobOrderExpectedSalary.setText(orderInfoBean.getOrder_salary());
        tvResumeJobOrderWorkCity.setText(FromStringToArrayList.getInstance().getCityListName(orderInfoBean.getWorkarea()));
        cityId = orderInfoBean.getWorkarea();
        positionId=orderInfoBean.getFunc();
        funId=orderInfoBean.getLingyu();
        selectCityBean = FromStringToArrayList.getInstance().getSelectCityList(cityId);
        ivResumeJobOrderExpectedSalaryDelete.setVisibility(View.GONE);
        industryBeanList.clear();
        //Log.i("funid",funId+"=------");
        initIndustryUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initIndustryUI() {
        StringBuffer sbPosition=new StringBuffer();
        StringBuffer sbFunc=new StringBuffer();
        StringBuffer sbIndustry=new StringBuffer();
        industryIds = new ArrayList<>();
        industryBeanList = orderInfoBean.getOrder_industry();
        selectFunctionLists=new ArrayList<>();
        selectPositionLists=new ArrayList<>();
        if (industryBeanList != null&&industryBeanList.size()!=0) {
            rlResumeJobOrderIndustry.setVisibility(View.VISIBLE);
            rlResumeJobOrderIndustry.removeAllViewsInLayout();
            for (int i = 0; i < industryBeanList.size(); i++) {
                View plantView = LayoutInflater.from(this).inflate(
                        R.layout.layout_industry, null);
                industryIds.add(industryBeanList.get(i).getIndustry());
                // initview
                RelativeLayout rlIndustry = plantView.findViewById(R.id.rl_resumeIndustry);
                MyFlowLayout flPosition = plantView.findViewById(R.id.fl_expectedPosition);
                TextView tvIndustryName = plantView.findViewById(R.id.tv_resumeIndustryName);
                ImageView iv_industryIcon = plantView.findViewById(R.id.iv_industryIcon);
                View view=plantView.findViewById(R.id.view_fengexian);
                MyFlowLayout flField = plantView.findViewById(R.id.fl_expectedField);
                tvIndustryName.setText(ResumeInfoIDToString.getIndustry(this, industryBeanList.get(i).getIndustry(), true));
                String positionNames = FromStringToArrayList.getInstance().getExpectPositionString(industryBeanList.get(i).getIndustry(), industryBeanList.get(i).getFunc());
                String fieldName="";
                if(FromStringToArrayList.getInstance().getIndustryIsHaveField(industryBeanList.get(i).getIndustry())==true) {
                    fieldName = FromStringToArrayList.getInstance().getExpectFieldName(industryBeanList.get(i).getIndustry(), industryBeanList.get(i).getLingyu());
                    sbFunc.append(","+industryBeanList.get(i).getLingyu());
                }
                sbPosition.append(","+industryBeanList.get(i).getFunc());
                sbIndustry.append(","+industryBeanList.get(i).getIndustry());
                String[] positionNameString = positionNames.split(",");
                String[] fieldNameString = fieldName.split(",");
                String[] positionIds = industryBeanList.get(i).getFunc().split(",");
                String[] funcIds = industryBeanList.get(i).getLingyu().split(",");
                //Log.i("当前的行业",sbFunc.toString());
                industryId = industryBeanList.get(i).getIndustry();
                iv_industryIcon.setImageDrawable(FromStringToArrayList.getInstance().getIndustryIcon(industryBeanList.get(i).getIndustry()));
                addView(flPosition, "期望职位");
                addView(flField, "期望领域");
                selectPositionList=new ArrayList<>();
                //Log.i("当前=",positionIds+"---------------"+fieldNameString[0].toString());
                for (int j = 0; j < positionIds.length; j++) {
                    CityBean cityBean = new CityBean();
                    cityBean.setId(positionIds[j]);
                    cityBean.setName(positionNameString[j]);
                    String s="";
                    s=positionNameString[j];
                    selectPositionList.add(cityBean);
                    addView(flPosition, s);
                }
                selectPositionLists.add(selectPositionList);
                //Log.i("当前=",positionIds+"---------------"+selectPositionLists.toString());
                selectFuncList=new ArrayList<>();
                for (int j = 0; j < fieldNameString.length; j++) {
                    CityBean cityBean = new CityBean();
                    cityBean.setId(funcIds[j]);
                    cityBean.setName(fieldNameString[j]);
                    selectFuncList.add(cityBean);
                    addView(flField, fieldNameString[j]);
                }
                if(selectFuncList!=null&&!"".equals(selectFuncList)&&selectFuncList.size()!=0&&selectFuncList.get(0).getId()!=null&&!"".equals(selectFuncList.get(0).getId())){
                    flField.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                   /* Log.i("当前的数据","显示");*/
                }else{
                    flField.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                    /*Log.i("当前的数据","隐藏");*/
                }
                selectFunctionLists.add(selectFuncList);
                final int finalI = i;

                rlIndustry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SelectOptionsActivity.startAction(ResumeJobOrderActivity.this, industryIds, finalI, industryBeanList.get(finalI).getIndustry(), selectFunctionLists.get(finalI), selectPositionLists.get(finalI));
                    }
                });
                /*   tvResumeName2.setText(experienceListBeanList.get(i).getPosition());
                rlResumeItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                llResumeWorkExpList.addView(plantView);*/
                rlResumeJobOrderIndustry.addView(plantView);
            }
            if(sbPosition!=null&&!"".equals(sbPosition)&&sbPosition.length()!=0) {
                positionId = sbPosition.deleteCharAt(0).toString();
            }
            if(sbFunc!=null&&!"".equals(sbFunc)&&sbFunc.length()!=0) {
                funId = sbFunc.deleteCharAt(0).toString();
            }
            if(sbIndustry!=null&&!"".equals(sbIndustry)&&sbIndustry.length()!=0) {
                industryId = sbIndustry.deleteCharAt(0).toString();
            }
            //Log.i("funid",funId+"-------------");
        }else{
            rlResumeJobOrderIndustry.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addView(MyFlowLayout flowLayout, String name) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 15;
        params.topMargin = 12;
        params.bottomMargin = 12;
        params.rightMargin = 15;
        final LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.item_textview_selected_gray, null, false);
        ll.setLayoutParams(params);
        TextView tv = ll.findViewById(R.id.item_select_gray);
        if ("期望职位".equals(name) || "期望领域".equals(name)) {
            tv.setBackground(null);
        }
        tv.setText(name);
        flowLayout.addView(ll);

    }

    @Override
    public void setJobOrderSuccess() {
        ToastUitl.showShort(R.string.saveSuccess);
        sUtils.setBooleanValue(Constants.IS_NEEDSAVEWARNNING,false);
        sUtils.setBooleanValue(Constants.IS_FERSH,true);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_resumejoborder;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        instance = this;
        setSupportActionBar(toolBar);
        sUtils=new SharedPreferencesUtils(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        MobclickAgent.onEvent(this,"v6_scan_resumeorder");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.jobIntention);
        tvToolbarSave.setVisibility(View.VISIBLE);
        tvToolbarSave.setText(getString(R.string.add));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   showDialogWay();
            }
        });
        setTextChangedListener();
        mPresenter.getJobOrderInfo();
    }

    private void showDialogWay() {
        myDialog=new MyDialog(ResumeJobOrderActivity.this,2);
        myDialog.setMessage(getString(R.string.exitWarning));
        myDialog.setYesOnclickListener("确定", new MyDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                finish();
                myDialog.dismiss();
            }
        });
        myDialog.setNoOnclickListener("取消", new MyDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    private void setTextChangedListener() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setChangeListener();
        initDialog();
    }
    private void initDialog() {
        datePickerJobStyle = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvResumeJobOrderJobStyle.setText(time);
                jobStyle = ResumeInfoIDToString.getCurrentStateId(ResumeJobOrderActivity.this, time, true);
            }
        }, getResources().getStringArray(R.array.array_workstate_zh));
        datePickerJobType = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvResumeJobOrderJobType.setText(time);
                jobType = ResumeInfoIDToString.getWorkTypeId(ResumeJobOrderActivity.this, time, true);
            }
        }, getResources().getStringArray(R.array.array_jobtype_zh));
    }

    private void setChangeListener() {
        tvResumeJobOrderExpectedSalary.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    ivResumeJobOrderExpectedSalaryDelete.setVisibility(View.GONE);
                } else {
                    if (tvResumeJobOrderExpectedSalary.getText().toString() != null && !"".equals(tvResumeJobOrderExpectedSalary.getText().toString())) {
                        ivResumeJobOrderExpectedSalaryDelete.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tvResumeJobOrderExpectedSalary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    ivResumeJobOrderExpectedSalaryDelete.setVisibility(View.VISIBLE);
                } else {
                    ivResumeJobOrderExpectedSalaryDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.rl_resumeJobOrderJobType,R.id.tv_toolbarSave, R.id.rl_resumeJobOrderJobStyle, R.id.iv_resumeJobOrderExpectedSalaryDelete, R.id.rl_resumeJobOrderWorkCity, R.id.btn_resumeJobOrderOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbarSave:
                if(industryIds.size()>=5) {
                    ToastUitl.showShort("最多只能添加5个行业");
                }else {
                    SelectOptionsActivity.startAction(ResumeJobOrderActivity.this, industryIds, 100,1);
                }
                break;
            case R.id.rl_resumeJobOrderJobType:
                setFocus();
                datePickerJobType.show(tvResumeJobOrderJobType.getText().toString());
                break;
            case R.id.rl_resumeJobOrderJobStyle:
                setFocus();
                datePickerJobStyle.show(tvResumeJobOrderJobStyle.getText().toString());
                break;
            case R.id.iv_resumeJobOrderExpectedSalaryDelete:
                tvResumeJobOrderExpectedSalary.setText("");
                break;
            case R.id.rl_resumeJobOrderWorkCity:
                setFocus();
                SelectCityActivity.startAction(this, 2, TAG, selectCityBean);
                break;
            case R.id.btn_resumeJobOrderOK:
                doSaveOrUpdateJobOrder();
                break;
        }
    }

    private void doSaveOrUpdateJobOrder() {
        if(jobType==null||"".equals(jobType)){
            ToastUitl.showShort("请选择求职类型");
            return;
        }
        if(jobStyle==null||"".equals(jobStyle)){
            ToastUitl.showShort("请选择求职状态");
            return;
        }
       if("".equals(tvResumeJobOrderExpectedSalary.getText().toString())||tvResumeJobOrderExpectedSalary.getText().toString()==null){
            ToastUitl.showShort("请填写期望薪资");
            return;
       }
       if(Integer.parseInt(tvResumeJobOrderExpectedSalary.getText().toString())==0){
           ToastUitl.showShort("期望月薪必须大于0");
           return;
       }
       if("".equals(cityId)||cityId==null){
           ToastUitl.showShort("请选择工作城市");
           return;
       }
       if(orderInfoBean==null||orderInfoBean.getOrder_industry()==null||"".equals(orderInfoBean.getOrder_industry())||orderInfoBean.getOrder_industry().size()==0){
           ToastUitl.showShort("请选择期望职位");
           return;
       }
       JobOrderData jobOrderData=new JobOrderData();
       jobOrderData.setAddress(cityId);
       jobOrderData.setSalary(tvResumeJobOrderExpectedSalary.getText().toString());
       jobOrderData.setWorkType(jobType);
       jobOrderData.setJobStyle(jobStyle);
       jobOrderData.setExpectPosition(positionId);
       jobOrderData.setExpectArea(funId);
       jobOrderData.setIndustry(industryId);
       jobOrderData.setMode(mode);
       mPresenter.setJobOrderInfo(jobOrderData);
    }

    private void setFocus() {
        clResumeJobOrder.setFocusable(true);
        clResumeJobOrder.setFocusableInTouchMode(true);
        clResumeJobOrder.requestFocus();
        clResumeJobOrder.findFocus();
    }

    public void setSelectCityList(List<CityBean> selectCityList) {
        this.selectCityBean.clear();
        this.selectCityBean = selectCityList;
        //Log.i("选择",selectFunctionList.toString());
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        for (int i = 0; i < selectCityBean.size(); i++) {
            sb.append("," + selectCityBean.get(i).getId());
            sbName.append("，" + selectCityBean.get(i).getName());
        }
        sb.deleteCharAt(0);
        sbName.deleteCharAt(0);
        cityId = sb.toString();
        tvResumeJobOrderWorkCity.setText(sbName);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void updateJobOrderInfo(int updatePostion, String industryId, List<CityBean> selectFunclist, List<CityBean> slectPositionList) {
        orderInfoBean.getOrder_industry().get(updatePostion).setIndustry(industryId);
        StringBuffer sbFunc = new StringBuffer();
        for (int i = 0; i < selectFunclist.size(); i++) {
            sbFunc.append("," + selectFunclist.get(i).getId());
        }
        if(sbFunc.length()!=0) {
            sbFunc.deleteCharAt(0);
        }
        orderInfoBean.getOrder_industry().get(updatePostion).setLingyu(sbFunc.toString());
        StringBuffer sbPosition = new StringBuffer();
        for (int i = 0; i < slectPositionList.size(); i++) {
            sbPosition.append("," + slectPositionList.get(i).getId());
        }
        sbPosition.deleteCharAt(0);
        if(orderInfoBean==null){
            orderInfoBean=new ResumeOrderInfoBean.OrderInfoBean();
        }
        orderInfoBean.getOrder_industry().get(updatePostion).setFunc(sbPosition.toString());
        mode="0";
        initIndustryUI();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void addJobOrderInfo( String industryId, List<CityBean> selectFunclist, List<CityBean> slectPositionList) {
        //Log.i("info数据","你好");
        ResumeOrderInfoBean.OrderInfoBean.OrderIndustryBean orderIndustryBean = new ResumeOrderInfoBean.OrderInfoBean.OrderIndustryBean();
        orderIndustryBean.setIndustry(industryId);
       /* Log.i("funid",selectFuncList.toString()+"你好");*/
        if(FromStringToArrayList.getInstance().getIndustryIsHaveField(industryId)==true) {
            StringBuffer sbFunc = new StringBuffer();
            for (int i = 0; i < selectFunclist.size(); i++) {
                sbFunc.append("," + selectFunclist.get(i).getId());
            }
            sbFunc.deleteCharAt(0);
            orderIndustryBean.setLingyu(sbFunc.toString());
            //Log.i("funid",sbFunc.toString()+"你好");
        }else{
            orderIndustryBean.setLingyu("");
            //Log.i("funid","你好");
        }
        StringBuffer sbPosition = new StringBuffer();
        for (int i = 0; i < slectPositionList.size(); i++) {
            sbPosition.append("," + slectPositionList.get(i).getId());
        }
        sbPosition.deleteCharAt(0);
        orderIndustryBean.setFunc(sbPosition.toString());
        orderInfoBean.getOrder_industry().add(orderIndustryBean);
        mode="1";
        initIndustryUI();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void deleteJobOrderInfo(int updatePosition) {
        orderInfoBean.getOrder_industry().remove(orderInfoBean.getOrder_industry().get(updatePosition));
        mode="0";
        initIndustryUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myDialog!=null){
            myDialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            showDialogWay();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
