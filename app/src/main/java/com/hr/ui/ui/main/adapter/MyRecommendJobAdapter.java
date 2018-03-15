package com.hr.ui.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.HomeRecommendBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.PieChartView;
import com.hr.ui.view.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hr.ui.ui.main.activity.JobOrderActivity.TAG;

/**
 *
 */
public class MyRecommendJobAdapter extends RecyclerView.Adapter<MyRecommendJobAdapter.ViewHolder> {

    private int firstNum, sencondNum;
    private int type;//type：1识别为搜索结果的item  没有传递的话就是主页的item
    private List<RecommendJobBean.JobsListBean> jobsListBeanList;
    private OnCalCulateScoreClickListener onCalCulateScoreClickListener;
    private List<HomeRecommendBean.JobsListBean> jobsListBeanList2;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public void setOnCalCulateScoreClickListener(OnCalCulateScoreClickListener onCalCulateScoreClickListener) {
        this.onCalCulateScoreClickListener = onCalCulateScoreClickListener;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    public interface OnCalCulateScoreClickListener {
        void onCalulateScore(int pos);
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_homefragment_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public MyRecommendJobAdapter() {
    }

    public MyRecommendJobAdapter(int type) {
        this.type = type;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (type != 3) {
            if (type == 1) {
                viewHolder.llHomeItemTop.setVisibility(View.GONE);
                if (jobsListBeanList.get(position).isTop() == true) {
                    viewHolder.ivTopJob.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.ivTopJob.setVisibility(View.GONE);
                }
                viewHolder.tvRecommendJobAddress.setText(jobsListBeanList.get(position).getWorkplace());
                viewHolder.tvRecommendJobCompanyName.setText(jobsListBeanList.get(position).getEnterprise_name());
                viewHolder.tvRecommendJobDegree.setText(jobsListBeanList.get(position).getStudy());
                viewHolder.tvRecommendJobIndustry.setText(jobsListBeanList.get(position).getIndustry_name());
                if (jobsListBeanList.get(position).getEnt_logo() != null && !"".equals(jobsListBeanList.get(position).getEnt_logo())) {
                    Utils.setImageResource(HRApplication.getAppContext(), viewHolder.ivRecommendJobCompanyIcon, Constants.IMAGE_BASEPATH2 + jobsListBeanList.get(position).getEnt_logo());
                } else {
                    Utils.setImageResourceDefault(HRApplication.getAppContext(), viewHolder.ivRecommendJobCompanyIcon);
                }
                viewHolder.tvRecommendJobName.setText(jobsListBeanList.get(position).getJob_name());
                viewHolder.tvRecommendJobSalary.setText(Utils.getSalary(jobsListBeanList.get(position).getSalary()));
                String day = Utils.getDateMonthAndDay(jobsListBeanList.get(position).getIssue_date());
                SimpleDateFormat formatter = new SimpleDateFormat("M - d");//格式为 2013年9月3日 14:44
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String currentDate = formatter.format(curDate);
                Log.d(TAG, "onBindViewHolder: .i"+currentDate);
                if(currentDate.equals(day)){
                    viewHolder.ivRecommendJobNowPublic.setVisibility(View.VISIBLE);
                }else{
                    viewHolder.ivRecommendJobNowPublic.setVisibility(View.GONE);
                    viewHolder.tvRecommendJobTime.setText(day);
                }

                viewHolder.tvRecommendPersonNum.setText(jobsListBeanList.get(position).getStuff_munber());
                viewHolder.tvRecommendJobWorkYear.setText(jobsListBeanList.get(position).getWorkyear());
                viewHolder.tvRecommendJobCompanyType.setText(jobsListBeanList.get(position).getCompany_type());
            } else {
                if (position == 0) {
                    viewHolder.llHomeItemTop.setVisibility(View.VISIBLE);
                    viewHolder.tvItemTitle.setText(R.string.recommendJob1);
                } else {
                    viewHolder.llHomeItemTop.setVisibility(View.GONE);
                }
                viewHolder.tvRecommendJobAddress.setText(jobsListBeanList2.get(position).getWorkplace());
                viewHolder.tvRecommendJobCompanyName.setText(jobsListBeanList2.get(position).getEnterprise_name());
                viewHolder.tvRecommendJobDegree.setText(jobsListBeanList2.get(position).getStudy());
                viewHolder.tvRecommendJobIndustry.setText(jobsListBeanList2.get(position).getIndustry_name());
                if (jobsListBeanList2.get(position).getEnt_logo() != null && !"".equals(jobsListBeanList2.get(position).getEnt_logo())) {
                    Utils.setImageResource(HRApplication.getAppContext(), viewHolder.ivRecommendJobCompanyIcon, Constants.IMAGE_BASEPATH2 + jobsListBeanList2.get(position).getEnt_logo());
                } else {
                    Utils.setImageResourceDefault(HRApplication.getAppContext(), viewHolder.ivRecommendJobCompanyIcon);
                }
                viewHolder.tvRecommendJobName.setText(jobsListBeanList2.get(position).getJob_name());
                viewHolder.tvRecommendJobSalary.setText(Utils.getSalary(jobsListBeanList2.get(position).getSalary()));
                String day = Utils.getDateMonthAndDay(jobsListBeanList2.get(position).getIssue_date());
                if ("".equals(day) || day == null) {
                    day = Utils.getDateMonthAndDay(Utils.timeStamp2Date(jobsListBeanList2.get(position).getIssue_date(), "yyyy-MM-dd"));
                }
                viewHolder.tvRecommendJobTime.setText(day);
                viewHolder.tvRecommendPersonNum.setText(jobsListBeanList2.get(position).getNumber());
                viewHolder.tvRecommendJobWorkYear.setText(jobsListBeanList2.get(position).getWorkyear());
                viewHolder.tvRecommendJobCompanyType.setText(jobsListBeanList2.get(position).getCompany_type());
            }
        } else {
            //Log.i("经过这里",jobsListBeanList2.toString());
            if (position < jobsListBeanList2.size()) {
                if (position == 0) {
                    viewHolder.llHomeItemTop.setVisibility(View.VISIBLE);
                    viewHolder.tvItemTitle.setText(R.string.recommendJob1);
                } else {
                    viewHolder.llHomeItemTop.setVisibility(View.GONE);
                }
                if (position == jobsListBeanList2.size() - 1) {
                    viewHolder.viewLineJob.setVisibility(View.GONE);
                }
                viewHolder.tvRecommendJobAddress.setText(jobsListBeanList2.get(position).getWorkplace());
                viewHolder.tvRecommendJobCompanyName.setText(jobsListBeanList2.get(position).getEnterprise_name());
                viewHolder.tvRecommendJobDegree.setText(jobsListBeanList2.get(position).getStudy());
                viewHolder.tvRecommendJobIndustry.setText(jobsListBeanList2.get(position).getIndustry_name());
                if (jobsListBeanList2.get(position).getEnt_logo() != null && !"".equals(jobsListBeanList2.get(position).getEnt_logo())) {
                    Utils.setImageResource(HRApplication.getAppContext(), viewHolder.ivRecommendJobCompanyIcon, Constants.IMAGE_BASEPATH2 + jobsListBeanList2.get(position).getEnt_logo());
                } else {
                    Utils.setImageResourceDefault(HRApplication.getAppContext(), viewHolder.ivRecommendJobCompanyIcon);
                }
                viewHolder.tvRecommendJobName.setText(jobsListBeanList2.get(position).getJob_name());
                viewHolder.tvRecommendJobSalary.setText(Utils.getSalary(jobsListBeanList2.get(position).getSalary()));
                String day = Utils.getDateMonthAndDay(jobsListBeanList2.get(position).getIssue_date());
                if ("".equals(day) || day == null) {
                    day = Utils.getDateMonthAndDay(Utils.timeStamp2Date(jobsListBeanList2.get(position).getIssue_date(), "yyyy-MM-dd"));
                }
                viewHolder.tvRecommendJobTime.setText(day);
                viewHolder.tvRecommendPersonNum.setText(jobsListBeanList2.get(position).getNumber());
                viewHolder.tvRecommendJobWorkYear.setText(jobsListBeanList2.get(position).getWorkyear());
                viewHolder.tvRecommendJobCompanyType.setText(jobsListBeanList2.get(position).getCompany_type());
                //viewHolder.tvRecommendJobCompanyType.setText(jobsListBeanList2.get(position).getCompany_type());
                int num = (int) (Double.parseDouble(jobsListBeanList2.get(position).getMatch_value()) * 100.0);
                viewHolder.pcvNum.SetProgram(num);
            } else {
                //Log.i("经过这里2","------");
                int i = jobsListBeanList2.size();
                if (position == jobsListBeanList2.size()) {
                    viewHolder.llHomeItemTop.setVisibility(View.VISIBLE);
                    viewHolder.tvItemTitle.setText(R.string.recommendJob2);
                } else {
                    viewHolder.llHomeItemTop.setVisibility(View.GONE);
                }
                viewHolder.viewLineJob.setVisibility(View.VISIBLE);
                viewHolder.tvRecommendJobAddress.setText(jobsListBeanList.get(position - i).getWorkplace());
                viewHolder.tvRecommendJobCompanyName.setText(jobsListBeanList.get(position - i).getEnterprise_name());
                viewHolder.tvRecommendJobDegree.setText(jobsListBeanList.get(position - i).getStudy());
                viewHolder.tvRecommendJobIndustry.setText(jobsListBeanList.get(position - i).getIndustry_name());
                if (jobsListBeanList.get(position - i).getEnt_logo() != null && !"".equals(jobsListBeanList.get(position - i).getEnt_logo())) {
                    Utils.setImageResource(HRApplication.getAppContext(), viewHolder.ivRecommendJobCompanyIcon, Constants.IMAGE_BASEPATH2 + jobsListBeanList.get(position - i).getEnt_logo());
                } else {
                    Utils.setImageResourceDefault(HRApplication.getAppContext(), viewHolder.ivRecommendJobCompanyIcon);
                }
                viewHolder.tvRecommendJobName.setText(jobsListBeanList.get(position - i).getJob_name());
                viewHolder.tvRecommendJobSalary.setText(Utils.getSalary(jobsListBeanList.get(position - i).getSalary()));
                viewHolder.tvRecommendJobTime.setText(Utils.getDateMonthAndDay(jobsListBeanList.get(position - i).getIssue_date()));
                viewHolder.tvRecommendPersonNum.setText(jobsListBeanList.get(position - i).getNumber());
                viewHolder.tvRecommendJobWorkYear.setText(jobsListBeanList.get(position - i).getWorkyear());
                viewHolder.tvRecommendJobCompanyType.setText(jobsListBeanList.get(position - i).getCompany_type());
            }
        }
        if (clickCallBack != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallBack.onItemClick(position);
                }
            });
        }
        if (onCalCulateScoreClickListener != null) {
            viewHolder.pcvNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCalCulateScoreClickListener.onCalulateScore(position);
                }
            });
        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (type != 3) {
            if (type == 1) {
                return jobsListBeanList == null ? 0 : jobsListBeanList.size();
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

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recommendJobName)
        TextView tvRecommendJobName;
        @BindView(R.id.tv_recommendJobSalary)
        TextView tvRecommendJobSalary;
        @BindView(R.id.tv_recommendJobAddress)
        TextView tvRecommendJobAddress;
        @BindView(R.id.iv_recommendJobNowPublic)
        ImageView ivRecommendJobNowPublic;
        @BindView(R.id.tv_recommendJobWorkYear)
        TextView tvRecommendJobWorkYear;
        @BindView(R.id.tv_recommendJobDegree)
        TextView tvRecommendJobDegree;
        @BindView(R.id.tv_recommendJobTime)
        TextView tvRecommendJobTime;
        @BindView(R.id.iv_recommendJobCompanyIcon)
        RoundImageView ivRecommendJobCompanyIcon;
        @BindView(R.id.tv_recommendJobCompanyName)
        TextView tvRecommendJobCompanyName;
        @BindView(R.id.tv_recommendJobIndustry)
        TextView tvRecommendJobIndustry;
        @BindView(R.id.tv_recommendJobCompanyType)
        TextView tvRecommendJobCompanyType;
        @BindView(R.id.tv_recommendPersonNum)
        TextView tvRecommendPersonNum;
        @BindView(R.id.pcv_num)
        PieChartView pcvNum;
        @BindView(R.id.iv_topJob)
        ImageView ivTopJob;
        @BindView(R.id.tv_itemTitle)
        TextView tvItemTitle;
        @BindView(R.id.view_lineJob)
        View viewLineJob;
        @BindView(R.id.ll_homeItemTop)
        LinearLayout llHomeItemTop;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}





















