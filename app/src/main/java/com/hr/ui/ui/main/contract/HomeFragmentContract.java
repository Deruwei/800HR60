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
        Observable<HomeRecommendBean> getRecommendJobInfo(String resumeId,int limit);
        Observable<ResponseBody> getResumeScore(String id);
        Observable<RecommendJobBean> getRecommendJob(String num);
    }
    interface View extends BaseView {
        void getRecommendJobSuccess(List<HomeRecommendBean.JobsListBean> jobsBeanList);
        void getResumeScoreSuccess(double s);
        void getRecommendJobError();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getRecommendJobInfo(String resumeId,int limit,boolean isCanRefresh);
        public abstract void getResumeScore(String id);
    }
}
