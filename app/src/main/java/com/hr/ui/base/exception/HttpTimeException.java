package com.hr.ui.base.exception;

import java.util.DuplicateFormatFlagsException;

/**
 * Created by wdr on 2017/11/30.
 */

public class HttpTimeException extends RuntimeException {
    public static final int UNKNOWN_ERROR=0x1002;
    public static final int NO_CACHE_ERROR=0x1003;
    public static final int CACHE_TIMEOUT_ERROR=0x1004;
    public HttpTimeException(int resultCode){
        super(getApiExceptionMessage(resultCode));
    }
    public HttpTimeException(String detailMessage){
        super(detailMessage);
    }
    public static String getApiExceptionMessage(int code){
        switch (code){
            case UNKNOWN_ERROR:
                return "网络错误";
            case NO_CACHE_ERROR:
                return "缓存错误";
            case CACHE_TIMEOUT_ERROR:
                return "缓存失效";
            default:
                return "未知错误";
        }
    }
}
