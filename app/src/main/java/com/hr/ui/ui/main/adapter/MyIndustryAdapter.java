package com.hr.ui.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.IndustryBean;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/11/29.
 */

public class MyIndustryAdapter extends RecyclerView.Adapter<MyIndustryAdapter.MyIndustryHolder> {
    private List<CityBean> industryBeanList;
    public Context context;
    private OnItemClickListener onItemClickListener;

    public MyIndustryAdapter(Context context, List<CityBean> industryBeanList) {
        this.context = context;
        this.industryBeanList = industryBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyIndustryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_industry, parent, false);
        return new MyIndustryAdapter.MyIndustryHolder(view);
    }

    @Override
    public void onBindViewHolder(MyIndustryHolder holder, final int position) {
        holder.ivIndustryImg.setImageDrawable(FromStringToArrayList.getInstance().getIndustryIcon(industryBeanList.get(position).getId()));
        holder.tvIndustryName.setText(industryBeanList.get(position).getName());
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemCLick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return industryBeanList == null ? 0 : industryBeanList.size();
    }

    class MyIndustryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_industryImg)
        ImageView ivIndustryImg;
        @BindView(R.id.tv_industryName)
        TextView tvIndustryName;
        public MyIndustryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
