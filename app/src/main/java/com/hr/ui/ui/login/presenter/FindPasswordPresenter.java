package com.hr.ui.ui.login.presenter;

import android.util.Log;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.NoDataBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.login.contract.FindPasswordContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/6.
 */

public class FindPasswordPresenter extends FindPasswordContract.Presenter {
    public static final String TAG=FindPasswordPresenter.class.getSimpleName();
    @Override
    public void resetPhonePsw(String phoneNumber, String validCode, String newPsw) {
        mRxManage.add(mModel.resetPhonePsw(phoneNumber,validCode,newPsw).subscribe(new RxSubscriber<NoDataBean>(mContext,false) {
            @Override
            protected void _onNext(NoDataBean noDataBean) {
                if(noDataBean.getError_code()==0){
                    ToastUitl.showShort("修改密码成功");
                    mView.resetPasswordSuccess();
                }else if(noDataBean.getError_code()==201) {
                    if ("token".equals(noDataBean.getError_field())){
                        ToastUitl.showShort(R.string.error_validCode);
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) noDataBean.getError_code()));
                    }
                } else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) noDataBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {
            }
        }));
    }

    @Override
    public void getValidCode(String phoneNumber, String captcha, int type, String way) {
        mRxManage.add(mModel.getValidCode(phoneNumber,captcha,type,way).subscribe(new RxSubscriber<ValidCodeBean>(mContext,false) {
            @Override
            protected void _onNext(ValidCodeBean validCodeBean) {
               // Log.i("现在的数据",validCodeBean.toString());
                if(validCodeBean.getError_code()==0){
                    ToastUitl.show("验证码发送成功", Toast.LENGTH_SHORT);
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(mContext);
                    sUtils.setIntValue(Constants.IS_FIRSTGETVALIDCODE,validCodeBean.getToken_times());
                    mView.getValidCodeSuccess(validCodeBean.getToken_times());
                }else if(validCodeBean.getError_code()==201){
                    ToastUitl.show("请输入正确的验证码",Toast.LENGTH_SHORT);
                }else if(validCodeBean.getError_code()==328){
                    mView.setNeedAotuCode();
                } else{
                    Toast.makeText(mContext,Rc4Md5Utils.getErrorResourceId((int) validCodeBean.getError_code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
    @Override
    public void getAutoCode() {
        mRxManage.add(mModel.getAutoCode().subscribe(new RxSubscriber<AutoCodeBean>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("请稍等");
            }

            @Override
            protected void _onNext(AutoCodeBean baseBean) {
                mView.stopLoading();
                if(baseBean.getError_code()==0){
                    mView.sendAutoCode(baseBean.getCaptcha());
                }else{
                    Toast.makeText(mContext,Rc4Md5Utils.getErrorResourceId((int) baseBean.getError_code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
                Log.i(TAG,message);
            }
        }));
    }
    @Override
    public void validPhoneIsExit(String phone) {
        mRxManage.add(mModel.validPhoneIsExit(phone).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody)  {
                try {
                    String s=responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    int errorCode=jsonObject.getInt("error_code");
                    if(errorCode==0) {
                        String flag = jsonObject.getString("flag_exist");
                        mView.phoneisExit(flag);
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(errorCode));
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
