package com.hr.ui.ui.main.presenter;

import android.util.Log;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.main.contract.JobSearchFragmentContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/9.
 */

public class JobSearchFragmentPresenter extends JobSearchFragmentContract.Presenter {
    @Override
    public void getSearchList(JobSearchBean jobSearchBean, int page, final boolean isCanRefresh) {
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

    @Override
    public void getTopSearchJob(JobSearchBean jobSearchBean) {
        mRxManage.add(mModel.getTopSearchJob(jobSearchBean).subscribe(new RxSubscriber<RecommendJobBean>(mContext,false) {
            @Override
            protected void _onNext(RecommendJobBean recommendJobBean) throws IOException {
                //Log.i("现在的数据",recommendJobBean.toString());
                if(recommendJobBean.getError_code().equals("0")) {
                    mView.getTopSearchJobSuccess(recommendJobBean.getJobs_list());
                }else{
                    mView.getTopSearchFaild();
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
    public void getNotice(String cid,String aid) {
        mRxManage.add(mModel.getNotice(cid,aid).subscribe(new RxSubscriber<FindBean>(mContext,false) {
            @Override
            protected void _onNext(FindBean findBean) throws IOException {
                if("0".equals(findBean.getError_code())){
                    mView.getNoticeSuccess(findBean.getList());
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
