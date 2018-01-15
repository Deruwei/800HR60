package com.hr.ui.ui.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.ui.job.activity.CompanyPageActivity;
import com.hr.ui.ui.message.adapter.MyFindAdapter;
import com.hr.ui.ui.message.adapter.MyWhoSeeMeAdapter;
import com.hr.ui.ui.message.contract.WhoSeeMeContract;
import com.hr.ui.ui.message.model.WhoSeeMeModel;
import com.hr.ui.ui.message.presenter.WhoSeeMePresenter;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.RegularExpression;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/15.
 */

public class WhoSeeMeActivity extends BaseActivity<WhoSeeMePresenter, WhoSeeMeModel> implements WhoSeeMeContract.View {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_find)
    XRecyclerView rvFind;
    private int page = 1;
    private MyWhoSeeMeAdapter adapter;
    private List<WhoSeeMeBean.BrowsedListBean> browsedListBeanList=new ArrayList<>();
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, WhoSeeMeActivity.class);
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
    public void getWhoSeeMeSuccess(final List<WhoSeeMeBean.BrowsedListBean> browsedListBeans) {
        if(browsedListBeans!=null&&browsedListBeans.size()!=0){
            if(page==1){
                browsedListBeanList.clear();
                browsedListBeanList.addAll(browsedListBeans);
                adapter.setListBeans(browsedListBeanList);
                rvFind.setAdapter(adapter);
            }else{
                browsedListBeanList.addAll(browsedListBeanList);
                adapter.notifyDataSetChanged();
            }
        }else{
            rvFind.setNoMore(true);
        }
        adapter.setClickCallBack(new MyWhoSeeMeAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                CompanyPageActivity.startAction(WhoSeeMeActivity.this,browsedListBeanList.get(pos).getEnterprise_id());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_find;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mPresenter.getWhoSeeMeData(page);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.whoSeeMe);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFind.setLayoutManager(linearLayoutManager);
        /*Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
        rvCollection.addItemDecoration(rvCollection.new DividerItemDecoration(dividerDrawable));*/
        rvFind.setRefreshProgressStyle(ProgressStyle.LineScaleParty);
        rvFind.setNestedScrollingEnabled(false);
        rvFind.setLoadingMoreProgressStyle(ProgressStyle.BallTrianglePath);
        rvFind.setArrowImageView(R.drawable.iconfont_downgrey);
        adapter=new MyWhoSeeMeAdapter(this);
        rvFind.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        page=1;
                        mPresenter.getWhoSeeMeData(page);
                        adapter.notifyDataSetChanged();
                        rvFind.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        mPresenter.getWhoSeeMeData(page);
                        rvFind.loadMoreComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
    }

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
