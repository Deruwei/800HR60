package com.hr.ui.ui.job.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.Base2Activity;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.ScanHistoryBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.ScanHistoryUtils;
import com.hr.ui.ui.job.contract.PositionPageContract;
import com.hr.ui.ui.job.model.PositionPageModel;
import com.hr.ui.ui.job.presenter.PositionPagePresenter;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2018/1/11.
 */

public class PositionPageActivity extends Base2Activity<PositionPagePresenter, PositionPageModel> implements PositionPageContract.View {
    @BindView(R.id.tv_positionPageJobName)
    TextView tvPositionPageJobName;
    @BindView(R.id.iv_positionPageClose)
    ImageView ivPositionPageClose;
    @BindView(R.id.tv_positionPageAddress)
    TextView tvPositionPageAddress;
    @BindView(R.id.tv_positionPageExp)
    TextView tvPositionPageExp;
    @BindView(R.id.tv_positionPageDegree)
    TextView tvPositionPageDegree;
    @BindView(R.id.tv_positionPageNum)
    TextView tvPositionPageNum;
    @BindView(R.id.tv_positionPageSalary)
    TextView tvPositionPageSalary;
    @BindView(R.id.tv_positionPageReleaseTime)
    TextView tvPositionPageReleaseTime;
    @BindView(R.id.iv_positionPageCompanyIcon)
    RoundImageView ivPositionPageCompanyIcon;
    @BindView(R.id.tv_positionPageCompanyName)
    TextView tvPositionPageCompanyName;
    @BindView(R.id.tv_positionPageIndustry)
    TextView tvPositionPageIndustry;
    @BindView(R.id.tv_positionPageCompanyType)
    TextView tvPositionPageCompanyType;
    @BindView(R.id.tv_positionPageCompanyScale)
    TextView tvPositionPageCompanyScale;
    @BindView(R.id.tv_positionPageJobDes)
    TextView tvPositionPageJobDes;
    @BindView(R.id.tv_positionPageCulScore)
    TextView tvPositionPageCulScore;
    @BindView(R.id.ll_positionPageCulScore)
    LinearLayout llPositionPageCulScore;
    @BindView(R.id.iv_positionPageCollection)
    ImageView ivPositionPageCollection;
    @BindView(R.id.tv_positionPageCollection)
    TextView tvPositionPageCollection;
    @BindView(R.id.ll_positionPageCollection)
    LinearLayout llPositionPageCollection;
    @BindView(R.id.tv_positionPageDeliverResume)
    TextView tvPositionPageDeliverResume;
    @BindView(R.id.ll_positionPageDeliverResume)
    LinearLayout llPositionPageDeliverResume;
    @BindView(R.id.ll_positionPageBottom)
    LinearLayout llPositionPageBottom;
    private String jobId,companyId;
    private int collection,apply;
    private int mWidth, mHeight;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String jobId) {
        Intent intent = new Intent(activity, PositionPageActivity.class);
        intent.putExtra("jobId", jobId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.zoom_in,
                R.anim.zoom_out);
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
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = mWidth-mWidth/10 ;
        lp.height = mHeight-mHeight/10*2;
        getWindowManager().updateViewLayout(view, lp);
    }

    @Override
    public void getPositionSuccess(PositionBean.JobInfoBean jobInfoBean) {
        initUI(jobInfoBean);
    }

    private void initUI(PositionBean.JobInfoBean jobInfoBean) {
        if(jobInfoBean!=null&&!"".equals(jobInfoBean)&&!"[]".equals(jobInfoBean)) {
            ScanHistoryBean scanHistoryBean=new ScanHistoryBean();
            scanHistoryBean.setJobId(jobInfoBean.getJob_id());
            scanHistoryBean.setCompanyName(jobInfoBean.getEnterprise_name());
            scanHistoryBean.setDegree(jobInfoBean.getStudy());
            scanHistoryBean.setPlace(jobInfoBean.getWorkplace());
            scanHistoryBean.setJobName(jobInfoBean.getJob_name());
            scanHistoryBean.setExp(jobInfoBean.getWorkyear());
            scanHistoryBean.setSalary(jobInfoBean.getSalary());
            SimpleDateFormat formatter   =   new   SimpleDateFormat   ("M - dd");
            Date curDate =  new Date(System.currentTimeMillis());
            String   str   =   formatter.format(curDate);
            scanHistoryBean.setTime(str);
            ScanHistoryUtils.getInstance().insertOrReplcae(scanHistoryBean);
            //Log.i("职位的信息",jobInfoBean.toString());
            tvPositionPageJobName.setText(jobInfoBean.getJob_name());
            tvPositionPageAddress.setText(jobInfoBean.getWorkplace());
            tvPositionPageExp.setText(jobInfoBean.getWorkyear());
            tvPositionPageDegree.setText(jobInfoBean.getStudy());
            tvPositionPageNum.setText(jobInfoBean.getNumber());
            tvPositionPageSalary.setText(Utils.getSalary(jobInfoBean.getSalary()));
            tvPositionPageReleaseTime.setText(Utils.getDateMonthAndDay(jobInfoBean.getIssue_date()));
            tvPositionPageCompanyName.setText(jobInfoBean.getEnterprise_name());
            tvPositionPageIndustry.setText("[" + jobInfoBean.getIndustry_name() + "]");
            tvPositionPageCompanyType.setText(jobInfoBean.getCompany_type());
            tvPositionPageCompanyScale.setText(jobInfoBean.getStuff_munber());
            tvPositionPageJobDes.setText(jobInfoBean.getSynopsis());
            collection = jobInfoBean.getIs_favourite();
            if (jobInfoBean.getEnt_logo() != null && !"".equals(jobInfoBean.getEnt_logo())) {
                Utils.setImageResource(this,ivPositionPageCompanyIcon,Constants.IMAGE_BASEPATH2+jobInfoBean.getEnt_logo());
            } else {
               Utils.setImageResourceDefault(this,ivPositionPageCompanyIcon);
            }
            companyId = jobInfoBean.getEnterprise_id();
            if (jobInfoBean.getIs_favourite() == 0) {
                tvPositionPageCollection.setText(R.string.collection);
            } else if (jobInfoBean.getIs_favourite() == 1) {
                tvPositionPageCollection.setText(R.string.allReadyCollection);
            }

            apply = jobInfoBean.getIs_apply();
            if (apply == 1) {
                tvPositionPageDeliverResume.setText(R.string.allReadyDeliver);
            } else if (apply == 0) {
                tvPositionPageDeliverResume.setText(R.string.deliverResume);
            }
        }
    }

    @Override
    public void collectionPositionSuccess() {
        if(collection==0){
            tvPositionPageCollection.setText(R.string.allReadyCollection);
        }else if(collection==1){
            tvPositionPageCollection.setText(R.string.collection);
        }
    }

    @Override
    public void deliverPositionSuccess() {
        if(apply==1){
            tvPositionPageDeliverResume.setText(R.string.deliverResume);
        }else if(apply==0){
            tvPositionPageDeliverResume.setText(R.string.allReadyDeliver);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_positionpage;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        jobId = getIntent().getStringExtra("jobId");
        mPresenter.getPositionData(jobId);
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

    @OnClick({R.id.rl_positionPageCompanyInfo,R.id.iv_positionPageClose, R.id.ll_positionPageCulScore, R.id.ll_positionPageCollection, R.id.ll_positionPageDeliverResume})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_positionPageCompanyInfo:
                CompanyPageActivity.startAction(this,companyId);
                break;
            case R.id.iv_positionPageClose:
                finish();
                break;
            case R.id.ll_positionPageCulScore:
                break;
            case R.id.ll_positionPageCollection:
                mPresenter.collectionPosition(jobId);
                break;
            case R.id.ll_positionPageDeliverResume:
                mPresenter.deliverPosition(jobId);
                break;
        }
    }
}