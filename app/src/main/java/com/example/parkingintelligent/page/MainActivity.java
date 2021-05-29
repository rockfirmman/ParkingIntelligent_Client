package com.example.parkingintelligent.page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.parkingintelligent.R;
import com.example.parkingintelligent.data.BillModel;
import com.example.parkingintelligent.data.StaticMessage;
import com.example.parkingintelligent.fragment.BDMapView;
import com.example.parkingintelligent.fragment.BillFragment;
import com.example.parkingintelligent.fragment.PersonalFragment;
import com.example.parkingintelligent.util.FormDataUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;

public class MainActivity extends AppCompatActivity {
    private NotificationManager manager;
    private Notification notification;
    Timer timer = new Timer();

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BDMapView _bdMapView;
    private BottomBar _bottomBar;
    private BillFragment _billFragment;
    private PersonalFragment _personalFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 碎片管理
        fragmentManager =getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        setContentView(R.layout.activity_main);
        requestPermissions();

        // 获得控件
        _bdMapView = new BDMapView();
        _billFragment = new BillFragment();
        _personalFragment = new PersonalFragment();

        fragmentTransaction.add(R.id.mainContent, _bdMapView);
        fragmentTransaction.add(R.id.mainContent, _billFragment);
        fragmentTransaction.hide( _billFragment);
        fragmentTransaction.add(R.id.mainContent, _personalFragment);
        fragmentTransaction.hide(_personalFragment);

        fragmentTransaction.commit();
        _bottomBar = findViewById(R.id.bottomBar);

        _bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                // 切换
                fragmentTransaction= fragmentManager.beginTransaction();
                int temp =_bottomBar.getCurrentTabId();
                switch (temp)
                {
                    case R.id.tab_home:
                        fragmentTransaction.hide(_bdMapView);
                        break;

                    case R.id.tab_bill:
                        fragmentTransaction.hide(_billFragment);
                        break;

                    case R.id.tab_person:
                        fragmentTransaction.hide(_personalFragment);
                        break;
                }
                switch (tabId)
                {
                    case R.id.tab_home:
                        fragmentTransaction.show(_bdMapView);
                        break;

                    case R.id.tab_bill:
                        fragmentTransaction.show(_billFragment);
                        break;

                    case R.id.tab_person:
                        fragmentTransaction.show(_personalFragment);

                        break;
                }
                fragmentTransaction.commit();
            }
        });


        //创建通知管理
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("leo", "测试通知", NotificationManager.IMPORTANCE_HIGH);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        notification = new NotificationCompat.Builder(this, "leo")
                .setAutoCancel(true)
                .setContentTitle("收到账单信息")
                .setContentText("今天晚上吃什么")
                .setSmallIcon(R.mipmap.ic_launcher)
                //在build()方法之前还可以添加其他方法
                .build();

        //使用timer开始轮询
        timer.schedule(task, 2000, 2000);
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(MainActivity.this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Log.d("TAG", permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d("TAG", permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d("TAG", permission.name + " is denied.");
                        }
                    }
                });


    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
                    builder.addFormDataPart("payerId", String.valueOf(StaticMessage.id));
                    String url = StaticMessage.baseURL + "/bill/poll";
                    JSONObject response = null;
                    try {
                        response = FormDataUtil.post(url,builder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if("success".equals(response.getString("status"))){
                        JSONObject bill = JSON.parseObject(response.getString("data"));
                        StaticMessage.billModel = JSONObject.toJavaObject(bill,BillModel.class);
                        String message = "";
                        if(StaticMessage.billModel.state==1){
                            message += "新的账单开始，开始时间为：" + StaticMessage.billModel.startTime;
                        }else if(StaticMessage.billModel.state==2){
                            message += "您的账单结束，等待支付，开始时间为：" + StaticMessage.billModel.startTime;
                            message += "    结束时间为：" + StaticMessage.billModel.endTime;
                        }else{
                            message = "您的订单完成支付";
                        }
                        notification = new NotificationCompat.Builder(getApplicationContext(), "leo")
                                .setAutoCancel(true)
                                .setContentTitle("收到账单信息")
                                .setContentText(StaticMessage.billModel.toString())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                //在build()方法之前还可以添加其他方法
                                .build();
                        manager.notify(1,notification);
                    }
                }
            });
        }
    };
}