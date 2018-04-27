package com.hr.ui.ui.main.adapter;

import android.support.annotation.BinderThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.HomeRecommendBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.message.activity.WebActivity;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.PieChartView;
import com.hr.ui.view.RoundImageView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyRecommendJobAdapter extends RecyclerView.Adapter<MyRecommendJobAdapter.ViewHolder> {

    private int type;//type：1识别为搜索结果的item  没有传递的话就是主页的item
    private List<RecommendJobBean.JobsListBean> jobsListBeanList;
    private OnCalCulateScoreClickListener onCalCulateScoreClickListener;
    private List<HomeRecommendBean.JobsListBean> jobsListBeanList2;
    private List<FindBean.ListBean> adsList;
    private OnFastDeliverListener onFastDeliverListener;
    private OnCheckListener onCheckListener;
    private boolean isCheck, isHasAd;
    private int limitLength = 14,n;
    private ViewHolderNormal viewHolderNormal;
    private ViewHolderAd viewHolderAd;
    private OnClickAdsListener onClickAdsListener;

    public void setOnClickAdsListener(OnClickAdsListener onClickAdsListener) {
        this.onClickAdsListener = onClickAdsListener;
    }

    public void setAdsList(List<FindBean.ListBean> adsList, int n) {
        isHasAd = true;
        this.n=n;
        this.adsList = adsList;
    }

    public void setOnFastDeliverListener(OnFastDeliverListener onFastDeliverListener) {
        this.onFastDeliverListener = onFastDeliverListener;
    }

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public void setOnCheckListener(OnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    public void setOnCalCulateScoreClickListener(OnCalCulateScoreClickListener onCalCulateScoreClickListener) {
        this.onCalCulateScoreClickListener = onCalCulateScoreClickListener;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        if (isCheck) {
            limitLength = 10;
        } else {
            limitLength = 14;
        }
    }

    public interface OnFastDeliverListener {
        void onFastDeliverListener(int pos);
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    public interface OnCalCulateScoreClickListener {
        void onCalulateScore(int pos);
    }
    public interface  OnClickAdsListener{
        void onClick(int pos);
    }
    public interface OnCheckListener {
        void onCheckListener(int pos, boolean b);
    }

    private ItemClickCallBack clickCallBack;

    public void setJobsListBeanList2(List<HomeRecommendBean.JobsListBean> jobsListBeanList2) {
        this.jobsListBeanList2 = jobsListBeanList2;
    }


    public void setJobsListBeanList(List<RecommendJobBean.JobsListBean> jobsListBeanList) {
        this.jobsListBeanList = jobsListBeanList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_homefragment_item, viewGroup, false);
            return new ViewHolderNormal(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommendjob_ads, viewGroup, false);
            return new ViewHolderAd(view);
        }
    }

    public MyRecommendJobAdapter() {
    }

    public MyRecommendJobAdapter(int type) {
        this.type = type;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (type != 3) {
            if (type == 1) {
                if (viewHolder instanceof ViewHolderNormal) {
                    ((ViewHolderNormal) viewHolder).llHomeItemTop.setVisibility(View.GONE);
                    if(isHasAd) {
                        if (position >= 3) {
                            position = position - 1;
                        }
                    }
                    if (jobsListBeanList.get(position).isTop() == true) {
                        ((ViewHolderNormal) viewHolder).ivTopJob.setVisibility(View.VISIBLE);
                    } else {
                        ((ViewHolderNormal) viewHolder).ivTopJob.setVisibility(View.GONE);
                    }
                    if (isCheck == true) {
                        ((ViewHolderNormal) viewHolder).rlHomeFragmentItemLeft.setVisibility(View.VISIBLE);
                    } else {
                        ((ViewHolderNormal) viewHolder).rlHomeFragmentItemLeft.setVisibility(View.GONE);
                    }
                    ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyType.setMaxEms(6);
                    if (jobsListBeanList.get(position).getIs_apply() == 1) {
                        ((ViewHolderNormal) viewHolder).rlHomeFragmentItemLeft.setEnabled(false);
                        ((ViewHolderNormal) viewHolder).tvSearchResultAlreadyDeliver.setVisibility(View.VISIBLE);
                        ((ViewHolderNormal) viewHolder).rbCheck.setVisibility(View.GONE);
                        ((ViewHolderNormal) viewHolder).ivCantCheck.setVisibility(View.VISIBLE);
                    } else {
                        ((ViewHolderNormal) viewHolder).rlHomeFragmentItemLeft.setEnabled(true);
                        ((ViewHolderNormal) viewHolder).tvSearchResultAlreadyDeliver.setVisibility(View.GONE);
                        ((ViewHolderNormal) viewHolder).rbCheck.setVisibility(View.VISIBLE);
                        ((ViewHolderNormal) viewHolder).ivCantCheck.setVisibility(View.GONE);
                    }
                    if (jobsListBeanList.get(position).isCheck()) {
                        ((ViewHolderNormal) viewHolder).rbCheck.setChecked(true);
                    } else {
                        ((ViewHolderNormal) viewHolder).rbCheck.setChecked(false);
                    }

                    ((ViewHolderNormal) viewHolder).tvRecommendJobAddress.setText(jobsListBeanList.get(position).getWorkplace());
                    ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyName.setMaxEms(limitLength);
                    ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyName.setText(jobsListBeanList.get(position).getEnterprise_name());
                    ((ViewHolderNormal) viewHolder).tvRecommendJobDegree.setText(jobsListBeanList.get(position).getStudy());
                    ((ViewHolderNormal) viewHolder).tvRecommendJobIndustry.setText(jobsListBeanList.get(position).getIndustry_name());
                    if (jobsListBeanList.get(position).getEnt_logo() != null && !"".equals(jobsListBeanList.get(position).getEnt_logo())) {
                        Utils.setImageResource(HRApplication.getAppContext(), ((ViewHolderNormal) viewHolder).ivRecommendJobCompanyIcon, Constants.IMAGE_BASEPATH2 + jobsListBeanList.get(position).getEnt_logo());
                    } else {
                        Utils.setImageResourceDefault(HRApplication.getAppContext(), ((ViewHolderNormal) viewHolder).ivRecommendJobCompanyIcon);
                    }
                    ((ViewHolderNormal) viewHolder).tvRecommendJobName.setText(jobsListBeanList.get(position).getJob_name());
                    ((ViewHolderNormal) viewHolder).tvRecommendJobSalary.setText(Utils.getSalary(jobsListBeanList.get(position).getSalary()));
                    String day = Utils.getDateMonthAndDay(jobsListBeanList.get(position).getIssue_date());
                    if (jobsListBeanList.get(position).getIs_shoufa() == 1) {
                        ((ViewHolderNormal) viewHolder).ivRecommendJobNowPublic.setVisibility(View.VISIBLE);
                        ((ViewHolderNormal) viewHolder).tvRecommendJobTime.setVisibility(View.GONE);
                    } else {
                        ((ViewHolderNormal) viewHolder).ivRecommendJobNowPublic.setVisibility(View.GONE);
                        ((ViewHolderNormal) viewHolder).tvRecommendJobTime.setVisibility(View.VISIBLE);
                        ((ViewHolderNormal) viewHolder).tvRecommendJobTime.setText(day);
                    }
                    ((ViewHolderNormal) viewHolder).tvRecommendPersonNum.setText(jobsListBeanList.get(position).getStuff_munber());
                    ((ViewHolderNormal) viewHolder).tvRecommendJobWorkYear.setText(jobsListBeanList.get(position).getWorkyear());
                    ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyType.setText(jobsListBeanList.get(position).getCompany_type());
                    if (onCheckListener != null) {
                        final int finalPosition = position;
                        ((ViewHolderNormal) viewHolder).rbCheck.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (jobsListBeanList.get(finalPosition).isCheck()) {
                                    onCheckListener.onCheckListener(finalPosition, false);
                                } else {
                                    onCheckListener.onCheckListener(finalPosition, true);
                                }
                            }
                        });
                        ((ViewHolderNormal) viewHolder).rlHomeFragmentItemLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (jobsListBeanList.get(finalPosition).isCheck()) {
                                    onCheckListener.onCheckListener(finalPosition, false);
                                } else {
                                    onCheckListener.onCheckListener(finalPosition, true);
                                }
                            }
                        });
                    }
                } else if (viewHolder instanceof ViewHolderAd) {
                    Glide.with(HRApplication.getAppContext()).load(adsList.get(n).getPic_path()).into(((ViewHolderAd) viewHolder).ivRecommendJobAdImage);
                    ((ViewHolderAd) viewHolder).tvRecommendJobAdTitle.setText(adsList.get(n).getTitle());
                    ((ViewHolderAd) viewHolder).tvRecommendJobAdDes.setText(adsList.get(n).getAd_txt());
                    if(onClickAdsListener!=null) {
                        ((ViewHolderAd) viewHolder).rlRecommendJobAd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickAdsListener.onClick(n);
                            }
                        });
                    }
                }
            } else {
                if (position == 0) {
                    ((ViewHolderNormal) viewHolder).llHomeItemTop.setVisibility(View.VISIBLE);
                    ((ViewHolderNormal) viewHolder).tvItemTitle.setText(R.string.recommendJob1);
                } else {
                    ((ViewHolderNormal) viewHolder).llHomeItemTop.setVisibility(View.GONE);
                }
                if (jobsListBeanList2.get(position).getIs_apply() == 1) {
                    ((ViewHolderNormal) viewHolder).tvSearchResultFastDeliver.setVisibility(View.GONE);
                    ((ViewHolderNormal) viewHolder).tvSearchResultAlreadyDeliver.setVisibility(View.VISIBLE);
                } else {
                    ((ViewHolderNormal) viewHolder).tvSearchResultFastDeliver.setVisibility(View.VISIBLE);
                    ((ViewHolderNormal) viewHolder).tvSearchResultAlreadyDeliver.setVisibility(View.GONE);
                }
                //Log.i("当前的数据",position+"----"+jobsListBeanList2.get(position).getIs_apply());
                ((ViewHolderNormal) viewHolder).tvRecommendJobAddress.setText(jobsListBeanList2.get(position).getWorkplace());
                ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyName.setText(jobsListBeanList2.get(position).getEnterprise_name());
                ((ViewHolderNormal) viewHolder).tvRecommendJobDegree.setText(jobsListBeanList2.get(position).getStudy());
                ((ViewHolderNormal) viewHolder).tvRecommendJobIndustry.setText(jobsListBeanList2.get(position).getIndustry_name());
                if (jobsListBeanList2.get(position).getEnt_logo() != null && !"".equals(jobsListBeanList2.get(position).getEnt_logo())) {
                    Utils.setImageResource(HRApplication.getAppContext(), ((ViewHolderNormal) viewHolder).ivRecommendJobCompanyIcon, Constants.IMAGE_BASEPATH2 + jobsListBeanList2.get(position).getEnt_logo());
                } else {
                    Utils.setImageResourceDefault(HRApplication.getAppContext(), ((ViewHolderNormal) viewHolder).ivRecommendJobCompanyIcon);
                }
                ((ViewHolderNormal) viewHolder).tvRecommendJobName.setText(jobsListBeanList2.get(position).getJob_name());
                ((ViewHolderNormal) viewHolder).tvRecommendJobSalary.setText(Utils.getSalary(jobsListBeanList2.get(position).getSalary()));
                String day = Utils.getDateMonthAndDay(jobsListBeanList2.get(position).getIssue_date());
                if ("".equals(day) || day == null) {
                    day = Utils.getDateMonthAndDay(Utils.timeStamp2Date(jobsListBeanList2.get(position).getIssue_date(), "yyyy-MM-dd"));
                }
                ((ViewHolderNormal) viewHolder).tvRecommendJobTime.setText(day);
                ((ViewHolderNormal) viewHolder).tvRecommendPersonNum.setText(jobsListBeanList2.get(position).getNumber());
                ((ViewHolderNormal) viewHolder).tvRecommendJobWorkYear.setText(jobsListBeanList2.get(position).getWorkyear());
                ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyType.setText(jobsListBeanList2.get(position).getCompany_type());
            }
        } else {
            if (position < jobsListBeanList2.size()) {
                if (position == 0) {
                    ((ViewHolderNormal) viewHolder).llHomeItemTop.setVisibility(View.VISIBLE);
                    ((ViewHolderNormal) viewHolder).tvItemTitle.setText(R.string.recommendJob1);
                } else {
                    ((ViewHolderNormal) viewHolder).llHomeItemTop.setVisibility(View.GONE);
                }
                if (position == jobsListBeanList2.size() - 1) {
                    ((ViewHolderNormal) viewHolder).viewLineJob.setVisibility(View.GONE);
                } else {
                    ((ViewHolderNormal) viewHolder).viewLineJob.setVisibility(View.VISIBLE);
                }
                if (jobsListBeanList2.get(position).getIs_apply() == 1) {
                    ((ViewHolderNormal) viewHolder).tvSearchResultFastDeliver.setVisibility(View.GONE);
                    ((ViewHolderNormal) viewHolder).tvSearchResultAlreadyDeliver.setVisibility(View.VISIBLE);
                } else {
                    ((ViewHolderNormal) viewHolder).tvSearchResultFastDeliver.setVisibility(View.VISIBLE);
                    ((ViewHolderNormal) viewHolder).tvSearchResultAlreadyDeliver.setVisibility(View.GONE);
                }
                //Log.i("当前的数据",position+"----"+jobsListBeanList2.get(position).getIs_apply());
                ((ViewHolderNormal) viewHolder).tvRecommendJobAddress.setText(jobsListBeanList2.get(position).getWorkplace());
                ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyName.setText(jobsListBeanList2.get(position).getEnterprise_name());
                ((ViewHolderNormal) viewHolder).tvRecommendJobDegree.setText(jobsListBeanList2.get(position).getStudy());
                ((ViewHolderNormal) viewHolder).tvRecommendJobIndustry.setText(jobsListBeanList2.get(position).getIndustry_name());
                if (jobsListBeanList2.get(position).getEnt_logo() != null && !"".equals(jobsListBeanList2.get(position).getEnt_logo())) {
                    Utils.setImageResource(HRApplication.getAppContext(), ((ViewHolderNormal) viewHolder).ivRecommendJobCompanyIcon, Constants.IMAGE_BASEPATH2 + jobsListBeanList2.get(position).getEnt_logo());
                } else {
                    Utils.setImageResourceDefault(HRApplication.getAppContext(), ((ViewHolderNormal) viewHolder).ivRecommendJobCompanyIcon);
                }
                ((ViewHolderNormal) viewHolder).tvRecommendJobName.setText(jobsListBeanList2.get(position).getJob_name());
                ((ViewHolderNormal) viewHolder).tvRecommendJobSalary.setText(Utils.getSalary(jobsListBeanList2.get(position).getSalary()));
                String day = Utils.getDateMonthAndDay(jobsListBeanList2.get(position).getIssue_date());
                if ("".equals(day) || day == null) {
                    day = Utils.getDateMonthAndDay(Utils.timeStamp2Date(jobsListBeanList2.get(position).getIssue_date(), "yyyy-MM-dd"));
                }
                ((ViewHolderNormal) viewHolder).tvRecommendJobTime.setText(day);
                ((ViewHolderNormal) viewHolder).tvRecommendPersonNum.setText(jobsListBeanList2.get(position).getNumber());
                ((ViewHolderNormal) viewHolder).tvRecommendJobWorkYear.setText(jobsListBeanList2.get(position).getWorkyear());
                ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyType.setText(jobsListBeanList2.get(position).getCompany_type());
                //viewHolder.tvRecommendJobCompanyType.setText(jobsListBeanList2.get(position).getCompany_type());
                int num = (int) (Double.parseDouble(jobsListBeanList2.get(position).getMatch_value()) * 100.0);
                ((ViewHolderNormal) viewHolder).pcvNum.SetProgram(num);
            } else {
                //Log.i("经过这里2","------");
                int i = jobsListBeanList2.size();
                if (position == jobsListBeanList2.size()) {
                    ((ViewHolderNormal) viewHolder).llHomeItemTop.setVisibility(View.VISIBLE);
                    ((ViewHolderNormal) viewHolder).tvItemTitle.setText(R.string.recommendJob2);
                } else {
                    ((ViewHolderNormal) viewHolder).llHomeItemTop.setVisibility(View.GONE);
                }
                if (jobsListBeanList.get(position - i).getIs_apply() == 1) {
                    ((ViewHolderNormal) viewHolder).tvSearchResultFastDeliver.setVisibility(View.GONE);
                    ((ViewHolderNormal) viewHolder).tvSearchResultAlreadyDeliver.setVisibility(View.VISIBLE);
                } else {
                    ((ViewHolderNormal) viewHolder).tvSearchResultFastDeliver.setVisibility(View.VISIBLE);
                    ((ViewHolderNormal) viewHolder).tvSearchResultAlreadyDeliver.setVisibility(View.GONE);
                }
                ((ViewHolderNormal) viewHolder).viewLineJob.setVisibility(View.VISIBLE);
                ((ViewHolderNormal) viewHolder).tvRecommendJobAddress.setText(jobsListBeanList.get(position - i).getWorkplace());
                ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyName.setText(jobsListBeanList.get(position - i).getEnterprise_name());
                ((ViewHolderNormal) viewHolder).tvRecommendJobDegree.setText(jobsListBeanList.get(position - i).getStudy());
                ((ViewHolderNormal) viewHolder).tvRecommendJobIndustry.setText(jobsListBeanList.get(position - i).getIndustry_name());
                if (jobsListBeanList.get(position - i).getEnt_logo() != null && !"".equals(jobsListBeanList.get(position - i).getEnt_logo())) {
                    Utils.setImageResource(HRApplication.getAppContext(), ((ViewHolderNormal) viewHolder).ivRecommendJobCompanyIcon, Constants.IMAGE_BASEPATH2 + jobsListBeanList.get(position - i).getEnt_logo());
                } else {
                    Utils.setImageResourceDefault(HRApplication.getAppContext(), ((ViewHolderNormal) viewHolder).ivRecommendJobCompanyIcon);
                }
                ((ViewHolderNormal) viewHolder).tvRecommendJobName.setText(jobsListBeanList.get(position - i).getJob_name());
                ((ViewHolderNormal) viewHolder).tvRecommendJobSalary.setText(Utils.getSalary(jobsListBeanList.get(position - i).getSalary()));
                ((ViewHolderNormal) viewHolder).tvRecommendJobTime.setText(Utils.getDateMonthAndDay(jobsListBeanList.get(position - i).getIssue_date()));
                ((ViewHolderNormal) viewHolder).tvRecommendPersonNum.setText(jobsListBeanList.get(position - i).getNumber());
                ((ViewHolderNormal) viewHolder).tvRecommendJobWorkYear.setText(jobsListBeanList.get(position - i).getWorkyear());
                ((ViewHolderNormal) viewHolder).tvRecommendJobCompanyType.setText(jobsListBeanList.get(position - i).getCompany_type());
            }
        }
        if (viewHolder instanceof ViewHolderNormal) {
            if (clickCallBack != null) {
                final int finalPosition1 = position;
                ((ViewHolderNormal) viewHolder).flContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCallBack.onItemClick(finalPosition1);
                    }
                });
            }
            if (onCalCulateScoreClickListener != null) {
                final int finalPosition2 = position;
                ((ViewHolderNormal) viewHolder).pcvNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCalCulateScoreClickListener.onCalulateScore(finalPosition2);
                    }
                });
            }
            if (onFastDeliverListener != null) {
                final int finalPosition3 = position;
                ((ViewHolderNormal) viewHolder).tvSearchResultFastDeliver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onFastDeliverListener.onFastDeliverListener(finalPosition3);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1) {
            if(isHasAd){
                if(jobsListBeanList==null){
                    return 1;
                }else if(jobsListBeanList.size()==1){
                    if(position==1){
                        return 1;
                    }else{
                        return 0;
                    }
                }else {
                    if (position == 2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }else{
                return 0;
            }

        } else {
            return 0;
        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (type != 3) {
            if (type == 1) {
                if (isHasAd) {
                    return jobsListBeanList == null ? 1 : jobsListBeanList.size() + 1;
                } else {
                    return jobsListBeanList == null ? 0 : jobsListBeanList.size();
                }
            } else {
                return jobsListBeanList2 == null ? 0 : jobsListBeanList2.size();
            }
        } else {
            if (jobsListBeanList != null && jobsListBeanList2 != null) {
                return jobsListBeanList.size() + jobsListBeanList2.size();
            } else {
                if (jobsListBeanList == null) {
                    if (jobsListBeanList2 == null) {
                        return 0;
                    } else {
                        return jobsListBeanList2.size();
                    }
                } else {
                    if (jobsListBeanList2 == null) {
                        return jobsListBeanList.size();
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolderNormal extends ViewHolder {
        @BindView(R.id.tv_itemTitle)
        TextView tvItemTitle;
        @BindView(R.id.ll_homeItemTop)
        LinearLayout llHomeItemTop;
        @BindView(R.id.rb_check)
        CheckBox rbCheck;
        @BindView(R.id.rl_homeFragmentItemLeft)
        RelativeLayout rlHomeFragmentItemLeft;
        @BindView(R.id.tv_recommendJobName)
        TextView tvRecommendJobName;
        @BindView(R.id.tv_recommendJobSalary)
        TextView tvRecommendJobSalary;
        @BindView(R.id.tv_recommendJobAddress)
        TextView tvRecommendJobAddress;
        @BindView(R.id.tv_recommendJobView1)
        TextView tvRecommendJobView1;
        @BindView(R.id.tv_recommendJobWorkYear)
        TextView tvRecommendJobWorkYear;
        @BindView(R.id.tv_recommendJobView2)
        TextView tvRecommendJobView2;
        @BindView(R.id.tv_recommendJobDegree)
        TextView tvRecommendJobDegree;
        @BindView(R.id.iv_recommendJobCompanyIcon)
        RoundImageView ivRecommendJobCompanyIcon;
        @BindView(R.id.tv_recommendJobCompanyName)
        TextView tvRecommendJobCompanyName;
        @BindView(R.id.tv_recommendJobIndustry)
        TextView tvRecommendJobIndustry;
        @BindView(R.id.tv_recommendJobView3)
        TextView tvRecommendJobView3;
        @BindView(R.id.tv_recommendJobCompanyType)
        TextView tvRecommendJobCompanyType;
        @BindView(R.id.tv_recommendJobView4)
        TextView tvRecommendJobView4;
        @BindView(R.id.tv_recommendPersonNum)
        TextView tvRecommendPersonNum;
        @BindView(R.id.pcv_num)
        PieChartView pcvNum;
        @BindView(R.id.tv_recommendJobTime)
        TextView tvRecommendJobTime;
        @BindView(R.id.iv_recommendJobNowPublic)
        ImageView ivRecommendJobNowPublic;
        @BindView(R.id.iv_topJob)
        ImageView ivTopJob;
        @BindView(R.id.fl_content)
        FrameLayout flContent;
        @BindView(R.id.view_lineJob)
        View viewLineJob;
        @BindView(R.id.tv_searchResultAlreadyDeliver)
        TextView tvSearchResultAlreadyDeliver;
        @BindView(R.id.tv_searchResultFastDeliver)
        TextView tvSearchResultFastDeliver;
        @BindView(R.id.iv_cantCheck)
        ImageView ivCantCheck;

        public ViewHolderNormal(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ViewHolderAd extends ViewHolder {
        @BindView(R.id.iv_recommendJobAdImage)
        ImageView ivRecommendJobAdImage;
        @BindView(R.id.tv_recommendJobAdTitle)
        TextView tvRecommendJobAdTitle;
        @BindView(R.id.view_recommendJobAd_line)
        View viewRecommendJobAdLine;
        @BindView(R.id.tv_recommendJobAd_des)
        TextView tvRecommendJobAdDes;
        @BindView(R.id.rl_recommendJobAd)
        RelativeLayout rlRecommendJobAd;
        public ViewHolderAd(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}





















