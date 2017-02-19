package com.example.yannis.dianming.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.ViewPagerAdapter;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by yannis on 2017/1/10.
 */

public class SignFragment extends BaseFragment {
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;

    private static final String load_courses_url = APIs.GET_ALL_COURSE
            + "?teacher_id=1&fields=course_name,course_id,weekday,section,classroom";

    private ArrayList<Course> allCourses;

    private String[] labels;
    private MyApplication myApplication;
    private ViewPagerAdapter adapter;

    @Override
    public int getLayoutID() {
        return R.layout.sign_fragment;
    }

    @Override
    public void initData(Bundle bundle) {

        allCourses = new ArrayList<>();
        myApplication = (MyApplication) getActivity().getApplication();

        labels = getResources().getStringArray(R.array.weekdays);
        for (int i = 0; i < labels.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(labels[i]));
        }


    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(load_courses_url, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                refreshData(object);

            }

            @Override
            public void onFailure(Object object) {

            }
        }, Course.class));
    }

    private void refreshData(Object object) {
        allCourses.clear();
        if (object instanceof ArrayList) {
            allCourses = (ArrayList<Course>) object;
        }

        adapter = new ViewPagerAdapter(getChildFragmentManager(), Arrays
                .asList(labels), allCourses);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(getWeekday());

    }

    private int getWeekday() {
        // TODO Auto-generated method stub
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        myApplication.setWeekday(i);
        return i == 1 ? 6 : (i - 2) % 7;
    }
}
