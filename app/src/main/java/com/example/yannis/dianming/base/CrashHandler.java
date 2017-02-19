package com.example.yannis.dianming.base;

/**
 * Created by yannis on 2016/12/15.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private CrashHandler instance;
    private Thread.UncaughtExceptionHandler defaultExceptionHandler;

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && defaultExceptionHandler !=null){
            defaultExceptionHandler.uncaughtException(thread, ex);
        }else {

        }
    }

    private boolean handleException(Throwable ex){
        return false;
    }
}
