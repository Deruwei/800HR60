package com.hr.ui.ui.message.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.GuidanceBean;


import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/16.
 */

public interface EmploymentGuidanceContract {
    interface Model extends BaseModel{
        Observable<GuidanceBean> getEmploymentGuidanceData(int page, String guidanceId);
    }
    interface View extends BaseView{
        void getEmploymentGuidanceSuccess(List<GuidanceBean.TitleListBean> titleListBeanList);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getEmploymentGuidanceData(int page,String guidanceId,boolean isCanRefresh);
    }
}
