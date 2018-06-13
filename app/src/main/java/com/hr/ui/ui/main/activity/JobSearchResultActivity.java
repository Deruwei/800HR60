package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CheckBean;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.bean.SearchHistoryBean;
import com.hr.ui.db.SearchHistoryUtils;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.adapter.MyRecommendJobAdapter;
import com.hr.ui.ui.main.adapter.MySalaryAdapter;
import com.hr.ui.ui.main.adapter.MySelectOtherAdapter;
import com.hr.ui.ui.main.contract.JobSearchFragmentContract;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.ui.main.modle.JobSearchFragmentModel;
import com.hr.ui.ui.main.presenter.JobSearchFragmentPresenter;
import com.hr.ui.ui.message.activity.WebActivity;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.utils.PopupWindowView;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyFlowLayout;
import com.hr.ui.view.MyRecommendDialog;
import com.hr.ui.view.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/3/15.
 */

public class JobSearchResultActivity extends BaseActivity<JobSearchFragmentPresenter, JobSearchFragmentModel> implements JobSearchFragmentContract.View {

    @BindView(R.id.rl_jobSearchFragmentBack)
    RelativeLayout rlJobSearchFragmentBack;
    @BindView(R.id.rl_jobSearchFragmentType)
    RelativeLayout rlJobSearchFragmentType;
    @BindView(R.id.iv_searchResultIcon)
    ImageView ivSearchResultIcon;
    @BindView(R.id.tv_searchResultName)
    TextView tvSearchResultName;
    @BindView(R.id.tv_jobSearchFragmentSelectCity)
    TextView tvJobSearchFragmentSelectCity;
    @BindView(R.id.iv_jobSearchFragmentSelectCity)
    ImageView ivJobSearchFragmentSelectCity;
    @BindView(R.id.rl_jobSearchFragmentSelectCity)
    RelativeLayout rlJobSearchFragmentSelectCity;
    @BindView(R.id.tv_jobSearchFragmentSelectOther)
    TextView tvJobSearchFragmentSelectOther;
    @BindView(R.id.iv_jobSearchFragmentSelectOther)
    ImageView ivJobSearchFragmentSelectOther;
    @BindView(R.id.rl_jobSearchFragmentSelectOther)
    RelativeLayout rlJobSearchFragmentSelectOther;
    @BindView(R.id.view_jobSearchFragment)
    View viewJobSearchFragment;
    @BindView(R.id.rv_jobSearchFragment)
    XRecyclerView rvJobSearchFragment;
    @BindView(R.id.cb_searchResultSelectAll)
    CheckBox cbSearchResultSelectAll;
    @BindView(R.id.tv_searchResultSelectAllName)
    TextView tvSearchResultSelectAllName;
    @BindView(R.id.searchResultNoSelectNum)
    TextView searchResultNoSelectNum;
    @BindView(R.id.tv_searchResultDeliverAll)
    TextView tvSearchResultDeliverAll;
    @BindView(R.id.rl_searchResultDeleteInABatches)
    RelativeLayout rlSearchResultDeleteInABatches;
    @BindView(R.id.cv_searchResult)
    CardView cvSearchResult;
    @BindView(R.id.iv_noContent)
    ImageView ivNoContent;
    @BindView(R.id.tv_noData)
    TextView tvNoData;
    @BindView(R.id.iv_noDataSearchIcon)
    ImageView ivNoDataSearchIcon;
    @BindView(R.id.iv_noDataSearch)
    RelativeLayout ivNoDataSearch;
    @BindView(R.id.rl_emptyView)
    RelativeLayout rlEmptyView;
    @BindView(R.id.rl_jobSearchFragmentTop)
    LinearLayout rlJobSearchFragmentTop;
    @BindView(R.id.rl_searchResultBack)
    RelativeLayout rlSearchResultBack;
    @BindView(R.id.rl_searchResultSelectAll)
    RelativeLayout rlSearchResultSelectAll;
    private PopupWindow popupWindowJobType, popupWindowOther,popupWindowSalary;
    private JobSearchBean jobSearchBean;
    //传过来的搜索类型
    private int jobSearchType;
    //筛选的id值
    private String placeId, searchWord, industryIdMain, fieldId, positionId, salaryAroundName, workExpId, degreeNeedId, releaseTimeId, workTypeId, companyTypeId, salary_left, salary_right;
    //省会列表集合
    private List<CityBean> provinceCityList = new ArrayList<>();
    //城市列表集合
    private List<CityBean> selectCityList = new ArrayList<>();
    //选择工作经验集合
    private List<CityBean> selectWorkExpList = new ArrayList<>();
    //选择学历要求集合
    private List<CityBean> selectDegreeNeedList = new ArrayList();
    //选择发布时间集合
    private List<CityBean> selectReleaseTimeList = new ArrayList<>();
    //选择工作性质集合
    private List<CityBean> selectWorkTypeList = new ArrayList<>();
    //选择公司性质集合
    private List<CityBean> selectCompanyTypeList = new ArrayList<>();
    private MyRecommendJobAdapter SearchAdapter;
    private List<CheckBean> list;
    //判断点击批量投递的状态
    private boolean isCanSelectDeliver;
    //投递职位和简历求职意向不相同或者简历不完整弹出的dialog
    private MyRecommendDialog dialog;
    //搜索职位集合
    private List<RecommendJobBean.JobsListBean> searchList = new ArrayList<>();
    //置顶职位集合
    private List<RecommendJobBean.JobsListBean> topSearchList = new ArrayList<>();
    private List<FindBean.ListBean> adsList;
    private List<CityBean> selectSalatyList=new ArrayList<>();
    private  List<CityBean> salary=new ArrayList<>();
    //页码，批量投递没有选择的列表数
    public int page = 1, notSelectNum;
    public static JobSearchResultActivity instance;
    private boolean isCityShow,isSalaryShow,isOtherShow;
    private SharedPreferencesUtils sUtils;
    public static String TAG=JobSearchResultActivity.class.getSimpleName();

