package com.example.parkingintelligent.data;

import com.baidu.mapapi.map.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaticMessage {
    public final static String baseURL = "http://116.63.40.220:8333";
    public static List<BillModel> billModelList = new ArrayList<>();
    public static BillModel billModel = null;
    public static int id;
    public static String username;
    public static String phone;
    public static String email;
    public static String account;

    public static List<ParkingFieldModel> parkingFieldModels;
    public static HashMap<Marker,ParkingFieldModel> map = new HashMap<>();
}
