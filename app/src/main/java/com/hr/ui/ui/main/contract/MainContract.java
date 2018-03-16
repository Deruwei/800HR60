package com.hr.ui.ui.main.contract;

import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ValidCodeBean;


import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2017/12/11.
 */

public interface MainContract {
    interface Model extends BaseModel{
        Observable<FindBean> getNotice(String cid,String aid);
    }
    interface View extends BaseView{
       void getNoticeSuccess(List<FindBean.ListBean> listBean);
    }
    abstract class Presenter extends BasePresenter<View,Model>{
       public abstract void getNotice(String cid,String aid);
    }
}
