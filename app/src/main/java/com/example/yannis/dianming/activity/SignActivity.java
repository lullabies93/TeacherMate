package com.example.yannis.dianming.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.PopAdapter;
import com.example.yannis.dianming.adapter.SignListAdapter;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.DialogListener;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.ShowDialogUtil;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Record;
import com.example.yannis.dianming.bean.Student;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;
import com.example.yannis.dianming.widget.Topbar;
import com.example.yannis.dianming.widget.swipemenulistview.SwipeMenu;
import com.example.yannis.dianming.widget.swipemenulistview.SwipeMenuCreator;
import com.example.yannis.dianming.widget.swipemenulistview.SwipeMenuItem;
import com.example.yannis.dianming.widget.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.InjectView;

public class SignActivity extends BaseActivity {


    @InjectView(R.id.activity_sign)
    RelativeLayout activitySign;
    @InjectView(R.id.courseName)
    TextView courseName;
    @InjectView(R.id.desc)
    TextView desc;

    @InjectView(R.id.topbar)
    Topbar topbar;
    @InjectView(R.id.submit)
    Button submit;
    @InjectView(R.id.title)
    RelativeLayout title;
    @InjectView(R.id.recommendListview)
    SwipeMenuListView recommendListview;
    @InjectView(R.id.commomListview)
    SwipeMenuListView commomListview;

    //private SignAdapter signAdapter;
    private SignListAdapter recommendAdapter, commomAdapter;
    private ArrayList<Student> recommendStudents, commomStudents, allStudents;
    private int courseID, sectionLength, recordId;
    private String url = "";
    private String course_name, date, sign_name;

    private ListPopupWindow listPopupWindow;
    private PopAdapter popAdapter;
    private ArrayList<Record> signRecords;

    private MyApplication myApplication;

    private SignActivity activity;

    private int weekday;

    @Override
    public int getLayoutID() {
        return R.layout.activity_sign;
    }

