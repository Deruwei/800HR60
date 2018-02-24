package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.HistoryBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.db.SearchHistoryUtils;
import com.hr.ui.ui.job.activity.CompanyPageActivity;
import com.hr.ui.ui.main.adapter.MyHotSearchAdapter;
import com.hr.ui.ui.main.contract.JobSearchContract;
import com.hr.ui.ui.main.modle.JobSearchModel;
import com.hr.ui.ui.main.presenter.JobSearchPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.view.MyFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/8.
 */

public class JobSerchActivity extends BaseActivity<JobSearchPresenter, JobSearchModel> implements JobSearchContract.View {
    @BindView(R.id.iv_jobSearchBack)
    ImageView ivJobSearchBack;
    @BindView(R.id.tv_jobSearchType)
    TextView tvJobSearchType;
    @BindView(R.id.rl_jobSearchType)
    RelativeLayout rlJobSearchType;
    @BindView(R.id.et_jobSearch)
    EditText etJobSearch;
    @BindView(R.id.tv_jobSearch)
    TextView tvJobSearch;
    @BindView(R.id.rl_jobSearchPlace)
    RelativeLayout rlJobSearchPlace;
    @BindView(R.id.rl_jobSearchIndustry)
    RelativeLayout rlJobSearchIndustry;
    @BindView(R.id.rl_jobSearchFunction)
    RelativeLayout rlJobSearchFunction;
    @BindView(R.id.rl_jobSearchHistory)
    MyFlowLayout rlJobSearchHistory;
    @BindView(R.id.iv_historyDelete)
    ImageView ivHistoryDelete;
    @BindView(R.id.tv_jobSearch1)
    TextView tvJobSearch1;
    @BindView(R.id.tv_jobSearchPlace)
    TextView tvJobSearchPlace;
    @BindView(R.id.iv_jobSearch1)
    ImageView ivJobSearch1;
    @BindView(R.id.tv_jobSearch2)
    TextView tvJobSearch2;
    @BindView(R.id.tv_jobSearchIndustry)
    TextView tvJobSearchIndustry;
    @BindView(R.id.iv_jobSearch2)
    ImageView ivJobSearch2;
    @BindView(R.id.tv_jobSearch3)
    TextView tvJobSearch3;
    @BindView(R.id.tv_jobSearchFunction)
    TextView tvJobSearchFunction;
    @BindView(R.id.iv_jobSearch3)
    ImageView ivJobSearch3;
    @BindView(R.id.view_middle)
    View viewMiddle;
    @BindView(R.id.rv_hotSearch)
    RecyclerView rvHotSearch;
    @BindView(R.id.rl_historyTitle)
    RelativeLayout rlHistoryTitle;
    private PopupWindow popupWindowJobType;
    private List<HistoryBean> historyBeanList;
    private String cityId, cityName, industryId, industryName, postionId, positionName;
    private List<CityBean> selectCityList = new ArrayList<>();
    private List<CityBean> selectIndustryList = new ArrayList<>();
    private List<CityBean> selectPositionList = new ArrayList<>();
    private List<CityBean> selectFunctionList = new ArrayList<>();
    private String functionId;
    private MyHotSearchAdapter adapter;
    private boolean isSearch;//判断是否可以跳转搜索结果页
    public static final String TAG = JobSerchActivity.class.getSimpleName();
    public static JobSerchActivity instance;
    private int jobSerchType = 1;//1代表搜索全部 2代表根据职位进行搜索 3代表根据公司进行搜索
    public int RESULT_CODE = 0x1008;
    public int RESULT_CODE2 = 0x1009;

    public static void startAction(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, JobSerchActivity.class);
        //Log.i("传到这里了",requestCode+"");
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    public static void startAction2(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, JobSerchActivity.class);
        //Log.i("传到这里了",requestCode+"");
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_jobsearch;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        instance = this;
        mPresenter.getHotSearchJob(1, "3");
        historyBeanList = SearchHistoryUtils.queryAll();
        if (historyBeanList != null && historyBeanList.size() != 0) {
            rlHistoryTitle.setVisibility(View.VISIBLE);
            initHistoryView();
        }else{
            rlHistoryTitle.setVisibility(View.GONE);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHotSearch.setLayoutManager(manager);
        DividerItemDecoration dividerDrawable = new DividerItemDecoration(this, 1);
        rvHotSearch.addItemDecoration(dividerDrawable);
    }

