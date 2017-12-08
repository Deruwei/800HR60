package com.hr.ui.ui.login.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ValidCodeBean;

import rx.Observable;

/**
 * Created by wdr on 2017/12/8.
 */

public interface BineNewAccountContract {
    interface Model extends BaseModel{
        Observable<ValidCodeBean> getValidCode(String phoneNumber, String captcha, int type, String way);
        Observable<AutoCodeBean> getAutoCode();
        Observable<RegisterBean> getRegister(String phoneNumber, String validCode, String psw);
    }
    interface View extends BaseView{
        void sendValidCode(int code);
        void sendAutoCode(String autoCode);
        void sendRegisterSuccess(int userId);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract  void getValidCode(String phoneNumber,String captcha,int type,String way);
        public abstract void getAutoCode();
        public abstract void getRegister(String phoneNumber,String validCode,String psw);
    }
}
