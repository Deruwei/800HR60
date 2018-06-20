package com.hr.ui.ui.login.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.ui.login.contract.RegisterContract;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.JsonUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/5.
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<ValidCodeBean> getValidCode(String phoneNum,String captcha,int type,String way,int validType) {
        return Api.getDefault(HostType.HR).getValidCode(EncryptUtils.encrypParams(ApiParameter.getValidCode(HRApplication.getAppContext(),phoneNum,captcha,type,way,validType)))
                .map(new Func1<ValidCodeBean, ValidCodeBean>() {
                    @Override
                    public ValidCodeBean call(ValidCodeBean baseBean) {
                        return baseBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<ValidCodeBean>io_main());
    }

    @Override
    public Observable<AutoCodeBean> getAutoCode() {
        return  Api.getDefault(HostType.HR).getAutoCode(EncryptUtils.encrypParams(ApiParameter.getAutoCode()))
                .map(new Func1<AutoCodeBean, AutoCodeBean>() {
                    @Override
                    public AutoCodeBean call(AutoCodeBean baseBean) {
                        return baseBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<AutoCodeBean>io_main());
    }

    @Override
    public Observable<RegisterBean> getRegister(String phoneNumber, String validCode, String psw) {
        return Api.getDefault(HostType.HR).getRegister(EncryptUtils.encrypParams(ApiParameter.getRegister(phoneNumber,validCode,psw)))
                .map(new Func1<RegisterBean, RegisterBean>() {
                    @Override
                    public RegisterBean call(RegisterBean registerBean) {
                        return registerBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<RegisterBean>io_main());
    }

    @Override
    public Observable<RegisterBean> getThirdBinding(ThirdLoginBean thirdPartBean, String userName, String psw, int type) {
        return Api.getDefault(HostType.HR).getThirdBinding(EncryptUtils.encrypParams(ApiParameter.getThirdPartBinding(thirdPartBean,userName,psw,type)))
                .map(new Func1<RegisterBean, RegisterBean>() {
                    @Override
                    public RegisterBean call(RegisterBean registerBean) {
                        return registerBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<RegisterBean>io_main());
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
    public Observable<ResumeBean> getResumeData(final String resumeId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getResumeDate(resumeId)))
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
    public Observable<ResponseBody> validPhoneIsExit(String phone) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.validPhoneIsExit(phone)))
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
