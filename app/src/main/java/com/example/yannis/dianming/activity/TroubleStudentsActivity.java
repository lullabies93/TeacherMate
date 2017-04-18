package com.example.yannis.dianming.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.MyReceiver;
import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.PushAdapter;
import com.example.yannis.dianming.adapter.StudentDetailAdapter;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.DBManager;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.DBEntity;
import com.example.yannis.dianming.bean.StudentSignDetail;
import com.example.yannis.dianming.bean.StudentSignState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class TroubleStudentsActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private TroubleStudentsActivity activity;
    private MyApplication myApplication;

    private ArrayList<DBEntity> datas;

    private PushAdapter adapter;

    private AlertDialog.Builder builder;

    private MyReceiver broadCast;

    @Override
    public int getLayoutID() {
        return R.layout.activity_trouble_students;
    }


    @Override
    public void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DBEntity entity = datas.get(position);
                StudentSignDetail param = new StudentSignDetail();
                param.setClass_number(entity.getClass_number());
                param.setMajor(entity.getMajor());
                param.setStudent_id(entity.getStudent_id());
                param.setStudent_name(entity.getStudent_name());
                param.setStudent_number(entity.getStudent_number());
                Intent intent = new Intent(activity, StudentDetailActivity.class);
                intent.putExtra(ConstantValues.studentInfo, param);
                activity.startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deletePosition = position;
                showDeleteDialog();
                return true;
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private int deletePosition;
    private boolean isSolved;

    private void showDeleteDialog() {
        if (builder == null) {
            builder = new AlertDialog.Builder(activity);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    modifyRecord();
                }
            });
        }
        isSolved = datas.get(deletePosition).isSolved();
        if (isSolved){
            builder.setTitle("取消标记?");
        }else {
            builder.setTitle("标记为已联系?");
        }
        builder.setMessage(datas.get(deletePosition).getStudent_name() + ":" + datas.get(deletePosition)
                .getException_times()+"次异常");
        builder.show();
    }

    private void modifyRecord() {
        DBManager.updateUser(datas.get(deletePosition), !isSolved);
        loadData();
    }

    Intent intent;

    @Override
    public void loadData() {

        datas = (ArrayList<DBEntity>) DBManager.query(myApplication.getTeacher_id());

        adapter.data = datas;
        adapter.notifyDataSetChanged();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    private IntentFilter filter;

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        myApplication = (MyApplication) getApplication();
        datas = new ArrayList<>();
        adapter = new PushAdapter(activity, datas);
        intent = getIntent();
        broadCast = new MyReceiver();
        filter = new IntentFilter();
        filter.addAction("refreshPushData");
        registerReceiver(broadCast, filter);
        broadCast.SetOnUpdateUIListenner(new MyReceiver.UpdateUIListenner() {
            @Override
            public void UpdateUI(Intent str) {
                intent = str;
                loadData();
            }
        });


    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadCast);
        super.onDestroy();
    }
}
