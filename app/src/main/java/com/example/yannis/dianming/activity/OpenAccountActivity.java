package com.example.yannis.dianming.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class OpenAccountActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.username)
    EditText username;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.notice)
    EditText notice;
    @InjectView(R.id.submit)
    Button submit;
    @InjectView(R.id.url)
    TextView url;
    @InjectView(R.id.activity_open_account)
    LinearLayout activityOpenAccount;
    @InjectView(R.id.title)
    TextView title;

    private int assistantId;
    private MyApplication myApplication;

    @Override
    public int getLayoutID() {
        return R.layout.activity_open_account;
    }


    @Override
    public void initViews() {

    }

    @Override
    public void loadData() {
        myApplication = (MyApplication) getApplication();
        CommonRequest.createGetRequest(APIs.GET_ASSISTANT + "?" + APIs.teacherID + "=" + myApplication
                .getTeacher_id(), null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(object.toString());
                    JSONArray assistants = ret.getJSONArray("items");
                    if (ret.getInt("count") > 0) {
                        title.setText("已有子账号");
                        submit.setText("删除");
                        JSONObject assistant = assistants.getJSONObject(0);
                        username.setText(assistant.getString("username"));
                        password.setText("**********");
                        notice.setText(assistant.getString("remark"));
                        assistantId = assistant.getInt(APIs.assistantId);
                        username.setEnabled(false);
                        password.setEnabled(false);
                        notice.setEnabled(false);
                    }
//                    else {
//                        title.setText("开通子账号");
//                        submit.setText("提交");
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(OpenAccountActivity.this, object.toString());
            }
        }));
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @OnClick({R.id.back, R.id.submit, R.id.url})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                OpenAccountActivity.this.finish();
                break;
            case R.id.submit:
                submit();
                break;
            case R.id.url:
                break;
        }
    }

    private void submit() {
        String text = submit.getText().toString();
        if (text.equals(APIs.submit)) {
            post();
        } else if (text.equals(APIs.delete)) {
            delete();
        }
    }

    private void delete() {
        JSONArray array = new JSONArray();
        array.put(assistantId);
        CommonRequest.createDeleteRequest(APIs.GET_ASSISTANT, array.toString(), new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(String.valueOf(object));
                    if (ret.getInt(APIs.status) == 1) {
                        Util.showToast(OpenAccountActivity.this, "子账号已删除，可重新创建");
                        title.setText("开通子账号");
                        submit.setText("提交");
                        username.setEnabled(true);
                        password.setEnabled(true);
                        notice.setEnabled(true);
                        username.setText(username.getHint());
                        password.setText(password.getHint());
                        notice.setText(notice.getHint());
                        assistantId = 0;
                    } else {
                        Util.showToast(OpenAccountActivity.this, "删除失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(OpenAccountActivity.this, "删除失败");

            }
        }));
    }

    private void post() {
        String name = username.getText().toString();
        String pwd = password.getText().toString();
        String remark = notice.getText().toString();
        if (name.isEmpty()||pwd.isEmpty()|remark.isEmpty()){
            Util.showToast(OpenAccountActivity.this, "输入不能为空");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject param = new JSONObject();
        try {
            jsonObject.put(APIs.USERNAME, username.getText().toString());
            jsonObject.put(APIs.PASSWORD, password.getText().toString());
            param.put(APIs.registerUser, jsonObject);
            param.put(APIs.teacherID, myApplication.getTeacher_id());
            param.put(APIs.remark, notice.getText().toString());
            CommonRequest.createJsonPostRequest(APIs.GET_ASSISTANT, param.toString(), new CommomHandler(new CommomListener() {


                @Override
                public void onSuccess(Object object) {
                    try {
                        JSONObject ret = new JSONObject(String.valueOf(object));
                        if (ret.getInt(APIs.status) == 1) {
                            Util.showToast(OpenAccountActivity.this, "成功开通子账号");
                            OpenAccountActivity.this.finish();
                        } else {
                            Util.showToast(OpenAccountActivity.this, "开通失败");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Object object) {
                    Util.showToast(OpenAccountActivity.this, "操作失败");

                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
