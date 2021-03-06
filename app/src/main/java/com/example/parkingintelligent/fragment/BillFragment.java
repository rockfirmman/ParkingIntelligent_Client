package com.example.parkingintelligent.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.parkingintelligent.R;
import com.example.parkingintelligent.adapter.BillAdapter;
import com.example.parkingintelligent.data.BillModel;
import com.example.parkingintelligent.data.StaticMessage;
import com.example.parkingintelligent.page.BillInformation;
import com.example.parkingintelligent.page.LoginPage;
import com.example.parkingintelligent.page.MainActivity;
import com.example.parkingintelligent.page.ParkUploadPage;
import com.example.parkingintelligent.page.RegisterPage;
import com.example.parkingintelligent.util.FormDataUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;


public class BillFragment extends Fragment implements AdapterView.OnItemClickListener {
    protected View _view;
    protected ViewGroup _container;
    protected ImageButton _refreshButton;
    protected TextView _PaidPageText;
    protected ListView _listView;

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
        _listView = _view.findViewById(R.id.lv_main);

        // ??????????????????????????????
        JSONObject response = selectBillById();
        JSONArray jsonArray = response.getJSONArray("data");
        //JSONArray???????????????javabean
        StaticMessage.billModelList = new ArrayList<>();
        StaticMessage.billModelList = JSONObject.parseArray(String.valueOf(jsonArray),BillModel.class);

        // ArrayAdapter??????
//        ArrayAdapter<BillModel> adapter
//                = new ArrayAdapter<BillModel>(
//                this.getActivity(),
//                android.R.layout.simple_list_item_1,
//                StaticMessage.billModelList);
//        _listView.setAdapter(adapter);

        // SimpleAdapter??????
//        List<Map<String,Object>> list = createMap();
//        SimpleAdapter adapter = new SimpleAdapter(
//                this.getActivity(),
//                list,
//                R.layout.item,
//                new String[]{"text1","text2","text3","logo","btn_text"},
//                new int[]{R.id.text1,R.id.text2,R.id.text3,R.id.logo,R.id.btn}
//        );
//        _listView.setAdapter(adapter);

        List<Map<String,Object>> list = createMap();
        BillAdapter billAdapter = new BillAdapter(this.getActivity());
        billAdapter.setList(list);
        _listView.setAdapter(billAdapter);

        // ??????item??????
        _listView.setOnItemClickListener(this);

        // ???????????????????????????listView
        _refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            JSONObject response = selectBillById();
            JSONArray jsonArray = response.getJSONArray("data");
            //JSONArray???????????????javabean
            StaticMessage.billModelList = new ArrayList<>();
            StaticMessage.billModelList = JSONObject.parseArray(String.valueOf(jsonArray),BillModel.class);
            //???????????????????????????display???listView
            List<Map<String,Object>> list = createMap();
            BillAdapter billAdapter = new BillAdapter(v.getContext());
            billAdapter.setList(list);
            _listView.setAdapter(billAdapter);


            }
        });

        return _view;
    }

    private JSONObject selectBillById(){
        final SweetAlertDialog pDialog = new SweetAlertDialog(_view.getContext(), SweetAlertDialog.PROGRESS_TYPE);
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("payerId",String.valueOf(StaticMessage.id));
        // ??????post??????
        String url = StaticMessage.baseURL + "/bill/selectBillByPayerId";
        JSONObject response = null;
        try {
            response = FormDataUtil.post(url,builder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ????????????success or fail
        if(response.getString("status")==null){
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText("??????????????????").show();
        }else if(response.getString("status").equals("success")){
            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            pDialog.setTitleText("????????????").show();
        }else {
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            pDialog.setTitleText(response.getString("message")).show();
        }
        return response;
    }

    private List<Map<String,Object>> createMap(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i < StaticMessage.billModelList.size() ; i++) {
            map = new HashMap<>();
            BillModel billModel = StaticMessage.billModelList.get(i);
            map.put("text1","??????: "+billModel.cost);
            map.put("text2",billModel.startTime);
            map.put("text3",billModel.endTime);
            if(billModel.state==1){
                map.put("logo",R.drawable.state_3);
                map.put("btn_text","?????????");
                map.put("btn_color","#fff2cc");
            }
            if(billModel.state==2){
                map.put("logo",R.drawable.state_3);
                map.put("btn_text","?????????");
                map.put("btn_color","#cfe2f3");
            }
            if(billModel.state==3){
                map.put("logo",R.drawable.state_3);
                map.put("btn_text","?????????");
                map.put("btn_color","#b6d7a8");
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // ??????????????????
        Toast.makeText(this.getActivity(),"??????"+position,Toast.LENGTH_SHORT).show();
        // ?????????????????????????????????
        StaticMessage.billModel = StaticMessage.billModelList.get(position);
        // Activity????????????
        Intent intent =new Intent(_view.getContext(), BillInformation.class);
        startActivity(intent);
    }
}
