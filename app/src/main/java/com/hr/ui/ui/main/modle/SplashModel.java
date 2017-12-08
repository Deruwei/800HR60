package com.hr.ui.ui.main.modle;

import com.hr.ui.app.HRApplication;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.ui.main.contract.SplashContract;
import com.hr.ui.utils.EncryptUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/11/21.
 */

public class SplashModel implements SplashContract.Model {
    @Override
    public Observable<BaseBean> getConnect() {
        return Api.getDefault(HostType.HR).getConnect(EncryptUtils.encrypParams(ApiParameter.getConnect(HRApplication.getAppContext())))
                .map(new Func1<BaseBean, BaseBean>() {
                    @Override
                    public BaseBean call(BaseBean baseBean) {
                        return baseBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<BaseBean>io_main());
    }
}
