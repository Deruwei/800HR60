package com.hr.ui.ui.main.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caption.netmonitorlibrary.netStateLib.NetUtils;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseFragment;
import com.hr.ui.bean.EventHomeBean;
import com.hr.ui.bean.EventType;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.HomeRecommendBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.activity.JobSerchActivity;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.adapter.MyRecommendJobAdapter;
import com.hr.ui.ui.main.contract.HomeFragmentContract;
import com.hr.ui.ui.main.modle.HomeFragmentModel;
import com.hr.ui.ui.main.presenter.HomeFragmentPresenter;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.utils.GlideImageLoader2;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.MyRecommendDialog;
import com.hr.ui.view.XRecyclerView;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wdr on 2017/11/22.
 */

public class HomeFragment extends BaseFragment<HomeFragmentPresenter, HomeFragmentModel> implements HomeFragmentContract.View {

    @BindView(R.id.rv_homeFragment)
    XRecyclerView rvHomeFragment;
    @BindView(R.id.iv_noContent)
    ImageView ivNoContent;
    @BindView(R.id.tv_noData)
    TextView tvNoData;
    @BindView(R.id.iv_noDataSearchIcon)
    ImageView ivNoDataSearchIcon;
    @BindView(R.id.rl_emptyView)
    RelativeLayout rlEmptyView;
    Unbinder unbinder;
    @BindView(R.id.iv_ResumePersonPhoto)
    CircleImageView ivResumePersonPhoto;
    @BindView(R.id.iv_mainSearch)
    ImageView ivMainSearch;
    @BindView(R.id.rl_mainSearch)
    RelativeLayout rlMainSearch;
    @BindView(R.id.rl_fragmentTitle)
    RelativeLayout rlFragmentTitle;
    @BindView(R.id.iv_noDataSearch)
    RelativeLayout ivNoDataSearch;
    @BindView(R.id.cl_homeFragment)
    FrameLayout clHomeFragment;
    @BindView(R.id.iv_noNetError)
    ImageView ivNoNetError;
    @BindView(R.id.ll_netError)
    LinearLayout llNetError;
    @BindView(R.id.rl_homeBg)
    RelativeLayout rlHomeBg;
    @BindView(R.id.home_cv)
    CardView homeCv;
    private int page = 1;
    public static HomeFragment instance;
    private MyRecommendDialog dialog;
    private String industryName;
    private MyDialog dialogMessage;
    private Banner banner;
    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    /**
     * 1 判断是否有第三方公司推荐职位的数据
     * 2.判断是否第三方公司推荐职位的数据满足20条
     * 3.判断是否有公司职位推荐的数据
     */
    private boolean isHaveThirdRecommendData, isThirdRecommendDataFill, isHaveRecommendData;
    private MyRecommendJobAdapter jobAdapter;
    //第三方公司推荐职位接口获取到的数据
    private List<HomeRecommendBean.JobsListBean> recommendList = new ArrayList<>();
    //公司职位推荐接口获取到的数据
    private List<RecommendJobBean.JobsListBean> recommendJobList = new ArrayList<>();
    private PopupWindow popupWindowCalculateScore;
    private SharedPreferencesUtils sUtils;
    private String personImage, jobId, jobName;
    private int mProgress, position;
    public static String TAG = HomeFragment.class.getSimpleName();
    private View header;

    public static HomeFragment newInstance(String s) {
        HomeFragment navigationFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        navigationFragment.setArguments(bundle);
        instance = navigationFragment;
        return navigationFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_homefragment;
    }

