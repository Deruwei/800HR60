package com.hr.ui.ui.me.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.CollectionBean;
import com.hr.ui.ui.me.contract.CollectionContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/12.
 */

public class CollectionPresenter extends CollectionContract.Presenter {
    @Override
    public void getCollectionInfo(int page) {
        mRxManage.add(mModel.getCollectionInfo(page).subscribe(new RxSubscriber<CollectionBean>(mContext,false) {
            @Override
            protected void _onNext(CollectionBean collectionBean) throws IOException {
                    if("0".equals(collectionBean.getError_code())) {
                        // System.out.print(s);
                       /* mView.setHideSuccess();*/
                       // mView.collectionPositionSuccess();
                        mView.getCollectionSuccess(collectionBean.getFavourite_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(Integer.parseInt(collectionBean.getError_code())));
                    }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void deleteCollection(String collectionId, String jobId) {
        mRxManage.add(mModel.deleteCollection(collectionId,jobId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        // System.out.print(s);
                       /* mView.setHideSuccess();*/
                        //mView.collectionPositionSuccess();
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }

    @Override
    public void deliverCollection(String jobId) {
        mRxManage.add(mModel.deliverCollection(jobId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        // System.out.print(s);
                       /* mView.setHideSuccess();*/
                       // mView.collectionPositionSuccess();
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId((int) error_code));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
}
