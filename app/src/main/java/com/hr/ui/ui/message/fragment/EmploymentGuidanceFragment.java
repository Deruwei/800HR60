package com.hr.ui.ui.message.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.GuidanceBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.message.activity.GuidanceInfoActivity;
import com.hr.ui.ui.message.adapter.MyDeliverFeedbackAdapter;
import com.hr.ui.ui.message.adapter.MyFindAdapter;
import com.hr.ui.ui.message.adapter.MyGuidanceAdapter;
import com.hr.ui.ui.message.contract.EmploymentGuidanceContract;
import com.hr.ui.ui.message.model.EmploymentGuidanceModel;
import com.hr.ui.ui.message.presenter.EmploymentGuidancePresenter;
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

public class EmploymentGuidanceFragment extends BaseFragment<EmploymentGuidancePresenter, EmploymentGuidanceModel> implements EmploymentGuidanceContract.View {
    @BindView(R.id.rv_deliverFeedback)
    XRecyclerView rvDeliverFeedback;
    Unbinder unbinder;
    private int page=1;
    private int position;
    private String guidanceId;
    private boolean can;
    private MyGuidanceAdapter adapter;
    private List<GuidanceBean.TitleListBean> titleListBeans=new ArrayList<>();
    public static EmploymentGuidanceFragment newInstance(int i) {
        EmploymentGuidanceFragment navigationFragment = new EmploymentGuidanceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",i);
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
    protected int getLayoutResource() {
        return R.layout.layout_deliverfeedback;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        initRv();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        position=getArguments().getInt("position");
        checkEmploymentId(position);
        mPresenter.getEmploymentGuidanceData(page, guidanceId, true);

    }

    private void checkEmploymentId(int position){
        if(position==0){
            guidanceId="83";
            MobclickAgent.onEvent(getActivity(),"v6_scan_bookCover");
        }else if(position==1){
            guidanceId="120";
            MobclickAgent.onEvent(getActivity(),"v6_scan_occupationGuidance");
        }else if(position==2){
            guidanceId="86";
            MobclickAgent.onEvent(getActivity(),"v6_scan_occupationDiagnose");
        }else if(position==3){
            guidanceId="82";
            MobclickAgent.onEvent(getActivity(),"v6_scan_workGrossip");
        }else if(position==4){
            guidanceId="723";
            MobclickAgent.onEvent(getActivity(),"v6_scan_startUpGuidance");
        }else if(position==5){
            guidanceId="121";
            MobclickAgent.onEvent(getActivity(),"v6_scan_interviewWay");
        }else if(position==6){
            guidanceId="846";
            MobclickAgent.onEvent(getActivity(),"v6_scan_modelWorker");
        }else if(position==7){
            guidanceId="87";
            MobclickAgent.onEvent(getActivity(),"v6_scan_relationShip");
        }else if(position==8) {
            guidanceId="122";
            MobclickAgent.onEvent(getActivity(),"v6_scan_salaryInfo");
        }
    }
    private void initRv() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity()){
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
       adapter=new MyGuidanceAdapter();
        rvDeliverFeedback.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        page=1;
                        mPresenter.getEmploymentGuidanceData(page,guidanceId,false);
                        adapter.notifyDataSetChanged();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        mPresenter.getEmploymentGuidanceData(page,guidanceId,false);
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

    @Override
    public void getEmploymentGuidanceSuccess(final List<GuidanceBean.TitleListBean> titleListBeanList) {
        if(titleListBeanList!=null&&!"[]".equals(titleListBeanList)&&!"".equals(titleListBeanList)&&titleListBeanList.size()!=0){
            if(page==1){
                adapter=new MyGuidanceAdapter();
                titleListBeans=new ArrayList<>();
                titleListBeans.addAll(titleListBeanList);
                adapter.setFavouriteListBeanList(titleListBeans);
                rvDeliverFeedback.setAdapter(adapter);
                rvDeliverFeedback.refreshComplete();
            }else{
                titleListBeans.addAll(titleListBeanList);
                rvDeliverFeedback.loadMoreComplete();
                adapter.notifyDataSetChanged();
            }
        }else{
            rvDeliverFeedback.setNoMore(true);
        }
        adapter.setClickCallBack(new MyGuidanceAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                GuidanceInfoActivity.startAction(getActivity(),position,titleListBeans.get(pos).getId());
            }
        });
      /*  adapter.setClickCallBack(new MyDeliverFeedbackAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                *//*PositionPageActivity.startAction(getActivity(),titleListBeans.get(pos).getCatid());*//*
            }
        });*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser==true){
            can=true;
        }
    }
}
