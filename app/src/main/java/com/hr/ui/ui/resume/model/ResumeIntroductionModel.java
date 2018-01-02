package com.hr.ui.ui.resume.model;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.ui.resume.contract.ResumeIntroductionContract;
import com.hr.ui.utils.EncryptUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeIntroductionModel implements ResumeIntroductionContract.Model {
    @Override
    public Observable<ResponseBody> getIntroduction() {
        return  Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getIntroduction()))
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
    public Observable<ResponseBody> addOrReplaceIntroduction(String content) {
        return  Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.addOrReplaceIntroduction(content)))
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
