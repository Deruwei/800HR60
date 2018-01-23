package com.hr.ui.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.PieChartView;
import com.hr.ui.view.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyRecommendJobAdapter extends RecyclerView.Adapter<MyRecommendJobAdapter.ViewHolder> {
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
    @BindView(R.id.tv_recommendJobTime)
    TextView tvRecommendJobTime;
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
    private List<RecommendJobBean.JobsListBean> jobsListBeanList;
    private OnCalCulateScoreClickListener onCalCulateScoreClickListener;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public void setOnCalCulateScoreClickListener(OnCalCulateScoreClickListener onCalCulateScoreClickListener) {
        this.onCalCulateScoreClickListener = onCalCulateScoreClickListener;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }
    public interface OnCalCulateScoreClickListener{
        void onCalulateScore(int pos);
    }
    private ItemClickCallBack clickCallBack;

    public List<RecommendJobBean.JobsListBean> getJobsListBeanList() {
        return jobsListBeanList;
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

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
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
        viewHolder.tvRecommendJobTime.setText(Utils.getDateMonthAndDay(jobsListBeanList.get(position).getIssue_date()));
        viewHolder.tvRecommendPersonNum.setText(jobsListBeanList.get(position).getStuff_munber());
        viewHolder.tvRecommendJobWorkYear.setText(jobsListBeanList.get(position).getWorkyear());
        viewHolder.tvRecommendJobCompanyType.setText(jobsListBeanList.get(position).getCompany_type());
        viewHolder.pcvNum.SetProgram(60);
        if (clickCallBack != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallBack.onItemClick(position);
                }
            });
        }
        if(onCalCulateScoreClickListener!=null){
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
        return jobsListBeanList == null ? 0 : jobsListBeanList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recommendJobName)
        TextView tvRecommendJobName;
        @BindView(R.id.tv_recommendJobSalary)
        TextView tvRecommendJobSalary;
        @BindView(R.id.tv_recommendJobAddress)
        TextView tvRecommendJobAddress;
        @BindView(R.id.tv_recommendJobWorkYear)
        TextView tvRecommendJobWorkYear;
        @BindView(R.id.tv_recommendJobDegree)
        TextView tvRecommendJobDegree;
        @BindView(R.id.tv_recommendJobTime)
        TextView tvRecommendJobTime;
        @BindView(R.id.iv_recommendJobCompanyIcon)
        ImageView ivRecommendJobCompanyIcon;
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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}





















