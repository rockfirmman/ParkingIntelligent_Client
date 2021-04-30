package com.example.parkingintelligent.util;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FormDataUtil {
    public static JSONObject post(String url, MultipartBody.Builder builder) throws IOException {
        RequestBody body= builder.build();
        final Request request = new Request.Builder().url(url).post(body).build();
        OkHttpClient client = new OkHttpClient();
        okhttp3.Call call = client.newCall(request);
        Response response = call.execute();
        String data = response.body().string();
        JSONObject jsonObject=JSONObject.parseObject(data);
        return jsonObject;
    }
}
