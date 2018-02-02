package com.hr.ui.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hr.ui.app.HRApplication;
import com.hr.ui.constants.Constants;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

/**
 * Created by wdr on 2018/1/30.
 */

public class AlamrReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        SharedPreferencesUtils sUtils=new SharedPreferencesUtils(HRApplication.getAppContext());
        sUtils.setBooleanValue(Constants.ISHAVENEWS,true);
    }

}
