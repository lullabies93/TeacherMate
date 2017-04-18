package com.example.yannis.dianming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.HomeworkAdapter;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CourseActivity extends BaseActivity {

    @InjectView(R.id.workListview)
    ListView workListview;
    @InjectView(R.id.back)
    TextView back;

    private ArrayList<Course> courses;
    private HomeworkAdapter homeworkAdapter;


    private String url = ConstantValues.GET_ALL_COURSE;
    private MyApplication myApplication;

    private CourseActivity activity;


    @Override
    public int getLayoutID() {
        return R.layout.activity_course;
    }

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        myApplication = (MyApplication) activity.getApplication();
        courses = new ArrayList<>();
        homeworkAdapter = new HomeworkAdapter(courses, activity);
    }

    @Override
    public void initViews() {
        workListview.setAdapter(homeworkAdapter);
        workListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(activity, SetPercentActivity.class);
                intent.putExtra(ConstantValues.courseInfo, courses.get(position));
                activity.startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadData() {
        Util.logHelper(url);
        CommonRequest.createGetRequest(url + "?teacher_id=" + myApplication.getTeacher_id(), null, new
                CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {

                if (object instanceof ArrayList) {
                    courses = (ArrayList<Course>) object;
                    refreshList();
                } else {
                    Util.showToast(activity, "data error");
                }

            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
            }
        }, Course.class));
    }

    private void refreshList() {
        homeworkAdapter.courses = courses;
        homeworkAdapter.notifyDataSetChanged();
    }

}
