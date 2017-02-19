package com.example.yannis.dianming.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.ClassActivity;
import com.example.yannis.dianming.activity.OpenAccountActivity;
import com.example.yannis.dianming.activity.SetPercentActivity;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.MyApplication;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by yannis on 2017/1/10.
 */

public class SettingFragment extends BaseFragment {
    @InjectView(R.id.teacherName)
    TextView teacherName;
    @InjectView(R.id.logout)
    TextView logout;
    @InjectView(R.id.teacherInfo)
    LinearLayout teacherInfo;
    @InjectView(R.id.setPercent)
    LinearLayout setPercent;
    @InjectView(R.id.inputSet)
    LinearLayout inputSet;
    @InjectView(R.id.openAccount)
    LinearLayout openAccount;
    @InjectView(R.id.myClass)
    LinearLayout myClass;
    @InjectView(R.id.mark)
    LinearLayout mark;
    @InjectView(R.id.aboutUs)
    LinearLayout aboutUs;

    private MyApplication myApplication;

    @Override
    public int getLayoutID() {
        return R.layout.setting_fragment;
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getActivity().getApplication();
        teacherName.setText(myApplication.getTeacher_name());
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void loadData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.logout, R.id.setPercent, R.id.inputSet, R.id.openAccount, R.id.myClass, R.id.mark, R.id
            .aboutUs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                break;
            case R.id.setPercent:
                getActivity().startActivity(new Intent(getActivity(), SetPercentActivity.class));

                break;
            case R.id.inputSet:
                break;
            case R.id.openAccount:
                getActivity().startActivity(new Intent(getActivity(), OpenAccountActivity.class));
                break;
            case R.id.myClass:
                getActivity().startActivity(new Intent(getActivity(), ClassActivity.class));
                break;
            case R.id.mark:
                break;
            case R.id.aboutUs:
                break;
        }
    }
}
