package com.hr.ui.ui.login.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.NoDataBean;

import rx.Observable;


/**
 * Created by wdr on 2017/12/7.
 */

public interface FindUserPasswordContract {
    interface Model extends BaseModel{
        Observable<NoDataBean> resetUserPsw(String email,String userName);
    }
    interface View extends BaseView{
        void resetUserPswSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void resetUserPsw(String email,String userName);
    }
}
