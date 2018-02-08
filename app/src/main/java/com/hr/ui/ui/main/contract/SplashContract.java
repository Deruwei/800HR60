package com.hr.ui.ui.main.contract;

import android.content.Context;
import android.widget.SeekBar;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.ArrayInfoBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.VersionBean;

import java.io.ObjectStreamException;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/11/21.
 */

public interface SplashContract {
    interface Model extends BaseModel{
        Observable<BaseBean> getConnect();
        Observable<RegisterBean> getAutoPhoneLogin(String phoneNumber,String psw,int type);
        Observable<ResponseBody> getAutoThirdBindingLogin(LoginBean loginBean);
        Observable<MultipleResumeBean> getResumeList();
        Observable<ResumeBean> getResumeData(String resumeId);
        Observable<ArrayInfoBean> getArrayInfo();
        Observable<ResponseBody> getArrayData(String path,String fileName);
        Observable<VersionBean> getVersion(String curVesion);
    }
    interface View extends BaseView{
        void SendConnectSuccess();
        void phoneLoginSuccess(int userId);
        void thirdBindingLoginSuccess(int userId);
        void getResumeListSuccess(MultipleResumeBean multipleResumeBean);
        void getResumeDataSuccess(ResumeBean resumeBean);
        void onConnectError();
        void getVersion(VersionBean.AndroidBean androidBean);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract  void getConnect(Context context);
        public abstract void getAutoPhoneLogin(String phoneNumber,String psw,int type);
        public abstract void getThirdBindingLogin(LoginBean loginBean);
        public abstract void getResumeList();
        public abstract void getResumeData(String resumeId);
        public abstract  void getArrayInfo();
        public abstract void getArrayData(String path,String fileName);
        public abstract void getVersion(String version);
    }
}