    private void initHistoryView() {
        for (int i = 0; i < historyBeanList.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 15;
            params.topMargin = 12;
            params.bottomMargin = 12;
            params.rightMargin = 15;
            final LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                    R.layout.item_history, null, false);
            TextView tv = ll.findViewById(R.id.item_selectHistory);
            Random random = new Random();
            GradientDrawable bgShape = (GradientDrawable) tv.getBackground();
//                    bgShape.setColor(Color.BLACK);
            bgShape.setStroke(1, ContextCompat.getColor(HRApplication.getAppContext(), R.color.bg_color));
            bgShape.setCornerRadius(15);
            tv.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            ll.setLayoutParams(params);

            tv.setText(historyBeanList.get(i).getSearchName());
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JobSearchBean jobSearchBean = new JobSearchBean();
                    jobSearchBean.setCompanyType(historyBeanList.get(finalI).getCompanyType());
                    jobSearchBean.setSearchName(historyBeanList.get(finalI).getSearchName());
                    jobSearchBean.setDegree(historyBeanList.get(finalI).getDegree());
                    jobSearchBean.setSalary_right(historyBeanList.get(finalI).getSalary_right());
                    jobSearchBean.setSalary_left(historyBeanList.get(finalI).getSalary_left());
                    jobSearchBean.setCompanyScale(historyBeanList.get(finalI).getCompanyScale());
                    jobSearchBean.setWorkType(historyBeanList.get(finalI).getWorkType());
                    jobSearchBean.setWorkExp(historyBeanList.get(finalI).getWorkExp());
                    jobSearchBean.setJobTime(historyBeanList.get(finalI).getJobTime());
                    jobSearchBean.setFieldId(historyBeanList.get(finalI).getFieldId());
                    jobSearchBean.setPositionId(historyBeanList.get(finalI).getPositionId());
                    jobSearchBean.setIndustryId(historyBeanList.get(finalI).getIndustryId());
                    jobSearchBean.setPlaceId(historyBeanList.get(finalI).getPlaceId());
                    jobSearchBean.setJobType(historyBeanList.get(finalI).getJobType());
                   // Log.i("当前的数据", jobSearchBean.toString());
                    MainActivity.instance.isHome = false;
                    Intent intent = new Intent(JobSerchActivity.this, MainActivity.class);
                    intent.putExtra("jobSearch", (Serializable) jobSearchBean);
                    setResult(RESULT_CODE, intent);
                    finish();
                }
            });
            rlJobSearchHistory.addView(ll);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_historyDelete, R.id.iv_jobSearchBack, R.id.rl_jobSearchType, R.id.tv_jobSearch, R.id.rl_jobSearchPlace, R.id.rl_jobSearchIndustry, R.id.rl_jobSearchFunction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_jobSearchBack:
                MainActivity.instance.isHome = true;
                JobSearchBean jobSearchBean1 = new JobSearchBean();
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.putExtra("jobSearch", (Serializable) jobSearchBean1);
                setResult(RESULT_CODE, intent1);
                finish();
                break;
            case R.id.rl_jobSearchType:
                initPopSearchType();
                break;
            case R.id.tv_jobSearch:
                JobSearchBean jobSearchBean = new JobSearchBean();
                HistoryBean historyBean = new HistoryBean();
                if (industryId != null && !"".equals(industryId)) {
                    jobSearchBean.setIndustryId(industryId);
                    historyBean.setIndustryId(industryId);
                    isSearch = true;
                }
                jobSearchBean.setJobType(jobSerchType);
                historyBean.setJobType(jobSerchType);
                if (!"".equals(cityId) && cityId != null) {
                    jobSearchBean.setPlaceId(cityId);
                    historyBean.setPlaceId(cityId);
                    isSearch = true;
                }
                if (!"".equals(postionId) && postionId != null) {
                    jobSearchBean.setPositionId(postionId);
                    historyBean.setPositionId(postionId);
                    isSearch = true;
                }
                if (!"".equals(functionId) && functionId != null) {
                    jobSearchBean.setFieldId(functionId);
                    historyBean.setFieldId(functionId);
                    isSearch = true;
                }
                if (isSearch == false) {
                    if (etJobSearch.getText().toString() == null || "".equals(etJobSearch.getText().toString())) {
                        if ("".equals(selectPositionList) || selectPositionList == null || selectPositionList.size() == 0) {
                            ToastUitl.showShort("职位与关键词至少选择一个");
                            return;
                        }

                    }
                }
                if (etJobSearch.getText().toString() != null && !"".equals(etJobSearch.getText().toString())) {
                    jobSearchBean.setSearchName(etJobSearch.getText().toString());
                    historyBean.setSearchName(etJobSearch.getText().toString());
                    SearchHistoryUtils.insertJobSearchDataOrReplace(historyBean);
                }

                MainActivity.instance.isHome = false;
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("jobSearch", (Serializable) jobSearchBean);
                setResult(RESULT_CODE, intent);
                finish();
                break;
            case R.id.rl_jobSearchPlace:
                SelectCityActivity.startAction(this, 2, TAG, selectCityList);
                break;
            case R.id.rl_jobSearchIndustry:
                SelectFunctionActivity.startAction(this, industryId, selectFunctionList, TAG);
                break;
            case R.id.rl_jobSearchFunction:
                if (industryId != null && !"".equals(industryId)) {
                    SelectPositionActivity.startAction(this, industryId, selectPositionList, TAG);
                } else {
                    ToastUitl.showShort("请选择行业");
                    return;
                }
                break;
            case R.id.iv_historyDelete:
                SearchHistoryUtils.deleteAll();
                rlJobSearchHistory.removeAllViewsInLayout();
                rlHistoryTitle.setVisibility(View.GONE);
                break;
        }
    }

    private void initPopSearchType() {
        View viewIndustry = getLayoutInflater().inflate(R.layout.layout_searchtype, null);
        popupWindowJobType = new PopupWindow(viewIndustry, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int[] location = new int[2];
        final TextView tvAll = viewIndustry.findViewById(R.id.tv_jobTypeAll);
        final TextView tvCompany = viewIndustry.findViewById(R.id.tv_jobTypeCompany);
        final TextView tvPosition = viewIndustry.findViewById(R.id.tv_jobTypePosition);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        tvJobSearch.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJobSearchType.setText("全文");
                jobSerchType = 1;
                popupWindowJobType.dismiss();
            }
        });
        tvCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJobSearchType.setText("公司");
                jobSerchType = 3;
                popupWindowJobType.dismiss();
            }
        });
        tvPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJobSearchType.setText("职位");
                jobSerchType = 2;
                popupWindowJobType.dismiss();
            }
        });
        popupWindowJobType.setOutsideTouchable(true);
        popupWindowJobType.setFocusable(true);
        popupWindowJobType.setAnimationStyle(R.style.style_pop_animation);
        popupWindowJobType.showAsDropDown(tvJobSearchType, 0, 5);
    }

    public void setPlace(List<CityBean> selectCityList) {
        this.selectCityList.clear();
        this.selectCityList = selectCityList;
        //Log.i("选择",selectFunctionList.toString());
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        for (int i = 0; i < selectCityList.size(); i++) {
            sb.append("," + selectCityList.get(i).getId());
            sbName.append("，" + selectCityList.get(i).getName());
        }
        sb.deleteCharAt(0);
        sbName.deleteCharAt(0);
        cityId = sb.toString();
        tvJobSearchPlace.setText(sbName);
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
            tvJobSearchFunction.setText("");
            selectPositionList.clear();
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
            functionId = sb.toString();
            tvJobSearchIndustry.setText("[" + ResumeInfoIDToString.getIndustry(this, industryId, true) + "]" + sbName);
        } else {

            tvJobSearchIndustry.setText("[" + ResumeInfoIDToString.getIndustry(HRApplication.getAppContext(), industryId, true) + "]");
        }
    }

    public void setPosition(List<CityBean> selectPositionList) {
        this.selectPositionList.clear();
        this.selectPositionList = selectPositionList;
        //Log.i("选择",selectFunctionList.toString());
        StringBuffer sb = new StringBuffer();
        StringBuffer sbName = new StringBuffer();
        for (int i = 0; i < selectPositionList.size(); i++) {
            sb.append("," + selectPositionList.get(i).getId());
            if (selectPositionList.get(i).getId().contains("|")) {
                sbName.append("，" + selectPositionList.get(i).getName() + "(" + Utils.getPositionClassName(selectPositionList.get(i).getId().substring(selectPositionList.get(i).getId().indexOf("|") + 1)) + ")");
            } else {
                sbName.append("，" + selectPositionList.get(i).getName());
            }
        }
        sb.deleteCharAt(0);
        sbName.deleteCharAt(0);
        postionId = sb.toString();
        tvJobSearchFunction.setText(sbName);
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
    public void getHotSearchJobSuccess(final List<FindBean.ListBean> listBeans) {
        adapter = new MyHotSearchAdapter(this);
        adapter.setListBeans(listBeans);
        rvHotSearch.setAdapter(adapter);
        adapter.setClickCallBack(new MyHotSearchAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                CompanyPageActivity.startAction(JobSerchActivity.this, listBeans.get(pos).getEnterprise_id());
            }
        });
    }
}
