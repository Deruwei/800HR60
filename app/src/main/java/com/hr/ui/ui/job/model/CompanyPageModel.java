package com.hr.ui.ui.job.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.CompanyBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.job.contract.CompanyPageContract;
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

public class CompanyPageModel implements CompanyPageContract.Model {
    @Override
    public Observable<CompanyBean> getCompanyData(String companyId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getCompanyData(companyId)))
                .map(new Func1<ResponseBody, CompanyBean>() {
                    @Override
                    public CompanyBean call(ResponseBody responseBody) {
                        CompanyBean companyBean=null;
                        try {
                            String s=responseBody.string().toString();
                            companyBean=new Gson().fromJson(s,CompanyBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return companyBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<CompanyBean>io_main());
    }

    @Override
    public Observable<RecommendJobBean> getReleaseJob(String companyId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getReleaseJob(companyId)))
                .map(new Func1<ResponseBody, RecommendJobBean>() {
                    @Override
                    public RecommendJobBean call(ResponseBody responseBody) {
                        RecommendJobBean positionBean=null;
                        try {
                            String s=responseBody.string().toString();
                            positionBean=new Gson().fromJson(s,RecommendJobBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return positionBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<RecommendJobBean>io_main());
    }
}
