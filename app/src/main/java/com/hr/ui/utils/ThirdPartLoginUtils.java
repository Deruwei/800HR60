package com.hr.ui.utils;

import android.util.Log;

import com.afa.tourism.greendao.gen.ThirdPartBeanDao;
import com.hr.ui.app.HRApplication;
import com.hr.ui.bean.ThirdPartBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.db.DBThirdPartService;
import com.hr.ui.ui.login.presenter.LoginPresenter;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by wdr on 2017/12/7.
 */

public class ThirdPartLoginUtils {
    public static final String TAG=ThirdPartLoginUtils.class.getSimpleName();
    public static void  LoginQQ(final LoginPresenter mPresenter){
        final DBThirdPartService dbThirdPartService=new DBThirdPartService();
        //final ThirdPartBeanDao thirdPartBeanDao= HRApplication.getDaoSession(HRApplication.getAppContext()).getThirdPartBeanDao();
        final ThirdPartBean thirdPartBean=new ThirdPartBean();
        Platform qq= ShareSDK.getPlatform(QQ.NAME);
        if(qq.isAuthValid()){
            qq.removeAccount(true);
        }
        qq.SSOSetting(false);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                System.out.println("wechat-complete"+hashMap.toString());
                thirdPartBean.setType("QQ");
                thirdPartBean.setName(hashMap.get("nickname") + "");
                thirdPartBean.setUId(platform.getDb().getUserId() + "");
                thirdPartBean.setBirthDay(hashMap.get("birthday") + "");
                thirdPartBean.setPhoto(hashMap.get("figureurl_qq_2") + "");
                thirdPartBean.setGender(hashMap.get("gender") + "");
                dbThirdPartService.saveData(thirdPartBean);
                Constants.TYPE_THIRDPARTLOGIN="QQ";
                mPresenter.getThirdPartLogin(thirdPartBean);
               // ToastUitl.showLong(hashMap.toString());
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i(TAG,throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.i(TAG,"取消");
            }
        });
        qq.showUser(null);
    }
    public static void  LoginWeChat(final LoginPresenter mPresenter){
        final ThirdPartBean thirdPartBean=new ThirdPartBean();
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//        if (wechat.isAuthValid()) {
        wechat.removeAccount(true);
//        }
        if (wechat.isClientValid()) {
            wechat.removeAccount(true);
        } else {
            ToastUitl.showShort( "您需要先安装微信客户端");
        }
        wechat.SSOSetting(false);
        wechat.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                System.out.println("wechat-complete"+arg2.toString());
                String nameString = arg2.get("name") + "";
                String nicknameString = arg2.get("nickname") + "";
                String uidString = arg2.get("unionid") + "";
                String birthdayString = arg2.get("birthday") + "";
                String tinyurlString = arg2.get("avatar_hd") + "";
                String genderString = arg2.get("gender") + "";
                thirdPartBean.setType("weixin");
                thirdPartBean.setName(nameString);
                thirdPartBean.setNikeName(nicknameString);
                thirdPartBean.setUId(uidString);
                thirdPartBean.setBirthDay(birthdayString);
                thirdPartBean.setPhoto(tinyurlString);
                thirdPartBean.setGender(genderString);
                Constants.TYPE_THIRDPARTLOGIN="weixin";
                mPresenter.getThirdPartLogin(thirdPartBean);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        wechat.showUser(null);
    }
}
