package com.hr.ui.ui.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.Base2Activity;
import com.hr.ui.bean.EventBoolean;
import com.hr.ui.bean.EventHomeBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.job.adapter.MyRecommendJobDialogAdapter;
import com.hr.ui.ui.job.contract.RecommendJobActivityContract;
import com.hr.ui.ui.job.model.RecommendJobActivityModel;
import com.hr.ui.ui.job.presenter.RecommendJobActivityPresenter;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyRecommendDialog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/11.
 */

public class RecommendJobActivity extends Base2Activity<RecommendJobActivityPresenter, RecommendJobActivityModel> implements RecommendJobActivityContract.View {
    @BindView(R.id.iv_recommendJobDialogClose)
    ImageView ivRecommendJobDialogClose;
    @BindView(R.id.iv_recommendJobDialogIcon)
    ImageView ivRecommendJobDialogIcon;
    @BindView(R.id.tv_recommendJobText1)
    TextView tvRecommendJobText1;
    @BindView(R.id.tv_tv_recommendJobText2)
    TextView tvTvRecommendJobText2;
    @BindView(R.id.view_recommendJobDialogLine)
    View viewRecommendJobDialogLine;
    @BindView(R.id.rv_recommendJobDialog)
    RecyclerView rvRecommendJobDialog;
    @BindView(R.id.btn_recommendJobDeliver)
    Button btnRecommendJobDeliver;
    @BindView(R.id.cl_recommendJobActivity)
    ConstraintLayout clRecommendJobActivity;
    private String jobId, companyId, tag;
    private int mWidth, mHeight;
    private SharedPreferencesUtils sUtils;
    private MyRecommendJobDialogAdapter adapter;
    private List<RecommendJobBean.JobsListBean> list;
    private PositionBean.JobInfoBean jobInfoBean;
    private MyRecommendDialog dialog;
    private boolean isSelect;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, PositionBean.JobInfoBean jobInfoBean) {
        Intent intent = new Intent(activity, RecommendJobActivity.class);
        intent.putExtra("jobInfo", jobInfoBean);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.zoom_in,
                R.anim.zoom_out);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        //设置改页面的宽高占手机屏幕的比例
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = mWidth - mWidth / 10;
        lp.height = mHeight - mHeight / 20 * 3;
        getWindowManager().updateViewLayout(view, lp);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_recommendjob_dialog;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        jobInfoBean = (PositionBean.JobInfoBean) getIntent().getSerializableExtra("jobInfo");
        sUtils = new SharedPreferencesUtils(this);
        tag = getIntent().getStringExtra("tag");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        JobSearchBean jobSearchBean = new JobSearchBean();
        jobSearchBean.setIndustryId(jobInfoBean.getIndustry());
        jobSearchBean.setPositionId(jobInfoBean.getJob_id());
        jobSearchBean.setDegree(ResumeInfoIDToString.getDegreeNeedId(jobInfoBean.getStudy()));
        jobSearchBean.setPlaceId(jobInfoBean.getWork_area());
        mPresenter.getSearchList(jobSearchBean, 1, false);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRecommendJobDialog.setLayoutManager(linearLayoutManager);
        String s = getString(R.string.RecommendDialogDeliverNum1) + (new Random().nextInt(8) + 31) + getString(R.string.RecommendDialogDeliverNum2);
        SpannableString mStyledText = new SpannableString(s);
        //对字符串 "系统开小差，请尝试刷新一下" 进行处理，将“刷新”两个字设置为蓝色的 且可点击的
        mStyledText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main)), 15, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTvRecommendJobText2.setText(mStyledText);
    }

    private void setAdapter() {
        adapter = new MyRecommendJobDialogAdapter();
        adapter.setListBeans(list);
        rvRecommendJobDialog.setAdapter(adapter);
        adapter.setClickCallBack(new MyRecommendJobDialogAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                PositionPageActivity.startAction(RecommendJobActivity.this, list.get(pos).getJob_id(),pos,RecommendJobActivity.class.getSimpleName());
            }
        });
        adapter.setOnSelectClickListener(new MyRecommendJobDialogAdapter.OnSelectClickListener() {
            @Override
            public void onSelectClickListener(int pos) {
                if (list.get(pos).isCheck()) {
                    list.get(pos).setCheck(false);
                } else {
                    list.get(pos).setCheck(true);
                }
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventBoolean eventBoolean) {
        switch (eventBoolean.getType()) {
            case 0:
                int num = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isCheck()) {
                        num++;
                    }
                }
                if (num == 0) {
                    btnRecommendJobDeliver.setBackgroundResource(R.drawable.edit_bg_gray);
                    btnRecommendJobDeliver.setClickable(false);
                } else {
                    btnRecommendJobDeliver.setBackgroundResource(R.drawable.edit_bg_orange);
                    btnRecommendJobDeliver.setClickable(true);
                }
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent2(EventHomeBean eventHomeBean) {
        switch (eventHomeBean.getType()) {
            case 11:
              list.get(eventHomeBean.getPosition()).setCheck(false);
              list.get(eventHomeBean.getPosition()).setIs_apply(1);
              if(adapter!=null){
                  adapter.notifyDataSetChanged();
              }
              break;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;

        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @OnClick({R.id.iv_recommendJobDialogClose, R.id.btn_recommendJobDeliver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_recommendJobDialogClose:
                finish();
                break;
            case R.id.btn_recommendJobDeliver:
                deliverJob();
                break;
        }
    }

    private void deliverJob() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheck()) {
                sb.append("," + list.get(i).getJob_id());
            }
        }
        if (sb != null) {
            jobId = sb.deleteCharAt(0).toString();
        }
        //Log.i("现在的数据",jobId);
        if (jobId != null && !"".equals(jobId)) {
            mPresenter.deliverPosition(jobId.substring(jobId.indexOf(",") + 1));
        } else {
            ToastUitl.showShort("请选择职位");
        }
    }

    @Override
    public void getSearchDataSuccess(List<RecommendJobBean.JobsListBean> jobsListBean) {
        list = new ArrayList<>();
        int num = 0;
        clRecommendJobActivity.setVisibility(View.VISIBLE);
        if (jobsListBean != null && !"".equals(jobsListBean) && jobsListBean.size() != 0) {
            rvRecommendJobDialog.setVisibility(View.VISIBLE);
            btnRecommendJobDeliver.setVisibility(View.VISIBLE);
            viewRecommendJobDialogLine.setVisibility(View.VISIBLE);
            if (jobsListBean.size() > 4) {
                for (int i = 0; i < jobsListBean.size(); i++) {
                    if (num < 4) {
                        if (jobsListBean.get(i).getIs_apply() == 0) {
                            list.add(jobsListBean.get(i));
                            num++;
                        }
                    } else {
                        break;
                    }
                }
            } else {
                for (int i = 0; i < jobsListBean.size(); i++) {
                    if (jobsListBean.get(i).getIs_apply() == 0) {
                        list.add(jobsListBean.get(i));
                    }
                }
                // list = jobsListBean;
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCheck(true);
            }
            setAdapter();
        } else {
        }
    }

    /**
     * 简历不符合跳出提示框
     *
     * @param type
     */
    private void setPopupwindow(int type) {
        dialog = new MyRecommendDialog(this);
        if (type == 1) {
            dialog.setTitle(getString(R.string.resumeNoComplete));
            dialog.setMessage(getString(R.string.resumeNoCompleteContent));
            dialog.setNoOnclickListener(getString(R.string.known), new MyRecommendDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                }
            });
            dialog.setYesOnclickListener(getString(R.string.goNow), new MyRecommendDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
                    finish();
                    MainActivity.instance.rbResume1.setChecked(true);
                    ResumeFragment.instance.refresh();
                }
            });
        } else {
            dialog.setTitle(getString(R.string.noIndustry1) + jobInfoBean.getIndustry_name() + "行业" + getString(R.string.noIndustry2));
            dialog.setMessage(getString(R.string.noJobOrderContent1) + jobInfoBean.getIndustry_name() + "行业" + getString(R.string.noJobOrderContent2));
            dialog.setNoOnclickListener(getString(R.string.known), new MyRecommendDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                }
            });
            dialog.setYesOnclickListener(getString(R.string.goNow), new MyRecommendDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
                    ResumeJobOrderActivity.startAction(RecommendJobActivity.this);
                }
            });
        }
        dialog.show();
    }

    @Override
    public void deliverPositionSuccess() {
        MobclickAgent.onEvent(this, "v6_resume_deliver");
        int i = sUtils.getIntValue(Constants.IS_RECOMMENDJOB, 0);
        sUtils.setIntValue(Constants.IS_RECOMMENDJOB, i + 1);
        ToastUitl.showShort("投递成功");
        finish();
    }

    @Override
    public void goToCompleteResume(int errorCode) {
        if (errorCode == 413 || errorCode == 417) {
            setPopupwindow(2);
        } else {
            setPopupwindow(1);
        }
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
