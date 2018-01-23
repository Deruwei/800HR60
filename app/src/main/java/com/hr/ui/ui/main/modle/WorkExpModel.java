package com.hr.ui.ui.main.modle;

import android.support.transition.Visibility;
import android.view.Display;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.WorkExpBase;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.ui.main.contract.WorkExpContract;
import com.hr.ui.utils.EncryptUtils;

import org.w3c.dom.Entity;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/13.
 */

public class WorkExpModel implements WorkExpContract.Model {

    @Override
    public Observable<WorkExpBase> sendWorkExpToResume(WorkExpData workExpData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.sendWorkExpToResume(workExpData)))
                .map(new Func1<ResponseBody, WorkExpBase>() {
                    @Override
                    public WorkExpBase call(ResponseBody responseBody) {
                        WorkExpBase workExpBase=null;
                        try {
                            String s=responseBody.string().toString();
                            workExpBase=new Gson().fromJson(s,WorkExpBase.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return workExpBase;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<WorkExpBase>io_main());
    }
}
