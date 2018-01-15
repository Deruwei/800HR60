package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
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
import com.hr.ui.ui.main.adapter.MyCityAdapter;
import com.hr.ui.ui.main.adapter.MyProvinceAdapter;
import com.hr.ui.ui.resume.activity.ResumeJobOrderActivity;
import com.hr.ui.ui.resume.activity.ResumePersonalInfoActivity;
import com.hr.ui.ui.resume.activity.ResumeWorkExpActivity;
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
 * Created by wdr on 2017/12/14.
 */

public class SelectCityActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.iv_dingWei)
    ImageView ivDingWei;
    @BindView(R.id.tv_locationTag)
    TextView tvLocationTag;
    @BindView(R.id.tv_location_city)
    TextView tvLocationCity;
    @BindView(R.id.lv_selectProvince)
    ListView lvSelectProvince;
    @BindView(R.id.lv_selectCity)
    ListView lvSelectCity;
    public List<CityBean> cityBeanList, cityBeanList2;
    @BindView(R.id.ll_location)
    RelativeLayout llLocation;
    @BindView(R.id.iv_fresh)
    ImageView ivFresh;
    @BindView(R.id.view_selectCity)
    View viewSelectCity;
    @BindView(R.id.tv_selectCityTag)
    TextView tvSelectCityTag;
    @BindView(R.id.tv_selectCityNum)
    TextView tvSelectCityNum;
    @BindView(R.id.tv_selectPositionRightTag)
    TextView tvSelectPositionRightTag;
    @BindView(R.id.rl_selectedCityTitle)
    RelativeLayout rlSelectedCityTitle;
    @BindView(R.id.ll_selectedCity)
    MyFlowLayout llSelectedCity;
    @BindView(R.id.rl_selectCityShow)
    RelativeLayout rlSelectCityShow;
    @BindView(R.id.tv_selectCityCancel)
    TextView tvSelectCityCancel;
    @BindView(R.id.tv_selectCityOK)
    TextView tvSelectCityOK;
    @BindView(R.id.ll_selectCityBottom)
    LinearLayout llSelectCityBottom;
    private List<CityBean> provinceCityList, cityList;
    private MyProvinceAdapter myProvinceAdapter;
    private MyCityAdapter myCityAdapter;
    private List<CityBean> selectCityList = new ArrayList<>();
    private int type; //1 代表选择一个城市 2代表选择多个城市
    private String tag;//来自于那个Activity
    private int sum;//选择城市的个数
    private int currentPosition;//当前点击了左边的哪个位置

    @Override
    public int getLayoutId() {
        return R.layout.activity_selectcity;
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, int type, String tag) {
        Intent intent = new Intent(activity, SelectCityActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("tag", tag);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    public static void startAction(Activity activity, int type, String tag,List<CityBean> selectCityList) {
        Intent intent = new Intent(activity, SelectCityActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("tag", tag);
        intent.putExtra("selectCityList", (Serializable) selectCityList);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra("type", 1);
        tag = getIntent().getStringExtra("tag");
        selectCityList= (List<CityBean>) getIntent().getSerializableExtra("selectCityList");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (type == 2) {
            llLocation.setVisibility(View.GONE);
            rlSelectCityShow.setVisibility(View.GONE);
            llSelectCityBottom.setVisibility(View.VISIBLE);
        } else if (type == 1) {
            llLocation.setVisibility(View.VISIBLE);
            rlSelectCityShow.setVisibility(View.GONE);
            llSelectCityBottom.setVisibility(View.GONE);
        }
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.selectCity);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        provinceCityList = new ArrayList<>();
        cityBeanList = FromStringToArrayList.getInstance().getCityList("city.txt");
        cityBeanList2 = FromStringToArrayList.getInstance().getCityList("city.txt");
        /**
         * 找出直辖市和省份，左边的数据
         */
        for (int i = 0; i < cityBeanList.size(); i++) {
            if (cityBeanList.get(i).getId().endsWith("00")) {
                provinceCityList.add(cityBeanList.get(i));
            }
        }
        if(selectCityList!=null&&selectCityList.size()!=0) {
            for (int i = 0; i < selectCityList.size(); i++) {
                if(type==2) {
                    addView(selectCityList.get(i));
                }
                for (int j = 0; j < 4; j++) {
                    if (selectCityList.get(i).getId().equals(provinceCityList.get(j).getId())) {
                        provinceCityList.get(j).setCheck(true);
                    }
                }
                for(int j=4;j<provinceCityList.size();j++){
                    if(selectCityList.get(i).getId().substring(0,2).equals(provinceCityList.get(j).getId().substring(0,2))){
                        provinceCityList.get(j).setCheck(true);
                    }
                }
            }
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

    @OnClick(R.id.iv_fresh)
    public void onViewClicked() {
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //Log.i("职位信息",provinceCityList.toString());
                    myProvinceAdapter = new MyProvinceAdapter(provinceCityList, type);
                    lvSelectProvince.setAdapter(myProvinceAdapter);
                    if(selectCityList!=null&&selectCityList.size()!=0){
                        cityList=new ArrayList<>();
                        for(int i=4;i<provinceCityList.size();i++){
                            if(provinceCityList.get(i).isCheck()==true){
                                provinceCityList.get(i).setCheck(false);
                                currentPosition=i;
                            }
                        }
                        if(currentPosition>=4) {
                            provinceCityList.get(currentPosition).setCheck(true);
                        }
                        for (int i = 0; i < cityBeanList2.size(); i++) {
                            if (provinceCityList.get(currentPosition).getId().substring(0, 2).equals(cityBeanList2.get(i).getId().substring(0, 2))&&currentPosition>=4) {
                                cityList.add(cityBeanList2.get(i));
                            }
                        }
                        for (int i = 0; i < cityList.size(); i++) {
                            cityList.get(i).setCheck(false);
                        }
                        Message message = Message.obtain();
                        message.what = 2;
                        handler.sendMessage(message);

                    }
                    lvSelectProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            cityList = new ArrayList<>();
                            currentPosition = position;
                            //省级
                            if (position >= 4) {
                                for (int i = 4; i < provinceCityList.size(); i++) {
                                    provinceCityList.get(i).setCheck(false);
                                }
                                provinceCityList.get(position).setCheck(true);
                                for (int i = 0; i < cityBeanList2.size(); i++) {
                                    if (provinceCityList.get(position).getId().substring(0, 2).equals(cityBeanList2.get(i).getId().substring(0, 2))) {
                                        cityList.add(cityBeanList2.get(i));
                                    }
                                }
                                for (int i = 0; i < cityList.size(); i++) {
                                    cityList.get(i).setCheck(false);
                                }
                            } else {
                                //直辖市
                                if (type == 2) {
                                    if (provinceCityList.get(position).isCheck() == false) {
                                        if (selectCityList.size() >= 5) {
                                            ToastUitl.showShort("选择城市已经达到最大值");
                                            return;
                                        }
                                        for (int i = 4; i < provinceCityList.size(); i++) {
                                            provinceCityList.get(i).setCheck(false);
                                        }
                                        provinceCityList.get(position).setCheck(true);
                                        selectCityList.add(provinceCityList.get(position));
                                        addView(provinceCityList.get(position));
                                    } else {
                                        for (int i = 4; i < provinceCityList.size(); i++) {
                                            provinceCityList.get(i).setCheck(false);
                                        }
                                        for(int i=0;i<selectCityList.size();i++){
                                            if(selectCityList.get(i).getId().equals(provinceCityList.get(position).getId())){
                                                selectCityList.remove(i);
                                                break;
                                            }
                                        }
                                        // Log.i("职位信息",selectCityList.toString()+"呵呵"+provinceCityList.get(position).toString());
                                        selectCityList.remove(provinceCityList.get(position));
                                        provinceCityList.get(position).setCheck(false);
                                        removeView(provinceCityList.get(position));
                                    }
                                } else {
                                    if (PersonalInformationActivity.TAG.equals(tag)) {
                                        PersonalInformationActivity.instance.setSelectCity(provinceCityList.get(position));
                                    } else if (WorkExpActivity.TAG.equals(tag)) {
                                        WorkExpActivity.instance.setSelectCity(provinceCityList.get(position));
                                    }else if(ResumePersonalInfoActivity.TAG.equals(tag)){
                                        ResumePersonalInfoActivity.instance.setSelectCity(provinceCityList.get(position));
                                    }else if(ResumeWorkExpActivity.TAG.equals(tag)){
                                        ResumeWorkExpActivity.instance.setSelectCity(provinceCityList.get(position));
                                    }
                                    finish();
                                }
                            }
                            myProvinceAdapter.notifyDataSetChanged();
                            Message message = Message.obtain();
                            message.what = 2;
                            handler.sendMessage(message);


                        }
                    });
                    break;
                case 2:
                    if (selectCityList != null && selectCityList.size() != 0) {
                        for (int i = 0; i < selectCityList.size(); i++) {
                            for (int j = 0; j < cityList.size(); j++) {
                                if (selectCityList.get(i).getId().equals(cityList.get(j).getId())) {
                                    cityList.get(j).setCheck(true);
                                }
                            }
                        }
                    }
                    myCityAdapter = new MyCityAdapter(cityList);
                    lvSelectCity.setAdapter(myCityAdapter);
                    lvSelectCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (type == 1) {
                                if (PersonalInformationActivity.TAG.equals(tag)) {
                                    PersonalInformationActivity.instance.setSelectCity(cityList.get(position));
                                } else if (WorkExpActivity.TAG.equals(tag)) {
                                    WorkExpActivity.instance.setSelectCity(cityList.get(position));
                                }else if(ResumePersonalInfoActivity.TAG.equals(tag)){
                                    ResumePersonalInfoActivity.instance.setSelectCity(cityList.get(position));
                                }
                                else if(ResumeWorkExpActivity.TAG.equals(tag)){
                                    ResumeWorkExpActivity.instance.setSelectCity(cityList.get(position));
                                }
                                finish();
                            } else if (type == 2) {
                                if (cityList.get(position).isCheck() == false) {
                                    List<CityBean> removeList=new ArrayList<>();
                                    if(cityList.get(position).getId().endsWith("00")){
                                        for(int i=0;i<selectCityList.size();i++){
                                            if(selectCityList.get(i).getId().substring(0,2).equals(cityList.get(position).getId().substring(0,2))){
                                                removeList.add(selectCityList.get(i));
                                                removeView(selectCityList.get(i));
                                            }
                                        }
                                    }else{
                                        cityList.get(0).setCheck(false);
                                        for(int i=0;i<selectCityList.size();i++){
                                            if(selectCityList.get(i).getId().equals(cityList.get(0).getId())){
                                                removeList.add(selectCityList.get(i));
                                                removeView(selectCityList.get(i));
                                            }
                                        }
                                    }
                                    for(int i=0;i<removeList.size();i++){
                                        for(int j=0;j<cityList.size();j++){
                                            if(removeList.get(i).getId().equals(cityList.get(j).getId())){
                                                cityList.get(j).setCheck(false);
                                            }
                                        }
                                    }
                                    selectCityList.removeAll(removeList);
                                    if (selectCityList.size() >= 5) {
                                        ToastUitl.showShort("选择城市已经达到最大值");
                                        return;
                                    }
                                    cityList.get(position).setCheck(true);
                                    selectCityList.add(cityList.get(position));
                                    addView(cityList.get(position));
                                } else {
                                    for(int i=0;i<selectCityList.size();i++){
                                        if(cityList.get(position).getId().equals(selectCityList.get(i).getId())){
                                            selectCityList.remove(selectCityList.get(i));
                                            break;
                                        }
                                    }
                                    cityList.get(position).setCheck(false);
                                    removeView(cityList.get(position));

                                }
                                myCityAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                    break;
            }
        }
    };

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
        TextView tv=ll.findViewById(R.id.item_select);
        tv.setText(cityBean.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCityList.remove(cityBean);
                llSelectedCity.removeView(llSelectedCity.findViewWithTag(cityBean.getId()));
                sum = selectCityList.size();
                getRightDataFresh();
                getLeftDataFresh();
                if (sum == 0) {
                    rlSelectCityShow.setVisibility(View.GONE);
                }
                setNum();
            }
        });
        ll.setTag(cityBean.getId());
        llSelectedCity.addView(ll);
        setNum();
    }

    private void getLeftDataFresh() {
        for (int i = 0; i < provinceCityList.size(); i++) {
            provinceCityList.get(i).setCheck(false);
            for (int j = 0; j < selectCityList.size(); j++) {
                if (provinceCityList.get(i).getId().equals(selectCityList.get(j).getId())) {
                    provinceCityList.get(i).setCheck(true);
                }
            }
        }
        if (currentPosition >= 4) {
            provinceCityList.get(currentPosition).setCheck(true);
        }
        myProvinceAdapter.notifyDataSetChanged();
    }

    private void getRightDataFresh() {
        if (cityList!=null&&!"".equals(cityList)) {
            for(int i=0;i<cityList.size();i++){
                cityList.get(i).setCheck(false);
            }
            for (int i = 0; i < cityList.size(); i++) {
                for (int j = 0; j < selectCityList.size(); j++) {
                    if (cityList.get(i).getId().equals(selectCityList.get(j).getId())) {
                        cityList.get(i).setCheck(true);
                    }
                }
            }
            myCityAdapter.notifyDataSetChanged();
        }
    }

    private void removeView(CityBean cityBean) {
        llSelectedCity.removeView(llSelectedCity.findViewWithTag(cityBean.getId()));
        sum = selectCityList.size();
        if (sum == 0) {
            rlSelectCityShow.setVisibility(View.GONE);
        }
        setNum();
    }

    private void setNum() {
        sum = selectCityList.size();
        if (sum > 0) {
            rlSelectCityShow.setVisibility(View.VISIBLE);
        }else{
            rlSelectCityShow.setVisibility(View.GONE);
        }
        tvSelectCityNum.setText(sum + "");
    }

    @OnClick({R.id.tv_selectCityCancel, R.id.tv_selectCityOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_selectCityCancel:
                finish();
                break;
            case R.id.tv_selectCityOK:
                if(selectCityList!=null&&!"".equals(selectCityList)&&selectCityList.size()!=0) {
                    if(ResumeJobOrderActivity.TAG.equals(tag)){
                        ResumeJobOrderActivity.instance.setSelectCityList(selectCityList);
                    }else if(JobOrderActivity.TAG.equals(tag)){
                            JobOrderActivity.instance.setAddress(selectCityList);
                    }else if(JobSerchActivity.TAG.equals(tag)){
                        JobSerchActivity.instance.setPlace(selectCityList);
                    }
                    finish();
                }else{
                    ToastUitl.showShort("请选择城市");
                }
                break;
        }
    }
}
