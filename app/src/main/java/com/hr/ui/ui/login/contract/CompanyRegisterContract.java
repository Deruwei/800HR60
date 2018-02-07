package com.hr.ui.ui.login.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.CompanyRegisterBean;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/2/6.
 */

public interface CompanyRegisterContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> registerCompany(CompanyRegisterBean companyRegisterBean);
    }
    interface View extends BaseView{
        void registerCompanySuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void registerCompany(CompanyRegisterBean companyRegisterBean);
    }
}
