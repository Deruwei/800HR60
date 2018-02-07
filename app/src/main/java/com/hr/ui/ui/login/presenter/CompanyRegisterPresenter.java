package com.hr.ui.ui.login.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.CompanyRegisterBean;
import com.hr.ui.ui.login.contract.CompanyRegisterContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/2/6.
 */

public class CompanyRegisterPresenter extends CompanyRegisterContract.Presenter {
    @Override
    public void registerCompany(CompanyRegisterBean companyRegisterBean) {
        mRxManage.add(mModel.registerCompany(companyRegisterBean).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody)  {
                try {
                    String s=responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    int errorCode=jsonObject.getInt("error_code");
                    if(errorCode==0) {
                       mView.registerCompanySuccess();
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
