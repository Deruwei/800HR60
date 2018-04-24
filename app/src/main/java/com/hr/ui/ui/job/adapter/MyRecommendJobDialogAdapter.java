package com.hr.ui.ui.job.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.EventBoolean;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.bean.TestBean;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyRecommendJobDialogAdapter extends RecyclerView.Adapter<MyRecommendJobDialogAdapter.ViewHolder> {
   private ItemClickCallBack clickCallBack;
    private  List<RecommendJobBean.JobsListBean>  list;
    private OnSelectClickListener onSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        this.onSelectClickListener = onSelectClickListener;
    }

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public void setListBeans(List<RecommendJobBean.JobsListBean>  list) {
        this.list=list;
    }
    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recommendjobdialog, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvItemRecommendJobDialogJobName.setText(list.get(position).getJob_name());
        viewHolder.tvItemRecommendJobDialogSalary.setText(Utils.getSalary(list.get(position).getSalary()));
        viewHolder.tvItemRecommendJobDialogCompanyName.setText(list.get(position).getEnterprise_name());
        viewHolder.tvItemRecommendJobDialogDate.setText(Utils.getDateMonthAndDay(list.get(position).getIssue_date()));
        viewHolder.tvItemRecommendJobDialogAddress.setText(list.get(position).getWorkplace());
        if(list.get(position).getIs_apply()==1){
            viewHolder.ivItemRecommendJobDialogAlreadyDeliver.setVisibility(View.VISIBLE);
            viewHolder.cbItemRecommendJobDialog.setBackgroundResource(R.mipmap.cant_select);
            viewHolder.rlItemRecommendJobDialog.setClickable(false);
            viewHolder.cbItemRecommendJobDialog.setClickable(false);

        }else{
            viewHolder.ivItemRecommendJobDialogAlreadyDeliver.setVisibility(View.GONE);
            viewHolder.cbItemRecommendJobDialog.setBackgroundResource(R.drawable.checkbox_bg);
            viewHolder.rlItemRecommendJobDialog.setClickable(true);
            viewHolder.cbItemRecommendJobDialog.setClickable(true);

        }
        if(list.get(position).isCheck()){
            viewHolder.cbItemRecommendJobDialog.setChecked(true);
        }else{
            viewHolder.cbItemRecommendJobDialog.setChecked(false);
        }
        if(clickCallBack!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallBack.onItemClick(position);

                }
            });

        }
        viewHolder.cbItemRecommendJobDialog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    list.get(position).setCheck(true);
                }else{
                    list.get(position).setCheck(false);
                }
                EventBus.getDefault().post(new EventBoolean(0,true));
            }
        });
        if(onSelectClickListener!=null){
            viewHolder.rlItemRecommendJobDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectClickListener.onSelectClickListener(position);
                    EventBus.getDefault().post(new EventBoolean(0,true));
                }
            });
         /*   viewHolder.cbItemRecommendJobDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectClickListener.onSelectClickListener(position);
                }
            });*/

        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_itemRecommendJobDialog)
        CheckBox cbItemRecommendJobDialog;
        @BindView(R.id.rl_itemRecommendJobDialog)
        RelativeLayout rlItemRecommendJobDialog;
        @BindView(R.id.tv_itemRecommendJobDialogJobName)
        TextView tvItemRecommendJobDialogJobName;
        @BindView(R.id.tv_itemRecommendJobDialogCompanyName)
        TextView tvItemRecommendJobDialogCompanyName;
        @BindView(R.id.tv_itemRecommendJobDialogSalary)
        TextView tvItemRecommendJobDialogSalary;
        @BindView(R.id.tv_itemRecommendJobDialogAddress)
        TextView tvItemRecommendJobDialogAddress;
        @BindView(R.id.tv_itemRecommendJobDialogDate)
        TextView tvItemRecommendJobDialogDate;
        @BindView(R.id.iv_itemRecommendJobDialogAlreadyDeliver)
        ImageView ivItemRecommendJobDialogAlreadyDeliver;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface OnSelectClickListener{
        void onSelectClickListener(int pos);
    }
}





















