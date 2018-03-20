package com.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.ApiService;
import com.hr.ui.api.HostType;
import com.hr.ui.utils.EncryptUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/3/14.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private final static String TAG=MyJobService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        startForeground(2, new Notification());
        Log.d(TAG, "onStartJob: ");
        if(isNetworkConnected()){
            new WebDownLoadTask().execute(params);
            return true;
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: ");
        return false;
    }
    private boolean isNetworkConnected(){
        ConnectivityManager connManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    private class WebDownLoadTask extends AsyncTask<JobParameters,Void,String> {
        protected  JobParameters mJobParam;
        @Override
        protected String doInBackground(JobParameters... params) {
            Log.d(TAG, "doInBackground: ");
            mJobParam=params[0];
            getConnect();
            return "";
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            jobFinished(mJobParam,false);
            Log.d(TAG, "onPostExecute: ");
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
    private void getConnect(){
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<ResponseBody> Object = RxService.getResponseString(EncryptUtils.encrypParams(ApiParameter.getAllInfo()));
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
                SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
                String   str   =   formatter.format(curDate);
                System.out.println("连接中："+str);
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }
}