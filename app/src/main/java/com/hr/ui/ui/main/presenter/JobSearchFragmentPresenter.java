package com.hr.ui.ui.main.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.main.contract.JobSearchFragmentContract;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/9.
 */

public class JobSearchFragmentPresenter extends JobSearchFragmentContract.Presenter {
    @Override
    public void getSearchList(JobSearchBean jobSearchBean, int page) {
        mRxManage.add(mModel.getSearchList(jobSearchBean,page).subscribe(new RxSubscriber<RecommendJobBean>(mContext,false) {
            @Override
            protected void _onNext(RecommendJobBean recommendJobBean) throws IOException {
                if(recommendJobBean.getError_code().equals("0")) {
                    mView.getSearchDataSuccess(recommendJobBean.getJobs_list());
                }else{

                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
