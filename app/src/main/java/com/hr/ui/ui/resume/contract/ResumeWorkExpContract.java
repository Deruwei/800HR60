package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.ui.main.activity.WorkExpActivity;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/29.
 */

public interface ResumeWorkExpContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> getWorkExpInfo(String experienceId);
        Observable<ResponseBody> deleteWorkExp(String experienceId);
        Observable<ResponseBody> addOrUpdateWorkExp(WorkExpData workExpData);
    }
    interface View extends BaseView{
        void getWorkExpinfo();
        void deleteSuccess();
        void addOrUpdateWorkExp();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getWorkExpInfo(String experienceId);
        public abstract void deleteWorkExp(String experienceId);
        public abstract void addOrUpdateWorkExp(WorkExpData workExpData);
    }
}
