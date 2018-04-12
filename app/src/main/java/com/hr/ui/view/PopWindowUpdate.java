package com.hr.ui.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.ui.BuildConfig;
import com.hr.ui.R;
import com.hr.ui.bean.VersionBean;
import com.hr.ui.ui.main.activity.SplashActivity;
import com.hr.ui.utils.ToastUitl;
import com.service.DownloadSignatureServic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wdr on 2018/1/31.
 */

public class PopWindowUpdate {
    private int program;
    protected static final String fileRootPath = Environment.getExternalStorageDirectory() + File.separator;
    protected static final String fileDownloadPath = "sunrise/download/";
    public static final String TAG=PopWindowUpdate.class.getSimpleName();
    protected int fileSzie;//文件大小
    protected int fileCache;//文件缓存
    protected String fileName = "";//文件名
    protected String fileNametemp = "";//临时文件
    private String versionName;
    protected String urlStr = "";//下载url
    protected File downloaddir, downloadfile, downloadfiletemp;
    private Activity activity;
    private  TextView tvUpdateNow;
    private PopupWindow popupWindow;
    private VersionBean.AndroidBean androidBean;
    private  BeerProgressView pb_update;
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
        tvUpdateNow=view.findViewById(R.id.tv_updateNow);
        pb_update=view.findViewById(R.id.pb_update);
        ImageView ivUpdateCancle=view.findViewById(R.id.iv_updateCancel);
        ivUpdateCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tvUpdateContent.setText(androidBean.getText());
        tvUpdateContent.setMovementMethod(ScrollingMovementMethod.getInstance());
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
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.style_pop_animation2);
        popupWindow.showAtLocation(viewMain, Gravity.CENTER, 0, 0);
    }
    private void downLoadApp(){
        String s=androidBean.getUrl()+"android/800hr.apk";
        fileName = s.substring(s.lastIndexOf("/") + 1);
        fileName=fileName.substring(0,fileName.indexOf("."))+ androidBean.getVer().replace(".","_")+fileName.substring(fileName.indexOf("."));
        /*缓存文件*/
        fileNametemp = "download.tmp";
        /*下载目录*/
        downloaddir = new File(fileRootPath + fileDownloadPath);
        downloadfile = new File(fileRootPath + fileDownloadPath + fileName);
        downloadfiletemp = new File(fileRootPath + fileDownloadPath + fileNametemp);
        DownloadFile(s);
     /*   Bundle bundle = new Bundle();
        bundle.putString("signatureurl", androidBean.getUrl()+"android/800hr.apk");*//*电子签名下载地址*//*
        bundle.putString("versionName",androidBean.getVer());
       *//* bundle.putString("version",);*//*
        Intent it = new Intent().setClass(activity, DownloadSignatureServic.class).putExtras(bundle);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            activity.startForegroundService(it);
        } else {
            activity.startService(it);
        }*/
    }
    /**
     * install Apk
     *
     * @param context
     * @param filePath
     */
    public void installApp(Context context, String filePath) {
        File file = new File(filePath);
        SplashActivity.instance.isAllreadyInstance=false;
        if(popupWindow!=null) {
            popupWindow.dismiss();
        }
        //Log.i("文件的路径",filePath+"");
        if (file.exists()) {
            if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
                Uri apkUri = FileProvider.getUriForFile(context, "com.hr.ui.fileProvider", file);//在AndroidManifest中的android:authorities值
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                context.startActivity(install);
            } else {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            }
        }else{
            ToastUitl.showShort("文件不存在");
        }
    }
    /**
     * Download Signature
     *
     * @param downloadUrl
     */
    @SuppressLint("StaticFieldLeak")
    protected void DownloadFile(String downloadUrl) {
        Log.e(TAG, "DownloadFile");
        /*文件名*/

        if (!downloaddir.exists()) {
            downloaddir.mkdirs();
        }
        /*如何文件存在 这安装文件*/
        if (downloadfile.exists()) {
            installApp(activity, fileRootPath + fileDownloadPath + fileName);

        }
        /*否则下载文件*/
        else {
          /*  ToastUitl.showShort("行业找工作后台下载中");*/
            program=0;
            //downloadfile
            new AsyncTask<String, Integer, String>() {
                @Override
                protected void onPreExecute() {
                    pb_update.setBeerProgress(0);
                    super.onPreExecute();
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    if(values[0]==100){
                        tvUpdateNow.setText("下载完毕");
                    }else {
                        tvUpdateNow.setText("下载中" + values[0] + "%");
                    }
                    pb_update.setBeerProgress(values[0]);
                    super.onProgressUpdate(values);
                }

                @Override
                protected String doInBackground(String... params) {
                   try {
                        //fileName = params[0].substring(params[0].lastIndexOf("/") + 1);
                        //Log.e("LLL", "---fileName = " + fileName);
                        //获取文件名
                        URL myURL = new URL(params[0]);
                        URLConnection conn = myURL.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        fileSzie = conn.getContentLength();//根据响应获取文件大小
                        if (fileSzie <= 0) {
                            throw new RuntimeException("无法获知文件大小 ");
                        }
                        if (is == null) throw new RuntimeException("stream is null");
                        //*下载目录*//*
                        if (!downloaddir.exists()) {
                            downloaddir.mkdirs();
                        }
                        //把数据存入 路径+文件名
                        FileOutputStream fos = new FileOutputStream(downloadfiletemp);
                        byte buf[] = new byte[1024];
                        fileCache = 0;
                        do {
                            //循环读取
                            int numread = is.read(buf);
                            if (numread == -1) {
                                fos.close();
                                break;
                            }
                            fos.write(buf, 0, numread);
                            fileCache += numread;
                            //this.publishProgress(fileCache);
                            publishProgress((int) ((fileCache / (float) fileSzie) * 100));
                        } while (true);

                        try {
                            is.close();
                        } catch (Exception ex) {
                            Log.e("tag", "error: " + ex.getMessage());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "下载成功";
                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    /*下载成功后*/
                    if (downloadfiletemp.exists()) {
                        downloadfiletemp.renameTo(downloadfile);
                    }
                    //Log.i("文件的路径",downloadfiletemp+"-------"+downloadfile);
                    Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();

                    installApp(activity, fileRootPath + fileDownloadPath + fileName);
                    /*取消通知*/
                   /* pb_update.setBeerProgress(100);

                    /*service kill 自杀*/
                }
            }.execute(downloadUrl);
        }


    }

}
