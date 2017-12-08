package com.hr.ui.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.IndustryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/11/29.
 */

public class MyIndustryAdapter extends RecyclerView.Adapter<MyIndustryAdapter.MyIndustryHolder> {
    private List<IndustryBean> industryBeanList;
    public Context context;
    private MyIndustryHolder myIndustryHolder;

    public MyIndustryAdapter(Context context, List<IndustryBean> industryBeanList) {
        this.context = context;
        this.industryBeanList = industryBeanList;
    }

    @Override
    public MyIndustryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (myIndustryHolder == null) {
            myIndustryHolder = new MyIndustryHolder(LayoutInflater.from(context).inflate(R.layout.item_industry, parent, false));

        }
        return myIndustryHolder;
    }

    @Override
    public void onBindViewHolder(MyIndustryHolder holder, int position) {
        holder.ivIndustryImg.setBackgroundResource(industryBeanList.get(position).getIndustryUrlId());
        holder.tvIndustryName.setText(industryBeanList.get(position).getIndustryName());
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
