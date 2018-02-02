package com.hr.ui.ui.message.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.PositionBean;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/16.
 */

public interface InviteContract {
    interface Model extends BaseModel{
        Observable<PositionBean> getJobInfo(String jobId);
        Observable<ResponseBody> setInviteIsRead(String recordId);
        Observable<ResponseBody> getInviteInfo(String recordId);
    }
    interface View extends BaseView{
        void getJobInfoSuccess(PositionBean.JobInfoBean jobInfoBean);
        void setInviteIsReadSuccess();
        void getInviteInfoSuccess();
        void noPosition();
    }
    abstract  class  Presenter extends BasePresenter<View,Model>{
        public abstract void getJobInfo(String jobId,boolean isCanRefresh);
        public abstract void setInviteIsRead(String recordId);
        public abstract void getInviteInfo(String recordId);
    }
}
