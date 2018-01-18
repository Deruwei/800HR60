package com.hr.ui.ui.message.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.FindBean;
import com.hr.ui.ui.message.contract.FindContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/15.
 */

public class FindPresenter extends FindContract.Presenter {
    @Override
    public void getFindData(int page,String ad_type) {
        mRxManage.add(mModel.getFindData(page,ad_type).subscribe(new RxSubscriber<FindBean>(mContext,false) {
            @Override
            protected void _onNext(FindBean findBean) throws IOException {
                    if("0".equals(findBean.getError_code())) {
                        // System.out.print(s);
                       /* mView.setHideSuccess();*/
                        //mView.collectionPositionSuccess();
                        mView.getFiindDataSuccess(findBean.getList());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(findBean.getError_code())));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
