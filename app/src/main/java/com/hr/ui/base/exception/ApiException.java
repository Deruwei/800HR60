package com.hr.ui.base.exception;

import com.hr.ui.api.Api;

/**
 * 回调统一的请求异常
 * Created by wdr on 2017/11/30.
 */

public class ApiException extends Exception {
    public int code;
    private String displayMessage;
    public ApiException(Throwable throwable){
        super(throwable);
    }
    public ApiException(Throwable throwable, @CodeException.CodeEp int code,String msg){
        super(msg,throwable);
        setCode(code);
        setDisplayMessage(msg);
    }
    @CodeException.CodeEp
    public int getCode() {
        return code;
    }

    public void setCode(@CodeException.CodeEp int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
