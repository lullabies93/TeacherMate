package com.example.yannis.dianming.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import java.util.Map;

import okhttp3.Cookie;

/**
 * Created by yannis on 2017/2/16.
 */
public class SPUtils {

    public static String FILLNAME = "config";
    public static String name = "cookie_name";
    public static String value = "cookie_value";
    public static String expire = "cookie_expire";
    public static String domain = "cookie_domain";
    public static String path = "cookie_path";
    public static String secure = "cookie_secure";
    public static String httpOnly = "cookie_httpOnly";
    public static String hostOnly = "cookie_hostOnly";
    public static String persistent = "cookie_persistent";

    /**
     * 存入某个key对应的value值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    /**
     * 得到某个key对应的值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object get(Context context, String key, Object defValue) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }
        return null;
    }

    /**
     * 返回所有数据
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    public static void putCookie(Context context, Cookie cookie) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, cookie.name());
        edit.putString(value, cookie.value());
        edit.putLong(expire, cookie.expiresAt());
        edit.putBoolean(persistent, cookie.persistent());
        edit.putBoolean(httpOnly, cookie.httpOnly());
        edit.putBoolean(hostOnly, cookie.hostOnly());
        edit.putBoolean(secure, cookie.secure());
        edit.putString(path, cookie.path());
        edit.putString(domain, cookie.domain());
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    public static Cookie getCookie(Context context){
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        Cookie.Builder builder = new Cookie.Builder();
        builder = builder.name(sp.getString(name, ""));
        builder =  builder.value(sp.getString(value, ""));
        String domain_value = sp.getString(domain, "");
        builder = builder.expiresAt(sp.getLong(expire, 0));
        builder = builder.path(sp.getString(path, "/"));
        boolean isHttpOnly = sp.getBoolean(httpOnly, false);
        builder = isHttpOnly ? builder.hostOnlyDomain(domain_value) : builder.domain(domain_value);
        boolean isSecure = sp.getBoolean(secure, false);
        builder = isSecure ? builder.secure() : builder;
        builder = isHttpOnly ? builder.httpOnly() : builder;
        return builder.build();
    }

    /**
     * 清除所有内容
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    public static void clearCookie(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Cookies_Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

}
