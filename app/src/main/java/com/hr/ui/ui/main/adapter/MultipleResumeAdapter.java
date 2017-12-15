package com.hr.ui.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.ResumeData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/12/11.
 */

public class MultipleResumeAdapter extends RecyclerView.Adapter<MultipleResumeAdapter.MultipleResumeViewHolder> {
    private Context context;
    private List<ResumeData> resumeDataList;
    public MultipleResumeAdapter(Context context,List<ResumeData> resumeDataList) {
        this.context = context;
        this.resumeDataList=resumeDataList;
    }

    @Override
    public MultipleResumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resumeitem, parent, false);
        return new MultipleResumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MultipleResumeViewHolder holder, int position) {
      /*  if(position==0){
            holder.tvItemResumeTitle.setText(resumeDataList.get(position).getTitle());
            holder.tvDiscoveryMultipleResumesComplete.setText("完整度："+resumeDataList.get(position).getComplete()+"%");
            holder.ivItemResumeIcon.setImageResource(R.mipmap.resume1);
        }else if(position==1){*/
            holder.tvItemResumeTitle.setText(resumeDataList.get(position).getTitle());
            holder.tvDiscoveryMultipleResumesComplete.setText("完整度："+resumeDataList.get(position).getComplete()+"%");
            holder.ivItemResumeIcon.setImageResource(resumeDataList.get(position).getImageId());
       /* }*//*/*else if (position==2){
            holder.tvItemResumeTitle.setText(resumeDataList.get(position).getTitle());
            if("".equals(resumeDataList.get(position).getComplete())){
                holder.tvDiscoveryMultipleResumesComplete.setVisibility(View.GONE);
            }else {
                holder.tvDiscoveryMultipleResumesComplete.setText("完整度：" + resumeDataList.get(position).getComplete() + "%");
            }
            if(!"".equals(resumeDataList.get(position).getResumeId())) {
                holder.ivItemResumeIcon.setImageResource(R.mipmap.resume3);
            }else{
                holder.ivItemResumeIcon.setImageResource(R.mipmap.resume3none);
            }
        }else if(position==3){
            holder.tvItemResumeTitle.setText(resumeDataList.get(position).getTitle());
            if("".equals(resumeDataList.get(position).getComplete())){
                holder.tvDiscoveryMultipleResumesComplete.setVisibility(View.GONE);
            }else {
                holder.tvDiscoveryMultipleResumesComplete.setText("完整度：" + resumeDataList.get(position).getComplete() + "%");
            }
            if(!"".equals(resumeDataList.get(position).getResumeId())) {
                holder.ivItemResumeIcon.setImageResource(R.mipmap.resume4);
            }else{
                holder.ivItemResumeIcon.setImageResource(R.mipmap.resume4none);
            }
        }else if(position==4){
            holder.tvItemResumeTitle.setText(resumeDataList.get(position).getTitle());
            if("".equals(resumeDataList.get(position).getComplete())){
                holder.tvDiscoveryMultipleResumesComplete.setVisibility(View.GONE);
            }else {
                holder.tvDiscoveryMultipleResumesComplete.setText("完整度：" + resumeDataList.get(position).getComplete() + "%");
            }
            if(!"".equals(resumeDataList.get(position).getResumeId())) {
                holder.ivItemResumeIcon.setImageResource(R.mipmap.resume5);
            }else{
                holder.ivItemResumeIcon.setImageResource(R.mipmap.resume5none);
            }
        }*/
    }

    @Override
    public int getItemCount() {
        return resumeDataList==null?0:resumeDataList.size();
    }

    class MultipleResumeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_itemResumeTitle)
        TextView tvItemResumeTitle;
        @BindView(R.id.iv_ItemResumeIcon)
        ImageView ivItemResumeIcon;
        @BindView(R.id.tv_discoveryMultipleResumesComplete)
        TextView tvDiscoveryMultipleResumesComplete;
        @BindView(R.id.rl_itemResumePreview)
        RelativeLayout rlItemResumePreview;
        @BindView(R.id.tv_itemResumeSetDefault)
        TextView tvItemResumeSetDefault;
        public MultipleResumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
