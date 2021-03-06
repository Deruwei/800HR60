package com.hr.ui.ui.me.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.ValidCodeBean;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/18.
 */

public interface ChangePhoneContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> changePhone(String phoneNumber,String validCode);
        Observable<ValidCodeBean> getValidCode(String phoneNumber, String type, int way,String captcha,int validType);
        Observable<AutoCodeBean> getCaptcha();
        Observable<ResponseBody> validPhoneIsExit(String phone);
    }
    interface View extends BaseView{
        void changePhoneSuccess();
        void getValidCodeSuccess(int code);
        void getCaptchaSuccess(String autoCode);
        void phoneIsExit(String flag);
        void getValidCodeFailt();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void changePhone(String phoneNumber,String validCode);
        public abstract void getValidCode(String phoneNumber,String type,int way,String captcha,int validType);
        public abstract void getCaptcha();
        public abstract void validPhoneIsExit(String phone);
    }
}
