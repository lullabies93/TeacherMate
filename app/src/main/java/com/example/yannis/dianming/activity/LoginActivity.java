package com.example.yannis.dianming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;
import com.example.yannis.dianming.network.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2016/12/13.
 */

public class LoginActivity extends BaseActivity {


    @InjectView(R.id.username)
    EditText username;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.login)
    Button login;

    private MyApplication myApplication;

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        CommonRequest.createGetRequest(APIs.LOGIN, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(String.valueOf(object));
                    myApplication.setUser_id(ret.getInt("user_id"));
                    myApplication.setTeacher_id(ret.getInt(APIs.teacherID));
                    myApplication.setTeacher_name(ret.getString(APIs.teacherName));
                    myApplication.setGroup(ret.getString(APIs.teacherGroup));
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    LoginActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(LoginActivity.this, String.valueOf(object));
            }
        }));
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getApplication();
    }
}
