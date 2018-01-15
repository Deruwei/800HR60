package com.hr.ui.ui.main.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.CityBean;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/11/29.
 */

public class MySelectOtherAdapter extends RecyclerView.Adapter<MySelectOtherAdapter.MyIndustryHolder> {
    private List<CityBean> listBean;
    public Context context;
    private OnItemClickListener onItemClickListener;
    private MyIndustryHolder myIndustryHolder;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MySelectOtherAdapter(Context context, List<CityBean> listBean) {
        this.listBean = listBean;
        this.context=context;
    }

    @Override
    public MyIndustryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_selectother, parent, false);
        return new MySelectOtherAdapter.MyIndustryHolder(view);
    }

    @Override
    public void onBindViewHolder(MyIndustryHolder holder, final int position) {
        if(listBean.get(position).isCheck()==true) {
            holder.rlSelectOtherItem.setBackgroundResource(R.drawable.tv_bg_orange);
            holder.tvSelectOtherItem.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.new_main));
        }else{
            holder.rlSelectOtherItem.setBackgroundResource(R.drawable.tv_bg_white);
            holder.tvSelectOtherItem.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.color_333));
        }
        holder.tvSelectOtherItem.setText(listBean.get(position).getName());
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
        return listBean == null ? 0 : listBean.size();
    }

    class MyIndustryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_selectOtherItem)
        TextView tvSelectOtherItem;
        @BindView(R.id.rl_selectOtherItem)
        LinearLayout rlSelectOtherItem;

        public MyIndustryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
