package com.hr.ui.ui.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.bean.CollectionBean;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.presenter.MainPresenter;
import com.hr.ui.ui.me.presenter.CollectionPresenter;
import com.hr.ui.utils.Utils;
import com.loopeer.itemtouchhelperextension.Extension;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_RECYCLER_WIDTH = 1000;
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    public static final int ITEM_TYPE_ACTION_WIDTH_NO_SPRING = 1002;
    public static final int ITEM_TYPE_NO_SWIPE = 1003;
    private List<CollectionBean.FavouriteListBean> mDatas;
    private Context mContext;
    private int type;
    private CollectionPresenter presenter;
    private ItemTouchHelperExtension mItemTouchHelperExtension;

    public MainRecyclerAdapter(Context context, int type,CollectionPresenter presenter) {
        mContext = context;
        this.type = type;
        this.presenter=presenter;
        mDatas = new ArrayList<>();
    }

    public void setDatas(List<CollectionBean.FavouriteListBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
    }

    public void updateData(List<CollectionBean.FavouriteListBean> datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }

    public void setItemTouchHelperExtension(ItemTouchHelperExtension itemTouchHelperExtension) {
        mItemTouchHelperExtension = itemTouchHelperExtension;
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_item_main, parent, false);
        if (viewType == ITEM_TYPE_ACTION_WIDTH) return new ItemSwipeWithActionWidthViewHolder(view);
        if (viewType == ITEM_TYPE_NO_SWIPE) return new ItemNoSwipeViewHolder(view);
        if (viewType == ITEM_TYPE_RECYCLER_WIDTH) {
            view = getLayoutInflater().inflate(R.layout.list_item_with_single_delete, parent, false);
            return new ItemViewHolderWithRecyclerWidth(view);
        }
        return new ItemSwipeWithActionWidthNoSpringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ItemBaseViewHolder baseViewHolder = (ItemBaseViewHolder) holder;
        baseViewHolder.bind(mDatas.get(position));
        baseViewHolder.viewListMainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PositionPageActivity.startAction((Activity) mContext,mDatas.get(position).getJob_id());
            }
        });
        baseViewHolder.btnItemCollectionDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deliverCollection(mDatas.get(position).getJob_id());
            }
        });
        if (holder instanceof ItemViewHolderWithRecyclerWidth) {
            ItemViewHolderWithRecyclerWidth viewHolder = (ItemViewHolderWithRecyclerWidth) holder;
            viewHolder.mActionViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doDelete(holder.getAdapterPosition());
                }
            });
        } else if (holder instanceof ItemSwipeWithActionWidthViewHolder) {
            ItemSwipeWithActionWidthViewHolder viewHolder = (ItemSwipeWithActionWidthViewHolder) holder;
            viewHolder.mActionViewRefresh.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(mContext, "Refresh Click" + holder.getAdapterPosition()
                                    , Toast.LENGTH_SHORT).show();
                            mItemTouchHelperExtension.closeOpened();
                        }
                    }

            );
            viewHolder.mActionViewDelete.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            doDelete(holder.getAdapterPosition());
                        }
                    }

            );
        }
    }

    private void doDelete(int adapterPosition) {
        if (adapterPosition == mDatas.size()) {
            presenter.deleteCollection(mDatas.get(adapterPosition-1).getRecord_id(),mDatas.get(adapterPosition -1).getJob_id());
            mDatas.remove(adapterPosition - 1);
        } else {
            presenter.deleteCollection(mDatas.get(adapterPosition).getRecord_id(),mDatas.get(adapterPosition).getJob_id());
            mDatas.remove(adapterPosition);
        }

        notifyItemRemoved(adapterPosition);
    }

    public void move(int from, int to) {
        CollectionBean.FavouriteListBean prev = mDatas.remove(from);
        mDatas.add(to > from ? to - 1 : to, prev);
        notifyItemMoved(from, to);
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1) {
            return ITEM_TYPE_ACTION_WIDTH_NO_SPRING;
        }
        if (type == 2) {
            return ITEM_TYPE_RECYCLER_WIDTH;
        }
        if (type == 3) {
            return ITEM_TYPE_NO_SWIPE;
        }
        return ITEM_TYPE_ACTION_WIDTH;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_list_repo_action_delete)
        TextView viewListRepoActionDelete;
        @BindView(R.id.view_list_repo_action_container)
        LinearLayout viewListRepoActionContainer;
        @BindView(R.id.tv_itemCollectionJobName)
        TextView tvItemCollectionJobName;
        @BindView(R.id.tv_itemCollectionSalary)
        TextView tvItemCollectionSalary;
        @BindView(R.id.tv_itemCollectionPlace)
        TextView tvItemCollectionPlace;
        @BindView(R.id.tv_itemCollectionExp)
        TextView tvItemCollectionExp;
        @BindView(R.id.tv_itemCollectionTime)
        TextView tvItemCollectionTime;
        @BindView(R.id.tv_itemCollectionDegree)
        TextView tvItemCollectionDegree;
        @BindView(R.id.tv_itemCollectionCompanyName)
        TextView tvItemCollectionCompanyName;
        @BindView(R.id.btn_itemCollectionDeliver)
        Button btnItemCollectionDeliver;
        @BindView(R.id.view_list_main_content)
        LinearLayout viewListMainContent;
        public View mViewContent;
        public View mActionContainer;

        public ItemBaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mViewContent = itemView.findViewById(R.id.view_list_main_content);
            mActionContainer = itemView.findViewById(R.id.view_list_repo_action_container);
        }

        public void bind(CollectionBean.FavouriteListBean testModel) {
            tvItemCollectionSalary.setText(Utils.getSalary(testModel.getSalary()));
            tvItemCollectionDegree.setText(testModel.getStudy());
            tvItemCollectionExp.setText(testModel.getWorkyear());
            tvItemCollectionJobName.setText(testModel.getJob_name());
            tvItemCollectionPlace.setText(testModel.getWorkplace());
            tvItemCollectionCompanyName.setText(testModel.getEnterprise_name());
            tvItemCollectionTime.setText(Utils.getDateMonthAndDay(testModel.getFavourite_time()));
            if (testModel.getIs_apply() == 0) {
                btnItemCollectionDeliver.setEnabled(true);
                btnItemCollectionDeliver.setText(R.string.deliver);
            } else if (testModel.getIs_apply() == 1) {
                btnItemCollectionDeliver.setText(R.string.allReadyDeliver);
                btnItemCollectionDeliver.setEnabled(false);
            }
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mItemTouchHelperExtension.startDrag(ItemBaseViewHolder.this);
                    }
                    return true;
                }
            });
        }
    }


    class ItemViewHolderWithRecyclerWidth extends ItemBaseViewHolder implements Extension {

        View mActionViewDelete;

        public ItemViewHolderWithRecyclerWidth(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.view_list_repo_action_delete);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }

    class ItemSwipeWithActionWidthViewHolder extends ItemBaseViewHolder implements Extension {

        View mActionViewDelete;
        View mActionViewRefresh;

        public ItemSwipeWithActionWidthViewHolder(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.view_list_repo_action_delete);
            mActionViewRefresh = itemView.findViewById(R.id.view_list_repo_action_update);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }

    public class ItemSwipeWithActionWidthNoSpringViewHolder extends ItemSwipeWithActionWidthViewHolder implements Extension {

        public ItemSwipeWithActionWidthNoSpringViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public float getActionWidth() {
            return mActionContainer.getWidth();
        }
    }

    public class ItemNoSwipeViewHolder extends ItemBaseViewHolder {

        public ItemNoSwipeViewHolder(View itemView) {
            super(itemView);
        }
    }

}