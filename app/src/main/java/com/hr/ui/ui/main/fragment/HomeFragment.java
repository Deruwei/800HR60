package com.hr.ui.ui.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.HomeRecommendBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.activity.JobSerchActivity;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.adapter.MyRecommendJobAdapter;
import com.hr.ui.ui.main.contract.HomeFragmentContract;
import com.hr.ui.ui.main.modle.HomeFragmentModel;
import com.hr.ui.ui.main.presenter.HomeFragmentPresenter;
import com.hr.ui.utils.ClickUtils;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.DatePickerView;
import com.hr.ui.view.PieChartView;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wdr on 2017/11/22.
 */

public class HomeFragment extends BaseFragment<HomeFragmentPresenter, HomeFragmentModel> implements HomeFragmentContract.View {

    @BindView(R.id.rv_homeFragment)
    XRecyclerView rvHomeFragment;
    @BindView(R.id.iv_noContent)
    ImageView ivNoContent;
    @BindView(R.id.tv_noData)
    TextView tvNoData;
    @BindView(R.id.iv_noDataSearchIcon)
    ImageView ivNoDataSearchIcon;
    @BindView(R.id.rl_emptyView)
    RelativeLayout rlEmptyView;
    Unbinder unbinder;
    @BindView(R.id.iv_ResumePersonPhoto)
    CircleImageView ivResumePersonPhoto;
    @BindView(R.id.iv_mainSearch)
    ImageView ivMainSearch;
    @BindView(R.id.rl_mainSearch)
    RelativeLayout rlMainSearch;
    @BindView(R.id.rl_fragmentTitle)
    RelativeLayout rlFragmentTitle;
    @BindView(R.id.iv_noDataSearch)
    RelativeLayout ivNoDataSearch;
    @BindView(R.id.cl_homeFragment)
    RelativeLayout clHomeFragment;
    @BindView(R.id.iv_noNetError)
    ImageView ivNoNetError;
    @BindView(R.id.ll_netError)
    LinearLayout llNetError;
    private int page = 1;
    public static HomeFragment instance;
    /**
     * 1 判断是否有第三方公司推荐职位的数据
     * 2.判断是否第三方公司推荐职位的数据满足20条
     * 3.判断是否有公司职位推荐的数据
     */
    private boolean isHaveThirdRecommendData,isThirdRecommendDataFill,isHaveRecommendData;
    private MyRecommendJobAdapter jobAdapter;
    //第三方公司推荐职位接口获取到的数据
    private List<HomeRecommendBean.JobsListBean> recommendList = new ArrayList<>();
    //公司职位推荐接口获取到的数据
    private List<RecommendJobBean.JobsListBean> recommendJobList=new ArrayList<>();
    private PopupWindow popupWindowCalculateScore;
    private SharedPreferencesUtils sUtils;
    private String personImage;
    private int mProgress;

    public static HomeFragment newInstance(String s) {
        HomeFragment navigationFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        navigationFragment.setArguments(bundle);
        instance = navigationFragment;
        return navigationFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_homefragment;
    }

    public void setImage() {
        personImage = sUtils.getStringValue(Constants.PERSONIMAGE, "");
        if (!"".equals(personImage) && personImage != null) {
           /* Glide.with(this).load(Constants.IMAGE_BASEPATH + personImage).centerCrop().into(ivResumePersonPhoto);*/
            Glide.with(this).load(Constants.IMAGE_BASEPATH + personImage) .fitCenter().into(ivResumePersonPhoto);
        }else{
           ivResumePersonPhoto.setImageResource(R.mipmap.persondefault);
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        sUtils = new SharedPreferencesUtils(getActivity());
        refresh(true);
        jobAdapter = new MyRecommendJobAdapter();
        setImage();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };

        tvNoData.setText("暂无合适职位推荐，点我刷新");
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHomeFragment.setLayoutManager(linearLayoutManager);
        rvHomeFragment.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvHomeFragment.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);

