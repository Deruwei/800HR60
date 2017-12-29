package com.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hr.ui.utils.CodeTimer;
import com.hr.ui.view.CodeTimer2;

/**
 * Created by wdr on 2017/12/26.
 */

public class MyTimeService extends Service {
    CodeTimer2 mCodeTimer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        //启动计时器
        if(intent!=null) {
            String action = intent.getAction();
            mCodeTimer = new CodeTimer2(this, 1200000, 1180000, action);
            mCodeTimer.start();
        }
    }
}
