package com.hr.ui.base.handleexception;

import android.util.Log;

import com.hr.ui.base.exception.FactoryException;

import rx.Observable;
import rx.functions.Func1;

/**
 * 异常的处理
 * Created by wdr on 2017/12/4.
 */

public class ExceptionFunc implements Func1<Throwable, Observable> {
    public static final String TAG=ExceptionFunc.class.getSimpleName();
    @Override
    public Observable call(Throwable throwable) {
        Log.e(TAG,"--------------"+throwable.getMessage());
        return Observable.error(FactoryException.analysisException(throwable));
    }
}
