package com.hr.ui.ui.job.contract;

import android.content.Context;
import android.view.Display;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.RecommendJobBean;


import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/11.
 */

public interface PositionPageContract {
    interface  View extends BaseView{
       void getPositionSuccess(PositionBean.JobInfoBean jobInfoBean);
       void collectionPositionSuccess();
       void deliverPositionSuccess();
        void getResumeScoreSuccess(double s);
        void getPositionFaile();
        void goToCompleteResume(int errorCode);
        void retryCulScore();
        void getSearchDataSuccess(List<RecommendJobBean.JobsListBean> jobsListBean);
    }
    interface Model extends BaseModel{
        Observable<PositionBean> getPositionData(String jobId);
        Observable<ResponseBody> collectionPosition(String jobId);
        Observable<ResponseBody> deliverPosition(String jobId);
        Observable<ResponseBody> getResumeScore(String id);
        Observable<RecommendJobBean> getSearchList(JobSearchBean jobSearchBean, int page);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void  getPositionData(String jobId, Context context);
        public abstract void  collectionPosition(String jobId);
        public abstract void  deliverPosition(String jobId);
        public abstract void getResumeScore(String id);
        public abstract void getSearchList(JobSearchBean jobSearchBean, int page, boolean isCanRefresh);
    }
}
