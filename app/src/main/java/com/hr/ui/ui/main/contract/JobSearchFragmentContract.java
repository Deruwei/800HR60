package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.RecommendJobBean;


import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/9.
 */

public interface JobSearchFragmentContract {
    interface Model extends BaseModel{
        Observable<RecommendJobBean> getSearchList(JobSearchBean jobSearchBean, int page);
        Observable<RecommendJobBean> getTopSearchJob(JobSearchBean jobSearchBean);
    }
    interface View extends BaseView{
        void getSearchDataSuccess(List<RecommendJobBean.JobsListBean> jobsListBean);
        void getTopSearchJobSuccess(List<RecommendJobBean.JobsListBean> jobsListBeans);
        void getTopSearchFaild();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getSearchList(JobSearchBean jobSearchBean,int page,boolean isCanRefresh);
        public abstract void getTopSearchJob(JobSearchBean jobSearchBean);
    }
}
