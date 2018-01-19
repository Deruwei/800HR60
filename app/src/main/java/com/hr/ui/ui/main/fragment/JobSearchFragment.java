package com.hr.ui.ui.main.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.HistoryBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.db.SearchHistoryUtils;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.activity.JobSerchActivity;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.adapter.MyRecommendJobAdapter;
import com.hr.ui.ui.main.adapter.MySelectOtherAdapter;
import com.hr.ui.ui.main.contract.JobSearchFragmentContract;
import com.hr.ui.ui.main.modle.JobSearchFragmentModel;
import com.hr.ui.ui.main.presenter.JobSearchFragmentPresenter;
import com.hr.ui.utils.PopupWindowFieldView;
import com.hr.ui.utils.PopupWindowView;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;
import com.hr.ui.view.CustomDatePicker;
import com.hr.ui.view.MyFlowLayout;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wdr on 2018/1/9.
 */

public class JobSearchFragment extends BaseFragment<JobSearchFragmentPresenter, JobSearchFragmentModel> implements JobSearchFragmentContract.View {
    @BindView(R.id.tv_jobSearchTypeFragment)
    TextView tvJobSearchTypeFragment;
    @BindView(R.id.rl_jobSearchFragmentType)
    RelativeLayout rlJobSearchFragmentType;
    @BindView(R.id.et_jobSearch)
    EditText etJobSearch;
    @BindView(R.id.tv_jobSearchFragment)
    TextView tvJobSearchFragment;
    @BindView(R.id.tv_jobSearchFragmentSelectCity)
    TextView tvJobSearchFragmentSelectCity;
    @BindView(R.id.iv_jobSearchFragmentSelectCity)
    ImageView ivJobSearchFragmentSelectCity;
    @BindView(R.id.rl_jobSearchFragmentSelectCity)
    RelativeLayout rlJobSearchFragmentSelectCity;
    @BindView(R.id.tv_jobSearchFragmentSelectLingYu)
    TextView tvJobSearchFragmentSelectLingYu;
    @BindView(R.id.iv_jobSearchFragmentSelectLingYu)
    ImageView ivJobSearchFragmentSelectLingYu;
    @BindView(R.id.rl_jobSearchFragmentSelectLingYu)
    RelativeLayout rlJobSearchFragmentSelectLingYu;
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
    @BindView(R.id.rl_jobSearchFragmentTop)
    LinearLayout rlJobSearchFragmentTop;
    Unbinder unbinder;
    public static JobSearchFragment instance;
    @BindView(R.id.rl_jobSearchFragmentBack)
    RelativeLayout rlJobSearchFragmentBack;
    @BindView(R.id.rl_jobSearchTypeFragment)
    RelativeLayout rlJobSearchTypeFragment;
    @BindView(R.id.iv_noContent)
    ImageView ivNoContent;
    @BindView(R.id.tv_noData)
    TextView tvNoData;
    @BindView(R.id.iv_noDataSearchIcon)
    ImageView ivNoDataSearchIcon;
    @BindView(R.id.rl_emptyView)
    RelativeLayout rlEmptyView;
    @BindView(R.id.iv_noDataSearch)
    RelativeLayout ivNoDataSearch;
    private PopupWindow popupWindowJobType, popupWindowOther, popupWindowField;
    private CustomDatePicker datePickerSalaryAround;
    private JobSearchBean jobSearchBean;
    private int jobSerchType;
    private String placeId, industryIdMain, fieldId, salaryAroundName, salaryAroundId, workExpId, degreeNeedId, releaseTimeId, workTypeId, companyTypeId, salaty_left, salary_right;
    private List<CityBean> cityBeanList2, cityBeanList;
    private List<CityBean> provinceCityList = new ArrayList<>();
    private List<CityBean> selectCityList = new ArrayList<>();
    private List<CityBean> selectFunctionList = new ArrayList<>();
    private List<CityBean> selectWorkExpList = new ArrayList<>();
    private List<CityBean> selectDegreeNeedList = new ArrayList();
    private List<CityBean> selectReleaseTimeList = new ArrayList<>();
    private List<CityBean> selectWorkTypeList = new ArrayList<>();
    private List<CityBean> selectCompanyTypeList = new ArrayList<>();
    private List<CityBean> selectIndustryList = new ArrayList<>();
    private TextView tvSalaryAround;
    private MyRecommendJobAdapter SearchAdapter;
    private List<RecommendJobBean.JobsListBean> searchList = new ArrayList<>();
    public int page = 1;
    private int type = 1;

