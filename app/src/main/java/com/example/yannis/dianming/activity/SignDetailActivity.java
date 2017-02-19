package com.example.yannis.dianming.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListPopupWindow;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.adapter.PopAdapter;
import com.example.yannis.dianming.adapter.SignAdapter;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Record;
import com.example.yannis.dianming.bean.StudentSignState;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;
import com.example.yannis.dianming.network.RequestParams;
import com.example.yannis.dianming.widget.Topbar;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignDetailActivity extends BaseActivity {

    @InjectView(R.id.topbar)
    Topbar topbar;
    @InjectView(R.id.course_name)
    TextView courseName;
    @InjectView(R.id.chart)
    PieChart chart;
    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.lists)
    ListView lists;
    @InjectView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private static final String url = APIs.GET_RESULT_BY_RECORD_ID;

    private TextView windowTitle;
    private Button arrive;
    private Button late;
    private Button askforleave;
    private Button absence;
    private String[] popMenuList;

    private SignDetailActivity activity;

    private ListPopupWindow listPopupWindow;
    private PopAdapter popAdapter;


    private int courseId, recordId, selectedStuID, sectionLength;
    private String course_name;

    private ArrayList<Record> signRecords;

    private int[] states;

    private PopupWindow popupWindow;

    private SignAdapter adapter;
    private ArrayList<StudentSignState> students, selectedStudents;

    //private LayoutInflater inflater;


    @Override
    public int getLayoutID() {
        return R.layout.activity_sign_detail;
    }

    @Override
    public void initData(Bundle bundle) {
        //inflater = LayoutInflater.from(this);
        states = new int[4];
        signRecords = new ArrayList<>();
        initChart();
        students = new ArrayList<>();
        selectedStudents = new ArrayList<>();
        activity = this;
        adapter = new SignAdapter(selectedStudents, this, R.layout.sign_list_item);
        lists.setAdapter(adapter);
        recordId = getIntent().getIntExtra(APIs.recordId, 0);
        courseId = getIntent().getIntExtra(APIs.courseId, 0);
        course_name = getIntent().getStringExtra(APIs.courseName);
        sectionLength = getIntent().getIntExtra(APIs.sectionLength, 0);

    }

    private void initChart() {
        chart.setTransparentCircleAlpha(110);
        PieData data = getData();
        chart.setHoleColorTransparent(true);
        data.setDrawValues(false);
        //chart.setHoleRadius(30f);  //半径
        //chart.setTransparentCircleRadius(64f); // 半透明圈
        chart.setHoleRadius(0);  //实心圆
        chart.setDrawCenterText(false);
        chart.setDrawHoleEnabled(false);
        chart.setDrawSliceText(false);
        chart.setUsePercentValues(true);
        chart.setRotationAngle(90); // 初始旋转角度
        chart.setDescription("");
        chart.setRotationEnabled(true); // 可以手动旋转
        chart.setUsePercentValues(false);  //显示成百分比
        chart.setCenterText("");  //饼状图中间的文字
        //设置数据
        chart.setData(data);

        Legend mLegend = chart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        chart.animateXY(1000, 1000);  //设置动画
    }

    private PieData getData() {
        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        xValues.add("已到");
        xValues.add("迟到");
        xValues.add("请假");
        xValues.add("缺席");
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        yValues.add(new Entry(25, 0));
        yValues.add(new Entry(25, 1));
        yValues.add(new Entry(25, 2));
        yValues.add(new Entry(25, 3));
        PieDataSet pieDataSet = new PieDataSet(yValues, " "/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色

        colors.add(Color.rgb(97, 221, 124));
        colors.add(Color.rgb(255, 211, 55));
        colors.add(Color.rgb(75, 197, 224));
        colors.add(Color.rgb(218, 108, 108));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);

        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }


    private void refreshChart() {
        states = new int[4];
        for (StudentSignState studentSignState : students) {
            ++states[studentSignState.getStatus()];
        }
        PieData data = chart.getData();
        PieDataSet dataSet = data.getDataSetByIndex(0);
        Entry entry1 = dataSet.getEntryForXIndex(0);
        entry1.setVal((float) (states[0] * 100 / students.size()));
        Entry entry2 = dataSet.getEntryForXIndex(1);
        entry2.setVal((float) (states[1] * 100 / students.size()));
        Entry entry3 = dataSet.getEntryForXIndex(2);
        entry3.setVal((float) (states[2] * 100 / students.size()));
        Entry entry4 = dataSet.getEntryForXIndex(3);
        entry4.setVal((float) (states[3] * 100 / students.size()));

        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    @Override
    public void initViews() {
        initPopupWindow();
        initPopupListWindow();
        courseName.setText(course_name);
        String[] labels = getResources().getStringArray(R.array.sign_status);
        for (int i = 0; i < labels.length; i++) {
            tabs.addTab(tabs.newTab().setText(labels[i]));

        }


        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                refreshListview();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        topbar.setOnTopBarClickListener(new Topbar.TopBarClickListener() {
            @Override
            public void leftClick() {
                activity.finish();
            }

            @Override
            public void right1Click() {//补课点名
                Intent intent = new Intent(activity, SignActivity.class);
                intent.putExtra(APIs.courseID, courseId);
                intent.putExtra(APIs.courseName, course_name);
                intent.putExtra(APIs.sectionLength, sectionLength);
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void right2Click() {//点名记录
                popupMenu();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popupWindow == null) {
                    initPopupWindow();
                }
                selectedStuID = selectedStudents.get(position).getAttendance_result_id();
                windowTitle.setText("将  " + selectedStudents.get(position).getStudent_name() + "  的签到状态改为  " +
                        "?");
                popupWindow.showAtLocation(activity.findViewById(R.id.activity_sign_detail), Gravity.BOTTOM |
                        Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

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
                if (listPopupWindow.isShowing()) {
                    listPopupWindow.dismiss();
                }
                recordId = signRecords.get(position).getAttendance_record_id();
                loadData();
            }
        });
    }

    private void popupMenu() {
        //listPopupWindow.show();
        CommonRequest.createGetRequest(APIs.GET_RECORD_ID + "?" + APIs.courseId + "=" + courseId, null, new
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
//        popMenuList = new String[signRecords.size()];
//        Record record = null;
//        int size = signRecords.size();
//        for (int i = 0; i < size; i ++){
//            record = signRecords.get(i);
//            popMenuList[i] = record.getName();
//        }
//        //popAdapter.
//        popAdapter.notifyDataSetChanged();
//        listPopupWindow.show();

        popAdapter.records = signRecords;
        popAdapter.notifyDataSetChanged();
        listPopupWindow.show();
    }


    private void initPopupWindow() {
        if (popupWindow == null) {
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.popup_layout, null);
            windowTitle = (TextView) layout.findViewById(R.id.window_title);
            arrive = (Button) layout.findViewById(R.id.arrive);
            late = (Button) layout.findViewById(R.id.late);
            askforleave = (Button) layout.findViewById(R.id.askforleave);
            absence = (Button) layout.findViewById(R.id.absence);
            popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());

            //添加按键事件监听
            setMenuListeners(layout);
        }
    }

    private void setMenuListeners(LinearLayout layout) {

        arrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSignStatus(selectedStuID, 0);
            }
        });
        late.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSignStatus(selectedStuID, 1);
            }
        });
        askforleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSignStatus(selectedStuID, 2);
            }
        });
        absence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSignStatus(selectedStuID, 3);
            }
        });


    }

    private void changeSignStatus(int selectedStuID, int i) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        if (tabs.getSelectedTabPosition() == i) {
            Util.showToast(activity, "已处于此状态");
            return;
        } else {
            final JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            try {
                jsonObject.put(APIs.resultId, selectedStuID);
                jsonObject.put(APIs.status, i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(jsonObject);
            CommonRequest.createPutRequest(APIs.POST_SIGN_RESULT, array.toString(), new CommomHandler(new CommomListener() {
                @Override
                public void onSuccess(Object object) {
                    try {
                        JSONObject ret = new JSONObject(String.valueOf(object));
                        if (ret.getInt(APIs.status) == 1) {
                            loadData();
                        } else {
                            Util.showToast(activity, ret.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Util.logHelper(String.valueOf(object) + "--success");
                }

                @Override
                public void onFailure(Object object) {
                    Util.logHelper(String.valueOf(object) + "--failure");
                }
            }));
        }
    }

    private void refreshListview() {
        int position = tabs.getSelectedTabPosition();
        selectedStudents.clear();
        for (StudentSignState studentSignState : students) {
            if (studentSignState.getStatus() == position) {
                selectedStudents.add(studentSignState);
            }
        }
        adapter.refresh(selectedStudents);
    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(url + "?" + APIs.ret_recordId + "=" + recordId, null, new
                CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                Util.logHelper(String.valueOf(object) + "success");
                if (object instanceof List) {
                    students = (ArrayList<StudentSignState>) object;
                    if (students.size() != 0){
                        refreshListview();
                        refreshChart();
                    }else {
                        Util.showToast(activity, "此次点名记录为空");
                    }

                } else {
                    Util.logHelper(String.valueOf(object) + "data error");
                    Util.showToast(activity, "data error");
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.logHelper(String.valueOf(object) + "failure");
            }
        }, StudentSignState.class));
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
