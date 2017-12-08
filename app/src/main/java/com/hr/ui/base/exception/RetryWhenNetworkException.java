package com.hr.ui.base.exception;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by wdr on 2017/11/30.
 */

public class RetryWhenNetworkException implements Func1<Observable<? extends Throwable>,Observable<?>> {
   private int count=1;//retry的次数

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return null;
    }
}
