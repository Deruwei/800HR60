package com.hr.ui.ui.main.modle;

import com.google.gson.Gson;
import com.hr.ui.app.HRApplication;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiParameter;
import com.hr.ui.api.HostType;
import com.hr.ui.base.RxSchedulers;
import com.hr.ui.bean.ArrayInfoBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.db.ThirdPartDao;
import com.hr.ui.ui.login.contract.RegisterContract;
import com.hr.ui.ui.main.contract.SplashContract;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.JsonUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2017/11/21.
 */

public class SplashModel implements SplashContract.Model {
    @Override
    public Observable<BaseBean> getConnect() {
        return Api.getDefault(HostType.HR).getConnect(EncryptUtils.encrypParams(ApiParameter.getConnect(HRApplication.getAppContext())))
                .map(new Func1<BaseBean, BaseBean>() {
                    @Override
                    public BaseBean call(BaseBean baseBean) {
                        return baseBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())        //在新线程里面处理网络请求
                .observeOn(AndroidSchedulers.mainThread())  //在主线程里面接受返回的数据
                .compose(RxSchedulers.<BaseBean>io_main());
    }

    @Override
    public Observable<RegisterBean> getAutoPhoneLogin(String phoneNumber, String psw,int type) {
        return Api.getDefault(HostType.HR).getLogin(EncryptUtils.encrypParams(ApiParameter.getLogin(phoneNumber,psw,type)))
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
    public Observable<ResponseBody> getAutoThirdBindingLogin(LoginBean loginBean) {
        ThirdLoginBean thirdLoginBean=new ThirdLoginBean();
        thirdLoginBean.setSUId(loginBean.getThirdPartSUid());
        thirdLoginBean.setUId(loginBean.getThirdPartUid());
        thirdLoginBean.setType(loginBean.getThirdPartLoginType());
        return  Api.getDefault(HostType.HR).getThirdPartLogin(EncryptUtils.encrypParams(ApiParameter.getThirdPartLogin(thirdLoginBean)))
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

    @Override
    public Observable<ArrayInfoBean> getArrayInfo() {
        return Api.getDefault(HostType.HR).getArrayInfo()
                .map(new Func1<ResponseBody, ArrayInfoBean>() {
                    @Override
                    public ArrayInfoBean call(ResponseBody responseBody) {
                        String s="";
                        try {
                            s=responseBody.string().toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ArrayInfoBean arrayInfoBean=new Gson().fromJson(s,ArrayInfoBean.class);
                        return arrayInfoBean;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxSchedulers.<ArrayInfoBean>io_main());
    }

    @Override
    public Observable<ResponseBody> getArrayData(String path, String fileName) {
        return Api.getDefault(HostType.HR).getLingYuArray(path)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(RxSchedulers.<ResponseBody>io_main());
    }
}
