package com.hr.ui.ui.main.presenter;

import android.util.Log;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.HomeRecommendBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.main.contract.HomeFragmentContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/5.
 */

public class HomeFragmentPresenter extends HomeFragmentContract.Presenter {
    @Override
    public void getRecommendJobInfo(String resumeId,int limit,boolean isCanRefresh) {
        mRxManage.add(mModel.getRecommendJobInfo(resumeId,limit).subscribe(new RxSubscriber<HomeRecommendBean>(mContext,isCanRefresh) {
            @Override
            protected void _onNext(HomeRecommendBean homeRecommendBean) throws IOException {
                    if(homeRecommendBean.getError_code()==0) {
                        mView.getRecommendJobSuccess(homeRecommendBean.getJobs_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(homeRecommendBean.getError_code() ));
                    }
            }

            @Override
            protected void _onError(String message) {
                //Log.i("你好",message);
                /*mView.getRecommendJobError();*/
                mView.cantGetData();
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
                        JSONArray array=jsonObject.getJSONArray("list");
                       double score=array.getJSONObject(0).getDouble("score");
                        mView.getResumeScoreSuccess(score);
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
            }
        }));
    }

    @Override
    public void getRecommendJob(int page,int pageNum,boolean isCanRefresh) {
        mRxManage.add(mModel.getRecommendJob(page,pageNum).subscribe(new RxSubscriber<RecommendJobBean>(mContext,isCanRefresh) {
            @Override
            protected void _onNext(RecommendJobBean recommendJobBean) throws IOException {
                if("0".equals(recommendJobBean.getError_code())) {
                    mView.getRecommendJobSuccess2(recommendJobBean.getJobs_list());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(recommendJobBean.getError_code())));
                }
            }

            @Override
            protected void _onError(String message) {
                mView.getRecommendJobError();
            }
        }));
    }
}
