package com.hr.ui.ui.me.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.ui.me.contract.ChangePswContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/18.
 */

public class ChangePswPresenter  extends ChangePswContract.Presenter{

    @Override
    public void getChangePsw(String oldPsw, String newPsw) {
        mRxManage.add(mModel.getChangePsw(oldPsw,newPsw).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        mView.getChangePswSuccess();
                    }else{
                        if (error_code==311){
                            ToastUitl.showShort("旧密码错误");
                        }else {
                            ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
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
                //ToastUitl.showShort(message);
            }
        }));
    }
}
