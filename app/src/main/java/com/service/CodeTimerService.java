package com.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hr.ui.utils.CodeTimer;

/**
 * 计时服务器
 * Created by wdr on 2017/12/6.
 */

public class CodeTimerService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1,new Notification());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //启动计时器
        String action = intent.getAction();
        CodeTimer mCodeTimer = new CodeTimer(this, 60000, 100, action);
        mCodeTimer.start();
    }
}