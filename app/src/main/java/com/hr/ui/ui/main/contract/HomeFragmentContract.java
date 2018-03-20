package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.HomeRecommendBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.main.fragment.HomeFragment;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/5.
 */

public interface HomeFragmentContract {
    interface Model extends BaseModel{
        Observable<HomeRecommendBean> getRecommendJobInfo(int limit);
        Observable<ResponseBody> getResumeScore(String id);
        Observable<RecommendJobBean> getRecommendJob(int page,int pageNum);
        Observable<ResponseBody> deliverPosition(String jobId);
    }
    interface View extends BaseView {
        void getRecommendJobSuccess(List<HomeRecommendBean.JobsListBean> jobsBeanList);
        void getResumeScoreSuccess(double s);
        void getRecommendJobError();
        void getRecommendJobSuccess2(List<RecommendJobBean.JobsListBean> jobsListBeanList);
        void cantGetData();
        void deliverPositionSuccess();
        void goToCompleteResume(int errorCode);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getRecommendJobInfo(int limit,boolean isCanRefresh);
        public abstract void getResumeScore(String id);
        public abstract void getRecommendJob(int page,int pageNum,boolean isCanRefresh);
        public abstract void  deliverPosition(String jobId);
    }
}
