package com.hr.ui.ui.me.contract;

import android.content.Context;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.PositionBean;

import rx.Observable;

/**
 * Created by wdr on 2018/3/26.
 */

public interface ScanHistoryContract {
    interface Model extends BaseModel{
        Observable<PositionBean> getPositionData(String jobId);
    }
    interface View extends BaseView{
        void getPositionSuccess(PositionBean.JobInfoBean jobInfoBean);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void  getPositionData(String jobId);
    }
}
