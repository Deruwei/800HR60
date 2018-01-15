package com.hr.ui.ui.me.contract;

import com.hr.ui.base.Base2Activity;
import com.hr.ui.base.BaseModel;
import com.hr.ui.base.BasePresenter;
import com.hr.ui.base.BaseView;
import com.hr.ui.bean.CollectionBean;
import com.hr.ui.bean.CompanyBean;


import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by wdr on 2018/1/12.
 */

public interface CollectionContract {
    interface Model extends BaseModel{
        Observable<CollectionBean> getCollectionInfo(int page);
        Observable<ResponseBody> deleteCollection(String collectionId,String jobId);
        Observable<ResponseBody> deliverCollection(String jobId);
    }
    interface View extends BaseView{
        void getCollectionSuccess(List<CollectionBean.FavouriteListBean> favouriteListBeanList);
        void deleteCollectionSuccess();
        void deliverCollection();
    }
    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getCollectionInfo(int page);
        public abstract void deleteCollection(String collectionId,String jobId);
        public abstract void deliverCollection(String jobId);
    }
}
