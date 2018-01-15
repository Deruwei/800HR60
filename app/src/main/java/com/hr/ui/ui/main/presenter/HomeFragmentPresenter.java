package com.hr.ui.ui.main.presenter;

import android.util.Log;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.RecommendJobBean;
import com.hr.ui.ui.main.contract.HomeFragmentContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/5.
 */

public class HomeFragmentPresenter extends HomeFragmentContract.Presenter {
    @Override
    public void getRecommendJobInfo(int page,int limit) {
        mRxManage.add(mModel.getRecommendJobInfo(page,limit).subscribe(new RxSubscriber<RecommendJobBean>(mContext,false) {
            @Override
            protected void _onNext(RecommendJobBean recommendJobBean) throws IOException {
                    if("0".equals(recommendJobBean.getError_code())) {
                        mView.getRecommendJobSuccess(recommendJobBean.getJobs_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(recommendJobBean.getError_code()) ));
                    }
            }

            @Override
            protected void _onError(String message) {
                //Log.i("你好",message);
            }
        }));
    }
}
