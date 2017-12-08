package com.hr.ui.ui.login.presenter;

import android.util.Log;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ThirdPartBean;
import com.hr.ui.ui.login.contract.LoginContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/6.
 */

public class LoginPresenter extends LoginContract.Presenter {
    public static final String TAG=LoginPresenter.class.getSimpleName();
    @Override
    public void getLogin(String phoneNumber, String psw,int type) {
        mRxManage.add(mModel.getLogin(phoneNumber,psw,type).subscribe(new RxSubscriber<RegisterBean>(mContext,false) {
            @Override
            protected void _onNext(RegisterBean registerBean) {
                if(registerBean.getError_code()==0){
                    mView.sendLoginSuccess(registerBean.getUser_id());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) registerBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void getThirdPartLogin(ThirdPartBean thirdPartBean) {
        mRxManage.add(mModel.getThirdPartLogin(thirdPartBean).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) {
                try {
                    String s=responseBody.string().toString();
                    Log.i(TAG,responseBody.string());
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0){
                        mView.thirdPartLoginSuccess();
                    }else if(error_code==305){
                        mView.thirdPartLoginGoToBind();
                    }else{

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
    public void getThidBinding(ThirdPartBean thirdPartBean, String userName, String psw, int type) {
    }

}
