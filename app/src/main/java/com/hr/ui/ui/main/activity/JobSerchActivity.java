package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.SearchHistoryBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.SearchHistoryUtils;
import com.hr.ui.ui.job.activity.CompanyPageActivity;
import com.hr.ui.ui.main.adapter.MyHotSearchAdapter;
import com.hr.ui.ui.main.contract.JobSearchContract;
import com.hr.ui.ui.main.modle.JobSearchModel;
import com.hr.ui.ui.main.presenter.JobSearchPresenter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/8.
 */

public class JobSerchActivity extends BaseActivity<JobSearchPresenter, JobSearchModel> implements JobSearchContract.View {
    @BindView(R.id.iv_jobSearchBack)
    ImageView ivJobSearchBack;
    @BindView(R.id.tv_searchIndustryName)
    TextView tvSearchIndustryName;
    @BindView(R.id.rl_selectIndustrySearch)
    RelativeLayout rlSelectIndustrySearch;
    @BindView(R.id.tv_selectCityName)
    TextView tvSelectCityName;
    @BindView(R.id.iv_imageArrow)
    ImageView ivImageArrow;
    @BindView(R.id.rl_selectCitySearch)
    RelativeLayout rlSelectCitySearch;
    @BindView(R.id.tv_jobSearchType)
    TextView tvJobSearchType;
    @BindView(R.id.rl_jobSearchType)
    RelativeLayout rlJobSearchType;
    @BindView(R.id.view_middle)
    View viewMiddle;
    @BindView(R.id.et_jobSearch)
    EditText etJobSearch;
    @BindView(R.id.tv_jobSearch)
    TextView tvJobSearch;
    @BindView(R.id.rl_jobSearchPlace)
    RelativeLayout rlJobSearchPlace;
    @BindView(R.id.rl_selectPositionSearch)
    RelativeLayout rlSelectPositionSearch;
    @BindView(R.id.iv_historyDelete)
    ImageView ivHistoryDelete;
    @BindView(R.id.rl_historyTitle)
    RelativeLayout rlHistoryTitle;
    @BindView(R.id.rl_jobSearchHistory)
    FlowLayout rlJobSearchHistory;
    @BindView(R.id.rv_hotSearch)
    RecyclerView rvHotSearch;
    @BindView(R.id.cl_searchJob)
    ConstraintLayout clSearchJob;
    @BindView(R.id.view_lineNoValue)
    View viewLineNoValue;
    @BindView(R.id.iv_locationAddress)
    ImageView ivLocationAddress;
    private PopupWindow popupWindowJobType;
    private List<SearchHistoryBean> historyBeanList;
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
    private SharedPreferencesUtils sUtils;

    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, JobSerchActivity.class);
        //Log.i("传到这里了",requestCode+"");
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    public static void startAction2(Activity activity) {
        Intent intent = new Intent(activity, JobSerchActivity.class);
        //Log.i("传到这里了",requestCode+"");
        activity.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getHotSearchJob(1, "3");
        historyBeanList = new ArrayList<>();
        historyBeanList = SearchHistoryUtils.queryAll();
        //BaiDuLocationUtils.getInstance().initData();
        if (historyBeanList != null && historyBeanList.size() != 0) {
            rlHistoryTitle.setVisibility(View.VISIBLE);
            rlJobSearchHistory.setVisibility(View.VISIBLE);
            initHistoryView();
        } else {
            rlHistoryTitle.setVisibility(View.GONE);
            rlJobSearchHistory.setVisibility(View.GONE);
        }
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
        sUtils = new SharedPreferencesUtils(this);
        cityName = sUtils.getStringValue(Constants.CITYNAME, "");
        if ("".equals(cityName)) {
            tvSelectCityName.setText("定位失败");
        } else {
            setPlace2(cityName);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        industryId = sUtils.getStringValue(Constants.INDUSTRY_ID, "");
        if (industryId.contains(",")) {
            industryId = industryId.substring(0,industryId.indexOf(","));
        }
        tvSearchIndustryName.setText(FromStringToArrayList.getInstance().getIndustryName(industryId));
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHotSearch.setLayoutManager(manager);
        DividerItemDecoration dividerDrawable = new DividerItemDecoration(this, 1);
        rvHotSearch.addItemDecoration(dividerDrawable);
        Utils.showSoftInputFromWindow(this, etJobSearch);
        etJobSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch();
                    InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        });
    }

    private void initHistoryView() {
        rlJobSearchHistory.removeAllViewsInLayout();
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
            /*Random random = new Random();
            GradientDrawable bgShape = (GradientDrawable) tv.getBackground();
//                    bgShape.setColor(Color.BLACK);
            bgShape.setStroke(1, ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_e4));
            bgShape.setCornerRadius(15);*/
            tv.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            ll.setLayoutParams(params);
            String name = "";
            if (historyBeanList.get(i).getSearchName() == null || "".equals(historyBeanList.get(i).getSearchName())) {
                name = FromStringToArrayList.getInstance().getExpectPositionName(historyBeanList.get(i).getPositionId(), historyBeanList.get(i).getIndustryId());
            } else {
                name = historyBeanList.get(i).getSearchName();
            }
            if (name.length() > 20) {
                name = name.substring(0, 20) + "...";
            }
            tv.setText("[" + FromStringToArrayList.getInstance().getIndustryName(historyBeanList.get(i).getIndustryId()) + "] " + name);
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
                    JobSearchResultActivity.startAction(JobSerchActivity.this, jobSearchBean);
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

    @OnClick({R.id.iv_historyDelete, R.id.iv_jobSearchBack, R.id.rl_jobSearchType, R.id.tv_jobSearch, R.id.rl_selectCitySearch, R.id.rl_selectIndustrySearch, R.id.rl_selectPositionSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_jobSearchBack:
                finish();
                break;
            case R.id.rl_jobSearchType:
                initPopSearchType();
                break;
            case R.id.tv_jobSearch:
                doSearch();
                break;
            case R.id.rl_selectCitySearch:
                setFocus();
                //SelectCityActivity.startAction(this, 2, TAG, selectCityList);
                SelectCitySearchActivity.startAction(this,selectCityList);
                //SelectCityNewActivity.startAction(this, selectCityList);
                break;
            case R.id.rl_selectIndustrySearch:
                setFocus();
                SelectIndustryActivity.startAction(JobSerchActivity.this, selectIndustryList);
                break;
            case R.id.rl_selectPositionSearch:
                setFocus();
                if (industryId != null && !"".equals(industryId)) {
                    SelectPositionActivity.startAction(this, industryId, cityId, TAG);
                } else {
                    ToastUitl.showShort("请选择行业");
                    return;
                }
                break;
            case R.id.iv_historyDelete:
                SearchHistoryUtils.deleteAll();
                rlJobSearchHistory.removeAllViewsInLayout();
                rlJobSearchHistory.setVisibility(View.GONE);
                rlHistoryTitle.setVisibility(View.GONE);
                break;
        }
    }

    private void setFocus() {
        clSearchJob.setFocusableInTouchMode(true);
        clSearchJob.setFocusable(true);
        clSearchJob.requestFocus();
        clSearchJob.findFocus();
    }

    private void doSearch() {
        JobSearchBean jobSearchBean = new JobSearchBean();
        SearchHistoryBean historyBean = new SearchHistoryBean();
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
      /*  if (isSearch == false) {
            if (etJobSearch.getText().toString() == null || "".equals(etJobSearch.getText().toString())) {
                if ("".equals(selectPositionList) || selectPositionList == null || selectPositionList.size() == 0) {
                    ToastUitl.showShort("职位与关键词至少选择一个");
                    return;
                }

            }
        }*/
        if (etJobSearch.getText().toString() != null && !"".equals(etJobSearch.getText().toString())) {
            jobSearchBean.setSearchName(etJobSearch.getText().toString());
            historyBean.setSearchName(etJobSearch.getText().toString());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
            historyBean.setAddDate(str);
            SearchHistoryUtils.insertJobSearchDataOrReplace(historyBean);
        } else {
            ToastUitl.showShort("请输入职位名/公司名");
            return;
        }
        JobSearchResultActivity.startAction(this, jobSearchBean);
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

    public void setPlace(List<CityBean> selectCityList1) {
        this.selectCityList.clear();
        this.selectCityList = selectCityList1;
        //Log.i("选择",selectFunctionList.toString());
        if (selectCityList != null && selectCityList.size() != 0) {
            StringBuffer sb = new StringBuffer();
            StringBuffer sbName = new StringBuffer();
            for (int i = 0; i < selectCityList.size(); i++) {
                sb.append("," + selectCityList.get(i).getId());
                sbName.append("，" + selectCityList.get(i).getName());
            }
            if (sb != null && !"".equals(sb)) {
                sb.deleteCharAt(0);
                cityId = sb.toString();
            }
            if (sbName != null && !"".equals(sbName)) {
                sbName.deleteCharAt(0);
                tvSelectCityName.setText(sbName.toString());
            }
        } else {
            tvSelectCityName.setText("不限");
            cityId = "";
        }
    }

    public void setPlace2(String cityName) {
        if (cityName != null && !"".equals(cityName)) {
            selectCityList.clear();
            tvSelectCityName.setText(cityName);
            cityId = FromStringToArrayList.getInstance().getCityListId(cityName);
            if (cityId != null && !"".equals(cityId)) {
                CityBean cityBean = new CityBean();
                cityBean.setId(cityId);
                cityBean.setName(cityName);
                selectCityList.add(cityBean);
            }
        }
    }

    /*
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
    }*/

    public void setIndustry(CityBean cityBean) {
        if (cityBean != null && !"".equals(cityBean)) {
           /* if (!cityBean.getId().equals(industryId)) {
                selectPositionList.clear();
                tvJobSearchFunction.setText("");
            }*/
            tvSearchIndustryName.setText(cityBean.getName());
            industryId = cityBean.getId();
        }
    }

   /* public void setPosition(List<CityBean> selectPositionList) {
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
    }*/

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

    @Override
    protected void onDestroy() {
        if(instance!=null){
            instance=null;
        }
        super.onDestroy();
    }
}
