package com.hr.ui.ui.message.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.GuidanceBean;
import com.hr.ui.ui.message.contract.EmploymentGuidanceContract;
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

public class EmploymentGuidanceModel implements EmploymentGuidanceContract.Model {
    @Override
    public Observable<GuidanceBean> getEmploymentGuidanceData(int page, final String guidanceId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getGuidanceTitle(page,guidanceId)))
                .map(new Func1<ResponseBody, GuidanceBean>() {
                    @Override
                    public GuidanceBean call(ResponseBody responseBody) {
                        GuidanceBean guidanceBean=null;
                        try {
                            String s=responseBody.string().toString();
                            guidanceBean=new Gson().fromJson(s,GuidanceBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return guidanceBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<GuidanceBean>io_main());
    }
}
