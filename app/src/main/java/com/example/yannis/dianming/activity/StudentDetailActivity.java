package com.example.yannis.dianming.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.PagerAdapter;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.BaseFragment;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.bean.Student;
import com.example.yannis.dianming.bean.StudentSignDetail;
import com.example.yannis.dianming.fragments.HomeworkFragment;
import com.example.yannis.dianming.fragments.SignRecordFragment;

import java.util.ArrayList;

import butterknife.InjectView;

public class StudentDetailActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;

    @InjectView(R.id.studentName)
    TextView studentName;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.studentMajor)
    TextView studentMajor;
    @InjectView(R.id.studentClass)
    TextView studentClass;
    @InjectView(R.id.studentNumber)
    TextView studentNumber;
    @InjectView(R.id.radioButton1)
    RadioButton radioButton1;
    @InjectView(R.id.radioButton2)
    RadioButton radioButton2;
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    private StudentDetailActivity activity;
    private MyApplication myApplication;
    private StudentSignDetail studentInfo;

    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private PagerAdapter adapter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_student_detail;
    }

    @Override
    public void initViews() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radioButton2:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    radioGroup.check(R.id.radioButton1);
                }else {
                    radioGroup.check(R.id.radioButton2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        SignRecordFragment fragment = new SignRecordFragment();
        fragment.setLabel(studentInfo);
        HomeworkFragment homeworkFragment = new HomeworkFragment();
        homeworkFragment.setStudent(studentInfo);
        fragments.add(fragment);
        fragments.add(homeworkFragment);
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        myApplication = (MyApplication) getApplication();
        studentInfo = (StudentSignDetail) getIntent().getSerializableExtra(ConstantValues.studentInfo);
        studentName.setText(studentInfo.getStudent_name());
        studentNumber.setText("学号："+studentInfo.getStudent_number());
        studentClass.setText("班级："+studentInfo.getClass_number());
        studentMajor.setText("专业："+studentInfo.getMajor());
    }

}
