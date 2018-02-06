package com.hr.ui.ui.job.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.CompanyBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.job.contract.CompanyPageContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/12.
 */

public class CompanyPagePresenter extends CompanyPageContract.Presenter {
    @Override
    public void getCompanyData(String companyId) {
        mRxManage.add(mModel.getCompanyData(companyId).subscribe(new RxSubscriber<CompanyBean>(mContext,true) {
            @Override
            protected void _onNext(CompanyBean companyBean) throws IOException {
                    if(companyBean.getError_code()==0) {
                        // System.out.print(s);
                       /* mView.setHideSuccess();*/
                        mView.getCompanyDataSuccess(companyBean.getEnterprise_info());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(companyBean.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {
                mView.getCompanyJobFaild();
            }
        }));
    }

    @Override
    public void getReleaseJob(String companyId) {
        mRxManage.add(mModel.getReleaseJob(companyId).subscribe(new RxSubscriber<RecommendJobBean>(mContext,false) {
            @Override
            protected void _onNext(RecommendJobBean positionBean) throws IOException {
                    if("0".equals(positionBean.getError_code())) {
                        // System.out.print(s);
                       /* mView.setHideSuccess();*/
                        mView.getReleaseJobSuccess(positionBean.getJobs_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(positionBean.getError_code())));
                    }

            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
