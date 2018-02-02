package com.hr.ui.ui.message.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.ui.message.contract.InviteContract;
import com.hr.ui.utils.EncryptUtils;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/16.
 */

public class InvitePresenter extends InviteContract.Presenter {
    @Override
    public void getJobInfo(String jobId,boolean isCanRefresh) {
        mRxManage.add(mModel.getJobInfo(jobId).subscribe(new RxSubscriber<PositionBean>(mContext,false) {
            @Override
            protected void _onNext(PositionBean positionBean) throws IOException {
                if("0".equals(positionBean.getError_code())){
                    mView.getJobInfoSuccess(positionBean.getJob_info());
                }else if("401".equals(positionBean.getError_code())||"402".equals(positionBean.getError_code())){
                    mView.noPosition();
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

    @Override
    public void setInviteIsRead(String recordId) {
        mRxManage.add(mModel.setInviteIsRead(recordId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s=responseBody.string().toString();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    int error=jsonObject.getInt("error_code");
                    if(error==0){
                        mView.setInviteIsReadSuccess();
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(error));
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

    @Override
    public void getInviteInfo(String recordId) {
        mRxManage.add(mModel.getInviteInfo(recordId).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s=responseBody.string().toString();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String error=jsonObject.getString("error_code");
                    if("0".equals(error)){
                        //mView.setInviteIsReadSuccess();
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
