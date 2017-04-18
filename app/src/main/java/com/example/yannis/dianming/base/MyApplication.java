package com.example.yannis.dianming.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.yannis.dianming.activity.LoginActivity;
import com.example.yannis.dianming.activity.TroubleStudentsActivity;
import com.example.yannis.dianming.bean.DBEntity;
import com.example.yannis.dianming.bean.TroubleStudent;
import com.example.yannis.dianming.network.CommonRequest;
import com.example.yannis.dianming.network.PersistentCookieStore;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by yannis on 2017/1/14.
 */

public class MyApplication extends Application {

    private int teacher_id;
    private int user_id;
    private int status;
    private String group;
    private boolean fromPush;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public boolean isFromPush() {
        return fromPush;
    }

    public void setFromPush(boolean fromPush) {
        this.fromPush = fromPush;
    }

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

    private static List<Activity> listActivity;

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
        initCrashHandler();

        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                Util.logHelper(s);
            }

            @Override
            public void onFailure(String s, String s1) {
                Util.logHelper(s + "--" + s1);
            }
        });
        UmengNotificationClickHandler clickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                super.dealWithCustomAction(context, uMessage);
                Map<String, String> msg = uMessage.extra;
                refreshDB(context, msg.get("data"));
                if (!Util.isAppRuning(context)) {
                    Util.logHelper("app is not running");
                    setFromPush(true);
                    launchApp(context, uMessage);
                } else if (getTopActivityName().equals
                        (TroubleStudentsActivity.class.getName())) {
                    Intent in = new Intent();
                    in.putExtra("data", msg.get("data"));
                    in.setAction("refreshPushData");
                    context.sendBroadcast(in);
                } else {
                    Intent in = new Intent(context, TroubleStudentsActivity.class);
                    in.putExtra("data", msg.get("data"));
                    Util.logHelper(msg.get("data"));
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(in);
                }

            }
        };
        pushAgent.setNotificationClickHandler(clickHandler);
        pushAgent.setDisplayNotificationNumber(3);
        initValue();
        initOkHttp();
        Configuration.Builder builder = new Configuration.Builder(this);
        builder.addModelClass(DBEntity.class);
        ActiveAndroid.initialize(builder.create());
    }

    private void refreshDB(Context mContext, String data) {
        Util.logHelper(data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray names = jsonObject.names();
            JSONArray array;
            JSONArray item;
            DBEntity entity;
            if (names == null) {
                Util.showToast(mContext, "data empty");
                return;
            }
            for (int i = 0; i < names.length(); i++) {
                String number = names.getString(i);
                array = new JSONArray(jsonObject.getJSONArray(number).toString());
                for (int j = 0; j < array.length(); j++) {
                    item = array.getJSONArray(j);
                    entity = new DBEntity(item.getInt(0), item
                            .getString(1), number,
                            item.getInt(4), item.getString(3), item.getString
                            (2), getTeacher_id());
                    DBManager.insertUser(entity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

    }

    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().cookieJar(new CookieJar() {

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0) {
                    SPUtils.putCookie(getApplicationContext(), cookies.get(0));
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = new ArrayList<Cookie>();
                if (String.valueOf(SPUtils.get(getApplicationContext(), "cookie_name", "")).equals("")) {
                    return cookies;
                }
                cookies.add(SPUtils.getCookie(getApplicationContext()));
                return cookies;
            }
        }).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit
                .SECONDS).build();
        CommonRequest.setCommonClient(okHttpClient);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    private void initValue() {
        setStatus(0);
        setTeacher_id(0);
        setUser_id(-1);
        setGroup("");
        setUsername("");
        setTeacher_name("");
        setFromPush(false);
        listActivity = Collections.synchronizedList(new LinkedList<Activity>());
        registerActivityListener();
    }

    public void pushActivity(Activity activity) {
        listActivity.add(activity);
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void popActivity(Activity activity) {
        listActivity.remove(activity);
    }

    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (listActivity) {
            final int size = listActivity.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = listActivity.get(size);
        }
        Util.logHelper(mBaseActivity.getClass().getName());
        return mBaseActivity.getClass().getName();
    }

    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    pushActivity(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    if (null == activity && listActivity.isEmpty()) {
                        return;
                    }
                    if (listActivity.contains(activity)) {
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        popActivity(activity);
                    }
                }
            });
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        if (listActivity == null) {
            return;
        }
        for (Activity activity : listActivity) {
            activity.finish();
        }
        listActivity.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            Util.logHelper("app exit");
            finishAllActivity();
        } catch (Exception e) {
        }
    }
}
