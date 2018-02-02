package com.hr.ui.ui.login.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.NoDataBean;
import com.hr.ui.bean.ValidCodeBean;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/6.
 */

public interface FindPasswordContract  {
    interface Model extends BaseModel{

        Observable<NoDataBean> resetPhonePsw(String phoneNumber,String validCode,String newPsw);
        Observable<ValidCodeBean> getValidCode(String phoneNumber,String captcha,int type,String way);
        Observable<AutoCodeBean> getAutoCode();
        Observable<ResponseBody> validPhoneIsExit(String phone);
    }
    interface View extends BaseView{
        void getValidCodeSuccess(int tokenTimes);
        void resetPasswordSuccess();
        void sendAutoCode(String autoCode);
        void setNeedAotuCode();
        void phoneisExit(String flag);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void resetPhonePsw(String phoneNumber,String validCode,String newPsw);
        public abstract void getValidCode(String phoneNumber,String captcha,int type,String way );
        public abstract void getAutoCode();
        public abstract void validPhoneIsExit(String phone);
    }
}
