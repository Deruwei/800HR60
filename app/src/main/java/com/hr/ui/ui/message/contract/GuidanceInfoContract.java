package com.hr.ui.ui.message.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.GuidanceInfoBean;


import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/16.
 */

public interface GuidanceInfoContract {
    interface Model extends BaseModel{
        Observable<GuidanceInfoBean> getGuidanceInfo(String guidanceId);
    }
    interface View extends BaseView{
        void getGuidanceInfoSuccess(List<GuidanceInfoBean.TitleContentListBean> titleContentListBeans);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getGuidanceInfo(String guidanceId);
    }
}
