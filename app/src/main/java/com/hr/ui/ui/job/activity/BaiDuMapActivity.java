package com.hr.ui.ui.job.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.hr.ui.R;
import com.hr.ui.api.Api;
import com.hr.ui.api.ApiService;
import com.hr.ui.api.HostType;
import com.hr.ui.app.HRApplication;
import com.hr.ui.base.BaseNoConnectNetworkAcitivty;
import com.hr.ui.utils.BaiDuLocationUtils;
import com.hr.ui.utils.datautils.SaveFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wdr on 2018/1/24.
 */

public class BaiDuMapActivity extends BaseNoConnectNetworkAcitivty implements BaiduMap.OnMapClickListener {
    @BindView(R.id.tv_toolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbarAdd)
    ImageView toolbarAdd;
    @BindView(R.id.tv_toolbarSave)
    TextView tvToolbarSave;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.map_location)
    MapView mapLocation;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    private BaiduMap baiduMap;
    private double longitude,latitude;
    private BitmapDescriptor mCurrentMarker;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    public static BaiDuMapActivity instance;
    private UiSettings uiSettings;
    private String companyAddress;
    private Polyline mPolyline;
    private RoutePlanSearch search;
    private BDLocation location,location1;
    private LocationClient mClient;
    private boolean isFirstLoc=true;
    private MyLocationListenner listenner;
    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity, String x,String y) {
        Intent intent = new Intent(activity, BaiDuMapActivity.class);
        intent.putExtra("x", x);
        intent.putExtra("y",y);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.zoom_in,
                R.anim.zoom_out);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_location;
    }

    @Override
    public void initView() {
        instance=this;
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        longitude=Double.parseDouble(getIntent().getStringExtra("x"));
        latitude=Double.parseDouble(getIntent().getStringExtra("y"));
        location1=new BDLocation();
        location1.setLongitude(longitude);
        location1.setLatitude(latitude);
        getCommanyData();
        toolBar.setTitle("");
        toolBar.setTitleTextColor(ContextCompat.getColor(HRApplication.getAppContext(), R.color.color_333));
        toolBar.setNavigationIcon(R.mipmap.back);
        tvToolbarTitle.setText(R.string.companyAddress);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initBaiduMap();

    }

    private void initBaiduMap() {
        baiduMap=mapLocation.getMap();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        uiSettings=baiduMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        listenner=new MyLocationListenner();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mClient=new LocationClient(this);
        mClient.registerLocationListener(listenner);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        mClient.setLocOption(option);
        mClient.start();
        //地图点击事件处理
        baiduMap.setOnMapClickListener(this);
        // 初始化搜索模块，注册事件监听
        search= RoutePlanSearch.newInstance();
    }

    private void initBaiDuMapStartAndEndPoint() {
        // 终点位置
        LatLng geoPoint1 = new LatLng(latitude, longitude);
        // 构建Marker图标
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                .fromResource(R.mipmap.location);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option1 = new MarkerOptions().position(geoPoint1)
                .icon(bitmap1).zIndex(8).draggable(true);
        List<OverlayOptions> overlay = new ArrayList<OverlayOptions>();
        overlay.add(option1);
        baiduMap.addOverlays(overlay);
        TextView tv = new TextView(this);
        tv.setPadding(5,10,5,10);
        tv.setBackgroundResource(R.drawable.edit_bg_small);
        tv.setTextColor(ContextCompat.getColor(HRApplication.getAppContext(),R.color.color_333));
        if(companyAddress!=null&&!"".equals(companyAddress)) {
            tv.setText(companyAddress);
        }
       InfoWindow mInfoWindow = new InfoWindow(tv,geoPoint1, -80);
//显示InfoWindow
        baiduMap.showInfoWindow(mInfoWindow);
        //定义地图
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(geoPoint1)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        instance=this;
    }

    @OnClick({R.id.tv_toolbarSave, R.id.iv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_toolbarSave:
                break;
            case R.id.iv_location:
                initLocation(location);
                break;
        }
    }
    public void initLocation(BDLocation bdLocation){
        // 开启定位图层
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();

// 设置定位数据
        baiduMap.setMyLocationData(locData);
        mCurrentMode= MyLocationConfiguration.LocationMode.COMPASS;
// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, null);
        baiduMap.setMyLocationConfiguration(config);
        float f = baiduMap.getMinZoomLevel();//19.0 最小比例尺
//设置地图显示比例尺
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(f+10);//大小按需求计算就可以
        baiduMap.animateMapStatus(u);
        LatLng geoPoint1 = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(geoPoint1)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.unRegisterLocationListener(listenner);
    }
    private void getCommanyData(){
        ApiService RxService = Api.getDefault(HostType.HR);
        Observable<ResponseBody> Object = RxService.getCompanyAddress("http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location="+latitude+","+longitude+"&output=json&pois=1&ak=jMfg95xhZFMHsDjfxVitMjyg&mcode=FE:53:F7:B1:21:8A:71:5B:DA:B7:F6:08:87:CE:B4:85:AB:CA:71:FC;com.hr.ui");
        Subscriber mSubscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.d("api", "onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                Log.d("api", "onError: " + e.toString());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String s= responseBody.string().toString();
                    s=s.substring(s.indexOf("(")+1,s.lastIndexOf(")"));
                    JSONObject jsonObject=new JSONObject(s);
                    int status=jsonObject.getInt("status");
                    if(status==0){
                        companyAddress=jsonObject.getJSONObject("result").getString("formatted_address");
                        initBaiDuMapStartAndEndPoint();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Object.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(mSubscriber);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location1) {
            location=location1;
            //取经纬度
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapLocation == null)
                return;
        }
    }


}
