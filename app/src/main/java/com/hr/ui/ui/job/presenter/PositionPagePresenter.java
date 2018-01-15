package com.hr.ui.ui.job.presenter;

import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.ui.job.contract.PositionPageContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/11.
 */

public class PositionPagePresenter extends PositionPageContract.Presenter {

    @Override
    public void getPositionData(String jobId) {
        mRxManage.add(mModel.getPositionData(jobId).subscribe(new RxSubscriber<PositionBean>(mContext,false) {
            @Override
            protected void _onNext(PositionBean positionBean) throws IOException {

                    if("0".equals(positionBean.getError_code())) {
                        mView.getPositionSuccess(positionBean.getJob_info());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(positionBean.getError_code())));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void collectionPosition(String jobId) {
        mRxManage.add(mModel.collectionPosition(jobId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                       // System.out.print(s);
                       /* mView.setHideSuccess();*/
                       mView.collectionPositionSuccess();
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
    public void deliverPosition(String jobId) {
        mRxManage.add(mModel.deliverPosition(jobId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        //System.out.print(s);
                      /*  mView.setHideSuccess();*/
                      mView.deliverPositionSuccess();
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
