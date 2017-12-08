package com.hr.ui.base.handleexception;

import com.hr.ui.R;
import com.hr.ui.base.exception.HttpTimeException;

import rx.functions.Func1;

/**
 * 服务器返回的数据判断
 * Created by wdr on 2017/11/30.
 */

public class ResultFunc implements Func1<Object,Object> {
    @Override
    public Object call(Object o) {
        if(o==null||"".equals(o.toString())){
          /*  throw new HttpTimeException()*/
          throw new HttpTimeException(R.string.error_dataError);
        }
        return o;
    }
}
