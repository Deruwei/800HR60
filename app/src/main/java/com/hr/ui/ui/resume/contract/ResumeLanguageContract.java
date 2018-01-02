package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.LanguageLevelData;
import com.hr.ui.bean.ResumeLanguageBean;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/2.
 */

public interface ResumeLanguageContract {
    interface Model extends BaseModel{
        Observable<ResumeLanguageBean> getLanguage(String languageId);
        Observable<ResponseBody> deleteLanguage(String languageId);
        Observable<ResponseBody> addOrReplace(LanguageLevelData languageLevelData);
    }
    interface View extends BaseView{
        void getLanguageSuccess(ResumeLanguageBean.LanguageListBean languageBean);
        void deleteLanguageSuccess();
        void addOrReplaceLanguageSuccess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getLanguage(String languageId);
        public abstract void deleteLanguage(String languageId);
        public abstract void addOrReplace(LanguageLevelData languageLevelData);
    }
}
