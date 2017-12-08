package com.hr.ui.ui.login.model;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.NoDataBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.ui.login.contract.FindPasswordContract;
import com.hr.ui.ui.login.contract.FindUserPasswordContract;
import com.hr.ui.utils.EncryptUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/7.
 */

public class FindUserPasswordModel implements FindUserPasswordContract.Model {
    @Override
    public Observable<NoDataBean> resetUserPsw(String email, String userName) {
        return Api.getDefault(HostType.HR).resetPhonePsw(EncryptUtils.encrypParams(ApiParameter.resetUserPsw(email,userName)))
                .map(new Func1<NoDataBean, NoDataBean>() {
                    @Override
                    public NoDataBean call(NoDataBean baseBean) {
                        return baseBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<NoDataBean>io_main());
    }
}
