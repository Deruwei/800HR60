package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.EducationBean;
import com.hr.ui.bean.EducationData;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/29.
 */

public interface ResumeEducationContract {
    interface Model extends BaseModel {
        Observable<ResponseBody> addOrUpdateEducation(EducationData educationData);
        Observable<EducationBean> getEducationInfo(String educationId);
        Observable<ResponseBody> deleteEducation(String educationId);
    }
    interface View extends BaseView {
        void getEducationInfoSuccess(EducationBean.EducationListBean educationBean);
        void addOrUpdateEducationSuccess();
        void deleteEducationSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void addOrUpdateEducation(EducationData educationData);
        public abstract void getEducationInfo(String education);
        public abstract void deleteEducation(String education);
    }
}
