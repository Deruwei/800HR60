package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.JobOrderData;
import com.hr.ui.bean.ResumeIdBean;
import com.hr.ui.bean.ResumeOrderInfoBean;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by wdr on 2017/12/26.
 */

public interface ResumeJobOrderContract {
    interface Model extends BaseModel{
        Observable<ResumeOrderInfoBean> getJobOderInfo();
       Observable<ResumeIdBean> setJobOrderInfo(JobOrderData jobOrderData);
    }
    interface View extends BaseView{
        void getJobOrderSuccess(ResumeOrderInfoBean.OrderInfoBean orderInfoBean);
        void setJobOrderSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract  void  getJobOrderInfo();
        public abstract void setJobOrderInfo(JobOrderData jobOrderData);
    }
}
