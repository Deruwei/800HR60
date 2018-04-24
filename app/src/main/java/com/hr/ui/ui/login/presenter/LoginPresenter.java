package com.hr.ui.ui.login.presenter;

import android.util.Log;
import android.widget.Toast;

import com.hr.ui.R;
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
import com.hr.ui.db.ThirdPartDao;
import com.hr.ui.ui.login.contract.LoginContract;
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

public class LoginPresenter extends LoginContract.Presenter {
    public static final String TAG=LoginPresenter.class.getSimpleName();
    @Override
    public void getLogin(final String phoneNumber, final String psw, final int type) {
        mRxManage.add(mModel.getLogin(phoneNumber,psw,type).subscribe(new RxSubscriber<RegisterBean>(mContext,true) {
            @Override
            protected void _onNext(RegisterBean registerBean) {
                if(registerBean.getError_code()==0){
                    LoginBean loginBean=new LoginBean();
                    if(type==1) {
                        loginBean.setLoginType(0);
                    }else if(type==2){
                        loginBean.setLoginType(1);
                    }
                    loginBean.setName(phoneNumber);
                    loginBean.setPassword(psw);
                    LoginDBUtils.insertData(loginBean);
                    mView.sendLoginSuccess(registerBean.getUser_id());
                }else if(registerBean.getError_code()==311){
                    if(type==1){
                        ToastUitl.showShort(R.string.error_phoneOrPsw);
                    }else{
                        ToastUitl.showShort(R.string.error_311);
                    }
                }else{
                    if(type==3){
                        if(registerBean.getError_code()==201){
                            ToastUitl.showShort(R.string.error_validCode);
                        }
                    }else {
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) registerBean.getError_code()));
                    }
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void getThirdPartLogin(final ThirdLoginBean thirdPartBean) {
        mRxManage.add(mModel.getThirdPartLogin(thirdPartBean).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) {
                try {
                    String s=responseBody.string().toString();
                    Log.i(TAG,responseBody.string());
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    ThirdPartDao.insertThirPart(thirdPartBean);
                    if(error_code==0){
                        //ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
                        System.out.println(thirdPartBean.toString());
                        int userId=jsonObject.getInt("user_id");
                        LoginBean loginBean=new LoginBean();
                        if("qq".equals(Constants.TYPE_THIRDPARTLOGIN)){
                            loginBean.setLoginType(2);
                        }else{
                            loginBean.setLoginType(3);
                        }
                        loginBean.setThirdPartUid(thirdPartBean.getUId());
                        loginBean.setThirdPartLoginType(Constants.TYPE_THIRDPARTLOGIN);
                        loginBean.setThirdPartSUid(userId+"");
                        LoginDBUtils.insertData(loginBean);
                        mView.thirdPartLoginSuccess(userId);
                    }else if(error_code==305){
                        //ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
                        mView.thirdPartLoginGoToBind();
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
                //ToastUitl.showShort(message);
            }
        }));
    }

    @Override
    public void getThidBinding(final ThirdLoginBean thirdPartBean, String userName, String psw, int type) {
        mRxManage.add(mModel.getThirdBinding(thirdPartBean,userName,psw,type).subscribe(new RxSubscriber<RegisterBean>(mContext,false) {
            @Override
            protected void _onNext(RegisterBean registerBean) throws IOException {
                if(registerBean.getError_code()==0){
                    mView.bindingSuccess(registerBean.getUser_id());
                    LoginBean loginBean=new LoginBean();
                    if("qq".equals(Constants.TYPE_THIRDPARTLOGIN)){
                        loginBean.setLoginType(2);
                    }else{
                        loginBean.setLoginType(3);
                    }
                    loginBean.setThirdPartUid(thirdPartBean.getUId());
                    loginBean.setThirdPartLoginType(Constants.TYPE_THIRDPARTLOGIN);
                    loginBean.setThirdPartSUid(registerBean.getUser_id()+"");
                    LoginDBUtils.insertData(loginBean);
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
                   // Log.i("ok","你好");
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
                        mView.phoneIsExit(flag);
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
                    ToastUitl.show("验证码发送成功", Toast.LENGTH_SHORT);
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(mContext);
                    sUtils.setIntValue(Constants.IS_FIRSTGETVALIDCODE,baseBean.getToken_times());
                    mView.sendValidCode(baseBean.getToken_times());
                }else if(baseBean.getError_code()==201){
                    ToastUitl.show("图形验证码错误",Toast.LENGTH_SHORT);
                }else if(baseBean.getError_code()==328){
                    mView.needToGetAutoCode();
                } else{
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
}
