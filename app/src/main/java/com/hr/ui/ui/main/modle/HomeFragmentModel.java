package com.hr.ui.ui.main.modle;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.HomeRecommendBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.bean.ResumeIdBean;
import com.hr.ui.ui.main.contract.HomeFragmentContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/5.
 */

public class HomeFragmentModel implements HomeFragmentContract.Model {
    @Override
    public Observable<HomeRecommendBean> getRecommendJobInfo(int limit) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getRecommendJobScore(limit)))
                .map(new Func1<ResponseBody, HomeRecommendBean>() {
                    @Override
                    public HomeRecommendBean call(ResponseBody responseBody) {
                        HomeRecommendBean recommendJobBean=null;
                        try {
                            String s=responseBody.string().toString();
                            recommendJobBean=new Gson().fromJson(s,HomeRecommendBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return recommendJobBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<HomeRecommendBean>io_main());
    }

    @Override
    public Observable<ResponseBody> getResumeScore(String id) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getResumeScore(id)))
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
    public Observable<RecommendJobBean> getRecommendJob(int page,int pageNum) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getRecommendJobInfo(page,pageNum)))
                .map(new Func1<ResponseBody, RecommendJobBean>() {
                    @Override
                    public RecommendJobBean call(ResponseBody responseBody) {
                        RecommendJobBean recommendJobBean=null;
                        try {
                            String s=responseBody.string().toString();
                            recommendJobBean=new Gson().fromJson(s,RecommendJobBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return recommendJobBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<RecommendJobBean>io_main());
    }

}
