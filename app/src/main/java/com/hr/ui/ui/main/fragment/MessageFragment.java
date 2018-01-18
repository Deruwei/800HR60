package com.hr.ui.ui.main.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.adapter.MyMessageAdapter;
import com.hr.ui.ui.main.contract.MessageFragmentContract;
import com.hr.ui.ui.main.modle.MessageFragmentModel;
import com.hr.ui.ui.main.presenter.MessageFragmentPresenter;
import com.hr.ui.ui.me.adapter.MainRecyclerAdapter;
import com.hr.ui.ui.message.activity.DeliverFeedbackActivity;
import com.hr.ui.ui.message.activity.EmploymentGuidanceActivity;
import com.hr.ui.ui.message.activity.FindActivity;
import com.hr.ui.ui.message.activity.InviteActivity;
import com.hr.ui.ui.message.activity.WhoSeeMeActivity;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wdr on 2018/1/15.
 */

public class MessageFragment extends BaseFragment<MessageFragmentPresenter, MessageFragmentModel> implements MessageFragmentContract.View {
    @BindView(R.id.tv_messageFeedBackNum)
    TextView tvMessageFeedBackNum;
    @BindView(R.id.fl_message1)
    FrameLayout flMessage1;
    @BindView(R.id.tv_message1)
    TextView tvMessage1;
    @BindView(R.id.tv_feedbackCompanyName)
    TextView tvFeedbackCompanyName;
    @BindView(R.id.tv_feedbackTime)
    TextView tvFeedbackTime;
    @BindView(R.id.tv_messageWhoSeeMeNum)
    TextView tvMessageWhoSeeMeNum;
    @BindView(R.id.fl_message2)
    FrameLayout flMessage2;
    @BindView(R.id.tv_message2)
    TextView tvMessage2;
    @BindView(R.id.tv_whoSeeMeCompanyName)
    TextView tvWhoSeeMeCompanyName;
    @BindView(R.id.tv_whoSeeMeCompanyTime)
    TextView tvWhoSeeMeCompanyTime;
    @BindView(R.id.iv_message3)
    ImageView ivMessage3;
    @BindView(R.id.tv_messageEmploymentGuidanceNum)
    TextView tvMessageEmploymentGuidanceNum;
    @BindView(R.id.fl_message3)
    FrameLayout flMessage3;
    @BindView(R.id.tv_message3)
    TextView tvMessage3;
    @BindView(R.id.iv_message4)
    ImageView ivMessage4;
    @BindView(R.id.tv_messageFindNum)
    TextView tvMessageFindNum;
    @BindView(R.id.fl_message4)
    FrameLayout flMessage4;
    @BindView(R.id.tv_message4)
    TextView tvMessage4;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.rv_message)
    XRecyclerView rvMessage;
    Unbinder unbinder;
    private int page=1;
    private MyMessageAdapter adapter;
    private List<InviteBean.InvitedListBean> invitedListBeanList=new ArrayList<>();

    public static MessageFragment newInstance(String s) {
        MessageFragment navigationFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
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
    public void getWhoSeeMeSuccess(List<WhoSeeMeBean.BrowsedListBean> browsedListBeans) {
        if(browsedListBeans!=null&&browsedListBeans.size()!=0){
            tvMessageWhoSeeMeNum.setText(browsedListBeans.size()+"");
        }else{
            tvMessageWhoSeeMeNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDeliverFeedBackSuccess(List<DeliverFeedbackBean.AppliedListBean> appliedListBeanList) {
        if (appliedListBeanList != null && appliedListBeanList.size() != 0) {
            tvMessageFeedBackNum.setText(appliedListBeanList.size() + "");
        } else {
            tvMessageFeedBackNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void getInviteInterViewSuceess(final List<InviteBean.InvitedListBean> invitedListBeans) {
        if(invitedListBeans!=null&&invitedListBeans.size()!=0){
            if(page==1){
                invitedListBeanList.clear();
                invitedListBeanList.addAll(invitedListBeans);
                adapter.setListBeans(invitedListBeanList);
                rvMessage.setAdapter(adapter);
            }else{
                invitedListBeanList.addAll(invitedListBeans);
                adapter.notifyDataSetChanged();
            }
        }else{
            rvMessage.setNoMore(true);
        }
        adapter.setClickCallBack(new MyMessageAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                InviteActivity.startAction(getActivity(),invitedListBeans.get(pos));
            }
        });
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessage.setLayoutManager(linearLayoutManager);
        /*Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
        rvCollection.addItemDecoration(rvCollection.new DividerItemDecoration(dividerDrawable));*/
        rvMessage.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvMessage.setNestedScrollingEnabled(false);
        rvMessage.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        adapter=new MyMessageAdapter(getActivity());
        rvMessage.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        page=1;
                        mPresenter.getInviteInterview(page);
                        adapter.notifyDataSetChanged();
                        rvMessage.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        mPresenter.getInviteInterview(page);
                        rvMessage.loadMoreComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getDeliverFeedback(page, 0, 0);
        mPresenter.getInviteInterview(page);
        mPresenter.getWHoSeeMe(page);
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

    @OnClick({R.id.rl_deliverFeedback, R.id.rl_whoSeeMe, R.id.rl_employmentGuidance, R.id.rl_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_deliverFeedback:
                DeliverFeedbackActivity.startAction(getActivity());
                break;
            case R.id.rl_whoSeeMe:
                WhoSeeMeActivity.startAction(getActivity());
                break;
            case R.id.rl_employmentGuidance:
                EmploymentGuidanceActivity.startAction(getActivity());
                break;
            case R.id.rl_find:
                FindActivity.startAction(getActivity());
                break;
        }
    }
}
