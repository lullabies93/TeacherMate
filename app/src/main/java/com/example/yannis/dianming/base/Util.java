package com.example.yannis.dianming.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yannis on 2016/12/14.
 */

public class Util {

    private static final String TAG = "teachermate";

    private static String patternStr = "[0-9]";
    private static Pattern pattern = Pattern.compile(patternStr);


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo gprs = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (!wifi.isConnected() && !gprs.isConnected()) return false;
        return true;
    }

    public static boolean isAppRuning(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info:runningTaskInfos){
            if (info.baseActivity.getPackageName().startsWith("com.example.yannis.dianming")){
                return true;
            }
        }
        return false;
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static void logHelper(String info) {
        Log.i(TAG, info);
    }

    public static void intent(Context context, Class clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }

    public static String getDate(Context context) {
        SimpleDateFormat df = new SimpleDateFormat(ConstantValues.dateFormat);
        return df.format(new Date());
    }

    public static String getDateBeforeOrLater(Context context, int days) {
        if (days == 0) {
            return getDate(context);
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE, days);//把日期往后增加一天.整数往后推,负数往前移动
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(calendar.getTime());

        return dateString;
    }

    public static boolean isMatch(String patternString){
        return (pattern.matcher(patternString)).matches();
    }
}
