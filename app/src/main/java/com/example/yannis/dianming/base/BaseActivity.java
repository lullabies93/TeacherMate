package com.example.yannis.dianming.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.example.yannis.dianming.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.message.PushAgent;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by yannis on 2016/12/12.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int getLayoutID();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutID());
        ButterKnife.inject(this);
        PushAgent.getInstance(this).onAppStart();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中.....");
        initData(savedInstanceState);
        initViews();
        loadData();
    }

    public void showDialog() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    public abstract void initViews();

    public abstract void loadData();

    public abstract void initData(Bundle bundle);


}
