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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.adapter.MySelectFunctionLeftAdapter;
import com.hr.ui.ui.main.adapter.MySelectFunctionRightAdapter;
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
    @BindView(R.id.tv_selectPositionTag)
    TextView tvSelectPositionTag;
    @BindView(R.id.tv_selectPositionRightTag)
    TextView tvSelectPositionRightTag;
    @BindView(R.id.rl_noFunction)
    RelativeLayout rlNoFunction;
    private List<CityBean> industryList;
    private List<CityBean> functionList;
    private MySelectFunctionLeftAdapter leftAdapter;
    private MySelectFunctionRightAdapter rightAdapter;
    private String industryId;
    private List<CityBean> selectFunctionList = new ArrayList<>();
    private int sum;
    private int currentPosition;
    private String tag;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String industryId, List<CityBean> selectFunctionList,String tag) {
        Intent intent = new Intent(activity, SelectFunctionActivity.class);
        intent.putExtra("selectFunction", (Serializable) selectFunctionList);
        intent.putExtra("industryId", industryId);
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
        industryId = getIntent().getStringExtra("industryId");
        selectFunctionList = (List<CityBean>) getIntent().getSerializableExtra("selectFunction");
        toolBar.setTitle("");
        tag=getIntent().getStringExtra("tag");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.expectedField);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        industryList = FromStringToArrayList.getInstance().getIndustryList();
        tvSelectPositionTag.setText(R.string.selectField);
        //显示传过来的行业
        if (industryId != null) {
            for (int i = 0; i < industryList.size(); i++) {
                if (industryList.get(i).getId().equals(industryId)) {
                    industryList.get(i).setCheck(true);
                }
            }

        }
        final Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
        //显示传过来的领域列表
        functionList = FromStringToArrayList.getInstance().getExpectField(industryId);
        if (selectFunctionList != null && selectFunctionList.size() != 0) {
            for (int j = 0; j < functionList.size(); j++) {
                for (int k = 0; k < selectFunctionList.size(); k++) {
                    if (functionList.get(j).getId().equals(selectFunctionList.get(k).getId())) {
                        functionList.get(j).setCheck(true);
                        addView(selectFunctionList.get(k), industryId);
                    }

                }
            }
        } else {
            rlSelectData.setVisibility(View.GONE);
        }
        Message message1 = new Message();
        message1.what = 2;
        handler.sendMessage(message1);


        lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                industryId = industryList.get(i).getId();
                currentPosition = i;
                functionList = FromStringToArrayList.getInstance().getExpectField(industryId);
                if (i < 5) {
                    lvRight.setVisibility(View.VISIBLE);
                    rlNoFunction.setVisibility(View.GONE);
                    for (int j = 0; j < industryList.size(); j++) {
                        industryList.get(j).setCheck(false);
                    }
                    if (selectFunctionList != null && selectFunctionList.size() != 0) {
                        for (int j = 0; j < functionList.size(); j++) {
                            for (int k = 0; k < selectFunctionList.size(); k++) {
                                if (functionList.get(j).getId().equals(selectFunctionList.get(k).getId())) {
                                    functionList.get(j).setCheck(true);
                                }
                            }
                        }
                    }
                    industryList.get(i).setCheck(true);

                } else {
                    lvRight.setVisibility(View.GONE);
                    rlNoFunction.setVisibility(View.VISIBLE);
                    for (int j = 0; j < selectFunctionList.size(); j++) {
                        removeView(selectFunctionList.get(j));
                    }
                    selectFunctionList.clear();
                    setNum();
                    if (industryList.get(i).isCheck() == false) {
                        for (int j = 0; j < industryList.size(); j++) {
                            industryList.get(j).setCheck(false);
                        }
                        industryList.get(i).setCheck(true);
                    } else {
                        industryList.get(i).setCheck(false);
                    }
                }
                leftAdapter.notifyDataSetChanged();
                Message message1 = new Message();
                message1.what = 2;
                handler.sendMessage(message1);
            }
        });
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
                    sum = selectFunctionList.size();
                    if (sum == 0) {
                        rlSelectData.setVisibility(View.VISIBLE);
                    }
                    if (selectFunctionList != null && selectFunctionList.size() != 0) {
                        for (int j = 0; j < selectFunctionList.size(); j++) {
                            if (!selectFunctionList.get(j).getId().substring(0, 2).equals(functionList.get(i).getId().substring(0, 2))) {
                                removeView(selectFunctionList.get(j));
                                if (selectFunctionList.size() - 1 == j) {
                                    selectFunctionList.clear();
                                }
                            }
                        }
                    }
                    if (sum >= 5) {
                        ToastUitl.showShort("最多只能选择五个领域");
                    } else {
                        functionList.get(i).setCheck(true);
                        selectFunctionList.add(functionList.get(i));
                        addView(functionList.get(i), industryList.get(currentPosition).getId());
                        //Log.i("当前选择的",selectFunctionList.toString());
                    }
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
                if (industryId != null) {
                    for (int i = 0; i < industryList.size(); i++) {
                        if (industryId.equals(industryList.get(i).getId())) {
                            currentPosition = i;
                            break;
                        }
                    }
                    if (currentPosition < 5) {
                        if (selectFunctionList != null && selectFunctionList.size() != 0) {
                            if(tag.equals(JobOrderActivity.TAG)) {
                                JobOrderActivity.instance.setFunctionList(industryId, selectFunctionList);
                            }else if(tag.equals(JobSerchActivity.TAG)){
                                JobSerchActivity.instance.setFunctionList(industryId, selectFunctionList);
                            }
                            finish();
                        } else {
                            ToastUitl.showShort("请选择领域");
                            return;
                        }
                    } else {
                        if(tag.equals(JobOrderActivity.TAG)) {
                            JobOrderActivity.instance.setFunctionList(industryId, selectFunctionList);
                        }else if(tag.equals(JobSerchActivity.TAG)){
                            JobSerchActivity.instance.setFunctionList(industryId, selectFunctionList);
                        }
                        finish();
                    }
                } else {
                    ToastUitl.showShort("请选择行业");
                    return;
                }
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    leftAdapter = new MySelectFunctionLeftAdapter(industryList);
                    lvLeft.setAdapter(leftAdapter);
                    break;
                case 2:
                    rightAdapter = new MySelectFunctionRightAdapter(functionList);
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
        TextView tv = ll.findViewById(R.id.item_select);
        tv.setText("[" + ResumeInfoIDToString.getIndustry(this, industryId + "", true) + "]" + cityBean.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFunctionList.remove(cityBean);
                llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
                sum = selectFunctionList.size();
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
        sum = selectFunctionList.size();
        if (sum > 0) {
            rlSelectData.setVisibility(View.VISIBLE);
        } else {
            rlSelectData.setVisibility(View.GONE);
        }
        tvSelectPositionNum.setText(sum + "");
    }

    private void removeView(CityBean cityBean) {
        llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
        sum = selectFunctionList.size();
        if (sum == 0) {
            rlSelectData.setVisibility(View.GONE);
        }
        setNum();
    }
}
