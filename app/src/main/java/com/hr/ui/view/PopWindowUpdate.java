package com.hr.ui.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hr.ui.R;

/**
 * Created by wdr on 2018/1/31.
 */

public class PopWindowUpdate {
    private Activity activity;
    private PopupWindow popupWindow;
    private String content;
    private View viewMain;
    public PopWindowUpdate(Activity activity,PopupWindow popupWindow, String content, View view){
        this.activity=activity;
        this.popupWindow=popupWindow;
        this.content=content;
        this.viewMain=view;
        initView();
    }

    private void initView() {
        View view = activity.getLayoutInflater().inflate(R.layout.layout_update, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvUpdateContent=view.findViewById(R.id.tv_updateContent);
        TextView tvUpdateNow=view.findViewById(R.id.tv_updateNow);
        tvUpdateContent.setText(content);
        tvUpdateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
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
        popupWindow.setAnimationStyle(R.style.style_pop_animation2);
        popupWindow.showAtLocation(viewMain, Gravity.CENTER, 0, 0);
    }
}
