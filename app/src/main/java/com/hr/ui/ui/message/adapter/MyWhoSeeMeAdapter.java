package com.hr.ui.ui.message.adapter;

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
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.constants.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class MyWhoSeeMeAdapter extends RecyclerView.Adapter<MyWhoSeeMeAdapter.ViewHolder> {
    private List<WhoSeeMeBean.BrowsedListBean> listBeans;
    private Context context;

    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public void setListBeans(List<WhoSeeMeBean.BrowsedListBean> listBeans) {
        this.listBeans = listBeans;
    }

    public interface ItemClickCallBack {
        void onItemClick(int pos);
    }

    private ItemClickCallBack clickCallBack;

    public MyWhoSeeMeAdapter(Context context) {
        this.context = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_whoseeme, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
      /* Glide.with(context).load(Constants.IMAGE_BASEPATH+listBeans.get(position).)*/
        viewHolder.tvWhoSeeMeCompanyType.setText(listBeans.get(position).getCompany_type());
        if (listBeans.get(position).getEnt_logo() != null && !"".equals(listBeans.get(position).getEnt_logo())) {
            Glide.with(context).load(Constants.IMAGE_BASEPATH2 + listBeans.get(position).getEnt_logo()).centerCrop().into(viewHolder.ivWhoSeeMeCompanyIcon);
        }
        viewHolder.tvWhoSeeMeIndustry.setText(listBeans.get(position).getIndustry_name());
        viewHolder.tvRecommendJobCompanyName.setText(listBeans.get(position).getEnterprise_name());
        viewHolder.tvWhoSeeMePersonNum.setText(listBeans.get(position).getStuffmunber());
        viewHolder.tvWhoSeeMeTime.setText(listBeans.get(position).getBrowsed_time());
        if ("".equals(listBeans.get(position).getRecruit_info().getJob_name()) && "".equals(listBeans.get(position).getRecruit_info().getTotal())&&"0".equals(listBeans.get(position).getRecruit_info().getTotal())) {
            viewHolder.llWhoSeeMePosition.setVisibility(View.GONE);
        } else {
            viewHolder.llWhoSeeMePosition.setVisibility(View.VISIBLE);
            viewHolder.tvWhoSeeMePositionNum.setText(listBeans.get(position).getRecruit_info().getTotal());
            viewHolder.tvWhoSeeMePosition.setText(listBeans.get(position).getRecruit_info().getJob_name());
        }
        if (clickCallBack != null) {
            viewHolder.llWhoSeeMeCompany.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.iv_whoSeeMeCompanyIcon)
        ImageView ivWhoSeeMeCompanyIcon;
        @BindView(R.id.tv_recommendJobCompanyName)
        TextView tvRecommendJobCompanyName;
        @BindView(R.id.tv_whoSeeMeIndustry)
        TextView tvWhoSeeMeIndustry;
        @BindView(R.id.tv_recommendJobView3)
        TextView tvRecommendJobView3;
        @BindView(R.id.tv_whoSeeMeCompanyType)
        TextView tvWhoSeeMeCompanyType;
        @BindView(R.id.tv_recommendJobView4)
        TextView tvRecommendJobView4;
        @BindView(R.id.tv_whoSeeMePersonNum)
        TextView tvWhoSeeMePersonNum;
        @BindView(R.id.tv_whoSeeMeTime)
        TextView tvWhoSeeMeTime;
        @BindView(R.id.ll_whoSeeMeCompany)
        LinearLayout llWhoSeeMeCompany;
        @BindView(R.id.tv_whoSeeMePosition)
        TextView tvWhoSeeMePosition;
        @BindView(R.id.tv_whoSeeMePositionNum)
        TextView tvWhoSeeMePositionNum;
        @BindView(R.id.ll_whoSeeMePosition)
        LinearLayout llWhoSeeMePosition;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}





















