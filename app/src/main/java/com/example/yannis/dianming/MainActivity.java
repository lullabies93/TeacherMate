package com.example.yannis.dianming;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.yannis.dianming.activity.HomeActivity;
import com.example.yannis.dianming.activity.LoginActivity;
import com.example.yannis.dianming.activity.TroubleStudentsActivity;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.SPUtils;
import com.example.yannis.dianming.base.Util;

public class MainActivity extends BaseActivity {

    private MyApplication myApplication;

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {
        Util.logHelper(String.valueOf(myApplication.getTeacher_id()));
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getApplication();
        if (Boolean.parseBoolean(SPUtils.get(this, ConstantValues.FIRST_LAUNCH, true).toString())){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    MainActivity.this.finish();
                }
            }, 1000);
        }else {
            myApplication.setGroup(String.valueOf(SPUtils.get(this, ConstantValues.teacherGroup, "")));
            myApplication.setTeacher_name(String.valueOf(SPUtils.get(this, ConstantValues.teacherName, "")));
            myApplication.setUsername(String.valueOf(SPUtils.get(this, ConstantValues.USERNAME, "")));
            myApplication.setTeacher_id(Integer.parseInt(String.valueOf(SPUtils.get(this, ConstantValues.teacherID, 0))));
            if (myApplication.isFromPush()){
                startActivity(new Intent(MainActivity.this, TroubleStudentsActivity.class));
                myApplication.setFromPush(false);
            }else {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }

            MainActivity.this.finish();
        }
    }
}
