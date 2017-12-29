package com.hr.ui.ui.resume.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.JobOrderData;
import com.hr.ui.bean.ResumeIdBean;
import com.hr.ui.bean.ResumeOrderInfoBean;
import com.hr.ui.bean.ResumePersonalInfoBean;
import com.hr.ui.ui.resume.contract.ResumeJobOrderContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/26.
 */

public class ResumeJobOrderModel implements ResumeJobOrderContract.Model {
    @Override
    public Observable<ResumeOrderInfoBean> getJobOderInfo() {
        return  Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getJobOrderInfo()))
                .map(new Func1<ResponseBody, ResumeOrderInfoBean>() {
                    @Override
                    public ResumeOrderInfoBean call(ResponseBody responseBody) {
                        String s="";
                        try {
                            s=responseBody.string().toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return new Gson().fromJson(s, ResumeOrderInfoBean.class);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeOrderInfoBean>io_main());
    }

    @Override
    public Observable<ResumeIdBean> setJobOrderInfo(JobOrderData jobOrderData) {
        return  Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.sendJobOrderToResume(jobOrderData)))
                .map(new Func1<ResponseBody, ResumeIdBean>() {
                    @Override
                    public ResumeIdBean call(ResponseBody responseBody) {
                        ResumeIdBean resumeIdBean=null;
                        try {
                            String s=responseBody.string().toString();
                            resumeIdBean=new Gson().fromJson(s,ResumeIdBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return resumeIdBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeIdBean>io_main());
    }
}
