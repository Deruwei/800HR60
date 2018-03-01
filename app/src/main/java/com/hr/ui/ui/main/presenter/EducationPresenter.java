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
    @Override
    public void createNewResume() {
        mRxManage.add(mModel.creatNewResume().subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s=responseBody.string().toString();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String error=jsonObject.getString("error_code");
                    if("0".equals(error)){
                        String resumeId=jsonObject.getString("resume_id");
                        mView.creatNewResumeSuccess(Integer.parseInt(resumeId));
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

             /*   if ("0".equals(pictureBean.getError_code())){
                    mView.uploadImageSuccess(pictureBean.getPic_filekey());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(pictureBean.getError_code())));
                }*/
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
