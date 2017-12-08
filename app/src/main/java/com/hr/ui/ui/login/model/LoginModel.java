package com.hr.ui.ui.login.model;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ThirdPartBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.utils.EncryptUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/6.
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<RegisterBean> getLogin(String phoneNumber, String psw,int type) {
        return  Api.getDefault(HostType.HR).getLogin(EncryptUtils.encrypParams(ApiParameter.getLogin(phoneNumber,psw,type)))
                .map(new Func1<RegisterBean, RegisterBean>() {
                    @Override
                    public RegisterBean call(RegisterBean baseBean) {
                        return baseBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<RegisterBean>io_main());
    }

    @Override
    public Observable<ResponseBody> getThirdPartLogin(ThirdPartBean thirdPartBean) {
        return Api.getDefault(HostType.HR).getThirdPartLogin(EncryptUtils.encrypParams(ApiParameter.getThirdPartLogin(thirdPartBean)))
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
    public Observable<RegisterBean> getThirdBinding(ThirdPartBean thirdPartBean, String userName, String psw, int type) {
        return null;
    }

}
