package com.hr.ui.view;

import android.view.MotionEvent;

/**
 * Created by wdr on 2017/12/20.
 */

public class MotionEventType {

    public static String getEnventTypeMsg(MotionEvent event) {
        String actionMsg = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionMsg = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                actionMsg = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_CANCEL:
                actionMsg = "ACTION_CANCEL";
                break;
            case MotionEvent.ACTION_UP:
                actionMsg = "ACTION_UP";
                break;
            default:
                actionMsg = "";
                break;

        }
        return actionMsg;
    }
}
