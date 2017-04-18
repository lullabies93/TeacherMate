package com.example.yannis.dianming.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.WithTitleAdapter;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.DialogListener;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.ShowDialogUtil;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.HomeworkRecord;
import com.example.yannis.dianming.bean.HomeworkType;
import com.example.yannis.dianming.bean.HomeworkWithTitle;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeworkActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.courseName)
    TextView courseName;
    @InjectView(R.id.addHomework)
    TextView addHomework;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.listview)
    ListView listview;


    private String course_name;
    private int course_id;
    private HomeworkActivity activity;
    private ArrayList<HomeworkType> classes;

    private Course course;

    private MyApplication myApplication;

    private TextView courseTv, typeTv;//创建作业对话框中的view

    private AlertDialog.Builder builder;
    private View dialog;
    private String defGrade;

    private WithTitleAdapter adapter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_homework;
    }

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        myApplication = (MyApplication) getApplication();
        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra(ConstantValues.courseInfo);
        course_name = course.getCourse_name();
        course_id = course.getCourse_id();
        courseName.setText(course_name);
        classes = new ArrayList<>();
        datas = new ArrayList<>();
        adapter = new WithTitleAdapter(activity, datas);
        adapter.setOnDeleteClickListener(new WithTitleAdapter.onDeleteClickListener() {
            @Override
            public void onDeleteClick(String msg, int id) {
                deleteRecord(msg, id);
            }
        });
        listview.setAdapter(adapter);
    }

    private void deleteRecord(String msg, final int position) {
        ShowDialogUtil.showConfirmDialog(activity, msg, new DialogListener() {
            @Override
            public void onDialogClick(Object ret) {
                deleteHomework(position);
            }
        });
    }

    private void deleteHomework(int id) {
        JSONArray array = new JSONArray();
        array.put(id);
        CommonRequest.createDeleteRequest(ConstantValues.GET_HOMEWORK_RECORDS, array.toString(), new
                CommomHandler(new CommomListener() {

            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(object.toString());
                    Util.logHelper(ret.toString());
                    if (ret.getInt(ConstantValues.status) == 1) {
                        Util.showToast(activity, "删除成功");
                        loadData();
                    } else {
                        Util.showToast(activity, "删除失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {

            }
        }));
    }


    @Override
    public void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        addHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加作业
                addHomework();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.logHelper(adapter.isEnabled(position) + "");
                if (adapter.isEnabled(position)) {
                    Intent intent = new Intent(activity, HomeworkDetailActivity.class);
                    intent.putExtra(ConstantValues.homeworkItem, (HomeworkRecord) adapter.getItem(position));
                    activity.startActivity(intent);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


    }

    private void getHomeworkRecordId(final String result) {
        JSONObject jsonObject = null;
        String name = "";
        try {
            jsonObject = new JSONObject(result);
            name = jsonObject.getString("name");
            //     先判断该名字是否已存在
            for (HomeworkWithTitle inst : datas) {
                List<HomeworkRecord> records = inst.getDatas();
                for (HomeworkRecord record : records) {
                    if (record.getName().equals(name)) {
                        Util.showToast(activity, "该名字已存在，请重试");
                        return;
                    }
                }

            }
            defGrade = jsonObject.getString("grade");
            jsonObject.remove("grade");
            jsonObject.put(ConstantValues.week, myApplication.getWeek());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String finalName = name;
        CommonRequest.createJsonPostRequest(ConstantValues.GET_HOMEWORK_RECORDS, jsonObject.toString(), new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                Util.logHelper(object.toString());
                try {
                    JSONObject ret = new JSONObject(object.toString());
                    if (ret.getInt(ConstantValues.status) == 1) {
                        Util.showToast(activity, "新建作业成功");
                        int id = ret.getInt(ConstantValues.homeworkRecordId);
                        Intent intent = new Intent();
                        intent.putExtra(ConstantValues.homeworkRecordId, id);
                        intent.putExtra(ConstantValues.signName, finalName);
                        intent.putExtra(ConstantValues.courseId, course_id);
                        intent.putExtra("default_grade", defGrade);
                        intent.setClass(activity, InputGradeActivity.class);
                        activity.startActivity(intent);
                    } else {
                        Util.showToast(activity, "新建作业失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
            }
        }));
    }


    private void addHomework() {
        if (classes == null || classes.size() <= 0) {
            intentToAddType();
            return;
        }
        ShowDialogUtil.showAddHomeworkDialog(activity, classes, new DialogListener<String>() {
            @Override
            public void onDialogClick(String ret) {
                getHomeworkRecordId(ret);
            }
        });
    }

    private void intentToAddType() {
        ShowDialogUtil.showTipDialog(activity, new DialogListener() {
            @Override
            public void onDialogClick(Object ret) {
                Intent intent = new Intent(activity, SetPercentActivity.class);
                intent.putExtra(ConstantValues.courseInfo, course);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void loadData() {//获取已有作业记录
        if (!swipeRefreshLayout.isRefreshing()) {
            showDialog();
        }
        CommonRequest.createGetRequest(ConstantValues.GET_HOMEWORK_CLASSES + "?" + ConstantValues
                .courseId + "=" + course_id, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    datas.clear();
                    classes = (ArrayList<HomeworkType>) object;
                    if (classes.size() > 0) {
                        getRecords();
                    } else {
                        dismissDialog();
                    }
                } else {
                    Util.showToast(activity, object.toString());
                    dismissDialog();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
                dismissDialog();
            }
        }, HomeworkType.class));
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private ArrayList<HomeworkWithTitle> datas;

    private void getRecords() {
        for (HomeworkType type : classes) {
            final HomeworkWithTitle item = new HomeworkWithTitle(type.getName(), null);
            CommonRequest.createGetRequest(ConstantValues.GET_HOMEWORK_RECORDS + "?" + ConstantValues
                    .homeworkClassId + "=" + type.getHomework_class_id(), null, new CommomHandler(new CommomListener() {
                @Override
                public void onSuccess(Object object) {
                    if (object instanceof ArrayList) {
                        item.setDatas((List<HomeworkRecord>) object);
                        datas.add(item);
                        if (datas.size() == classes.size()) {
                            adapter.datas = datas;
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Object object) {
                    Util.showToast(activity, object.toString());
                }
            }, HomeworkRecord.class));
        }
        dismissDialog();
    }

}
