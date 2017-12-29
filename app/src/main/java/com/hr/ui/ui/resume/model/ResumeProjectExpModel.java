package com.hr.ui.ui.resume.model;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.ProjectExpData;
import com.hr.ui.ui.resume.contract.ResumeProjectExpContract;
import com.hr.ui.utils.EncryptUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/29.
 */

public class ResumeProjectExpModel implements ResumeProjectExpContract.Model {
    @Override
    public Observable<ResponseBody> getProjectInfo(String projectId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getProjectInfo(projectId)))
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
    public Observable<ResponseBody> deleteProjectInfo(String projectId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deleteProjectInfo(projectId)))
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
    public Observable<ResponseBody> addOrUpdateProjectInfo(ProjectExpData projectExpData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.addOrReplaceProjectExp(projectExpData)))
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
