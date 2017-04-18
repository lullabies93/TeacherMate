package com.example.yannis.dianming.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.LoginActivity;
import com.example.yannis.dianming.adapter.ViewPagerAdapter;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import butterknife.InjectView;

/**
 * Created by yannis on 2017/1/10.
 */


public class SignFragment extends BaseFragment {
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.refresh)
    ImageButton refresh;


    private static final String load_courses_url = ConstantValues.GET_ALL_COURSE;
    ;

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
        viewPager.setOffscreenPageLimit(4);
        labels = getResources().getStringArray(R.array.weekdays);
        for (int i = 0; i < labels.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(labels[i]));
        }

    }

    @Override
    public void initView(View view) {
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setVisibility(View.GONE);
                loadData();
            }
        });
    }

    @Override
    public void loadData() {
        String url = load_courses_url + "?teacher_id=" + myApplication.getTeacher_id();
        Util.logHelper(url);
        CommonRequest.createGetRequest(url, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                refreshData(object);
                if (refresh.getVisibility()==View.VISIBLE){
                    refresh.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Object object) {
                if (object.toString().equals("请求失败")) {
                    PushAgent.getInstance(getActivity()).removeAlias(String.valueOf(myApplication
                                    .getTeacher_id()), ConstantValues.teacherID,
                            new UTrack.ICallBack() {
                                @Override
                                public void onMessage(boolean b, String s) {
                                    Util.logHelper(String.valueOf(b) + ":" + s);
                                }
                            });
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                } else {
                    if (refresh.getVisibility() == View.GONE) {
                        refresh.setVisibility(View.VISIBLE);
                    }
                    Util.showToast(getActivity(), object.toString());
                }
            }
        }, Course.class));
    }

    private void refreshData(Object object) {
        allCourses.clear();
        if (object instanceof ArrayList) {
            allCourses = (ArrayList<Course>) object;
        } else {
            Util.logHelper(object.toString() + "succ");
            Util.showToast(getActivity(), object.toString());
            return;
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
        int weekday = i == 1 ? 6 : (i - 2) % 7;
        myApplication.setWeekday(weekday);
        return weekday;
    }
}
