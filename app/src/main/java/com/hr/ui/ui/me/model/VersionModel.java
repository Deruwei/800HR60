package com.hr.ui.ui.me.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.VersionBean;
import com.hr.ui.ui.me.contract.VersionContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/2/8.
 */

public class VersionModel implements VersionContract.Model {
    @Override
    public Observable<VersionBean> getVersion(String cur_clienVersion) {
        return Api.getDefault(HostType.HR).getVersionCode(cur_clienVersion)
                .map(new Func1<ResponseBody, VersionBean>() {
                    @Override
                    public VersionBean call(ResponseBody responseBody) {
                        VersionBean versionBean=null;
                        try {
                            String s=responseBody.string().toString();
                            versionBean=new Gson().fromJson(s,VersionBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return versionBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<VersionBean>io_main());
    }
}
