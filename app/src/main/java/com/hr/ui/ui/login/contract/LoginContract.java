package com.hr.ui.ui.login.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by wdr on 2017/12/6.
 */

public interface LoginContract  {
    interface Model extends BaseModel{
        Observable<RegisterBean> getLogin(String phoneNumber,String psw,int type);
        Observable<ResponseBody> getThirdPartLogin(ThirdLoginBean thirdPartBean);
        Observable<RegisterBean> getThirdBinding(ThirdLoginBean thirdPartBean, String userName, String psw, int type);
        Observable<MultipleResumeBean> getResumeList();
        Observable<ResumeBean> getResumeData(String resumeId);
    }
    interface View extends BaseView{
        void sendLoginSuccess(int userId);
        void thirdPartLoginSuccess(int userId);
        void thirdPartLoginGoToBind();
        void bindingSuccess(int userId);
        void getResumeListSuccess(MultipleResumeBean multipleResumeBean);
        void getResumeDataSuccess(ResumeBean resumeBean);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getLogin(String phoneNumber,String psw,int type);
        public abstract void getThirdPartLogin(ThirdLoginBean thirdPartBean);
        public abstract void getThidBinding(ThirdLoginBean thirdPartBean,String userName,String psw,int type);
        public abstract void getResumeList();
        public abstract void getResumeData(String resumeId);
    }
}