    public static void startAction(Activity activity, JobSearchBean jobSearchBean) {
        Intent intent = new Intent(activity, JobSearchResultActivity.class);
        intent.putExtra("jobSearch", jobSearchBean);
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
    public void getSearchDataSuccess(final List<RecommendJobBean.JobsListBean> jobsListBeanList) {
        MobclickAgent.onEvent(this, "v6_positon_search");
        if(jobSearchBean.getPositionId()!=null&&!"".equals(jobSearchBean.getPositionId())){
            MobclickAgent.onEvent(this,"v6_jobSearchByPosition");
        }else{
            MobclickAgent.onEvent(this,"v6_jobSearchByKeyword");
        }
        if (jobsListBeanList != null  && jobsListBeanList.size() != 0) {
            rlSearchResultDeleteInABatches.setVisibility(View.VISIBLE);
            if (page == 1) {
                SearchAdapter = new MyRecommendJobAdapter(this,1);
                SearchAdapter.setCheck(true);
                searchList.clear();
                if (topSearchList != null && !"".equals(topSearchList) && topSearchList.size() != 0) {
                    for (int i = 0; i < topSearchList.size(); i++) {
                        topSearchList.get(i).setTop(true);
                    }
                    searchList.addAll(topSearchList);
                }
                if(adsList!=null&&adsList.size()!=0){
                    int n=Utils.getRandomInt(list,adsList.size());
                    SearchAdapter.setAdsList(adsList,n);
                }
                searchList.addAll(jobsListBeanList);
                SearchAdapter.setJobsListBeanList(searchList);
                rvJobSearchFragment.setAdapter(SearchAdapter);
                rvJobSearchFragment.refreshComplete();
              /*  rvJobSearchFragment.refresh();*/
            } else {
                //SearchAdapter = new MyRecommendJobAdapter(this,1);
                searchList.addAll(jobsListBeanList);
                rvJobSearchFragment.loadMoreComplete();
                SearchAdapter.notifyDataSetChanged();
            }
            getNoSelectNum(searchList);
            rlEmptyView.setVisibility(View.GONE);
            rvJobSearchFragment.setVisibility(View.VISIBLE);
        } else {
            if (page == 1) {
                if(adsList!=null&&adsList.size()!=0){
                    SearchAdapter = new MyRecommendJobAdapter(this,1);
                    int n=Utils.getRandomInt(list,adsList.size());
                    SearchAdapter.setAdsList(adsList,n);
                    rvJobSearchFragment.setAdapter(SearchAdapter);
                    rvJobSearchFragment.refreshComplete();
                    rlSearchResultDeleteInABatches.setVisibility(View.GONE);
                }else {
                    rlEmptyView.setVisibility(View.VISIBLE);
                    ivNoDataSearch.setVisibility(View.GONE);
                    rvJobSearchFragment.setVisibility(View.GONE);
                }
            } else {
                rvJobSearchFragment.setNoMore(true);
            }
        }
        SearchAdapter.setClickCallBack(new MyRecommendJobAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                PositionPageActivity.startAction(JobSearchResultActivity.this, searchList.get(pos).getJob_id(),pos,TAG);
            }
        });
        SearchAdapter.setOnCheckListener(new MyRecommendJobAdapter.OnCheckListener() {
            @Override
            public void onCheckListener(int pos, boolean b) {
                if(notSelectNum==0){
                    if(b==true) {
                        ToastUitl.showShort("只能勾选20个职位");
                        searchList.get(pos).setCheck(false);
                        SearchAdapter.notifyDataSetChanged();
                    }else{
                        searchList.get(pos).setCheck(b);
                        getNoSelectNum(searchList);
                        SearchAdapter.notifyDataSetChanged();
                    }
                }else {
                    searchList.get(pos).setCheck(b);
                    getNoSelectNum(searchList);
                    SearchAdapter.notifyDataSetChanged();
                }
            }
        });
        if(adsList!=null&&adsList.size()!=0){
            SearchAdapter.setOnClickAdsListener(new MyRecommendJobAdapter.OnClickAdsListener() {
                @Override
                public void onClick(int pos) {
                    WebActivity.startAction(JobSearchResultActivity.this,adsList.get(pos).getTopic_url());
                }
            });
        }
        /*rvJobSearchFragment.refreshComplete();*/
    }

    @Override
    public void getTopSearchJobSuccess(List<RecommendJobBean.JobsListBean> jobsListBeans) {
        topSearchList.clear();
        topSearchList.addAll(jobsListBeans);
    }

    @Override
    public void getTopSearchFaild() {
        topSearchList.clear();
    }

    @Override
    public void deliverPositionSuccess() {
        MobclickAgent.onEvent(this, "v6_resume_deliver");
        ToastUitl.showShort("投递成功");
        for (int i = 0; i < searchList.size(); i++) {
            if (searchList.get(i).isCheck()) {
                searchList.get(i).setIs_apply(1);
                searchList.get(i).setCheck(false);
            }
        }
        isCanSelectDeliver=false;
        SearchAdapter.setCheck(true);
        getNoSelectNum(searchList);
        cbSearchResultSelectAll.setChecked(false);
        SearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void goToCompleteResume(int errorCode) {
        if (errorCode == 413 || errorCode == 417) {
            setPopupwindow(2);
        } else {
            setPopupwindow(1);
        }
    }

    @Override
    public void getNoticeSuccess(List<FindBean.ListBean> listBean) {
        adsList=new ArrayList<>();
        adsList.addAll(listBean);
        if(adsList!=null&&adsList.size()!=0){
            list=new ArrayList<>();
            for(int i=0;i<adsList.size();i++){
                CheckBean cb=new CheckBean();
                cb.setCheck(false);
                cb.setNum(i);
                list.add(cb);
            }
        }
    }

    /**
     * 简历不符合跳出提示框
     *
     * @param type
     */
    private void setPopupwindow(int type) {
        dialog = new MyRecommendDialog(this);
        if (type == 1) {
            dialog.setTitle(getString(R.string.resumeNoComplete));
            dialog.setMessage(getString(R.string.resumeNoCompleteContent));
            dialog.setNoOnclickListener(getString(R.string.known), new MyRecommendDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                }
            });
            dialog.setYesOnclickListener(getString(R.string.goNow), new MyRecommendDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
                    finish();
                    MainActivity.instance.rbResume1.setChecked(true);
                    ResumeFragment.instance.refresh();
                }
            });
        } else {
            dialog.setTitle(getString(R.string.noIndustry1) + FromStringToArrayList.getInstance().getIndustryName(industryIdMain) + "行业" + getString(R.string.noIndustry2));
            dialog.setMessage(getString(R.string.noJobOrderContent1) + FromStringToArrayList.getInstance().getIndustryName(industryIdMain) + "行业" + getString(R.string.noJobOrderContent2));
            dialog.setNoOnclickListener(getString(R.string.known), new MyRecommendDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                }
            });
            dialog.setYesOnclickListener(getString(R.string.goNow), new MyRecommendDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
                    ResumeJobOrderActivity.startAction(JobSearchResultActivity.this);
                }
            });
        }
        dialog.show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jobserch;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    /**
     * 数据请求
     *
     * @param jobSearchBean
     */
    public void initData(JobSearchBean jobSearchBean, boolean b) {
        int page = 1;
        mPresenter.getSearchList(jobSearchBean, page, b);
        mPresenter.getTopSearchJob(jobSearchBean);
    }
   public void notificationDataItem(int position){
       searchList.get(position).setIs_apply(1);
       searchList.get(position).setCheck(false);
       SearchAdapter.notifyDataSetChanged();
       getNoSelectNum(searchList);

    }
    @Override
    public void initView() {
        instance = this;
        jobSearchBean = (JobSearchBean) getIntent().getSerializableExtra("jobSearch");
        MobclickAgent.onEvent(this, "v6_scan_searchResultPage");
        industryIdMain = jobSearchBean.getIndustryId();
        fieldId = jobSearchBean.getFieldId();
        searchWord = jobSearchBean.getSearchName();
        positionId = jobSearchBean.getPositionId();

        if (searchWord == null || "null".equals(searchWord)) {
            searchWord = "";

        } else {
            tvSearchResultName.setText(searchWord);
        }
        workExpId=jobSearchBean.getWorkExp();
        degreeNeedId=jobSearchBean.getDegree();
        releaseTimeId=jobSearchBean.getJobTime();
        workTypeId=jobSearchBean.getWorkType();
        companyTypeId=jobSearchBean.getCompanyType();
        salary_left=jobSearchBean.getSalary_left();
        salary_right=jobSearchBean.getSalary_right();
        //Log.i("现在的数据",salary_left+"-"+salary_right);
        selectSalatyList=ResumeInfoIDToString.getSelectSalaryAroundList(this,Utils.getSalaryAround(salary_left+"-"+salary_right));
        //Log.i("现在的数据",selectSalatyList.toString());
        if(workExpId!=null&&!"".equals(workExpId)) {
            selectWorkExpList=ResumeInfoIDToString.getSelectWorkExp(this, workExpId);
        }
        if(degreeNeedId!=null&&!"".equals(degreeNeedId)){
            selectDegreeNeedList=ResumeInfoIDToString.getSelectDegreeNeed(this,degreeNeedId);
        }
        if(releaseTimeId!=null&&!"".equals(releaseTimeId)){
            selectReleaseTimeList=ResumeInfoIDToString.getSelectReleaseTime(this,releaseTimeId);
        }
        if(workTypeId!=null&&!"".equals(workTypeId)){
            selectWorkTypeList=ResumeInfoIDToString.getSelectWorkType(this,workTypeId);
        }
        if(companyTypeId!=null&&!"".equals(companyTypeId)){
            selectCompanyTypeList=ResumeInfoIDToString.getSelectCompanyType(this,companyTypeId);
        }
        placeId = jobSearchBean.getPlaceId();
        if (placeId != null && !"".equals(placeId)) {
            selectCityList = FromStringToArrayList.getInstance().getSelectCityList(placeId);
        }
        jobSearchType = jobSearchBean.getJobType();
        SearchAdapter = new MyRecommendJobAdapter(this,1);
        mPresenter.getNotice("99","");
        initData(jobSearchBean, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvJobSearchFragment.setLayoutManager(linearLayoutManager);
        rvJobSearchFragment.setRefreshProgressStyle(ProgressStyle.BallPulse);
        rvJobSearchFragment.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        rvJobSearchFragment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = 1;
                        mPresenter.getTopSearchJob(jobSearchBean);
                        mPresenter.getSearchList(jobSearchBean, page, false);

                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        mPresenter.getSearchList(jobSearchBean, page, false);
                    }
                }, 1000);
            }
        });
        cbSearchResultSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setSearchList(true);
                    } else {
                        setSearchList(false);
                    }
                    getNoSelectNum(searchList);
            }
        });
        rlSearchResultSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!cbSearchResultSelectAll.isChecked()) {
                        cbSearchResultSelectAll.setChecked(true);
                        setSearchList(true);
                    } else {
                        cbSearchResultSelectAll.setChecked(false);
                        setSearchList(false);
                    }
                    getNoSelectNum(searchList);
            }
        });

    }
    private void setShowWatch(int type){
       switch (type){
           case 1:
              isCityShow=!isCityShow;
               isOtherShow=false;
               isSalaryShow=false;
               break;
           case 2:
               isCityShow=false;
               isOtherShow=false;
               isSalaryShow=!isSalaryShow;
               break;
           case 3:
               isCityShow=false;
               isOtherShow=!isOtherShow;
               isSalaryShow=false;
               break;
       }
    }
    private void setSearchList(boolean b) {
        int num=0;
        int selectNum=0;
        for(int j=0;j<searchList.size();j++){
            if(searchList.get(j).isCheck()){
                selectNum++;
            }
        }
        if (searchList != null && searchList.size() != 0) {
            for (int i = 0; i < searchList.size(); i++) {
                if (!b) {
                    if (searchList.get(i).getIs_apply() != 1) {
                            searchList.get(i).setCheck(b);
                    }
                } else {
                    if(num<20-selectNum) {
                        if(searchList.get(i).getIs_apply()!=1 ){
                            searchList.get(i).setCheck(b);
                            num++;
                        }
                    }else{
                        break;
                    }
                }
            }
            SearchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance = this;
    }
    public void doSearch(boolean b) {
       // rlSearchResultDeleteInABatches.setVisibility(View.GONE);
        SearchAdapter.setCheck(true);
        cbSearchResultSelectAll.setChecked(false);
        isCanSelectDeliver=false;
        if (searchWord != null && !"".equals(searchWord)) {
            jobSearchBean.setSearchName(searchWord);
        } else {
            if(positionId!=null&&!"".equals(positionId)) {
                jobSearchBean.setSearchName(FromStringToArrayList.getInstance().getExpectPositionName(positionId,industryIdMain));
            }else {
                jobSearchBean.setSearchName("");
            }
        }
        jobSearchBean.setJobType(jobSearchType);
        if (placeId != null && !"".equals(placeId)) {
            jobSearchBean.setPlaceId(placeId);
        } else {
            jobSearchBean.setPlaceId("");
        }
        if (industryIdMain != null && !"".equals(industryIdMain)) {
            jobSearchBean.setIndustryId(industryIdMain);
        } else {
            jobSearchBean.setIndustryId("");
        }
        if (!"".equals(fieldId) && fieldId != null) {
            jobSearchBean.setFieldId(fieldId);
        } else {
            jobSearchBean.setFieldId("");
        }
        if (!"".equals(positionId) && positionId != null) {
            jobSearchBean.setPositionId(positionId);
        } else {
            jobSearchBean.setPositionId("");
        }
        if (!"".equals(releaseTimeId) && releaseTimeId != null) {
            jobSearchBean.setJobTime(releaseTimeId);
        } else {
            jobSearchBean.setJobTime("");
        }
        if (!"".equals(workExpId) && workExpId != null) {
            jobSearchBean.setWorkExp(workExpId);
        } else {
            jobSearchBean.setWorkExp("");
        }
        if (!"".equals(workTypeId) && workTypeId != null) {
            jobSearchBean.setWorkType(workTypeId);
        } else {
            jobSearchBean.setWorkType("");
        }
        if (!"".equals(companyTypeId) && companyTypeId != null) {
            jobSearchBean.setCompanyType(companyTypeId);
        } else {
            jobSearchBean.setCompanyType("");
        }
        if (degreeNeedId != null && !"".equals(degreeNeedId)) {
            jobSearchBean.setDegree(degreeNeedId);
        } else {
            jobSearchBean.setDegree("");
        }
        if (salary_right != null && !"".equals(salary_right)) {
            jobSearchBean.setSalary_right(salary_right);
        } else {
            jobSearchBean.setSalary_right("");
        }
        if (salary_left != null && !"".equals(salary_left)) {
            jobSearchBean.setSalary_left(salary_left);
        } else {
            jobSearchBean.setSalary_left("");
        }
        SearchHistoryBean historyBean = new SearchHistoryBean();
        historyBean.setCompanyType(jobSearchBean.getCompanyType());
        historyBean.setSearchName(jobSearchBean.getSearchName());
        historyBean.setDegree(jobSearchBean.getDegree());
        historyBean.setSalary_right(jobSearchBean.getSalary_right());
        historyBean.setSalary_left(jobSearchBean.getSalary_left());
        historyBean.setCompanyScale(jobSearchBean.getCompanyScale());
        historyBean.setWorkType(jobSearchBean.getWorkType());
        historyBean.setWorkExp(jobSearchBean.getWorkExp());
        historyBean.setJobTime(jobSearchBean.getJobTime());
        historyBean.setFieldId(jobSearchBean.getFieldId());
        historyBean.setPositionId(jobSearchBean.getPositionId());
        historyBean.setIndustryId(jobSearchBean.getIndustryId());
        historyBean.setPlaceId(jobSearchBean.getPlaceId());
        historyBean.setJobType(jobSearchBean.getJobType());
        if (!"".equals(historyBean.getSearchName()) && historyBean.getSearchName() != null) {
            SearchHistoryUtils.insertJobSearchDataOrReplace(historyBean);
        }
        initData(jobSearchBean, true);

        //rvJobSearchFragment.refresh();
    }
    private void initPopOther() {
        if(popupWindowSalary!=null){
            popupWindowSalary.dismiss();
        }
        if(popupWindowSalary!=null){
            popupWindowSalary.dismiss();

        }
        View viewOther = getLayoutInflater().inflate(R.layout.layout_selectother, null);
        popupWindowOther = new PopupWindow(viewOther, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        RecyclerView rvWorkExp = viewOther.findViewById(R.id.rv_selectOtherWorkExp);
        RecyclerView rvDegreeNeed = viewOther.findViewById(R.id.rv_selectOtherDegreeNeed);
        RecyclerView rvReleaseTime = viewOther.findViewById(R.id.rv_selectOtherReleaseTime);
        RecyclerView rvWorkType = viewOther.findViewById(R.id.rv_selectOtherWorkType);
        RecyclerView rvCompanyType = viewOther.findViewById(R.id.rv_selectOtherCompanyType);
        TextView tvOK = viewOther.findViewById(R.id.tv_selectOtherOK);
        TextView tvCancel = viewOther.findViewById(R.id.tv_selectOtherCancel);
        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSaveOther();
                page = 1;
                popupWindowOther.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowOther.dismiss();
            }
        });
        final List<CityBean> workExopList = ResumeInfoIDToString.getWorkExp(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rvWorkExp.setLayoutManager(manager);

        GridLayoutManager manager2 = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        manager2.setOrientation(GridLayoutManager.VERTICAL);
        rvDegreeNeed.setLayoutManager(manager2);

        GridLayoutManager manager3 = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        manager3.setOrientation(GridLayoutManager.VERTICAL);
        rvReleaseTime.setLayoutManager(manager3);

        GridLayoutManager manager4 = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        manager4.setOrientation(GridLayoutManager.VERTICAL);
        rvWorkType.setLayoutManager(manager4);

        GridLayoutManager manager5 = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        manager5.setOrientation(GridLayoutManager.VERTICAL);
        rvCompanyType.setLayoutManager(manager5);

        final List<CityBean> releaseTimeList = ResumeInfoIDToString.getReleaseTime(this);
        if (selectReleaseTimeList != null && selectReleaseTimeList.size() != 0) {
            for (int i = 0; i < selectReleaseTimeList.size(); i++) {
                for (int j = 0; j < releaseTimeList.size(); j++) {
                    if (selectReleaseTimeList.get(i).getId().equals(releaseTimeList.get(j).getId())) {
                        releaseTimeList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        } else {
            selectReleaseTimeList.add(releaseTimeList.get(0));
            releaseTimeList.get(0).setCheck(true);
        }
        final MySelectOtherAdapter releaseTimeAdapter = new MySelectOtherAdapter(this, releaseTimeList);
        rvReleaseTime.setAdapter(releaseTimeAdapter);
        releaseTimeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (releaseTimeList.get(position).isCheck() == true) {
                    Utils.setListCheckFalse(releaseTimeList);
                    releaseTimeList.get(0).setCheck(true);
                    selectReleaseTimeList.clear();
                    selectReleaseTimeList.add(releaseTimeList.get(0));
                } else {
                    Utils.setListCheckFalse(releaseTimeList);
                    releaseTimeList.get(position).setCheck(true);
                    selectReleaseTimeList.clear();
                    selectReleaseTimeList.add(releaseTimeList.get(position));
                }
                releaseTimeId = FromStringToArrayList.getInstance().getDataId(selectReleaseTimeList);
                releaseTimeAdapter.notifyDataSetChanged();
            }
        });

        final List<CityBean> degreeNeedList = ResumeInfoIDToString.getDegreeNeed(this);
        if (selectDegreeNeedList != null && selectDegreeNeedList.size() != 0) {
            for (int i = 0; i < selectDegreeNeedList.size(); i++) {
                for (int j = 0; j < degreeNeedList.size(); j++) {
                    if (selectDegreeNeedList.get(i).getId().equals(degreeNeedList.get(j).getId())) {
                        degreeNeedList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        } else {
            selectDegreeNeedList.add(degreeNeedList.get(0));
            degreeNeedList.get(0).setCheck(true);
        }
        final MySelectOtherAdapter degreeNeedAdapter = new MySelectOtherAdapter(this, degreeNeedList);
        rvDegreeNeed.setAdapter(degreeNeedAdapter);
        degreeNeedAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (degreeNeedList.get(position).isCheck() == true) {
                    Utils.setListCheckFalse(degreeNeedList);
                    degreeNeedList.get(0).setCheck(true);
                    selectDegreeNeedList.clear();
                    selectDegreeNeedList.add(degreeNeedList.get(0));
                } else {
                    Utils.setListCheckFalse(degreeNeedList);
                    degreeNeedList.get(position).setCheck(true);
                    selectDegreeNeedList.clear();
                    selectDegreeNeedList.add(degreeNeedList.get(position));
                }
                degreeNeedId = FromStringToArrayList.getInstance().getDataId(selectDegreeNeedList);
                degreeNeedAdapter.notifyDataSetChanged();
            }
        });
        if (selectWorkExpList != null && !"".equals(selectWorkExpList) && selectWorkExpList.size() != 0) {
            for (int i = 0; i < selectWorkExpList.size(); i++) {
                for (int j = 0; j < workExopList.size(); j++) {
                    if (selectWorkExpList.get(i).getId().equals(workExopList.get(j).getId())) {
                        workExopList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        } else {
            selectWorkExpList.add(workExopList.get(0));
            workExopList.get(0).setCheck(true);
        }
        final MySelectOtherAdapter workExpAdapter = new MySelectOtherAdapter(this, workExopList);
        rvWorkExp.setAdapter(workExpAdapter);
        workExpAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (workExopList.get(position).isCheck() == true) {
                    Utils.setListCheckFalse(workExopList);
                    workExopList.get(0).setCheck(true);
                    selectWorkExpList.clear();
                    selectWorkExpList.add(workExopList.get(0));
                } else {
                    Utils.setListCheckFalse(workExopList);
                    workExopList.get(position).setCheck(true);
                    selectWorkExpList.clear();
                    selectWorkExpList.add(workExopList.get(position));
                }
                workExpId = FromStringToArrayList.getInstance().getDataId(selectWorkExpList);
                workExpAdapter.notifyDataSetChanged();
            }
        });
        final List<CityBean> workTypeList = ResumeInfoIDToString.getWorkType(this);
        if (selectWorkTypeList != null && selectWorkTypeList.size() != 0) {
            for (int i = 0; i < selectWorkTypeList.size(); i++) {
                for (int j = 0; j < workTypeList.size(); j++) {
                    if (selectWorkTypeList.get(i).getId().equals(workTypeList.get(j).getId())) {
                        workTypeList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        } else {
            selectWorkTypeList.add(workTypeList.get(0));
            workTypeList.get(0).setCheck(true);
        }
        final MySelectOtherAdapter workTypeAdapter = new MySelectOtherAdapter(this, workTypeList);
        rvWorkType.setAdapter(workTypeAdapter);
        workTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (workTypeList.get(position).isCheck() == true) {
                    Utils.setListCheckFalse(workTypeList);
                    workTypeList.get(0).setCheck(true);
                    selectWorkTypeList.clear();
                    selectWorkTypeList.add(workTypeList.get(0));
                } else {
                    Utils.setListCheckFalse(workTypeList);
                    workTypeList.get(position).setCheck(true);
                    selectWorkTypeList.clear();
                    selectWorkTypeList.add(workTypeList.get(position));
                }
                workTypeId = FromStringToArrayList.getInstance().getDataId(selectWorkTypeList);
                workTypeAdapter.notifyDataSetChanged();
            }
        });

        final List<CityBean> companyTypeList = ResumeInfoIDToString.getCompanyType(this);
        if (selectCompanyTypeList != null && selectCompanyTypeList.size() != 0) {
            for (int i = 0; i < selectCompanyTypeList.size(); i++) {
                for (int j = 0; j < companyTypeList.size(); j++) {
                    if (selectCompanyTypeList.get(i).getId().equals(companyTypeList.get(j).getId())) {
                        companyTypeList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        } else {
            selectCompanyTypeList.add(companyTypeList.get(0));
            companyTypeList.get(0).setCheck(true);
        }
        final MySelectOtherAdapter companyTypeAdapter = new MySelectOtherAdapter(this, companyTypeList);
        rvCompanyType.setAdapter(companyTypeAdapter);
        companyTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (companyTypeList.get(position).isCheck() == true) {
                    Utils.setListCheckFalse(companyTypeList);
                    companyTypeList.get(0).setCheck(true);
                    selectCompanyTypeList.clear();
                    selectCompanyTypeList.add(companyTypeList.get(0));
                } else {
                    Utils.setListCheckFalse(companyTypeList);
                    companyTypeList.get(position).setCheck(true);
                    selectCompanyTypeList.clear();
                    selectCompanyTypeList.add(companyTypeList.get(position));
                }
                companyTypeId = FromStringToArrayList.getInstance().getDataId(selectCompanyTypeList);
                companyTypeAdapter.notifyDataSetChanged();
            }
        });
        int[] location = new int[2];
        viewJobSearchFragment.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        getResources().getStringArray(R.array.array_skilllevel_zh);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        popupWindowOther.setWidth(width);
        popupWindowOther.setHeight(height - y);
        viewJobSearchFragment.getLocationOnScreen(location);
        popupWindowOther.setOutsideTouchable(true);
        popupWindowOther.setAnimationStyle(R.style.style_pop_animation);
        popupWindowOther.showAtLocation(viewJobSearchFragment, Gravity.NO_GRAVITY, 0, y + viewJobSearchFragment.getHeight());
    }
    private void doSaveOther() {
        doSearch(true);
    }
    private void initPopCity() {
       if(popupWindowSalary!=null){
           popupWindowSalary.dismiss();
       }
       if(popupWindowOther!=null){
           popupWindowOther.dismiss();
       }
        View viewIndustry = getLayoutInflater().inflate(R.layout.layout_selectcity, null);
        popupWindowJobType = new PopupWindow(viewIndustry, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tvCancel = viewIndustry.findViewById(R.id.tv_selectCityCancel);
        TextView tvOk = viewIndustry.findViewById(R.id.tv_selectCityOK);
        ListView lvLeft = viewIndustry.findViewById(R.id.lv_selectProvince);
        ListView lvRight = viewIndustry.findViewById(R.id.lv_selectCity);
        RelativeLayout rlNoSelect = viewIndustry.findViewById(R.id.rl_selectCityShow);
        TextView tvNum = viewIndustry.findViewById(R.id.tv_selectCityNum);
        MyFlowLayout flData = viewIndustry.findViewById(R.id.ll_selectedCity);
        int[] location = new int[2];
        viewJobSearchFragment.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        PopupWindowView popupWindowView = new PopupWindowView(popupWindowJobType, page, selectCityList, tvCancel, tvOk, flData, lvRight, lvLeft, this, rlNoSelect, tvNum);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        popupWindowJobType.setWidth(width);
        popupWindowJobType.setHeight(height - y);
        viewJobSearchFragment.getLocationOnScreen(location);
        popupWindowJobType.setOutsideTouchable(true);
        popupWindowJobType.setAnimationStyle(R.style.style_pop_animation);
        popupWindowJobType.showAtLocation(viewJobSearchFragment, Gravity.NO_GRAVITY, 0, y + viewJobSearchFragment.getHeight());
    }
    public void setPlaceId(List<CityBean> selectCityList1) {
        if (!"".equals(selectCityList1) && selectCityList1 != null && selectCityList1.size() != 0) {
            selectCityList = new ArrayList<>();
            selectCityList = selectCityList1;
            //Log.i("您好",selectCityList.toString());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < selectCityList.size(); i++) {
                sb.append("," + selectCityList.get(i).getId());
            }
            if (sb != null && !"".equals(sb)) {
                sb.deleteCharAt(0);
            }
            placeId = sb.toString();
        } else {
            placeId = "";
        }
    }
    @OnClick({R.id.tv_searchResultDeliverAll, R.id.rl_searchResultBack,R.id.rl_jobSearchFragmentSelectSalary, R.id.rl_jobSearchFragmentBack, R.id.rl_jobSearchFragmentSelectCity, R.id.rl_jobSearchFragmentSelectOther})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_jobSearchFragmentBack:
                finish();
                break;
            case R.id.rl_searchResultBack:
                finish();
                break;
            case R.id.rl_jobSearchFragmentSelectCity:

                if(isCityShow){
                    if(popupWindowJobType!=null) {
                        popupWindowJobType.dismiss();
                    }
                }else {
                    initPopCity();
                }
                setShowWatch(1);
                break;
            case R.id.rl_jobSearchFragmentSelectSalary:

                if(isSalaryShow){
                    if(popupWindowSalary!=null) {
                        popupWindowSalary.dismiss();
                    }
                }else {
                    initPopSalary();
                }
                setShowWatch(2);
                break;
            case R.id.rl_jobSearchFragmentSelectOther:

                if(isOtherShow){
                    if(popupWindowOther!=null) {
                        popupWindowOther.dismiss();
                    }
                }else {
                    initPopOther();
                }
                setShowWatch(3);
                break;
            case R.id.tv_searchResultDeliverAll:
                if (searchList != null && searchList.size() != 0) {
                    deliverAll();
                    MobclickAgent.onEvent(this,"v6_deliverByQuery");
                }
                break;
        }
    }

    private void initPopSalary() {
        if(popupWindowJobType!=null){
            popupWindowJobType.dismiss();
        }
        if(popupWindowOther!=null){
            popupWindowOther.dismiss();
        }
        View viewSalary = getLayoutInflater().inflate(R.layout.layout_choosesalary, null);
        popupWindowSalary = new PopupWindow(viewSalary, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        RecyclerView rvSalary=viewSalary.findViewById(R.id.rv_chooseSalary);
       Button btnCancel=viewSalary.findViewById(R.id.btn_SalaryCancel);
       Button btnOk=viewSalary.findViewById(R.id.btn_SalaryOK);
        salary=ResumeInfoIDToString.getSalaryAroundList(this);
        if(selectSalatyList!=null&&selectSalatyList.size()!=0){
            for(int i=0;i<salary.size();i++){
                if(salary.get(i).getId().equals(selectSalatyList.get(0).getId())){
                    salary.get(i).setCheck(true);
                    break;
                }
            }
        }else{
            salary.get(0).setCheck(true);
        }
        int[] location = new int[2];
        viewJobSearchFragment.getLocationOnScreen(location);
        GridLayoutManager manager = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rvSalary.setLayoutManager(manager);
        final MySalaryAdapter adapter=new MySalaryAdapter(this,salary);
        rvSalary.setAdapter(adapter);
       adapter.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void OnItemCLick(View view, int position) {
                selectSalatyList.clear();
                selectSalatyList.add(salary.get(position));
                for(int i=0;i<salary.size();i++){
                    salary.get(i).setCheck(false);
                }
                salary.get(position).setCheck(true);
               salaryAroundName = salary.get(position).getName();
               salary_left = Utils.getLeftSalary(salaryAroundName);
               salary_right = Utils.getRightSalary(salaryAroundName);
               adapter.notifyDataSetChanged();
           }
       });
       btnOk.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               page=1;
               doSearch(true);
               popupWindowSalary.dismiss();
           }
       });
       btnCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               popupWindowSalary.dismiss();
           }
       });
        int x = location[0];
        int y = location[1];
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        popupWindowSalary.setWidth(width);
        popupWindowSalary.setHeight(height - y);
        viewJobSearchFragment.getLocationOnScreen(location);
        popupWindowSalary.setOutsideTouchable(true);
        popupWindowSalary.setAnimationStyle(R.style.style_pop_animation);
        popupWindowSalary.showAtLocation(viewJobSearchFragment, Gravity.NO_GRAVITY, 0, y + viewJobSearchFragment.getHeight());
    }

    private void deliverAll() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < searchList.size(); i++) {
            if (searchList.get(i).isCheck() == true && searchList.get(i).getIs_apply() != 1) {
                sb.append("," + searchList.get(i).getJob_id());
            }
        }
        if (sb.length() == 0) {
            ToastUitl.showShort("请选择您需要投递的职位");
            return;
        } else {
            sb.deleteCharAt(0);
            //Log.i("现在的id",sb.toString());
            mPresenter.deliverPosition(sb.toString());
        }
    }
    private void getNoSelectNum(List<RecommendJobBean.JobsListBean> jobsListBeanList) {
        int selectNum = 0;
        notSelectNum = 0;
        String str = "";
        if (jobsListBeanList != null && jobsListBeanList.size() != 0) {
            for (int i = 0; i < jobsListBeanList.size(); i++) {
                if (jobsListBeanList.get(i).isCheck()&&jobsListBeanList.get(i).getIs_apply()!=1) {
                    selectNum++;
                }
            }
            notSelectNum = 20 - selectNum;
            str = getString(R.string.selectTip1) + notSelectNum + getString(R.string.selectTip2);
            char[] s = str.toCharArray();
            SpannableString ss = new SpannableString(str);
            for (int i = 0; i < s.length; i++) {
                if (Utils.isNum(String.valueOf(s[i]))) {
                    ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.new_main)), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }     //设置指定位置textview的背景颜色
            //设置指定位置文字的颜色
            searchResultNoSelectNum.setText(ss);
        }
       /* if(selectNum==0){
            rlSearchResultDeleteInABatches.setVisibility(View.GONE);
        }else{
            rlSearchResultDeleteInABatches.setVisibility(View.VISIBLE);
        }*/

    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }
}
