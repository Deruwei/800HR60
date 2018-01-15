package com.hr.ui.ui.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.icu.util.IslamicCalendar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.IndustryBean;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/12/27.
 */

public class SelectIndustryAdapter extends RecyclerView.Adapter<SelectIndustryAdapter.MyViewHolder> {
    private List<CityBean> industryList;
    private Context context;
    private int type;
    private String canSelectOther;
    private String industryId;
    private List<String> industryIds;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SelectIndustryAdapter(List<CityBean> industryList,String industryId, Context context,String canSelectOther,List<String> industryIds) {
        this.industryList = industryList;
        this.industryId=industryId;
        this.context = context;
        this.canSelectOther=canSelectOther;
        this.industryIds=industryIds;
    }
    public SelectIndustryAdapter(List<CityBean> industryList, Context context,int type) {
        this.industryList = industryList;
        this.context = context;
        this.type=type;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(type==1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_territorytext, parent, false);
            return new SelectIndustryAdapter.MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_industrytext, parent, false);
            return new SelectIndustryAdapter.MyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if ("1".equals(canSelectOther)) {
            if (industryList.get(position).isCheck() == true) {
                holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_orange);
                holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
                if (onItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.OnItemCLick(v, position);
                        }
                    });
                }
            }else{
                for(int i=0;i<industryIds.size();i++){
                    if(industryIds.get(i).equals(industryList.get(position).getId())&&!industryId.equals(industryIds.get(i))){
                        holder.tvIndustryItemName.setPaintFlags(Paint. STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);
                        holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_gray_noselct);
                        holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                    }
                }
            }
            if(!industryIds.contains(industryList.get(position).getId())&&industryList.get(position).isCheck()!=true){
                holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_white);
                holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
                if (onItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.OnItemCLick(v, position);
                        }
                    });
                }
            }
        } else if("2".equals(canSelectOther)){
            if (type == 1) {
                if (industryList.get(position).isCheck() == true) {
                    holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_orange);
                    holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
                }else{
                    holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_white);
                    holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
                }
                if (onItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.OnItemCLick(v, position);
                        }
                    });
                }
            } else {
                if (industryList.get(position).isCheck() == true) {
                    holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_orange);
                    holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
                } else {
                    for (int i = 0; i < industryIds.size(); i++) {
                        if (industryIds.get(i).equals(industryList.get(position).getId())) {
                            holder.tvIndustryItemName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_gray_noselct);
                            holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_999));
                        }
                    }
                }
                if (!industryIds.contains(industryList.get(position).getId())&&industryList.get(position).isCheck()!=true) {
                    holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_white);
                    holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
                    if (onItemClickListener != null) {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.OnItemCLick(v, position);
                            }
                        });
                    }
                }

            }

        }else{
            if (industryList.get(position).isCheck() == true) {
                holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_orange);
                holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.new_main));
            } else{
                holder.tvIndustryItemName.setBackgroundResource(R.drawable.tv_bg_white);
                holder.tvIndustryItemName.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
            }
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.OnItemCLick(v, position);
                    }
                });
            }
        }
        holder.tvIndustryItemName.setText(industryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return industryList == null ? 0 : industryList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_IndustryItemName)
        TextView tvIndustryItemName;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
