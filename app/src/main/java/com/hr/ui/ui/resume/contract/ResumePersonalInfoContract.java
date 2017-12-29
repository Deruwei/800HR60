package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.PersonalInformationData;
import com.hr.ui.bean.PictureBean;
import com.hr.ui.bean.ResumePersonalInfoBean;


import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/25.
 */

public interface ResumePersonalInfoContract {
    interface Model extends BaseModel{
        Observable<ResumePersonalInfoBean> getPersonalInfo();
       Observable<ResponseBody> UpdatePersonalInfo(PersonalInformationData personalInformationData);
       Observable<PictureBean> upLoadImage(String content);
    }
    interface View extends BaseView{
        void getPersonalInfoSuccess(ResumePersonalInfoBean resumePersonalInfoBean);
        void updatePersonalInfoSuccess();
        void uploadImageSuccess(String path);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getPersonalInfo();
        public abstract void updatePersonalInfo(PersonalInformationData personalInformationData);
        public abstract void upLoadImage(String content);
    }
}
