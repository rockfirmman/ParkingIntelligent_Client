package com.example.parkingintelligent.data;

import android.net.Uri;

import com.baidu.mapapi.model.LatLng;

import java.util.List;

public class ParkUploadData {
    public float latitude;
    public float longitude;
    public String pname;             // 名字
    public String description;      // 简介
    public String picture_url;      // 图片url
    public ParkSlotData[] slotList; // 停车位数组
    // uri是暂时的，之后传url
    public Uri uri;
    public ParkUploadData()
    {
        latitude =0;
        longitude =0;
        pname = "";
        description = "";
        picture_url = "";
        uri= null;
        slotList = new ParkSlotData[100];
    }

    public void Clear()
    {
        latitude =0;
        longitude =0;
        pname = "";
        description = "";
        picture_url = "";
        uri= null;
        slotList = new ParkSlotData[100];
    }
    public void setLag(LatLng lag)
    {
        latitude = (float) lag.latitude;
        longitude = (float) lag.longitude;
    }

    public void setPName(String name)
    {
        this.pname = name;
    }

    public void setDescription(String des)
    {
        this.description = des;
    }

    public void setPicture_url(String url)
    {
        this.picture_url = url;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
