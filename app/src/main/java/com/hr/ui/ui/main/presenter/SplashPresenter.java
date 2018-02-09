package com.hr.ui.ui.main.presenter;

import android.content.Context;
import android.icu.text.Replaceable;
import android.renderscript.ScriptGroup;
import android.support.v7.util.AsyncListUtil;
import android.util.Log;
import android.widget.Toast;

import com.hr.ui.R;
import com.hr.ui.api.Api;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.ArrayInfoBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.LoginBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.VersionBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.hr.ui.ui.main.contract.SplashContract;
import com.hr.ui.utils.NetUtils;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SaveFile;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.sharesdk.framework.Platform;
import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/11/21.
 */

public class SplashPresenter extends SplashContract.Presenter {
    private static final String TAG=SplashPresenter.class.getSimpleName();
    @Override
    public void getConnect(final Context context) {
        mRxManage.add(mModel.getConnect().subscribe(new RxSubscriber<BaseBean>(mContext,true) {

            @Override
            protected void _onNext(BaseBean baseBean) {
                mView.stopLoading();
                if(baseBean.getError_code()==0){
                    SharedPreferencesUtils sUtils=new SharedPreferencesUtils(mContext);
                    sUtils.setStringValue(Constants.SESSION_KEY,baseBean.getSession_key());
                    Constants.SESSION_KEY=baseBean.getSession_key();
                    Rc4Md5Utils.secret_key = Constants.PRE_SECRET_KRY + baseBean.getSecret_key();
                    getArrayInfo();
                    Constants.SVR_API_VER = baseBean.getSvr_api_ver();
                    mView.SendConnectSuccess();
                }else{
                    if(baseBean.getError_code()==204||baseBean.getError_code()==205||baseBean.getError_code()==303) {
                        Rc4Md5Utils.secret_key = Constants.INIT_SECRET_KRY;
                        getConnect(context);
                    }else {
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) baseBean.getError_code()));
                    }
                }
            }

            @Override
            protected void _onError(String message) {
                //ToastUitl.showShort(message);
                mView.onConnectError();
            }
        }));
    }

    @Override
    public void getAutoPhoneLogin(String phoneNumber, String psw,int type,boolean isCanRefresh) {
        mRxManage.add(mModel.getAutoPhoneLogin(phoneNumber,psw,type).subscribe(new RxSubscriber<RegisterBean>(mContext,isCanRefresh) {
            @Override
            protected void _onNext(RegisterBean registerBean) throws IOException {
                if(registerBean.getError_code()==0){
                    MobclickAgent.onProfileSignIn(registerBean.getUser_id()+"");
                    mView.phoneLoginSuccess(registerBean.getUser_id());
                }else if(registerBean.getError_code()==301) {
                    ToastUitl.showShort(R.string.error_301);
                    mView.onConnectError();
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) registerBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {
                //ToastUitl.showShort(message);
                mView.onConnectError();
            }
        }));
    }


    @Override
    public void getThirdBindingLogin(LoginBean loginBean,boolean isCanRefresh) {
        mRxManage.add(mModel.getAutoThirdBindingLogin(loginBean).subscribe(new RxSubscriber<ResponseBody>(mContext,isCanRefresh) {
            @Override
            protected void _onNext(ResponseBody responseBody) {
                try {
                    String s = responseBody.string().toString();
                    //Log.i(TAG, responseBody.string());
                    JSONObject jsonObject = new JSONObject(s);
                    double error_code = jsonObject.getDouble("error_code");
                    if (error_code == 0) {
                        //ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
                        int userId = jsonObject.getInt("user_id");
                        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
                        sUtils.setStringValue(Constants.USERID,userId+"");
                        //当用户使用自有账号登录时，可以这样统计：
                        //当用户使用第三方账号（如新浪微博）登录时，可以这样统计：
                        MobclickAgent.onProfileSignIn("WB",userId+"");

                        mView.thirdBindingLoginSuccess(userId);
                    } else if(error_code==301) {
                        ToastUitl.showShort(R.string.error_301);
                        mView.onConnectError();
                    }else {
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
                mView.onConnectError();
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
                ToastUitl.showShort(message);
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
                    SharedPreferencesUtils sutis=new SharedPreferencesUtils(HRApplication.getAppContext());
                    sutis.setStringValue(Constants.USERID,resumeBean.getResume_info().getTitle_info().get(0).getUser_id());
                }else{
                    ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) resumeBean.getError_code()));
                }
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }

    @Override
    public void getArrayInfo() {
        mRxManage.add(mModel.getArrayInfo().subscribe(new RxSubscriber<ArrayInfoBean>(mContext,false) {
            @Override
            protected void _onNext(ArrayInfoBean arrayInfoBean)  {
                SharedPreferencesUtils sUtils=new SharedPreferencesUtils(mContext);
                if(!sUtils.getStringValue(Constants.CITY_VER,"").equals(arrayInfoBean.getCity().getVer())){
                    getArrayData("client/file/array/city.php","city.txt");
                }
                if(!sUtils.getStringValue(Constants.JOB_VER,"").equals(arrayInfoBean.getJob().getVer())){
                    getArrayData("client/file/array/job.php","job.txt");
                }
                if(!sUtils.getStringValue(Constants.LINGYU_VER,"").equals(arrayInfoBean.getLingyu().getVer())){
                    getArrayData("client/file/array/lingyu.php","lingyu.txt");
                }
                sUtils.setStringValue(Constants.CITY_VER,arrayInfoBean.getCity().getVer());
                sUtils.setStringValue(Constants.JOB_VER,arrayInfoBean.getJob().getVer());
                sUtils.setStringValue(Constants.LINGYU_VER,arrayInfoBean.getLingyu().getVer());
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }

    @Override
    public void getArrayData(final String path, final String fileName) {
        mRxManage.add(mModel.getArrayData(path,fileName).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody)  {
                try {
                    String s= responseBody.string().toString();
                    SaveFile.updateCJ(mContext,fileName,s);
                } catch (IOException e) {
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
    public void getVersion(String version) {
        mRxManage.add(mModel.getVersion(version).subscribe(new RxSubscriber<VersionBean>(mContext,false) {
            @Override
            protected void _onNext(VersionBean versionBean) throws IOException {
                mView.getVersion(versionBean.getAndroid());
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
