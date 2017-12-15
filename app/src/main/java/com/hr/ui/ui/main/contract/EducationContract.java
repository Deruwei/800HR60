package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseActivity;
import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.EducationData;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/13.
 */

public interface EducationContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> sendEducationToResume(EducationData educationData);
    }
    interface View extends BaseView{
        void sendEducationSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void sendEducationToResume(EducationData educationData);
    }
}
