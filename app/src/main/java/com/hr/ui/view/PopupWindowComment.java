package com.hr.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.utils.ToastUitl;

import org.w3c.dom.Text;

/**
 * Created by wdr on 2018/1/31.
 */

public class PopupWindowComment {
    private PopupWindow popupWindow;
    private Activity activity;
    private View viewMain;
    public PopupWindowComment(PopupWindow popupWindow, Activity activity,View view){
        this.popupWindow=popupWindow;
        this.activity=activity;
        this.viewMain=view;
        init();
    }
    private void init(){
            View view = activity.getLayoutInflater().inflate(R.layout.layout_ads, null);
            popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvGiveComment=view.findViewById(R.id.tv_commentYes);
        TextView tvNoComment=view.findViewById(R.id.tv_commentNo);
        tvGiveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doElaluateMine();
                popupWindow.dismiss();
            }
        });
        tvNoComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                activity.finish();
            }
        });
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
                }
            });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.style_pop_animation2);
        popupWindow.showAtLocation(viewMain, Gravity.CENTER, 0, 0);
    }
    private void doElaluateMine() {
        try{
            Uri uri = Uri.parse("market://details?id="+activity.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }catch(Exception e){
            ToastUitl.showShort("您的手机没有安装Android应用市场");
            e.printStackTrace();
        }

    }
}
