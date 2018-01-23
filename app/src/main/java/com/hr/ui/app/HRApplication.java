package com.hr.ui.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.afa.tourism.greendao.gen.DaoMaster;
import com.afa.tourism.greendao.gen.DaoSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.ApiService;
import com.hr.ui.api.HostType;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.MySQLiteOpenHelper;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.GlideImageLoader;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.UnCeHandler;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.mob.MobApplication;
import com.service.CodeTimerService;
import com.service.MyTimeService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/11/20.
 */

public class HRApplication extends MobApplication {
    private static HRApplication hrApplication;
    private  MySQLiteOpenHelper mHelper;
    private static DaoSession daoSession;
    private Intent mCodeTimerServiceIntent;
    public static final String CODE = "connectCode";

    public static HRApplication getAppContext() {
        return hrApplication;
    }

    @Override

    public void onCreate() {
        super.onCreate();
        if(hrApplication == null)
            hrApplication = this;
        init();
        initPhotoPicker();
        setupDatabase();
        mCodeTimerServiceIntent = new Intent(HRApplication.getAppContext(), MyTimeService.class);
        mCodeTimerServiceIntent.setAction(CODE);
        //注册接收验证码计时器信息的广播
        IntentFilter filter = new IntentFilter(CODE);
        registerReceiver(mCodeTimerReceiver, filter);
    }
    public void init(){
        //设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }
    public void setService(){
        if(mCodeTimerServiceIntent!=null) {
            startService(mCodeTimerServiceIntent);//启动服务
        }
    }
    /**
     * 验证码倒计时的广播
     */
    private BroadcastReceiver mCodeTimerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (CODE.equals(action)) {
                //接收信息，改变button的点击状态和text
                if(intent.getBooleanExtra("noSession",false)==true) {
                    startService(mCodeTimerServiceIntent);//启动服务
                    connect();
                }
            }
        }
    };
    private void connect(){
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<BaseBean> Object = RxService.getConnect(EncryptUtils.encrypParams(ApiParameter.getConnect(HRApplication.getAppContext())));
        Subscriber mSubscriber = new Subscriber<BaseBean>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean.getError_code()==0){
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(hrApplication.getApplicationContext());
                    sUtils.setStringValue(Constants.SESSION_KEY,baseBean.getSession_key());
                    Constants.SESSION_KEY=baseBean.getSession_key();
                    Rc4Md5Utils.secret_key = Constants.PRE_SECRET_KRY + baseBean.getSecret_key();
                    Constants.SVR_API_VER = baseBean.getSvr_api_ver();
                   // Log.i("数据的加载",baseBean.toString());

                }
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
    private void initPhotoPicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
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
