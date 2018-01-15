package com.hr.ui.ui.job.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.CompanyBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.RecommendJobBean;

import java.net.ResponseCache;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/12.
 */

public interface CompanyPageContract {
    interface Model extends BaseModel{
        Observable<CompanyBean> getCompanyData(String companyId);
        Observable<RecommendJobBean> getReleaseJob(String companyId);
    }
    interface View extends BaseView{
        void getCompanyDataSuccess(CompanyBean.EnterpriseInfoBean enterpriseInfoBean);
        void getReleaseJobSuccess(List<RecommendJobBean.JobsListBean> jobInfoList);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getCompanyData(String companyId);
        public abstract void getReleaseJob(String companyId);
    }
}
