package com.hr.ui.ui.message.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.DeliverFeedbackBean;

import java.net.ResponseCache;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/15.
 */

public interface DeliverFeedbackContract {
    interface Model extends BaseModel{
        Observable<DeliverFeedbackBean> getDeliverFeedBack(int  page,int isRead,int isInvite);
        Observable<ResponseBody> setDeliverFeedBackIsRead(String id);
    }
    interface View extends BaseView{
        void getDeliverFeedbackSuccess(List<DeliverFeedbackBean.AppliedListBean> appliedListBeanList);
        void setDeliverFeedBackIsReadSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getDeliverFeedBack(int  page,int isRead,int isInvite,boolean isCanRefresh);
        public abstract void setDeliverFeedBackIsRead(String id);
    }
}
