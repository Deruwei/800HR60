package com.hr.ui.ui.login.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.bean.ValidCodeBean;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by wdr on 2017/12/5.
 */

public interface RegisterContract {
    interface Model extends BaseModel{
        Observable<ValidCodeBean> getValidCode(String phoneNumber,String captcha,int type,String way);
        Observable<AutoCodeBean> getAutoCode();
        Observable<RegisterBean> getRegister(String phoneNumber, String validCode, String psw);
        Observable<RegisterBean> getThirdBinding(ThirdLoginBean thirdPartBean, String userName, String psw, int type);
        Observable<MultipleResumeBean> getResumeList();
        Observable<ResumeBean> getResumeData(String resumeId);
    }
    interface  View extends BaseView{
        void sendValidCode(int code);
        void sendAutoCode(String autoCode);
        void sendRegisterSuccess(int userId);
        void bindingSuccess(int userId);
        void getResumeListSuccess(MultipleResumeBean multipleResumeBean);
        void getResumeDataSuccess(ResumeBean resumeBean);
        void needToGetAutoCode();
    }
    abstract  class Presenter extends BasePresenter<RegisterContract.View, RegisterContract.Model> {
        public abstract  void getValidCode(String phoneNumber,String captcha,int type,String way);
        public abstract void getAutoCode();
        public abstract void getRegister(String phoneNumber,String validCode,String psw);
        public abstract void getThidBinding(ThirdLoginBean thirdPartBean,String userName,String psw,int type);
        public abstract void getResumeList();
        public abstract void getResumeData(String resumeId);
    }
}
