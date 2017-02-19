package com.example.yannis.dianming.base;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannis on 2017/1/14.
 */

public class MyApplication extends Application {

    private int teacher_id ;
    private int user_id;
    private int status;
    private String group;

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    private String teacher_name;

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    private int weekday;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    private int week;

    private static List<BaseActivity> listActivity;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initValue();
    }

    private void initValue() {
        setStatus(0);
        setTeacher_id(0);
        setUser_id(-1);
        setGroup("");
        setTeacher_name("");
        listActivity = new ArrayList<BaseActivity>();
    }

    public void addActivity(BaseActivity activity){
        listActivity.add(activity);
    }

    public void clearActivity(){
        listActivity.clear();
    }
}
