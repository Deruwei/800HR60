package com.hr.ui.ui.resume.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.ProfessionSkillData;
import com.hr.ui.bean.ResumeProfessionSkillBean;
import com.hr.ui.ui.resume.contract.ResumeProfessionSkillContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeProfessionSkillPresenter extends ResumeProfessionSkillContract.Presenter {
    @Override
    public void getSkill(String skillId) {
        mRxManage.add(mModel.getSkill(skillId).subscribe(new RxSubscriber<ResumeProfessionSkillBean>(mContext,false) {
            @Override
            protected void _onNext(ResumeProfessionSkillBean professionSkillBean) throws IOException {

                    if(professionSkillBean.getError_code()==0) {
                        mView.getSkillSuccess(professionSkillBean.getSkill_list().get(0));
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) professionSkillBean.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void deleteSkill(String skillId) {
        mRxManage.add(mModel.deleteSkill(skillId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.deleteSkillSuccess();
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
    public void addOrReplaceSkill(ProfessionSkillData skillData) {
        mRxManage.add(mModel.addOrReplaceSkill(skillData).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        System.out.print(s);
                        mView.addOrReplaceSkillSuccess();
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
