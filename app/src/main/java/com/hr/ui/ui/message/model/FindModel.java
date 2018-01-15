package com.hr.ui.ui.message.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.FindBean;
import com.hr.ui.ui.message.contract.FindContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/15.
 */

public class FindModel implements FindContract.Model {
    @Override
    public Observable<FindBean> getFindData(int page) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getFind(page)))
                .map(new Func1<ResponseBody, FindBean>() {
                    @Override
                    public FindBean call(ResponseBody responseBody) {
                        FindBean findBean=null;
                        try {
                            String s=responseBody.string().toString();
                            findBean=new Gson().fromJson(s,FindBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return findBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<FindBean>io_main());
    }
}
