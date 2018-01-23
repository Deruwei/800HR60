package com.hr.ui.ui.main.presenter;

import android.util.Log;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.EducationBase;
import com.hr.ui.bean.EducationData;
import com.hr.ui.ui.main.contract.EducationContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/13.
 */

public class EducationPresenter extends EducationContract.Presenter {
    public static final String TAG=EducationPresenter.class.getSimpleName();
    @Override
    public void sendEducationToResume(EducationData educationData) {
        mRxManage.add(mModel.sendEducationToResume(educationData).subscribe(new RxSubscriber<EducationBase>(mContext,true) {
            @Override
            protected void _onNext(EducationBase educationBase) throws IOException {
                    if(educationBase.getError_code()==0) {
                        mView.sendEducationSuccess(educationBase.getEducation_id());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(educationBase.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
