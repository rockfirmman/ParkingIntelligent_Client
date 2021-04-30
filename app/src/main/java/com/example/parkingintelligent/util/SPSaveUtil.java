package com.example.parkingintelligent.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SPSaveUtil {
    // 保存账号和登录密码到data.xml文件中
    public static boolean saveUserInfo(Context context, String name, String password) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("username",name);
        edit.putString("password",password);
        edit.commit();
        return true;
    }

    //从data.xml文件中获取存储的帐号和密码
    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String name = sp.getString("username",null);
        String password = sp.getString("password", null);
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("username", name);
        userMap.put("password",password);
        return userMap;
    }
}
