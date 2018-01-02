package com.hr.ui.ui.resume.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.ResumeTrainBean;
import com.hr.ui.bean.TrainExpData;
import com.hr.ui.ui.resume.activity.ResumeTrainActivity;
import com.hr.ui.ui.resume.contract.ResumeTrainExpContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeTrainModel implements ResumeTrainExpContract.Model{

    @Override
    public Observable<ResumeTrainBean> getTrainExpData(String trainId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getTrainExp(trainId)))
                .map(new Func1<ResponseBody, ResumeTrainBean>() {
                    @Override
                    public ResumeTrainBean call(ResponseBody responseBody) {
                        ResumeTrainBean resumeTrainBean=null;
                        try {
                            String s=responseBody.string().toString();
                            resumeTrainBean=new Gson().fromJson(s,ResumeTrainBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return resumeTrainBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeTrainBean>io_main());
    }

    @Override
    public Observable<ResponseBody> deleteExpData(String trainId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deleteTrainExp(trainId)))
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
    public Observable<ResponseBody> AddOrReplaceData(TrainExpData trainExpData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.addOrReplaceTrainExp(trainExpData)))
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
