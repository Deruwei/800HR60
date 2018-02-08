package com.hr.ui.ui.me.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.VersionBean;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/2/8.
 */

public interface VersionContract {
    interface  Model extends BaseModel{
        Observable<VersionBean> getVersion(String cur_clienVersion);
    }
    interface View extends BaseView{
        void getVersionSuccess(VersionBean.AndroidBean androidBean);
    }
    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void getVersion(String cur_clienVersion);
    }
}
