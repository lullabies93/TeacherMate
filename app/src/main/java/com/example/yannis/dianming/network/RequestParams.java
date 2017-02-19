package com.example.yannis.dianming.network;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yannis on 2016/12/16.
 */

public class RequestParams {

    private ConcurrentHashMap<String, Object> params = new ConcurrentHashMap<String, Object>();

    public RequestParams() {
        this(null);
    }

    public RequestParams(Map<String, Object> param) {
        if (param != null){
            for (Map.Entry<String, Object> entry : param.entrySet()){
                Object object = entry.getValue();
                params.put(entry.getKey(), object);

            }
        }

    }

    public ConcurrentHashMap<String, Object> getParams() {
        return params;
    }
}
