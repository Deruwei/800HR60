package com.hr.ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.LoginBeanDao;
import com.afa.tourism.greendao.gen.ResumeDataDao;
import com.afa.tourism.greendao.gen.ScanHistoryBeanDao;
import com.afa.tourism.greendao.gen.SearchHistoryBeanDao;
import com.afa.tourism.greendao.gen.ThirdLoginBeanDao;

import org.greenrobot.greendao.database.Database;

/**
 * 类名：HMROpenHelper
 * 类描述：用于数据库升级的工具类
 * 创建人：孙广竹
 * 创建日期： 2016/10/20.
 * 版本：V1.0
 */

public class HMROpenHelper extends DaoMaster.OpenHelper {

    public HMROpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新
        MigrationHelper.migrate(db, LoginBeanDao.class, ResumeDataDao.class, ScanHistoryBeanDao.class, SearchHistoryBeanDao.class, ThirdLoginBeanDao.class);
    }

}
