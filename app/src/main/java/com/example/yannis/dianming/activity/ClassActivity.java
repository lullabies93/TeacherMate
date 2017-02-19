package com.example.yannis.dianming.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClassActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.classListview)
    ListView classListview;

    private ClassActivity activity;

    private ArrayAdapter myAdapter;

    private String[] classes;

    @Override
    public int getLayoutID() {
        return R.layout.activity_class;
    }


    @Override
    public void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(APIs.GET_CLASS, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(object.toString());
                    if (ret.getInt(APIs.status)==1){
                        JSONArray array = ret.getJSONArray(APIs.classNumber);
                        int length = array.length();
                        classes = new String[length];
                        for (int i = 0; i < length; i++){
                            classes[i] = array.getString(i);
                        }
                        myAdapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, classes);
                        classListview.setAdapter(myAdapter);
                    }else {
                        Util.logHelper("data error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.logHelper(object.toString());
            }
        }));
    }

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        classes = new String[]{};
    }


}
