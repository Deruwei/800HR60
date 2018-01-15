package com.hr.ui.ui.job.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.ui.job.contract.PositionPageContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/11.
 */

public class PositionPageModel implements PositionPageContract.Model {

    @Override
    public Observable<PositionBean> getPositionData(String jobId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getPositionData(jobId)))
                .map(new Func1<ResponseBody, PositionBean>() {
                    @Override
                    public PositionBean call(ResponseBody responseBody) {
                        PositionBean positionBean=null;
                        try {
                            String s=responseBody.string().toString();
                            positionBean=new Gson().fromJson(s,PositionBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return positionBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<PositionBean>io_main());
    }

    @Override
    public Observable<ResponseBody> collectionPosition(String jobId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.collectionJob(jobId)))
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ResponseBody> deliverPosition(String jobId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deliverJob(jobId)))
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResponseBody>io_main());
    }
}
