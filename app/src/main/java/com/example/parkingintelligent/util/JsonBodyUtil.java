package com.example.parkingintelligent.util;

import com.example.parkingintelligent.response.UserData;
import com.google.gson.Gson;


import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JsonBodyUtil {
    private HashMap<String,String> jsonHashMap = new HashMap<String,String>();// 存储json数值对
    private Gson gson = new Gson();

    public void addProp(String key,String value)
    {
        jsonHashMap.put(key, value);
    }
    //回传RequestBody
    public RequestBody getBody()
    {
        String str = gson.toJson(jsonHashMap);
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),str);
    }

    public static void main(String[] args) throws IOException {
        MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("username","aaa");
        builder.addFormDataPart("password","111111");
        String url = "http://localhost:8333/user/login";
        RequestBody body= builder.build();
        final Request request = new Request.Builder().url(url).post(body).build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();
        String data = response.body().string();
        JSONObject jsonObject=JSONObject.parseObject(data);
        System.out.println(jsonObject.getString("status"));
        System.out.println(jsonObject.getString("message"));
        int id;
        id = JSONObject.parseObject(jsonObject.getString("data")).getInteger("id");
        System.out.println(id);
    }
}
