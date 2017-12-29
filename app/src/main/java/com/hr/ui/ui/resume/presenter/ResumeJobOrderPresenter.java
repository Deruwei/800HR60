package com.hr.ui.ui.resume.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.JobOrderData;
import com.hr.ui.bean.ResumeIdBean;
import com.hr.ui.bean.ResumeOrderInfoBean;
import com.hr.ui.ui.resume.contract.ResumeJobOrderContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/26.
 */

public class ResumeJobOrderPresenter extends ResumeJobOrderContract.Presenter {
    @Override
    public void getJobOrderInfo() {
        mRxManage.add(mModel.getJobOderInfo().subscribe(new RxSubscriber<ResumeOrderInfoBean>(mContext,true) {
            @Override
            protected void _onNext(ResumeOrderInfoBean orderInfoBean) throws IOException {
                if(orderInfoBean.getError_code()==0){
                    mView.getJobOrderSuccess(orderInfoBean.getOrder_info().get(0));
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) orderInfoBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void setJobOrderInfo(JobOrderData jobOrderData) {
        mRxManage.add(mModel.setJobOrderInfo(jobOrderData).subscribe(new RxSubscriber<ResumeIdBean>(mContext,true) {
            @Override
            protected void _onNext(ResumeIdBean resumeIdBean) throws IOException {
                    double error_code=resumeIdBean.getError_code();
                    if(error_code==0) {
                        System.out.print(resumeIdBean.toString());
                        mView.setJobOrderSuccess();
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
                    }

            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
