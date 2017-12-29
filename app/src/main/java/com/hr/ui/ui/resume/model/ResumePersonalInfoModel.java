package com.hr.ui.ui.resume.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.PictureBean;
import com.hr.ui.bean.ResumePersonalInfoBean;
import com.hr.ui.ui.resume.contract.ResumePersonalInfoContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/25.
 */

public class ResumePersonalInfoModel implements ResumePersonalInfoContract.Model {
    @Override
    public Observable<ResumePersonalInfoBean> getPersonalInfo() {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getResumePersonnalInfo()))
                .map(new Func1<ResponseBody, ResumePersonalInfoBean>() {
                    @Override
                    public ResumePersonalInfoBean call(ResponseBody responseBody) {
                        String s="";
                        try {s=responseBody.string().toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return new Gson().fromJson(s,ResumePersonalInfoBean.class);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumePersonalInfoBean>io_main());
    }

    @Override
    public Observable<ResponseBody> UpdatePersonalInfo(PersonalInformationData personalInformationData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.sendPersonalInformationToResume(personalInformationData)))
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
    public Observable<PictureBean> upLoadImage(String content) {
        return Api.getDefault(HostType.HR).uploadImage(EncryptUtils.encrypParams(ApiParameter.upLoadImage(content)))
                .map(new Func1<PictureBean, PictureBean>() {
                    @Override
                    public PictureBean call(PictureBean pictureBean) {
                        return pictureBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<PictureBean>io_main());
    }
}
