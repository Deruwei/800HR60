package com.hr.ui.ui.job.presenter;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.JobSearchBean;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.job.contract.RecommendJobActivityContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

public class RecommendJobActivityPresenter extends RecommendJobActivityContract.Presenter {
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
}
