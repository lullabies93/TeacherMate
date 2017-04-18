package com.example.yannis.dianming.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.HomeworkRecordAdapter;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.HomeworkGrade;
import com.example.yannis.dianming.bean.Student;
import com.example.yannis.dianming.bean.StudentSignDetail;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * Created by yannis on 2017/2/16.
 */

public class HomeworkFragment extends BaseFragment {
    @InjectView(R.id.listview)
    ListView listview;
    private StudentSignDetail student;

    private ArrayList<HomeworkGrade> grades;
    private HomeworkRecordAdapter adapter;

    public void setStudent(StudentSignDetail student) {
        this.student = student;
    }

    @Override
    public int getLayoutID() {
        return R.layout.homework_record_fragment;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(ConstantValues.GET_HOMEWORK_GRADES + "?" + ConstantValues.studentId + "=" + student
                .getStudent_id(), null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    grades = (ArrayList<HomeworkGrade>) object;
                    adapter = new HomeworkRecordAdapter(grades, getActivity());
                    listview.setAdapter(adapter);
                }else {
                    Util.showToast(getActivity(), "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(getActivity(), object.toString());
            }
        }, HomeworkGrade.class));
    }
}
