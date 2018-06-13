package com.hr.ui.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.bean.City;
import com.hr.ui.bean.CityBean;
import com.hr.ui.bean.HotCity;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.adapter.MySelectCityByLetterRightAdapter;
import com.hr.ui.ui.main.adapter.MySelectCityLetterAdapter;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.hr.ui.utils.recyclerviewutils.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCityByLetterActivity extends BaseNoConnectNetworkAcitivty {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.rv_selectCityByLetterLeft)
    RecyclerView rvSelectCityByLetterLeft;
    @BindView(R.id.rv_selectCityByLetterRight)
    RecyclerView rvSelectCityByLetterRight;
    @BindView(R.id.tv_selectCityByLetter_SelectLetter)
    TextView tvSelectCityByLetterSelectLetter;
    private List<City> mTotalCityList;
    private String cityName;
    private List<City> selectCityList=new ArrayList<>();
    private List<City> mHotCityList = new ArrayList<>();
    private List<City> mLocationCityList=new ArrayList<>();
    private SharedPreferencesUtils sUtils;
    private static final String[] DEFAULT_INDEX_ITEMS = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private MySelectCityLetterAdapter adapter;
    private MySelectCityByLetterRightAdapter adapterRight;
    private LinearLayoutManager managerLeft, managerRight;

    public static void startAction(Activity activity, List<CityBean> selectCityList) {
        Intent intent = new Intent(activity, SelectCityByLetterActivity.class);
        intent.putExtra("selectCityList", (Serializable) selectCityList);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_selectcity_byletter;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setTitle("");
        sUtils=new SharedPreferencesUtils(this);
        cityName = sUtils.getStringValue(Constants.CITYNAME, "");
        if ("".equals(cityName)) {
            cityName="定位失败";
        }
        mTotalCityList = FromStringToArrayList.getInstance().getCityList2();
        for(int i=0;i<mTotalCityList.size();i++){
            if(cityName.contains(mTotalCityList.get(i).getName())){
                mLocationCityList.add(mTotalCityList.get(i));
                break;
            }
        }
        List<CityBean> list= (List<CityBean>) getIntent().getSerializableExtra("selectCityList");
        if(list!=null&&list.size()!=0){
            for(int i=0;i<list.size();i++){
                for(int j=0;j<mTotalCityList.size();j++){
                    if(list.get(i).getId().equals(mTotalCityList.get(j).getCode())){
                        mTotalCityList.get(j).setCheck(true);
                        selectCityList.add(mTotalCityList.get(j));
                        break;
                    }
                }
            }
        }
        initLocatedCity();
        for (int i = 0; i < 4; i++) {
            mHotCityList.add(mTotalCityList.get(i));
        }
        mTotalCityList = FromStringToArrayList.getInstance().sortCityList(mTotalCityList);
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.selectCity);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        managerLeft = new LinearLayoutManager(this);
        managerLeft.setOrientation(LinearLayoutManager.VERTICAL);
        rvSelectCityByLetterLeft.setLayoutManager(managerLeft);

        managerRight = new LinearLayoutManager(this);
        managerRight.setOrientation(LinearLayoutManager.VERTICAL);
        rvSelectCityByLetterRight.setLayoutManager(managerRight);

        adapter = new MySelectCityLetterAdapter(this);
        adapter.setLetters(DEFAULT_INDEX_ITEMS);
        adapter.setDate(mLocationCityList, mHotCityList, mTotalCityList);
        adapter.setSelectCityList(selectCityList);
        rvSelectCityByLetterLeft.setAdapter(adapter);

        adapterRight = new MySelectCityByLetterRightAdapter();
        adapterRight.setLetters(DEFAULT_INDEX_ITEMS);
        rvSelectCityByLetterRight.setAdapter(adapterRight);
        adapterRight.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemCLick(View view, int position) {
                managerLeft.scrollToPositionWithOffset(position, 0);
                tvSelectCityByLetterSelectLetter.setVisibility(View.VISIBLE);
                tvSelectCityByLetterSelectLetter.setText(DEFAULT_INDEX_ITEMS[position]);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvSelectCityByLetterSelectLetter.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });
    }

    private void initLocatedCity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
