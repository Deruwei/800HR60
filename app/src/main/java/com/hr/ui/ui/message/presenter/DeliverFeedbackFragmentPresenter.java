package com.hr.ui.ui.message.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.ui.message.contract.DeliverFeedbackContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import java.io.IOException;

import rx.Observable;

/**
 * Created by wdr on 2018/1/15.
 */

public class DeliverFeedbackFragmentPresenter extends DeliverFeedbackContract.Presenter {

    @Override
    public void getDeliverFeedBack(int page, int isRead, int isInvite,boolean isCanRefresh) {
        mRxManage.add(mModel.getDeliverFeedBack(page,isRead,isInvite).subscribe(new RxSubscriber<DeliverFeedbackBean>(mContext,isCanRefresh) {
            @Override
            protected void _onNext(DeliverFeedbackBean deliverFeedbackBean) throws IOException {
                if("0".equals(deliverFeedbackBean.getError_code())) {
                    mView.getDeliverFeedbackSuccess(deliverFeedbackBean.getApplied_list());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(deliverFeedbackBean.getError_code())));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
