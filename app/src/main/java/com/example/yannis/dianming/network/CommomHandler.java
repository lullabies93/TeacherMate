package com.example.yannis.dianming.network;

/**
 * Created by yannis on 2016/12/17.
 */

public class CommomHandler {

    public CommomListener listener;
    public Class<?> clazz;

    public CommomHandler(CommomListener listener) {
        this.listener = listener;
    }

    public CommomHandler(CommomListener listener, Class<?> clazz) {
        this.listener = listener;
        this.clazz = clazz;
    }
}
