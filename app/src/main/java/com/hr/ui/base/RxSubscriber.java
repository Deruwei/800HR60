package com.hr.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.hr.ui.R;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.ApiService;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.ArrayInfoBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.job.activity.PositionPageActivity;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.LoadingDialog;
import com.hr.ui.utils.LoadingDialog2;
import com.hr.ui.utils.NetWorkUtils;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SaveFile;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.mob.tools.utils.LocationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * des:订阅封装
 *Created by wdr on 2017/11/20.
 */
/********************使用例子********************/
/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new RxSubscriber<User user>(mContext,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtil.showShort(mActivity, msg);
        });*/
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog=true;
    private boolean isShow;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog= true;
    }
    public void hideDialog() {
        this.showDialog= true;
    }

    public RxSubscriber(Context context, String msg,boolean showDialog) {
       // Log.i("到这里","shide3");
        this.mContext = context;
        this.msg = msg;
        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, HRApplication.getAppContext().getString(R.string.loading),true);
    }
    public RxSubscriber(Context context,boolean showDialog) {
        this(context,HRApplication.getAppContext().getString(R.string.loading),showDialog);
       // Log.i("到这里","shide2");
    }

    @Override
    public void onCompleted() {
        /*if (isShow==true)
            isShow=false;
            LoadingDialog.cancelDialogForLoading();*/
    }
    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                //Log.i("到这里","shide");
                if(mContext instanceof PositionPageActivity) {
                    LoadingDialog2.showDialogForLoading((Activity) mContext);
                }else{
                    LoadingDialog.showDialogForLoading(mContext, msg, true);
                }
               // Log.i("到这里","shide4");
                isShow=true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        try {
            if(isShow==true) {
                isShow=false;
                if(mContext instanceof PositionPageActivity) {
                    LoadingDialog2.cancelDialogForLoading();
                }else {
                    LoadingDialog.cancelDialogForLoading();
                }
            }
            _onNext(t);
            String s=t.toString();
            s=s.substring(s.indexOf("{"),s.lastIndexOf("}")+1);
            //Log.i("当前的数据",s);
            JSONObject jsonObject=new JSONObject(s);
            String errorCode=jsonObject.getString("error_code");
            //Log.i("当前",""+errorCode);
           if("204".equals(errorCode)||"203".equals(errorCode)||"205.1".equals(errorCode)||"205.2".equals(errorCode)||"303".equals(errorCode)) {
                //Rc4Md5Utils.secret_key = Constants.INIT_SECRET_KRY;
                //Log.i("当前","3");
                Constants.SESSION_KEY=null;
                getConnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onError(final Throwable e) {
       // Log.i("网络错误码",e.getMessage());
        /*if (showDialog) {
           *//* LoadingDialog.cancelDialogForLoading();
            ToastUitl.showShort(HRApplication.getAppContext().getString(R.string.net_error));*//*
        }*/
        //e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(HRApplication.getAppContext())) {
            if (isShow == true) {
                if (mContext instanceof PositionPageActivity) {
                    LoadingDialog2.cancelDialogForLoading();
                } else {
                    LoadingDialog.cancelDialogForLoading();
                }
            }
            _onError(HRApplication.getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            if (isShow == true) {
                if (mContext instanceof PositionPageActivity) {
                    LoadingDialog2.cancelDialogForLoading();
                } else {
                    LoadingDialog.cancelDialogForLoading();
                }
            }
            _onError(e.getMessage());
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isShow == true) {
                        if (mContext instanceof PositionPageActivity) {
                            LoadingDialog2.cancelDialogForLoading();
                        } else {
                            LoadingDialog.cancelDialogForLoading();
                        }
                        ToastUitl.showShort(HRApplication.getAppContext().getString(R.string.net_error));
                        _onError(HRApplication.getAppContext().getString(R.string.net_error));
                    }
                }
            }, 10000);

        }
      /*  else{
            ToastUitl.showShort(HRApplication.getAppContext().getString(R.string.net_error));
        }*/
    }

    protected abstract void _onNext(T t) throws IOException;

    protected abstract void _onError(String message);
    private void getConnect(){
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
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
                    sUtils.setStringValue(Constants.SESSION_KEY,baseBean.getSession_key());
                    Constants.SESSION_KEY=baseBean.getSession_key();
                    Rc4Md5Utils.secret_key = Constants.PRE_SECRET_KRY + baseBean.getSecret_key();
                    Constants.SVR_API_VER = baseBean.getSvr_api_ver();
                    // Log.i("数据的加载",baseBean.toString());
                    getArray();
                }
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
    private void getArray(){
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<ResponseBody> Object = RxService.getArrayInfo();
        Subscriber mSubscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                ArrayInfoBean arrayInfoBean=null;
                try {
                    String s=responseBody.string().toString();
                    arrayInfoBean=new Gson().fromJson(s,ArrayInfoBean.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SharedPreferencesUtils sUtils=new SharedPreferencesUtils(mContext);
                if(!sUtils.getStringValue(Constants.CITY_VER,"").equals(arrayInfoBean.getCity().getVer())){
                    getArrayData("client/file/array/city.php","city.txt");
                }
                if(!sUtils.getStringValue(Constants.JOB_VER,"").equals(arrayInfoBean.getJob().getVer())){
                    getArrayData("client/file/array/job.php","job.txt");
                }
                if(!sUtils.getStringValue(Constants.LINGYU_VER,"").equals(arrayInfoBean.getLingyu().getVer())){
                    getArrayData("client/file/array/lingyu.php","lingyu.txt");
                }
                sUtils.setStringValue(Constants.CITY_VER,arrayInfoBean.getCity().getVer());
                sUtils.setStringValue(Constants.JOB_VER,arrayInfoBean.getJob().getVer());
                sUtils.setStringValue(Constants.LINGYU_VER,arrayInfoBean.getLingyu().getVer());
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
    private void getArrayData(String path, final String fileName){
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<ResponseBody> Object = RxService.getLingYuArray(path);
        Subscriber mSubscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String s= responseBody.string().toString();
                    SaveFile.updateCJ(mContext,fileName,s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
}
