package com.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.hr.ui.R;
import com.hr.ui.ui.main.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PushService;

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private int num=0;
    private  NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    /** Notification的ID */
    int notifyId = 100;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Notification notification=mBuilder.build();
                    mNotificationManager.notify(100054622,notification);
                    break;
            }
        }
    };
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if(mBuilder==null) {
                mBuilder = new NotificationCompat.Builder(context);
            }
            Bundle bundle = intent.getExtras();
          //  Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
               /* int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                receivingNotification(context,bundle,notifactionId);*/
                //processCustomMessage(context, bundle);
                receivingNotification(context,bundle);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

               /* //打开自定义的Activity
                Intent i = new Intent(context, MainDetailActivity.class);
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(i);*/

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e){

        }

    }

    private void receivingNotification(Context context, Bundle bundle) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationChannel channel = null;
        long time=System.currentTimeMillis()/10000;//获取系统时间的10位的时间戳
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1",
                    "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            mNotificationManager.createNotificationChannel(channel);
            Intent i =new Intent(context,MainActivity.class);
            PendingIntent pi = PendingIntent.getActivities(context, 0, new Intent[]{i}, PendingIntent.FLAG_CANCEL_CURRENT);
            mBuilder.setChannelId("1");
            // 点击跳转到播报界面
            mBuilder.setSmallIcon(R.drawable.jpush_ic_richpush_actionbar_back); // 设置顶部图标（状态栏）
            mBuilder.setContentText(bundle.getString(JPushInterface.EXTRA_MESSAGE));
            mBuilder.setContentTitle("行业找工作");
            mBuilder.setContentIntent(pi);
            mBuilder.setAutoCancel(true);
            mBuilder.setNumber(num++);
            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mBuilder.setOngoing(true);
            Notification notification=mBuilder.build();
            mNotificationManager.notify(Math.toIntExact(time),notification);
        }else{
            Intent i =new Intent(context,MainActivity.class);
            PendingIntent pi = PendingIntent.getActivities(context, 0, new Intent[]{i}, PendingIntent.FLAG_CANCEL_CURRENT);
            // 点击跳转到播报界面
            mBuilder.setSmallIcon(R.drawable.jpush_ic_richpush_actionbar_back); // 设置顶部图标（状态栏）
            mBuilder.setContentText(bundle.getString(JPushInterface.EXTRA_MESSAGE));
            mBuilder.setContentTitle("行业找工作");
            mBuilder.setContentIntent(pi);
            mBuilder.setNumber(num++);
            mBuilder.setAutoCancel(true);
            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mBuilder.setOngoing(true);
            Notification notification=mBuilder.build();
            mNotificationManager.notify((int) time,notification);
        }
    }
}
