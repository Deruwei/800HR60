package com.hr.ui.ui.main.modle;

import android.icu.text.Replaceable;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.PictureBean;
import com.hr.ui.bean.ResumeIdBean;
import com.hr.ui.ui.main.contract.PersonalInformationContract;
import com.hr.ui.utils.EncryptUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/13.
 */

public class PersonalInformationModel implements PersonalInformationContract.Model {
    @Override
    public Observable<ResponseBody> sendPersonalInformationToResume(PersonalInformationData personalInformationData) {
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
    public Observable<ResumeIdBean> setResumeType(String type) {
        return Api.getDefault(HostType.HR).setResumeType(EncryptUtils.encrypParams(ApiParameter.setResumeType(type)))
                .map(new Func1<ResumeIdBean, ResumeIdBean>() {
                    @Override
                    public ResumeIdBean call(ResumeIdBean resumeIdBean) {
                        return resumeIdBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeIdBean>io_main());
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
