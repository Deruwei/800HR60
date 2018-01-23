package com.hr.ui.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.CircleImageView;
import com.hr.ui.view.RoundImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.ViewHolder> {
    private List<InviteBean.InvitedListBean> listBeans;
    private Context context;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public void setListBeans(List<InviteBean.InvitedListBean> listBeans) {
        this.listBeans = listBeans;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    private ItemClickCallBack clickCallBack;

    public MyMessageAdapter(Context context) {
        this.context = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inviteinterview, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvInviteCompanyName.setText(listBeans.get(position).getInvited_title());
        viewHolder.tvInviteCompanyTime.setText(Utils.getDateMonthAndDay(listBeans.get(position).getInvited_time()));
        viewHolder.tvInvitePersonName.setText(listBeans.get(position).getEnterprise_name());
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
        @BindView(R.id.iv_itemInviteInterViewPerson)
        RoundImageView ivItemInviteInterViewPerson;
        @BindView(R.id.tv_invitePersonName)
        TextView tvInvitePersonName;
        @BindView(R.id.tv_inviteCompanyName)
        TextView tvInviteCompanyName;
        @BindView(R.id.tv_inviteCompanyTime)
        TextView tvInviteCompanyTime;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}





















