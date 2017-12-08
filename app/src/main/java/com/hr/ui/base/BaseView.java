package com.hr.ui.base;

/**
 * des:baseview
 * Created by wdr on 2017/11/20.
 */
public interface BaseView {
    /*******内嵌加载*******/
    void showLoading(String title);
    void stopLoading();
    void showErrorTip(String msg);
}
