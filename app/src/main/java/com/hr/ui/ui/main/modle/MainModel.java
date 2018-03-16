package com.hr.ui.ui.main.modle;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ValidCodeBean;
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
