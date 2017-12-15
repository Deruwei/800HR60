package com.hr.ui.ui.login.model;

import android.content.Context;
import android.util.Log;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.db.ThirdPartDao;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.JsonUtils;
import com.hr.ui.utils.LogUtils;
import com.hr.ui.utils.ToastUitl;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/12/6.
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<RegisterBean> getLogin(String phoneNumber, String psw,int type) {
        return  Api.getDefault(HostType.HR).getLogin(EncryptUtils.encrypParams(ApiParameter.getLogin(phoneNumber,psw,type)))
                .map(new Func1<RegisterBean, RegisterBean>() {
                    @Override
                    public RegisterBean call(RegisterBean baseBean) {
                        return baseBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<RegisterBean>io_main());
    }

    @Override
    public Observable<ResponseBody> getThirdPartLogin(ThirdLoginBean thirdPartBean) {
        /*ThirdLoginBeanDao thirdPartBeanDao = HRApplication.getDaoSession().getThirdLoginBeanDao();
        long k=thirdPartBeanDao.insert(thirdPartBean);*/
      /*  System.out.println("getThirdPartLogin:");
        final ThirdLoginBeanDao thirdLoginBeanDao= HRApplication.getAppContext().getDaoSession().getThirdLoginBeanDao();
        System.out.println("this3");
        thirdLoginBeanDao.insert(thirdPartBean);
        System.out.println("this2");*/
        return Api.getDefault(HostType.HR).getThirdPartLogin(EncryptUtils.encrypParams(ApiParameter.getThirdPartLogin(thirdPartBean)))
                .map(new Func1<ResponseBody, ResponseBody>() {
                    @Override
                    public ResponseBody call(ResponseBody responseBody) {
                        return responseBody;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<ResponseBody>io_main());
    }

    @Override
    public Observable<RegisterBean> getThirdBinding(ThirdLoginBean thirdPartBean, String userName, String psw, int type) {
        return Api.getDefault(HostType.HR).getThirdBinding(EncryptUtils.encrypParams(ApiParameter.getThirdPartBinding(thirdPartBean,userName,psw,type)))
                .map(new Func1<RegisterBean, RegisterBean>() {
                    @Override
                    public RegisterBean call(RegisterBean registerBean) {
                        return registerBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<RegisterBean>io_main());
    }

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
                            resumeBean= JsonUtils.getInstance().fixJson(s);
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
