package com.example.parkingintelligent.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.parkingintelligent.R;
import com.example.parkingintelligent.data.BillModel;
import com.example.parkingintelligent.data.ParkingFieldModel;
import com.example.parkingintelligent.data.ParkingSlotModel;
import com.example.parkingintelligent.data.StaticMessage;
import com.example.parkingintelligent.util.FormDataUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

public class BDMapView extends Fragment {


    private LatLng latLng;
    private boolean isFirstLoc = true;//是否是首次定
    protected MapView _bdMapView;
    protected View _view;
    protected ViewGroup _container;
    protected BaiduMap _bdMap;
    protected LocationClient _locationClient;
    protected BDLocationListener myListener = new MyLocationListener();

    protected OverlayOptions targetOverlayOption;


    @Override
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        _view = inflater.inflate(R.layout.map_view, container, false);
        _container = container;
        _bdMapView = _view.findViewById(R.id.baiduMapView);
        _bdMap  = _bdMapView.getMap();
        initMap();
        draw();
        _bdMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ParkingFieldModel parkingFieldModel = StaticMessage.map.get(marker);
                Toast.makeText(getActivity(),"点击"+parkingFieldModel.getName(),Toast.LENGTH_SHORT).show();
                // 获取控件
                View view = View.inflate(getActivity(), R.layout.parking_field_information, null);
                TextView _name = view.findViewById(R.id.name);
                TextView _description = view.findViewById(R.id.description);
                TextView _remain = view.findViewById(R.id.remain);
                TextView _price = view.findViewById(R.id.price);
                Button _quit = view.findViewById(R.id.quit);

                // 获取当前停车场中停车位
                List<ParkingSlotModel> parkingSlotModelList = selectParkingSlotByFieldId(parkingFieldModel.getId());
                // modify message
                _name.setText(parkingFieldModel.getName());
                _description.setText("描述: " + parkingFieldModel.getDescription());
                _remain.setText("空闲车位: " + countSlot(parkingSlotModelList));
                _price.setText("平均价格: " + countPrice(parkingSlotModelList));

                _quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //关闭InfoWindow
                        _bdMap.hideInfoWindow();
                    }
                });
                final InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
                _bdMap.showInfoWindow(mInfoWindow);
                return false;
            }
        });
        return _view;
    }
    @Override
    public void onPause() {
        _bdMapView.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        _locationClient.stop();
        _bdMap.setMyLocationEnabled(false);
        _bdMapView.onDestroy();
        _bdMapView = null;
        super.onDestroy();
    }
    @Override
    public void onResume() {
        _bdMapView.onResume();
        super.onResume();
    }

    // 添加地点事件



    // 传入参数

    // 初始化地图
    private void initMap() {
        _bdMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//默认显示普通地图
        _bdMap.setMyLocationEnabled(true);// 开启定位图层
        _locationClient = new LocationClient(_view.getContext());     //声明LocationClient类
        initLocation();//配置定位SDK参数
        _locationClient.registerLocationListener(myListener);    //注册监听函数
        _locationClient.start();//开启定位
        _locationClient.requestLocation();//图片点击事件，回到定位点
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//可选，coorType - 取值有3个： 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
        Log.e("获取地址信息设置", option.getAddrType());//获取地址信息设置
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true); // 是否打开gps进行定位
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setScanSpan(1000);//可选，设置的扫描间隔，单位是毫秒，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        Log.e("获取设置的Prod字段值", option.getProdName());
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setNeedDeviceDirect(true);//在网络定位时，是否需要设备方向- true:需要 ; false:不需要。默认为false
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        _locationClient.setLocOption(option);
    }


    // 地图定位监听器
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            _bdMap.setMyLocationData(locData);// 设置定位数据
//            mBaiduMap.setMyLocationEnabled(false);// 当不需要定位图层时关闭定位图层
            if (isFirstLoc) {
                isFirstLoc = false;
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(18.0f);
                _bdMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    Toast.makeText(_view.getContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    Toast.makeText(_view.getContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    Toast.makeText(_view.getContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeServerError) {//服务器错误
                    Toast.makeText(_view.getContext(), "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {//网络错误
                    Toast.makeText(_view.getContext(), "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {//手机模式错误
                    Toast.makeText(_view.getContext(), "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void draw(){
        //获取停车场信息
        // Get
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("payerId","test");
        String url = StaticMessage.baseURL + "/parkingfield/findAllFields";
        JSONObject response = null;
        try {
            response = FormDataUtil.post(url,builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = response.getJSONArray("data");
        StaticMessage.parkingFieldModels = new ArrayList<>();
        StaticMessage.parkingFieldModels = JSONObject.parseArray(String.valueOf(jsonArray), ParkingFieldModel.class);
        // 添加地图Marker
//        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.ic_location);
//        for (ParkingFieldModel p:StaticMessage.parkingFieldModels){
//            LatLng point = new LatLng(p.getLatitude(),p.getLongitude());
//            targetOverlayOption = new MarkerOptions()
//                    .position(point)
//                    .icon(bitmap);
//            //在地图上添加Marker，并显示
//            _bdMap.addOverlay(targetOverlayOption);
//        }

        for (ParkingFieldModel p:StaticMessage.parkingFieldModels){
            LatLng point = new LatLng(p.getLatitude(),p.getLongitude());
            BitmapDescriptor bd_temp;
            View v_temp = LayoutInflater.from(this.getActivity()).inflate(R.layout.text_up_img_down, null);//加载自定义的布局
            TextView tv_temp = (TextView) v_temp.findViewById(R.id.baidumap_custom_text);//获取自定义布局中的textview
            ImageView img_temp = (ImageView) v_temp.findViewById(R.id.baidumap_custom_img);//获取自定义布局中的imageview
            tv_temp.setText(p.getName());//设置要显示的文本
//        img_temp.setImageResource(imgIds[i]);//设置marker的图标
            bd_temp = BitmapDescriptorFactory.fromView(v_temp);//用到了这个实例化方法来把自定义布局实现到marker中。
            MarkerOptions oo = new MarkerOptions().position(point)
                    .icon(bd_temp)
                    .anchor(0.5f, 1.0f).zIndex(7);
            //在地图上添加Marker，并显示
            Marker marker = (Marker) _bdMap.addOverlay(oo);
            StaticMessage.map.put(marker,p);
        }
    }

    public List<ParkingSlotModel> selectParkingSlotByFieldId(int fieldId){
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("fieldId", String.valueOf(fieldId));
        String url = StaticMessage.baseURL + "/parkingslot/selectParkingSlotByFieldId";
        JSONObject response = null;
        try {
            response = FormDataUtil.post(url,builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = response.getJSONArray("data");
        List <ParkingSlotModel> result = JSONObject.parseArray(String.valueOf(jsonArray),ParkingSlotModel.class);
        return result;
    }

    public int countSlot(List<ParkingSlotModel> parkingSlotModelList){
        int res = 0;
        for(ParkingSlotModel slot:parkingSlotModelList){
            if(!slot.isOccupied()) res++;
        }
        return res;
    }

    public double countPrice(List<ParkingSlotModel> parkingSlotModelList){
        int res = 0;
        for(ParkingSlotModel slot:parkingSlotModelList){
            res += slot.getPrice();
        }
        return res/parkingSlotModelList.size();
    }
}
