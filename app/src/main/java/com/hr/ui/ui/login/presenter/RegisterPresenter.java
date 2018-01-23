package com.hr.ui.ui.login.presenter;

import android.util.Log;
import android.widget.Toast;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.ui.login.contract.RegisterContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/5.
 */

public class RegisterPresenter extends RegisterContract.Presenter {
    public static final String TAG=RegisterPresenter.class.getSimpleName();
    @Override
    public void getValidCode(final String phoneNumber, String captcha,int type,String way) {
        mRxManage.add(mModel.getValidCode(phoneNumber,captcha,type,way).subscribe(new RxSubscriber<ValidCodeBean>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("请稍等");
            }
            @Override
            protected void _onNext(ValidCodeBean baseBean) {
                mView.stopLoading();
               // Log.i("现在的数据",baseBean.toString());
                if(baseBean.getError_code()==0){
                    ToastUitl.show("验证码发送成功",Toast.LENGTH_SHORT);
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(mContext);
                    sUtils.setIntValue(Constants.IS_FIRSTGETVALIDCODE,baseBean.getToken_times());
                    mView.sendValidCode(baseBean.getToken_times());
                }else if(baseBean.getError_code()==201){
                    ToastUitl.show("请输入正确的验证码",Toast.LENGTH_SHORT);
                }else if(baseBean.getError_code()==328){
                    mView.needToGetAutoCode();
                } else{
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
               // Log.i(TAG,message);
            }
        }));
    }

    @Override
    public void getRegister(final String phoneNumber, String validCode, final String psw) {
        mRxManage.add(mModel.getRegister(phoneNumber,validCode,psw).subscribe(new RxSubscriber<RegisterBean>(mContext,true) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading("请稍等");
            }
            @Override
            protected void _onNext(RegisterBean registerBean) {
                mView.stopLoading();
                if(registerBean.getError_code()==0){
                    ToastUitl.showShort("注册成功");
                    LoginBean loginBean=new LoginBean();
                    loginBean.setLoginType(0);
                    loginBean.setName(phoneNumber);
                    loginBean.setPassword(psw);
                    LoginDBUtils.insertData(loginBean);
                    mView.sendRegisterSuccess(registerBean.getUser_id());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) registerBean.getError_code()));
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
    public void getThidBinding(final ThirdLoginBean thirdPartBean, final String userName, final String psw, final int type) {
        mRxManage.add(mModel.getThirdBinding(thirdPartBean,userName,psw,type).subscribe(new RxSubscriber<RegisterBean>(mContext,true) {
            @Override
            protected void _onNext(RegisterBean registerBean) throws IOException {
                if(registerBean.getError_code()==0){
                    LoginBean loginBean=new LoginBean();
                    if("QQ".equals(Constants.TYPE_THIRDPARTLOGIN)){
                        loginBean.setLoginType(2);
                    }else{
                        loginBean.setLoginType(3);
                    }
                    loginBean.setThirdPartUid(thirdPartBean.getUId());
                    loginBean.setThirdPartLoginType(Constants.TYPE_THIRDPARTLOGIN);
                    loginBean.setThirdPartSUid(registerBean.getUser_id()+"");
                    LoginDBUtils.insertData(loginBean);
                    mView.bindingSuccess(registerBean.getUser_id());
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
    public void getResumeList() {
        mRxManage.add(mModel.getResumeList().subscribe(new RxSubscriber<MultipleResumeBean>(mContext,false) {
            @Override
            protected void _onNext(MultipleResumeBean multipleResumeBean)  {
                if("0".equals(multipleResumeBean.getError_code())){
                    mView.getResumeListSuccess(multipleResumeBean);
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(multipleResumeBean.getError_code())));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void getResumeData(String resumeId) {
        mRxManage.add(mModel.getResumeData(resumeId).subscribe(new RxSubscriber<ResumeBean>(mContext,false) {
            @Override
            protected void _onNext(ResumeBean resumeBean) throws IOException {
                if(resumeBean.getError_code()==0){
                    mView.getResumeDataSuccess(resumeBean);
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) resumeBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

}
