package com.hr.ui.ui.job.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.RecommendJobBean;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

public interface RecommendJobActivityContract {
    interface Model extends BaseModel {
        Observable<RecommendJobBean> getSearchList(JobSearchBean jobSearchBean, int page);
        Observable<ResponseBody> deliverPosition(String jobId);

    }
    interface View extends BaseView {
        void getSearchDataSuccess(List<RecommendJobBean.JobsListBean> jobsListBean);
        void deliverPositionSuccess();
        void goToCompleteResume(int errorCode);
    }
    abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void getSearchList(JobSearchBean jobSearchBean,int page,boolean isCanRefresh);
        public abstract void  deliverPosition(String jobId);
    }
}
