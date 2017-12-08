package com.hr.ui.ui.login.contract;

import com.afa.tourism.greendao.gen.ThirdPartBeanDao;
import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ThirdPartBean;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by wdr on 2017/12/6.
 */

public interface LoginContract  {
    interface Model extends BaseModel{
        Observable<RegisterBean> getLogin(String phoneNumber,String psw,int type);
        Observable<ResponseBody> getThirdPartLogin(ThirdPartBean thirdPartBean);
        Observable<RegisterBean> getThirdBinding(ThirdPartBean thirdPartBean,String userName,String psw,int type);
    }
    interface View extends BaseView{
        void sendLoginSuccess(int userId);
        void thirdPartLoginSuccess();
        void thirdPartLoginGoToBind();
        void bindingSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getLogin(String phoneNumber,String psw,int type);
        public abstract void getThirdPartLogin(ThirdPartBean thirdPartBean);
        public abstract void getThidBinding(ThirdPartBean thirdPartBean,String userName,String psw,int type);
    }
}
