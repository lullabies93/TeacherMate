package com.example.yannis.dianming;

import com.example.yannis.dianming.base.BaseActivity;

import java.util.Stack;

/**
 * Created by yannis on 2017/4/11.
 */

public class ActivityStack {
    private static Stack<BaseActivity> activities;
    private ActivityStack mInstance;

    public ActivityStack getInstance() {
        if (mInstance == null) {
            return new ActivityStack();
        }
        return mInstance;
    }

    private  ActivityStack() {
        activities = new Stack<>();
    }

    public static void addActivity(BaseActivity baseActivity) {
        if (baseActivity != null) {
            activities.push(baseActivity);
        }
    }

    public static void finishActivity() {
        if (activities==null) return;
        activities.peek().finish();
        activities.pop();
    }

    public static String getTopActivityName(){
        if (activities==null||activities.size()<1) return "";
        BaseActivity activity = activities.peek();
        return activity.getComponentName().getClassName();
    }

    public void clear(){
        if (activities!=null){
            for (BaseActivity activity: activities){
                activity.finish();
            }
            activities.clear();
        }
    }

    public void exitApp(){
        clear();
        System.exit(0);
    }
}
