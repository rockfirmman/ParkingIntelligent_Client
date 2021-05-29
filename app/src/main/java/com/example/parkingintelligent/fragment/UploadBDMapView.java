package com.example.parkingintelligent.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.parkingintelligent.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UploadBDMapView extends BDMapView {
    protected boolean AddAble=false;
    protected OverlayOptions targetOverlayOption;
    protected LatLng _selectPoint;  // 选中的点
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);

        if (getArguments() != null) {
            AddAble = getArguments().getBoolean("addAble");
            if(AddAble)
            {
                _bdMap.setOnMapClickListener(AddPositionListener());
            }

            // test for location
            LatLng latLng = new LatLng(22,114);

        }
        return _view;
    }


    // 获得经纬度
    public LatLng get_selectPoint() {
        return _selectPoint;
    }

    public static UploadBDMapView newInstance(boolean addAble)
    {
        UploadBDMapView fragment = new UploadBDMapView();
        Bundle args = new Bundle();
        args.putBoolean("addAble",addAble);
        fragment.setArguments(args);
        return fragment;
    }

    // 添加地点标记Listener
    private BaiduMap.OnMapClickListener AddPositionListener()
    {
        BaiduMap.OnMapClickListener BDlistener = new BaiduMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng point)
            {
//                Toast.makeText(_view.getContext(), point.toString(), Toast.LENGTH_SHORT).show();

                // 添加地图Marker
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_location);

                //如果存在marker 就将他clear
                if(targetOverlayOption!=null){
                    _bdMap.clear();
                    targetOverlayOption =null;
                }
                targetOverlayOption = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                _selectPoint = point;
                //在地图上添加Marker，并显示
                _bdMap.addOverlay(targetOverlayOption);

                // 弹出确认信息

                new SweetAlertDialog(_view.getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("停车场信息")
                        .setContentText("你的停车场地点为: 经度 "+point.latitude+", 纬度 "+point.longitude)
                        .setCancelText("取消")
                        .setConfirmText("确认！")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                                // 清除所有marker
                                if(targetOverlayOption!=null){
                                    _bdMap.clear();
                                    targetOverlayOption =null;
                                    _selectPoint=null;
                                }
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // TODO
                                // 保存信息到缓存


                                sDialog.cancel();
                            }
                        })
                        .show();
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {

            }
        };
        return BDlistener;
    }
}
