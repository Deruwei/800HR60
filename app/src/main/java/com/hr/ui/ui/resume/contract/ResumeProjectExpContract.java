package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.ProjectExpData;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/29.
 */

public interface ResumeProjectExpContract {
    interface  Model extends BaseModel{
        Observable<ResponseBody> getProjectInfo(String projectId);
        Observable<ResponseBody> deleteProjectInfo(String projectId);
        Observable<ResponseBody> addOrUpdateProjectInfo(ProjectExpData projectExpData);
    }
    interface View extends BaseView{
        void getProjectInfoSuccess();
        void deleteProjectSuccess();
        void addOrUpdateProjectInfo();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getProjectInfo(String projectId);
        public abstract void deleteProjectInfo(String projectId);
        public abstract void addOrUpdateProjectInfo(ProjectExpData projectExpData);
    }
}
