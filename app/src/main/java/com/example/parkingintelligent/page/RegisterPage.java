package com.example.parkingintelligent.page;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.parkingintelligent.R;
import com.example.parkingintelligent.util.DelayUtil;
import com.example.parkingintelligent.util.FormDataUtil;
import com.example.parkingintelligent.util.JsonBodyUtil;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterPage extends AppCompatActivity {

    private EditText _username;
    private EditText _password;
    private EditText _passwordVerify;
    private EditText _emailLine;
    private EditText _checkCode;
    private Button _regButton;
    private Button _checkButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        _username = findViewById(R.id.usernameText_reg);
        _password = findViewById(R.id.passwordText_reg);
        _passwordVerify = findViewById(R.id.passwordVerifyText_reg);
        _emailLine = findViewById(R.id.emailText_reg);
        _regButton = findViewById(R.id.regButton);
        _checkCode = findViewById(R.id.checkCodeText_reg);
        _checkButton = findViewById(R.id.emailCheckButton);

        _checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加载完成后，将输入信息添加入builder
                MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("username",_username.getText().toString());
                builder.addFormDataPart("password",_password.getText().toString());
                builder.addFormDataPart("phone","");
                builder.addFormDataPart("email",_emailLine.getText().toString());
                builder.addFormDataPart("account","");

                // 发送post请求
                String url = "http://116.63.40.220:8333/user/register";
                JSONObject response = null;
                try {
                    response = FormDataUtil.post(url,builder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 成功弹窗
                final SweetAlertDialog checkCodeDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE);
                checkCodeDialog.setTitleText("验证码发送成功");
                checkCodeDialog.show();
            }
        });

        // 注册按钮

        _regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("code",_checkCode.getText().toString());
                builder.addFormDataPart("email",_emailLine.getText().toString());

                // 发送post请求
                String url = "http://116.63.40.220:8333/user/registerVerify";
                JSONObject response = null;
                try {
                    response = FormDataUtil.post(url,builder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 判断请求success or fail
                final SweetAlertDialog regDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE);
                if(response.getString("status").equals("success")){
                    // 验证成功
                    regDialog.setTitleText("注册成功");
                    regDialog.show();
                    // 跳转
                    DelayUtil.delayJump(RegisterPage.this, LoginPage.class,1000);
                    //保存用户信息至缓存
                    final SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
                    preferences.edit()
                            .putString("username", _username.getText().toString())
                            .putString("password", _password.getText().toString())
                            .apply();
                }else{
                    // 注册失败
                    regDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    regDialog.setTitleText("注册失败");
                    regDialog.show();
                }
            }
        });
    }
}
