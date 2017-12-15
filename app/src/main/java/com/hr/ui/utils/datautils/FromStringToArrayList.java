package com.hr.ui.utils.datautils;

import android.util.Log;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.CityBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wdr on 2017/12/14.
 */

public class FromStringToArrayList {
    private static FromStringToArrayList instance;
    public static FromStringToArrayList getInstance(){
        if(instance==null){
            instance=new FromStringToArrayList();
        }
        return instance;
    }
    public List<CityBean> getCityList(String fileName){
        List<CityBean> cityBeanList=new ArrayList<>();
        String s=SaveFile.getDataFromInternalStorage(HRApplication.getAppContext(),"city.txt");
        try {
            JSONArray cityJSONArray=new JSONArray(s);
            for (int i = 0; i < cityJSONArray.length(); i++) {
                CityBean cityBean=new CityBean();
                JSONObject object = cityJSONArray.getJSONObject(i);
                Iterator it=object.keys();
                while(it.hasNext()){
                    String key=(String) it.next();
                    cityBean.setId(key);
                    cityBean.setName(object.getString(key));
                    cityBean.setCheck(false);
                }
                cityBeanList.add(cityBean);
            }
            //System.out.println("cityBean"+cityBeanList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityBeanList;
    }
    public  List<CityBean> getExpectPosition(String industryId){
        List<CityBean> expectPositionList=new ArrayList<>();
        String s=SaveFile.getDataFromInternalStorage(HRApplication.getAppContext(),"job.txt");
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray(industryId);
            for (int i = 0; i < jsonArray.length(); i++) {
                CityBean cityBean=new CityBean();
                JSONObject object = jsonArray.getJSONObject(i);
                Iterator it=object.keys();
                while(it.hasNext()){
                    String key=(String) it.next();
                    cityBean.setId(key);
                    cityBean.setName(object.getString(key));
                    cityBean.setCheck(false);
                }
                expectPositionList.add(cityBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expectPositionList;
    }
    public List<CityBean> getIndustryList(){
        List<CityBean> industryList=new ArrayList<>();
        String[] sName=HRApplication.getAppContext().getResources().getStringArray(R.array.array_industry_zh);
        String[] sId=HRApplication.getAppContext().getResources().getStringArray(R.array.array_industry_ids);
        for(int i=0;i<sName.length;i++){
            CityBean cityBean=new CityBean();
            cityBean.setId(sId[i]);
            cityBean.setName(sName[i]);
            industryList.add(cityBean);
        }
        return industryList;
    }
    public  List<CityBean> getExpectField(String industryId){
        List<CityBean> expectFieldList=new ArrayList<>();
        String s=SaveFile.getDataFromInternalStorage(HRApplication.getAppContext(),"lingyu.txt");
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray(industryId);
            for (int i = 0; i < jsonArray.length(); i++) {
                CityBean cityBean=new CityBean();
                JSONObject object = jsonArray.getJSONObject(i);
                Iterator it=object.keys();
                while(it.hasNext()){
                    String key=(String) it.next();
                    cityBean.setId(key);
                    cityBean.setName(object.getString(key));
                    cityBean.setCheck(false);
                }
                expectFieldList.add(cityBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expectFieldList;
    }
}
