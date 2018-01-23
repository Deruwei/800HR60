package com.hr.ui.ui.main.presenter;

import android.util.Log;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.WorkExpBase;
import com.hr.ui.bean.WorkExpBean;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.ui.main.contract.WorkExpContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/13.
 */

public class WorkExpPresenter extends WorkExpContract.Presenter {
    public static final String TAG=WorkExpPresenter.class.getSimpleName();
    @Override
    public void sendWorkExpToResume(WorkExpData workExpData) {
            mRxManage.add(mModel.sendWorkExpToResume(workExpData).subscribe(new RxSubscriber<WorkExpBase>(mContext,true) {
                @Override
                protected void _onNext(WorkExpBase workExpBase) throws IOException {
                        if(workExpBase.getError_code()==0) {
                            mView.sendWorkExpSuccess(workExpBase.getExperience_id());
                        }else{
                            ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(workExpBase.getError_code()));
                        }

                }

                @Override
                protected void _onError(String message) {

                }
            }));
    }
}
