package com.hr.ui.ui.message.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.WhoSeeMeBean;
import com.hr.ui.ui.message.contract.WhoSeeMeContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import java.io.IOException;

/**
 * Created by wdr on 2018/1/15.
 */

public class WhoSeeMePresenter extends WhoSeeMeContract.Presenter {
    @Override
    public void getWhoSeeMeData(int page) {
        mRxManage.add(mModel.getWhoSeeMeData(page).subscribe(new RxSubscriber<WhoSeeMeBean>(mContext,false) {
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
}
