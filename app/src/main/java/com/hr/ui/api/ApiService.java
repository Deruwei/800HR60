package com.hr.ui.api;

import com.hr.ui.bean.AutoCodeBean;
import com.hr.ui.bean.BaseBean;
import com.hr.ui.bean.MultipleResumeBean;
import com.hr.ui.bean.NoDataBean;
import com.hr.ui.bean.RegisterBean;
import com.hr.ui.bean.ResumeBean;
import com.hr.ui.bean.ValidCodeBean;
import com.hr.ui.constants.Constants;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
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

    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<RegisterBean> getThirdBinding(@FieldMap HashMap<String,String> map);

    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<ResponseBody> getResponseString(@FieldMap HashMap<String,String> map);

    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<MultipleResumeBean> getResumeList(@FieldMap HashMap<String,String> map);

    @POST(Constants.API_MIDDLE_TEST)
    @FormUrlEncoded
    Observable<ResumeBean> getResume(@FieldMap HashMap<String,String> map);

    @GET("client/getarrayver.php")
    Observable<ResponseBody>  getArrayInfo();

    @GET("client/file/array/city.php")
    Observable<RequestBody> getCityArray();

    @GET("client/file/array/job.php")
    Observable<RequestBody> getJobArray();

    @GET
    Observable<ResponseBody> getLingYuArray(@Url String url);
}
