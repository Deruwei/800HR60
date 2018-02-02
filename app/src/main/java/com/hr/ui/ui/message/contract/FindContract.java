package com.hr.ui.ui.message.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.FindBean;


import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/15.
 */

public interface FindContract {
    interface Model extends BaseModel{
        Observable<FindBean> getFindData(int page,String ad_type);
    }
    interface View extends BaseView{
        void getFiindDataSuccess(List<FindBean.ListBean> listBeans);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public  abstract void getFindData(int page,String ad_type,boolean isCanRefresh);
    }
}
