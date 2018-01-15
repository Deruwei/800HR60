package com.hr.ui.ui.me.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.CollectionBean;
import com.hr.ui.ui.me.contract.CollectionContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/12.
 */

public class CollectionModel implements CollectionContract.Model {
    @Override
    public Observable<CollectionBean> getCollectionInfo(int page) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getCollectionData(page)))
                .map(new Func1<ResponseBody, CollectionBean>() {
                    @Override
                    public CollectionBean call(ResponseBody responseBody) {
                        CollectionBean collectionBean=null;
                        try {
                            String s=responseBody.string().toString();
                            collectionBean=new Gson().fromJson(s,CollectionBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return collectionBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<CollectionBean>io_main());
    }

    @Override
    public Observable<ResponseBody> deleteCollection(String collectionId, String jobId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deleteCollectionData(collectionId,jobId)))
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

    @Override
    public Observable<ResponseBody> deliverCollection(String jobId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deliverJob(jobId)))
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
