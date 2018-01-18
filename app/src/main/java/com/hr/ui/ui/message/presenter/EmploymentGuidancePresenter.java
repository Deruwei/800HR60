package com.hr.ui.ui.message.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.GuidanceBean;
import com.hr.ui.ui.message.contract.EmploymentGuidanceContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/16.
 */

public class EmploymentGuidancePresenter extends EmploymentGuidanceContract.Presenter {
    @Override
    public void getEmploymentGuidanceData(int page, final String guidanceId) {
        mRxManage.add(mModel.getEmploymentGuidanceData(page,guidanceId).subscribe(new RxSubscriber<GuidanceBean>(mContext,false) {
            @Override
            protected void _onNext(GuidanceBean guidanceBean) throws IOException {
                    if("0".equals(guidanceBean.getError_code())) {
                        // System.out.print(s);
                       /* mView.setHideSuccess();*/
                        mView.getEmploymentGuidanceSuccess(guidanceBean.getTitle_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(guidanceBean.getError_code())));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
