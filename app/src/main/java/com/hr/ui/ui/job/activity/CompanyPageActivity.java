package com.hr.ui.ui.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CompanyBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.job.contract.CompanyPageContract;
import com.hr.ui.ui.job.model.CompanyPageModel;
import com.hr.ui.ui.job.presenter.CompanyPagePresenter;
import com.hr.ui.ui.main.adapter.MyReleaseJobAdapter;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.ExpandableTextView;
import com.hr.ui.view.RoundImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by wdr on 2018/1/12.
 */

public class CompanyPageActivity extends BaseActivity<CompanyPagePresenter, CompanyPageModel> implements CompanyPageContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_backDefault)
    ImageView ivBackDefault;
    @BindView(R.id.tv_companyPageCompanyName)
    TextView tvCompanyPageCompanyName;
    @BindView(R.id.tv_companyPagePlace)
    TextView tvCompanyPagePlace;
    @BindView(R.id.tv_companyPageIndustry)
    TextView tvCompanyPageIndustry;
    @BindView(R.id.tv_companyPageCompanyType)
    TextView tvCompanyPageCompanyType;
    @BindView(R.id.tv_companyPageCompanyScale)
    TextView tvCompanyPageCompanyScale;
    @BindView(R.id.iv_companyPageCompanyImage)
    RoundImageView ivCompanyPageCompanyImage;
    @BindView(R.id.iv_companyLocationIcon)
    ImageView ivCompanyLocationIcon;
    @BindView(R.id.tv_companyPageAddress)
    TextView tvCompanyPageAddress;
    @BindView(R.id.id_source_textview)
    TextView idSourceTextview;
    @BindView(R.id.id_expand_textview)
    TextView idExpandTextview;
    @BindView(R.id.id_expand_imageview)
    ImageView idExpandImageview;
    @BindView(R.id.id_expand_rl)
    LinearLayout idExpandRl;
    @BindView(R.id.rv_companyPageInfo)
    RecyclerView rvCompanyPageInfo;
    @BindView(R.id.etv_companyPage)
    ExpandableTextView etvCompanyPage;
    @BindView(R.id.ll_companyLocation)
    LinearLayout llCompanyLocation;
    @BindView(R.id.tv_noDataPosition)
    TextView tvNoDataPosition;
    @BindView(R.id.iv_noNetError)
    ImageView ivNoNetError;
    @BindView(R.id.ll_netError)
    LinearLayout llNetError;
    @BindView(R.id.ll_companyContent)
    RelativeLayout llCompanyContent;
    private String companyId;
    private MyReleaseJobAdapter adapter;
    private List<RecommendJobBean.JobsListBean> jobsListBeanList;
    private CompanyBean.EnterpriseInfoBean enterpriseInfoBean;
    private long currTime = 0;// 分享的点击时间

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String companyId) {
        Intent intent = new Intent(activity, CompanyPageActivity.class);
        intent.putExtra("companyId", companyId);
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
    public void getCompanyDataSuccess(CompanyBean.EnterpriseInfoBean enterpriseInfoBean1) {
        MobclickAgent.onEvent(this,"v6_scan_company");
        if (enterpriseInfoBean1 != null && !"".equals(enterpriseInfoBean1)) {
            enterpriseInfoBean = enterpriseInfoBean1;
            tvCompanyPageAddress.setText(enterpriseInfoBean.getAddress());
            tvCompanyPageCompanyName.setText(enterpriseInfoBean.getEnterprise_name());
            tvCompanyPagePlace.setText(enterpriseInfoBean.getHome_area());
            tvCompanyPageIndustry.setText(enterpriseInfoBean.getIndustry_name());
            tvCompanyPageCompanyType.setText(enterpriseInfoBean.getCompany_type());
            tvCompanyPageCompanyScale.setText(enterpriseInfoBean.getStuff_munber());
            etvCompanyPage.setText(enterpriseInfoBean.getSynopsis(), true);
            if (enterpriseInfoBean.getEnt_logo() != null && !"".equals(enterpriseInfoBean.getEnt_logo())) {
                Utils.setImageResource(this, ivCompanyPageCompanyImage, Constants.IMAGE_BASEPATH2 + enterpriseInfoBean.getEnt_logo());
            } else {
                Utils.setImageResourceDefault(this, ivCompanyPageCompanyImage);
            }
        }
    }

    @Override
    public void getReleaseJobSuccess(List<RecommendJobBean.JobsListBean> jobInfoBeanList) {
        llCompanyContent.setVisibility(View.VISIBLE);
        llNetError.setVisibility(View.GONE);
        if (jobInfoBeanList != null && jobInfoBeanList.size() != 0) {
            jobsListBeanList = new ArrayList<>();
            jobsListBeanList.addAll(jobInfoBeanList);
            adapter.setJobsListBeanList(jobInfoBeanList);
            rvCompanyPageInfo.setAdapter(adapter);
            tvNoDataPosition.setVisibility(View.GONE);
            rvCompanyPageInfo.setVisibility(View.VISIBLE);
        } else {
            rvCompanyPageInfo.setVisibility(View.GONE);
            tvNoDataPosition.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getCompanyJobFaild() {
        llCompanyContent.setVisibility(View.GONE);
        llNetError.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_companypage;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        companyId = getIntent().getStringExtra("companyId");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setBackgroundColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.bg_color));
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.companyHome);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.getCompanyData(companyId);
        mPresenter.getReleaseJob(companyId);
        toolbarAdd.setVisibility(View.VISIBLE);
        toolbarAdd.setImageResource(R.mipmap.share);
        toolbarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShare();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCompanyPageInfo.setLayoutManager(manager);
        adapter = new MyReleaseJobAdapter();
        adapter.setClickCallBack(new MyReleaseJobAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                if (jobsListBeanList.get(pos).getIs_expire() == 1) {
                    ToastUitl.showShort(R.string.error_401);
                    return;
                } else {
                    PositionPageActivity.startAction(CompanyPageActivity.this, jobsListBeanList.get(pos).getJob_id());
                }
            }
        });
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
        if (!"".equals(enterpriseInfoBean.getEnterprise_name()) && enterpriseInfoBean.getEnterprise_name() != null) {
            enterprise_name = enterpriseInfoBean.getEnterprise_name();
        }

        if (!"".endsWith(enterpriseInfoBean.getEnterprise_id()) && enterpriseInfoBean.getEnterprise_id() != null) {
            enterpriseId = enterpriseInfoBean.getEnterprise_id();
        }
        if (enterpriseId != null && !"".equals(enterpriseId) && enterprise_name != null) {
            text = "我在行业找工作上看到了" + enterprise_name + "发布了招聘职位。";
            mobilUrl = EncryptUtils.getCompanyUrl(enterpriseId, enterpriseInfoBean.getIndustry());
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_companyLocation,R.id.iv_noNetError})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.ll_companyLocation:
                if(enterpriseInfoBean.getBaidu_map_lat()==null||enterpriseInfoBean.getBaidu_map_lon()==null||"".equals(enterpriseInfoBean.getBaidu_map_lat())||"".equals(enterpriseInfoBean.getBaidu_map_lon())){

                }else {
                    BaiDuMapActivity.startAction(this, enterpriseInfoBean.getBaidu_map_lon(), enterpriseInfoBean.getBaidu_map_lat());
                }
                break;
            case R.id.iv_noNetError:
                mPresenter.getCompanyData(companyId);
                mPresenter.getReleaseJob(companyId);
                break;
        }
    }
}
