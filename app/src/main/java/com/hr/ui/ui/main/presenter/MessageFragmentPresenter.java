package com.hr.ui.ui.main.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.bean.InviteBean;
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.ui.main.contract.MessageFragmentContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/15.
 */

public class MessageFragmentPresenter extends MessageFragmentContract.Presenter {
    @Override
    public void getWHoSeeMe(int page) {
        mRxManage.add(mModel.getWhoSeeMe(page).subscribe(new RxSubscriber<WhoSeeMeBean>(mContext,false) {
            @Override
            protected void _onNext(WhoSeeMeBean whoSeeMeBean) throws IOException {
                    if(whoSeeMeBean.getError_code()==0) {
                        mView.getWhoSeeMeSuccess(whoSeeMeBean.getBrowsed_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(whoSeeMeBean.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void getDeliverFeedback(int page, int isRead, int isInvite, final boolean isCanRefresh) {
        mRxManage.add(mModel.getDeliverFeedback(page,isRead,isInvite).subscribe(new RxSubscriber<DeliverFeedbackBean>(mContext,isCanRefresh) {
            @Override
            protected void _onNext(DeliverFeedbackBean deliverFeedbackBean) throws IOException {
                    if("0".equals(deliverFeedbackBean.getError_code())) {
                        mView.getDeliverFeedBackSuccess(deliverFeedbackBean.getApplied_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(deliverFeedbackBean.getError_code())));
                    }
            }

            @Override
            protected void _onError(String message) {
                if(isCanRefresh==true){
                    mView.netError();
                }
            }
        }));
    }

    @Override
    public void getInviteInterview(int page) {
        mRxManage.add(mModel.getInviteInterview(page).subscribe(new RxSubscriber<InviteBean>(mContext,false) {
            @Override
            protected void _onNext(InviteBean inviteBean) throws IOException {

                    if("0".equals(inviteBean.getError_code())) {
                        mView.getInviteInterViewSuceess(inviteBean.getInvited_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(inviteBean.getError_code())));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
