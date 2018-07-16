package com.hr.ui.ui.message.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hr.ui.R;
import com.hr.ui.bean.FindBean;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.glideTransformation.GlideRoundTransform;
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
        if (type != 2) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find, viewGroup, false);
            return new ViewHolderNormal(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find2, viewGroup, false);
            return new ViewHolderSpecial(view);
        }
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolderNormal) {
            ((ViewHolderNormal) viewHolder).tvFindCompanyScale.setText(listBeans.get(position).getStuff_munber());
            ((ViewHolderNormal) viewHolder).tvFindCompanyType.setText(listBeans.get(position).getCompany_type());
            ((ViewHolderNormal) viewHolder).tvFindCompanyName.setText(listBeans.get(position).getTitle());
            Utils.setImageResource(context, ((ViewHolderNormal) viewHolder).ivFindIcon, listBeans.get(position).getPic_path());
            if (clickCallBack != null) {
                ((ViewHolderNormal) viewHolder).llFindMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCallBack.onItemClick(position);
                    }
                });
            }
        }
        if(viewHolder instanceof ViewHolderSpecial){

            Glide.with(context).load(listBeans.get(position).getPic_path())
                    .fitCenter()
                    .bitmapTransform(new GlideRoundTransform(context, 15))
                    .animate(R.anim.crop_image_fade_anim)
                    .fitCenter().into(((ViewHolderSpecial) viewHolder).ivFindItem2);
            ((ViewHolderSpecial) viewHolder).tvFindItemTitle2.setText(listBeans.get(position).getTitle());
            if (clickCallBack != null) {
                ((ViewHolderSpecial) viewHolder).cvFindItem2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCallBack.onItemClick(position);
                    }
                });
            }
        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return listBeans == null ? 0 : listBeans.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }

    public static class ViewHolderNormal extends ViewHolder {
        @BindView(R.id.iv_findIcon)
        RoundCornerImageView ivFindIcon;
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
        @BindView(R.id.ll_findMain)
        LinearLayout llFindMain;

        public ViewHolderNormal(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class ViewHolderSpecial extends ViewHolder {
        @BindView(R.id.iv_findItem2)
        ImageView ivFindItem2;
        @BindView(R.id.tv_findItemTitle2)
        TextView tvFindItemTitle2;
        @BindView(R.id.cv_findItem2)
        CardView cvFindItem2;

        ViewHolderSpecial(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}





















