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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.ScanHistoryBean;
import com.hr.ui.db.ScanHistoryUtils;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.ui.me.adapter.MyScanHistoryAdapter;
import com.hr.ui.utils.ProgressStyle;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.view.MyDialog;
import com.hr.ui.view.XRecyclerView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
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
    private MyScanHistoryAdapter adapter;
    private List<ScanHistoryBean> scanHistoryBeanList = new ArrayList<>();
    private int page = 1;
    private List<ScanHistoryBean> totalScanHistoryList = new ArrayList<>();
    private MyDialog dialog;

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
        scanHistoryBeanList = ScanHistoryUtils.getInstance().query(page);
        setSupportActionBar(toolBar);
        MobclickAgent.onEvent(this,"v6_scan_history");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        ivNoDataSearch.setVisibility(View.GONE);
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
                dialog = new MyDialog(ScanHistoryActivity.this, 2);
                dialog.setMessage(getString(R.string.sureDeleteHistory));
                dialog.setNoOnclickListener(getString(R.string.cancel), new MyDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.setYesOnclickListener(getString(R.string.sure), new MyDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        ScanHistoryUtils.getInstance().deleteAll();
                        totalScanHistoryList.clear();
                        adapter.notifyDataSetChanged();
                        rvFind.setVisibility(View.GONE);
                        rlEmptyView.setVisibility(View.VISIBLE);
                        tvToolbarSave.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        initRv();

    }

    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFind.setLayoutManager(linearLayoutManager);
        /*Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
        rvCollection.addItemDecoration(rvCollection.new DividerItemDecoration(dividerDrawable));*/
        rvFind.setRefreshProgressStyle(ProgressStyle.BallPulse);
        rvFind.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        rvFind.setNestedScrollingEnabled(false);
        adapter = new MyScanHistoryAdapter(this);
        rvFind.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
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
                        scanHistoryBeanList = ScanHistoryUtils.getInstance().query(page);
                        rvFind.loadMoreComplete();
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }, 1000);
            }
        });
        Message message = Message.obtain();
        message.what = 0;
        handler.sendMessage(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (scanHistoryBeanList != null && scanHistoryBeanList.size() != 0 && !"[]".equals(scanHistoryBeanList)) {
                        tvToolbarSave.setVisibility(View.VISIBLE);
                        rvFind.setVisibility(View.VISIBLE);
                        rlEmptyView.setVisibility(View.GONE);
                        adapter = new MyScanHistoryAdapter(ScanHistoryActivity.this);
                        totalScanHistoryList.clear();
                        totalScanHistoryList.addAll(scanHistoryBeanList);
                        adapter.setListBeans(totalScanHistoryList);
                        rvFind.setAdapter(adapter);
                        rvFind.setPullRefreshEnabled(true);
                    } else {
                        rvFind.setVisibility(View.GONE);
                        rlEmptyView.setVisibility(View.VISIBLE);
                        rvFind.setPullRefreshEnabled(false);
                        tvToolbarSave.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    if (scanHistoryBeanList != null && scanHistoryBeanList.size() != 0) {
                        totalScanHistoryList.addAll(scanHistoryBeanList);
                        adapter.notifyDataSetChanged();
                    } else {
                        rvFind.setNoMore(true);
                    }
                    break;

            }
            adapter.setClickCallBack(new MyScanHistoryAdapter.ItemClickCallBack() {
                @Override
                public void onItemClick(int pos) {
                    if ("1".equals(totalScanHistoryList.get(pos).getIs_expect())) {
                        ToastUitl.showShort(R.string.error_401);
                    } else {
                        PositionPageActivity.startAction(ScanHistoryActivity.this, totalScanHistoryList.get(pos).getJobId());
                    }
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
