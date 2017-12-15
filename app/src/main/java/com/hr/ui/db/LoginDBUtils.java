package com.hr.ui.db;

import com.afa.tourism.greendao.gen.LoginBeanDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.LoginBean;

import java.util.List;

/**
 * Created by wdr on 2017/12/12.
 */

public class LoginDBUtils {
    public static void insertOrReplaceData(LoginBean loginBean){
        HRApplication.getDaoInstant().getLoginBeanDao().insertOrReplace(loginBean);
    }
    public static void insertData(LoginBean loginBean){
        HRApplication.getDaoInstant().getLoginBeanDao().insertOrReplace(loginBean);
    }
    public static LoginBean queryDataById(String type){
        return  HRApplication.getDaoInstant().getLoginBeanDao().queryBuilder().where(LoginBeanDao.Properties.LoginType.eq(type)).build().list().get(0);
    }
    public static void deleteByType(String type){
        HRApplication.getDaoInstant().getLoginBeanDao().delete(HRApplication.getDaoInstant().getLoginBeanDao().queryBuilder().where(LoginBeanDao.Properties.LoginType.eq(type)).unique());
    }
}
