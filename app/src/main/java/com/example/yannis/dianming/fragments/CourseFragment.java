package com.example.yannis.dianming.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.CourseAdapter;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannis on 2017/1/10.
 */

public class CourseFragment extends BaseFragment {

    private LinearLayout morningCourse, afternoonCourse, eveningCourse;
    private TextView morningTime, afternoonTime, eveningTime;
    private ListView morningList, afternoonList, eveningList;

    private ArrayList<Course2> morningCourses, afternoonCourses, eveningCourses;
    private List<Course2> allCourses = new ArrayList<>();

    private CourseAdapter morningAdapter, afternoonAdapter, eveningAdapter;

    private String label;
    private int weekday;

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public void setCourses(List<Course2> courses) {
        this.allCourses = courses;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int getLayoutID() {
        return R.layout.course_fragment;
    }

    @Override
    public void initData(Bundle bundle) {
        morningCourses = new ArrayList<Course2>();
        afternoonCourses = new ArrayList<Course2>();
        eveningCourses = new ArrayList<Course2>();
        morningAdapter = new CourseAdapter(morningCourses, getActivity());
        afternoonAdapter = new CourseAdapter(afternoonCourses, getActivity());
        eveningAdapter = new CourseAdapter(eveningCourses, getActivity());

    }

    @Override
    public void initView(View view) {
        morningCourse = (LinearLayout) view.findViewById(R.id.morning_course);
        afternoonCourse = (LinearLayout) view.findViewById(R.id.afternoon_course);
        eveningCourse = (LinearLayout) view.findViewById(R.id.evening_course);
        morningTime = (TextView) morningCourse.findViewById(R.id.time);
        afternoonTime = (TextView) afternoonCourse.findViewById(R.id.time);
        eveningTime = (TextView) eveningCourse.findViewById(R.id.time);
        morningList = (ListView) morningCourse.findViewById(R.id.courses);
        afternoonList = (ListView) afternoonCourse.findViewById(R.id.courses);
        eveningList = (ListView) eveningCourse.findViewById(R.id.courses);
        morningTime.setText(getString(R.string.morning));
        afternoonTime.setText(getString(R.string.afternoon));
        eveningTime.setText(getString(R.string.evening));

        morningList.setAdapter(morningAdapter);
        afternoonList.setAdapter(afternoonAdapter);
        eveningList.setAdapter(eveningAdapter);

    }

    @Override
    public void loadData() {
        morningCourses.clear();
        afternoonCourses.clear();
        eveningCourses.clear();
        String sec = null;
        String[] section = null;
        int length = 0;
        for (Course2 course2 : allCourses) {
            sec = course2.getSection();
            section = sec.split(",");
            length = section.length;
            if (sec.contains("1,") || sec.contains("3,")) {
                course2.setSection("第" + section[0] + "-" + section[length-1] + "节");
                morningCourses.add(course2);
            }
            if (sec.contains("6,") || sec.contains(",9")) {
                course2.setSection("第" + section[0] + "-" + section[length-1] + "节");
                afternoonCourses.add(course2);
            }
            if (sec.contains("10,")) {
                course2.setSection("第" + section[0] + "-" + section[length-1] + "节");
                eveningCourses.add(course2);
            }
        }

        if (morningCourses.isEmpty() && morningCourse.getVisibility() == View.VISIBLE) {
            morningCourse.setVisibility(View.GONE);
        }
        if ((!morningCourses.isEmpty()) && morningCourse.getVisibility() == View.GONE) {
            morningCourse.setVisibility(View.VISIBLE);
        }
        if (afternoonCourses.isEmpty() && afternoonCourse.getVisibility() == View.VISIBLE) {
            afternoonCourse.setVisibility(View.GONE);
        }
        if ((!afternoonCourses.isEmpty()) && afternoonCourse.getVisibility() == View.GONE) {
            afternoonCourse.setVisibility(View.VISIBLE);
        }
        if (eveningCourses.isEmpty() && eveningCourse.getVisibility() == View.VISIBLE) {
            eveningCourse.setVisibility(View.GONE);
        }
        if ((!eveningCourses.isEmpty()) && eveningCourse.getVisibility() == View.GONE) {
            eveningCourse.setVisibility(View.VISIBLE);
        }
        morningAdapter.courses = morningCourses;
        morningAdapter.notifyDataSetChanged();
        afternoonAdapter.courses = afternoonCourses;
        afternoonAdapter.notifyDataSetChanged();
        eveningAdapter.courses = eveningCourses;
        eveningAdapter.notifyDataSetChanged();


    }


}
