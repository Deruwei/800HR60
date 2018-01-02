package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by wdr on 2018/1/2.
 */

public interface ResumeIntroductionContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> getIntroduction();
        Observable<ResponseBody> addOrReplaceIntroduction(String content);
    }
    interface View extends BaseView{
        void getIntroductionSuccess();
        void addOrReplaceIntroductionSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getIntroduction();
        public abstract void addOrReplaceIntroduction(String content);
    }
}

