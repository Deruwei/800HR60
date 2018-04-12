package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.EvenList;
import com.hr.ui.bean.HistoryBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.SearchHistoryBean;
import com.hr.ui.db.SearchHistoryUtils;
import com.hr.ui.ui.main.adapter.MySelectPositionLeftAdapter;
import com.hr.ui.ui.main.adapter.MySelectPositionRightAdapter;
import com.hr.ui.utils.PopupWindowPositonClassView;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.view.MyFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.view_industry)
    View viewIndustry;
    @BindView(R.id.rl_noFunction)
    RelativeLayout rlNoFunction;
    @BindView(R.id.tv_selectPositionTag)
    TextView tvSelectPositionTag;
    @BindView(R.id.tv_selectPositionRightTag)
    TextView tvSelectPositionRightTag;
    @BindView(R.id.rl_selectedPositionTitle)
    RelativeLayout rlSelectedPositionTitle;
    @BindView(R.id.tv_selectPositionCancel)
    TextView tvSelectPositionCancel;
    @BindView(R.id.tv_selectPositionOK)
    TextView tvSelectPositionOK;
    @BindView(R.id.cl_selectPosition)
    ConstraintLayout clSelectPosition;
    private List<CityBean> positonBeanList1,positonBeanList;
    private String industryId,cityid;
    private List<CityBean> positonLeftList, positionRightList;
    private MySelectPositionLeftAdapter leftAdapter;
    private MySelectPositionRightAdapter rightAdapter;
    private List<CityBean> selectPositionList = new ArrayList<>();
    private int sum, currentJobPosition;//选择数量
    private String tag;
    private PopupWindow popupWindowPositonClass;
    private List<CityBean> selectPositionClassList = new ArrayList<>();
    public static SelectPositionActivity instance;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String industryId, String cityId, String tag) {
        Intent intent = new Intent(activity, SelectPositionActivity.class);
        intent.putExtra("tag", tag);
        intent.putExtra("industryId", industryId);
        intent.putExtra("cityId", cityId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    public static void startAction(Activity activity, String industryId, List<CityBean> selectPositionList, String tag) {
        Intent intent = new Intent(activity, SelectPositionActivity.class);
        intent.putExtra("tag", tag);
        intent.putExtra("industryId", industryId);
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
        instance = this;
        viewIndustryBottom.setVisibility(View.GONE);
        llIndustryTitle.setVisibility(View.GONE);
        cityid=  getIntent().getStringExtra("cityId");
        industryId = getIntent().getStringExtra("industryId");
        selectPositionList= (List<CityBean>) getIntent().getSerializableExtra("selectPosition");
        if(selectPositionList==null||"".equals(selectPositionList)){
            selectPositionList=new ArrayList<>();
        }
        tag = getIntent().getStringExtra("tag");
        if(tag.equals(JobSerchActivity.TAG)){
            tvSelectPositionOK.setText(R.string.search);
        }else{
            tvSelectPositionOK.setText(R.string.sure);
        }
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
        positonBeanList1=FromStringToArrayList.getInstance().getExpectPosition(industryId);
        //System.out.println(positonBeanList.toString());
        positonLeftList = new ArrayList<>();
        for (int i = 0; i < positonBeanList1.size(); i++) {
            if (positonBeanList1.get(i).getId().endsWith("000")) {
                positonLeftList.add(positonBeanList1.get(i));
            }
        }
        if (selectPositionList != null && selectPositionList.size() != 0) {
            for (int i = 0; i < selectPositionList.size(); i++) {
                addView(selectPositionList.get(i));
            }
        } else {
            rlSelectData.setVisibility(View.GONE);
        }
        final Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance = this;
    }

    @OnClick({R.id.tv_selectPositionCancel, R.id.tv_selectPositionOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_selectPositionCancel:
                finish();
                break;
            case R.id.tv_selectPositionOK:
                if (selectPositionList != null && selectPositionList.size() != 0) {
                    if (JobOrderActivity.TAG.equals(tag)) {
                        //JobOrderActivity.instance.setPositionList(selectPositionList);
                        EventBus.getDefault().post(new EvenList(1,selectPositionList));
                    } else if (JobSerchActivity.TAG.equals(tag)) {
                       /* JobSerchActivity.instance.setPosition(selectPositionList);*/
                       JobSearchBean jobSearchBean=new JobSearchBean();
                        SearchHistoryBean historyBean=new SearchHistoryBean();
                       jobSearchBean.setPositionId(FromStringToArrayList.getInstance().getPositionId(selectPositionList));
                       historyBean.setPositionId(FromStringToArrayList.getInstance().getPositionId(selectPositionList));
                       jobSearchBean.setIndustryId(industryId);
                        historyBean.setIndustryId(industryId);
                       jobSearchBean.setPlaceId(cityid);
                        historyBean.setPlaceId(cityid);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String str = formatter.format(curDate);
                        historyBean.setAddDate(str);
                        SearchHistoryUtils.insertJobSearchData(historyBean);
                        JobSearchResultActivity.startAction(this,jobSearchBean);
                    }
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
        final LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.item_textview_selected, null, false);
        ll.setLayoutParams(params);
        TextView tv = ll.findViewById(R.id.item_select);
        if (cityBean.getId().contains("|")) {
            if(Utils.checkMedicinePositionClass2(cityBean)==true) {
                tv.setText(cityBean.getName() + "(" + "行政后勤" + ")");
            }else{
                tv.setText(cityBean.getName() + "(" + Utils.getPositionClassName(cityBean.getId().substring(cityBean.getId().indexOf("|") + 1)) + ")");
            }
        } else {
            tv.setText(cityBean.getName());
        }
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
                    lvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int i = 0; i < positonLeftList.size(); i++) {
                                positonLeftList.get(i).setCheck(false);
                            }
                            positonLeftList.get(position).setCheck(true);
                            positionRightList = new ArrayList<>();
                            for (int i = 0; i < positonBeanList.size(); i++) {
                                if (positonLeftList.get(position).getId().substring(0, 3).equals(positonBeanList.get(i).getId().substring(0, 3))) {
                                    positonBeanList.get(i).setCheck(false);
                                    positionRightList.add(positonBeanList.get(i));
                                }
                            }
                            if (selectPositionList != null && selectPositionList.size() != 0) {
                                for (int i = 0; i < positionRightList.size(); i++) {
                                    for (int j = 0; j < selectPositionList.size(); j++) {
                                        if (selectPositionList.get(j).getId().contains("|")) {
                                            if (positionRightList.get(i).getId().equals(selectPositionList.get(j).getId().substring(0, selectPositionList.get(j).getId().indexOf("|")))) {
                                                positionRightList.get(i).setCheck(true);
                                            }
                                        } else {
                                            if (positionRightList.get(i).getId().equals(selectPositionList.get(j).getId())) {
                                                positionRightList.get(i).setCheck(true);
                                            }
                                        }
                                    }
                                }
                            }
                            leftAdapter.notifyDataSetChanged();
                            Message message1 = Message.obtain();
                            message1.what = 2;
                            handler.sendMessage(message1);

                        }
                    });
                    break;
                case 2:
                    rightAdapter = new MySelectPositionRightAdapter(positionRightList);
                    lvRight.setAdapter(rightAdapter);
                    lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            sum = selectPositionList.size();
                            currentJobPosition = position;
                            if (positionRightList.get(position).isCheck() == false) {
                                if(tag.equals(JobSerchActivity.TAG)){
                                    doAddJobView(position, selectPositionClassList);
                                }else {
                                    if (Utils.checkMedicinePositionClass(positionRightList.get(position)) == true) {
                                        PopupWindowPositonClassView viewPositionClass = new PopupWindowPositonClassView(popupWindowPositonClass, SelectPositionActivity.this, clSelectPosition);
                                    } else {
                                        doAddJobView(position, selectPositionClassList);
                                    }
                                }
                            } else {
                                positionRightList.get(position).setCheck(false);
                                for (int i = 0; i < selectPositionList.size(); i++) {
                                    if (selectPositionList.get(i).getId().contains("|")) {
                                        if (positionRightList.get(position).getId().equals(selectPositionList.get(i).getId().substring(0, selectPositionList.get(i).getId().indexOf("|")))) {
                                            removeView(selectPositionList.get(i));
                                            selectPositionList.remove(selectPositionList.get(i));
                                            break;
                                        }
                                    } else {
                                        if (positionRightList.get(position).getId().equals(selectPositionList.get(i).getId())) {
                                            removeView(selectPositionList.get(i));
                                            selectPositionList.remove(selectPositionList.get(i));
                                            break;
                                        }
                                    }
                                }
                                setNum();
                            }

                            rightAdapter.notifyDataSetChanged();
                        }
                    });
                    break;
            }
        }
    };

    public void setPositionClassList(List<CityBean> selectPositionList) {
        this.selectPositionClassList = selectPositionList;
        doAddJobView(currentJobPosition, selectPositionClassList);
        selectPositionClassList.clear();
    }

    private void doAddJobView(int position, List<CityBean> selectPositionClassList) {
        sum = selectPositionList.size();
        if (sum == 0) {
            rlSelectData.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            for (int i = 0; i < positionRightList.size(); i++) {
                positionRightList.get(i).setCheck(false);
            }
            List<CityBean> ints = new ArrayList<>();
            for (int i = 0; i < selectPositionList.size(); i++) {
                if (selectPositionList.get(i).getId().substring(0, 3).equals(positionRightList.get(position).getId().substring(0, 3))) {
                    removeView(selectPositionList.get(i));
                    ints.add(selectPositionList.get(i));
                }
            }
            selectPositionList.removeAll(ints);
        } else {
            if (positionRightList.get(0).isCheck() == true) {
                for (int i = 0; i < selectPositionList.size(); i++) {
                    if (selectPositionList.get(i).getId().contains("|")) {
                        if (selectPositionList.get(i).getId().substring(0, selectPositionList.get(i).getId().indexOf("|")).equals(positionRightList.get(0).getId())) {
                            removeView(selectPositionList.get(i));
                            selectPositionList.remove(i);
                        }
                    } else {
                        if (selectPositionList.get(i).getId().equals(positionRightList.get(0).getId())) {
                            removeView(selectPositionList.get(i));
                            selectPositionList.remove(i);
                        }
                    }
                }
                positionRightList.get(0).setCheck(false);
            }
        }
        sum = selectPositionList.size();
        if (sum >= 5) {
            ToastUitl.showShort("最多只能选择5个职位");
            return;
        } else {
            CityBean cityBean = new CityBean();
            if (selectPositionClassList != null && selectPositionClassList.size() != 0) {
                cityBean.setId(positionRightList.get(position).getId() + "|" + selectPositionClassList.get(0).getId());
                cityBean.setName(positionRightList.get(position).getName());
                cityBean.setCheck(true);
                selectPositionList.add(cityBean);
            } else {
                if(Utils.checkMedicinePositionClass2(positionRightList.get(position))==true){
                    if(tag.equals(JobOrderActivity.TAG)) {
                        cityBean.setId(positionRightList.get(position).getId() + "|" + "10500");
                        cityBean.setName(positionRightList.get(position).getName());
                        cityBean.setCheck(true);
                        selectPositionList.add(cityBean);
                    }else{
                        cityBean = positionRightList.get(position);
                        selectPositionList.add(positionRightList.get(position));
                    }
                }else {
                    cityBean = positionRightList.get(position);
                    selectPositionList.add(positionRightList.get(position));
                }
            }
            //Log.i("到这里了",cityBean.toString());
            addView(cityBean);
            positionRightList.get(position).setCheck(true);
        }
        rightAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance=null;
    }
}
