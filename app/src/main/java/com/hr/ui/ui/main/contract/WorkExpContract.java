package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.WorkExpBase;
import com.hr.ui.bean.WorkExpBean;
import com.hr.ui.bean.WorkExpData;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/13.
 */

public interface WorkExpContract {
    interface Model extends BaseModel{
        Observable<WorkExpBase> sendWorkExpToResume(WorkExpData  workExpData);
    }
    interface View extends BaseView{
        void sendWorkExpSuccess(String expId);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void sendWorkExpToResume(WorkExpData workExpData);
    }
}
