package com.hr.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.bean.VersionBean;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.service.DownloadSignatureServic;

/**
 * Created by wdr on 2018/1/31.
 */

public class PopWindowUpdate {
    private Activity activity;
    private PopupWindow popupWindow;
    private VersionBean.AndroidBean androidBean;
    private View viewMain;
    public PopWindowUpdate(Activity activity,PopupWindow popupWindow, VersionBean.AndroidBean androidBean, View view){
        this.activity=activity;
        this.popupWindow=popupWindow;
        this.androidBean=androidBean;
        this.viewMain=view;
        initView();
    }

    private void initView() {
        View view = activity.getLayoutInflater().inflate(R.layout.layout_update, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tvUpdateContent=view.findViewById(R.id.tv_updateContent);
        TextView tvUpdateNow=view.findViewById(R.id.tv_updateNow);
        tvUpdateContent.setText(androidBean.getText());
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        activity.getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
                if(SplashActivity.instance.isAllreadyInstance==true) {
                    SplashActivity.instance.doAutoLogin();
                }
            }
        });
        tvUpdateNow.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    downLoadApp();
                    SplashActivity.instance.isAllreadyInstance=false;
                    popupWindow.dismiss();
                }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.style_pop_animation2);
        popupWindow.showAtLocation(viewMain, Gravity.CENTER, 0, 0);
    }
    private void downLoadApp(){
        Bundle bundle = new Bundle();
        bundle.putString("signatureurl", androidBean.getUrl()+"android/800hr.apk");/*电子签名下载地址*/
        bundle.putString("versionName",androidBean.getVer());
       /* bundle.putString("version",);*/
        Intent it = new Intent().setClass(activity, DownloadSignatureServic.class).putExtras(bundle);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            activity.startForegroundService(it);
        } else {
            activity.startService(it);
        }
    }
}
