package com.example.yannis.dianming.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.AboutActivity;
import com.example.yannis.dianming.activity.ClassActivity;
import com.example.yannis.dianming.activity.CourseActivity;
import com.example.yannis.dianming.activity.LoginActivity;
import com.example.yannis.dianming.activity.OpenAccountActivity;
import com.example.yannis.dianming.activity.RefreshActivity;
import com.example.yannis.dianming.activity.SetPercentActivity;
import com.example.yannis.dianming.activity.TroubleStudentsActivity;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.SPUtils;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.widget.SettingBar;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

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
    SettingBar setPercent;
    @InjectView(R.id.inputSet)
    SettingBar inputSet;
    @InjectView(R.id.openAccount)
    SettingBar openAccount;
    @InjectView(R.id.myClass)
    SettingBar myClass;
    @InjectView(R.id.mark)
    SettingBar mark;
    @InjectView(R.id.aboutUs)
    SettingBar aboutUs;
    @InjectView(R.id.refreshData)
    SettingBar refreshData;

    private MyApplication myApplication;
    private PushAgent pushAgent;

    @Override
    public int getLayoutID() {
        return R.layout.setting_fragment;
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getActivity().getApplication();
        teacherName.setText(myApplication.getTeacher_name());
        pushAgent = PushAgent.getInstance(getActivity());
    }

    @Override
    public void initView(View view) {
        if (myApplication.getGroup().equals("teacher")) {
            inputSet.setVisibility(View.GONE);
            myClass.setVisibility(View.GONE);
        }
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
            .aboutUs, R.id.refreshData})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                SPUtils.clear(getActivity());
                pushAgent.removeAlias(String.valueOf(myApplication.getTeacher_id()), ConstantValues.teacherID,
                        new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean b, String s) {
                        Util.logHelper(String.valueOf(b) + ":" + s);
                    }
                });
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.setPercent:
                getActivity().startActivity(new Intent(getActivity(), CourseActivity.class));

                break;
            case R.id.inputSet://问题学生推送
                getActivity().startActivity(new Intent(getActivity(), TroubleStudentsActivity.class));
                break;
            case R.id.openAccount:
                getActivity().startActivity(new Intent(getActivity(), OpenAccountActivity.class));
                break;
            case R.id.myClass:
                if (myApplication.getTeacher_id() == 0) {
                    Util.showToast(getActivity(), "请重新登录");
                    return;
                }
                getActivity().startActivity(new Intent(getActivity(), ClassActivity.class));
                break;
            case R.id.mark:
                break;
            case R.id.aboutUs:
                getActivity().startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.refreshData:
                getActivity().startActivity(new Intent(getActivity(), RefreshActivity.class));
                break;
        }
    }
}
