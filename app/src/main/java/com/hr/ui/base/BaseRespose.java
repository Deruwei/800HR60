package com.hr.ui.base;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by wdr on 2017/11/20.
 */
public class BaseRespose <T> implements Serializable {
    public String error_code;
    public String msg;

    public T data;

    public boolean success() {
        return "0".equals(error_code);
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "error_code='" + error_code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
