package com.hr.ui.ui.message.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.WhoSeeMeBean;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;


/**
 * Created by wdr on 2018/1/15.
 */

public interface WhoSeeMeContract {
    interface Model extends BaseModel{
        Observable<WhoSeeMeBean> getWhoSeeMeData(int page);
    }
    interface View extends BaseView{
        void getWhoSeeMeSuccess(List<WhoSeeMeBean.BrowsedListBean> browsedListBeans);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getWhoSeeMeData(int page);
    }
}
