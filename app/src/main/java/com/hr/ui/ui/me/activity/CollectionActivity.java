package com.hr.ui.ui.me.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CollectionBean;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.me.adapter.MainRecyclerAdapter;
import com.hr.ui.ui.me.adapter.MyCollectionAdapter;
import com.hr.ui.ui.me.contract.CollectionContract;
import com.hr.ui.ui.me.model.CollectionModel;
import com.hr.ui.ui.me.presenter.CollectionPresenter;
import com.hr.ui.utils.ItemTouchHelperCallback;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.RecycleItemTouchHelper;
import com.hr.ui.utils.SwipeItemLayout;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.SwipeLayoutManager;
import com.hr.ui.view.XRecyclerView;
import com.loopeer.itemtouchhelperextension.ItemTouchHelperExtension;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/12.
 */

public class CollectionActivity extends BaseActivity<CollectionPresenter, CollectionModel> implements CollectionContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_collection)
    XRecyclerView rvCollection;
    private Button btn;
    private int page=1;
    private MyCollectionAdapter adapter;
    private List<CollectionBean.FavouriteListBean> favouriteListBeanList=new ArrayList<>();
    public ItemTouchHelperExtension mItemTouchHelper;
    public ItemTouchHelperExtension.Callback mCallback;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, CollectionActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void getCollectionSuccess(final List<CollectionBean.FavouriteListBean> favouriteListBeanList1) {
        if(favouriteListBeanList1!=null&&favouriteListBeanList1.size()!=0) {
            if (page == 1) {
                favouriteListBeanList.clear();
                favouriteListBeanList.addAll(favouriteListBeanList1);
                adapter.setFavouriteListBeanList(favouriteListBeanList);
                rvCollection.setAdapter(adapter);
            } else {
                favouriteListBeanList.addAll(favouriteListBeanList1);
                adapter.notifyDataSetChanged();
            }
        }else{
            rvCollection.setNoMore(true);
        }
        adapter.setClickCallBack(new MyCollectionAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                PositionPageActivity.startAction(CollectionActivity.this,favouriteListBeanList.get(pos).getJob_id());
            }
        });
        adapter.setOnViewClick(new MyCollectionAdapter.OnViewClick() {
            @Override
            public void onViewClick(Button btn1, int position) {
                btn=btn1;
                mPresenter.deliverCollection(favouriteListBeanList.get(position).getJob_id());
            }
        });
        adapter.setOnDeleteClick(new MyCollectionAdapter.OnDeleteClick() {
            @Override
            public void onViewClick(View view, int position) {
                mPresenter.deleteCollection(favouriteListBeanList.get(position).getRecord_id(),favouriteListBeanList.get(position).getJob_id());
                favouriteListBeanList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void deleteCollectionSuccess() {
        ToastUitl.showShort("取消收藏成功");
    }

    @Override
    public void deliverCollection() {
        if(btn!=null){
            btn.setClickable(false);
            btn.setText(R.string.allReadyDeliver);
            btn.setTextColor(ContextCompat.getColor(CollectionActivity.this,R.color.color_999));
            btn.setBackgroundResource(R.drawable.edit_bg_999);
        }
        ToastUitl.showShort("投递成功");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        mPresenter.getCollectionInfo(page);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.collectionPosition);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*tvToolbarSave.setVisibility(View.VISIBLE);*/
       /* tvToolbarSave.setText(R.string.edit);
        tvToolbarSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCollection.setLayoutManager(linearLayoutManager);
        rvCollection.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvCollection.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        adapter=new MyCollectionAdapter();
        rvCollection.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mPresenter.getCollectionInfo(1);
                        adapter.notifyDataSetChanged();
                        rvCollection.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        mPresenter.getCollectionInfo(page);
                        rvCollection.loadMoreComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
