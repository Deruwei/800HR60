package com.hr.ui.ui.me.model;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.ui.me.contract.ChangePhoneContract;
import com.hr.ui.utils.EncryptUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/18.
 */

public class ChangePhoneModel implements ChangePhoneContract.Model {
    @Override
    public Observable<ResponseBody> changePhone(String phoneNumber, String validCode) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.changePhone(phoneNumber,validCode)))
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<ValidCodeBean> getValidCode(String phoneNumber, String type,int way, String captcha,int validType) {
        return Api.getDefault(HostType.HR).getValidCode(EncryptUtils.encrypParams(ApiParameter.getValidCode(HRApplication.getAppContext(),phoneNumber,captcha,way,type,validType)))
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
    public Observable<AutoCodeBean> getCaptcha() {
        return Api.getDefault(HostType.HR).getAutoCode(EncryptUtils.encrypParams(ApiParameter.getAutoCode()))
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
