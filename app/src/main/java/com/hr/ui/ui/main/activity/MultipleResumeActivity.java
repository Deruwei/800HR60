package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.AppManager;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.ResumeData;
import com.hr.ui.db.ResumeDataUtils;
import com.hr.ui.ui.main.adapter.MultipleResumeAdapter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.utils.recyclerviewutils.SpacesItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2017/12/11.
 */

public class MultipleResumeActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_discoveryMultipleResumes)
    RecyclerView rvDiscoveryMultipleResumes;
    private List<ResumeData> resumeDataList;
    private MultipleResumeAdapter multipleResumeAdapter;
    private int userId;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,int userId) {
        Intent intent = new Intent(activity, MultipleResumeActivity.class);
        intent.putExtra("userId",userId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_discoverymultipleresumes;
    }

    @Override
    public void initView() {
        userId=getIntent().getIntExtra("userId",0);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                MainActivity.startAction(MultipleResumeActivity.this,userId);
                AppManager.getAppManager().finishAllActivity();
            }
        });
        GridLayoutManager linearLayoutManager=new GridLayoutManager(this,2);
        rvDiscoveryMultipleResumes.setLayoutManager(linearLayoutManager);
        SpacesItemDecoration decoration=new SpacesItemDecoration(30);
        rvDiscoveryMultipleResumes.addItemDecoration(decoration);
        resumeDataList= ResumeDataUtils.queryAll();
        multipleResumeAdapter=new MultipleResumeAdapter(this,resumeDataList);
        rvDiscoveryMultipleResumes.setAdapter(multipleResumeAdapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            MainActivity.startAction(MultipleResumeActivity.this,userId);
            AppManager.getAppManager().finishAllActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
