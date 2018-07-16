package com.hr.ui.utils;

import android.view.View;

import java.util.Calendar;

/**
 * Created by wdr on 2018/1/11.
 */

public class ClickUtils {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static final int MIN_CLICK_DELAY_TIME2 = 5000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) <= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
    public static boolean isFastClick2() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) <= MIN_CLICK_DELAY_TIME2) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
