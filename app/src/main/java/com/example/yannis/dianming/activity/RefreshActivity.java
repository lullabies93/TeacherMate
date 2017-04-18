package com.example.yannis.dianming.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

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

public class RefreshActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.pwdEditview)
    EditText pwdEditview;
    @InjectView(R.id.codeEditview)
    EditText codeEditview;
    @InjectView(R.id.codeView)
    ImageView codeView;
    @InjectView(R.id.confirm)
    Button confirm;
    @InjectView(R.id.getCode)
    Button getCode;

    private MyApplication myApplication;
    private RefreshActivity activity;
    private String name, pwd, code, captcha_file_name;
    private Handler uiHandler;
    private ProgressDialog progressDialog;

    private CountDownTimer  timer2;

    @Override
    public int getLayoutID() {
        return R.layout.activity_refresh;
    }

    @Override
    public void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd = pwdEditview.getText().toString();
                requestCode();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setEnabled(false);
                pwd = pwdEditview.getText().toString();
                code = codeEditview.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(ConstantValues.USERNAME, name);
                    jsonObject.put(ConstantValues.PASSWORD, pwd);
                    jsonObject.put("captcha", code);
                    jsonObject.put(ConstantValues.captcha_file_name, captcha_file_name);
                    getData(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getData(String string) {
        progressDialog.show();
        CommonRequest.createJsonPostRequest(ConstantValues.GET_CAPTCHA, string, new CommomHandler(new CommomListener() {

            @Override
            public void onSuccess(Object object) {
                try {
                    Util.logHelper(object.toString());
                    JSONObject ret = new JSONObject(object.toString());
                    if (ret.getInt(ConstantValues.status) == 1) {
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Util.showToast(activity, "refresh done");
                        activity.finish();
                    }else {
                        Util.showToast(activity, "refresh failure");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    confirm.setEnabled(true);
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }


            }

            @Override
            public void onFailure(Object object) {
                if (object==null){
                    Util.showToast(activity, "error null");
                }else {
                    Util.showToast(activity, object.toString());

                }
                confirm.setEnabled(true);
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        }));

    }

    private void requestCode() {
        CommonRequest.createGetRequest(ConstantValues.GET_CAPTCHA, null, new CommomHandler
                (new CommomListener() {


                    @Override
                    public void onSuccess(Object object) {
                        Util.logHelper(object.toString());
                        try {
                            JSONObject json = new JSONObject(object.toString());
                            if (json.has("captcha_file_name")) {
                                captcha_file_name = json.getString("captcha_file_name");
                                gerCodeImage(ConstantValues.GET_CODE + captcha_file_name + ".jpg");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Object object) {
                        Util.showToast(activity, object.toString());
                    }
                }));
    }

    private void gerCodeImage(String url) {
        Util.logHelper(url);
        Request request = new Request.Builder().url(url).build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Util.showToast(activity, e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final InputStream is = response.body().byteStream();
                final Bitmap bm = BitmapFactory.decodeStream(is);
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        codeView.setImageBitmap(bm);
                        getCode.setEnabled(false);
                        timer2.start();
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
    protected void onDestroy() {
        if (timer2 != null){
            timer2.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getApplication();
        activity = this;
        name = myApplication.getUsername();
        uiHandler = new Handler(Looper.getMainLooper());
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("refreshing.....please wait for a few seconds");
        progressDialog.setCancelable(false);
        timer2 = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getCode.setText("还剩" + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                getCode.setText("获取验证码");
                getCode.setEnabled(true);
            }
        };
    }


}
