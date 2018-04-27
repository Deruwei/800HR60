package com.hr.ui.ui.main.modle;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.main.contract.JobSearchFragmentContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/9.
 */

public class JobSearchFragmentModel implements JobSearchFragmentContract.Model {
    @Override
    public Observable<RecommendJobBean> getSearchList(JobSearchBean jobSearchBean, int page) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getJobSearchList(jobSearchBean,page)))
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
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<RecommendJobBean>io_main());
    }

    @Override
    public Observable<RecommendJobBean> getTopSearchJob(JobSearchBean jobSearchBean) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getTopSearchJob(jobSearchBean)))
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
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<RecommendJobBean>io_main());
    }
    @Override
    public Observable<ResponseBody> deliverPosition(String jobId) {
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
    @Override
    public Observable<FindBean> getNotice(String cid, String aid) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getNotice(cid,aid)))
                .map(new Func1<ResponseBody, FindBean>() {
                    @Override
                    public FindBean call(ResponseBody responseBody) {
                        FindBean findBean=null;
                        try {
                            String s=responseBody.string().toString();
                            findBean=new Gson().fromJson(s,FindBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return findBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<FindBean>io_main());
    }
}
