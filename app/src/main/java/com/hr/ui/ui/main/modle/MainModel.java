package com.hr.ui.ui.main.modle;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.ui.main.contract.MainContract;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/11.
 */

public class MainModel implements MainContract.Model {
    @Override
    public Observable<MultipleResumeBean> getResumeList() {
        return Api.getDefault(HostType.HR).getResumeList(EncryptUtils.encrypParams(ApiParameter.getResumeList()))
                .map(new Func1<MultipleResumeBean, MultipleResumeBean>() {
                    @Override
                    public MultipleResumeBean call(MultipleResumeBean multipleResumeBean) {
                       return multipleResumeBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<MultipleResumeBean>io_main());
    }

    @Override
    public Observable<ResumeBean> getResumeData(final String resumeId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getResumeDate(resumeId)))
                .map(new Func1<ResponseBody, ResumeBean>() {
                    @Override
                    public ResumeBean call(ResponseBody responseBody) {
                        ResumeBean resumeBean=new ResumeBean();
                        try {
                            String s=responseBody.string().toString();
                            resumeBean=JsonUtils.getInstance().fixJson(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return resumeBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeBean>io_main());
    }
}