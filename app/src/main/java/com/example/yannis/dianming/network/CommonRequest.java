package com.example.yannis.dianming.network;

import com.example.yannis.dianming.base.Util;

import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by yannis on 2016/12/16.
 */

public class CommonRequest {

    private static OkHttpClient commonClient;

    static {
        commonClient = new OkHttpClient();
    }

    public static void createGetRequest(String url, RequestParams params, CommomHandler handler){

        Util.logHelper(url);
        Request request = new Request.Builder().url(url).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));
    }

    public static void createDeleteRequest(String url, String json, CommomHandler handler){
        Util.logHelper(url+"\n"+json);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).delete(requestBody).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));
    }

    public static void createPutRequest(String url, String json, CommomHandler handler){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Util.logHelper(json);
        Request request = new Request.Builder().url(url).put(requestBody).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));


    }

    public static void createPostRequest(String url, RequestParams params, CommomHandler handler){
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null){
            for (Map.Entry<String, Object> entry : params.getParams().entrySet()){
                formBody.add(entry.getKey(), entry.getValue().toString());
            }
        }
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));
    }

    public static void createJsonPostRequest(String url, String json, CommomHandler handler){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));
    }
}
