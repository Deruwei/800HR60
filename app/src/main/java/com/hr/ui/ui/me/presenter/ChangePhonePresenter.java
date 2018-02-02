package com.hr.ui.ui.me.presenter;

import android.util.Log;
import android.widget.Toast;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.me.contract.ChangePhoneContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/18.
 */

public class ChangePhonePresenter extends ChangePhoneContract.Presenter {
    @Override
    public void changePhone(String phoneNumber, String validCode) {
        mRxManage.add(mModel.changePhone(phoneNumber,validCode).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s=responseBody.string().toString();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String error=jsonObject.getString("error_code");
                    if("0".equals(error)){
                       mView.changePhoneSuccess();
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(error) ));
                    }
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
    public void getValidCode(String phoneNumber, String type,int way, String captcha) {
        mRxManage.add(mModel.getValidCode(phoneNumber,type,way,captcha).subscribe(new RxSubscriber<ValidCodeBean>(mContext,false) {
            @Override
            protected void _onNext(ValidCodeBean baseBean) {
                mView.stopLoading();
                if(baseBean.getError_code()==0){
                    ToastUitl.show("验证码发送成功",Toast.LENGTH_SHORT);
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(mContext);
                    sUtils.setIntValue(Constants.IS_FIRSTGETVALIDCODE,baseBean.getToken_times());
                    mView.getValidCodeSuccess(baseBean.getToken_times());
                }else if(baseBean.getError_code()==201){
                    ToastUitl.show("请输入正确的验证码",Toast.LENGTH_SHORT);
                } else{
                    Toast.makeText(mContext,Rc4Md5Utils.getErrorResourceId((int) baseBean.getError_code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getCaptcha() {
        mRxManage.add(mModel.getCaptcha().subscribe(new RxSubscriber<AutoCodeBean>(mContext,false) {
            @Override
            protected void _onNext(AutoCodeBean baseBean) {
                mView.stopLoading();
                if(baseBean.getError_code()==0){
                    mView.getCaptchaSuccess(baseBean.getCaptcha());
                }else{
                    Toast.makeText(mContext, Rc4Md5Utils.getErrorResourceId((int) baseBean.getError_code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
                // Log.i(TAG,message);
            }
        }));
    }
}
