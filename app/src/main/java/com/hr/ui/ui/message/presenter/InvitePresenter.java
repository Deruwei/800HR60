package com.hr.ui.ui.message.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.ui.message.contract.InviteContract;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import java.io.IOException;

/**
 * Created by wdr on 2018/1/16.
 */

public class InvitePresenter extends InviteContract.Presenter {
    @Override
    public void getJobInfo(String jobId) {
        mRxManage.add(mModel.getJobInfo(jobId).subscribe(new RxSubscriber<PositionBean>(mContext,false) {
            @Override
            protected void _onNext(PositionBean positionBean) throws IOException {
                if("0".equals(positionBean.getError_code())){
                    mView.getJobInfoSuccess(positionBean.getJob_info());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(positionBean.getError_code())));
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }
}
