package com.hr.ui.utils;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.hr.ui.app.HRApplication;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.job.activity.BaiDuMapActivity;
import com.hr.ui.ui.main.activity.JobSerchActivity;
import com.hr.ui.ui.main.activity.SelectCityActivity;
import com.hr.ui.ui.main.activity.SelectCitySearchActivity;
import com.hr.ui.ui.main.activity.SelectIndustryActivity;
import com.hr.ui.utils.datautils.FromStringToArrayList;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

/**
 * Created by wdr on 2018/1/24.
 */

public class MyLocationListener extends BDAbstractLocationListener {
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        String city=bdLocation.getCity();
        if("NULL".equals(city)||city==null||"".equals(city)){
            //ToastUitl.showShort("定位失败，请重新进行定位");
            city="";
        }else {
            /*sUtils.setStringValue(Constants.CITYNAME, city);*/
            if(city.contains("北京")||city.contains("上海")||city.contains("天津")||city.contains("重庆")){
               city= city.substring(0,city.indexOf("市"));
            }
           /* ToastUitl.showShort("当前定位到的城市：" + city);*/
        }
        city= FromStringToArrayList.getInstance().getCityName(city);
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        sUtils.setStringValue(Constants.CITYNAME,city);
       /* if(JobSerchActivity.instance!=null){
            JobSerchActivity.instance.setPlace2(city);
        }*/
        if(SelectCityActivity.instance!=null) {
            ToastUitl.showShort("当前定位的城市："+city);
            SelectCityActivity.instance.setCityName(city);
        }
        if(SelectCitySearchActivity.instance!=null){
            SelectCitySearchActivity.instance.setCityName(city);
        }
    }
}
