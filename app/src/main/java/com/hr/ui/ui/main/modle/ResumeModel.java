package com.hr.ui.ui.main.modle;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.PictureBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.ui.main.fragment.ResumeContract;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.JsonUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/22.
 */

public class ResumeModel implements ResumeContract.Model {
    @Override
    public Observable<ResumeBean> getResume(String ResumeId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getResumeDate(ResumeId)))
                .map(new Func1<ResponseBody, ResumeBean>() {
                    @Override
                    public ResumeBean call(ResponseBody responseBody) {
                        ResumeBean resumeBean=new ResumeBean();
                        try {
                            String s=responseBody.string().toString();
                            resumeBean= new Gson().fromJson(s,ResumeBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return resumeBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeBean>io_main());
    }

    @Override
    public Observable<MultipleResumeBean> getResumeList() {
        return Api.getDefault(HostType.HR).getResumeList(EncryptUtils.encrypParams(ApiParameter.getResumeList()))
                .map(new Func1<MultipleResumeBean, MultipleResumeBean>() {
                    @Override
                    public MultipleResumeBean call(MultipleResumeBean multipleResumeBean) {
                        return multipleResumeBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<MultipleResumeBean>io_main());
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

    @Override
    public Observable<ResponseBody> setHide(String openstate) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.setHide(openstate)))
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
    public Observable<ResponseBody> updateResume(String resumeId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.updateResume(resumeId)))
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
