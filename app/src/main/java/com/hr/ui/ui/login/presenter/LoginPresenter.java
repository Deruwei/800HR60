package com.hr.ui.ui.login.presenter;

import android.util.Log;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ThirdLoginBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.LoginDBUtils;
import com.hr.ui.db.ThirdPartDao;
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
    public void getLogin(final String phoneNumber, final String psw, final int type) {
        mRxManage.add(mModel.getLogin(phoneNumber,psw,type).subscribe(new RxSubscriber<RegisterBean>(mContext,false) {
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
    public void getThirdPartLogin(final ThirdLoginBean thirdPartBean) {
        mRxManage.add(mModel.getThirdPartLogin(thirdPartBean).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) {
                try {
                    String s=responseBody.string().toString();
                    Log.i(TAG,responseBody.string());
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0){
                        ThirdPartDao.insertThirPart(thirdPartBean);
                        //ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
                        int userId=jsonObject.getInt("user_id");
                        LoginBean loginBean=new LoginBean();
                        if("QQ".equals(Constants.TYPE_THIRDPARTLOGIN)){
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
                ToastUitl.showShort(message);
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
                    if("QQ".equals(Constants.TYPE_THIRDPARTLOGIN)){
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
