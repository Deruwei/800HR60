package com.hr.ui.ui.message.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.bean.GuidanceBean;
import com.hr.ui.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyGuidanceAdapter extends RecyclerView.Adapter<MyGuidanceAdapter.ViewHolder> {
    private List<GuidanceBean.TitleListBean> favouriteListBeanList;
    private OnViewClick onViewClick;

    public void setOnViewClick(OnViewClick onViewClick) {
        this.onViewClick = onViewClick;
    }

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }


    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    private ItemClickCallBack clickCallBack;

    public List<GuidanceBean.TitleListBean> getFavouriteListBeanList() {
        return favouriteListBeanList;
    }

    public void setFavouriteListBeanList(List<GuidanceBean.TitleListBean> favouriteListBeanList) {
        this.favouriteListBeanList = favouriteListBeanList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_guidance, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvItemGuidanceTitle.setText(favouriteListBeanList.get(position).getTitle());
        viewHolder.tvItemGuidanceScanNum.setText(favouriteListBeanList.get(position).getViews());
        if(!"".equals(favouriteListBeanList.get(position).getInputtime())&&favouriteListBeanList.get(position).getInputtime()!=null) {
            viewHolder.tvItemGuidanceTime.setText(favouriteListBeanList.get(position).getInputtime());
        }
        viewHolder.tvItemGuidanceType.setText(favouriteListBeanList.get(position).getCopyfrom());
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
        return favouriteListBeanList == null ? 0 : favouriteListBeanList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout viewListMainContent;
        @BindView(R.id.tv_itemGuidanceTitle)
        TextView tvItemGuidanceTitle;
        @BindView(R.id.tv_itemGuidanceScanNum)
        TextView tvItemGuidanceScanNum;
        @BindView(R.id.tv_itemGuidanceTime)
        TextView tvItemGuidanceTime;
        @BindView(R.id.tv_itemGuidanceType)
        TextView tvItemGuidanceType;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnViewClick {
        void onViewClick(Button btn, int position);
    }
}





















