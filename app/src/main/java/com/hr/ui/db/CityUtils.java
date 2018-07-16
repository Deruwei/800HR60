package com.hr.ui.db;

import com.afa.tourism.greendao.gen.CityDao;
import com.afa.tourism.greendao.gen.LoginBeanDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.City;
import com.hr.ui.bean.LoginBean;

import java.util.List;

public class CityUtils {
    public static void insertOrReplaceData(City city){
        HRApplication.getDaoInstant().getCityDao().insertOrReplace(city);
    }
    public static void insertData(City city){
        HRApplication.getDaoInstant().getCityDao().insert(city);
    }
    public static void insertData(List<City> cityList){
        HRApplication.getDaoInstant().getCityDao().insertInTx(cityList);
    }
    public static List<City> queryDataById(String name){
        return  HRApplication.getDaoInstant().getCityDao().queryBuilder().where(CityDao.Properties.Name.like("%"+name+"%")).list();
    }
    public static void deleteAll(){
        HRApplication.getDaoInstant().getCityDao().deleteAll();
    }
}
