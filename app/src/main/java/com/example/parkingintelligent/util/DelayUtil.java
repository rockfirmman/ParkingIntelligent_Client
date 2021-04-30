package com.example.parkingintelligent.util;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class DelayUtil {

    /// 工具类
    /// 延时跳转
    /// @param current 页面 如 “MainActivity.this”
    /// @param tar 目标页面类 如 “MainActivity.class”
    /// @param delayMillis 延时毫秒数
    public static void delayJump(final AppCompatActivity current, final Class<?> tar, long delayMillis){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 跳转到主页
                current.startActivity(new Intent(current,tar));
            }
        }, delayMillis);

    }
}
