package com.hr.ui.ui.job.presenter;

import android.content.Context;
import android.util.Log;

import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.PositionBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.job.contract.PositionPageContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/11.
 */

public class PositionPagePresenter extends PositionPageContract.Presenter {

    @Override
    public void getPositionData(String jobId, Context context) {
        mRxManage.add(mModel.getPositionData(jobId).subscribe(new RxSubscriber<PositionBean>(context,true) {
            @Override
            protected void _onNext(PositionBean positionBean) throws IOException {
                //Log.i("你好","2");
                    if("0".equals(positionBean.getError_code())) {
                        mView.getPositionSuccess(positionBean.getJob_info());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(positionBean.getError_code())));
                    }
            }

            @Override
            protected void _onError(String message) {
               // Log.i("你好","我到这里了");
                mView.getPositionFaile();
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
                    int error_code=jsonObject.getInt("error_code");
                    //Log.i("现在的参数",error_code+"");
                    if(error_code==0) {
                        //System.out.print(s);
                      /*  mView.setHideSuccess();*/
                      mView.deliverPositionSuccess();
                    }else{
                        if(error_code==413||error_code==404||error_code==417){
                            mView.goToCompleteResume(error_code);
                        }else {
                            ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(error_code));
                        }
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
    public void getResumeScore(String id) {
            mRxManage.add(mModel.getResumeScore(id).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
                @Override
                protected void _onNext(ResponseBody responseBody) throws IOException {
                    String s=responseBody.string().toString();
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        String error=jsonObject.getString("error_code");
                        if("0".equals(error)){
                            if(!jsonObject.toString().contains("score")){
                                mView.retryCulScore();
                            }else {
                                JSONArray array=jsonObject.getJSONArray("list");
                                double score = array.getJSONObject(0).getDouble("score");
                                mView.getResumeScoreSuccess(score);
                            }
                        }else{
                            ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(error) ));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                protected void _onError(String message) {
                    // Log.i("okht",message);
                    mView.retryCulScore();
                }
            }));
    }
    @Override
    public void getSearchList(JobSearchBean jobSearchBean, int page, boolean isCanRefresh) {
        mRxManage.add(mModel.getSearchList(jobSearchBean,page).subscribe(new RxSubscriber<RecommendJobBean>(mContext,isCanRefresh) {
            @Override
            protected void _onNext(RecommendJobBean recommendJobBean) throws IOException {
                if(recommendJobBean.getError_code().equals("0")) {
                    mView.getSearchDataSuccess(recommendJobBean.getJobs_list());
                }else{
                }
            }

            @Override
            protected void _onError(String message) {
            }
        }));
    }

}
