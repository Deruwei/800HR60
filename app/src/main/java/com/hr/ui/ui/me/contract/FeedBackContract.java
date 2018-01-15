package com.hr.ui.ui.me.contract;


import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/13.
 */

public interface FeedBackContract {
    interface View extends BaseView{
        void feedBackSuccess();
    }
    interface Model extends BaseModel{
        Observable<ResponseBody> feedBack(String content, String email);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract  void feedBack(String content,String email);
    }
}
