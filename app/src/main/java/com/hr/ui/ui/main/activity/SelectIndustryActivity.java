package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.adapter.SelectIndustryAdapter;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wdr on 2018/1/8.
 */

public class SelectIndustryActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_selectIndustry)
    RecyclerView rvSelectIndustry;
    private List<CityBean> selectIndustryList;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, List<CityBean> selectIndustryList) {
        Intent intent = new Intent(activity, SelectIndustryActivity.class);
        intent.putExtra("selectIndustry", (Serializable) selectIndustryList);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_selectindustry;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        selectIndustryList= (List<CityBean>) getIntent().getSerializableExtra("selectIndustry");
        toolBar.setTitle(R.string.selectIndustry);
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rvSelectIndustry.setLayoutManager(manager);
        final List<CityBean> industryList = FromStringToArrayList.getInstance().getIndustryList();
        if (selectIndustryList!= null && !"".equals(selectIndustryList)) {
            for(int j=0;j<selectIndustryList.size();j++){
                for (int i = 0; i < industryList.size(); i++) {
                    if (selectIndustryList.get(j).getId().equals(industryList.get(i).getId())) {
                        industryList.get(i).setCheck(true);
                        continue;
                    }
                }
            }
        }
        final SelectIndustryAdapter selectIndustryAdapter;
        //Log.i("你好2",industryList.toString());
        selectIndustryAdapter = new SelectIndustryAdapter(industryList,SelectIndustryActivity.this,1 );
        rvSelectIndustry.setAdapter(selectIndustryAdapter);
        selectIndustryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                selectIndustryList.clear();
              if(industryList.get(position).isCheck()==true){
                  selectIndustryList.remove(industryList.get(position));
                  industryList.get(position).setCheck(false);
              }else{
                  industryList.get(position).setCheck(true);
                  selectIndustryList.add(industryList.get(position));
                  JobSerchActivity.instance.setIndustry(selectIndustryList);
                  finish();
              }
              selectIndustryAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
