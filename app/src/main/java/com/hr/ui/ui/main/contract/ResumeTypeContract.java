package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.ResumeIdBean;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/18.
 */

public interface ResumeTypeContract {
    interface Model extends BaseModel{
        Observable<ResumeIdBean> setResumeType(String type);
        Observable<ResponseBody> setDefaultResume(String resumeId,String important);
    }
    interface View extends BaseView{
        void sendResumeId(int resumeId);
        void setDefaultResumeSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void setResumeType(String type);
        public abstract void setDefaultResume(String resumeId,String important);
    }
}
