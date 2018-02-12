package com.hr.ui.ui.me.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.ValidCodeBean;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by wdr on 2018/2/6.
 */

public interface ValidPhoneContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> validPhone(String phoneNumber, String validCode);
        Observable<ValidCodeBean> getValidCode(String phoneNumber, String type, int way, String captcha);
        Observable<AutoCodeBean> getCaptcha();
    }
    interface View extends BaseView{
        void validPhoneSuccess();
        void getValidCodeSuccess(int code);
        void getCaptchaSuccess(String autoCode);
        void getValidCodeFailt();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void validPhone(String phoneNumber,String validCode);
        public abstract void getValidCode(String phoneNumber,String type,int way,String captcha);
        public abstract void getCaptcha();
    }
}
