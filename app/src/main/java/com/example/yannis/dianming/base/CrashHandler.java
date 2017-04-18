package com.example.yannis.dianming.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yannis on 2016/12/15.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static final boolean DEBUG = true;
    //文件路径
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator
            + "crash";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFEIX = ".trace";
    private static Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private static CrashHandler mCrashHandler = new CrashHandler();
    private Context mContext;
    private MyApplication myApplication;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return mCrashHandler;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
        myApplication = (MyApplication) mContext.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            //将文件写入sd卡
            writeToSDcard(ex);
            //写入后在这里可以进行上传操作
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ex.printStackTrace();
        if (!handleEx(ex) && mDefaultCrashHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultCrashHandler.uncaughtException(thread, ex);
        }
//        else {
//            android.os.Process.killProcess(android.os.Process.myPid());
//            System.exit(1);
//        }

    }

    private boolean handleEx(final Throwable ex) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Util.showToast(mContext, ex.getMessage());
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
//                new AlertDialog.Builder(mContext).setTitle("Error").setMessage(ex.getMessage())
//                        .setPositiveButton
//                        ("ok", new DialogInterface.OnClickListener() {
//
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
////                                myApplication.appExit();
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                                System.exit(1);
//
//                            }
//                        }).show();
                Looper.loop();
            }
        }).start();

        return true;
    }

    //将异常写入文件
    private void writeToSDcard(Throwable ex) throws IOException, PackageManager.NameNotFoundException {
        //如果没有SD卡，直接返回
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        File filedir = new File(PATH);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        long currenttime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currenttime));

        File exfile = new File(PATH + File.separator + FILE_NAME + time + FILE_NAME_SUFEIX);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(exfile)));
        Log.e("错误日志文件路径", "" + exfile.getAbsolutePath());
        pw.println(time);
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //当前版本号
        pw.println("App Version:" + pi.versionName + "_" + pi.versionCode);
        //当前系统
        pw.println("OS version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        //制造商
        pw.println("Vendor:" + Build.MANUFACTURER);
        //手机型号
        pw.println("Model:" + Build.MODEL);
        //CPU架构
        pw.println("CPU ABI:" + Build.CPU_ABI);

        ex.printStackTrace(pw);
        pw.close();

    }


}

