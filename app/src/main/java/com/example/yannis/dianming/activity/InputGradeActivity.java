package com.example.yannis.dianming.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.StudentAdapter;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.Student;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.InjectView;

public class InputGradeActivity extends BaseActivity {

    @InjectView(R.id.stuListview)
    ListView stuListview;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.courseName)
    TextView courseName;
    @InjectView(R.id.title)
    RelativeLayout title;
    @InjectView(R.id.submit)
    Button submit;

    private String work_name;
    private InputGradeActivity activity;
    private MyApplication myApplication;
    private StudentAdapter studentAdapter;
    private ArrayList<Student> students;
    private List<String> grades;
    private String defGrade;


    private int homework_record_id;
    private int course_id;

    private View dialog;
    private AlertDialog.Builder builder;
    private int currentStudent;

    private EditText inputGradeEt;
    private TextView stuName, stuNo;
    private Button last, next;

    private ContentValues contentValues;

    private boolean hasSubmit;

    private ProgressDialog progressDialog;

    @Override
    public int getLayoutID() {
        return R.layout.activity_input_grade;
    }

    @Override
    public void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        stuListview.setAdapter(studentAdapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackAction();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        stuListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initDialog();
                Student student = students.get(position);
                stuName.setText(student.getStudent_name());
                stuNo.setText(student.getStudent_number());
                currentStudent = position;
                inputGradeEt.setHint(contentValues.getAsString(String.valueOf(student.getStudent_id())));
                builder.show();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitGrade();
            }
        });

    }


    private void initDialog() {
        builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = activity.getLayoutInflater();
        dialog = inflater.inflate(R.layout.input_dialog, null);
        inputGradeEt = (EditText) dialog.findViewById(R.id.inputEt);
        stuName = (TextView) dialog.findViewById(R.id.studentName);
        stuNo = (TextView) dialog.findViewById(R.id.studentNo);
        last = (Button) dialog.findViewById(R.id.last);
        next = (Button) dialog.findViewById(R.id.next);
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!save(inputGradeEt.getText().toString())) {
                    return;
                }
                if (currentStudent <= 0) {
                    Util.showToast(activity, "没有上一个");
                    return;
                }
                currentStudent = currentStudent - 1;
                stuName.setText(students.get(currentStudent).getStudent_name());
                stuNo.setText(students.get(currentStudent).getStudent_number());
                inputGradeEt.setText("");
                inputGradeEt.setHint(contentValues.getAsString(String.valueOf(students.get
                        (currentStudent).getStudent_id())));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!save(inputGradeEt.getText().toString())) {
                    return;
                }

                if (currentStudent >= students.size() - 1) {
                    Util.showToast(activity, "没有下一个");
                    return;
                }
                currentStudent = currentStudent + 1;
                stuName.setText(students.get(currentStudent).getStudent_name());
                stuNo.setText(students.get(currentStudent).getStudent_number());
                inputGradeEt.setText("");
                inputGradeEt.setHint(contentValues.getAsString(String.valueOf(students.get
                        (currentStudent).getStudent_id())));
            }
        });
        builder.setNegativeButton("暂停输入", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (save(inputGradeEt.getText().toString())) {
                    dialog.dismiss();
                }
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                refreshGrade();
            }
        });
        builder.setPositiveButton("提交成绩", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (save(inputGradeEt.getText().toString())) {
                    dialog.dismiss();
                    submitGrade();
                }
            }
        });
        builder.setView(dialog);
    }

    private boolean save(String string) {
        if (TextUtils.isEmpty(string)) {
            contentValues.put(String.valueOf(students.get(currentStudent).getStudent_id()),
                    inputGradeEt.getHint().toString());
            grades.set(currentStudent, inputGradeEt.getHint().toString());
        } else {
            if (Integer.parseInt(string) > 100) {
                Util.showToast(activity, "不得超过100分");
                return false;
            }
            contentValues.put(String.valueOf(students.get(currentStudent).getStudent_id()),
                    string);
            grades.set(currentStudent, string);
        }
        return true;
    }

    private void refreshGrade() {
        studentAdapter.grades = grades;
        studentAdapter.notifyDataSetChanged();
    }

    private void submitGrade() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            jsonObject.put("homeworks_record_id", homework_record_id);
            Set<String> keys = contentValues.keySet();
            for (String str : keys) {
                JSONObject item = new JSONObject();
                item.put("student_id", Integer.parseInt(str));
                item.put("score", contentValues.getAsInteger(str));
                array.put(item);
            }
            jsonObject.put("grades", array);
            progressDialog.show();
            CommonRequest.createJsonPostRequest(ConstantValues.GET_HOMEWORK_GRADES, jsonObject.toString(),
                    new CommomHandler(new CommomListener() {


                        @Override
                        public void onSuccess(Object object) {
                            Util.showToast(activity, "成绩提交成功");
                            hasSubmit = true;
                            progressDialog.dismiss();
                            activity.finish();
                        }

                        @Override
                        public void onFailure(Object object) {
                            Util.showToast(activity, "成绩提交失败");
                            progressDialog.dismiss();
                            //activity.finish();
                        }
                    }));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadData() {
        if (!swipeRefreshLayout.isRefreshing()){
            progressDialog.show();
        }
        CommonRequest.createGetRequest(ConstantValues.GET_STUDENT_IN_COURSE + "?" + ConstantValues.courseID
                + "=" + course_id, null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    students = (ArrayList<Student>) object;
                    grades.clear();
                    contentValues.clear();
                    for (int i = 0; i < students.size(); i++) {
                        grades.add(defGrade);
                    }
                    studentAdapter.students = students;
                    studentAdapter.notifyDataSetChanged();
                    for (Student student : students) {
                        contentValues.put(String.valueOf(student.getStudent_id()), defGrade);
                    }
                } else {
                    Util.showToast(activity, "data error");
                }
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, Student.class));

    }

    @Override
    public void initData(Bundle bundle) {
        contentValues = new ContentValues();
        students = new ArrayList<>();
        grades = new ArrayList<>();
        hasSubmit = false;
        homework_record_id = getIntent().getIntExtra(ConstantValues.homeworkRecordId, 0);
        course_id = getIntent().getIntExtra(ConstantValues.courseId, 0);
        work_name = getIntent().getStringExtra(ConstantValues.signName);
        defGrade = getIntent().getStringExtra("default_grade");
        courseName.setText(work_name);
        myApplication = (MyApplication) getApplication();
        activity = this;
        studentAdapter = new StudentAdapter(students, activity, grades);
        currentStudent = 0;
    }

    @Override
    public void onBackPressed() {
        handleBackAction();
    }

    private void handleBackAction() {
        if (!hasSubmit) {
            new AlertDialog.Builder(activity).setTitle("提示").setMessage("若不提交，系统将上传默认分数，用户可再进行修改或删除")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {


                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    submitGrade();
                    dialog.dismiss();
                    activity.finish();
                }
            }).show();
        } else {
            activity.finish();
        }
    }
}
