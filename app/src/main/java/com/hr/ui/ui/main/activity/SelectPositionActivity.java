package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
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
import com.hr.ui.utils.ToastUitl;
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

public class SelectPositionActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.lv_right)
    ListView lvRight;
    @BindView(R.id.ll_selectedPosition)
    MyFlowLayout llSelectedPosition;
    @BindView(R.id.rl_selectData)
    RelativeLayout rlSelectData;
    @BindView(R.id.tv_selectPositionNum)
    TextView tvSelectPositionNum;
    @BindView(R.id.ll_selectPositionBottom)
    LinearLayout llSelectPositionBottom;
    @BindView(R.id.ll_industryTitle)
    LinearLayout llIndustryTitle;
    @BindView(R.id.view_industryBottom)
    View viewIndustryBottom;
    private List<CityBean> positonBeanList;
    private String industryId;
    private List<CityBean> positonLeftList, positionRightList;
    private MySelectPositionLeftAdapter leftAdapter;
    private MySelectPositionRightAdapter rightAdapter;
    private List<CityBean> selectPositionList = new ArrayList<>();
    private int sum;//选择数量

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String industryId, List<CityBean> selectPositionList) {
        Intent intent = new Intent(activity, SelectPositionActivity.class);
        intent.putExtra("industryId",industryId);
        intent.putExtra("selectPosition", (Serializable) selectPositionList);
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
        viewIndustryBottom.setVisibility(View.GONE);
        llIndustryTitle.setVisibility(View.GONE);
        selectPositionList = (List<CityBean>) getIntent().getSerializableExtra("selectPosition");
        industryId=getIntent().getStringExtra("industryId");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        positonBeanList = FromStringToArrayList.getInstance().getExpectPosition(industryId);
        //System.out.println(positonBeanList.toString());
        positonLeftList = new ArrayList<>();
        for (int i = 0; i < positonBeanList.size(); i++) {
            if (positonBeanList.get(i).getId().endsWith("000")) {
                positonLeftList.add(positonBeanList.get(i));
            }
        }
        if (selectPositionList != null && selectPositionList.size() != 0) {
            for (int i = 0; i < selectPositionList.size(); i++) {
                addView(selectPositionList.get(i));
            }
        }else{
            rlSelectData.setVisibility(View.GONE);
        }
        final Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (positonLeftList.get(position).isCheck() == false) {
                    for (int i = 0; i < positonLeftList.size(); i++) {
                        positonLeftList.get(i).setCheck(false);
                    }
                    positonLeftList.get(position).setCheck(true);
                } else {
                    positonLeftList.get(position).setCheck(false);
                }
                positionRightList = new ArrayList<>();
                for (int i = 0; i < positonBeanList.size(); i++) {
                    if (positonLeftList.get(position).getId().substring(0, 3).equals(positonBeanList.get(i).getId().substring(0, 3)) && !positonLeftList.get(position).getId().equals(positonBeanList.get(i).getId())) {
                        positonBeanList.get(i).setCheck(false);
                        positionRightList.add(positonBeanList.get(i));
                    }
                }
                if (selectPositionList != null && selectPositionList.size() != 0) {
                    for (int i = 0; i < positionRightList.size(); i++) {
                        for (int j = 0; j < selectPositionList.size(); j++) {
                            if (positionRightList.get(i).getId().equals(selectPositionList.get(j).getId())) {
                                positionRightList.get(i).setCheck(true);
                            }
                        }
                    }
                }
                Message message1 = Message.obtain();
                message1.what = 2;
                handler.sendMessage(message1);
                leftAdapter.notifyDataSetChanged();
            }
        });
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sum = selectPositionList.size();
                if (positionRightList.get(position).isCheck() == false) {
                    sum = selectPositionList.size();
                    if (sum == 0) {
                        rlSelectData.setVisibility(View.VISIBLE);
                    }
                    if (sum >= 5) {
                        ToastUitl.showShort("最多只能选择5个职位");
                        return;
                    } else {
                        selectPositionList.add(positionRightList.get(position));
                        addView(positionRightList.get(position));
                        positionRightList.get(position).setCheck(true);
                    }
                } else {

                    positionRightList.get(position).setCheck(false);
                    for (int i = 0; i < selectPositionList.size(); i++) {
                        if (positionRightList.get(position).getId().equals(selectPositionList.get(i).getId())) {
                            selectPositionList.remove(selectPositionList.get(i));
                        }
                    }
                    removeView(positionRightList.get(position));
                    setNum();
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
                finish();
                break;
            case R.id.tv_selectPositionOK:
                if (selectPositionList != null && selectPositionList.size() != 0) {
                    JobOrderActivity.instance.setPositionList(selectPositionList);
                    finish();
                } else {
                    ToastUitl.showShort("请选择期望职位");
                }
                break;
        }
    }

    /**
     * 添加已选职位视图
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
        final LinearLayout ll= (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.item_textview_selected, null, false);
        ll.setLayoutParams(params);
        TextView tv=ll.findViewById(R.id.item_select);
        tv.setText(cityBean.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPositionList.remove(cityBean);
                llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
                sum = selectPositionList.size();
                getRightDataFresh();
                if (sum == 0) {
                    rlSelectData.setVisibility(View.GONE);
                }
                setNum();
            }
        });
        ll.setTag(cityBean.getId());
        llSelectedPosition.addView(ll);
        setNum();
    }

    private void getRightDataFresh() {
        if (positionRightList != null && positionRightList.size() != 0) {
            for (int i = 0; i < positionRightList.size(); i++) {
                positionRightList.get(i).setCheck(false);
                for (int j = 0; j < selectPositionList.size(); j++) {
                    if (positionRightList.get(i).getId().equals(selectPositionList.get(j).getId())) {
                        positionRightList.get(i).setCheck(true);
                    }
                }
            }
            rightAdapter.notifyDataSetChanged();
        }
    }

    private void removeView(CityBean cityBean) {
        llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
        sum = selectPositionList.size();
        if (sum == 0) {
            rlSelectData.setVisibility(View.GONE);
        }
    }

    private void setNum() {
        sum = selectPositionList.size();
        tvSelectPositionNum.setText(sum + "");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    leftAdapter = new MySelectPositionLeftAdapter(positonLeftList);
                    lvLeft.setAdapter(leftAdapter);
                    break;
                case 2:
                    rightAdapter = new MySelectPositionRightAdapter(positionRightList);
                    lvRight.setAdapter(rightAdapter);
                    break;
            }
        }
    };
}
