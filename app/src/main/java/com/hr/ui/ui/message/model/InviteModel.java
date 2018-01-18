package com.hr.ui.ui.message.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.ui.message.contract.InviteContract;
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

public class InviteModel implements InviteContract.Model {
    @Override
    public Observable<PositionBean> getJobInfo(String jobId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getPositionData(jobId)))
                .map(new Func1<ResponseBody, PositionBean>() {
                    @Override
                    public PositionBean call(ResponseBody responseBody) {
                        PositionBean positionBean=null;
                        try {
                            String s=responseBody.string().toString();
                            positionBean=new Gson().fromJson(s,PositionBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return positionBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<PositionBean>io_main());
    }
}
