package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.RecommendJobBean;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/5.
 */

public interface HomeFragmentContract {
    interface Model extends BaseModel{
        Observable<RecommendJobBean> getRecommendJobInfo(int page,int limit);
    }
    interface View extends BaseView {
        void getRecommendJobSuccess(List<RecommendJobBean.JobsListBean> jobsBeanList);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getRecommendJobInfo(int page,int limit);
    }
}
