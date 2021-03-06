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

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.bean.EventHomeBean;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.fragment.MessageFragment;
import com.hr.ui.ui.message.adapter.MyDeliverFeedbackAdapter;
import com.hr.ui.ui.message.contract.DeliverFeedbackContract;
import com.hr.ui.ui.message.model.DeliverFeedBackFragmentModel;
import com.hr.ui.ui.message.presenter.DeliverFeedbackFragmentPresenter;
import com.hr.ui.utils.EventBusAction;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

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
        rvDeliverFeedback.setNestedScrollingEnabled(false);
        rvDeliverFeedback.setRefreshProgressStyle(ProgressStyle.BallPulse);
        rvDeliverFeedback.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        adapter = new MyDeliverFeedbackAdapter();
        ivNoDataSearch.setVisibility(View.GONE);
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
            MobclickAgent.onEvent(getActivity(),"v6_scan_deliverPosition");
            mPresenter.getDeliverFeedBack(page, 0, 0,true);
        } else if (position == 1) {
            MobclickAgent.onEvent(getActivity(),"v6_scan_seem");
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
    protected void onNetworkConnected(NetUtils.NetType type) {
        if (position == 0) {
            MobclickAgent.onEvent(getActivity(),"v6_scan_deliverPosition");
            mPresenter.getDeliverFeedBack(page, 0, 0,false);
        } else if (position == 1) {
            MobclickAgent.onEvent(getActivity(),"v6_scan_seem");
            mPresenter.getDeliverFeedBack(page, 1, 0,false);
        }
    }

    @Override
    protected void onNetworkDisConnected() {

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
                rvDeliverFeedback.refreshComplete();
            } else {
                appliedListBeanList.addAll(appliedListBeanList1);
                rvDeliverFeedback.loadMoreComplete();
                adapter.notifyDataSetChanged();
            }
            if(appliedListBeanList1.size()<20){
                rvDeliverFeedback.setLoadingMoreEnabled(false);
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
                if(appliedListBeanList.get(pos).getIs_expire()==1){
                    ToastUitl.showShort(R.string.error_401);
                    return;
                }else {
                    PositionPageActivity.startAction(getActivity(), appliedListBeanList.get(pos).getJob_id());
                }
            }
        });
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<appliedListBeanList.size();i++){
            if("1".equals(appliedListBeanList.get(i).getIsnew())) {
                sb.append("," + appliedListBeanList.get(i).getRecord_id());
            }else{
                continue;
            }
        }
        if(sb!=null&&!"".equals(sb)&&sb.length()!=0){
            sb.deleteCharAt(0);
            mPresenter.setDeliverFeedBackIsRead(sb.toString());
        }
    }

    @Override
    public void setDeliverFeedBackIsReadSuccess() {
        EventBus.getDefault().post(new EventHomeBean(EventBusAction.MESSAGEFRAGMENT_DELIVERFEEDBACK_READSTATE));
    }
}
