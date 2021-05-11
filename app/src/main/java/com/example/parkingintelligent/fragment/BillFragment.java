package com.example.parkingintelligent.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.parkingintelligent.R;
import com.example.parkingintelligent.data.BillModel;
import com.example.parkingintelligent.data.StaticMessage;
import com.example.parkingintelligent.page.LoginPage;
import com.example.parkingintelligent.page.MainActivity;
import com.example.parkingintelligent.page.RegisterPage;
import com.example.parkingintelligent.util.FormDataUtil;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;


public class BillFragment extends Fragment {
    protected View _view;
    protected ViewGroup _container;
    protected ImageButton _refreshButton;
    protected TextView _PaidPageText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        _view = inflater.inflate(R.layout.bill_page, container, false);
        _container = container;
        _refreshButton = _view.findViewById(R.id.refreshButton);
        _PaidPageText = _view.findViewById(R.id.PaidPageText);
        _PaidPageText.setText("Hello " + StaticMessage.username);

//        _PaidPageText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                _PaidPageText.setText("click " + StaticMessage.username);
//                JSONObject response = selectBillById(pDialog);
//                _PaidPageText.setText(response.getString("status"));
//            }
//        });
        _PaidPageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject response = selectBillById();
                JSONArray jsonArray = response.getJSONArray("data");
                _PaidPageText.setText(jsonArray.get(0).toString());
                int length = jsonArray.size();
                _PaidPageText.setText(jsonArray.get(length-1).toString());
//                List<BillModel>  billModelList = JSONArray.toList(jsonArray,BillModel.class);
//                _PaidPageText.setText(billModelList.get(0).toString());
            }
        });

        return _view;
    }

    private JSONObject selectBillById(){
        final SweetAlertDialog pDialog = new SweetAlertDialog(_view.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("payerId",String.valueOf(StaticMessage.id));
        // 发送post请求
        String url = "http://116.63.40.220:8333/bill/selectBillByPayerId";
        JSONObject response = null;
        try {
            response = FormDataUtil.post(url,builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 判断请求success or fail
        if(response.getString("status")==null){
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText("网络出现问题").show();
        }else if(response.getString("status").equals("success")){
            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            pDialog.setTitleText("登陆成功").show();
        }else {
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText(response.getString("message")).show();
        }
        return response;
    }
}
