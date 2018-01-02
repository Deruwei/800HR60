package com.hr.ui.ui.main.fragment;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.PictureBean;
import com.hr.ui.bean.ResumeBean;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/22.
 */

public interface ResumeContract {
    interface View extends BaseView{
        void getResumeSuccess(ResumeBean resumeBean);
        void getResumeList(String resumeId);
        void uploadImageSuccess(String path);
        void setHideSuccess();
    }
    interface Model extends BaseModel{
        Observable<ResumeBean> getResume(String resumeId);
        Observable<MultipleResumeBean> getResumeList();
         Observable<PictureBean> upLoadImage(String content);
         Observable<ResponseBody> setHide(String openstate);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getResume(String resumeId);
        public abstract void getResumeList();
        public abstract void upLoadImage(String content);
        public abstract void setHide(String openstate);
    }
}
