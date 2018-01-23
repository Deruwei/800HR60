package com.hr.ui.ui.message.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.ui.message.activity.WebActivity;
import com.hr.ui.utils.AutolinkSpan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

/**
 *
 */
public class MyCompanyMessageAdapter extends RecyclerView.Adapter<MyCompanyMessageAdapter.ViewHolder> {
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

    public MyCompanyMessageAdapter(Context context) {
        this.context = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_invite, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if("1".equals(listBeans.get(position).getIs_email())) {
            viewHolder.tvItemInviteContent.setText(listBeans.get(position).getEmail_content());
            viewHolder.tvItemInviteContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    viewHolder.tvItemInviteContent.setTag(R.id.long_click,true);
                    return true;
                }
            });
            Spannable spannable = (Spannable)(viewHolder.tvItemInviteContent.getText());
            URLSpan[] spans = spannable.getSpans(0, spannable.length(), URLSpan.class);
            for (int i = 0; i < spans.length; i++) {
                String url = spans[i].getURL();
                int index = spannable.toString().indexOf(url);
                int end = index + url.length();
                if (index == -1) {
                    if (url.contains("http://")) {
                        url = url.replace("http://", "");
                    } else if (url.contains("https://")) {
                        url = url.replace("https://", "");
                    } else if (url.contains("rtsp://")) {
                        url = url.replace("rtsp://", "");
                    }
                    index = spannable.toString().indexOf(url);
                    end = index + url.length();
                }
                if (index != -1) {
                    spannable.removeSpan(spans[i]);
                    spannable.setSpan(new AutolinkSpan(spans[i].getURL()), index
                            , end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
        }else{
            viewHolder.tvItemInviteContent.setText(listBeans.get(position).getSms_content());
        }


        //Glide.with(context).load(listBeans.get(position).getPic_path()).centerCrop().into(viewHolder.ivFindIcon);
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
        @BindView(R.id.iv_itemInviteImage)
        ImageView ivItemInviteImage;
        @BindView(R.id.tv_itemInviteContent)
        TextView tvItemInviteContent;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}





















