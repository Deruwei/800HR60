package com.hr.ui.ui.me.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/16.
 */

public interface SettingContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> getLoginOut();
    }
    interface View extends BaseView{
        void getLoginOutSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getLoginOut();
    }
}
