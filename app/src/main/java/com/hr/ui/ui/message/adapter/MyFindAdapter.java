package com.hr.ui.ui.message.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.FindBean;
import com.hr.ui.utils.Utils;
import com.hr.ui.view.RoundCornerImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyFindAdapter extends RecyclerView.Adapter<MyFindAdapter.ViewHolder> {
    private List<FindBean.ListBean> listBeans;
    private Context context;
    private int type;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public void setListBeans(List<FindBean.ListBean> listBeans) {
        this.listBeans = listBeans;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    private ItemClickCallBack clickCallBack;

    public MyFindAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvFindCompanyName.setText(listBeans.get(position).getTitle());
        if ("2".equals(listBeans.get(position).getTopic_type())) {
            viewHolder.rlFindHide.setVisibility(View.VISIBLE);
            viewHolder.tvFindCompanyScale.setText(listBeans.get(position).getStuff_munber());
            viewHolder.tvFindCompanyType.setText(listBeans.get(position).getCompany_type());
        } else {
            viewHolder.rlFindHide.setVisibility(View.GONE);
        }
        if (type == 2) {
            viewHolder.tvFindCompanyName.setVisibility(View.VISIBLE);
            viewHolder.tvCompanyTitle.setText(listBeans.get(position).getTitle());
            viewHolder.rlItemFind.setVisibility(View.GONE);
        } else {
            viewHolder.rlFindHide.setVisibility(View.VISIBLE);
            viewHolder.tvFindCompanyName.setText(listBeans.get(position).getTitle());
            viewHolder.tvCompanyTitle.setVisibility(View.GONE);
        }
        Utils.setImageResource(context, viewHolder.ivFindIcon, listBeans.get(position).getPic_path());
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
        @BindView(R.id.iv_findIcon)
        RoundCornerImageView ivFindIcon;
        @BindView(R.id.tv_CompanyTitle)
        TextView tvCompanyTitle;
        @BindView(R.id.tv_findCompanyName)
        TextView tvFindCompanyName;
        @BindView(R.id.tv_findCompanyType)
        TextView tvFindCompanyType;
        @BindView(R.id.tv_findCompanyScale)
        TextView tvFindCompanyScale;
        @BindView(R.id.rl_findHide)
        LinearLayout rlFindHide;
        @BindView(R.id.rl_itemFind)
        LinearLayout rlItemFind;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}





