    public static JobSearchFragment newInstance(JobSearchBean jobSearchBean) {
        JobSearchFragment navigationFragment = new JobSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("jobSearch", jobSearchBean);
        navigationFragment.setArguments(bundle);
        instance = navigationFragment;
        return navigationFragment;
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
    public void getSearchDataSuccess(List<RecommendJobBean.JobsListBean> jobsListBeanList) {
        if (jobsListBeanList != null && !"[]".equals(jobsListBeanList.toString()) && jobsListBeanList.size() != 0) {
            if (page == 1) {
                SearchAdapter = new MyRecommendJobAdapter();
                searchList.clear();
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
                PositionPageActivity.startAction(getActivity(), searchList.get(pos).getJob_id());
            }
        });
        rvJobSearchFragment.refreshComplete();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_jobserch;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        jobSearchBean = (JobSearchBean) getArguments().getSerializable("jobSearch");
        initDialog();
        tvJobSearchFragment.setText("关闭");
        initData(jobSearchBean);
        etJobSearch.setText(jobSearchBean.getSearchName());
        jobSerchType = jobSearchBean.getJobType();
        if ("1".equals(jobSerchType)) {
            tvJobSearchFragment.setText("全部");
        } else if ("2".equals(jobSerchType)) {
            tvJobSearchFragment.setText("职位");
        } else if ("3".equals(jobSerchType)) {
            tvJobSearchFragment.setText("公司");
        }
        SearchAdapter = new MyRecommendJobAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvJobSearchFragment.setLayoutManager(linearLayoutManager);
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_sample);
        rvJobSearchFragment.addItemDecoration(rvJobSearchFragment.new DividerItemDecoration(dividerDrawable));
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
                        mPresenter.getSearchList(jobSearchBean, page);
                    }
                }, 1000);
            }
        });
        etJobSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    tvJobSearchFragment.setText("关闭");
                    type = 1;
                } else {
                    tvJobSearchFragment.setText("搜索");
                    type = 2;
                }
            }
        });
        etJobSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    etJobSearch.setCursorVisible(false);
                    doSearch();
                    InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;

            }

        });
    }

    public void initData(JobSearchBean jobSearchBean) {
        mPresenter.getSearchList(jobSearchBean, page);
        rvJobSearchFragment.refresh();
    }

    private void initDialog() {
        datePickerSalaryAround = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                tvSalaryAround.setText(time);
                salaryAroundName = time;
                salaty_left = time.substring(0, time.indexOf("-"));
                salary_right = time.substring(time.indexOf("-") + 1);
                salaryAroundId = ResumeInfoIDToString.getSalaryAroundId(getActivity(), time);
            }
        }, getActivity().getResources().getStringArray(R.array.salaryAround));
    }


    @OnClick({R.id.tv_jobSearchTypeFragment, R.id.rl_jobSearchFragmentBack, R.id.tv_jobSearchFragment, R.id.rl_jobSearchFragmentSelectCity, R.id.rl_jobSearchFragmentSelectLingYu, R.id.rl_jobSearchFragmentSelectOther})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_jobSearchFragmentBack:
                JobSerchActivity.startAction(getActivity(), MainActivity.instance.REQUEST_CODE);
                break;
            case R.id.tv_jobSearchTypeFragment:
                setFocus();
                initPopSearchType();
                break;
            case R.id.tv_jobSearchFragment:
                if (type == 1) {
                    MainActivity.instance.isHome = true;
                    MainActivity.instance.switchFragment(0);
                } else if (type == 2) {
                    doSearch();
                }
                setFocus();
                break;
            case R.id.rl_jobSearchFragmentSelectCity:
                setFocus();
                initPopCity();
                break;
            case R.id.rl_jobSearchFragmentSelectLingYu:
                setFocus();
                initPopField();
                break;
            case R.id.rl_jobSearchFragmentSelectOther:
                setFocus();
                initPopOther();
                break;
        }
    }

    private void setFocus() {
        rlJobSearchFragmentTop.setFocusableInTouchMode(true);
        rlJobSearchFragmentTop.setFocusable(true);
        rlJobSearchFragmentTop.requestFocus();
        rlJobSearchFragmentTop.findFocus();
    }

    private void doSearch() {
        if (!"".equals(etJobSearch.getText().toString()) && etJobSearch.getText().toString() != null) {
            jobSearchBean.setSearchName(etJobSearch.getText().toString());
        } else {
            jobSearchBean.setSearchName("");
        }
        jobSearchBean.setJobType(jobSerchType);
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
            jobSearchBean.setPositionId("");
            jobSearchBean.setFieldId(fieldId);
        } else {
            jobSearchBean.setFieldId("");
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
        if (salaty_left != null && !"".equals(salaty_left)) {
            jobSearchBean.setSalary_left(salaty_left);
        } else {
            jobSearchBean.setSalary_left("");
        }
        HistoryBean historyBean = new HistoryBean();
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
        SearchHistoryUtils.insertJobSearchDataOrReplace(historyBean);
        mPresenter.getSearchList(jobSearchBean, page);
        rvJobSearchFragment.refresh();
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
        final List<CityBean> workExopList = ResumeInfoIDToString.getWorkExp(getActivity());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rvWorkExp.setLayoutManager(manager);

        GridLayoutManager manager2 = new GridLayoutManager(getActivity(), 4);
        manager2.setOrientation(GridLayoutManager.VERTICAL);
        rvDegreeNeed.setLayoutManager(manager2);

        GridLayoutManager manager3 = new GridLayoutManager(getActivity(), 4);
        manager3.setOrientation(GridLayoutManager.VERTICAL);
        rvReleaseTime.setLayoutManager(manager3);

        GridLayoutManager manager4 = new GridLayoutManager(getActivity(), 4);
        manager4.setOrientation(GridLayoutManager.VERTICAL);
        rvWorkType.setLayoutManager(manager4);

        GridLayoutManager manager5 = new GridLayoutManager(getActivity(), 4);
        manager5.setOrientation(GridLayoutManager.VERTICAL);
        rvCompanyType.setLayoutManager(manager5);

        final List<CityBean> releaseTimeList = ResumeInfoIDToString.getReleaseTime(getActivity());
        if (selectReleaseTimeList != null) {
            for (int i = 0; i < selectReleaseTimeList.size(); i++) {
                for (int j = 0; j < releaseTimeList.size(); j++) {
                    if (selectReleaseTimeList.get(i).getId().equals(releaseTimeList.get(j).getId())) {
                        releaseTimeList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        }
        final MySelectOtherAdapter releaseTimeAdapter = new MySelectOtherAdapter(getActivity(), releaseTimeList);
        rvReleaseTime.setAdapter(releaseTimeAdapter);
        releaseTimeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (releaseTimeList.get(position).isCheck() == true) {
                    for (int i = 0; i < selectReleaseTimeList.size(); i++) {
                        if (selectReleaseTimeList.get(i).getId().equals(releaseTimeList.get(position).getId())) {
                            selectReleaseTimeList.remove(i);
                            break;
                        }
                    }
                    releaseTimeList.get(position).setCheck(false);
                } else {
                    releaseTimeList.get(position).setCheck(true);
                    selectReleaseTimeList.add(releaseTimeList.get(position));
                }
                releaseTimeAdapter.notifyDataSetChanged();
            }
        });

        final List<CityBean> degreeNeedList = ResumeInfoIDToString.getDegreeNeed(getActivity());
        if (selectDegreeNeedList != null) {
            for (int i = 0; i < selectDegreeNeedList.size(); i++) {
                for (int j = 0; j < degreeNeedList.size(); j++) {
                    if (selectDegreeNeedList.get(i).getId().equals(degreeNeedList.get(j).getId())) {
                        degreeNeedList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        }
        final MySelectOtherAdapter degreeNeedAdapter = new MySelectOtherAdapter(getActivity(), degreeNeedList);
        rvDegreeNeed.setAdapter(degreeNeedAdapter);
        degreeNeedAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (degreeNeedList.get(position).isCheck() == true) {
                    for (int i = 0; i < selectDegreeNeedList.size(); i++) {
                        if (selectDegreeNeedList.get(i).getId().equals(degreeNeedList.get(position).getId())) {
                            selectDegreeNeedList.remove(i);
                            break;
                        }
                    }
                    degreeNeedList.get(position).setCheck(false);
                } else {
                    degreeNeedList.get(position).setCheck(true);
                    selectDegreeNeedList.add(degreeNeedList.get(position));
                }
                degreeNeedAdapter.notifyDataSetChanged();
            }
        });
        if (selectWorkExpList != null) {
            for (int i = 0; i < selectWorkExpList.size(); i++) {
                for (int j = 0; j < workExopList.size(); j++) {
                    if (selectWorkExpList.get(i).getId().equals(workExopList.get(j).getId())) {
                        workExopList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        }
        //Log.i("当前的数据",selectWorkExpList.toString());
        final MySelectOtherAdapter workExpAdapter = new MySelectOtherAdapter(getActivity(), workExopList);
        rvWorkExp.setAdapter(workExpAdapter);
        workExpAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (workExopList.get(position).isCheck() == true) {
                    for (int i = 0; i < selectWorkExpList.size(); i++) {
                        if (selectWorkExpList.get(i).getId().equals(workExopList.get(position).getId())) {
                            selectWorkExpList.remove(i);
                            break;
                        }
                    }
                    workExopList.get(position).setCheck(false);
                } else {
                    workExopList.get(position).setCheck(true);
                    selectWorkExpList.add(workExopList.get(position));
                }
                workExpAdapter.notifyDataSetChanged();
            }
        });
        final List<CityBean> workTypeList = ResumeInfoIDToString.getWorkType(getActivity());
        if (selectWorkTypeList != null) {
            for (int i = 0; i < selectWorkTypeList.size(); i++) {
                for (int j = 0; j < workTypeList.size(); j++) {
                    if (selectWorkTypeList.get(i).getId().equals(workTypeList.get(j).getId())) {
                        workTypeList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        }
        final MySelectOtherAdapter workTypeAdapter = new MySelectOtherAdapter(getActivity(), workTypeList);
        rvWorkType.setAdapter(workTypeAdapter);
        workTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (workTypeList.get(position).isCheck() == true) {
                    for (int i = 0; i < selectWorkTypeList.size(); i++) {
                        if (selectWorkTypeList.get(i).getId().equals(workTypeList.get(position).getId())) {
                            selectWorkTypeList.remove(i);
                            break;
                        }
                    }
                    workTypeList.get(position).setCheck(false);
                } else {
                    workTypeList.get(position).setCheck(true);
                    selectWorkTypeList.add(workTypeList.get(position));

                }
                workTypeAdapter.notifyDataSetChanged();
            }
        });

        final List<CityBean> companyTypeList = ResumeInfoIDToString.getCompanyType(getActivity());
        if (selectCompanyTypeList != null) {
            for (int i = 0; i < selectCompanyTypeList.size(); i++) {
                for (int j = 0; j < companyTypeList.size(); j++) {
                    if (selectCompanyTypeList.get(i).getId().equals(companyTypeList.get(j).getId())) {
                        companyTypeList.get(j).setCheck(true);
                        break;
                    }
                }
            }
        }
        final MySelectOtherAdapter companyTypeAdapter = new MySelectOtherAdapter(getActivity(), companyTypeList);
        rvCompanyType.setAdapter(companyTypeAdapter);
        companyTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                if (companyTypeList.get(position).isCheck() == true) {
                    for (int i = 0; i < selectCompanyTypeList.size(); i++) {
                        if (selectCompanyTypeList.get(i).getId().equals(companyTypeList.get(position).getId())) {
                            selectCompanyTypeList.remove(i);
                            break;
                        }
                    }
                    companyTypeList.get(position).setCheck(false);
                } else {
                    companyTypeList.get(position).setCheck(true);
                    selectCompanyTypeList.add(companyTypeList.get(position));
                }
                companyTypeAdapter.notifyDataSetChanged();
            }
        });
        int[] location = new int[2];
        viewJobSearchFragment.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        tvSalaryAround.setText(salaryAroundName);
        rlSalaryAround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerSalaryAround.show(salaryAroundName);
            }
        });
        getActivity().getResources().getStringArray(R.array.array_skilllevel_zh);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
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
        doSearch();
        //Log.i("当前的数据", "------" + workExpId);
    }

    private void initPopField() {
        View viewField = getLayoutInflater().inflate(R.layout.layout_selectfield, null);
        popupWindowField = new PopupWindow(viewField, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvCancel = viewField.findViewById(R.id.tv_selectPositionCancel);
        TextView tvOK = viewField.findViewById(R.id.tv_selectPositionOK);
        TextView tvNum = viewField.findViewById(R.id.tv_selectPositionNum);
        RelativeLayout rlNoField = viewField.findViewById(R.id.rl_noFunction);
        RelativeLayout rlSelectData = viewField.findViewById(R.id.rl_selectData);
        MyFlowLayout flSelectData = viewField.findViewById(R.id.ll_selectedPosition);
        ListView lvLeft = viewField.findViewById(R.id.lv_left);
        ListView lvRight = viewField.findViewById(R.id.lv_right);
        int[] location = new int[2];
        viewJobSearchFragment.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        PopupWindowFieldView popupWindowView = new PopupWindowFieldView(popupWindowField, page, getActivity(), selectIndustryList, selectFunctionList, rlSelectData, rlNoField, lvLeft, lvRight, flSelectData, tvNum, tvCancel, tvOK);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        popupWindowField.setWidth(wm.getDefaultDisplay().getWidth());
        popupWindowField.setHeight(wm.getDefaultDisplay().getHeight() - y);
        viewJobSearchFragment.getLocationOnScreen(location);
        popupWindowField.setOutsideTouchable(true);
        popupWindowField.setFocusable(true);
        popupWindowField.setAnimationStyle(R.style.style_pop_animation);
        popupWindowField.showAtLocation(viewJobSearchFragment, Gravity.NO_GRAVITY, 0, y + viewJobSearchFragment.getHeight());
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
        PopupWindowView popupWindowView = new PopupWindowView(popupWindowJobType, page, selectCityList, tvCancel, tvOk, flData, lvRight, lvLeft, getActivity(), rlNoSelect, tvNum);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        popupWindowJobType.setWidth(wm.getDefaultDisplay().getWidth());
        popupWindowJobType.setHeight(wm.getDefaultDisplay().getHeight() - y);
        viewJobSearchFragment.getLocationOnScreen(location);
        popupWindowJobType.setOutsideTouchable(true);
        popupWindowJobType.setFocusable(true);
        popupWindowJobType.setAnimationStyle(R.style.style_pop_animation);
        popupWindowJobType.showAtLocation(viewJobSearchFragment, Gravity.NO_GRAVITY, 0, y + viewJobSearchFragment.getHeight());
    }

    private void initPopSearchType() {
        View viewIndustry = getLayoutInflater().inflate(R.layout.layout_searchtype, null);
        popupWindowJobType = new PopupWindow(viewIndustry, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int[] location = new int[2];
        final TextView tvAll = viewIndustry.findViewById(R.id.tv_jobTypeAll);
        final TextView tvCompany = viewIndustry.findViewById(R.id.tv_jobTypeCompany);
        final TextView tvPosition = viewIndustry.findViewById(R.id.tv_jobTypePosition);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        tvJobSearchFragment.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJobSearchTypeFragment.setText("全部");
                jobSerchType = 1;
                popupWindowJobType.dismiss();
            }
        });
        tvCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJobSearchTypeFragment.setText("公司");
                jobSerchType = 3;
                popupWindowJobType.dismiss();
            }
        });
        tvPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvJobSearchTypeFragment.setText("职位");
                jobSerchType = 2;
                popupWindowJobType.dismiss();
            }
        });
        popupWindowJobType.setOutsideTouchable(true);
        popupWindowJobType.setFocusable(true);
        popupWindowJobType.setAnimationStyle(R.style.style_pop_animation);
        popupWindowJobType.showAsDropDown(tvJobSearchTypeFragment, 0, 5);
    }

    public void setPlaceId(List<CityBean> selectCityList) {
        if (selectCityList != null && selectCityList.size() != 0) {
            this.selectCityList = new ArrayList<>();
            this.selectCityList = selectCityList;
            //Log.i("您好",selectCityList.toString());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.selectCityList.size(); i++) {
                sb.append("," + this.selectCityList.get(i).getId());
            }
            if (sb != null && !"".equals(sb)) {
                sb.deleteCharAt(0);
            }
            placeId = sb.toString();
        } else {
            placeId = "";
        }
        doSearch();
    }

    /**
     * 选择领域页面传递过来的参数
     *
     * @param selectIndustryList1
     * @param selectFunctionList1
     */
    public void setFunctionList(List<CityBean> selectIndustryList1, List<CityBean> selectFunctionList1) {
        selectFunctionList = new ArrayList<>();
        selectIndustryList = new ArrayList<>();
        selectFunctionList = selectFunctionList1;
        this.selectIndustryList = selectIndustryList1;
        //Log.i("选择","-------"+selectIndustryList1.toString());
        if (selectFunctionList1 != null && !"".equals(selectFunctionList1) && selectFunctionList1.size() != 0) {


            StringBuffer sb = new StringBuffer();
            StringBuffer sbIndustryId = new StringBuffer();
            for (int i = 0; i < selectFunctionList.size(); i++) {
                sb.append("," + selectFunctionList.get(i).getId());
            }
            for (int i = 0; i < selectIndustryList.size(); i++) {
                sbIndustryId.append("," + selectIndustryList.get(i).getId());
            }
            sb.deleteCharAt(0);
            if (sbIndustryId != null && sbIndustryId.length() != 0) {
                sbIndustryId.deleteCharAt(0);
            }
            fieldId = sb.toString();
            industryIdMain = sbIndustryId.toString();
        } else {
            fieldId = "";
            if (selectIndustryList != null && selectIndustryList.size() != 0) {
                StringBuffer sbIndustryId = new StringBuffer();
                for (int i = 0; i < selectIndustryList.size(); i++) {
                    sbIndustryId.append("," + selectIndustryList.get(i).getId());
                }
                if (sbIndustryId != null && sbIndustryId.length() != 0) {
                    sbIndustryId.deleteCharAt(0);
                }
                industryIdMain = sbIndustryId.toString();
            } else {
                industryIdMain = "";
            }
        }
        doSearch();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
