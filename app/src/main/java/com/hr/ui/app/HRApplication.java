package com.hr.ui.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.DaoSession;
import com.baidu.mapapi.SDKInitializer;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.MySQLiteOpenHelper;
import com.service.AlamrReceiver;
import com.hr.ui.utils.GlideImageLoader;
import com.hr.ui.utils.UnCeHandler;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.mob.MobApplication;
import com.networkbench.agent.impl.NBSAppAgent;
import com.service.MyTimeService;
import com.umeng.commonsdk.UMConfigure;

import java.util.Calendar;

/**
 * Created by wdr on 2017/11/20.
 */

public class HRApplication extends MobApplication {
    private static HRApplication hrApplication;
    private  MySQLiteOpenHelper mHelper;
    private static DaoSession daoSession;
    private Intent mCodeTimerServiceIntent;
    private Calendar c;
    public static final String CODE = "connectCode";
    private String nbsAppKey="8a97e06a76944ee3886dafe60f20a809";

    public static HRApplication getAppContext() {
        return hrApplication;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        if(hrApplication == null)
            hrApplication = this;
        //init();
        SDKInitializer.initialize(getApplicationContext());
        initPhotoPicker();
        setupDatabase();
        //友盟初始化
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);//设置log日志
        UMConfigure.setEncryptEnabled(true); //设置是否加密传输log
        NBSAppAgent.setLicenseKey(nbsAppKey).withLocationServiceEnabled(true).startInApplication(this.getApplicationContext());//Appkey 请从官网获取
        Constants.SESSION_KEY=null;
        mCodeTimerServiceIntent = new Intent(HRApplication.getAppContext(), MyTimeService.class);
        mCodeTimerServiceIntent.setAction(CODE);
        initIsHaveNew();
    }
    private void initIsHaveNew() {
        c=Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY, 8);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Intent intent = new Intent(getAppContext(),AlamrReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(getAppContext(), 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);//设置闹钟
        am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), (24*60*60*1000), pi);//
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public void init(){
        //设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }
    private void initPhotoPicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(600);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(600);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);//保存文件的高度。单位像素
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
