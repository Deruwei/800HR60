package com.hr.ui.base;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/2.
 */

public interface BaseResumeModel<T> {
    Observable<ResponseBody> deleteById();
    Observable<ResponseBody> getDataById();
    Observable<T> addOrReplaceData();
}
