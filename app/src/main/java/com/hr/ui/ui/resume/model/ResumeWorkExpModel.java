package com.hr.ui.ui.resume.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.WorkExpBean;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.ui.resume.contract.ResumeWorkExpContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/29.
 */

public class ResumeWorkExpModel implements ResumeWorkExpContract.Model {
    @Override
    public Observable<WorkExpBean> getWorkExpInfo(String experienceId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getWorkExp(experienceId)))
                .map(new Func1<ResponseBody, WorkExpBean>() {
                    @Override
                    public WorkExpBean call(ResponseBody responseBody) {
                        WorkExpBean workExpBean=null;
                        try {
                            String s=responseBody.string().toString();
                            workExpBean=new Gson().fromJson(s,WorkExpBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return workExpBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<WorkExpBean>io_main());
    }

    @Override
    public Observable<ResponseBody> deleteWorkExp(String experienceId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deleteWorkExp(experienceId)))
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
    public Observable<ResponseBody> addOrUpdateWorkExp(WorkExpData workExpData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.sendWorkExpToResume(workExpData)))
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
