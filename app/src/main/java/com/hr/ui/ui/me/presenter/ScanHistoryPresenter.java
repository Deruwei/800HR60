package com.hr.ui.ui.me.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.ui.me.contract.ScanHistoryContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import java.io.IOException;

/**
 * Created by wdr on 2018/3/26.
 */

public class ScanHistoryPresenter extends ScanHistoryContract.Presenter {
    @Override
    public void getPositionData(String jobId) {
        mRxManage.add(mModel.getPositionData(jobId).subscribe(new RxSubscriber<PositionBean>(mContext,false) {
            @Override
            protected void _onNext(PositionBean positionBean) throws IOException {
                //Log.i("你好","2");
                if("0".equals(positionBean.getError_code())) {
                    mView.getPositionSuccess(positionBean.getJob_info());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(positionBean.getError_code())));
                }
            }

            @Override
            protected void _onError(String message) {
                // Log.i("你好","我到这里了");
                /*mView.getPositionFaile();*/
            }
        }));
    }
}
