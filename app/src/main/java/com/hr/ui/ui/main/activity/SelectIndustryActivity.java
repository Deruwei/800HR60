package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.adapter.MySelectFunctionLeftAdapter;
import com.hr.ui.ui.main.adapter.MySelectPositionRightAdapter;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.view.MyFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.view_industry)
    View viewIndustry;
    @BindView(R.id.ll_industryTitle)
    LinearLayout llIndustryTitle;
    @BindView(R.id.view_industryBottom)
    View viewIndustryBottom;
    @BindView(R.id.lv_left)
    ListView lvLeft;
    @BindView(R.id.lv_right)
    ListView lvRight;
    @BindView(R.id.rl_noFunction)
    RelativeLayout rlNoFunction;
    @BindView(R.id.tv_selectPositionTag)
    TextView tvSelectPositionTag;
    @BindView(R.id.tv_selectPositionNum)
    TextView tvSelectPositionNum;
    @BindView(R.id.tv_selectPositionRightTag)
    TextView tvSelectPositionRightTag;
    @BindView(R.id.rl_selectedPositionTitle)
    RelativeLayout rlSelectedPositionTitle;
    @BindView(R.id.ll_selectedPosition)
    MyFlowLayout llSelectedPosition;
    @BindView(R.id.rl_selectData)
    RelativeLayout rlSelectData;
    @BindView(R.id.tv_selectPositionCancel)
    TextView tvSelectPositionCancel;
    @BindView(R.id.tv_selectPositionOK)
    TextView tvSelectPositionOK;
    @BindView(R.id.ll_selectPositionBottom)
    LinearLayout llSelectPositionBottom;
    private List<CityBean> industryList;
    private List<CityBean> functionList;
    private MySelectFunctionLeftAdapter leftAdapter;
    private MySelectPositionRightAdapter rightAdapter;
    private String industryId;
    private List<CityBean> selectFunctionList = new ArrayList<>();
    private List<CityBean> selectIndustryList=new ArrayList<>();
    private int sum;
    private int currentPosition;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, List<CityBean> selectIndustryList,String tag) {
        Intent intent = new Intent(activity, SelectIndustryActivity.class);
        intent.putExtra("selectIndustry", (Serializable) selectIndustryList);
        intent.putExtra("tag",tag);
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
        selectIndustryList = (List<CityBean>) getIntent().getSerializableExtra("selectIndustry");
        tvToolbarTitle.setText(R.string.selectIndustry);
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        industryList = FromStringToArrayList.getInstance().getIndustryList();
        //显示传过来的行业
        if (selectIndustryList != null && !"".equals(selectIndustryList) && selectIndustryList.size() != 0) {
            for (int i = 0; i < industryList.size(); i++) {
                for (int j = 0; j < selectIndustryList.size(); j++) {
                    if (industryList.get(i).getId().equals(selectIndustryList.get(j).getId())) {
                        if (i >= 5) {
                            industryList.get(i).setCheck(true);
                        }
                        currentPosition = i;
                    }
                }
            }
            if (currentPosition < 5) {
                for (int i = 0; i < 5; i++) {
                    industryList.get(i).setCheck(false);
                }
                industryList.get(currentPosition).setCheck(true);
            }
            functionList = FromStringToArrayList.getInstance().getExpectField(industryList.get(currentPosition).getId());
            if (selectFunctionList != null && selectFunctionList.size() != 0) {
                for (int j = 0; j < functionList.size(); j++) {
                    for (int k = 0; k < selectFunctionList.size(); k++) {
                        if (functionList.get(j).getId().equals(selectFunctionList.get(k).getId())) {
                            functionList.get(j).setCheck(true);
                        }

                    }
                }
                for (int j = 0; j < selectFunctionList.size(); j++) {
                    for (int k = 0; k < industryList.size(); k++) {
                        if (selectFunctionList.get(j).getId().substring(0, 2).equals(industryList.get(k).getId())) {
                            addView(selectFunctionList.get(j), industryList.get(k).getId());
                        }
                    }
                }
                for (int i = 5; i < industryList.size(); i++) {
                    for (int j = 0; j < selectIndustryList.size(); j++) {
                        if (selectIndustryList.get(j).getId().equals(industryList.get(i).getId())) {
                            addView(selectIndustryList.get(j), selectIndustryList.get(j).getId());
                        }
                    }
                }
            } else {
                if (selectIndustryList != null && selectIndustryList.size() != 0) {
                    for (int i = 5; i < industryList.size(); i++) {
                        for (int j = 0; j < selectIndustryList.size(); j++) {
                            if (selectIndustryList.get(j).getId().equals(industryList.get(i).getId())) {
                                addView(selectIndustryList.get(j), selectIndustryList.get(j).getId());
                            }
                        }
                    }
                } else {
                    rlSelectData.setVisibility(View.GONE);
                }
            }
            Message message1 = new Message();
            message1.what = 2;
            handler.sendMessage(message1);
        }
        Message message = Message.obtain();
        message.what = 1;
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
                case 1:
                    leftAdapter = new MySelectFunctionLeftAdapter(industryList);
                    lvLeft.setAdapter(leftAdapter);
                    lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            currentPosition = i;
                            // Log.i("行业的Id",industryId);
                            functionList = FromStringToArrayList.getInstance().getExpectField(industryList.get(i).getId());
                            if (i < 5) {
                                lvRight.setVisibility(View.VISIBLE);
                                rlNoFunction.setVisibility(View.GONE);
                                if (selectFunctionList != null && selectFunctionList.size() != 0) {
                                    for (int j = 0; j < functionList.size(); j++) {
                                        for (int k = 0; k < selectFunctionList.size(); k++) {
                                            if (functionList.get(j).getId().equals(selectFunctionList.get(k).getId())) {
                                                functionList.get(j).setCheck(true);
                                            }
                                        }
                                    }
                                }
                                for (int j = 0; j < 5; j++) {
                                    industryList.get(j).setCheck(false);
                                }
                                industryList.get(i).setCheck(true);
                            } else {
                                lvRight.setVisibility(View.GONE);
                                rlNoFunction.setVisibility(View.VISIBLE);
                                for (int j = 0; j < 5; j++) {
                                    industryList.get(j).setCheck(false);
                                }
                                setNum();
                                if (industryList.get(i).isCheck() == false) {
                                    if (sum >= 5) {
                                        ToastUitl.showShort("最多只能选择五个领域");
                                    } else {
                                        industryList.get(i).setCheck(true);
                                        selectIndustryList.add(industryList.get(i));
                                        addView(industryList.get(i), industryList.get(i).getId());
                                    }
                                } else {
                                    for (int j = 0; j < selectIndustryList.size(); j++) {
                                        if (selectIndustryList.get(j).getId().equals(industryList.get(i).getId())) {
                                            selectIndustryList.remove(j);
                                        }

                                    }
                                    industryList.get(i).setCheck(false);
                                    removeView(industryList.get(i));
                                }
                            }
                            leftAdapter.notifyDataSetChanged();
                            Message message1 = new Message();
                            message1.what = 2;
                            handler.sendMessage(message1);
                        }
                    });
                    break;
                case 2:
                    rightAdapter = new MySelectPositionRightAdapter(functionList);
                    lvRight.setAdapter(rightAdapter);
                    lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (functionList.get(i).isCheck() == true) {
                                functionList.get(i).setCheck(false);
                                for (int j = 0; j < selectFunctionList.size(); j++) {
                                    if (selectFunctionList.get(j).getId().equals(functionList.get(i).getId())) {
                                        selectFunctionList.remove(selectFunctionList.get(j));
                                    }
                                }
                                removeView(functionList.get(i));
                            } else {
                                if (sum == 0) {
                                    rlSelectData.setVisibility(View.VISIBLE);
                                }
                               /* if (selectFunctionList != null && selectFunctionList.size() != 0) {
                                    for (int j = 0; j < selectFunctionList.size(); j++) {
                                        if (!selectFunctionList.get(j).getId().substring(0, 2).equals(functionList.get(i).getId().substring(0, 2))) {
                                            removeView(selectFunctionList.get(j));
                                            if (selectFunctionList.size() - 1 == j) {
                                                selectFunctionList.clear();
                                            }
                                        }
                                    }
                                }*/
                                if (sum >= 5) {
                                    ToastUitl.showShort("最多只能选择五个领域");
                                } else {
                                    functionList.get(i).setCheck(true);
                                    selectFunctionList.add(functionList.get(i));
                                    if (!selectIndustryList.contains(industryList.get(currentPosition))) {
                                        selectIndustryList.add(industryList.get(currentPosition));
                                    }
                                    addView(functionList.get(i), industryList.get(currentPosition).getId());
                                    //Log.i("您好",selectFunctionList.toString()+selectIndustryList.toString());
                                    //Log.i("当前选择的",selectFunctionList.toString());
                                }
                            }
                            rightAdapter.notifyDataSetChanged();
                        }
                    });
                    break;
            }
        }
    };

    /**
     * 添加已选领域视图
     *
     * @param cityBean
     */
    private void addView(final CityBean cityBean, String industryId) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 15;
        params.topMargin = 12;
        params.bottomMargin = 12;
        params.rightMargin = 15;
        final LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.item_textview_selected, null, false);
        ll.setLayoutParams(params);
        sum++;
        TextView tv = ll.findViewById(R.id.item_select);
        tv.setText("[" + ResumeInfoIDToString.getIndustry(this, industryId + "", true) + "]" + cityBean.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFunctionList.remove(cityBean);
                llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
                sum--;
                removeIndustry(cityBean);
                if (cityBean.getId().length() == 2) {
                    selectIndustryList.remove(cityBean);
                    getLeftDataFresh(cityBean);
                }
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

    private void removeIndustry(CityBean cityBean) {
        String id = cityBean.getId().substring(0, 2);
        int num = 0;
        for (int i = 0; i < selectFunctionList.size(); i++) {
            if (selectFunctionList.get(i).getId().equals(cityBean.getId().substring(0, 2))) {
                num++;
            }
        }
        if (num == 0) {
            for (int i = 0; i < selectIndustryList.size(); i++) {
                if (id.equals(selectIndustryList.get(i).getId())) {
                    selectIndustryList.remove(i);
                    break;
                }
            }
        }
    }

    private void getLeftDataFresh(CityBean cityBean) {
        for (int i = 5; i < industryList.size(); i++) {
            industryList.get(i).setCheck(false);
            for (int j = 0; j < selectIndustryList.size(); j++) {
                if (industryList.get(i).getId().equals(selectIndustryList.get(j).getId())) {
                    industryList.get(i).setCheck(true);
                }
            }
        }
        leftAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新一下右边的listView
     */
    private void getRightDataFresh() {
        if (functionList != null && functionList.size() != 0) {
            for (int i = 0; i < functionList.size(); i++) {
                functionList.get(i).setCheck(false);
                for (int j = 0; j < selectFunctionList.size(); j++) {
                    if (functionList.get(i).getId().equals(selectFunctionList.get(j).getId())) {
                        functionList.get(i).setCheck(true);
                    }
                }
            }
            rightAdapter.notifyDataSetChanged();
        }
    }

    private void setNum() {
        if (sum > 0) {
            rlSelectData.setVisibility(View.VISIBLE);
        } else {
            rlSelectData.setVisibility(View.GONE);
        }
        tvSelectPositionNum.setText(sum + "");
    }

    private void removeView(CityBean cityBean) {
        llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
        sum--;
        removeIndustry(cityBean);
        if (sum == 0) {
            rlSelectData.setVisibility(View.GONE);
        }
        setNum();
    }
    @OnClick({R.id.tv_selectPositionCancel, R.id.tv_selectPositionOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_selectPositionCancel:
                finish();
                break;
            case R.id.tv_selectPositionOK:
                if (industryId != null) {
                    for (int i = 0; i < industryList.size(); i++) {
                        if (industryId.equals(industryList.get(i).getId())) {
                            currentPosition = i;
                            break;
                        }
                    }
                    if (currentPosition < 5) {
                        if (selectFunctionList != null && selectFunctionList.size() != 0) {
                            JobOrderActivity.instance.setFunctionList(industryId, selectFunctionList);
                            finish();
                        } else {
                            ToastUitl.showShort("请选择领域");
                            return;
                        }
                    } else {
                        JobOrderActivity.instance.setFunctionList(industryId, selectFunctionList);
                        finish();
                    }
                } else {
                    ToastUitl.showShort("请选择行业");
                    return;
                }
                break;
        }
    }
}
