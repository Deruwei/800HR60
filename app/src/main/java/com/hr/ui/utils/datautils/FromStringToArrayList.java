package com.hr.ui.utils.datautils;

import android.graphics.drawable.Drawable;
import android.media.audiofx.Equalizer;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.City;
import com.hr.ui.bean.CityBean;
import com.hr.ui.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    /**
     *
     * @return
     */
    public List<CityBean> getCityList(){
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
    public List<City> getCityList2() {
        List<City> cityBeanList = new ArrayList<>();
        String s = SaveFile.getDataFromInternalStorage(HRApplication.getAppContext(), "city_new.txt");
        if (!"".equals(s)) {
            cityBeanList = new Gson().fromJson(s, new TypeToken<List<City>>() {
            }.getType());
        }
        return cityBeanList;


    }
    public  static List<City> sortCityList(List<City> cityList){
        Collections.sort(cityList, new Comparator<City>(){
            /*
             * int compare(Person p1, Person p2) 返回一个基本类型的整型，
             * 返回负数表示：p1 小于p2，
             * 返回0 表示：p1和p2相等，
             * 返回正数表示：p1大于p2
             */
            public int compare(City p1, City p2) {
                //按照Person的年龄进行升序排列
                int n=p1.getPinyin().compareToIgnoreCase(p2.getPinyin());
                if(n>0){
                    return 1;
                }else if(n==0){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
        return  cityList;
    }
    public boolean checkIsMunicipality(String id){
        boolean b=false;
        switch (id.substring(0,2)){
            case "11":
                b=true;
                break;
            case "12":
                b=true;
                break;
            case "13":
                b=true;
                break;
            case "14":
                b=true;
                break;
            default:
                b=false;
                break;
        }
        if(id.contains("00")){
            b=false;
        }
        return b;
    }

    public CityBean getLocationCityBean(String name){
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
        CityBean cityBean=null;
        for(int i=0;i<cityBeanList.size();i++){
            if(cityBeanList.get(i).getName().contains(name)){
                cityBean=cityBeanList.get(i);
            }
        }
        return cityBean;


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
    public  String getExpectPositionName(String positionId,String industryId){
        List<CityBean> expectPositionList=new ArrayList<>();
        StringBuffer sb=new StringBuffer();
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
        String[] ids=positionId.split(",");
        for(int i=0;i<ids.length;i++){
            for(int j=0;j<expectPositionList.size();j++){
                if(expectPositionList.get(j).getId().equals(ids[i])){
                    sb.append(","+expectPositionList.get(j).getName());
                    continue;
                }
            }
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }
    public String getCityListId(String cityName) {
        List<CityBean> cityBeanList = new ArrayList<>();
        String cityId="";
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
        for (int i = 0; i < cityBeanList.size(); i++) {
                if(cityName.equals(cityBeanList.get(i).getName())) {
                   cityId=cityBeanList.get(i).getId();
                }
        }
        return cityId;
    }

    public String getCityName(String text) {
        List<CityBean> cityBeanList = new ArrayList<>();
        String cityName="";
        String name="";
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
        for (int i = 0; i < cityBeanList.size(); i++) {
            name=cityBeanList.get(i).getName();
            if(text.contains(name)){
                cityName=name;
            }else{
                if(name.contains("州")){
                    if(text.contains(name.substring(0,name.indexOf("州")))){
                        cityName=name;
                    }
                }
            }
        }

        return cityName;
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
        if(sb.length()>0) {
            sb.deleteCharAt(0);
        }
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
    public List<CityBean> getIndustryList2(){
        List<CityBean> industryList=new ArrayList<>();
        String[] sName=HRApplication.getAppContext().getResources().getStringArray(R.array.array_industry_zh2);
        String[] sId=HRApplication.getAppContext().getResources().getStringArray(R.array.array_industry_ids2);
        for(int i=0;i<sName.length;i++){
            CityBean cityBean=new CityBean();
            cityBean.setId(sId[i]);
            cityBean.setName(sName[i]);
            industryList.add(cityBean);
        }
        return industryList;
    }
    public String getIndustryName(String industryId){
        List<CityBean> industryList=new ArrayList<>();
        String industryName="";
        String[] sName=HRApplication.getAppContext().getResources().getStringArray(R.array.array_industry_zh);
        String[] sId=HRApplication.getAppContext().getResources().getStringArray(R.array.array_industry_ids);
        for(int i=0;i<sName.length;i++){
            CityBean cityBean=new CityBean();
            cityBean.setId(sId[i]);
            cityBean.setName(sName[i]);
            industryList.add(cityBean);
        }
        for(int i=0;i<industryList.size();i++){
            if(industryList.get(i).getId().equals(industryId)){
                industryName=industryList.get(i).getName();
                break;
            }
        }
        return industryName;
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
                if(cityBean.getId().contains("00")) {
                    expectFieldList.add(cityBean);
                }
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
      /*  for(int i=0;i<ids.length;i++){
            if("14".equals(industryId)) {
                if(ids[i].indexOf("|")!=-1) {
                   ids[i]=ids[i].substring(0,ids[i].indexOf("|"));
                }
            }
        }*/
        StringBuffer sb=new StringBuffer();
        for(int j=0;j<ids.length;j++) {
            for (int i = 0; i < expectPositionList.size(); i++) {
                if(ids[j].contains("|")) {
                    if (ids[j].substring(0,ids[j].indexOf("|")).equals(expectPositionList.get(i).getId())) {
                        if(Utils.checkMedicinePositionClass2(expectPositionList.get(i))==true){
                            sb.append("," + expectPositionList.get(i).getName() + "(" + "行政后勤" + ")");
                        }else {
                            sb.append("," + expectPositionList.get(i).getName() + "(" + Utils.getPositionClassName(ids[j].substring(ids[j].indexOf("|") + 1)) + ")");
                        }
                        continue;
                    }
                }else{
                    if (ids[j].equals(expectPositionList.get(i).getId())) {
                        sb.append("," + expectPositionList.get(i).getName());
                        continue;
                    }
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
        //Log.i("现在的数据",s);
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
    public  String getDataId(List<CityBean> dataList){
        if(dataList!=null&&!"".equals(dataList)&&dataList.size()!=0){
            StringBuffer sb=new StringBuffer();
            for(int i=0;i<dataList.size();i++){
                sb.append(","+dataList.get(i).getId());
            }
            sb.deleteCharAt(0);
            return sb.toString();
        }else{
            return "";
        }

    }
    public String getPositionId(List<CityBean> selectPositionList){
        if(selectPositionList!=null&&selectPositionList.size()!=0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < selectPositionList.size(); i++) {
                sb.append(","+selectPositionList.get(i).getId());
            }
            sb.deleteCharAt(0);
            return sb.toString();
        }
        return"";

    }
    public List<CityBean> getPositionClassList(){
        List<CityBean> list=new ArrayList<>();
        String[] names=HRApplication.getAppContext().getResources().getStringArray(R.array.positionClassName);
        String[] ids=HRApplication.getAppContext().getResources().getStringArray(R.array.positionClassId);
        for(int i=0;i<names.length;i++){
            CityBean cityBean=new CityBean();
            cityBean.setId(ids[i]);
            cityBean.setName(names[i]);
            cityBean.setCheck(false);
            list.add(cityBean);
        }
        return list;
    }
}
