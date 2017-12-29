package com.hr.ui.ui.resume.presenter;

import android.view.Display;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.EducationData;
import com.hr.ui.ui.resume.activity.ResumeEducationActivity;
import com.hr.ui.ui.resume.contract.ResumeEducationContract;
import com.hr.ui.ui.resume.contract.ResumeJobOrderContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringBufferInputStream;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/29.
 */

public class ResumeEducationPresenter  extends ResumeEducationContract.Presenter {
    @Override
    public void addOrUpdateEducation(EducationData educationData) {
        mRxManage.add(mModel.addOrUpdateEducation(educationData).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.addOrUpdateEducationSuccess();
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
    public void getEducationInfo(String education) {
        mRxManage.add(mModel.getEducationInfo(education).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.getEducationInfoSuccess();
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
    public void deleteEducation(String education) {
            mRxManage.add(mModel.deleteEducation(education).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
                @Override
                protected void _onNext(ResponseBody responseBody) throws IOException {
                    String s= null;
                    try {
                        s = responseBody.string().toString();
                        JSONObject jsonObject=new JSONObject(s);
                        double error_code=jsonObject.getDouble("error_code");
                        if(error_code==0) {
                            System.out.print(s);
                            mView.deleteEducationSuccess();
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
}
