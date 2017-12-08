package com.hr.ui.ui.login.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.NoDataBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.contract.FindPasswordContract;
import com.hr.ui.ui.login.contract.FindUserPasswordContract;
import com.hr.ui.ui.main.modle.SplashModel;
import com.hr.ui.ui.main.presenter.SplashPresenter;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

/**
 * Created by wdr on 2017/12/7.
 */

public class FindUserPasswordPresenter extends FindUserPasswordContract.Presenter{
    @Override
    public void resetUserPsw(String email, String userName) {
        mRxManage.add(mModel.resetUserPsw(email,userName).subscribe(new RxSubscriber<NoDataBean>(mContext,false) {
            @Override
            protected void _onNext(NoDataBean noDataBean) {
                if(noDataBean.getError_code()==0){
                    mView.resetUserPswSuccess();
                }else{
                    if(noDataBean.getError_code()==204) {
                        Constants.SESSION_KEY="";
                    }else {
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) noDataBean.getError_code()));
                    }
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
