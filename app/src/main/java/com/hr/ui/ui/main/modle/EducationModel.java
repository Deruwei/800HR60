package com.hr.ui.ui.main.modle;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.EducationBase;
import com.hr.ui.bean.EducationData;
import com.hr.ui.ui.main.contract.EducationContract;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.view.LeftSlideView;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/13.
 */

public class EducationModel implements EducationContract.Model {
    @Override
    public Observable<EducationBase> sendEducationToResume(EducationData educationData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.sendEducationToResume(educationData)))
                .map(new Func1<ResponseBody, EducationBase>() {
                    @Override
                    public EducationBase call(ResponseBody responseBody) {
                        EducationBase educationBase=null;
                        try {
                            String s=responseBody.string().toString();
                            educationBase=new Gson().fromJson(s, EducationBase.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return educationBase;
                    }
                })

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<EducationBase>io_main());
    }
    @Override
    public Observable<ResponseBody> creatNewResume() {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.createNewResume()))
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
