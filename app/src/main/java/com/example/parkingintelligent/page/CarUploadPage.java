package com.example.parkingintelligent.page;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.parkingintelligent.R;
import com.example.parkingintelligent.data.StaticMessage;
import com.example.parkingintelligent.util.DelayUtil;
import com.example.parkingintelligent.util.FormDataUtil;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;

public class CarUploadPage extends AppCompatActivity {
    private TextView carUploadText;
    private EditText license;
    private EditText length;
    private EditText width;
    private EditText height;
    private Button carUploadButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_upload);
        carUploadText = findViewById(R.id.carUploadText);
        license = findViewById(R.id.licenseText_reg);
        length = findViewById(R.id.lengthText_reg);
        width = findViewById(R.id.widthText_reg);
        height = findViewById(R.id.heightText_reg);
        carUploadButton = findViewById(R.id.carUploadButton);

        carUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加载完成后，将输入信息添加入builder
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("license", license.getText().toString());
                builder.addFormDataPart("length", length.getText().toString());
                builder.addFormDataPart("width", width.getText().toString());
                builder.addFormDataPart("height", height.getText().toString());
                builder.addFormDataPart("color", "其他");
                builder.addFormDataPart("userId", String.valueOf(StaticMessage.id));

                // 发送post请求
                String url = StaticMessage.baseURL + "/car/registerCar";
                JSONObject response = null;
                try {
                    response = FormDataUtil.post(url, builder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 判断请求success or fail
                final SweetAlertDialog regDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE);
                if(response.getString("status")==null){
                    //无返回
                    regDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    regDialog.setTitleText("车辆注册失败");
                    regDialog.show();
                }else if(response.getString("status").equals("success")){
                    // 验证成功
                    regDialog.setTitleText("车辆注册成功");
                    regDialog.show();
                    // 跳转
                    DelayUtil.delayJump(CarUploadPage.this, MainActivity.class,1000);
                }else{
                    // 注册失败
                    regDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    regDialog.setTitleText("车辆注册失败");
                    regDialog.show();
                }
            }
        });

        carUploadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carUploadText.setText("test code");
            }
        });

    }
}
