package com.hr.ui.ui.me.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.CollectionBean;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.ViewHolder> {
    private List<CollectionBean.FavouriteListBean> favouriteListBeanList;
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

    public List<CollectionBean.FavouriteListBean> getFavouriteListBeanList() {
        return favouriteListBeanList;
    }

    public void setFavouriteListBeanList(List<CollectionBean.FavouriteListBean> favouriteListBeanList) {
        this.favouriteListBeanList = favouriteListBeanList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collection, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvItemCollectionSalary.setText(favouriteListBeanList.get(position).getSalary());
        viewHolder.tvItemCollectionDegree.setText(favouriteListBeanList.get(position).getStudy());
        viewHolder.tvItemCollectionExp.setText(favouriteListBeanList.get(position).getWorkyear());
        viewHolder.tvItemCollectionJobName.setText(favouriteListBeanList.get(position).getJob_name());
        viewHolder.tvItemCollectionPlace.setText(favouriteListBeanList.get(position).getWorkplace());
        viewHolder.tvItemCollectionCompanyName.setText(favouriteListBeanList.get(position).getEnterprise_name());
        if (favouriteListBeanList.get(position).getIs_apply() == 0) {
            viewHolder.btnItemCollectionDeliver.setClickable(true);
            viewHolder.btnItemCollectionDeliver.setText(R.string.deliver);
        } else if (favouriteListBeanList.get(position).getIs_apply() == 1) {
            viewHolder.btnItemCollectionDeliver.setText(R.string.allReadyDeliver);
            viewHolder.btnItemCollectionDeliver.setEnabled(false);
        }
        if (clickCallBack != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickCallBack.onItemClick(position);
                }
            });
        }
        if (onViewClick != null) {
            viewHolder.btnItemCollectionDeliver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClick.onViewClick((Button) v, position);
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
        @BindView(R.id.tv_itemCollectionJobName)
        TextView tvItemCollectionJobName;
        @BindView(R.id.tv_itemCollectionSalary)
        TextView tvItemCollectionSalary;
        @BindView(R.id.tv_itemCollectionPlace)
        TextView tvItemCollectionPlace;
        @BindView(R.id.tv_itemCollectionExp)
        TextView tvItemCollectionExp;
        @BindView(R.id.tv_itemCollectionDegree)
        TextView tvItemCollectionDegree;
        @BindView(R.id.tv_itemCollectionCompanyName)
        TextView tvItemCollectionCompanyName;
        @BindView(R.id.btn_itemCollectionDeliver)
        Button btnItemCollectionDeliver;
        View mViewContent;
        View mActionContainer;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnViewClick {
        void onViewClick(Button btn, int position);
    }
}





















