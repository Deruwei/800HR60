package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.FindBean;


import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/2/1.
 */

public interface JobSearchContract {
    interface  Model extends BaseModel{
        Observable<FindBean> getHotSearchJob(int pageNum, String c_type);
    }
    interface View extends BaseView{
        void getHotSearchJobSuccess(List<FindBean.ListBean> listBeans);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getHotSearchJob(int pageNum,String c_type);
    }
}
