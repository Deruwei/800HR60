package com.hr.ui.base.exception;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义错误类型，注解写法
 * Created by wdr on 2017/11/30.
 */

public class CodeException {
    public static final int NETWORK_ERROR=0x1;
    public static final int HTTP_ERROR=0x2;
    public static final int JSON_ERROR=0x3;
    public static final int UNKNOWN_ERROR=0x4;
    public static final int RUNTIMR_ERROR=0x5;
    public static final int UNKNOWHOST_ERROR=0x6;
    @IntDef({NETWORK_ERROR,HTTP_ERROR,JSON_ERROR,UNKNOWN_ERROR,RUNTIMR_ERROR,UNKNOWHOST_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CodeEp{

    }
}
