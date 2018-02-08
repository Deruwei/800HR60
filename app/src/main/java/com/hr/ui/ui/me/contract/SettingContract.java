package com.hr.ui.ui.me.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.NoticeBean;
import com.hr.ui.bean.NoticeData;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/16.
 */

public interface SettingContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> getLoginOut();
        Observable<NoticeBean> getNotice(String ims);
        Observable<ResponseBody> setNotice(NoticeData noticeData);
    }
    interface View extends BaseView{
        void getLoginOutSuccess();
        void getImsSuccess(NoticeBean.NoticeInfoBean noticeInfoBean);
        void setNoticeSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getLoginOut();
        public abstract void getNotice(String ims);
        public abstract void setNotice(NoticeData noticeData);
    }
}
