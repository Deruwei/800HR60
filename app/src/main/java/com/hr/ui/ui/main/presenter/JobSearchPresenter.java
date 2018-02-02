package com.hr.ui.ui.main.presenter;

import android.content.Intent;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.FindBean;
import com.hr.ui.ui.main.contract.JobSearchContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/2/1.
 */

public class JobSearchPresenter extends JobSearchContract.Presenter {
    @Override
    public void getHotSearchJob(int pageNum, String c_type) {
        mRxManage.add(mModel.getHotSearchJob(pageNum,c_type).subscribe(new RxSubscriber<FindBean>(mContext,false) {
            @Override
            protected void _onNext(FindBean findBean) throws IOException {
                if("0".equals(findBean.getError_code())){
                    mView.getHotSearchJobSuccess(findBean.getList());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(findBean.getError_code())));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
