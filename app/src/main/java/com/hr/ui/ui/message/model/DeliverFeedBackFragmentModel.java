package com.hr.ui.ui.message.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.ui.message.contract.DeliverFeedbackContract;
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

public class DeliverFeedBackFragmentModel implements DeliverFeedbackContract.Model {
    @Override
    public Observable<DeliverFeedbackBean> getDeliverFeedBack(int page, int isRead, int isInvite) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getDeliverFeedback(page,isRead,isInvite)))
                .map(new Func1<ResponseBody, DeliverFeedbackBean>() {
                    @Override
                    public DeliverFeedbackBean call(ResponseBody responseBody) {
                        DeliverFeedbackBean deliverFeedbackBean=null;
                        try {
                            String s=responseBody.string().toString();
                            deliverFeedbackBean=new Gson().fromJson(s,DeliverFeedbackBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return deliverFeedbackBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<DeliverFeedbackBean>io_main());
    }

    @Override
    public Observable<ResponseBody> setDeliverFeedBackIsRead(String id) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.setDeliverFeedBack(id)))
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
