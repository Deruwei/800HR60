package com.hr.ui.ui.me.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.ui.me.contract.SettingContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/16.
 */

public class SettingPresenter extends SettingContract.Presenter {
    @Override
    public void getLoginOut() {
        mRxManage.add(mModel.getLoginOut().subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        mView.getLoginOutSuccess();
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
                ToastUitl.showShort(message);
            }
        }));
    }
}
