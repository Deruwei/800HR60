package com.hr.ui.ui.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.base.Base2Activity;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.ScanHistoryBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.ScanHistoryUtils;
import com.hr.ui.ui.job.contract.PositionPageContract;
import com.hr.ui.ui.job.model.PositionPageModel;
import com.hr.ui.ui.job.presenter.PositionPagePresenter;
import com.hr.ui.utils.ClickUtils;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.PieChartView;
import com.hr.ui.view.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    @BindView(R.id.rl_positionPageCompanyInfo)
    RelativeLayout rlPositionPageCompanyInfo;
    @BindView(R.id.pcv_culScore)
    PieChartView pcvCulScore;
    @BindView(R.id.view_lin1)
    View viewLin1;
    @BindView(R.id.view_lin2)
    View viewLin2;
    @BindView(R.id.iv_positionPageShare)
    ImageView ivPositionPageShare;
    @BindView(R.id.cl_positionPage)
    LinearLayout clPositionPage;
    @BindView(R.id.iv_culScore)
    ImageView ivCulScore;
    @BindView(R.id.iv_loadCul)
    ImageView ivLoadCul;
    private AnimationDrawable drawable;
    private String jobId, companyId;
    private int collection, apply;
    private int mWidth, mHeight;
    private int score;
    private SharedPreferencesUtils sUtils;
    private PositionBean.JobInfoBean jobInfoBean;
    private int type;//1代表的是从主页过来的  2。代表从其他页面过来的  主页过来带有算分
    private long currTime = 0;// 分享的点击时间

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String jobId, int type, int score) {
        Intent intent = new Intent(activity, PositionPageActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("jobId", jobId);
        intent.putExtra("score", score);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.zoom_in,
                R.anim.zoom_out);
    }

    public static void startAction(Activity activity, String jobId, int type) {
        Intent intent = new Intent(activity, PositionPageActivity.class);
        intent.putExtra("type", type);
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
        lp.width = mWidth - mWidth / 10;
        lp.height = mHeight - mHeight / 10 * 2;
        getWindowManager().updateViewLayout(view, lp);
        jobId = getIntent().getStringExtra("jobId");
        type = getIntent().getIntExtra("type", 0);
        score = getIntent().getIntExtra("score", 0);
        mPresenter.getPositionData(jobId, this);
    }

    @Override
    public void getPositionSuccess(PositionBean.JobInfoBean jobInfoBean) {
        initUI(jobInfoBean);
    }

    private void initUI(PositionBean.JobInfoBean jobInfoBean1) {
        jobInfoBean = jobInfoBean1;
        if (jobInfoBean != null && !"".equals(jobInfoBean) && !"[]".equals(jobInfoBean)) {
            ScanHistoryBean scanHistoryBean = new ScanHistoryBean();
            scanHistoryBean.setJobId(jobInfoBean.getJob_id());
            scanHistoryBean.setCompanyName(jobInfoBean.getEnterprise_name());
            scanHistoryBean.setDegree(jobInfoBean.getStudy());
            scanHistoryBean.setPlace(jobInfoBean.getWorkplace());
            scanHistoryBean.setJobName(jobInfoBean.getJob_name());
            scanHistoryBean.setExp(jobInfoBean.getWorkyear());
            scanHistoryBean.setSalary(jobInfoBean.getSalary());
            SimpleDateFormat formatter = new SimpleDateFormat("M - d");
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
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
          /*  if (type == 1) {
                pcvCulScore.SetProgram(score);
                tvPositionPageCulScore.setText(score + "分");
            }*/
            if (jobInfoBean.getEnt_logo() != null && !"".equals(jobInfoBean.getEnt_logo())) {
                Utils.setImageResource(this, ivPositionPageCompanyIcon, Constants.IMAGE_BASEPATH2 + jobInfoBean.getEnt_logo());
            } else {
                Utils.setImageResourceDefault(this, ivPositionPageCompanyIcon);
            }
            companyId = jobInfoBean.getEnterprise_id();
            if (jobInfoBean.getIs_favourite() == 0) {
                tvPositionPageCollection.setText(R.string.collection);
                ivPositionPageCollection.setImageResource(R.mipmap.collection_already);
            } else if (jobInfoBean.getIs_favourite() == 1) {
                ivPositionPageCollection.setImageResource(R.mipmap.collection_white);
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
        if (collection == 0) {
            ivPositionPageCollection.setImageResource(R.mipmap.collection_white);
            tvPositionPageCollection.setText(R.string.allReadyCollection);
        } else if (collection == 1) {
            ivPositionPageCollection.setImageResource(R.mipmap.collection_already);
            tvPositionPageCollection.setText(R.string.collection);
        }
    }

    @Override
    public void deliverPositionSuccess() {
        if (apply == 1) {
            tvPositionPageDeliverResume.setText(R.string.deliverResume);
        } else if (apply == 0) {
            tvPositionPageDeliverResume.setText(R.string.allReadyDeliver);
        }
    }

    @Override
    public void getResumeScoreSuccess(double s) {
        drawable.stop();
        pcvCulScore.setVisibility(View.VISIBLE);
        ivLoadCul.setVisibility(View.GONE);
        int number = (int) (s * 100.0);
        Log.i("当前的数字",number+"----");
        tvPositionPageCulScore.setText(number+"分");
        pcvCulScore.SetProgram(number);
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
        sUtils = new SharedPreferencesUtils(this);
        drawable= (AnimationDrawable) ivLoadCul.getDrawable();
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

    @OnClick({R.id.rl_positionPageCompanyInfo, R.id.ll_positionPageCulScore, R.id.iv_positionPageShare, R.id.iv_positionPageClose, R.id.ll_positionPageCollection, R.id.ll_positionPageDeliverResume})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_positionPageCulScore:
                llPositionPageCulScore.setEnabled(false);
                if (!ClickUtils.isFastClick()) {
                    ivLoadCul.setVisibility(View.VISIBLE);
                    ivCulScore.setVisibility(View.GONE);
                    drawable.start();
                    mPresenter.getResumeScore(jobInfoBean.getJob_id() + ":" + sUtils.getStringValue(Constants.USERID, "") + "_" + sUtils.getIntValue(Constants.RESUME_ID, 0) + "_zh");
                }
                break;
            case R.id.iv_positionPageShare:
                doShare();
                break;
            case R.id.rl_positionPageCompanyInfo:
                CompanyPageActivity.startAction(this, companyId);
                break;
            case R.id.iv_positionPageClose:
                finish();
                break;
            case R.id.ll_positionPageCollection:
                mPresenter.collectionPosition(jobId);
                break;
            case R.id.ll_positionPageDeliverResume:
                mPresenter.deliverPosition(jobId);
                break;
        }
    }

    private void doShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
//                oks.addHiddenPlatform(QQ.NAME);
        String text = "";
        String mobilUrl = "";
        String enterprise_name = "";
        String jobnameString = "";
        String enterpriseId = "";
        String jobId = "";
        if (!"".equals(jobInfoBean.getEnterprise_name()) && jobInfoBean.getEnterprise_name() != null) {
            enterprise_name = jobInfoBean.getEnterprise_name();
        }
        if (!"".equals(jobInfoBean.getJob_name()) && jobInfoBean.getJob_name() != null) {
            jobnameString = jobInfoBean.getJob_name();
        }
        if (!"".endsWith(jobInfoBean.getEnterprise_id()) && jobInfoBean.getEnterprise_id() != null) {
            enterpriseId = jobInfoBean.getEnterprise_id();
        }
        if (!"".equals(jobInfoBean.getJob_id()) && jobInfoBean.getJob_id() != null) {
            jobId = jobInfoBean.getJob_id();
        }
        if (jobId != null && !"".equals(jobId) && jobnameString != null) {
            text = "我在行业找工作上看到了" + enterprise_name + "的" + jobnameString + "职位";
            mobilUrl = EncryptUtils.getJobUrl(jobId, jobInfoBean.getIndustry());
        }
        //System.out.println("mobilUrl==" + mobilUrl);
        text = text + " " + mobilUrl;
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(text);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(mobilUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//                if (!Image_path.equals("")) {
//                    String fileName = FileUtil.getRootDir() + "/800HR/Poster/"
//                            + Image_path.substring(Image_path.lastIndexOf("/") + 1);
//                    oks.setImagePath(fileName);
//                }
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setImagePath(EncryptUtils.getBenDiPhoto(this));
        oks.setUrl(mobilUrl);
        // 启动分享GUI,防止用户多次点击
        if (System.currentTimeMillis() - currTime > 500) {
            oks.show(this);
        }
        currTime = System.currentTimeMillis();
    }
}
