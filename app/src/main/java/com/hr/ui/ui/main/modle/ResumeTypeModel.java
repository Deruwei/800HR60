package com.hr.ui.ui.main.modle;

import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.ResumeIdBean;
import com.hr.ui.ui.main.contract.ResumeTypeContract;
import com.hr.ui.utils.EncryptUtils;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/18.
 */

public class ResumeTypeModel implements ResumeTypeContract.Model {
    @Override
    public Observable<ResumeIdBean> setResumeType(String type) {
        return Api.getDefault(HostType.HR).setResumeType(EncryptUtils.encrypParams(ApiParameter.setResumeType(type)))
                .map(new Func1<ResumeIdBean, ResumeIdBean>() {
                    @Override
                    public ResumeIdBean call(ResumeIdBean resumeIdBean) {
                        return resumeIdBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeIdBean>io_main());
    }

    @Override
    public Observable<ResponseBody> setDefaultResume(String resumeId,String important) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.setDefaultResume(resumeId,important)))
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
