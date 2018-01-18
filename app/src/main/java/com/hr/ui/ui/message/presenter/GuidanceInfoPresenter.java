package com.hr.ui.ui.message.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.GuidanceInfoBean;
import com.hr.ui.ui.message.contract.GuidanceInfoContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/16.
 */

public class GuidanceInfoPresenter extends GuidanceInfoContract.Presenter {
    @Override
    public void getGuidanceInfo(String guidanceId) {
        mRxManage.add(mModel.getGuidanceInfo(guidanceId).subscribe(new RxSubscriber<GuidanceInfoBean>(mContext,false) {
            @Override
            protected void _onNext(GuidanceInfoBean guidanceInfoBean) throws IOException {
                    if(guidanceInfoBean.getError_code()==0) {
                        // System.out.print(s);
                       /* mView.setHideSuccess();*/
                        mView.getGuidanceInfoSuccess(guidanceInfoBean.getTitle_content_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(guidanceInfoBean.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }
}
