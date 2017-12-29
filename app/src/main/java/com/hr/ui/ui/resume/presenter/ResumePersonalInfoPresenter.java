package com.hr.ui.ui.resume.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.PictureBean;
import com.hr.ui.bean.ResumePersonalInfoBean;
import com.hr.ui.ui.resume.contract.ResumePersonalInfoContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/25.
 */

public class ResumePersonalInfoPresenter extends ResumePersonalInfoContract.Presenter {
    @Override
    public void getPersonalInfo() {
        mRxManage.add(mModel.getPersonalInfo().subscribe(new RxSubscriber<ResumePersonalInfoBean>(mContext,true) {
            @Override
            protected void _onNext(ResumePersonalInfoBean resumePersonalInfoBean) throws IOException {
                    if(resumePersonalInfoBean.getError_code()==0) {
                      mView.getPersonalInfoSuccess(resumePersonalInfoBean);
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) resumePersonalInfoBean.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void updatePersonalInfo(PersonalInformationData personalInformationData) {
     mRxManage.add(mModel.UpdatePersonalInfo(personalInformationData).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
         @Override
         protected void _onNext(ResponseBody responseBody) throws IOException {
             String s= null;
             try {
                 s = responseBody.string().toString();
                 JSONObject jsonObject=new JSONObject(s);
                 double error_code=jsonObject.getDouble("error_code");
                 if(error_code==0) {
                     System.out.print(s);
                     mView.updatePersonalInfoSuccess();
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
    public void upLoadImage(String content) {
        mRxManage.add(mModel.upLoadImage(content).subscribe(new RxSubscriber<PictureBean>(mContext,false) {
            @Override
            protected void _onNext(PictureBean pictureBean) throws IOException {
                if (pictureBean.getError_code()==0){
                    mView.uploadImageSuccess(pictureBean.getPic_filekey());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) pictureBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
