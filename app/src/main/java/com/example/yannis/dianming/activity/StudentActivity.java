package com.example.yannis.dianming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.StudentDetailAdapter;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.StudentSignDetail;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;

import butterknife.InjectView;

public class StudentActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.classNumber)
    TextView classNumber;
    @InjectView(R.id.stuListview)
    ListView stuListview;

    private StudentActivity activity;

    private StudentDetailAdapter studentAdapter;

    private MyApplication myApplication;

    private ArrayList<StudentSignDetail> students;

    private String class_number;


    @Override
    public int getLayoutID() {
        return R.layout.activity_student;
    }

    @Override
    public void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        stuListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, StudentDetailActivity.class);
                intent.putExtra(ConstantValues.studentInfo, students.get(position));
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(ConstantValues.GET_STUDENT_IN_COURSE + "?" + ConstantValues.classNumber + "=" +
                class_number+"&fields=attendance_detail", null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    students = (ArrayList<StudentSignDetail>) object;
                    studentAdapter.studentSignDetails = students;
                    studentAdapter.notifyDataSetChanged();
                }else {
                    Util.showToast(activity, "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.logHelper(object.toString());
            }
        }, StudentSignDetail.class));
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getApplication();
        activity = this;
        students = new ArrayList<>();
        class_number = getIntent().getStringExtra(ConstantValues.classNumber);
        classNumber.setText(class_number);
        studentAdapter = new StudentDetailAdapter(students, activity);
        stuListview.setAdapter(studentAdapter);
    }

}
