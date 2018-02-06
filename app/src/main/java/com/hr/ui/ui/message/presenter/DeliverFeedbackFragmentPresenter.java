package com.hr.ui.ui.message.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.DeliverFeedbackBean;
import com.hr.ui.ui.message.contract.DeliverFeedbackContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
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

    @Override
    public void setDeliverFeedBackIsRead(String id) {
        mRxManage.add(mModel.setDeliverFeedBackIsRead(id).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s=responseBody.string().toString();
                try {
                    JSONObject json=new JSONObject(s);
                    String error=json.getString("error_code");
                    if("0".equals(error)) {
                      mView.setDeliverFeedBackIsReadSuccess();
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(error)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
