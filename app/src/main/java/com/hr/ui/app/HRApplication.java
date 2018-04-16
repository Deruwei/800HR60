package com.hr.ui.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.DaoSession;
import com.baidu.mapapi.SDKInitializer;
import com.caption.netmonitorlibrary.netStateLib.NetStateReceiver;
import com.hr.ui.R;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.HMROpenHelper;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;
import com.service.AlamrReceiver;
import com.hr.ui.utils.GlideImageLoader;
import com.hr.ui.utils.UnCeHandler;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.mob.MobApplication;
import com.networkbench.agent.impl.NBSAppAgent;
import com.service.MyJobService;
import com.umeng.commonsdk.UMConfigure;

import java.util.Calendar;

/**
 * Created by wdr on 2017/11/20.
 */

public class HRApplication extends MobApplication {
    private static HRApplication hrApplication;
    private static DaoSession daoSession;
    private Calendar c;
    private int refCount = 0;
    public static final String CODE = "connectCode";
    private String nbsAppKey="8a97e06a76944ee3886dafe60f20a809";
    private CountDownTimer countDownTimer;

    public static HRApplication getAppContext() {
        return hrApplication;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    countDownTimer= new CountDownTimer(1000*60*20, 1000*60*20) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            AppManager.getAppManager().exitApp();
                        }
                        @Override
                        public void onFinish() {

                        }
                    };
                    break;
                case 1:
                    if(countDownTimer!=null) {
                        countDownTimer.cancel();
                    }
                    break;
            }
        }
    };
    @Override

    public void onCreate() {
        super.onCreate();
        if(hrApplication == null)
            hrApplication = this;
        init();
        SDKInitializer.initialize(getApplicationContext());
        initPhotoPicker();
        setupDatabase();
        //内存泄漏检测工具的初始化
         /*开启网络广播监听*/
        NetStateReceiver.registerNetworkStateReceiver(this);
        //友盟初始化
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);//设置log日志
        UMConfigure.setEncryptEnabled(true); //设置是否加密传输log
        //听云
        NBSAppAgent.setLicenseKey(nbsAppKey).withLocationServiceEnabled(true).startInApplication(this.getApplicationContext());//Appkey 请从官网获取
        Constants.SESSION_KEY=null;
        //设置每天8点左右消息的就业指导
        initIsHaveNew();
        //设置监听app前后台监听
        registerAppCallback();
    }
    private void registerAppCallback() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                refCount++;
                if(refCount>0){
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                refCount--;
                if(refCount == 0){
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
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
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        NetStateReceiver.unRegisterNetworkStateReceiver(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        HMROpenHelper mHelper = new HMROpenHelper(this, "resume.db", null);//为数据库升级封装过的使用方式
        //获取可写数据库
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
