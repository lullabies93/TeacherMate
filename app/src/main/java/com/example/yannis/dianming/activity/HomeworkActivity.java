package com.example.yannis.dianming.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.HomeworkListAdapter;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.HomeworkRecord;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeworkActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.courseName)
    TextView courseName;
    @InjectView(R.id.addHomework)
    TextView addHomework;
    @InjectView(R.id.homeworkListview)
    ListView homeworkListview;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Course courseInfo;
    private HomeworkActivity activity;
    private HomeworkListAdapter homeworkListAdapter;
    private ArrayList<HomeworkRecord> records;

    @Override
    public int getLayoutID() {
        return R.layout.activity_homework;
    }

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        courseInfo = (Course) getIntent().getSerializableExtra(APIs.courseInfo);
        records = new ArrayList<>();
        homeworkListAdapter = new HomeworkListAdapter(records, activity);
        courseName.setText(courseInfo.getCourse_name());
    }


    @Override
    public void initViews() {
        homeworkListview.setAdapter(homeworkListAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        addHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加作业
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(APIs.GET_HOMEWORK_RECORDS + "?" + APIs.courseId + "=" + courseInfo
                .getCourse_id(), null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList){
                    records = (ArrayList<HomeworkRecord>) object;
                    refreshListview();
                }else{
                    Util.showToast(activity, "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
            }
        }, HomeworkRecord.class));
    }

    private void refreshListview() {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        homeworkListAdapter.records = records;
        homeworkListAdapter.notifyDataSetChanged();
    }


}
