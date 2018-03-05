package com.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.hr.ui.BuildConfig;
import com.hr.ui.utils.ToastUitl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wdr on 2018/2/7.
 */

public class DownloadSignatureServic extends Service {
    protected static final String fileRootPath = Environment.getExternalStorageDirectory() + File.separator;
    protected static final String fileDownloadPath = "sunrise/download/";
    protected int fileSzie;//文件大小
    protected int fileCache;//文件缓存
    protected String fileName = "";//文件名
    protected String fileNametemp = "";//临时文件
    private String versionName;
    protected String urlStr = "";//下载url
    protected File downloaddir, downloadfile, downloadfiletemp;
    protected static NotificationManager mNotifyManager;
    protected static NotificationCompat.Builder mBuilder;
    protected static final int notifiID = 0x000;
    protected static final String TAG = DownloadSignatureServic.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1,new Notification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        if(intent!=null&&intent.getExtras()!=null) {
            urlStr = (String) intent.getExtras().get("signatureurl");
            Log.e(TAG, "urlStr = " + urlStr);
            versionName = (String) intent.getExtras().get("versionName");
        /*Signature Download Url*/
            DownloadFile(urlStr);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    /**
     * Download Signature
     *
     * @param downloadUrl
     */
    protected void DownloadFile(String downloadUrl) {
        Log.e(TAG, "DownloadFile");
        /*文件名*/
        fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        fileName=fileName.substring(0,fileName.indexOf("."))+BuildConfig.VERSION_NAME.replace(".","_")+fileName.substring(fileName.indexOf("."));
        /*缓存文件*/
        fileNametemp = "download.tmp";
        /*下载目录*/
        downloaddir = new File(fileRootPath + fileDownloadPath);
        downloadfile = new File(fileRootPath + fileDownloadPath + fileName);
        downloadfiletemp = new File(fileRootPath + fileDownloadPath + fileNametemp);

        if (!downloaddir.exists()) {
            downloaddir.mkdirs();
        }
        /*如何文件存在 这安装文件*/
        if (downloadfile.exists()) {
                installApp(DownloadSignatureServic.this, fileRootPath + fileDownloadPath + fileName);
        }
        /*否则下载文件*/
        else {
            ToastUitl.showShort("行业找工作后台下载中");
            mNotifyManager =
                    (NotificationManager) DownloadSignatureServic.this.getSystemService(DownloadSignatureServic.this.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(DownloadSignatureServic.this);
            mBuilder.setContentTitle("下载行业找工作App")
                    .setContentText("正在下载···")
                    .setProgress(100, 0, false)
                    .setSmallIcon(android.R.drawable.stat_sys_download);
            //downloadfile
            new AsyncTask<String, Integer, String>() {
                @Override
                protected void onPreExecute() {
                    mBuilder.setTicker("下载App").setProgress(100, 0, false);
                    mNotifyManager.notify(notifiID, mBuilder.build());

                    super.onPreExecute();
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    Log.e(TAG, "---下载缓存" + values[0] + "---");
                    int pp = ((values[0] + 1) * 100 / fileSzie);
                    mBuilder.setProgress(100, pp, false).setContentText("已下载" + pp + "%");
                    mNotifyManager.notify(notifiID, mBuilder.build());
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
                        /*下载目录*/
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
                            this.publishProgress(fileCache);

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
                    Log.i("文件的路径",downloadfiletemp+"-------"+downloadfile);
                    Toast.makeText(DownloadSignatureServic.this, s, Toast.LENGTH_SHORT).show();
                    installApp(DownloadSignatureServic.this, fileRootPath + fileDownloadPath + fileName);
                    /*取消通知*/
                    mBuilder.setContentText(s).setProgress(100, 0, false);
                    mNotifyManager.cancel(notifiID);
                    /*service kill 自杀*/
                    DownloadSignatureServic.this.stopSelf();
                }
            }.execute(downloadUrl);

        }


    }

    /**
     * install Apk
     *
     * @param context
     * @param filePath
     */
    public void installApp(Context context, String filePath) {
        File file = new File(filePath);
        Log.i("文件的路径",filePath+"");
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
}