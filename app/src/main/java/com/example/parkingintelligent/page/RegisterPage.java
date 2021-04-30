package com.example.parkingintelligent.page;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parkingintelligent.R;
import com.example.parkingintelligent.util.DelayUtil;
import com.example.parkingintelligent.util.JsonBodyUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.getString(R.string.url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RegisterAPI reg_api =retrofit.create(RegisterAPI.class);

        _username = findViewById(R.id.usernameText_reg);
        _password = findViewById(R.id.passwordText_reg);
        _passwordVerify = findViewById(R.id.passwordVerifyText_reg);
        _emailLine = findViewById(R.id.emailText_reg);
        _regButton = findViewById(R.id.regButton);
        _checkCode = findViewById(R.id.checkCodeText_reg);
        _checkButton = findViewById(R.id.emailCheckButton);

        // 内容检查

        //发送验证码

        _checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonBodyUtil registerJsonBody = new JsonBodyUtil();
                registerJsonBody.addProp("username",_username.getText().toString());
                registerJsonBody.addProp("password",_password.getText().toString());
                registerJsonBody.addProp("email",_emailLine.getText().toString());
                registerJsonBody.addProp("phone",_password.getText().toString());

                // Cheat
                final SweetAlertDialog checkCodeDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE);
                // 当下面验证码交互成功后，注释下两行
                checkCodeDialog.setTitleText("验证码发送成功");
                checkCodeDialog.show();

                // 下面需要验证
                // 发送验证码
//                Call<String> call =  reg_api.registerPost(registerJsonBody.getBody());
//                call.enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        // 成功
//                        checkCodeDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                        checkCodeDialog.setTitleText("验证码发送成功");
//                        checkCodeDialog.show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        //失败
//                        checkCodeDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
//                        checkCodeDialog.setTitleText("验证码失败");
//                        checkCodeDialog.show();
//
//                    }
//                });
                // 这里可以添加一个60秒之内不能重发
                // TODO
            }
        });

        // 注册按钮

        _regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonBodyUtil checkJsonBody = new JsonBodyUtil();
                checkJsonBody.addProp("code",_checkCode.getText().toString());
                checkJsonBody.addProp("email",_emailLine.getText().toString());

                // 暂时直接跳转
                final SweetAlertDialog regDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE);
                // 当下面数据对接完成时，注释掉下三行
                regDialog.setTitleText("注册成功");
                regDialog.show();
                DelayUtil.delayJump(RegisterPage.this, LoginPage.class,1000);


                // TODO
                // 实现数据对接

//                Call<String> call =  reg_api.registerVerify(checkJsonBody.getBody());
//                call.enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        //成功
//                        regDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                        regDialog.setTitleText("注册成功");
//                        regDialog.show();
//                        DelayUtil.delayJump(RegisterPage.this,LoginPage.class,1000);
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        // 失败
//                        regDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
//                        regDialog.setTitleText("注册失败");
//                        regDialog.show();
//
//                    }
//                });
            }
        });
    }

    interface RegisterAPI {

        // 注册 请求
        //{
        // "password":"123456",
        // "username":"email_test_1",
        // "phone":"18888888888",
        // "email":"1424602142@qq.com"
        // }
        @POST("/user/register")
        Call<String> registerPost(
                @Body RequestBody route
        );
        @POST("/user/registerVerify")
        Call<String> registerVerify(
                @Body RequestBody route
        );
    }
}
