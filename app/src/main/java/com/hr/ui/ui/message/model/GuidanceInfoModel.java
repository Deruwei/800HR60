package com.hr.ui.ui.message.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.GuidanceInfoBean;
import com.hr.ui.ui.message.contract.GuidanceInfoContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/16.
 */

public class GuidanceInfoModel implements GuidanceInfoContract.Model {
    @Override
    public Observable<GuidanceInfoBean> getGuidanceInfo(final String guidanceId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getGuidanceTitleInfo(guidanceId)))
                .map(new Func1<ResponseBody, GuidanceInfoBean>() {
                    @Override
                    public GuidanceInfoBean call(ResponseBody responseBody) {
                        GuidanceInfoBean guidanceInfoBean=null;
                        try {
                            String s=responseBody.string().toString();
                            guidanceInfoBean=new Gson().fromJson(s,GuidanceInfoBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return guidanceInfoBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<GuidanceInfoBean>io_main());
    }
}
