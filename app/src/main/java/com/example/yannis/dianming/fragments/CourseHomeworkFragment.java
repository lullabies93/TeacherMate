package com.example.yannis.dianming.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.CourseActivity;
import com.example.yannis.dianming.adapter.HomeworkAdapter;
import com.example.yannis.dianming.base.BaseFragment;
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

/**
 * Created by yannis on 2017/3/4.
 */

public class CourseHomeworkFragment extends BaseFragment {
    @InjectView(R.id.workListview)
    ListView workListview;
    private ArrayList<Course> courses;
    private HomeworkAdapter homeworkAdapter;


    private String url = ConstantValues.GET_ALL_COURSE;
    private MyApplication myApplication;

    @Override
    public int getLayoutID() {
        return R.layout.homework_course_fragment;
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getActivity().getApplication();
        courses = new ArrayList<>();
        homeworkAdapter = new HomeworkAdapter(courses, getActivity());
        workListview.setAdapter(homeworkAdapter);
    }

    @Override
    public void initView(View view) {
        workListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
            }
        });
    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(url, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    courses = (ArrayList<Course>) object;
                    homeworkAdapter.courses = courses;
                    homeworkAdapter.notifyDataSetChanged();
                } else {
                    Util.showToast(getActivity(), "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(getActivity(), object.toString());
            }
        }, Course.class));
    }
}
