package com.hr.ui.ui.main.presenter;

import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.FindBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.constants.Constants;
import com.hr.ui.ui.main.contract.MainContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;
import com.hr.ui.utils.datautils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2017/12/11.
 */

public class MainPresenter extends MainContract.Presenter {
    public static final String TAG=MainPresenter.class.getSimpleName();

    @Override
    public void getNotice(String cid,String aid) {
        mRxManage.add(mModel.getNotice(cid,aid).subscribe(new RxSubscriber<FindBean>(mContext,false) {
            @Override
            protected void _onNext(FindBean findBean) throws IOException {
               if("0".equals(findBean.getError_code())){
                   mView.getNoticeSuccess(findBean.getList());
               }
            }

            @Override
            protected void _onError(String message) {

            }
        }));
    }
    @Override
    public void bindJpush(String registerId) {
        mRxManage.add(mModel.bindJpush(registerId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody)  {
                try {
                    String s= responseBody.string().toString();
                    Log.i("1099","JPUSH"+s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void _onError(String message) {
                //ToastUitl.showShort(message);
            }
        }));
    }

}
