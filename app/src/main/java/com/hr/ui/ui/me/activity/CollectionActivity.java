package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.CollectionBean;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.main.activity.MainActivity;
import com.hr.ui.ui.main.fragment.ResumeFragment;
import com.hr.ui.ui.me.adapter.MyCollectionAdapter;
import com.hr.ui.ui.me.contract.CollectionContract;
import com.hr.ui.ui.me.model.CollectionModel;
import com.hr.ui.ui.me.presenter.CollectionPresenter;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.SwipeItemLayout;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.view.MyRecommendDialog;
import com.hr.ui.view.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

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
    @BindView(R.id.iv_noContent)
    ImageView ivNoContent;
    @BindView(R.id.tv_noData)
    TextView tvNoData;
    @BindView(R.id.iv_noDataSearchIcon)
    ImageView ivNoDataSearchIcon;
    @BindView(R.id.iv_noDataSearch)
    RelativeLayout ivNoDataSearch;
    @BindView(R.id.rl_emptyView)
    RelativeLayout rlEmptyView;
    private Button btn;
    private int page = 1;
    private MyCollectionAdapter adapter;
    private List<CollectionBean.FavouriteListBean> favouriteListBeanList = new ArrayList<>();
    private MyRecommendDialog dialog;
    private int position;
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
        //Log.i("到这里了",favouriteListBeanList1.toString());
        if (favouriteListBeanList1 != null && !"".equals(favouriteListBeanList1)&&favouriteListBeanList1.size() != 0) {
            if (page == 1) {
                adapter=new MyCollectionAdapter();
                favouriteListBeanList.clear();
                favouriteListBeanList.addAll(favouriteListBeanList1);
                adapter.setFavouriteListBeanList(favouriteListBeanList);
                rvCollection.setAdapter(adapter);
            } else {
                favouriteListBeanList.addAll(favouriteListBeanList1);
                adapter.notifyDataSetChanged();
            }
        } else {
            if(page==1){
                rlEmptyView.setVisibility(View.VISIBLE);
                ivNoDataSearch.setVisibility(View.GONE);
            }else {
                rvCollection.setNoMore(true);
            }
        }
        MobclickAgent.onEvent(this,"v6_scan_collection");
        adapter.setClickCallBack(new MyCollectionAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                if(favouriteListBeanList.get(pos).getIs_expire()==1){
                    ToastUitl.showShort(R.string.error_401);
                    return;
                }else {
                    PositionPageActivity.startAction(CollectionActivity.this, favouriteListBeanList.get(pos).getJob_id());
                }
            }
        });
        adapter.setOnViewClick(new MyCollectionAdapter.OnViewClick() {
            @Override
            public void onViewClick(Button btn1, int position) {
                btn = btn1;
                mPresenter.deliverCollection(favouriteListBeanList.get(position).getJob_id());
            }
        });
        adapter.setOnDeleteClick(new MyCollectionAdapter.OnDeleteClick() {
            @Override
            public void onViewClick(View view, int position1) {
                    position=position1;
                    mPresenter.deleteCollection(favouriteListBeanList.get(position).getRecord_id(), favouriteListBeanList.get(position).getJob_id());
                    favouriteListBeanList.remove(position);
                    adapter.notifyDataSetChanged();
                    if (favouriteListBeanList.size() == 0) {
                        rlEmptyView.setVisibility(View.VISIBLE);
                        ivNoDataSearch.setVisibility(View.GONE);
                    }
            }
        });
        rvCollection.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    @Override
    public void deleteCollectionSuccess() {
        ToastUitl.showShort("取消收藏成功");
    }

    @Override
    public void deliverCollection() {
        MobclickAgent.onEvent(this,"v6_resume_deliver");
        if (btn != null) {
            btn.setClickable(false);
            btn.setText(R.string.allReadyDeliver);
            btn.setTextColor(ContextCompat.getColor(CollectionActivity.this, R.color.color_999));
            btn.setBackgroundResource(R.drawable.edit_bg_999);
        }
        ToastUitl.showShort("投递成功");
    }

    @Override
    public void goToCompleteResume(int error) {
        if(error==413||error==417){
            //ToastUitl.showShort(errorCode+"");
            setPopupwindow(2);
        }else{
            //ToastUitl.showShort(errorCode+"");
            setPopupwindow(1);
        }
    }
    private void setPopupwindow(int type){
        dialog=new MyRecommendDialog(this);
        if(type==1) {
            dialog.setTitle(getString(R.string.resumeNoComplete));
            dialog.setMessage(getString(R.string.resumeNoCompleteContent));
            dialog.setNoOnclickListener(getString(R.string.known), new MyRecommendDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                }
            });
            dialog.setYesOnclickListener(getString(R.string.goNow), new MyRecommendDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
                    finish();
                    MainActivity.instance.rbResume1.setChecked(true);
                    ResumeFragment.instance.refresh();
                }
            });
        }else{
            dialog.setTitle(getString(R.string.noIndustry1)+ FromStringToArrayList.getInstance().getIndustryName(favouriteListBeanList.get(position).getIndustry())+"行业"+getString(R.string.noIndustry2));
            dialog.setMessage(getString(R.string.noJobOrderContent1)+FromStringToArrayList.getInstance().getIndustryName(favouriteListBeanList.get(position).getIndustry())+"行业"+getString(R.string.noJobOrderContent2));
            dialog.setNoOnclickListener(getString(R.string.known), new MyRecommendDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    dialog.dismiss();
                }
            });
            dialog.setYesOnclickListener(getString(R.string.goNow), new MyRecommendDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {
                    dialog.dismiss();
                    ResumeJobOrderActivity.startAction(CollectionActivity.this);
                }
            });
        }
        dialog.show();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.getCollectionInfo(page);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        rvCollection.setPullRefreshEnabled(false);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCollection.setLayoutManager(linearLayoutManager);
        rvCollection.setRefreshProgressStyle(ProgressStyle.BallPulse);
        rvCollection.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        adapter = new MyCollectionAdapter();
        rvCollection.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
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

    @Override
    protected void onDestroy() {
        if(dialog!=null){
            dialog.dismiss();
        }
        super.onDestroy();
    }
}
