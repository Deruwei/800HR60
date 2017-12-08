package com.hr.ui.base.exception;

import com.hr.ui.api.Api;

import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by wdr on 2017/11/30.
 */

public class FactoryException {
    public static final String HttpException_Msg="网络错误";
    public static final String ConnectException_Msg="连接错误";
    public static final String JSONException_Msg="数据解析错误";
    public static final String UnknownHostException_Msg="无法解析当前域名";
    public static ApiException analysisException(Throwable e){
        ApiException apiException=new ApiException(e);
        if(e instanceof HttpException){
            apiException.setCode(CodeException.HTTP_ERROR);
        }else if(e instanceof HttpTimeException){
            HttpTimeException exception= (HttpTimeException) e;
            apiException.setCode(CodeException.RUNTIMR_ERROR);
            apiException.setDisplayMessage(exception.getMessage());
        }else if(e instanceof ConnectException||e instanceof SocketTimeoutException){
            apiException.setCode(CodeException.JSON_ERROR);
            apiException.setDisplayMessage(JSONException_Msg);
        }else if(e instanceof UnknownHostException){
            apiException.setCode(CodeException.UNKNOWHOST_ERROR);
            apiException.setDisplayMessage(UnknownHostException_Msg);
        }else{
            apiException.setCode(CodeException.UNKNOWHOST_ERROR);
            apiException.setDisplayMessage(e.getMessage());
        }
        return apiException;
    }
}
