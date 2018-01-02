package com.hr.ui.ui.resume.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.ResumeTrainBean;
import com.hr.ui.bean.TrainExpData;
import com.hr.ui.ui.resume.contract.ResumeTrainExpContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeTrainExpPresenter extends ResumeTrainExpContract.Presenter {
    @Override
    public void getTrainExpData(String trainId) {
        mRxManage.add(mModel.getTrainExpData(trainId).subscribe(new RxSubscriber<ResumeTrainBean>(mContext,false) {
            @Override
            protected void _onNext(ResumeTrainBean resumeTrainBean) throws IOException {
              if(resumeTrainBean.getError_code()==0){
                  mView.getTrainSuccess(resumeTrainBean.getPlant_list().get(0));
              }else{
                  ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) resumeTrainBean.getError_code()));
              }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void deleteExpDate(String trainId) {
        mRxManage.add(mModel.deleteExpData(trainId).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        //System.out.print(s);
                        mView.deleteTrainSuccess();
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
    public void addOrReplaceData(TrainExpData trainExpData) {
        mRxManage.add(mModel.AddOrReplaceData(trainExpData).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        //System.out.print(s);
                        mView.addOrReplaceSucess();
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
