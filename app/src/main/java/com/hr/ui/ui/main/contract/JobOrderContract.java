package com.hr.ui.ui.main.contract;

import android.view.Display;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.JobOrderData;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/13.
 */

public interface JobOrderContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> sendJobOrderToResume(JobOrderData jobOrderData);
        Observable<ResponseBody> setDefaultResume(String resumeId,String important);
        Observable<ResponseBody> setHide(String openstate);
    }
    interface View extends BaseView{
        void sendJobOrderSuccess();
        void setDefaultResumeSuccess();
        void setHideSuccess();
    }
    abstract  class Presenter extends BasePresenter<View,Model>{
        public abstract void sendJobOrderToResume(JobOrderData jobOrderData);
        public abstract void setDefaultResume(String resumeId,String important);
        public abstract void setHide(String openstate);
    }
}
