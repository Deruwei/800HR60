package com.hr.ui.ui.main.modle;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.ui.main.contract.MessageFragmentContract;
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

public class MessageFragmentModel implements MessageFragmentContract.Model {
    @Override
    public Observable<WhoSeeMeBean> getWhoSeeMe(int page) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getWhoSeeMe(page)))
                .map(new Func1<ResponseBody, WhoSeeMeBean>() {
                    @Override
                    public WhoSeeMeBean call(ResponseBody responseBody) {
                        WhoSeeMeBean whoSeeMeBean=null;
                        try {
                            String s=responseBody.string().toString();
                            whoSeeMeBean=new Gson().fromJson(s,WhoSeeMeBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return whoSeeMeBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<WhoSeeMeBean>io_main());
    }

    @Override
    public Observable<DeliverFeedbackBean> getDeliverFeedback(int page, int isRead, int isInvite) {
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
    public Observable<InviteBean> getInviteInterview(int page) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getInviteInterview(page)))
                .map(new Func1<ResponseBody, InviteBean>() {
                    @Override
                    public InviteBean call(ResponseBody responseBody) {
                        InviteBean inviteBean=null;
                        try {
                            String s=responseBody.string().toString();
                            inviteBean=new Gson().fromJson(s,InviteBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return inviteBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<InviteBean>io_main());
    }
}
