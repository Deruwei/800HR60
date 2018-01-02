package com.hr.ui.ui.resume.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.LanguageLevelData;
import com.hr.ui.bean.ResumeLanguageBean;
import com.hr.ui.ui.resume.contract.ResumeLanguageContract;
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

public class ResumeLanguageModel implements ResumeLanguageContract.Model {
    @Override
    public Observable<ResumeLanguageBean> getLanguage(String languageId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getLanguage(languageId)))
                .map(new Func1<ResponseBody, ResumeLanguageBean>() {
                    @Override
                    public ResumeLanguageBean call(ResponseBody responseBody) {
                        ResumeLanguageBean resumeLanguageBean=null;
                        try {
                            String s=responseBody.string().toString();
                            resumeLanguageBean=new Gson().fromJson(s,ResumeLanguageBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return resumeLanguageBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeLanguageBean>io_main());
    }

    @Override
    public Observable<ResponseBody> deleteLanguage(String languageId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deleteLanguageLevel(languageId)))
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
    public Observable<ResponseBody> addOrReplace(LanguageLevelData languageLevelData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.addOrReplaceLanguageLevel(languageLevelData)))
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
