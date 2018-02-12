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
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.message.activity.WebActivity;
import com.hr.ui.utils.Utils;
import com.hr.ui.utils.recyclerviewutils.AVLoadingIndicatorView;

/**
 * Created by wdr on 2018/1/31.
 */

public class PopupWindowAd {
    private PopupWindow popupWindow;
    private View viewMain;
    private String url;
    private Activity activity;
    private String imageUrl;
    public PopupWindowAd(Activity activity,PopupWindow popupWindow,View view){
        this.activity=activity;
        this.popupWindow=popupWindow;
        this.viewMain=view;
        initView();
    }
    public PopupWindowAd(Activity activity,PopupWindow popupWindow,View view,String url,String imageUrl){
        this.popupWindow=popupWindow;
        this.activity=activity;
        this.viewMain=view;
        this.url=url;
        this.imageUrl=imageUrl;
        initView();
    }
    private void initView() {
        View view=activity.getLayoutInflater().inflate(R.layout.layout_newads,null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView ivAds=view.findViewById(R.id.iv_adPhoto);
        ImageView ivClose=view.findViewById(R.id.iv_closeAd);
        if(imageUrl!=null) {
            Utils.setImageResource(activity,ivAds, imageUrl);
        }
        ivAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.startAction(activity,url);
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
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
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.style_pop_animation2);
        popupWindow.showAtLocation(viewMain, Gravity.CENTER, 0, 0);
    }

}
