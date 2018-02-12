package com.hr.ui.ui.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.fragment.MessageFragment;
import com.hr.ui.ui.message.adapter.MyCompanyMessageAdapter;
import com.hr.ui.ui.message.contract.InviteContract;
import com.hr.ui.ui.message.model.InviteModel;
import com.hr.ui.ui.message.presenter.InvitePresenter;
import com.hr.ui.utils.Utils;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/16.
 */

public class InviteActivity extends BaseActivity<InvitePresenter, InviteModel> implements InviteContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_inviteJobName)
    TextView tvInviteJobName;
    @BindView(R.id.tv_inviteSalary)
    TextView tvInviteSalary;
    @BindView(R.id.tv_invitePlace)
    TextView tvInvitePlace;
    @BindView(R.id.tv_inviteExp)
    TextView tvInviteExp;
    @BindView(R.id.tv_inviteDegree)
    TextView tvInviteDegree;
    @BindView(R.id.iv_inviteCompanyImage)
    ImageView ivInviteCompanyImage;
    @BindView(R.id.tv_inviteCompanyName)
    TextView tvInviteCompanyName;
    @BindView(R.id.tv_inviteTime)
    TextView tvInviteTime;
    @BindView(R.id.ll_inviteTop)
    LinearLayout llInviteTop;
    @BindView(R.id.rv_invite)
    RecyclerView rvInvite;
    @BindView(R.id.tv_inviteNoPosition)
    TextView tvInviteNoPosition;
    @BindView(R.id.fl_inviteTop)
    FrameLayout flInviteTop;
    private int position;
    private MyCompanyMessageAdapter adapter;
    private InviteBean.InvitedListBean invitedListBean;
    private List<InviteBean.InvitedListBean> invitedListBeanList = new ArrayList<>();

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, InviteBean.InvitedListBean invitedListBean,int i) {
        Intent intent = new Intent(activity, InviteActivity.class);
        intent.putExtra("invites", (Serializable) invitedListBean);
        intent.putExtra("position",i);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        invitedListBean = (InviteBean.InvitedListBean) getIntent().getSerializableExtra("invites");
        //mPresenter.getInviteInfo(invitedListBean.getRecord_id());
        setSupportActionBar(toolBar);
        if(invitedListBean.getJob_id()!=null&&!"".equals(invitedListBean.getJob_id())){
            mPresenter.getJobInfo(invitedListBean.getJob_id(), true);
        }else{
            flInviteTop.setVisibility(View.GONE);
        }
        position=getIntent().getIntExtra("position",0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.companyMessage);
        MobclickAgent.onEvent(this,"v6_scan_companyMessage");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvInvite.setLayoutManager(manager);
        rvInvite.setFocusable(false);
        initUI();
        llInviteTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PositionPageActivity.startAction(InviteActivity.this, invitedListBean.getJob_id());
            }
        });
    }

    private void initUI() {
        if (invitedListBean != null) {
            adapter = new MyCompanyMessageAdapter(this);
            invitedListBeanList.add(invitedListBean);
            adapter.setListBeans(invitedListBeanList);
            rvInvite.setAdapter(adapter);
            mPresenter.setInviteIsRead(invitedListBean.getRecord_id());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
    public void getJobInfoSuccess(PositionBean.JobInfoBean jobInfoBean) {
        intJobInfo(jobInfoBean);
    }

    @Override
    public void setInviteIsReadSuccess() {
        //Log.i("现在的位置",position+"-----");
        MessageFragment.instance.setInviteHide(position);
    }

    @Override
    public void getInviteInfoSuccess() {
    }

    @Override
    public void noPosition() {
        llInviteTop.setVisibility(View.GONE);
        tvInviteNoPosition.setVisibility(View.VISIBLE);
    }

    private void intJobInfo(PositionBean.JobInfoBean jobInfoBean) {
        if (jobInfoBean != null) {
            tvInviteCompanyName.setText(jobInfoBean.getEnterprise_name());
            tvInviteJobName.setText(jobInfoBean.getJob_name());
            tvInviteDegree.setText(jobInfoBean.getStudy());
            tvInviteExp.setText(jobInfoBean.getWorkyear());
            tvInvitePlace.setText(jobInfoBean.getWorkplace());
            tvInviteSalary.setText(Utils.getSalary(jobInfoBean.getSalary()));
            if (jobInfoBean.getEnt_logo() != null && !"".equals(jobInfoBean.getEnt_logo())) {
                Glide.with(this).load(Constants.IMAGE_BASEPATH2 + jobInfoBean.getEnt_logo()).centerCrop().into(ivInviteCompanyImage);
            }
            tvInviteTime.setText(Utils.getDateMonthAndDay(jobInfoBean.getIssue_date()));
        }
    }
}
