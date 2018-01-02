package com.hr.ui.base;

/**
 * Created by wdr on 2018/1/2.
 */

public class ResumeBaseBean<T> {
    private T Bean;

    public T getBean() {
        return Bean;
    }

    public void setBean(T bean) {
        Bean = bean;
    }
}
