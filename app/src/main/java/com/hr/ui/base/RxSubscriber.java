package com.hr.ui.base;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hr.ui.R;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.ApiService;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.LoadingDialog;
import com.hr.ui.utils.NetWorkUtils;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.mob.tools.utils.LocationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
        this.mContext = context;
        this.msg = msg;
        this.showDialog=showDialog;
    }
    public RxSubscriber(Context context) {
        this(context, HRApplication.getAppContext().getString(R.string.loading),true);
    }
    public RxSubscriber(Context context,boolean showDialog) {
        this(context,HRApplication.getAppContext().getString(R.string.loading),showDialog);
    }

    @Override
    public void onCompleted() {
        if (showDialog)
            LoadingDialog.cancelDialogForLoading();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                LoadingDialog.showDialogForLoading((Activity) mContext,msg,true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        try {
            _onNext(t);
            String s=t.toString();
            s=s.substring(s.indexOf("{"),s.lastIndexOf("}")+1);
            //Log.i("当前的数据",s);
            JSONObject jsonObject=new JSONObject(s);
            String errorCode=jsonObject.getString("error_code");
            //Log.i("当前",""+errorCode);
            LoadingDialog.cancelDialogForLoading();
           if("204".equals(errorCode)||"203".equals(errorCode)||"205.2".equals(errorCode)||"303".equals(errorCode)) {
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
    public void onError(Throwable e) {
        Log.i("网络错误码",e.getMessage());
        if (showDialog) {
            LoadingDialog.cancelDialogForLoading();
        }
        e.printStackTrace();
        //网络
        if (!NetWorkUtils.isNetConnected(HRApplication.getAppContext())) {
            _onError(HRApplication.getAppContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
       /* else{
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
                }
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
}
