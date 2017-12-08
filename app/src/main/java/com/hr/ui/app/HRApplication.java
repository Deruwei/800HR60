package com.hr.ui.app;

import android.app.Application;
import android.content.Context;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.DaoSession;
import com.hr.ui.constants.Constants;
import com.mob.MobApplication;

/**
 * Created by wdr on 2017/11/20.
 */

public class HRApplication extends MobApplication {
    private static HRApplication hrApplication;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static Context getAppContext() {
        return hrApplication;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        if(hrApplication == null)
            hrApplication = this;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
