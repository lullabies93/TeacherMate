package com.example.yannis.dianming.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.HomeworkAdapter;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/1/10.
 */

public class WorkFragment extends BaseFragment {
    @InjectView(R.id.workListview)
    ListView workListview;

    private ArrayList<Course> courses;
    private HomeworkAdapter homeworkAdapter;

    private String url = APIs.GET_ALL_COURSE + "?teacher_id=1";


    @Override
    public int getLayoutID() {
        return R.layout.work_fragment;
    }

    @Override
    public void initData(Bundle bundle) {

        courses = new ArrayList<>();
        homeworkAdapter = new HomeworkAdapter(courses, getActivity());

    }

    @Override
    public void initView(View view) {
        workListview.setAdapter(homeworkAdapter);
    }

    @Override
    public void loadData() {
        Util.logHelper(url);
        CommonRequest.createGetRequest(url, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {

                if (object instanceof ArrayList) {
                    courses = (ArrayList<Course>) object;
                    Util.logHelper("success");
                    refreshList();
                }else {
                    Util.showToast(getActivity(), "data error");
                }

            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(getActivity(), object.toString());
            }
        }, Course.class));
    }

    private void refreshList() {
        homeworkAdapter.courses = courses;
        homeworkAdapter.notifyDataSetChanged();
    }
}
