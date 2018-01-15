package com.hr.ui.ui.job.contract;

import android.view.Display;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.PositionBean;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/11.
 */

public interface PositionPageContract {
    interface  View extends BaseView{
       void getPositionSuccess(PositionBean.JobInfoBean jobInfoBean);
       void collectionPositionSuccess();
       void deliverPositionSuccess();
    }
    interface Model extends BaseModel{
        Observable<PositionBean> getPositionData(String jobId);
        Observable<ResponseBody> collectionPosition(String jobId);
        Observable<ResponseBody> deliverPosition(String jobId);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void  getPositionData(String jobId);
        public abstract void  collectionPosition(String jobId);
        public abstract void  deliverPosition(String jobId);
    }
}
