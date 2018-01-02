package com.hr.ui.ui.resume.model;

import com.google.gson.Gson;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.ProfessionSkillData;
import com.hr.ui.bean.ResumeProfessionSkillBean;
import com.hr.ui.ui.resume.contract.ResumeProfessionSkillContract;
import com.hr.ui.utils.EncryptUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeProfessionSkillModel implements ResumeProfessionSkillContract.Model{
    @Override
    public Observable<ResumeProfessionSkillBean> getSkill(String skillId) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.getProfessionSkill(skillId)))
                .map(new Func1<ResponseBody, ResumeProfessionSkillBean>() {
                    @Override
                    public ResumeProfessionSkillBean call(ResponseBody responseBody) {
                        ResumeProfessionSkillBean resumeProfessionSkillBean=null;
                        try {
                            String s=responseBody.string().toString();
                            resumeProfessionSkillBean=new Gson().fromJson(s,ResumeProfessionSkillBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return resumeProfessionSkillBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ResumeProfessionSkillBean>io_main());
    }

    @Override
    public Observable<ResponseBody> deleteSkill(String skillid) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.deleteProfessionSkill(skillid)))
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
    public Observable<ResponseBody> addOrReplaceSkill(ProfessionSkillData skillData) {
        return Api.getDefault(HostType.HR).getResponseString(EncryptUtils.encrypParams(ApiParameter.addOrReplaceProfessionSkill(skillData)))
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
