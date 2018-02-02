package com.hr.ui.ui.me.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.QueryShieldCompanyBean;
import com.hr.ui.bean.ShieldCompanyBean;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/17.
 */

public interface ShieldCompanyContract {
    interface Model extends BaseModel{
        Observable<ShieldCompanyBean> getShieldCompanyData();
        Observable<QueryShieldCompanyBean> queryShieldCompanyDataByKeyword(String content);
        Observable<ResponseBody> setShiledCompany(String companyName);
        Observable<ResponseBody> deleteShieldCompany(String shieldId);
    }
    interface View extends BaseView{
        void getShieldCompanyDataSuccess(List<ShieldCompanyBean.EliminateListBean> eliminateListBeans);
        void queryShieldCompanyDataByKeyWordSuccess(List<QueryShieldCompanyBean.EnteListBean> enteListBeans,String total);
        void setShieldCompanySuccess();
        void deleteShieldCompany();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getShieldCompanyData();
        public abstract void queryShieldCompanyDataByKeyword(String content);
        public abstract void setShiledCompany(String companyName);
        public abstract void deleteShieldCompany(String shieldId);
    }
}
