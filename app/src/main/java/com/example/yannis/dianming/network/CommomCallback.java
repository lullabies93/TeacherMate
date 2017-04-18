package com.example.yannis.dianming.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Clazz;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yannis on 2016/12/17.
 */

public class CommomCallback implements Callback {

    private CommomListener listener;
    private Class<?> clazz;
    private Handler uiHandler;

    public CommomCallback(CommomHandler handler) {
        listener = handler.listener;
        clazz = handler.clazz;
        uiHandler = new Handler(Looper.getMainLooper());
    }


    private void handleResult(String result) {
        if (result == null || result.equals("")) {
            listener.onFailure("返回错误");
            return;
        }
        try {

            JSONObject jsonObject = new JSONObject(result);
            if (clazz == null) {
                listener.onSuccess(result);
            } else if (jsonObject.getInt("status") == 1 && jsonObject.has("items")) {
                listener.onSuccess(GsonToList(jsonObject.getJSONArray("items").toString(), clazz));

            } else {
                listener.onSuccess(result);
            }
        } catch (JSONException e) {
            listener.onFailure(e.getMessage());
        }
    }

    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        //使用GSON解析成实体对象返回
        Gson gson = new Gson();
        //Util.logHelper("json::"+gsonString);
        JsonArray array = new JsonParser().parse(gsonString).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(new Gson().fromJson(elem, cls));
        }
        return list;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String error = e.getMessage();
                if (error.startsWith("Failed to connect to")){
                    error = "网络错误";
                }
                listener.onFailure(error);
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()){
            final String ret = response.body().string();
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    handleResult(ret);
                }
            });
        }else {//重新登录
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    listener.onFailure("请求失败");
                }
            });
        }

    }
}
