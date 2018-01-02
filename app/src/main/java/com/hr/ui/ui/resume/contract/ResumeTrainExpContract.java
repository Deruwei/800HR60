package com.hr.ui.ui.resume.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseResumeModel;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.ResumeTrainBean;
import com.hr.ui.bean.TrainExpData;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by wdr on 2018/1/2.
 */

public interface ResumeTrainExpContract {
    interface Model extends BaseModel{
        Observable<ResumeTrainBean> getTrainExpData(String trainId);
        Observable<ResponseBody> deleteExpData(String trainId);
        Observable<ResponseBody> AddOrReplaceData(TrainExpData trainExpData);
    }
    interface View extends BaseView{
        void getTrainSuccess(ResumeTrainBean.PlantListBean trainBean);
        void deleteTrainSuccess();
        void addOrReplaceSucess();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getTrainExpData(String trainId);
        public abstract void deleteExpDate(String trainId);
        public abstract void addOrReplaceData(TrainExpData trainExpData);
    }
}
