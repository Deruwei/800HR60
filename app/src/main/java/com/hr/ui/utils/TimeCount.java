package com.hr.ui.utils;

import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;

/**
 * Created by wdr on 2017/12/5.
 */

public class TimeCount extends CountDownTimer {
    private TextView tv;
    public TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
        super(millisInFuture, countDownInterval);
        this.tv=tv;
    }

    //计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        tv.setTextSize(14);
        tv.setText(millisUntilFinished / 1000 + "秒后重新发送");
        tv.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.white));
        tv.setClickable(false);
        tv.setBackgroundDrawable(ContextCompat.getDrawable(HRApplication.getAppContext(),R.drawable.validcode_bg_gray));
    }
    //计时完成触发
    @Override
    public void onFinish() {
        tv.setText("获取验证码");
        tv.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.new_main));
        tv.setBackgroundDrawable(ContextCompat.getDrawable(HRApplication.getAppContext(),R.drawable.validcode_bg));
        tv.setClickable(true);
    }
}