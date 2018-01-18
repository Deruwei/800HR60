package com.hr.ui.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.PieChartView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyReleaseJobAdapter extends RecyclerView.Adapter<MyReleaseJobAdapter.ViewHolder> {
    private List<RecommendJobBean.JobsListBean> jobsListBeanList;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_releasejob, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvItemReleaseJobName.setText(jobsListBeanList.get(position).getJob_name());
        viewHolder.tvItemReleaseJobSalary.setText(Utils.getSalary(jobsListBeanList.get(position).getSalary()));
        viewHolder.tvItemReleaseJobAddress.setText(jobsListBeanList.get(position).getWorkplace());
        viewHolder.tvItemReleaseJobDegree.setText(jobsListBeanList.get(position).getStudy());
        viewHolder.tvItemReleaseJobExp.setText(jobsListBeanList.get(position).getWorkyear());
        String time=jobsListBeanList.get(position).getIssue_date().substring(jobsListBeanList.get(position).getIssue_date().indexOf("-")+1);
        String month=time.substring(0,time.indexOf("-"));
        String day=time.substring(time.indexOf("-")+1);
        viewHolder.tvItemReleaseJobTime.setText(month+" - "+day);
        if (clickCallBack != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallBack.onItemClick(position);
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
        @BindView(R.id.tv_itemReleaseJobName)
        TextView tvItemReleaseJobName;
        @BindView(R.id.tv_itemReleaseJobSalary)
        TextView tvItemReleaseJobSalary;
        @BindView(R.id.tv_itemReleaseJobAddress)
        TextView tvItemReleaseJobAddress;
        @BindView(R.id.tv_itemReleaseJobExp)
        TextView tvItemReleaseJobExp;
        @BindView(R.id.tv_itemReleaseJobDegree)
        TextView tvItemReleaseJobDegree;
        @BindView(R.id.tv_itemReleaseJobTime)
        TextView tvItemReleaseJobTime;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}





















