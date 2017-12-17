package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.adapter.MySelectPositionLeftAdapter;
import com.hr.ui.ui.main.adapter.MySelectPositionRightAdapter;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.view.MyFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wdr on 2017/12/15.
 */

public class SelectFunctionActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.lv_right)
    ListView lvRight;
    @BindView(R.id.tv_selectPositionNum)
    TextView tvSelectPositionNum;
    @BindView(R.id.ll_selectedPosition)
    MyFlowLayout llSelectedPosition;
    @BindView(R.id.rl_selectData)
    RelativeLayout rlSelectData;
    private List<CityBean> industryList;
    private List<CityBean> functionList;
    private MySelectPositionLeftAdapter leftAdapter;
    private MySelectPositionRightAdapter rightAdapter;
    private String industryId;
    private List<CityBean> selectFunctionList=new ArrayList<>();
    private int sum;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String industryId, List<CityBean> selectFunctionList) {
        Intent intent = new Intent(activity, SelectFunctionActivity.class);
        intent.putExtra("selectFunction", (Serializable) selectFunctionList);
        intent.putExtra("industryId",industryId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public int getLayoutId() {
        return R.layout.acticity_selectposition;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        industryId=getIntent().getStringExtra("industryId");
        selectFunctionList= (List<CityBean>) getIntent().getSerializableExtra("selectFunction");
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.expectedPosition);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        industryList=FromStringToArrayList.getInstance().getIndustryList();
        final Message message= Message.obtain();
        message.what=1;
        handler.sendMessage(message);
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                industryId=industryList.get(i).getId();
                functionList=FromStringToArrayList.getInstance().getExpectField(industryId);
                for(int j=0;j<industryList.size();j++){
                        industryList.get(j).setCheck(false);
                }
                if(selectFunctionList!=null&&selectFunctionList.size()!=0) {
                    for (int j = 0; j < functionList.size(); j++) {
                        for (int k = 0; k < selectFunctionList.size(); k++) {
                            if (functionList.get(j).getId().equals(selectFunctionList.get(k).getId())) {
                                functionList.get(j).setCheck(true);
                            }
                        }
                    }
                }
                industryList.get(i).setCheck(true);
                leftAdapter.notifyDataSetChanged();
                Message message1=new Message();
                message1.what=2;
                handler.sendMessage(message1);
            }
        });
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(functionList.get(i).isCheck()==true){
                    functionList.get(i).setCheck(false);
                    for(int j=0;j<selectFunctionList.size();j++){
                        if(selectFunctionList.get(j).getId().equals(functionList.get(i).getId())){
                            selectFunctionList.remove(selectFunctionList.get(j));
                        }
                    }
                    removeView(functionList.get(i));
                }else{
                    functionList.get(i).setCheck(true);
                    if(selectFunctionList!=null&&selectFunctionList.size()!=0) {
                        for (int j = 0; j < selectFunctionList.size(); j++) {
                            if (!selectFunctionList.get(j).getId().substring(0,3).equals(functionList.get(i).getId().substring(0,3))) {
                                removeView(selectFunctionList.get(j));
                                if(selectFunctionList.size()-1==j) {
                                    selectFunctionList.clear();
                                }
                            }
                        }
                    }
                    addView(functionList.get(i));
                    selectFunctionList.add(functionList.get(i));
                }
                rightAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_selectPositionCancel, R.id.tv_selectPositionOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_selectPositionCancel:
                break;
            case R.id.tv_selectPositionOK:
                break;
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    leftAdapter=new MySelectPositionLeftAdapter(industryList);
                    lvLeft.setAdapter(leftAdapter);
                    break;
                case 2:
                    rightAdapter=new MySelectPositionRightAdapter(functionList);
                    lvRight.setAdapter(rightAdapter);
                    break;
            }
        }
    };
    /**
     * 添加已选领域视图
     *
     * @param cityBean
     */
    private void addView(final CityBean cityBean) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 15;
        params.topMargin = 12;
        params.bottomMargin = 12;
        params.rightMargin = 15;
        final TextView tv = (TextView) LayoutInflater.from(this).inflate(
                R.layout.item_textview_selected, null, false);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(6, 4, 6, 4);
        tv.setText(cityBean.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFunctionList.remove(cityBean);
                llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
                sum = selectFunctionList.size();
               /* getRightDataFresh();*/
                if (sum == 0) {
                    rlSelectData.setVisibility(View.GONE);
                }
                setNum();
            }
        });
        tv.setTag(cityBean.getId());
        llSelectedPosition.addView(tv);
        setNum();
    }

   /* private void getRightDataFresh() {
        if(positionRightList!=null&&positionRightList.size()!=0) {
            for (int i = 0; i < positionRightList.size(); i++) {
                positionRightList.get(i).setCheck(false);
                for (int j = 0; j < selectPositionList.size(); j++) {
                    if (positionRightList.get(i).equals(selectPositionList.get(j))) {
                        positionRightList.get(i).setCheck(true);
                    }
                }
            }
            rightAdapter.notifyDataSetChanged();
        }
    }*/
    private void setNum() {
        sum = selectFunctionList.size();
        tvSelectPositionNum.setText(sum+"");
    }
    private void removeView(CityBean cityBean) {
        llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
        sum=selectFunctionList.size();
        if (sum == 0) {
            rlSelectData.setVisibility(View.GONE);
        }
    }
}
