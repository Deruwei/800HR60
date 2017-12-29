package com.hr.ui.utils.datautils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
    public String getCityListName(String text) {
        List<CityBean> cityBeanList = new ArrayList<>();
        String s = SaveFile.getDataFromInternalStorage(HRApplication.getAppContext(), "city.txt");
        try {
            JSONArray cityJSONArray = new JSONArray(s);
            for (int i = 0; i < cityJSONArray.length(); i++) {
                CityBean cityBean = new CityBean();
                JSONObject object = cityJSONArray.getJSONObject(i);
                Iterator it = object.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
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
        String[] ids=text.split(",");
        StringBuffer sb=new StringBuffer();
        for(int j=0;j<ids.length;j++) {
            for (int i = 0; i < cityBeanList.size(); i++) {
                if(ids[j].equals(cityBeanList.get(i).getId())) {
                    sb.append(","+cityBeanList.get(i).getName());
                    continue;
                }
            }

        }
        sb.deleteCharAt(0);
        return sb.toString();
    }
    public List<CityBean> getSelectCityList(String text) {
        List<CityBean> cityBeanList = new ArrayList<>();
        List<CityBean> selectCityBeanList=new ArrayList<>();
        String s = SaveFile.getDataFromInternalStorage(HRApplication.getAppContext(), "city.txt");
        try {
            JSONArray cityJSONArray = new JSONArray(s);
            for (int i = 0; i < cityJSONArray.length(); i++) {
                CityBean cityBean = new CityBean();
                JSONObject object = cityJSONArray.getJSONObject(i);
                Iterator it = object.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
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
        String[] ids=text.split(",");
        for(int j=0;j<ids.length;j++) {
            for (int i = 0; i < cityBeanList.size(); i++) {
                if(ids[j].equals(cityBeanList.get(i).getId())) {
                    cityBeanList.get(i).setCheck(true);
                    selectCityBeanList.add(cityBeanList.get(i));
                    continue;
                }
            }

        }
        return selectCityBeanList;
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

    public  String getExpectPositionString(String industryId,String text){
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
        String[] ids=text.split(",");
        StringBuffer sb=new StringBuffer();
        for(int j=0;j<ids.length;j++) {
            for (int i = 0; i < expectPositionList.size(); i++) {
                if(ids[j].equals(expectPositionList.get(i).getId())) {
                    sb.append(","+expectPositionList.get(i).getName());
                    continue;
                }
            }

        }
        if(sb.length()!=0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
    public Drawable getIndustryIcon(String industryId){
        if("11".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.build);
        }else if("14".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.medicine);
        }else if ("29".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.chemical);
        }else if("12".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.finance);
        }else if("22".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.mechine);
        }else if("26".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.cloth);
        } else if("15".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.teach);
        } else if("23".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.it);
        } else if("40".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.restaurant);
        } else if("16".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.transport);
        }else if("21".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.communication);
        }else if("20".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.power);
        }else if("30".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.tour);
        } else if("19".equals(industryId)){
            return ContextCompat.getDrawable(HRApplication.getAppContext(),R.mipmap.electron);
        }
        return null;
    }
    public boolean getIndustryIsHaveField(String industryId){
        if("11".equals(industryId)){
           return true;
        }else if("14".equals(industryId)){
            return true;
        }else if ("29".equals(industryId)){
            return true;
        }else if("12".equals(industryId)){
            return true;
        }else if("22".equals(industryId)){
            return true;
        }else {
            return false;
        }
    }
    public  String getExpectFieldName(String industryId,String text){
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
        String[] ids=text.split(",");
        StringBuffer sb=new StringBuffer();
        for(int j=0;j<ids.length;j++) {
            for (int i = 0; i <  expectFieldList.size(); i++) {
                if(ids[j].equals( expectFieldList.get(i).getId())) {
                    sb.append(","+ expectFieldList.get(i).getName());
                    continue;
                }
            }

        }
        if(sb.length()>0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
}
