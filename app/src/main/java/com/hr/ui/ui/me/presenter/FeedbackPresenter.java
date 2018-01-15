package com.hr.ui.ui.me.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.ui.me.contract.FeedBackContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/13.
 */

public class FeedbackPresenter extends FeedBackContract.Presenter {
    @Override
    public void feedBack(String content, String email) {
        mRxManage.add(mModel.feedBack(content,email).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
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
                        mView.feedBackSuccess();
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
