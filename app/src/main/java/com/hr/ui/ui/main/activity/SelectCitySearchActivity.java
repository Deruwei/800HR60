package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.adapter.MyCityAdapter;
import com.hr.ui.ui.main.adapter.MyProvinceAdapter;
import com.hr.ui.ui.resume.activity.ResumePersonalInfoActivity;
import com.hr.ui.ui.resume.activity.ResumeWorkExpActivity;
import com.hr.ui.utils.BaiDuLocationUtils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.view.MyFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

/**
 * Created by wdr on 2018/3/14.
 */

public class SelectCitySearchActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.tv_locationTag)
    TextView tvLocationTag;
    @BindView(R.id.tv_location_city)
    TextView tvLocationCity;
    @BindView(R.id.iv_fresh)
    ImageView ivFresh;
    @BindView(R.id.iv_freshIcon)
    ImageView ivFreshIcon;
    @BindView(R.id.ll_location)
    RelativeLayout llLocation;
    @BindView(R.id.view_selectCity)
    View viewSelectCity;
    @BindView(R.id.lv_selectProvince)
    ListView lvSelectProvince;
    @BindView(R.id.lv_selectCity)
    ListView lvSelectCity;
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
    private List<CityBean> cityListProvince = new ArrayList<>();
    private List<CityBean> cityListCity = new ArrayList<>();
    private List<CityBean> selectCityList = new ArrayList<>();
    private MyProvinceAdapter provinceAdapter;
    private MyCityAdapter cityAdapter;
    private AnimationDrawable AniDraw;
    public static SelectCitySearchActivity instance;
    private SharedPreferencesUtils sUtils;
    private int sum;
    private String cityName;
    private boolean isDingWei;

    @Override
    public int getLayoutId() {
        return R.layout.activity_selectcity;
    }
    public static void startAction(Activity activity, List<CityBean> selectCityList) {
        Intent intent = new Intent(activity, SelectCitySearchActivity.class);
        intent.putExtra("selectCityList", (Serializable) selectCityList);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public void initView() {
        instance = this;
        sUtils=new SharedPreferencesUtils(this);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        cityName=sUtils.getStringValue(Constants.CITYNAME,"");
        if("".equals(cityName)){
            tvLocationCity.setText("定位失败");
        }else {
            tvLocationCity.setText(cityName);
        }
        selectCityList= (List<CityBean>) getIntent().getSerializableExtra("selectCityList");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.selectCity);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AniDraw = (AnimationDrawable) ivFresh.getDrawable();
        initData();
    }

    private void initData() {
        final List<CityBean> cityList1= FromStringToArrayList.getInstance().getCityList();
        final List<CityBean> cityList2=FromStringToArrayList.getInstance().getCityList();
        cityListProvince=Utils.findProvinceCityList(cityList1);
        if(Utils.checkListIsNotEmpty(selectCityList)){
            Utils.setSelectCityInToOriginList(selectCityList,cityListProvince);
            for(int i=0;i<selectCityList.size();i++){
                addView(selectCityList.get(i));
                if(selectCityList.get(i).getName().contains(cityName)){
                    isDingWei=true;
                   Utils.setTvState(tvLocationCity,true);
                }
            }
        }
        handler.sendEmptyMessage(0);
        lvSelectProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>=4){
                    Utils.setCityListIsAllFalse(cityListProvince,4);
                    cityListProvince.get(position).setCheck(true);
                    cityListCity=Utils.findCityList(cityListProvince.get(position),cityList2);
                    cityListCity=Utils.setSelectCityInToOriginList2(selectCityList,cityListCity);
                    handler.sendEmptyMessage(1);
                }else{
                    if(cityListProvince.get(position).isCheck()){
                        if(cityListProvince.get(position).getName().contains(cityName)){
                            Utils.setTvState(tvLocationCity,false);
                            isDingWei=false;
                        }
                        cityListProvince.get(position).setCheck(false);
                        selectCityList=Utils.removeList(cityListProvince.get(position),selectCityList);
                        removeView(cityListProvince.get(position));
                    }else{
                        if(selectCityList.size()>=5){
                            ToastUitl.showShort("选择城市已经达到最大值");
                            return;
                        }else {
                            if(cityListProvince.get(position).getName().contains(cityName)){
                                Utils.setTvState(tvLocationCity,true);
                                isDingWei=true;
                            }
                            cityListProvince.get(position).setCheck(true);
                            selectCityList.add(cityListProvince.get(position));
                            addView(cityListProvince.get(position));
                        }
                    }
                }
                provinceAdapter.notifyDataSetChanged();
            }
        });
        lvSelectCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              if(position==0){
                  if(cityListCity.get(position).isCheck()){
                      cityListCity.get(position).setCheck(false);
                      Utils.removeList(cityListCity.get(position),selectCityList);
                      removeView(cityListCity.get(position));
                  }else{
                      Utils.setCityListIsAllFalse(cityListCity,0);
                      cityListCity.get(position).setCheck(true);
                      for(int i=0;i<selectCityList.size();i++){
                          if(selectCityList.get(i).getId().substring(0,2).equals(cityListCity.get(position).getId().substring(0,2))){
                              removeView(selectCityList.get(i));
                              selectCityList.remove(selectCityList.get(i));
                              i--;
                          }
                      }
                      if(selectCityList.size()>=5){
                          ToastUitl.showShort("选择城市已经达到最大值");
                          return;
                      }else {
                          selectCityList.add(cityListCity.get(position));
                          addView(cityListCity.get(position));
                      }
                  }
              }else{
                  if(cityListCity.get(position).isCheck()){
                      if(cityListCity.get(position).getName().contains(cityName)){
                          Utils.setTvState(tvLocationCity,false);
                          isDingWei=false;
                      }
                      cityListCity.get(position).setCheck(false);
                      Utils.removeList(cityListCity.get(position),selectCityList);
                      removeView(cityListCity.get(position));
                  }else{
                      if(cityListCity.get(position).getName().contains(cityName)){
                          Utils.setTvState(tvLocationCity,true);
                          isDingWei=true;
                      }

                      removeView(cityListCity.get(0));
                      Utils.removeList(cityListCity.get(0),selectCityList);
                      if(selectCityList.size()>=5){
                          ToastUitl.showShort("选择城市已经达到最大值");
                          return;
                      }else {
                          cityListCity.get(0).setCheck(false);
                          cityListCity.get(position).setCheck(true);
                          selectCityList.add(cityListCity.get(position));
                          addView(cityListCity.get(position));
                      }
                  }
              }
              cityAdapter.notifyDataSetChanged();
            }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    provinceAdapter=new MyProvinceAdapter(cityListProvince,2);
                    lvSelectProvince.setAdapter(provinceAdapter);
                    break;
                case 1:
                    cityAdapter=new MyCityAdapter(cityListCity);
                    lvSelectCity.setAdapter(cityAdapter);
                    break;
                case 3:
                    ivFreshIcon.setVisibility(View.VISIBLE);
                    ivFresh.setVisibility(View.GONE);
                    AniDraw.stop();
                    if ("".equals(cityName)) {
                        tvLocationCity.setText("定位失败");
                    } else {
                        ToastUitl.showShort("当前定位的城市："+cityName);
                        tvLocationCity.setText(cityName);
                        for(int i=0;i<selectCityList.size();i++){
                            if(selectCityList.get(i).getName().contains(cityName)){
                                isDingWei=true;
                                Utils.setTvState(tvLocationCity,true);
                                break;
                            }else{
                                isDingWei=false;
                                Utils.setTvState(tvLocationCity,false);
                            }
                        }
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance = this;
    }
    public void setCityName(String city) {
        this.cityName = city;
        handler.sendEmptyMessage(3);
    }
    @OnClick({R.id.tv_location_city, R.id.iv_freshIcon, R.id.tv_selectCityCancel, R.id.tv_selectCityOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location_city:
               if(!"".equals(cityName)&&selectCityList!=null){
                   if(isDingWei==false){
                       if(selectCityList.size()>=5){
                           ToastUitl.showShort("选择城市已经达到最大值");
                           return;
                       }else {
                           CityBean cityBean = new CityBean();
                           cityBean.setId(FromStringToArrayList.getInstance().getCityListId(cityName));
                           cityBean.setName(cityName);
                           selectCityList.add(cityBean);
                           addView(cityBean);
                           isDingWei = true;
                           Utils.setTvState(tvLocationCity, true);
                       }
                   }else{
                       for(int i=0;i<selectCityList.size();i++){
                           if(selectCityList.get(i).getName().contains(cityName)){
                               removeView(selectCityList.get(i));
                               selectCityList.remove(selectCityList.get(i));
                               break;
                           }
                       }
                       isDingWei=false;
                       Utils.setTvState(tvLocationCity,false);
                   }
                   getLeftDataFresh();
                   getRightDataFresh();
                   setNum();
               }
                break;
            case R.id.iv_freshIcon:
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        BaiDuLocationUtils.getInstance().stopLocation();
                        BaiDuLocationUtils.getInstance().initData();
                    }
                }, 1000);
                ivFreshIcon.setVisibility(View.GONE);
                ivFresh.setVisibility(View.VISIBLE);
                AniDraw.start();
                break;
            case R.id.tv_selectCityCancel:
                finish();
                break;
            case R.id.tv_selectCityOK:
                JobSerchActivity.instance.setPlace(selectCityList);
                finish();
                break;
        }
    }
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
        tv.setText(cityBean.getName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCityList.remove(cityBean);
                if(cityBean.getName().contains(cityName)){
                    Utils.setTvState(tvLocationCity,false);
                    isDingWei=false;
                }
                llSelectedCity.removeView(llSelectedCity.findViewWithTag(cityBean.getId()));
                getRightDataFresh();
                getLeftDataFresh();
                setNum();
            }
        });
        ll.setTag(cityBean.getId());
        llSelectedCity.addView(ll);
        setNum();
    }

    private void getRightDataFresh() {
        for(int i=0;i<cityListCity.size();i++){
            cityListCity.get(i).setCheck(false);
            for(int j=0;j<selectCityList.size();j++){
                if(cityListCity.get(i).getId().equals(selectCityList.get(j).getId())){
                    cityListCity.get(i).setCheck(true);
                    continue;
                }
            }
        }
        if(cityAdapter!=null){
            cityAdapter.notifyDataSetChanged();
        }
    }

    private void getLeftDataFresh() {
        for(int i=0;i<4;i++){
            cityListProvince.get(i).setCheck(false);
            for(int j=0;j<selectCityList.size();j++){
                if(cityListProvince.get(i).getId().equals(selectCityList.get(j).getId())){
                    cityListProvince.get(i).setCheck(true);
                    continue;
                }
            }
        }
        if(provinceAdapter!=null){
            provinceAdapter.notifyDataSetChanged();
        }
    }

    private void removeView(CityBean cityBean) {
        llSelectedCity.removeView(llSelectedCity.findViewWithTag(cityBean.getId()));
        setNum();
    }
    private void setNum() {
        sum = selectCityList.size();
        if (sum > 0) {
            rlSelectCityShow.setVisibility(View.VISIBLE);
        } else {
            rlSelectCityShow.setVisibility(View.GONE);
        }
        tvSelectCityNum.setText(sum + "");
    }
}
