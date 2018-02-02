package com.hr.ui.utils;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hr.ui.app.HRApplication;

/**
 * Created by wdr on 2018/1/24.
 */

public class BaiDuLocationUtils {
    public static BaiDuLocationUtils instance;
    private LocationClientOption option;
    private LocationClient client;
    private MyLocationListener listener;
    public static BaiDuLocationUtils getInstance(){
        if(instance==null){
            instance=new BaiDuLocationUtils();
        }

        return instance;
    }
    public void initData(){
        client=new LocationClient(HRApplication.getAppContext());
        option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        listener=new MyLocationListener();
        client.registerLocationListener(listener);
        client.setLocOption(option);
        client.start();
    }
    public void reStart(){
        if(client.isStarted()){
            client.restart();
        }
    }
    public void stopLocation(){
        if(client.isStarted()){
            client.unRegisterLocationListener(listener);
            client.stop();
        }
    }
}
