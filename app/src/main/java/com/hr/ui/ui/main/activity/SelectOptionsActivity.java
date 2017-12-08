package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGL10;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/11/28.
 */

public class SelectOptionsActivity extends BaseActivity {
    @BindView(R.id.iv_baseEditBack)
    ImageView ivBaseEditBack;
    @BindView(R.id.tv_baseEditTitle)
    TextView tvBaseEditTitle;
    @BindView(R.id.tv_baseEditSave)
    TextView tvBaseEditSave;
    @BindView(R.id.tv_selectIndustry)
    TextView tvSelectIndustry;
    @BindView(R.id.tv_selectTerritory)
    TextView tvSelectTerritory;
    @BindView(R.id.tv_selectJob)
    TextView tvSelectJob;
    @BindView(R.id.ll_selectJobOrder)
    LinearLayout llSelectJobOrder;
    @BindView(R.id.view_selectJobOrder)
    View viewSelectJobOrder;
    @BindView(R.id.ll_industryItems)
    LinearLayout llIndustryItems;
    @BindView(R.id.rl_industry)
    RelativeLayout rlIndustry;
    @BindView(R.id.gl_territoryItems)
    GridLayout glTerritoryItems;
    @BindView(R.id.rl_territory)
    RelativeLayout rlTerritory;
    @BindView(R.id.gl_jobItems)
    GridLayout glJobItems;
    @BindView(R.id.rl_job)
    RelativeLayout rlJob;
    private int num=2;//每行的个数
    private PopupWindow popupWindowIndustry,popupWindowTerritory,popupWindowJob;
    private boolean isShowIndustry,isShowTerritory,isShowJob;
    /** 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SelectOptionsActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_selectjoborder;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        addIndustryView(0);
        glTerritoryItems.setColumnCount(2);
        glTerritoryItems.setRowCount(2);
        glJobItems.setRowCount(2);
        glJobItems.setColumnCount(2);
        for(int i=0;i<4;i++){
                addTerritoryView(i);
                addJobView(i);

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_baseEditBack, R.id.tv_baseEditSave, R.id.tv_selectIndustry, R.id.tv_selectTerritory, R.id.tv_selectJob})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_baseEditBack:
                break;
            case R.id.tv_baseEditSave:
                break;
            case R.id.tv_selectIndustry:
                if(isShowIndustry==false) {
                    initIndustryPopupWindow();
                }else{
                    if(popupWindowIndustry!=null){
                        popupWindowIndustry.dismiss();
                    }
                }
                isShowIndustry=!isShowIndustry;
                break;
            case R.id.tv_selectTerritory:
                if (isShowTerritory==false){
                    initTerritoryPopupWindow();
                }else{
                    if(popupWindowTerritory!=null){
                        popupWindowTerritory.dismiss();
                    }
                }
                isShowTerritory=!isShowTerritory;
                break;
            case R.id.tv_selectJob:
                if(isShowJob==false){
                    initJobPopupWindow();
                } else{
                    if(popupWindowJob!=null){
                        popupWindowJob.dismiss();
                    }
                }
                isShowJob=!isShowJob;
                break;
        }
    }

    /**
     * 添加行业视图
     * @param i
     */
    private void addIndustryView(final int i){

        LinearLayout ll= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_industry,null,false);
        TextView tv_territory=ll.findViewById(R.id.tv_industryName);
        tv_territory.setText("你好"+i);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llIndustryItems.removeView(llIndustryItems.findViewWithTag(i));
                if(llIndustryItems.getChildCount()==0){
                    rlIndustry.setVisibility(View.GONE);
                }
            }
        });
        ll.setTag(i);
        llIndustryItems.addView(ll);
    }

    /**
     * 添加领域视图
     * @param i1
     */
    private void addTerritoryView(final int i1){
        LinearLayout linearLayout= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_territory_job,null,false);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        // 获取屏幕宽度
        int W = this.getWindowManager().getDefaultDisplay().getWidth();
        // 根据每行个数设置布局大小
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(W / num, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(5, 5, 5, 5);
        linearLayout.setGravity(Gravity.CENTER);

        TextView tv_territory=linearLayout.findViewById(R.id.tv_territoryOrJobName);
        tv_territory.setText("你好"+i1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glTerritoryItems.removeView(glTerritoryItems.findViewWithTag(i1));
                if(glTerritoryItems.getChildCount()==0){
                    rlTerritory.setVisibility(View.GONE);
                }
            }
        });
        linearLayout.setTag(i1);
        glTerritoryItems.addView(linearLayout);
    }

    /**
     * 添加职位视图
     * @param i1
     */
    private void addJobView(final int i1){
        LinearLayout ll= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_territory_job,null,false);
        ll.setOrientation(LinearLayout.VERTICAL);
        // 获取屏幕宽度
        int W = this.getWindowManager().getDefaultDisplay().getWidth();
        // 根据每行个数设置布局大小
        ll.setLayoutParams(new LinearLayout.LayoutParams(W / num, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setPadding(5, 5, 5, 5);
        ll.setGravity(Gravity.CENTER);
        TextView tv_territory=ll.findViewById(R.id.tv_territoryOrJobName);
        tv_territory.setText("你好"+i1);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glJobItems.removeView(glJobItems.findViewWithTag(i1));
                if(glJobItems.getChildCount()==0){
                    rlJob.setVisibility(View.GONE);
                }
            }
        });
        ll.setTag(i1);
        glJobItems.addView(ll);
    }

    private void initIndustryPopupWindow(){
        View viewIndustry=getLayoutInflater().inflate(R.layout.layout_chooseindustry,null);
        RecyclerView rvIndustry=viewIndustry.findViewById(R.id.rv_chooseIndustry);
        popupWindowIndustry= new PopupWindow(viewIndustry, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindowIndustry.setFocusable(false);
        popupWindowIndustry.setOutsideTouchable(true);
        popupWindowIndustry.showAsDropDown(llSelectJobOrder);
    }
    private void initTerritoryPopupWindow(){
        View viewTerritory=getLayoutInflater().inflate(R.layout.layout_chooseterritory,null);
        popupWindowTerritory= new PopupWindow(viewTerritory, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindowTerritory.setFocusable(false);
        popupWindowTerritory.setOutsideTouchable(true);
        popupWindowTerritory.showAsDropDown(llSelectJobOrder);
    }
    private void initJobPopupWindow(){
        View viewJob=getLayoutInflater().inflate(R.layout.layout_choosejob,null);
        popupWindowJob= new PopupWindow(viewJob, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindowJob.setFocusable(false);
        popupWindowJob.setOutsideTouchable(true);
        popupWindowJob.showAsDropDown(llSelectJobOrder);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        }
        return false;
    }

}
