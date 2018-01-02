package com.hr.ui.ui.resume.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.LanguageLevelData;
import com.hr.ui.bean.ResumeLanguageBean;
import com.hr.ui.ui.resume.contract.ResumeLanguageContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeLanguagePresenter extends ResumeLanguageContract.Presenter {
    @Override
    public void getLanguage(String languageId) {
        mRxManage.add(mModel.getLanguage(languageId).subscribe(new RxSubscriber<ResumeLanguageBean>(mContext,false) {
            @Override
            protected void _onNext(ResumeLanguageBean resumeLanguageBean) throws IOException {
                    if(resumeLanguageBean.getError_code()==0) {

                       // System.out.print(s);
                        mView.getLanguageSuccess(resumeLanguageBean.getLanguage_list().get(0));
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) resumeLanguageBean.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void deleteLanguage(String languageId) {
        mRxManage.add(mModel.deleteLanguage(languageId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.deleteLanguageSuccess();
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

            }
        }));
    }

    @Override
    public void addOrReplace(LanguageLevelData languageLevelData) {
        mRxManage.add(mModel.addOrReplace(languageLevelData).subscribe(new RxSubscriber<ResponseBody>(mContext,true) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.addOrReplaceLanguageSuccess();
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

            }
        }));
    }
}
