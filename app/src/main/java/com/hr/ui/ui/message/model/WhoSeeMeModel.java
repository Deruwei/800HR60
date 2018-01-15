package com.hr.ui.ui.message.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.ui.message.contract.WhoSeeMeContract;
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

public class WhoSeeMeModel implements WhoSeeMeContract.Model {
    @Override
    public Observable<WhoSeeMeBean> getWhoSeeMeData(int page) {
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

}
