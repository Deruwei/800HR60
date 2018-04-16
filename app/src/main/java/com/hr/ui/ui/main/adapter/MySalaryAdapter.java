package com.hr.ui.ui.main.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.CityBean;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/11/29.
 */

public class MySalaryAdapter extends RecyclerView.Adapter<MySalaryAdapter.MyIndustryHolder> {
    private List<CityBean> salarys;
    public Context context;
    private OnItemClickListener onItemClickListener;

    public MySalaryAdapter(Context context, List<CityBean> salarys) {
        this.context = context;
        this.salarys = salarys;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyIndustryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_territorytext, parent, false);
        return new MyIndustryHolder(view);
    }

    @Override
    public void onBindViewHolder(MyIndustryHolder holder, final int position) {
        holder.tvIndustryItemName.setText(salarys.get(position).getName());
        if (salarys.get(position).isCheck() == true) {
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
    }

    @Override
    public int getItemCount() {
        return salarys == null ? 0 : salarys.size();
    }

    class MyIndustryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_IndustryItemName)
        TextView tvIndustryItemName;
        public MyIndustryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
