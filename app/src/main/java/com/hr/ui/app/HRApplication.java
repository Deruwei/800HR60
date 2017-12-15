package com.hr.ui.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.DaoSession;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.MySQLiteOpenHelper;
import com.hr.ui.utils.ToastUitl;
import com.mob.MobApplication;

/**
 * Created by wdr on 2017/11/20.
 */

public class HRApplication extends MobApplication {
    private static HRApplication hrApplication;
    private  MySQLiteOpenHelper mHelper;
    private static DaoSession daoSession;

    public static HRApplication getAppContext() {
        return hrApplication;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        if(hrApplication == null)
            hrApplication = this;
        setupDatabase();
    }
    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "resume.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
