package com.hr.ui.ui.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.adapter.MyMessageAdapter;
import com.hr.ui.ui.main.contract.MessageFragmentContract;
import com.hr.ui.ui.main.modle.MessageFragmentModel;
import com.hr.ui.ui.main.presenter.MessageFragmentPresenter;
import com.hr.ui.ui.message.activity.DeliverFeedbackActivity;
import com.hr.ui.ui.message.activity.EmploymentGuidanceActivity;
import com.hr.ui.ui.message.activity.FindActivity;
import com.hr.ui.ui.message.activity.InviteActivity;
import com.hr.ui.ui.message.activity.WhoSeeMeActivity;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
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

public class MessageFragment extends BaseFragment<MessageFragmentPresenter, MessageFragmentModel> implements MessageFragmentContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.fl_message1)
    FrameLayout flMessage1;
    @BindView(R.id.tv_message1)
    TextView tvMessage1;
    @BindView(R.id.tv_feedbackCompanyName)
    TextView tvFeedbackCompanyName;
    @BindView(R.id.tv_feedbackTime)
    TextView tvFeedbackTime;
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
    @BindView(R.id.rl_deliverFeedback)
    RelativeLayout rlDeliverFeedback;
    @BindView(R.id.rl_whoSeeMe)
    RelativeLayout rlWhoSeeMe;
    @BindView(R.id.rl_employmentGuidance)
    RelativeLayout rlEmploymentGuidance;
    @BindView(R.id.rl_find)
    RelativeLayout rlFind;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.iv_messageFeedBackNum)
    ImageView ivMessageFeedBackNum;
    @BindView(R.id.iv_messageWhoSeeMeNum)
    ImageView ivMessageWhoSeeMeNum;
    @BindView(R.id.iv_messageEmploymentGuidanceNum)
    ImageView ivMessageEmploymentGuidanceNum;
    @BindView(R.id.iv_ResumePersonPhoto)
    CircleImageView ivResumePersonPhoto;
    @BindView(R.id.tv_fragmentMessage)
    TextView tvFragmentMessage;
    @BindView(R.id.rl_fragmentTitle)
    RelativeLayout rlFragmentTitle;
    @BindView(R.id.iv_message1)
    ImageView ivMessage1;
    @BindView(R.id.iv_message2)
    ImageView ivMessage2;
    @BindView(R.id.iv_noNetError)
    ImageView ivNoNetError;
    @BindView(R.id.ll_netError)
    LinearLayout llNetError;
    private int page = 1;
    private MyMessageAdapter adapter;
    private boolean isFlesh;
    private String personImage;
    public static MessageFragment instance;
    private SharedPreferencesUtils sUtils;
    private List<InviteBean.InvitedListBean> invitedListBeanList = new ArrayList<>();

    public static MessageFragment newInstance(String s) {
        MessageFragment navigationFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        instance = navigationFragment;
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

    public void setInviteHide(int position) {
        if (invitedListBeanList != null) {

            invitedListBeanList.get(position).setIs_new("0");
            //Log.i("现在的",invitedListBeanList.get(position).toString());
            adapter.notifyDataSetChanged();
        }
    }

    public void setDeliverBackHide() {
        ivMessageFeedBackNum.setVisibility(View.GONE);
    }

    @Override
    public void getWhoSeeMeSuccess(List<WhoSeeMeBean.BrowsedListBean> browsedListBeans) {
        if (isFlesh == true) {
            isFlesh=false;
            ToastUitl.showShort("刷新成功");
        }
        if (browsedListBeans != null && browsedListBeans.size() != 0) {
            ivMessageWhoSeeMeNum.setVisibility(View.GONE);
            tvWhoSeeMeCompanyName.setText(browsedListBeans.get(0).getEnterprise_name());
            tvWhoSeeMeCompanyTime.setText(Utils.getDateMonthAndDay(browsedListBeans.get(0).getBrowsed_time()));
        } else {
            tvWhoSeeMeCompanyName.setText("暂无记录");
            ivMessageWhoSeeMeNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void getDeliverFeedBackSuccess(List<DeliverFeedbackBean.AppliedListBean> appliedListBeanList) {
        llNetError.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.VISIBLE);
        for (int i = 0; i < appliedListBeanList.size(); i++) {
            if ("1".equals(appliedListBeanList.get(i).getIsnew())) {
                ivMessageFeedBackNum.setVisibility(View.VISIBLE);
                break;
            }
        }
        if (appliedListBeanList != null && appliedListBeanList.size() != 0) {
            tvFeedbackCompanyName.setText(appliedListBeanList.get(0).getEnterprise_name());
            //Log.i("现在的时间",appliedListBeanList.get(0).getApplied_time());
            tvFeedbackTime.setText(Utils.getDateMonthAndDay(appliedListBeanList.get(0).getApplied_time()));
        } else {
            tvFeedbackCompanyName.setText("暂无记录");
            ivMessageFeedBackNum.setVisibility(View.GONE);
        }
    }

    @Override
    public void getInviteInterViewSuceess(final List<InviteBean.InvitedListBean> invitedListBeans) {
        if (invitedListBeans != null && invitedListBeans.size() != 0) {
            if (page == 1) {
                adapter = new MyMessageAdapter(getActivity());
                invitedListBeanList.clear();
                invitedListBeanList.addAll(invitedListBeans);
                adapter.setListBeans(invitedListBeanList);
                rvMessage.setAdapter(adapter);
            } else {
                invitedListBeanList.addAll(invitedListBeans);
                adapter.notifyDataSetChanged();
            }
        } else {
            rvMessage.setNoMore(true);
        }
        adapter.setClickCallBack(new MyMessageAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                InviteActivity.startAction(getActivity(), invitedListBeanList.get(pos), pos);
            }
        });
    }

    @Override
    public void netError() {
        llNetError.setVisibility(View.VISIBLE);
        swipeRefresh.setVisibility(View.GONE);
    }

    public void setImage() {
        personImage = sUtils.getStringValue(Constants.PERSONIMAGE, "");
        if (!"".equals(personImage) && personImage != null) {
           /* Glide.with(this).load(Constants.IMAGE_BASEPATH + personImage).centerCrop().into(ivResumePersonPhoto);*/
            Glide.with(this).load(Constants.IMAGE_BASEPATH + personImage).fitCenter() .into(ivResumePersonPhoto);
        }else{
            ivResumePersonPhoto.setImageResource(R.mipmap.persondefault);
        }
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
        sUtils = new SharedPreferencesUtils(getActivity());
        ivResumePersonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.toggle();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        boolean isHaveNews = sUtils.getBooleanValue(Constants.ISHAVENEWS, true);
        if (isHaveNews == true) {
            ivMessageEmploymentGuidanceNum.setVisibility(View.VISIBLE);
        } else {
            ivMessageEmploymentGuidanceNum.setVisibility(View.GONE);
        }
        setImage();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessage.setLayoutManager(linearLayoutManager);
        /*Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
        rvCollection.addItemDecoration(rvCollection.new DividerItemDecoration(dividerDrawable));*/
        rvMessage.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvMessage.setNestedScrollingEnabled(false);
        rvMessage.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        adapter = new MyMessageAdapter(getActivity());
        rvMessage.setLoadingMoreEnabled(false);
        rvMessage.setPullRefreshEnabled(false);
        swipeRefresh.setColorSchemeResources(R.color.new_main);
        // 设置下拉监听，当用户下拉的时候会去执行回调
        swipeRefresh.setOnRefreshListener(this);
        // 调整进度条距离屏幕顶部的距离
        swipeRefresh.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        getDate(true);

    }

    public void getDate(boolean b) {
        page=1;
        mPresenter.getDeliverFeedback(page, 0, 0, b);
        mPresenter.getInviteInterview(page);
        mPresenter.getWHoSeeMe(page);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            getDate(false);
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

    @OnClick({R.id.rl_deliverFeedback,R.id.iv_noNetError, R.id.rl_whoSeeMe, R.id.rl_employmentGuidance, R.id.rl_find})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_noNetError:
                getDate(true);
                break;
            case R.id.rl_deliverFeedback:
                DeliverFeedbackActivity.startAction(getActivity());
                break;
            case R.id.rl_whoSeeMe:
                WhoSeeMeActivity.startAction(getActivity());
                break;
            case R.id.rl_employmentGuidance:
                sUtils.setBooleanValue(Constants.ISHAVENEWS, false);
                ivMessageEmploymentGuidanceNum.setVisibility(View.GONE);
                EmploymentGuidanceActivity.startAction(getActivity());
                break;
            case R.id.rl_find:
                FindActivity.startAction(getActivity());
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isFlesh = true;
                // 设置SwipeRefreshLayout当前是否处于刷新状态，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false。
                getDate(false);
                if (swipeRefresh != null) {
                    swipeRefresh.setRefreshing(false);
                }
            }
        }, 2000);
    }
}
