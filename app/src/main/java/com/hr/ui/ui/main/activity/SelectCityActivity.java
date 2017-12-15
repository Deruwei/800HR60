package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.ArrayInfoBean;
import com.hr.ui.bean.CityBean;
import com.hr.ui.ui.main.adapter.MyCityAdapter;
import com.hr.ui.ui.main.adapter.MyProvinceAdapter;
import com.hr.ui.utils.datautils.FromStringToArrayList;

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
    public List<CityBean> cityBeanList;
    private List<CityBean> provinceCityList,cityList;
    private MyProvinceAdapter myProvinceAdapter;
    private MyCityAdapter myCityAdapter;
    private int type; //1 代表选择一个城市 2代表选择多个城市
    private String tag;//来自于那个Activity
    @Override
    public int getLayoutId() {
        return R.layout.activity_selectcity;
    }
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity,int type,String tag) {
        Intent intent = new Intent(activity, SelectCityActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("tag",tag);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
    @Override
    public void initView() {
        type=getIntent().getIntExtra("type",1);
        tag=getIntent().getStringExtra("tag");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        provinceCityList=new ArrayList<>();
        cityBeanList=FromStringToArrayList.getInstance().getCityList("city.txt");
        for (int i=0;i<cityBeanList.size();i++){
            if(cityBeanList.get(i).getId().endsWith("00")){
                provinceCityList.add(cityBeanList.get(i));
            }
        }
        Message message=Message.obtain();
        message.what=1;
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
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    myProvinceAdapter=new MyProvinceAdapter(provinceCityList);
                    lvSelectProvince.setAdapter(myProvinceAdapter);
                    lvSelectProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            cityList=new ArrayList<>();
                            if(position>=4) {
                                for (int i = 0; i < cityBeanList.size(); i++) {
                                    if (provinceCityList.get(position).getId().substring(0, 2).equals(cityBeanList.get(i).getId().substring(0, 2)) && !provinceCityList.get(position).getId().equals(cityBeanList.get(i).getId())) {
                                        cityList.add(cityBeanList.get(i));

                                    }
                                }
                            }else{
                                if(type==2) {
                                    provinceCityList.get(position).setCheck(true);
                                    myProvinceAdapter.notifyDataSetChanged();
                                }else{
                                    if(PersonalInformationActivity.TAG.equals(tag)) {
                                        PersonalInformationActivity.instance.setSelectCity(provinceCityList.get(position));
                                    }else if(WorkExpActivity.TAG.equals(tag)){
                                        WorkExpActivity.instance.setSelectCity(provinceCityList.get(position));
                                    }
                                    finish();
                                }
                            }
                            Message message = Message.obtain();
                            message.what = 2;
                            handler.sendMessage(message);
                        }
                    });
                    break;
                case 2:
                    myCityAdapter=new MyCityAdapter(cityList);
                    lvSelectCity.setAdapter(myCityAdapter);
                    lvSelectCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(type==1){
                                if(PersonalInformationActivity.TAG.equals(tag)) {
                                    PersonalInformationActivity.instance.setSelectCity(provinceCityList.get(position));
                                }else if(WorkExpActivity.TAG.equals(tag)){
                                    WorkExpActivity.instance.setSelectCity(provinceCityList.get(position));
                                }
                                finish();
                            }else{

                            }
                        }
                    });
                    break;
            }
        }
    };
}
