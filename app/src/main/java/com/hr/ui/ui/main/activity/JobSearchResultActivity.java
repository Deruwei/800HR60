package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.bean.SearchHistoryBean;
import com.hr.ui.db.SearchHistoryUtils;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.adapter.MyRecommendJobAdapter;
import com.hr.ui.ui.main.adapter.MySelectOtherAdapter;
import com.hr.ui.ui.main.contract.JobSearchFragmentContract;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.ui.main.modle.JobSearchFragmentModel;
import com.hr.ui.ui.main.presenter.JobSearchFragmentPresenter;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.utils.PopupWindowFieldView;
import com.hr.ui.utils.PopupWindowView;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyFlowLayout;
import com.hr.ui.view.MyRecommendDialog;
import com.hr.ui.view.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tv_jobSearchFragmentSelectLingYu)
    TextView tvJobSearchFragmentSelectLingYu;
    @BindView(R.id.rl_jobSearchFragmentSelectLingYu)
    RelativeLayout rlJobSearchFragmentSelectLingYu;
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
    private PopupWindow popupWindowJobType, popupWindowOther;
    private CustomDatePicker datePickerSalaryAround;
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
    private TextView tvSalaryAround;
    private MyRecommendJobAdapter SearchAdapter;
    //判断点击批量投递的状态
    private boolean isCanSelectDeliver;
    //投递职位和简历求职意向不相同或者简历不完整弹出的dialog
    private MyRecommendDialog dialog;
    //搜索职位集合
    private List<RecommendJobBean.JobsListBean> searchList = new ArrayList<>();
    //置顶职位集合
    private List<RecommendJobBean.JobsListBean> topSearchList = new ArrayList<>();
    //页码，批量投递没有选择的列表数
    public int page = 1, notSelectNum;
    public static JobSearchResultActivity instance;

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
        if (jobsListBeanList != null && !"[]".equals(jobsListBeanList.toString()) && jobsListBeanList.size() != 0) {
            if (page == 1) {
                SearchAdapter = new MyRecommendJobAdapter(1);
                searchList.clear();
                if (topSearchList != null && !"".equals(topSearchList) && topSearchList.size() != 0) {
                    for (int i = 0; i < topSearchList.size(); i++) {
                        topSearchList.get(i).setTop(true);
                    }
                    searchList.addAll(topSearchList);
                }
                searchList.addAll(jobsListBeanList);
                SearchAdapter.setJobsListBeanList(searchList);
                rvJobSearchFragment.setAdapter(SearchAdapter);
                rvJobSearchFragment.refreshComplete();
              /*  rvJobSearchFragment.refresh();*/
            } else {
                searchList.addAll(jobsListBeanList);
                rvJobSearchFragment.loadMoreComplete();
                SearchAdapter.notifyDataSetChanged();
            }
            for(int i=0;i<searchList.size();i++){
                if(searchList.get(i).getIs_apply()==1){
                    searchList.get(i).setCheck(true);
                }
            }
            getNoSelectNum(searchList);
            rlEmptyView.setVisibility(View.GONE);
            rvJobSearchFragment.setVisibility(View.VISIBLE);
        } else {
            if (page == 1) {
                rlEmptyView.setVisibility(View.VISIBLE);
                ivNoDataSearch.setVisibility(View.GONE);
                rvJobSearchFragment.setVisibility(View.GONE);
            } else {
                rvJobSearchFragment.setNoMore(true);
            }
        }
        SearchAdapter.setClickCallBack(new MyRecommendJobAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                PositionPageActivity.startAction(JobSearchResultActivity.this, searchList.get(pos).getJob_id());
            }
        });
        SearchAdapter.setOnCheckListener(new MyRecommendJobAdapter.OnCheckListener() {
            @Override
            public void onCheckListener(int pos, boolean b) {
                searchList.get(pos).setCheck(b);
                getNoSelectNum(searchList);
                SearchAdapter.notifyDataSetChanged();
            }
        });
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
        for(int i=0;i<searchList.size();i++){
            if(searchList.get(i).isCheck()){
                searchList.get(i).setIs_apply(1);
            }
        }
        rlSearchResultDeleteInABatches.setVisibility(View.GONE);
        SearchAdapter.setCheck(false);
        tvJobSearchFragmentSelectLingYu.setText(R.string.selectDeliver);
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
     * @param jobSearchBean
     */
    public void initData(JobSearchBean jobSearchBean,boolean b) {
        int page = 1;
        mPresenter.getSearchList(jobSearchBean, page, b);
        mPresenter.getTopSearchJob(jobSearchBean);
    }

    /**
     * 初始化选择薪资范围控件
     */
    private void initDialog() {
        datePickerSalaryAround = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvSalaryAround.setText(time);
                salaryAroundName = time;
                salary_left = Utils.getLeftSalary(time);
                salary_right = Utils.getRightSalary(time);
                //salaryAroundId = ResumeInfoIDToString.getSalaryAroundId(JobSearchResultActivity.this, time);
            }
        }, getResources().getStringArray(R.array.salaryAround));
    }

    @Override
    public void initView() {
        instance = this;
        jobSearchBean = (JobSearchBean) getIntent().getSerializableExtra("jobSearch");
        MobclickAgent.onEvent(this, "v6_scan_searchResultPage");
        initDialog();
        industryIdMain = jobSearchBean.getIndustryId();
        fieldId = jobSearchBean.getFieldId();
        searchWord = jobSearchBean.getSearchName();
        positionId = jobSearchBean.getPositionId();
        if (searchWord == null || "null".equals(searchWord)) {
            searchWord = "";
            if(positionId!=null&&!"".equals(positionId)){
                tvSearchResultName.setText(FromStringToArrayList.getInstance().getExpectPositionName(positionId,industryIdMain));
            }
        }else {
            tvSearchResultName.setText(searchWord);
        }
        placeId = jobSearchBean.getPlaceId();
        if (placeId != null && !"".equals(placeId)) {
            selectCityList = FromStringToArrayList.getInstance().getSelectCityList(placeId);
        }
        jobSearchType = jobSearchBean.getJobType();
        SearchAdapter = new MyRecommendJobAdapter(1);
        initData(jobSearchBean,true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvJobSearchFragment.setLayoutManager(linearLayoutManager);
        rvJobSearchFragment.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvJobSearchFragment.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        rvJobSearchFragment.setPullRefreshEnabled(false);
        rvJobSearchFragment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                     /*   page = 1;
                        mPresenter.getSearchList(jobSearchBean, page);*/
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
    }

    private void setSearchList(boolean b) {
        if (searchList != null && searchList.size() != 0) {
            for (int i = 0; i < searchList.size(); i++) {
                if(!b) {
                    if(searchList.get(i).getIs_apply()!=1) {
                        searchList.get(i).setCheck(b);
                    }
                }else{
                    searchList.get(i).setCheck(b);
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

    private void doSearch(boolean b) {
       /* if (!"".equals(etJobSearch.getText().toString()) && etJobSearch.getText().toString() != null) {
            jobSearchBean.setSearchName(etJobSearch.getText().toString());
        } else {
            jobSearchBean.setSearchName("");
        }
        if (!etJobSearch.getText().toString().equals(searchWord)) {
            fieldId = "";
            industryIdMain = "";
            positionId = "";
        }
        searchWord = etJobSearch.getText().toString();*/
       if(searchWord!=null&&!"".equals(searchWord)){
           jobSearchBean.setSearchName(searchWord);
       }else{
           jobSearchBean.setSearchName("");
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
        //Log.i("jobSearchBean",jobSearchBean.toString());
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
       initData(jobSearchBean,true);

        //rvJobSearchFragment.refresh();
    }

    private void initPopOther() {
        View viewOther = getLayoutInflater().inflate(R.layout.layout_selectother, null);
        popupWindowOther = new PopupWindow(viewOther, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tvSalaryAround = viewOther.findViewById(R.id.tv_selectOtherSalaryAround);
        RelativeLayout rlSalaryAround = viewOther.findViewById(R.id.rl_selectOtherSalaryAround);
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
                companyTypeAdapter.notifyDataSetChanged();
            }
        });
        int[] location = new int[2];
        viewJobSearchFragment.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (salaryAroundName != null && !"".equals(salaryAroundName)) {
            tvSalaryAround.setText(salaryAroundName);
        } else {
            tvSalaryAround.setText("不限");
        }
        rlSalaryAround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerSalaryAround.show(salaryAroundName);
            }
        });
        getResources().getStringArray(R.array.array_skilllevel_zh);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        popupWindowOther.setWidth(wm.getDefaultDisplay().getWidth());
        popupWindowOther.setHeight(wm.getDefaultDisplay().getHeight() - y);
        viewJobSearchFragment.getLocationOnScreen(location);
        popupWindowOther.setOutsideTouchable(true);
        popupWindowOther.setFocusable(true);
        popupWindowOther.setAnimationStyle(R.style.style_pop_animation);
        popupWindowOther.showAtLocation(viewJobSearchFragment, Gravity.NO_GRAVITY, 0, y + viewJobSearchFragment.getHeight());
    }

    private void doSaveOther() {
        companyTypeId = FromStringToArrayList.getInstance().getDataId(selectCompanyTypeList);
        workExpId = FromStringToArrayList.getInstance().getDataId(selectWorkExpList);
        workTypeId = FromStringToArrayList.getInstance().getDataId(selectWorkTypeList);
        releaseTimeId = FromStringToArrayList.getInstance().getDataId(selectReleaseTimeList);
        degreeNeedId = FromStringToArrayList.getInstance().getDataId(selectDegreeNeedList);
        doSearch(true);
        //Log.i("当前的数据", "------" + workExpId);
    }


    private void initPopCity() {
        View viewIndustry = getLayoutInflater().inflate(R.layout.layout_selectcity, null);
        popupWindowJobType = new PopupWindow(viewIndustry, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
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
        popupWindowJobType.setWidth(wm.getDefaultDisplay().getWidth());
        popupWindowJobType.setHeight(wm.getDefaultDisplay().getHeight() - y);
        viewJobSearchFragment.getLocationOnScreen(location);
        popupWindowJobType.setOutsideTouchable(true);
        popupWindowJobType.setFocusable(true);
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
        doSearch(true);
    }

    private void setFocus() {
        rlJobSearchFragmentTop.setFocusableInTouchMode(true);
        rlJobSearchFragmentTop.setFocusable(true);
        rlJobSearchFragmentTop.requestFocus();
        rlJobSearchFragmentTop.findFocus();
    }


    @OnClick({R.id.tv_searchResultDeliverAll,R.id.rl_searchResultBack, R.id.rl_jobSearchFragmentBack, R.id.rl_jobSearchFragmentSelectCity, R.id.rl_jobSearchFragmentSelectLingYu, R.id.rl_jobSearchFragmentSelectOther})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_jobSearchFragmentBack:
                finish();
                break;
            case R.id.rl_searchResultBack:
                finish();
                break;
            case R.id.rl_jobSearchFragmentSelectCity:
                setFocus();
                initPopCity();
                break;
            case R.id.rl_jobSearchFragmentSelectLingYu:
                setFocus();
                if (SearchAdapter != null) {
                    if (isCanSelectDeliver) {
                        rlSearchResultDeleteInABatches.setVisibility(View.GONE);
                        SearchAdapter.setCheck(false);
                        tvJobSearchFragmentSelectLingYu.setText(R.string.selectDeliver);
                    } else {
                        SearchAdapter.setCheck(true);
                        getNoSelectNum(searchList);
                        rlSearchResultDeleteInABatches.setVisibility(View.VISIBLE);
                        tvJobSearchFragmentSelectLingYu.setText(R.string.cancelSelectDeliver);
                    }
                    SearchAdapter.notifyDataSetChanged();
                    isCanSelectDeliver = !isCanSelectDeliver;
                }
                break;
            case R.id.rl_jobSearchFragmentSelectOther:
                setFocus();
                initPopOther();
                break;
            case R.id.tv_searchResultDeliverAll:
                if (searchList != null && searchList.size() != 0) {
                    deliverAll();
                }
                break;
        }
    }

    private void deliverAll() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < searchList.size(); i++) {
            if (searchList.get(i).isCheck() == true&&searchList.get(i).getIs_apply()!=1) {
                sb.append("," + searchList.get(i).getJob_id());
            }
        }
        if(sb.length()==0){
            ToastUitl.showShort("请选择您需要投递的职位");
            return;
        }else {
            sb.deleteCharAt(0);
            //Log.i("现在的id",sb.toString());
            mPresenter.deliverPosition(sb.toString());
        }
    }

    private void getNoSelectNum(List<RecommendJobBean.JobsListBean> jobsListBeanList) {
        int selectNum = 0;
        notSelectNum = 0;
        String str="";
        if (jobsListBeanList != null && jobsListBeanList.size() != 0) {
            for (int i = 0; i < jobsListBeanList.size(); i++) {
                if (jobsListBeanList.get(i).isCheck()) {
                    selectNum++;
                }
            }
            notSelectNum = jobsListBeanList.size() - selectNum;
            str=getString(R.string.selectTip1) + notSelectNum + getString(R.string.selectTip2);
            char[] s = str.toCharArray();
            SpannableString ss = new SpannableString(str);
            for (int i = 0; i < s.length; i++){
                if (Utils.isNum(String.valueOf(s[i]))){
                    ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this,R.color.new_main)), i, i+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }     //设置指定位置textview的背景颜色
            //设置指定位置文字的颜色
             searchResultNoSelectNum.setText(ss);
        }

    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }
}
