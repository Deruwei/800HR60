package com.hr.ui.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.QueryShieldCompanyBean;
import com.hr.ui.utils.datautils.FromStringToArrayList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyShieldCompanyAdapter extends RecyclerView.Adapter<MyShieldCompanyAdapter.ViewHolder> {
    private List<QueryShieldCompanyBean.EnteListBean> favouriteListBeanList;
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

    public List<QueryShieldCompanyBean.EnteListBean> getFavouriteListBeanList() {
        return favouriteListBeanList;
    }

    public void setFavouriteListBeanList(List<QueryShieldCompanyBean.EnteListBean> favouriteListBeanList) {
        this.favouriteListBeanList = favouriteListBeanList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shieldcompany, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
       viewHolder.tvItemShieldCompanyName.setText(favouriteListBeanList.get(position).getEnterprise_name());
       viewHolder.tvItemShieldCompanyIndustry.setText(FromStringToArrayList.getInstance().getIndustryName(favouriteListBeanList.get(position).getIndustry()));
        if (clickCallBack != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallBack.onItemClick(position);
                }
            });
        }
        if (onViewClick != null) {
            viewHolder.tvItemShieldCompanySetShield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClick.onViewClick((TextView) v, position);
                    doDelete(viewHolder.getAdapterPosition());

                }
            });
        }
    }
    private void doDelete(int adapterPosition) {
        if (adapterPosition == favouriteListBeanList.size()) {
            favouriteListBeanList.remove(adapterPosition - 1);
        } else {
            favouriteListBeanList.remove(adapterPosition);
        }

        notifyItemRemoved(adapterPosition);
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return favouriteListBeanList == null ? 0 : favouriteListBeanList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_itemShieldCompanyIcon)
        ImageView ivItemShieldCompanyIcon;
        @BindView(R.id.tv_itemShieldCompanyName)
        TextView tvItemShieldCompanyName;
        @BindView(R.id.tv_itemShieldCompanyIndustry)
        TextView tvItemShieldCompanyIndustry;
        @BindView(R.id.tv_itemShieldCompanyType)
        TextView tvItemShieldCompanyType;
        @BindView(R.id.tv_itemShieldCompanyScale)
        TextView tvItemShieldCompanyScale;
        @BindView(R.id.tv_itemShieldCompanySetShield)
        TextView tvItemShieldCompanySetShield;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnViewClick {
        void onViewClick(TextView btn, int position);
    }
}





















