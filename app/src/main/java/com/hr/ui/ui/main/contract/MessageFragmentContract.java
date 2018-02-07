package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.bean.WhoSeeMeBean;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/15.
 */

public interface MessageFragmentContract {
    interface  Model extends BaseModel{
        Observable<WhoSeeMeBean> getWhoSeeMe(int page);
        Observable<DeliverFeedbackBean> getDeliverFeedback(int page, int isRead, int isInvite);
        Observable<InviteBean> getInviteInterview(int page);
    }
    interface View extends BaseView{
        void getWhoSeeMeSuccess(List<WhoSeeMeBean.BrowsedListBean> browsedListBeans);
        void getDeliverFeedBackSuccess(List<DeliverFeedbackBean.AppliedListBean> appliedListBeanList );
        void getInviteInterViewSuceess(List<InviteBean.InvitedListBean> invitedListBeans);
        void netError();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getWHoSeeMe(int page);
        public abstract void getDeliverFeedback(int page,int isRead,int isInvite,boolean isCanRefresh);
        public abstract void getInviteInterview(int page);
    }
}
