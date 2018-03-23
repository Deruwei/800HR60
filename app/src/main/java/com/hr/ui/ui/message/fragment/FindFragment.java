package com.hr.ui.ui.message.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.FindBean;
import com.hr.ui.ui.job.activity.CompanyPageActivity;
import com.hr.ui.ui.message.activity.WebActivity;
import com.hr.ui.ui.message.adapter.MyFindAdapter;
import com.hr.ui.ui.message.contract.FindContract;
import com.hr.ui.ui.message.model.FindModel;
import com.hr.ui.ui.message.presenter.FindPresenter;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.view.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wdr on 2018/1/16.
 */

public class FindFragment extends BaseFragment<FindPresenter, FindModel> implements FindContract.View {
    @BindView(R.id.rv_deliverFeedback)
    XRecyclerView rvDeliverFeedback;
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
    private int page = 1;
    Unbinder unbinder;
    private String ad_type;
    private int position;
    private MyFindAdapter adapter;
    private List<FindBean.ListBean> listBeanList = new ArrayList<>();

    public static FindFragment newInstance(int i) {
        FindFragment navigationFragment = new FindFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", i);
        navigationFragment.setArguments(bundle);
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
    public void getFiindDataSuccess(final List<FindBean.ListBean> listBeans) {
        if (listBeans != null && !"".equals(listBeans) && !"[]".equals(listBeans) && listBeans.size() != 0) {
            rlEmptyView.setVisibility(View.GONE);
            rvDeliverFeedback.setVisibility(View.VISIBLE);
            if (page == 1) {
                adapter = new MyFindAdapter(getActivity(), position);
                listBeanList = new ArrayList<>();
                listBeanList.addAll(listBeans);
                adapter.setListBeans(listBeanList);
                rvDeliverFeedback.setAdapter(adapter);
                rvDeliverFeedback.refreshComplete();
            } else {
                listBeanList.addAll(listBeans);
                rvDeliverFeedback.loadMoreComplete();
                adapter.notifyDataSetChanged();
            }
            if(listBeans.size()<20){
                rvDeliverFeedback.setLoadingMoreEnabled(false);
            }
        } else {
            if(page==1){
                rvDeliverFeedback.setVisibility(View.GONE);
                rlEmptyView.setVisibility(View.VISIBLE);
            }else {
                rvDeliverFeedback.setNoMore(true);
            }
        }
        adapter.setClickCallBack(new MyFindAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                if("1".equals(listBeanList.get(pos).getTopic_type())) {
                    WebActivity.startAction(getActivity(), listBeanList.get(pos).getTopic_url());
                }else if("2".equals(listBeanList.get(pos).getTopic_type())){
                    CompanyPageActivity.startAction(getActivity(),listBeanList.get(pos).getEnterprise_id());
                }
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_deliverfeedback;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        initRv();
    }

    @Override
    protected void lazyLoad() {
        position = getArguments().getInt("position");
        if (position == 0) {
            ad_type = "4";
            MobclickAgent.onEvent(getActivity(),"v6_scan_brandRecuitment");
        } else if (position == 1) {
            ad_type = "7";
            MobclickAgent.onEvent(getActivity(),"v6_scan_displayCompany");
        } else if (position == 2) {
            ad_type = "5";
            MobclickAgent.onEvent(getActivity(),"v6_scan_topicActivity");
        }
        mPresenter.getFindData(page, ad_type, true);
    }

    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDeliverFeedback.setLayoutManager(linearLayoutManager);
        /*Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
        rvCollection.addItemDecoration(rvCollection.new DividerItemDecoration(dividerDrawable));*/
        rvDeliverFeedback.setNestedScrollingEnabled(false);
        ivNoDataSearch.setVisibility(View.GONE);
        rvDeliverFeedback.setRefreshProgressStyle(ProgressStyle.BallPulse);
        rvDeliverFeedback.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        ivNoContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page=1;
                mPresenter.getFindData(page,ad_type,true);
            }
        });
        adapter = new MyFindAdapter(getActivity(), position);
        rvDeliverFeedback.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = 1;
                        mPresenter.getFindData(page, ad_type, false);
                        adapter.notifyDataSetChanged();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        mPresenter.getFindData(page, ad_type, false);
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
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
