package com.example.yannis.dianming.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.CourseActivity;
import com.example.yannis.dianming.activity.HomeworkActivity;
import com.example.yannis.dianming.adapter.ExpandableAdapter;
import com.example.yannis.dianming.adapter.HomeworkAdapter;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.HomeworkType;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/1/10.
 */

public class WorkFragment extends BaseFragment {

    @InjectView(R.id.listview)
    ListView listview;
    @InjectView(R.id.refresh)
    ImageButton refresh;
    private MyApplication myApplication;
    private ArrayList<Course> courses;
    private HomeworkAdapter homeworkAdapter;
    private String url = ConstantValues.GET_ALL_COURSE;

    @Override
    public int getLayoutID() {
        return R.layout.work_fragment;
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getActivity().getApplication();
        courses = new ArrayList<>();
        homeworkAdapter = new HomeworkAdapter(courses, getActivity());
    }

    @Override
    public void initView(View view) {
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        listview.setAdapter(homeworkAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HomeworkActivity.class);
                intent.putExtra(ConstantValues.courseInfo, courses.get(position));
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(url + "?" + ConstantValues.teacherID + "="
                + myApplication.getTeacher_id(), null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                Util.logHelper("success");
                if (refresh.getVisibility()==View.VISIBLE){
                    refresh.setVisibility(View.GONE);
                }
                courses = (ArrayList<Course>) object;
                refreshList();
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(getActivity(), object.toString());
                if (refresh.getVisibility()==View.GONE){
                    refresh.setVisibility(View.VISIBLE);
                }
            }
        }, Course.class));

    }

    private void refreshList() {
        homeworkAdapter.courses = courses;
        homeworkAdapter.notifyDataSetChanged();
    }
}
