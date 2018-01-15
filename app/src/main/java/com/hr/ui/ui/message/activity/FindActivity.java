package com.hr.ui.ui.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseActivity;
import com.hr.ui.bean.FindBean;
import com.hr.ui.ui.message.adapter.MyDeliverFeedbackAdapter;
import com.hr.ui.ui.message.adapter.MyFindAdapter;
import com.hr.ui.ui.message.contract.FindContract;
import com.hr.ui.ui.message.model.FindModel;
import com.hr.ui.ui.message.presenter.FindPresenter;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/15.
 */

public class FindActivity extends BaseActivity<FindPresenter, FindModel> implements FindContract.View {
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
    private MyFindAdapter adapter;
    private int page=1;
    private List<FindBean.ListBean> listBeanList=new ArrayList<>();
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, FindActivity.class);
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
    public int getLayoutId() {
        return R.layout.activity_find;
    }

    @Override
    public void initPresenter() {
       mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        mPresenter.getFindData(page);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.find);
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
        adapter=new MyFindAdapter(this);
        rvFind.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        page=1;
                       mPresenter.getFindData(page);
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
                       mPresenter.getFindData(page);
                        rvFind.loadMoreComplete();
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
    public void getFiindDataSuccess(List<FindBean.ListBean> listBeans) {
        if(listBeans!=null&&!"".equals(listBeans)&&!"[]".equals(listBeans)&&listBeans.size()!=0){
            if(page==1){
                listBeanList.clear();
                listBeanList.addAll(listBeans);
                adapter.setListBeans(listBeanList);
                rvFind.setAdapter(adapter);
            }else{
                listBeanList.addAll(listBeans);
                adapter.notifyDataSetChanged();
            }
        }else{
            rvFind.setNoMore(true);
        }
        adapter.setClickCallBack(new MyFindAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                if (listBeanList.get(pos).getTopic_url() != null && !"".equals(listBeanList.get(pos).getTopic_url())){
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(listBeanList.get(pos).getTopic_url());
                    intent.setData(content_url);
                    startActivity(intent);
                }
            }
        });
    }
}
