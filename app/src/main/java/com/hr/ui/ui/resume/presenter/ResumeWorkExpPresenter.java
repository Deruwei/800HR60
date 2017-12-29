package com.hr.ui.ui.resume.presenter;

import com.hr.ui.base.RxBus;
import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.WorkExpData;
import com.hr.ui.ui.resume.contract.ResumeJobOrderContract;
import com.hr.ui.ui.resume.contract.ResumeWorkExpContract;
import com.hr.ui.ui.resume.model.ResumeWorkExpModel;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/29.
 */

public class ResumeWorkExpPresenter extends ResumeWorkExpContract.Presenter {
    @Override
    public void getWorkExpInfo(String experienceId) {
        mRxManage.add(mModel.getWorkExpInfo(experienceId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.getWorkExpinfo();
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
    public void deleteWorkExp(String experienceId) {
        mRxManage.add(mModel.deleteWorkExp(experienceId).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.deleteSuccess();
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
    public void addOrUpdateWorkExp(WorkExpData workExpData) {
        mRxManage.add(mModel.addOrUpdateWorkExp(workExpData).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.addOrUpdateWorkExp();
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
