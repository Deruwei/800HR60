package com.hr.ui.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import com.hr.ui.R;
import com.hr.ui.app.HRApplication;
import com.hr.ui.ui.message.activity.WebActivity;

/**
 * Created by wdr on 2018/1/18.
 */

public class AutolinkSpan extends URLSpan {
    private String url;
    public AutolinkSpan(String url) {
        super(url);
        this.url=url;
    }

    @Override
    public void onClick(View widget) {
        if (widget.getTag(R.id.long_click) != null) {
            widget.setTag(R.id.long_click, null);
            return;
        }
        WebActivity.startAction((Activity) widget.getContext(),url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.blue_color));
        ds.setUnderlineText(true);
    }
}

