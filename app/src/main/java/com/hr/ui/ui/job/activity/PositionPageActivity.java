package com.hr.ui.ui.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.base.Base2Activity;
import com.hr.ui.bean.EventHomeBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.bean.ScanHistoryBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.ScanHistoryUtils;
import com.hr.ui.ui.job.contract.PositionPageContract;
import com.hr.ui.ui.job.model.PositionPageModel;
import com.hr.ui.ui.job.presenter.PositionPagePresenter;
import com.hr.ui.ui.main.activity.JobSearchResultActivity;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.fragment.HomeFragment;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.utils.ClickUtils;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.EventBusAction;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyFlowLayout;
import com.hr.ui.view.MyRecommendDialog;
import com.hr.ui.view.PieChartView;
import com.hr.ui.view.RoundImageView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qq.QQ;

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
    @BindView(R.id.iv_noNetError)
    ImageView ivNoNetError;
    @BindView(R.id.ll_netError)
    LinearLayout llNetError;
    @BindView(R.id.rl_positionContent)
    RelativeLayout rlPositionContent;
    @BindView(R.id.iv_positionPageSmile)
    ImageView ivPositionPageSmile;
    @BindView(R.id.iv_money)
    ImageView ivMoney;
    @BindView(R.id.fl_companyPageCompanyGoodness)
    MyFlowLayout flCompanyPageCompanyGoodness;
    @BindView(R.id.tv_position1)
    TextView tvPosition1;
    @BindView(R.id.view_lin3)
    View viewLin3;
    @BindView(R.id.view_lin4)
    View viewLin4;
    @BindView(R.id.iv_positionPageSharePosition)
    ImageView ivPositionPageSharePosition;
    @BindView(R.id.tv_positionPageShare)
    TextView tvPositionPageShare;
    @BindView(R.id.ll_positionPageShare)
    LinearLayout llPositionPageShare;
    @BindView(R.id.rl_companyGoodness)
    RelativeLayout rlCompanyGoodness;
    private AnimationDrawable drawable;
    private String jobId, companyId, tag;
    private int collection, apply, position;
    private int mWidth, mHeight;
    private SharedPreferencesUtils sUtils;
    private MyRecommendDialog dialog;
    private PositionBean.JobInfoBean jobInfoBean;
    private long currTime = 0;// 分享的点击时间
    private List<RecommendJobBean.JobsListBean> list;
    private List<String> companyGoodnessList = new ArrayList<>();

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

    public static void startAction(Activity activity, String jobId, int position, String tag) {
        Intent intent = new Intent(activity, PositionPageActivity.class);
        intent.putExtra("jobId", jobId);
        intent.putExtra("position", position);
        intent.putExtra("tag", tag);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
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
        //设置改页面的宽高占手机屏幕的比例
        View view = getWindow().getDecorView();
        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = mWidth - mWidth / 10;
        lp.height = mHeight - mHeight / 20 * 3;
        getWindowManager().updateViewLayout(view, lp);
        jobId = getIntent().getStringExtra("jobId");
        mPresenter.getPositionData(jobId, this);
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
                    ResumeJobOrderActivity.startAction(PositionPageActivity.this);
                }
            });
        }
        dialog.show();
    }

    @Override
    public void getPositionSuccess(PositionBean.JobInfoBean jobInfoBean) {
        MobclickAgent.onEvent(this, "v6_scan_position");
        rlPositionContent.setVisibility(View.VISIBLE);
        llNetError.setVisibility(View.GONE);
        initUI(jobInfoBean);
    }

    private void initUI(PositionBean.JobInfoBean jobInfoBean1) {
        jobInfoBean = jobInfoBean1;
        companyGoodnessList.clear();
        String companyGoodness=jobInfoBean.getWelfare_label();
        if (jobInfoBean != null && !"".equals(jobInfoBean) && !"[]".equals(jobInfoBean)) {
            ScanHistoryBean scanHistoryBean = new ScanHistoryBean();
            scanHistoryBean.setJobId(jobInfoBean.getJob_id());
            scanHistoryBean.setCompanyName(jobInfoBean.getEnterprise_name());
            scanHistoryBean.setDegree(jobInfoBean.getStudy());
            scanHistoryBean.setPlace(jobInfoBean.getWorkplace());
            scanHistoryBean.setJobName(jobInfoBean.getJob_name());
            scanHistoryBean.setExp(jobInfoBean.getWorkyear());
            scanHistoryBean.setSalary(jobInfoBean.getSalary());
            scanHistoryBean.setIs_expect(jobInfoBean.getIs_expire() + "");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            //flCompanyPageCompanyGoodness.removeAllViewsInLayout();
            if (companyGoodness != null&&!"".equals(companyGoodness)) {
                String[] s = companyGoodness.split(",");
                rlCompanyGoodness.setVisibility(View.VISIBLE);
                flCompanyPageCompanyGoodness.setVisibility(View.VISIBLE);
                for (int i = 0; i < s.length; i++) {
                    addCompanyGoodness(s[i]);
                }
            } else {
                rlCompanyGoodness.setVisibility(View.GONE);
                flCompanyPageCompanyGoodness.setVisibility(View.GONE);
            }
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
                ivPositionPageSmile.setVisibility(View.VISIBLE);
                tvPositionPageDeliverResume.setText(R.string.allReadyDeliver);
                llPositionPageDeliverResume.setBackgroundResource(R.drawable.tv_bg_right_gray);
            } else if (apply == 0) {
                tvPositionPageDeliverResume.setText(R.string.deliverResume);
            }
        }
    }

    private void addCompanyGoodness(String name) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 15;
        params.topMargin = 12;
        params.bottomMargin = 12;
        params.rightMargin = 15;
        LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.item_textposition, null, false);
        ll.setLayoutParams(params);
        TextView tv = ll.findViewById(R.id.tv_itemTextPosition);
        tv.setText(name);
        flCompanyPageCompanyGoodness.addView(ll);
    }

    @Override
    public void collectionPositionSuccess() {
        ToastUitl.showShort("收藏成功");
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
        MobclickAgent.onEvent(this, "v6_resume_deliver");
        if (apply == 1) {
            tvPositionPageDeliverResume.setText(R.string.deliverResume);
        } else if (apply == 0) {
            ivPositionPageSmile.setVisibility(View.VISIBLE);
            llPositionPageDeliverResume.setBackgroundResource(R.drawable.tv_bg_right_gray);
            tvPositionPageDeliverResume.setText(R.string.allReadyDeliver);
        }
        if (tag.equals(JobSearchResultActivity.TAG)) {
            JobSearchResultActivity.instance.notificationDataItem(position);
        }
        if (tag.equals(RecommendJobActivity.class.getSimpleName())) {
            EventBus.getDefault().post(new EventHomeBean(EventBusAction.RECOMMENDJOBACTIVITY_UPDATEJOBDELIVERSTATE, position));
        }
        if (tag.equals(HomeFragment.TAG)) {
            EventBus.getDefault().post(new EventHomeBean(EventBusAction.HOMEFRAGMENT_UPDATEJOBDELIVERSTATE, position));
        }
        if (sUtils.getIntValue(Constants.IS_RECOMMENDJOB, 0) < 3) {
            JobSearchBean jobSearchBean = new JobSearchBean();
            jobSearchBean.setIndustryId(jobInfoBean.getIndustry());
            jobSearchBean.setPositionId(jobInfoBean.getJob_id());
            jobSearchBean.setDegree(ResumeInfoIDToString.getDegreeNeedId(jobInfoBean.getStudy()));
            jobSearchBean.setPlaceId(jobInfoBean.getWork_area());
            mPresenter.getSearchList(jobSearchBean, 1, false);
        } else {
            ToastUitl.showShort("投递成功");
        }
    }

    @Override
    public void getResumeScoreSuccess(double s) {
        drawable.stop();
        pcvCulScore.setVisibility(View.VISIBLE);
        ivLoadCul.setVisibility(View.GONE);
        llPositionPageCulScore.setEnabled(false);
        s = Double.parseDouble(Utils.formatDouble(s, 2));
        int number = (int) (s * 100.0);
        //Log.i("当前的数字",number+"----");
        tvPositionPageCulScore.setText(number + "分");
        pcvCulScore.SetProgram(number);
    }

    @Override
    public void getPositionFaile() {
        rlPositionContent.setVisibility(View.GONE);
        llNetError.setVisibility(View.VISIBLE);
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
    public void retryCulScore() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                drawable.stop();
                pcvCulScore.setVisibility(View.GONE);
                ivLoadCul.setVisibility(View.GONE);
                ivCulScore.setVisibility(View.VISIBLE);
                tvPositionPageCulScore.setText(R.string.culculate);
                llPositionPageCulScore.setEnabled(true);
                ToastUitl.showShort(R.string.error_cul);
            }
        }, 2000);

    }

    @Override
    public void getSearchDataSuccess(List<RecommendJobBean.JobsListBean> jobsListBean) {
        list = new ArrayList<>();
        int num = 0;
        if (jobsListBean != null && !"".equals(jobsListBean) && jobsListBean.size() != 0) {
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
            // Log.i("到这里了","好的");
            if (list != null && list.size() != 0) {
                RecommendJobActivity.startAction(this, list);
                finish();
            } else {
                ToastUitl.showShort("投递成功");
            }
        } else {
            ToastUitl.showShort("投递成功");
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
        sUtils = new SharedPreferencesUtils(this);
        drawable = (AnimationDrawable) ivLoadCul.getDrawable();
        position = getIntent().getIntExtra("position", 1000);
        tag = getIntent().getStringExtra("tag");
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

    @OnClick({R.id.rl_positionPageCompanyInfo, R.id.iv_noNetError, R.id.ll_positionPageShare, R.id.ll_positionPageCulScore, R.id.iv_positionPageShare, R.id.iv_positionPageClose, R.id.ll_positionPageCollection, R.id.ll_positionPageDeliverResume})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_noNetError:
                mPresenter.getPositionData(jobId, this);
                break;
            case R.id.ll_positionPageCulScore:
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
            case R.id.ll_positionPageShare:
                doShare();
                break;
        }
    }

    private void doShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
//                oks.addHiddenPlatform(QQ.NAME);
        String text = "";
        String title="";
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
            title=jobInfoBean.getJob_name();
            text = "诚邀您来行业找工作平台投递“" + enterprise_name + "”发布的“" + jobnameString + "”职位";
            mobilUrl = EncryptUtils.getJobUrl(jobId, jobInfoBean.getIndustry());
        }
        //System.out.println("mobilUrl==" + mobilUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        final String finalText = text;
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if("WechatMoments".equals(platform.getName())||"QZone".equals(platform.getName())){
                    paramsToShare.setTitle(finalText);
                }
            }
        });
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(mobilUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setImagePath(EncryptUtils.getBenDiPhoto(this));
        oks.setUrl(mobilUrl);
        // 启动分享GUI,防止用户多次点击
        if (System.currentTimeMillis() - currTime > 500) {
            oks.show(this);
        }
        currTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
       // mPresenter.getPositionData(jobId, this);
    }

    @Override
    protected void onNetworkDisConnected() {

    }
}
