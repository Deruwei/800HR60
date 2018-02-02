package com.hr.ui.ui.me.presenter;

import com.hr.ui.base.RxSubscriber;
import com.hr.ui.bean.QueryShieldCompanyBean;
import com.hr.ui.bean.ShieldCompanyBean;
import com.hr.ui.ui.me.contract.ShieldCompanyContract;
import com.hr.ui.utils.Rc4Md5Utils;
import com.hr.ui.utils.ToastUitl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by wdr on 2018/1/17.
 */

public class ShieldCompanyPresenter extends ShieldCompanyContract.Presenter {
    @Override
    public void getShieldCompanyData() {
        mRxManage.add(mModel.getShieldCompanyData().subscribe(new RxSubscriber<ShieldCompanyBean>(mContext,true) {
            @Override
            protected void _onNext(ShieldCompanyBean shieldCompanyBean) throws IOException {
                    if(shieldCompanyBean.getError_code()==0) {
                        mView.getShieldCompanyDataSuccess(shieldCompanyBean.getEliminate_list());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(shieldCompanyBean.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }

    @Override
    public void queryShieldCompanyDataByKeyword(String content) {
        mRxManage.add(mModel.queryShieldCompanyDataByKeyword(content).subscribe(new RxSubscriber<QueryShieldCompanyBean>(mContext,true) {
            @Override
            protected void _onNext(QueryShieldCompanyBean queryShieldCompanyBean) throws IOException {
                    if(queryShieldCompanyBean.getError_code()==0) {
                        mView.queryShieldCompanyDataByKeyWordSuccess(queryShieldCompanyBean.getEnte_list(),queryShieldCompanyBean.getTotals());
                    }else{
                        ToastUitl.showShort(Rc4Md5Utils.getErrorResourceId(queryShieldCompanyBean.getError_code()));
                    }
            }

            @Override
            protected void _onError(String message) {
                ToastUitl.showShort(message);
            }
        }));
    }

    @Override
    public void setShiledCompany(String companyName) {
        mRxManage.add(mModel.setShiledCompany(companyName).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        mView.setShieldCompanySuccess();
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
                ToastUitl.showShort(message);
            }
        }));
    }

    @Override
    public void deleteShieldCompany(String shieldId) {
        mRxManage.add(mModel.deleteShieldCompany(shieldId).subscribe(new RxSubscriber<ResponseBody>(mContext,false) {
            @Override
            protected void _onNext(ResponseBody responseBody) throws IOException {
                String s= null;
                try {
                    s = responseBody.string().toString();
                    JSONObject jsonObject=new JSONObject(s);
                    double error_code=jsonObject.getDouble("error_code");
                    if(error_code==0) {
                        mView.deleteShieldCompany();
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
                ToastUitl.showShort(message);
            }
        }));
    }
}
