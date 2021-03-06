package com.hr.ui.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.activity.JobSearchResultActivity;
import com.hr.ui.ui.main.adapter.MySelectFunctionLeftAdapter;
import com.hr.ui.ui.main.adapter.MySelectPositionRightAdapter;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.ResumeInfoIDToString;
import com.hr.ui.view.MyFlowLayout;

import java.util.List;

/**
 * Created by wdr on 2018/1/9.
 */

public class PopupWindowFieldView {
    private List<CityBean> industryList;
    private String industryId;
    private List<CityBean> functionList;
    private List<CityBean> selectFunctionList;
    private RelativeLayout rlSelectData;
    private ListView lvLeft,lvRight;
    private int currentPosition;
    private List<CityBean> selectIndustryList;
    private MySelectFunctionLeftAdapter leftAdapter;
    private MySelectPositionRightAdapter rightAdapter;
    private RelativeLayout rlNoFunction;
    private int sum;
    private Activity context;
    private PopupWindow popupWindow;
    private MyFlowLayout llSelectedPosition;
    private TextView tvSelectPositionNum;
    private TextView tvCancel,tvOK;
    private int page;
    public PopupWindowFieldView(final PopupWindow popupWindow, final int page1, final Activity context, final List<CityBean> selectIndustryList, final List<CityBean> selectFunctionList1, RelativeLayout rlSelectData, RelativeLayout rlNoFunction,
                                ListView lvLeft, ListView lvRight, MyFlowLayout llSelectedPosition, TextView tvSelectPositionNum, TextView tvCancel, TextView tvOK){
        this.context=context;
        this.selectFunctionList= selectFunctionList1;
        this.rlNoFunction=rlNoFunction;
        this.rlSelectData=rlSelectData;
        this.lvLeft= lvLeft;
        this.lvRight=lvRight;
        this.page= page1;
        this.selectIndustryList=selectIndustryList;
        this.llSelectedPosition=llSelectedPosition;
        this.tvSelectPositionNum=tvSelectPositionNum;
        this.tvCancel=tvCancel;
        this.tvOK=tvOK;
        initUI();
        /*Log.i("您好",selectFunctionList.toString()+industryId1);*/
        this.tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  JobSearchResultActivity.instance.setFunctionList(selectIndustryList,selectFunctionList);*/
                            if(popupWindow!=null){
                                popupWindow.dismiss();
                            }
                            JobSearchResultActivity.instance.page=1;
            }
        });
        this.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null) {
                    popupWindow.dismiss();
                    selectFunctionList.clear();
                    selectIndustryList.clear();
                }
            }
        });
    }

    private void initUI() {
        industryList = FromStringToArrayList.getInstance().getIndustryList();
        //显示传过来的行业
        if (selectIndustryList != null&&!"".equals(selectIndustryList)&&selectIndustryList.size()!=0) {
            for (int i = 0; i <industryList.size(); i++) {
                for(int j=0;j<selectIndustryList.size();j++) {
                    if (industryList.get(i).getId().equals(selectIndustryList.get(j).getId())) {
                        if(i>=5){
                            industryList.get(i).setCheck(true);
                        }
                        currentPosition = i;
                    }
                }
            }
            if(currentPosition<5){
                for(int i=0;i<5;i++){
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
                for(int j=0;j<selectFunctionList.size();j++){
                    for(int k=0;k<industryList.size();k++) {
                        if (selectFunctionList.get(j).getId().substring(0, 2).equals(industryList.get(k).getId())){
                            addView(selectFunctionList.get(j), industryList.get(k).getId());
                        }
                    }
                }
                for(int i=5;i<industryList.size();i++){
                    for(int j=0;j<selectIndustryList.size();j++){
                        if(selectIndustryList.get(j).getId().equals(industryList.get(i).getId())){
                            addView(selectIndustryList.get(j),selectIndustryList.get(j).getId());
                        }
                    }
                }
            } else {
                if(selectIndustryList!=null&&selectIndustryList.size()!=0){
                    for(int i=5;i<industryList.size();i++){
                        for(int j=0;j<selectIndustryList.size();j++){
                            if(selectIndustryList.get(j).getId().equals(industryList.get(i).getId())){
                                addView(selectIndustryList.get(j),selectIndustryList.get(j).getId());
                            }
                        }
                    }
                }else {
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



/*
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
                        }
                    } else {
                        JobOrderActivity.instance.setFunctionList(industryId, selectFunctionList);
                        finish();
                    }
                } else {
                    ToastUitl.showShort("请选择行业");
                }
                break;
        }
    }
*/

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
                                for(int j=0;j<5;j++){
                                    industryList.get(j).setCheck(false);
                                }
                                industryList.get(i).setCheck(true);
                            } else {
                                lvRight.setVisibility(View.GONE);
                                rlNoFunction.setVisibility(View.VISIBLE);
                                for(int j=0;j<5;j++){
                                    industryList.get(j).setCheck(false);
                                }
                                setNum();
                                if (industryList.get(i).isCheck() == false) {
                                    if (sum >= 5) {
                                        ToastUitl.showShort("最多只能选择五个领域");
                                    }else {
                                        industryList.get(i).setCheck(true);
                                        selectIndustryList.add(industryList.get(i));
                                        addView(industryList.get(i), industryList.get(i).getId());
                                    }
                                } else {
                                    for(int j=0;j<selectIndustryList.size();j++) {
                                        if(selectIndustryList.get(j).getId().equals(industryList.get(i).getId())){
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
                                    if(!selectIndustryList.contains(industryList.get(currentPosition))) {
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
        final LinearLayout ll = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.item_textview_selected, null, false);
        ll.setLayoutParams(params);
        sum++;
        TextView tv = ll.findViewById(R.id.item_select);
        tv.setText("[" + ResumeInfoIDToString.getIndustry(context, industryId + "", true) + "]" + cityBean.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFunctionList.remove(cityBean);
                llSelectedPosition.removeView(llSelectedPosition.findViewWithTag(cityBean.getId()));
                sum--;
                removeIndustry(cityBean);
                if(cityBean.getId().length()==2){
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
    private void removeIndustry(CityBean cityBean){
        String id=cityBean.getId().substring(0,2);
        int num=0;
        for(int i=0;i<selectFunctionList.size();i++){
            if(selectFunctionList.get(i).getId().equals(cityBean.getId().substring(0,2))){
                num++;
            }
        }
        if(num==0){
            for(int i=0;i<selectIndustryList.size();i++){
                if(id.equals(selectIndustryList.get(i).getId())){
                    selectIndustryList.remove(i);
                    break;
                }
            }
        }
    }
    private void getLeftDataFresh(CityBean cityBean) {
        for(int i=5;i<industryList.size();i++){
            industryList.get(i).setCheck(false);
            for(int j=0;j<selectIndustryList.size();j++){
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
}
