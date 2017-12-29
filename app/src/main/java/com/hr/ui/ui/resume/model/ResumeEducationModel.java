package com.hr.ui.ui.resume.model;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.EducationData;
import com.hr.ui.ui.main.contract.EducationContract;
import com.hr.ui.ui.resume.contract.ResumeEducationContract;
import com.hr.ui.utils.EncryptUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/29.
 */

public class ResumeEducationModel implements ResumeEducationContract.Model {
    @Override
    public Observable<ResponseBody> addOrUpdateEducation(EducationData educationData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.sendEducationToResume(educationData)))
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
    public Observable<ResponseBody> getEducationInfo(String educationId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getEducation(educationId)))
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
    public Observable<ResponseBody> deleteEducation(String educationId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deleteEducation(educationId)))
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
