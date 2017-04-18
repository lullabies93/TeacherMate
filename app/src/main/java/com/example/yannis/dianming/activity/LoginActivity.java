package com.example.yannis.dianming.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.SPUtils;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    @InjectView(R.id.codeEditview)
    EditText codeEditview;
    @InjectView(R.id.codeImage)
    ImageView codeImage;
    @InjectView(R.id.codeView)
    RelativeLayout codeView;

    private MyApplication myApplication;

    private boolean hasCode = false;

    private Handler uiHandler;
    private PushAgent pushAgent;

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = username.getText().toString();
                pwd = password.getText().toString();
                if (codeView.getVisibility() == View.VISIBLE) {
                    code = codeEditview.getText().toString();
                }
                doLogin();
            }
        });
    }

    private String name, pwd, code;

    private void doLogin() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantValues.USERNAME, name);
            jsonObject.put(ConstantValues.PASSWORD, pwd);
            if (hasCode) {
                jsonObject.put(ConstantValues.captcha_file_name, captcha_file_name);
                jsonObject.put("captcha", code);
                progressDialog.show();
                postWithCode(jsonObject.toString());
            } else {
                postWithCode(jsonObject.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String captcha_file_name;

    private ProgressDialog progressDialog;

    private void postWithCode(String string) {

        CommonRequest.createJsonPostRequest(ConstantValues.LOGIN, string, new CommomHandler
                (new CommomListener() {
                    @Override
                    public void onSuccess(Object object) {
                        try {
                            Util.logHelper(object.toString());
                            JSONObject json = new JSONObject(object.toString());
                            if (json.getInt(ConstantValues.status) == 3){
                                Util.showToast(LoginActivity.this, "用户名或密码错误");
                            } else if (json.getInt(ConstantValues.status) == 2) {//请求验证码
                                if (codeView.getVisibility() == View.GONE) {
                                    codeView.setVisibility(View.VISIBLE);
                                }
                                if (hasCode){
                                    Util.showToast(LoginActivity.this, "请检查用户名和密码");
                                }
                                captcha_file_name = json.getString("captcha_file_name");
                                hasCode = true;
                                getCode(ConstantValues.GET_CODE + captcha_file_name + ".jpg");

                            } else if (json.getInt(ConstantValues.status) == 1) {//登录成功

                                if (hasCode) {
                                    hasCode = false;
                                    doLogin();
                                } else {
                                    JSONObject ret = json.getJSONObject("data");
                                    Util.logHelper(ret.toString());
                                    myApplication.setTeacher_id(ret.getInt(ConstantValues.teacherID));
                                    myApplication.setTeacher_name(ret.getString(ConstantValues.teacherName));
                                    myApplication.setGroup(ret.getString(ConstantValues.teacherGroup));
                                    myApplication.setUsername(username.getText().toString());
                                    SPUtils.put(LoginActivity.this, ConstantValues.FIRST_LAUNCH, false);
                                    SPUtils.put(LoginActivity.this, ConstantValues.teacherGroup, ret.getString
                                            (ConstantValues.teacherGroup));
                                    SPUtils.put(LoginActivity.this, ConstantValues.USERNAME, name);
                                    SPUtils.put(LoginActivity.this, ConstantValues.teacherName, ret.getString
                                            (ConstantValues.teacherName));
                                    SPUtils.put(LoginActivity.this, ConstantValues.teacherID, ret.getInt
                                            (ConstantValues
                                                    .teacherID));
                                    pushAgent = PushAgent.getInstance(LoginActivity.this);
                                    pushAgent.addAlias(String.valueOf(myApplication.getTeacher_id()), ConstantValues.teacherID, new UTrack.ICallBack() {


                                        @Override
                                        public void onMessage(boolean b, String s) {
                                            Util.logHelper(String.valueOf(b)+":"+s);
                                        }
                                    });
                                    if (myApplication.isFromPush()){
                                        startActivity(new Intent(LoginActivity.this, TroubleStudentsActivity.class));
                                        myApplication.setFromPush(false);
                                    }else {
                                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    }

                                    LoginActivity.this.finish();
                                }

                                if (codeView.getVisibility() == View.VISIBLE) {

                                    codeView.setVisibility(View.GONE);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            if (progressDialog.isShowing()) {
                                progressDialog.cancel();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Object object) {
                        Util.showToast(LoginActivity.this, String.valueOf(object));
                        if (progressDialog.isShowing()){
                            progressDialog.cancel();
                        }
                    }
                }));
    }

    private void getCode(String url) {
        Util.logHelper(url);
        Request request = new Request.Builder().url(url).build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Util.showToast(LoginActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream is = response.body().byteStream();
                final Bitmap bm = BitmapFactory.decodeStream(is);
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        codeImage.setImageBitmap(bm);
                    }
                });
                is.close();

            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getApplication();
        uiHandler = new Handler(Looper.getMainLooper());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("waiting.....");
        progressDialog.setCancelable(false);
    }
}
