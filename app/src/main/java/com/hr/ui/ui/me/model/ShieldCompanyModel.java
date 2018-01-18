package com.hr.ui.ui.me.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.QueryShieldCompanyBean;
import com.hr.ui.bean.ShieldCompanyBean;
import com.hr.ui.ui.me.contract.ShieldCompanyContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/17.
 */

public class ShieldCompanyModel implements ShieldCompanyContract.Model {
    @Override
    public Observable<ShieldCompanyBean> getShieldCompanyData() {
        return  Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getShieldCompanyData()))
                .map(new Func1<ResponseBody, ShieldCompanyBean>() {
                    @Override
                    public ShieldCompanyBean call(ResponseBody responseBody) {
                        ShieldCompanyBean shieldCompanyBean=null;
                        try {
                            String s=responseBody.string().toString();
                            shieldCompanyBean=new Gson().fromJson(s,ShieldCompanyBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return shieldCompanyBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ShieldCompanyBean>io_main());
    }

    @Override
    public Observable<QueryShieldCompanyBean> queryShieldCompanyDataByKeyword(String content) {
        return  Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.queryShieldCompanyDataByKeyWord(content)))
                .map(new Func1<ResponseBody, QueryShieldCompanyBean>() {
                    @Override
                    public QueryShieldCompanyBean call(ResponseBody responseBody) {
                        QueryShieldCompanyBean queryShieldCompanyBean=null;
                        try {
                            String s=responseBody.string().toString();
                            queryShieldCompanyBean=new Gson().fromJson(s,QueryShieldCompanyBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return queryShieldCompanyBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<QueryShieldCompanyBean>io_main());
    }

    @Override
    public Observable<ResponseBody> setShiledCompany(String companyName) {
        return  Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.setShieldCompany(companyName)))
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
    public Observable<ResponseBody> deleteShieldCompany(String shieldId) {
        return  Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deleteShieldCompany(shieldId)))
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
