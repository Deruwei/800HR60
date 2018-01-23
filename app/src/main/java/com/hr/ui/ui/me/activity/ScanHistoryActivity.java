package com.hr.ui.ui.me.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.ScanHistoryBean;
import com.hr.ui.db.ScanHistoryUtils;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.me.adapter.MyScanHistoryAdapter;
import com.hr.ui.ui.message.adapter.MyWhoSeeMeAdapter;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.view.XRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/17.
 */

public class ScanHistoryActivity extends BaseNoConnectNetworkAcitivty {
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
    private MyScanHistoryAdapter adapter;
    private List<ScanHistoryBean> scanHistoryBeanList=new ArrayList<>();
    private int page=1;
    private List<ScanHistoryBean> totalScanHistoryList=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_find;
    }
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, ScanHistoryActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public void initView() {
        scanHistoryBeanList=ScanHistoryUtils.getInstance().query(page);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.scan_history);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvToolbarSave.setVisibility(View.VISIBLE);
        tvToolbarSave.setText(R.string.clear);
        tvToolbarSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanHistoryUtils.getInstance().deleteAll();
                totalScanHistoryList.clear();
                adapter.notifyDataSetChanged();
            }
        });
        initRv();

    }

    private void initRv() {
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
        adapter=new MyScanHistoryAdapter(this);
        rvFind.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                            page = 1;
                            scanHistoryBeanList = ScanHistoryUtils.getInstance().query(page);
                            Message message = Message.obtain();
                            message.what = 0;
                            handler.sendMessage(message);
                        rvFind.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page++;
                        scanHistoryBeanList=ScanHistoryUtils.getInstance().query(page);
                        rvFind.loadMoreComplete();
                        Message message=Message.obtain();
                        message.what=1;
                        handler.sendMessage(message);
                    }
                }, 1000);
            }
        });
        Message message=Message.obtain();
        message.what=0;
        handler.sendMessage(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    if(scanHistoryBeanList!=null&&scanHistoryBeanList.size()!=0&&!"[]".equals(scanHistoryBeanList)) {
                        tvToolbarSave.setVisibility(View.VISIBLE);
                        adapter = new MyScanHistoryAdapter(ScanHistoryActivity.this);
                        totalScanHistoryList.clear();
                        totalScanHistoryList.addAll(scanHistoryBeanList);
                        adapter.setListBeans(totalScanHistoryList);
                        rvFind.setAdapter(adapter);
                        rvFind.setPullRefreshEnabled(true);
                    }else{
                        rvFind.setPullRefreshEnabled(false);
                        tvToolbarSave.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    if(scanHistoryBeanList!=null&&scanHistoryBeanList.size()!=0) {
                        totalScanHistoryList.addAll(scanHistoryBeanList);
                        adapter.notifyDataSetChanged();
                    }else{
                        rvFind.setNoMore(true);
                    }
                    break;

            }
            adapter.setClickCallBack(new MyScanHistoryAdapter.ItemClickCallBack() {
                @Override
                public void onItemClick(int pos) {
                    PositionPageActivity.startAction(ScanHistoryActivity.this,totalScanHistoryList.get(pos).getJobId());
                }
            });
        }
    };

}
