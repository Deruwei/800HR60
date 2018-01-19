package com.hr.ui.ui.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.adapter.MyRecommendJobAdapter;
import com.hr.ui.ui.main.contract.HomeFragmentContract;
import com.hr.ui.ui.main.modle.HomeFragmentModel;
import com.hr.ui.ui.main.presenter.HomeFragmentPresenter;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private int page = 1;
    public static HomeFragment instance;
    private MyRecommendJobAdapter jobAdapter;
    private List<RecommendJobBean.JobsListBean> recommendList = new ArrayList<>();

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

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        jobAdapter = new MyRecommendJobAdapter();
        mPresenter.getRecommendJobInfo(page, 20);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHomeFragment.setLayoutManager(linearLayoutManager);
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_sample);
        rvHomeFragment.addItemDecoration(rvHomeFragment.new DividerItemDecoration(dividerDrawable));
        rvHomeFragment.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvHomeFragment.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);

        rvHomeFragment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mPresenter.getRecommendJobInfo(1, 20);
                        jobAdapter.notifyDataSetChanged();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        mPresenter.getRecommendJobInfo(page, 20);
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

    public void refresh() {
        page = 1;
        mPresenter.getRecommendJobInfo(page, 20);
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
    public void getRecommendJobSuccess(List<RecommendJobBean.JobsListBean> jobsBeanList) {
        Log.i("现在的数据",jobsBeanList.toString());
        if (jobsBeanList != null&&!"[]".equals(jobsBeanList) && jobsBeanList.size() != 0) {
            if (page == 1) {
                jobAdapter = new MyRecommendJobAdapter();
                recommendList.clear();
                recommendList.addAll(jobsBeanList);
                jobAdapter.setJobsListBeanList(recommendList);
                rvHomeFragment.setAdapter(jobAdapter);
                rvHomeFragment.refreshComplete();
            } else {
                recommendList.addAll(jobsBeanList);
                rvHomeFragment.loadMoreComplete();
                jobAdapter.notifyDataSetChanged();
            }
            rlEmptyView.setVisibility(View.GONE);
            rvHomeFragment.setVisibility(View.VISIBLE);

        } else {
            if (page == 1) {
               rlEmptyView.setVisibility(View.VISIBLE);
               rvHomeFragment.setVisibility(View.GONE);
            } else {
                rvHomeFragment.setNoMore(true);
            }
        }
        jobAdapter.setClickCallBack(new MyRecommendJobAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                PositionPageActivity.startAction(getActivity(), recommendList.get(pos).getJob_id());
            }
        });

    }
}