    @Override
    public void initViews() {
        commomAdapter = new SignListAdapter(commomStudents, SignActivity.this);
        recommendAdapter = new SignListAdapter(recommendStudents, SignActivity.this);

        initSwipeListview(recommendListview, 0);
        initSwipeListview(commomListview, 1);

        recommendListview.setAdapter(recommendAdapter);
        commomListview.setAdapter(commomAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        initPopupListWindow();//初始化记录窗口


        topbar.setOnTopBarClickListener(new Topbar.TopBarClickListener() {
            @Override
            public void leftClick() {
                SignActivity.this.finish();
            }

            @Override
            public void right1Click() {
                topbar.findViewById(R.id.rightTv1_id).setVisibility(View.GONE);
            }

            @Override
            public void right2Click() {

                popupMenu();

            }
        });
    }

    private void popupMenu() {
        CommonRequest.createGetRequest(ConstantValues.GET_RECORD_ID + "?" + ConstantValues.courseId + "=" +
                courseID, null, new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    Util.logHelper(object.toString() + "---success");
                    signRecords = (ArrayList<Record>) object;
                    if (signRecords.size() == 0) {
                        Util.showToast(activity, "无点名记录");
                    } else {
                        refreshMenu();

                    }
                } else {
                    Util.showToast(activity, "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.logHelper(object.toString() + "---failure");
            }
        }, Record.class));
    }

    private void refreshMenu() {
        popAdapter.records = signRecords;
        popAdapter.notifyDataSetChanged();
        listPopupWindow.show();
    }

    private void initPopupListWindow() {
        listPopupWindow = new ListPopupWindow(this);
        popAdapter = new PopAdapter(signRecords, this);
        listPopupWindow.setAdapter(popAdapter);
        listPopupWindow.setWidth(300);
        listPopupWindow.setHeight(300);
        listPopupWindow.setModal(true);

        listPopupWindow.setAnchorView(topbar.findViewById(R.id.rightTv2_id));
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listPopupWindow.isShowing()) {
                    listPopupWindow.dismiss();
                }
                recordId = signRecords.get(position).getAttendance_record_id();
                //跳转到detail页面
                Intent intent = new Intent(SignActivity.this, SignDetailActivity.class);
                intent.putExtra(ConstantValues.courseId, courseID);
                intent.putExtra(ConstantValues.recordId, recordId);
                intent.putExtra(ConstantValues.courseName, course_name);
                intent.putExtra(ConstantValues.sectionLength, sectionLength);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initSwipeListview(SwipeMenuListView listview, final int flag) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                menu.addMenuItem(createMenuItem(new ColorDrawable(Color.rgb(0x11, 0xFF,
                        0x11)), "已到"));
                menu.addMenuItem(createMenuItem(new ColorDrawable(Color.rgb(0x00, 0xB2,
                        0xEE)), "请假"));
                menu.addMenuItem(createMenuItem(new ColorDrawable(Color.rgb(0xFF, 0x40,
                        0x40)), "缺席"));
            }
        };
        // set creator
        listview.setMenuCreator(creator);

        listview.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                // TODO Auto-generated method stub
                if (flag == 0) {
                    recommendAdapter.students.get(position).setStatus(index);
                    //recommendAdapter.students = recommendStudents;
                    recommendAdapter.notifyDataSetChanged();
                }
                if (flag == 1) {
                    commomAdapter.students.get(position).setStatus(index);
                    // commomAdapter.students = commomStudents;
                    commomAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    private SwipeMenuItem createMenuItem(ColorDrawable colorDrawable, String s) {
        SwipeMenuItem item = new SwipeMenuItem(getApplicationContext());
        // set item background
        item.setBackground(colorDrawable);
        // set item width
        item.setWidth(dp2px(70));
        item.setTitle(s);
        item.setTitleSize(18);
        // set item title font color
        item.setTitleColor(Color.WHITE);
        return item;
    }

    private int dp2px(int i) {
        return Util.dp2px(SignActivity.this, i);
    }

    private void submit() {
        showTheDialog();
    }

    private void showTheDialog() {
        ShowDialogUtil.showAddMarkDialog(activity, weekday, myApplication.getWeekday(), new
                DialogListener<String>() {


            @Override
            public void onDialogClick(String ret) {
                isNameUnique(ret);
            }
        });
    }

    private boolean isNameUnique = true;

    private void isNameUnique(final String sign_name) {
        CommonRequest.createGetRequest(ConstantValues.GET_RECORD_ID + "?" + ConstantValues.courseId + "=" +
                courseID, null, new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    Util.logHelper(object.toString() + "---success");
                    signRecords = (ArrayList<Record>) object;
                    Util.logHelper(String.valueOf(signRecords.size()));
                    if (signRecords.size() == 0) {
                        isNameUnique = true;
                        getRecordId(sign_name);
                    } else {
                        for (Record record : signRecords) {
                            if (record.getName().equals(sign_name)) {
                                Util.logHelper(record.getName());
                                isNameUnique = false;
                            }
                        }
                        if (isNameUnique) {
                            getRecordId(sign_name);
                        } else {
                            Util.showToast(activity, "该名字已存在，请重新输入");
                            isNameUnique = true;
                        }
                    }
                } else {
                    Util.showToast(activity, "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, object.toString());
                Util.logHelper(object.toString() + "---failure");
            }
        }, Record.class));
    }

    private void getRecordId(String name) {
        JSONObject param = new JSONObject();
        try {
            param.put(ConstantValues.courseId, courseID);
            param.put(ConstantValues.signName, name);
            param.put(ConstantValues.sectionLength, sectionLength);
            param.put(ConstantValues.week, myApplication.getWeek());
            param.put(ConstantValues.weekday, weekday);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Util.logHelper(param.toString());
        CommonRequest.createJsonPostRequest(ConstantValues.GET_RECORD_ID, param.toString(), new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(object));
                    if (jsonObject.getInt(ConstantValues.status) == 1) {
                        recordId = jsonObject.getInt(ConstantValues.recordId);
                        submitSignResult();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(SignActivity.this, String.valueOf(object));
            }
        }));
    }

    private void submitSignResult() {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject item = null;
        recommendStudents = recommendAdapter.students;
        commomStudents = commomAdapter.students;
        try {
            for (Student student : recommendStudents) {
                item = new JSONObject();
                item.put(ConstantValues.studentId, student.getStudent_id());
                switch (student.getStatus()) {
                    case 0:
                        item.put(ConstantValues.status, 0);
                        break;
                    case 1:
                        item.put(ConstantValues.status, 2);
                        break;
                    case 2:
                        item.put(ConstantValues.status, 3);
                }
                array.put(item);
            }
            for (Student student : commomStudents) {
                item = new JSONObject();
                item.put(ConstantValues.studentId, student.getStudent_id());
                switch (student.getStatus()) {
                    case 0:
                        item.put(ConstantValues.status, 0);
                        break;
                    case 1:
                        item.put(ConstantValues.status, 2);
                        break;
                    case 2:
                        item.put(ConstantValues.status, 3);
                }
                array.put(item);
            }
            result.put(ConstantValues.ret_recordId, recordId);
            result.put(ConstantValues.sign_result, array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (recordId == 0) {
            return;
        }
        Util.logHelper(result.toString());


        CommonRequest.createJsonPostRequest(ConstantValues.POST_SIGN_RESULT, result.toString(), new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(object));
                    if (jsonObject.getInt(ConstantValues.status) == 1) {
                        Util.showToast(SignActivity.this, "submit success");
                        Intent intent = new Intent(SignActivity.this, SignDetailActivity.class);
                        intent.putExtra(ConstantValues.courseId, courseID);
                        intent.putExtra(ConstantValues.recordId, recordId);
                        intent.putExtra(ConstantValues.courseName, course_name);
                        intent.putExtra(ConstantValues.sectionLength, sectionLength);
                        intent.putExtra(ConstantValues.weekday, weekday);
                        intent.putExtra("sign_nickname", sign_name);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(SignActivity.this, String.valueOf(object));
            }
        }));
    }

    @Override
    public void loadData() {
        getStudentsInCourse();
    }

    private void getStudentsInCourse() {
        allStudents.clear();
        recommendStudents.clear();
        commomStudents.clear();
        url = ConstantValues.GET_STUDENT_IN_COURSE + "?" + ConstantValues.courseID + "=" + courseID;
        CommonRequest.createGetRequest(url, null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    allStudents = (ArrayList<Student>) object;
                    if (allStudents.size() <= 3) {
                        recommendStudents = allStudents;
                    } else {
                        recommendStudents.add(allStudents.get(0));
                        recommendStudents.add(allStudents.get(1));
                        recommendStudents.add(allStudents.get(2));
                        allStudents.remove(0);
                        allStudents.remove(0);
                        allStudents.remove(0);
                        commomStudents = allStudents;
                    }
                    commomAdapter.students = commomStudents;
                    recommendAdapter.students = recommendStudents;
                    commomAdapter.notifyDataSetChanged();
                    recommendAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(SignActivity.this, String.valueOf(object));
            }
        }, Student.class));
    }

    @Override
    public void initData(Bundle bundle) {
        activity = this;
        signRecords = new ArrayList<>();
        myApplication = (MyApplication) getApplication();
        Intent intent = getIntent();
        courseID = intent.getIntExtra(ConstantValues.courseID, -1);
        course_name = intent.getStringExtra(ConstantValues.courseName);
        courseName.setText(course_name);
        sectionLength = intent.getIntExtra(ConstantValues.sectionLength, 0);
        recommendStudents = new ArrayList<Student>();
        commomStudents = new ArrayList<Student>();
        allStudents = new ArrayList<Student>();
        date = Util.getDate(SignActivity.this);
        desc.setText("创建于" + date);
        weekday = intent.getIntExtra(ConstantValues.weekday, -1);
    }
}
