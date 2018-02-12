package com.hr.ui.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.ui.message.activity.WebActivity;
import com.hr.ui.utils.Utils;

/**
 * Created by wdr on 2018/1/31.
 */

public class PopupWindowWarm {
    private String content;
    private PopupWindow popupWindow;
    private View viewMain;
    private Activity activity;
    public PopupWindowWarm(PopupWindow popupWindow,String content,View view,Activity activity){
        this.viewMain=view;
        this.content=content;
        this.activity=activity;
        this.popupWindow=popupWindow;
        initView();
    }

    private void initView() {
        View view=activity.getLayoutInflater().inflate(R.layout.layout_warn,null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvContent=view.findViewById(R.id.tv_warmContent);
        TextView tvKnow=view.findViewById(R.id.tv_warmKnow);
        tvKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               popupWindow.dismiss();
            }
        });
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        tvContent.setText(content);
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
}
