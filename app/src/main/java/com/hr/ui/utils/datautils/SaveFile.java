package com.hr.ui.utils.datautils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.github.promeg.pinyinhelper.Pinyin;
import com.google.gson.Gson;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.City;
import com.hr.ui.bean.CityBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.CityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wdr on 2017/12/14.
 */

public class SaveFile {
    public static void updateCJ(Context context, String filename,String json)
            throws IOException, FileNotFoundException {
        if(filename.equals("city_new.txt")){
            json=getCityJson(json);
        }
        // 要写入的文本
        try {
            FileOutputStream fos = context.openFileOutput(filename,
                    MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(json);
            //osw.write("hello Wrold !");
            osw.flush();
            fos.flush();
            osw.close();
            fos.close();
           System.out.println("往data/包名/files/ 路径下创建文本文件成功，注意查收");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getCityJson(String s){
        List<City> cityList=new ArrayList<>();
            JSONArray cityJSONArray= null;
            try {
                cityJSONArray = new JSONArray(s);
                for (int i = 0; i < cityJSONArray.length(); i++) {
                    City city=new City();
                    JSONObject object = cityJSONArray.getJSONObject(i);
                    Iterator it=object.keys();
                    while(it.hasNext()){
                        String key=(String) it.next();
                        city.setCode(key);
                        city.setName(object.getString(key));
                        city.setPinyin(Pinyin.toPinyin(object.getString(key),""));
                    }
                    cityList.add(city);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i=0;i< cityList.size();i++){
                if(FromStringToArrayList.getInstance().checkIsMunicipality( cityList.get(i).getCode())){
                    cityList.remove( cityList.get(i));
                    i--;
                }
            }
            CityUtils.insertData(cityList);
            List<City> provinceIdList=new ArrayList<>();
            for(int i=0;i<cityList.size();i++){
                if(cityList.get(i).getCode().contains("00")){
                    City city=new City();
                    city.setName(cityList.get(i).getName());
                    city.setCode(cityList.get(i).getCode().substring(0,2));
                    provinceIdList.add(city);
                }
            }
            for(int i=0;i<provinceIdList.size();i++){
                for(int j=0;j<cityList.size();j++){
                    if(cityList.get(j).getCode().substring(0,2).equals(provinceIdList.get(i).getCode())){
                        cityList.get(j).setProvince(provinceIdList.get(i).getName());
                    }
                }
            }
            s=new Gson().toJson(cityList);
            return  s;
    }
    //判断文件是否存在
    public static boolean fileIsExists(String strFile)
    {
        try
        {
            FileInputStream fin = HRApplication.getAppContext().openFileInput(strFile);
            if(fin.available()==0)
            {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static String getDataFromInternalStorage(Context context,String fileName){
        String s="";
        try {

            FileInputStream fps = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fps, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sBuilder = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sBuilder.append(line);
            }
           s=sBuilder.toString();
           System.out.println("读取成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;

    }
}
