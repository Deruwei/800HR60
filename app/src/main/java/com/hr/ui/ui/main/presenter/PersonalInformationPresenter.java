package com.hr.ui.ui.main.presenter;

import android.util.Log;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.PictureBean;
import com.hr.ui.bean.ResumeIdBean;
import com.hr.ui.ui.main.contract.PersonalInformationContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/13.
 */

public class PersonalInformationPresenter extends PersonalInformationContract.Presenter {
    public static final String TAG=PersonalInformationPresenter.class.getSimpleName();
    @Override
    public void sendPersonalInformationToResume(PersonalInformationData personalInformationData) {
        mRxManage.add(mModel.sendPersonalInformationToResume(personalInformationData).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody){
                String s= null;
                try {
                    s = responseBody.string().toString();
                   // Log.i(TAG,responseBody.string());
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        mView.sendInformationSuccess();
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
    public void setResumeType(String type) {
        mRxManage.add(mModel.setResumeType(type).subscribe(new RxSubscriber<ResumeIdBean>(mContext,false) {
            @Override
            protected void _onNext(ResumeIdBean resumeIdBean) throws IOException {
                if(resumeIdBean.getError_code()==0){
                    mView.sendResumeId(resumeIdBean.getResume_id());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) resumeIdBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
    @Override
    public void upLoadImage(String content) {
        mRxManage.add(mModel.upLoadImage(content).subscribe(new RxSubscriber<PictureBean>(mContext,false) {
            @Override
            protected void _onNext(PictureBean pictureBean) throws IOException {
                if ("0".equals(pictureBean.getError_code())){
                    mView.uploadImageSuccess(pictureBean.getPic_filekey());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(pictureBean.getError_code())));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

}
