package com.example.parkingintelligent.page;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.anton46.stepsview.StepsView;
import com.baidu.mapapi.map.BaiduMap;
import com.example.parkingintelligent.R;
import com.example.parkingintelligent.data.ParkUploadData;
import com.example.parkingintelligent.fragment.BDMapView;
import com.example.parkingintelligent.fragment.ImageUpload;
import com.example.parkingintelligent.fragment.ParkExtraInfo;
import com.example.parkingintelligent.fragment.ParkFieldConfirm;
import com.example.parkingintelligent.fragment.StepBar;
import com.example.parkingintelligent.fragment.UploadBDMapView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ParkUploadPage extends AppCompatActivity {

    private final String[] labels = {"位置信息", "上传平面图", "其他信息", "确认"};

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    // 控件
    private TitleBar titleBar;
    private StepBar stepbar;
    private UploadBDMapView bdMapView;
    private ImageUpload imageUpload;
    private ParkExtraInfo parkExtraInfo;
    private ParkFieldConfirm parkFieldConfirm;
    private ParkUploadData parkUploadData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager =getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        setContentView(R.layout.park_upload_1);

        // 初始化数据
        parkUploadData = new ParkUploadData();


        // 初始化所有fragment
        stepbar = StepBar.newInstance(0,labels, R.color.material_blue_grey_800, R.color.orange);
        bdMapView = UploadBDMapView.newInstance(true);
        imageUpload = new ImageUpload();
        parkExtraInfo = new ParkExtraInfo();
        parkFieldConfirm = new ParkFieldConfirm();
        // 步骤栏
        fragmentTransaction.add(R.id.parkStep, stepbar);
        // 界面1
        fragmentTransaction.add(R.id.ParkStepContent,bdMapView);
        // 界面2
        fragmentTransaction.add(R.id.ParkStepContent,imageUpload);
        fragmentTransaction.hide(imageUpload);
        // 界面3
        fragmentTransaction.add(R.id.ParkStepContent,parkExtraInfo);
        fragmentTransaction.hide(parkExtraInfo);

        // 界面4
        fragmentTransaction.add(R.id.ParkStepContent,parkFieldConfirm);
        fragmentTransaction.hide(parkFieldConfirm);
        fragmentTransaction.commit();

        titleBar= findViewById(R.id.parkUploadTitleBar);

        // 顶部栏操作
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(View v) {
                if(stepbar.isStart())
                {

                    // 确认
                    new SweetAlertDialog(ParkUploadPage.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("退出")
                            .setContentText("未保存停车位信息，你确定要回到主页吗？")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    Intent intent =new Intent(ParkUploadPage.this, MainActivity.class);
                                    //启动
                                    startActivity(intent);
                                }
                            })
                            .show();


                    return;
                }else
                {
                    stepbar.Previous();
                    titleBar.setRightTitle("下一步");
                    if(stepbar.isStart())
                    {
                        titleBar.setLeftTitle("  主  页");
                    }else{
                        titleBar.setLeftTitle("上一步");
                    }
                    fragmentTransaction = fragmentManager.beginTransaction();
                    switch (stepbar._position)
                    {
                        case 0:
                            // 第1页面
                            fragmentTransaction.hide(imageUpload);
                            fragmentTransaction.show(bdMapView);

                            break;
                        case 1:
                            //第2页面
                            fragmentTransaction.hide(parkExtraInfo);
                            fragmentTransaction.show(imageUpload);
                            break;
                        case 2:
                            // 第3页面
                            fragmentTransaction.hide(parkFieldConfirm);
                            fragmentTransaction.show(parkExtraInfo);
                            break;
                    }
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
                if(stepbar.isEnd())
                {
                    // TODO
                    // 上传信息

                    // 弹出提交成功信息

                    new SweetAlertDialog(ParkUploadPage.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("上传成功！")
                            .show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 跳转回主页
                            Intent intent =new Intent(ParkUploadPage.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, 2000);

                    return;
                }
                else
                {
                    stepbar.Next();
                    titleBar.setLeftTitle("上一步");
                    if(stepbar.isEnd())
                    {
                        titleBar.setRightTitle("上  传  ");
                    }else
                    {
                        titleBar.setRightTitle("下一步");
                    }
                    fragmentTransaction = fragmentManager.beginTransaction();
                    switch (stepbar._position)
                    {
                        case 1:
                            // 第2页面
                            fragmentTransaction.hide(bdMapView);
                            fragmentTransaction.show(imageUpload);
                            // 保存经纬度信息
                            parkUploadData.setLag(bdMapView.get_selectPoint());
                            break;
                        case 2:
                            //第3页面
                            fragmentTransaction.hide(imageUpload);
                            fragmentTransaction.show(parkExtraInfo);
                            // TODO
                            // 保存图片url
                            parkUploadData.setUri(imageUpload.get_uri());

                            // 保存各个停车位信息

                            break;
                        case 3:
                            // 第4页面
                            fragmentTransaction.hide(parkExtraInfo);
                            fragmentTransaction.show(parkFieldConfirm);
                            // 保存名字、简介
                            parkUploadData.setPName(parkExtraInfo.getParkName());
                            parkUploadData.setDescription(parkExtraInfo.getParkDes());

                            // 设置确认信息
                            parkFieldConfirm.SetByUploadData(parkUploadData);
                            break;
                    }
                    fragmentTransaction.commit();
                }
            }
        });
    }
    public void ClearData()
    {
        this.parkUploadData.Clear();
    }

}
