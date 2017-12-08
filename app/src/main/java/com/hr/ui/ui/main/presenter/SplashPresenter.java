package com.hr.ui.ui.main.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.SplashContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

/**
 * Created by wdr on 2017/11/21.
 */

public class SplashPresenter extends SplashContract.Presenter {
    private static final String TAG=SplashPresenter.class.getSimpleName();
    @Override
    public void getConnect(final Context context) {
        mRxManage.add(mModel.getConnect().subscribe(new RxSubscriber<BaseBean>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("请稍等");
            }

            @Override
            protected void _onNext(BaseBean baseBean) {
                mView.stopLoading();
                if(baseBean.getError_code()==0){
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(mContext);
                    sUtils.setStringValue(Constants.SESSION_KEY,baseBean.getSession_key());
                    Constants.SESSION_KEY=baseBean.getSession_key();
                    Rc4Md5Utils.secret_key = Constants.PRE_SECRET_KRY + baseBean.getSecret_key();
                    Constants.SVR_API_VER = baseBean.getSvr_api_ver();
                    mView.SendConnectSuccess();
                }else{
                    if(baseBean.getError_code()==204) {
                        Constants.SESSION_KEY="";
                        getConnect(context);
                    }else {
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) baseBean.getError_code()));
                    }
                }
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
                Log.i(TAG,message);
            }
        }));
    }
}