    public void setImage() {
        personImage = sUtils.getStringValue(Constants.PERSONIMAGE, "");
        if (!"".equals(personImage) && personImage != null) {
            /* Glide.with(this).load(Constants.IMAGE_BASEPATH + personImage).centerCrop().into(ivResumePersonPhoto);*/
            Glide.with(this).load(Constants.IMAGE_BASEPATH + personImage).fitCenter().into(ivResumePersonPhoto);
        } else {
            ivResumePersonPhoto.setImageResource(R.mipmap.persondefault);
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        sUtils = new SharedPreferencesUtils(getActivity());
        refresh(true);
        mPresenter.getNotice("98", "825");
        jobAdapter = new MyRecommendJobAdapter();
        setImage();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        rlHomeBg.setBackgroundResource(R.color.bg_homeTitle);
        tvNoData.setText("暂无合适职位推荐，点我刷新");
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvHomeFragment.setLayoutManager(linearLayoutManager);
        rvHomeFragment.setRefreshProgressStyle(ProgressStyle.BallPulse);
        rvHomeFragment.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        rvHomeFragment.setScrollAlphaChangeListener(new XRecyclerView.ScrollAlphaChangeListener() {
            @Override
            public void onAlphaChange(int alpha) {
                rlHomeBg.getBackground().setAlpha( alpha);
            }

            @Override
            public int setLimitHeight() {
                return header.getMeasuredHeight()-rlFragmentTitle.getMeasuredHeight();
            }
        });
        rvHomeFragment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        MobclickAgent.onEvent(HRApplication.getAppContext(), "v6_fresh_home");
                        refresh(false);
                        jobAdapter.notifyDataSetChanged();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (isThirdRecommendDataFill == true) {
                            rvHomeFragment.setLoadingMoreEnabled(false);
                        } else {
                            page++;
                            mPresenter.getRecommendJob(page, 20, false);
                        }
                        jobAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
      /*  listData = new ArrayList<String>();
        for(int i = 0; i < 15 ;i++){
            listData.add("item" + i);
        }
        mAdapter = new MyAdapter(listData);

        rvMsg.setAdapter(mAdapter);
        rvMsg.refresh();*/
    }

    public void refresh(final boolean isrefresh) {
        page = 1;
        //获取第三方推荐职位
        mPresenter.getRecommendJobInfo(20, false);
        //获取公司的推荐职位
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getRecommendJob(page, 20, isrefresh);
            }
        }, 2000);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvenTypeMethod(EventType eventType) {
        switch (eventType.getType()) {
            case 0:
                rlFragmentTitle.setVisibility(View.GONE);
                break;
            case 1:
                rlFragmentTitle.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (dialog != null) {
            dialog.dismiss();
        }
        if (dialogMessage != null) {
            dialogMessage.dismiss();
        }
        instance = null;
        //结束轮播
        banner.stopAutoPlay();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        refresh(false);
    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void getRecommendJobSuccess(List<HomeRecommendBean.JobsListBean> jobsBeanList) {
        //Log.i("现在的数据",jobsBeanList.toString());
        if (jobsBeanList != null && !"".equals(jobsBeanList) && jobsBeanList.size() != 0) {
            recommendList.clear();
            //Log.i("你好",jobsBeanList.size()+"");
            recommendList.addAll(jobsBeanList);
            isHaveThirdRecommendData = true;
            if (recommendList.size() >= 20) {
                isThirdRecommendDataFill = true;
            } else {
                isThirdRecommendDataFill = false;
            }
        } else {
            isHaveThirdRecommendData = false;
            isThirdRecommendDataFill = false;
        }
    }

    @Override
    public void getResumeScoreSuccess(double score) {
        int i = (int) (score * 100);
        /*  initCalculateScore(i);*//**/
    }

    @Override
    public void getRecommendJobError() {
        if (rlEmptyView != null) {
            rlEmptyView.setVisibility(View.GONE);
        }
        if (rvHomeFragment != null) {
            rvHomeFragment.setVisibility(View.GONE);
        }
        if (rvHomeFragment != null) {
            llNetError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getRecommendJobSuccess2(List<RecommendJobBean.JobsListBean> jobsListBeanList) {
        //Log.i("推荐职位的信息",jobsListBeanList.toString());
        if (jobsListBeanList != null && jobsListBeanList.size() != 0 && jobsListBeanList.size() != 0) {
            isHaveRecommendData = true;
            if (page == 1) {
                recommendJobList.clear();
            }
            recommendJobList.addAll(jobsListBeanList);
        } else {
            if (page == 1) {
                isHaveRecommendData = false;
            } else {
                rvHomeFragment.setNoMore(true);
                rvHomeFragment.setLoadingMoreEnabled(false);
            }
        }
        setAdapter();
        jobAdapter.setClickCallBack(new MyRecommendJobAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                if (isThirdRecommendDataFill == false) {
                    if (pos < recommendList.size()) {
                        PositionPageActivity.startAction(getActivity(), recommendList.get(pos).getJob_id(), pos, TAG);
                    } else {
                        PositionPageActivity.startAction(getActivity(), recommendJobList.get(pos - recommendList.size()).getJob_id(), pos, TAG);
                    }
                } else {
                    PositionPageActivity.startAction(getActivity(), recommendList.get(pos).getJob_id(), pos, TAG);
                }
            }
        });
        jobAdapter.setOnFastDeliverListener(new MyRecommendJobAdapter.OnFastDeliverListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFastDeliverListener(int pos) {
                position = pos;
                dialogMessage = new MyDialog(getActivity(), 2);
                if (isThirdRecommendDataFill == false) {
                    if (pos < recommendList.size()) {
                        industryName = recommendList.get(pos).getIndustry_name();
                        jobId = recommendList.get(pos).getJob_id();
                        jobName = recommendList.get(pos).getJob_name();
                    } else {
                        industryName = recommendJobList.get(pos - recommendList.size()).getIndustry_name();
                        jobId = recommendJobList.get(pos - recommendList.size()).getJob_id();
                        jobName = recommendJobList.get(pos - recommendList.size()).getJob_name();
                    }
                } else {
                    industryName = recommendList.get(pos).getIndustry_name();
                    jobId = recommendList.get(pos).getJob_id();
                    jobName = recommendList.get(pos).getJob_name();
                }
                String s = getString(R.string.deliverOrNot1) + "“ " + jobName + " ”" + getString(R.string.deliverOrNot2);
                SpannableStringBuilder style = new SpannableStringBuilder(s);
                style.setSpan(new ForegroundColorSpan(Color.RED), 6, 6 + jobName.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置textview的背景颜色
                //设置指定位置文字的颜色
                dialogMessage.setMessage(style);
                dialogMessage.setNoOnclickListener("否", new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialogMessage.dismiss();
                    }
                });
                dialogMessage.setYesOnclickListener("是", new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        mPresenter.deliverPosition(jobId);
                        dialogMessage.dismiss();
                    }
                });
                dialogMessage.show();
            }
        });
    }

    private void setAdapter() {
        if (isHaveRecommendData == true || isHaveThirdRecommendData == true) {
            rlEmptyView.setVisibility(View.GONE);
            rvHomeFragment.setVisibility(View.VISIBLE);
            llNetError.setVisibility(View.GONE);
            if (isThirdRecommendDataFill) {
                setDate1();
            } else {
                setData2();
            }
        } else {
            rlEmptyView.setVisibility(View.VISIBLE);
            rvHomeFragment.setVisibility(View.GONE);
            llNetError.setVisibility(View.GONE);
        }
    }

    //第三方公司推荐职位满足20个
    private void setDate1() {
        jobAdapter = new MyRecommendJobAdapter();
        jobAdapter.setJobsListBeanList2(recommendList);
        rvHomeFragment.setAdapter(jobAdapter);
        rvHomeFragment.refreshComplete();
        rlFragmentTitle.setVisibility(View.VISIBLE);
        rvHomeFragment.setLoadingMoreEnabled(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMethod(EventHomeBean eventHomeBean) {
        switch (eventHomeBean.getType()) {
            case 0:
                notificationDataItem(eventHomeBean.getPosition());
                break;
            case 1:
                setImage();
                break;
        }
    }

    public void notificationDataItem(int pos) {
        position = pos;
        if (isThirdRecommendDataFill == false) {
            if (position < recommendList.size()) {
                recommendList.get(position).setIs_apply(1);
            } else {
                recommendJobList.get(position - recommendList.size()).setIs_apply(1);
            }
        } else {
            recommendList.get(position).setIs_apply(1);
        }
        jobAdapter.notifyDataSetChanged();

    }

    /**
     * 第三方公司推荐职位有数据但是不满足20个，拼接上公司职位推荐数据
     */
    private void setData2() {
        if (page == 1) {
            jobAdapter = new MyRecommendJobAdapter(3);
            jobAdapter.setJobsListBeanList(recommendJobList);
            jobAdapter.setJobsListBeanList2(recommendList);
            rvHomeFragment.setAdapter(jobAdapter);
            rvHomeFragment.refreshComplete();
            rlFragmentTitle.setVisibility(View.VISIBLE);
        } else {
            rvHomeFragment.loadMoreComplete();
            jobAdapter.notifyDataSetChanged();
        }
        if (recommendJobList.size() < 20) {
            rvHomeFragment.setLoadingMoreEnabled(false);
        }
    }

    @Override
    public void cantGetData() {
        /*mPresenter.getRecommendJob(page,20,true*/
    }

    @Override
    public void deliverPositionSuccess() {
        MobclickAgent.onEvent(getActivity(), "v6_resume_deliver");
        ToastUitl.showShort("投递成功");
        if (isThirdRecommendDataFill == false) {
            if (position < recommendList.size()) {
                recommendList.get(position).setIs_apply(1);
            } else {
                recommendJobList.get(position - recommendList.size()).setIs_apply(1);
            }
        } else {
            recommendList.get(position).setIs_apply(1);
        }
        jobAdapter.notifyDataSetChanged();
    }

    @Override
    public void goToCompleteResume(int errorCode) {
        if (errorCode == 413 || errorCode == 417) {
            //ToastUitl.showShort(errorCode+"");
            setPopupwindow(2);
        } else {
            //ToastUitl.showShort(errorCode+"");
            setPopupwindow(1);
        }
    }

    @Override
    public void getNoticeSuccess(List<FindBean.ListBean> listBean) {
        if(listBean!=null&&listBean.size()!=0){
            for(int i=0;i<listBean.size();i++){
                images.add(listBean.get(i).getPic_path());
                titles.add(listBean.get(i).getTitle());
                header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header, null);
                banner = header.findViewById(R.id.banner);
                //设置banner样式
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader2());
                //设置图片集合
                banner.setImages(images);
                //设置banner动画效果
                banner.setBannerAnimation(Transformer.Tablet);
                //设置标题集合（当banner样式有显示title时）
                banner.setBannerTitles(titles);
                //设置自动轮播，默认为true
                banner.isAutoPlay(true);
                //设置轮播时间
                banner.setDelayTime(3000);
                //设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                rvHomeFragment.addHeaderView(header);
            }
        }
    }

    /**
     * 简历不符合跳出提示框
     *
     * @param type
     */
    private void setPopupwindow(int type) {
        dialog = new MyRecommendDialog(getActivity());
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
                    MainActivity.instance.rbResume1.setChecked(true);
                    ResumeFragment.instance.refresh();
                }
            });
        } else {
            dialog.setTitle(getString(R.string.noIndustry1) + industryName + "行业" + getString(R.string.noIndustry2));
            dialog.setMessage(getString(R.string.noJobOrderContent1) + industryName + "行业" + getString(R.string.noJobOrderContent2));
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
                    ResumeJobOrderActivity.startAction(getActivity());
                }
            });
        }
        dialog.show();
    }
   /* private void initCalculateScore(final int i) {
        View viewCalculateScore = getLayoutInflater().inflate(R.layout.layout_calculatescore, null);
        popupWindowCalculateScore = new PopupWindow(viewCalculateScore, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        PieChartView pcv = viewCalculateScore.findViewById(R.id.pcv_calculateScore);
        TextView tvScore = viewCalculateScore.findViewById(R.id.tv_calculateSore);
        final TextView tv_culculateSoreNum = viewCalculateScore.findViewById(R.id.tv_calculateScoreNum);
        Button btnOK = viewCalculateScore.findViewById(R.id.btn_calculateScoreOK);
        pcv.SetProgram(i);
        mProgress = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress < i) {
                    mProgress++;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_culculateSoreNum.setText(mProgress + "分");
                        }
                    });
                    try {
                        Thread.sleep(22);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        tvScore.setText(i + "");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowCalculateScore.dismiss();
            }
        });
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindowCalculateScore.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        popupWindowCalculateScore.setFocusable(true);
        popupWindowCalculateScore.setOutsideTouchable(false);
        popupWindowCalculateScore.setAnimationStyle(R.style.style_pop_animation);
        popupWindowCalculateScore.showAtLocation(clHomeFragment, Gravity.CENTER, 0, 0);
    }*/

    @OnClick({R.id.iv_ResumePersonPhoto, R.id.ll_netError, R.id.iv_noContent, R.id.iv_noDataSearch, R.id.rl_mainSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_netError:
                /*mPresenter.getRecommendJobInfo(20,true);*/
                refresh(true);
                break;
            case R.id.iv_ResumePersonPhoto:
                MainActivity.instance.toggle();
                break;
            case R.id.iv_noDataSearch:
                JobSerchActivity.startAction(getActivity());
                break;
            case R.id.iv_noContent:
                /*  mPresenter.getRecommendJobInfo( 20, true);*//**/
                refresh(true);
                break;
            case R.id.rl_mainSearch:
                JobSerchActivity.startAction(getActivity());
                break;
        }
    }

}
