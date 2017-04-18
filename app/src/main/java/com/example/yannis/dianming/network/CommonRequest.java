package com.example.yannis.dianming.network;

import android.content.Context;

import com.example.yannis.dianming.base.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by yannis on 2016/12/16.
 */

public class CommonRequest {

    private static OkHttpClient commonClient;

    public static OkHttpClient getCommonClient() {
        return commonClient;
    }

    public static void setCommonClient(OkHttpClient commonClient) {
        CommonRequest.commonClient = commonClient;
    }

    public static void createGetRequest(String url, RequestParams params, CommomHandler handler) {

        Util.logHelper(url);
        Request request = new Request.Builder().url(url).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));
    }

    public static void createDeleteRequest(String url, String json, CommomHandler handler) {
        Util.logHelper(url + "\n" + json);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).delete(requestBody).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));
    }

    public static void createPutRequest(String url, String json, CommomHandler handler) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Util.logHelper(json);
        Request request = new Request.Builder().url(url).put(requestBody).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));


    }

    public static void createPostRequest(String url, RequestParams params, CommomHandler handler) {
        Util.logHelper(url);
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.getParams().entrySet()) {
                formBody.add(entry.getKey(), entry.getValue().toString());
                Util.logHelper(entry.getValue().toString());
            }
        }
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));
    }

    public static void createJsonPostRequest(String url, String json, CommomHandler handler) {
        Util.logHelper(url + "\n" + json);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = commonClient.newCall(request);
        call.enqueue(new CommomCallback(handler));
    }
}
