package com.hr.ui.ui.me.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/18.
 */

public interface ChangePswContract  {
    interface Model extends BaseModel{
        Observable<ResponseBody>  getChangePsw(String oldPsw,String newPsw);
    }
    interface View extends BaseView{
        void getChangePswSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getChangePsw(String oldPsw,String newPsw);
    }
}
