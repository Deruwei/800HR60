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
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.message.adapter.MyDeliverFeedbackAdapter;
import com.hr.ui.ui.message.contract.DeliverFeedbackContract;
import com.hr.ui.ui.message.model.DeliverFeedBackFragmentModel;
import com.hr.ui.ui.message.presenter.DeliverFeedbackFragmentPresenter;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wdr on 2017/11/22.
 */

public class DeliverFeedbackFragment extends BaseFragment<DeliverFeedbackFragmentPresenter, DeliverFeedBackFragmentModel> implements DeliverFeedbackContract.View {
    @BindView(R.id.rv_deliverFeedback)
    XRecyclerView rvDeliverFeedback;
    Unbinder unbinder;
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
    private int position;
    private MyDeliverFeedbackAdapter adapter;
    private int page = 1;
    private boolean can;
    private List<DeliverFeedbackBean.AppliedListBean> appliedListBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_deliverfeedback;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    public static DeliverFeedbackFragment newInstance(int i) {
        DeliverFeedbackFragment navigationFragment = new DeliverFeedbackFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", i);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }


    @Override
    protected void initView() {
        initRv();
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
        rvDeliverFeedback.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvDeliverFeedback.setNestedScrollingEnabled(false);
        rvDeliverFeedback.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        adapter = new MyDeliverFeedbackAdapter();
        rvDeliverFeedback.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = 1;
                        if (position == 0) {
                            mPresenter.getDeliverFeedBack(page, 0, 0,false);
                        } else if (position == 1) {
                            mPresenter.getDeliverFeedBack(page, 1, 0,false);
                        }
                        adapter.notifyDataSetChanged();
                        rvDeliverFeedback.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        if (position == 0) {
                            mPresenter.getDeliverFeedBack(page, 0, 0,false);
                        } else if (position == 1) {
                            mPresenter.getDeliverFeedBack(page, 1, 0,false);
                        }

                        adapter.notifyDataSetChanged();
                        rvDeliverFeedback.loadMoreComplete();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        position = getArguments().getInt("position");
        if (position == 0) {
            mPresenter.getDeliverFeedBack(page, 0, 0,true);
        } else if (position == 1) {
            mPresenter.getDeliverFeedBack(page, 1, 0,true);
        }
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
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void getDeliverFeedbackSuccess(final List<DeliverFeedbackBean.AppliedListBean> appliedListBeanList1) {
        if (appliedListBeanList1 != null && !"[]".equals(appliedListBeanList1) && !"".equals(appliedListBeanList1) && appliedListBeanList1.size() != 0) {
            if (page == 1) {
                adapter = new MyDeliverFeedbackAdapter();
                appliedListBeanList = new ArrayList<>();
                appliedListBeanList.addAll(appliedListBeanList1);
                adapter.setFavouriteListBeanList(appliedListBeanList);
                rvDeliverFeedback.setAdapter(adapter);
            } else {
                appliedListBeanList.addAll(appliedListBeanList1);
                adapter.notifyDataSetChanged();
            }
        } else {
            if(page==1){
                ivNoDataSearch.setVisibility(View.GONE);
                rlEmptyView.setVisibility(View.VISIBLE);
            }else {
                rvDeliverFeedback.setNoMore(true);
            }
        }
        adapter.setClickCallBack(new MyDeliverFeedbackAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                if(appliedListBeanList1.get(pos).getIs_expire()==1){
                    ToastUitl.showShort(R.string.error_401);
                    return;
                }else {
                    PositionPageActivity.startAction(getActivity(), appliedListBeanList.get(pos).getJob_id(), 2);
                }
            }
        });
    }
}
