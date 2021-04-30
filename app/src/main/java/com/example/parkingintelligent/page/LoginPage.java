package com.example.parkingintelligent.page;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingintelligent.R;
import com.example.parkingintelligent.util.FormDataUtil;
import com.example.parkingintelligent.util.SPSaveUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;
import com.alibaba.fastjson.JSONObject;

public class LoginPage extends AppCompatActivity {

    private EditText _username;
    private EditText _password;
    private Button _loginButton;
    private TextView _registerButton;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        _username = findViewById(R.id.usernameText);
        _password = findViewById(R.id.passwordText);
        _loginButton = findViewById(R.id.LoginButton);
        _registerButton = findViewById(R.id.registerbutton);
        requestPermissions();
        Map<String, String> userInfo = SPSaveUtil.getUserInfo(this);
        if (userInfo != null){
            _username.setText(userInfo.get("username"));
            _password.setText(userInfo.get("password"));
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
                // 加载中
                final SweetAlertDialog pDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                // 加载完成后
                MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("username",_username.getText().toString());
                builder.addFormDataPart("password",_password.getText().toString());

                // 发送post请求
                String url = "http://116.63.40.220:8333/user/login";
                JSONObject response = null;
                try {
                    response = FormDataUtil.post(url,builder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 判断请求success or fail
                if(response.getString("status").equals("success")){
                    // 直接跳转
                    pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("登陆成功").show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 跳转到主页
                            startActivity(new Intent(LoginPage.this,MainActivity.class));
                        }
                    }, 1000);
                } else {
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText(response.getString("message")).show();
                }

                //保存用户信息
                preferences.edit()
                        .putString("username", _username.getText().toString())
                        .putString("password", _password.getText().toString())
                        .apply();
            }
        });
        _registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,RegisterPage.class));
            }
        });
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(LoginPage.this);
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
}