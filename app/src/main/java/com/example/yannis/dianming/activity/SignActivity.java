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
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.MyApplication;
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

import butterknife.ButterKnife;
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
    private String course_name, date;

    private ListPopupWindow listPopupWindow;
    private PopAdapter popAdapter;
    private ArrayList<Record> signRecords;

    private MyApplication myApplication;

    private SignActivity activity;

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

        topbar.findViewById(R.id.rightTv1_id).setVisibility(View.GONE);


        topbar.setOnTopBarClickListener(new Topbar.TopBarClickListener() {
            @Override
            public void leftClick() {
                SignActivity.this.finish();
            }

            @Override
            public void right1Click() {

            }

            @Override
            public void right2Click() {

                popupMenu();

            }
        });
    }

    private void popupMenu() {
        CommonRequest.createGetRequest(APIs.GET_RECORD_ID + "?" + APIs.courseId + "=" + courseID, null, new
                CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    Util.logHelper(object.toString()+"---success");
                    signRecords = (ArrayList<Record>) object;
                    if (signRecords.size() == 0){
                        Util.showToast(activity, "无点名记录");
                    }else{
                        refreshMenu();

                    }
                }else {
                    Util.showToast(activity, "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.logHelper(object.toString()+"---failure");
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
        listPopupWindow.setHeight(400);
        listPopupWindow.setModal(true);

        listPopupWindow.setAnchorView(topbar.findViewById(R.id.rightTv2_id));
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listPopupWindow.isShowing()){
                    listPopupWindow.dismiss();
                }
                recordId = signRecords.get(position).getAttendance_record_id();
                //跳转到detail页面
                Intent intent  = new Intent(SignActivity.this, SignDetailActivity.class);
                intent.putExtra(APIs.courseId, courseID);
                intent.putExtra(APIs.recordId, recordId);
                intent.putExtra(APIs.courseName, course_name);
                intent.putExtra(APIs.sectionLength, sectionLength);
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
                menu.addMenuItem(createMenuItem(new ColorDrawable(Color.rgb(0x11, 0x11,
                        0xFF)), "请假"));
                menu.addMenuItem(createMenuItem(new ColorDrawable(Color.rgb(0xFF, 0x11,
                        0x11)), "缺席"));
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
        showDialog();


    }

    private void showDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View   dialog = inflater.inflate(R.layout.dialog,(ViewGroup) findViewById(R.id.dialog));
        final EditText   editText = (EditText) dialog.findViewById(R.id.et);
        editText.setHint(date);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("请输入备注");
        builder.setMessage(null);
        builder.setView(dialog);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                getRecordId(editText.getText().toString());
            }
        });
        builder.show();
    }

    private void getRecordId(String name) {
        JSONObject param = new JSONObject();
        try {
            param.put(APIs.courseId, courseID);
            param.put(APIs.signName, name);
            param.put(APIs.sectionLength, sectionLength);
            param.put(APIs.week, myApplication.getWeek());
            param.put(APIs.weekday, myApplication.getWeekday());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonRequest.createJsonPostRequest(APIs.GET_RECORD_ID, param.toString(), new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(object));
                    if (jsonObject.getInt(APIs.status) == 1) {
                        recordId = jsonObject.getInt(APIs.recordId);
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
                item.put(APIs.studentId, student.getStudent_id());
                switch (student.getStatus()){
                    case 0:
                        item.put(APIs.status, 0);
                        break;
                    case 1:
                        item.put(APIs.status, 2);
                        break;
                    case 2:
                        item.put(APIs.status, 3);
                }
                array.put(item);
            }
            for (Student student : commomStudents) {
                item = new JSONObject();
                item.put(APIs.studentId, student.getStudent_id());
                switch (student.getStatus()){
                    case 0:
                        item.put(APIs.status, 0);
                        break;
                    case 1:
                        item.put(APIs.status, 2);
                        break;
                    case 2:
                        item.put(APIs.status, 3);
                }
                array.put(item);
            }
            result.put(APIs.ret_recordId, recordId);
            result.put(APIs.sign_result, array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (recordId == 0){
            return;
        }
        Util.logHelper(result.toString());


        CommonRequest.createJsonPostRequest(APIs.POST_SIGN_RESULT, result.toString(), new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(object));
                    if (jsonObject.getInt(APIs.status)==1){
                        Util.showToast(SignActivity.this, "submit success");
                        Intent intent  = new Intent(SignActivity.this, SignDetailActivity.class);
                        intent.putExtra(APIs.courseId, courseID);
                        intent.putExtra(APIs.recordId, recordId);
                        intent.putExtra(APIs.courseName, course_name);
                        intent.putExtra(APIs.sectionLength, sectionLength);
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
        url = APIs.GET_STUDENT_IN_COURSE + "?" + APIs.courseID + "=" + courseID;
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
        courseID = getIntent().getIntExtra(APIs.courseID, -1);
        course_name = getIntent().getStringExtra(APIs.courseName);
        courseName.setText(course_name);
        sectionLength = getIntent().getIntExtra(APIs.sectionLength, 0);
        recommendStudents = new ArrayList<Student>();
        commomStudents = new ArrayList<Student>();
        allStudents = new ArrayList<Student>();
        date = Util.getDate(SignActivity.this);
        desc.setText("创建于"+date);
    }
}