        rvHomeFragment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                       refresh(false);
                       jobAdapter.notifyDataSetChanged();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if(recommendList!=null&&recommendList.size()>=20) {
                            rvHomeFragment.setLoadingMoreEnabled(false);
                        }else{
                            page++;
                            mPresenter.getRecommendJob(page,20,false);
                        }
                        jobAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
      /*  listData = new ArrayList<String>();
        for(int i = 0; i < 15 ;i++){
            listData.add("item" + i);
        }
        mAdapter = new MyAdapter(listData);

        rvMsg.setAdapter(mAdapter);
        rvMsg.refresh();*/
    }

    public void refresh(final boolean isrefresh) {
        page = 1;
        //获取第三方推荐职位
        mPresenter.getRecommendJobInfo( 20, false);
        //获取公司的推荐职位
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getRecommendJob(page,20,isrefresh);
            }
        },500);
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

    @Override
    public void getRecommendJobSuccess(List<HomeRecommendBean.JobsListBean> jobsBeanList) {
        //Log.i("现在的数据",jobsBeanList.toString());
        if (jobsBeanList != null && !"".equals(jobsBeanList) && jobsBeanList.size() != 0) {
            recommendList.clear();
            recommendList.addAll(jobsBeanList);
            isHaveThirdRecommendData=true;
            if(recommendList.size()>=20){
                isThirdRecommendDataFill=true;
            }else{
                isThirdRecommendDataFill=false;
            }
        } else {
            isHaveThirdRecommendData= false;
            isThirdRecommendDataFill=false;
        }
    }

    @Override
    public void getResumeScoreSuccess(double score) {
        int i = (int) (score * 100);
      /*  initCalculateScore(i);*//**/
    }

    @Override
    public void getRecommendJobError() {
            if(rlEmptyView!=null) {
                rlEmptyView.setVisibility(View.GONE);
            }
            if(rvHomeFragment!=null) {
                rvHomeFragment.setVisibility(View.GONE);
            }
            if(rvHomeFragment!=null) {
                llNetError.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public void getRecommendJobSuccess2(List<RecommendJobBean.JobsListBean> jobsListBeanList) {
        if(jobsListBeanList!=null&&jobsListBeanList.size()!=0&&jobsListBeanList.size()!=0) {
            isHaveRecommendData=true;
            if(page==1){
                recommendJobList.clear();
            }
            recommendJobList.addAll(jobsListBeanList);
        }else{
            if(page==1) {
                isHaveRecommendData = false;
            }else{
                rvHomeFragment.setNoMore(true);
                rvHomeFragment.setLoadingMoreEnabled(false);
            }
        }
        setAdapter();
        jobAdapter.setClickCallBack(new MyRecommendJobAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                if(isThirdRecommendDataFill==false) {
                    if (pos < recommendList.size()) {
                        PositionPageActivity.startAction(getActivity(), recommendList.get(pos).getJob_id());
                    } else {
                        PositionPageActivity.startAction(getActivity(), recommendJobList.get(pos - recommendList.size()).getJob_id());
                    }
                }else{
                    PositionPageActivity.startAction(getActivity(), recommendList.get(pos).getJob_id());
                }
            }
        });
    }
    private void setAdapter(){
        if(isHaveRecommendData==true||isHaveThirdRecommendData==true){
            rlEmptyView.setVisibility(View.GONE);
            rvHomeFragment.setVisibility(View.VISIBLE);
            llNetError.setVisibility(View.GONE);
            if(isThirdRecommendDataFill){
                setDate1();
            }else{
                setData2();
            }
        }else {
            rlEmptyView.setVisibility(View.VISIBLE);
            rvHomeFragment.setVisibility(View.GONE);
            llNetError.setVisibility(View.GONE);
        }
    }
    //第三方公司推荐职位满足20个
    private void setDate1(){
        jobAdapter = new MyRecommendJobAdapter();
        jobAdapter.setJobsListBeanList2(recommendList);
        rvHomeFragment.setAdapter(jobAdapter);
        rvHomeFragment.refreshComplete();
        rvHomeFragment.setLoadingMoreEnabled(false);
    }

    /**
     * 第三方公司推荐职位有数据但是不满足20个，拼接上公司职位推荐数据
     */
    private void setData2(){
        if(page==1) {
            jobAdapter = new MyRecommendJobAdapter(3);
            jobAdapter.setJobsListBeanList(recommendJobList);
            jobAdapter.setJobsListBeanList2(recommendList);
            rvHomeFragment.setAdapter(jobAdapter);
            rvHomeFragment.refreshComplete();
        }else{
            rvHomeFragment.loadMoreComplete();
            jobAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void cantGetData() {
        /*mPresenter.getRecommendJob(page,20,true*/
    }

   /* private void initCalculateScore(final int i) {
        View viewCalculateScore = getLayoutInflater().inflate(R.layout.layout_calculatescore, null);
        popupWindowCalculateScore = new PopupWindow(viewCalculateScore, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        PieChartView pcv = viewCalculateScore.findViewById(R.id.pcv_calculateScore);
        TextView tvScore = viewCalculateScore.findViewById(R.id.tv_calculateSore);
        final TextView tv_culculateSoreNum = viewCalculateScore.findViewById(R.id.tv_calculateScoreNum);
        Button btnOK = viewCalculateScore.findViewById(R.id.btn_calculateScoreOK);
        pcv.SetProgram(i);
        mProgress = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress < i) {
                    mProgress++;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_culculateSoreNum.setText(mProgress + "分");
                        }
                    });
                    try {
                        Thread.sleep(22);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        tvScore.setText(i + "");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowCalculateScore.dismiss();
            }
        });
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindowCalculateScore.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        popupWindowCalculateScore.setFocusable(true);
        popupWindowCalculateScore.setOutsideTouchable(false);
        popupWindowCalculateScore.setAnimationStyle(R.style.style_pop_animation);
        popupWindowCalculateScore.showAtLocation(clHomeFragment, Gravity.CENTER, 0, 0);
    }*/

    @OnClick({R.id.iv_ResumePersonPhoto,R.id.ll_netError, R.id.iv_noContent, R.id.iv_noDataSearch, R.id.rl_mainSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_netError:
                /*mPresenter.getRecommendJobInfo(20,true);*/
                refresh(true);
                break;
            case R.id.iv_ResumePersonPhoto:
                MainActivity.instance.toggle();
                break;
            case R.id.iv_noDataSearch:
                JobSerchActivity.startAction(getActivity(), MainActivity.instance.REQUEST_CODE);
                break;
            case R.id.iv_noContent:
              /*  mPresenter.getRecommendJobInfo( 20, true);*//**/
                refresh(true);
                break;
            case R.id.rl_mainSearch:
                JobSerchActivity.startAction(getActivity(), MainActivity.instance.REQUEST_CODE);
                break;
        }
    }
}
