package com.example.yannis.dianming.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;

/**
 * Created by yannis on 2016/12/12.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int getLayoutID();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.inject(this);
        initData(savedInstanceState);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
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
