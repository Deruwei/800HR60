package com.hr.ui.ui.main.contract;

import android.content.Context;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.BaseBean;

import rx.Observable;

/**
 * Created by wdr on 2017/11/21.
 */

public interface SplashContract {
    interface Model extends BaseModel{
        Observable<BaseBean> getConnect();
    }
    interface View extends BaseView{
        void SendConnectSuccess();
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        public abstract  void getConnect(Context context);
    }
}
