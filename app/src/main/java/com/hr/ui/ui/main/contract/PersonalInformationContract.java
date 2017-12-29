package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.PictureBean;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/13.
 */

public interface PersonalInformationContract {
    interface Model extends BaseModel{
        Observable<ResponseBody> sendPersonalInformationToResume(PersonalInformationData personalInformationData);
        Observable<PictureBean> upLoadImage(String content);
    }
    interface View extends BaseView{
        void sendInformationSuccess();
        void uploadImageSuccess(String path);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void sendPersonalInformationToResume(PersonalInformationData personalInformationData);
        public abstract void upLoadImage(String content);
    }
}
