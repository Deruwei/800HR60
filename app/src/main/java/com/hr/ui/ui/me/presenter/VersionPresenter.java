package com.hr.ui.ui.me.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.VersionBean;
import com.hr.ui.ui.me.contract.VersionContract;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/2/8.
 */

public class VersionPresenter extends VersionContract.Presenter {
    @Override
    public void getVersion(String cur_clienVersion) {
        mRxManage.add(mModel.getVersion(cur_clienVersion).subscribe(new RxSubscriber<VersionBean>(mContext,false) {
            @Override
            protected void _onNext(VersionBean versionBean) throws IOException {
                mView.getVersionSuccess(versionBean.getAndroid());
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
