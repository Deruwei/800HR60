package com.hr.ui.api;

import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.NoDataBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.constants.Constants;

import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * des:ApiService
 * Created by wdr on 2017/11/20.
 */
public interface ApiService {
    //获取连接服务器
    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<BaseBean> getConnect(@FieldMap HashMap<String, String> map);
    //获取验证码
    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<ValidCodeBean> getValidCode(@FieldMap HashMap<String,String> map);

    //获取图片验证码
    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<AutoCodeBean> getAutoCode(@FieldMap HashMap<String,String> map);

    //注册
    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<RegisterBean> getRegister(@FieldMap HashMap<String,String> map);

    //手机/用户登录
    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<RegisterBean> getLogin(@FieldMap HashMap<String,String> map);

    //重置密码
    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<NoDataBean> resetPhonePsw(@FieldMap HashMap<String,String> map);

    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<ResponseBody> getThirdPartLogin(@FieldMap HashMap<String,String> map);
}
