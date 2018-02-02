package com.hr.ui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hr.ui.R;


/**
 * description:弹窗浮动加载进度条
 * Created by wdr on 2017/11/20.
 */
public class LoadingDialog {
    /** 加载数据对话框 */
    private static Dialog mLoadingDialog;
    /**
     * 显示加载对话框
     * @param context 上下文
     * @param msg 对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public static Dialog showDialogForLoading(Context context, String msg, boolean cancelable) {
        //Log.i("进去","here");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
       // Log.i("进去2","here");
        TextView loadingText = (TextView)view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText(msg);
        //Log.i("进去2","here");
        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);

        //Log.i("进去3","here");
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return  mLoadingDialog;
    }

    public static Dialog showDialogForLoading(Activity context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView)view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText("加载中...");

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return  mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public static void cancelDialogForLoading() {
        if(mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }
}
