package com.hr.ui.ui.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.bean.ScanHistoryBean;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.MyStartAndEndTimeCustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyScanHistoryAdapter extends RecyclerView.Adapter<MyScanHistoryAdapter.ViewHolder> {
    private List<ScanHistoryBean> listBeans;
    private Context context;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public void setListBeans(List<ScanHistoryBean> listBeans) {
        this.listBeans = listBeans;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    private ItemClickCallBack clickCallBack;

    public MyScanHistoryAdapter(Context context) {
        this.context = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_scanhistory, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String time=listBeans.get(position).getTime().substring(listBeans.get(position).getTime().indexOf("-")+1,listBeans.get(position).getTime().lastIndexOf("-")+3);
        if(position!=0&&listBeans.get(position-1).getTime().substring(listBeans.get(position-1).getTime().indexOf("-")+1,listBeans.get(position-1).getTime().lastIndexOf("-")+3).equals(time)){
            viewHolder.tvItemScanHistoryTime.setVisibility(View.GONE);
        }else{
            viewHolder.tvItemScanHistoryTime.setVisibility(View.VISIBLE);
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");//格式为 2013年9月3日 14:44
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String currentDate = formatter.format(curDate);
           if(time.equals(currentDate)){
                viewHolder.tvItemScanHistoryTime.setText("今天");
            }else {
                String month=String.valueOf(Integer.parseInt(time.substring(0,time.indexOf("-"))));
                String day=String.valueOf(Integer.parseInt(time.substring(time.indexOf("-")+1)));
                time=month+" - "+day;
                viewHolder.tvItemScanHistoryTime.setText(time);
           }
        }
        viewHolder.tvItemScanHistoryJobName.setText(listBeans.get(position).getJobName());
        viewHolder.tvItemScanHistoryCompanyName.setText(listBeans.get(position).getCompanyName());
        viewHolder.tvItemScanHistoryDegree.setText(listBeans.get(position).getDegree());
        viewHolder.tvItemScanHistoryExp.setText(listBeans.get(position).getExp());
        viewHolder.tvItemScanHistoryPlace.setText(listBeans.get(position).getPlace());
        viewHolder.tvItemScanHistorySalary.setText(Utils.getSalary(listBeans.get(position).getSalary()));
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
        return listBeans == null ? 0 : listBeans.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_itemScanHistoryTime)
        TextView tvItemScanHistoryTime;
        @BindView(R.id.tv_itemScanHistoryJobName)
        TextView tvItemScanHistoryJobName;
        @BindView(R.id.tv_itemScanHistorySalary)
        TextView tvItemScanHistorySalary;
        @BindView(R.id.tv_itemScanHistoryPlace)
        TextView tvItemScanHistoryPlace;
        @BindView(R.id.tv_itemScanHistoryExp)
        TextView tvItemScanHistoryExp;
        @BindView(R.id.tv_itemScanHistoryDegree)
        TextView tvItemScanHistoryDegree;
        @BindView(R.id.tv_itemScanHistoryCompanyName)
        TextView tvItemScanHistoryCompanyName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}





















