package com.example.yannis.dianming.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class SetPercentActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.courseEditView)
    EditText courseEditView;
    @InjectView(R.id.chart)
    PieChart chart;
    @InjectView(R.id.seekbar)
    AppCompatSeekBar seekbar;
    @InjectView(R.id.punishEdit)
    EditText punishEdit;
    @InjectView(R.id.submit)
    Button submit;

    private MyApplication myApplication;
    private ArrayList<Course> courses;
    private String[] courseNames;
    private SetPercentActivity activity;

    private NumberPickerView pickerView;
    private int signPercentage, homeworkPercentage, courseId;

    @Override
    public int getLayoutID() {
        return R.layout.activity_set_percent;
    }

    @Override
    public void initViews() {
        initChart();
        seekbar.setMax(100);
        seekbar.setProgress(50);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateChart(seekBar.getProgress());
            }
        });

    }


    private void initChart() {
        chart.setTransparentCircleAlpha(110);
        PieData data = getData();
        data.setDrawValues(true);

        chart.setHoleColorTransparent(true);
        data.setDrawValues(true);
        data.setValueTextSize(18f);
        data.setValueTextColor(getResources().getColor(R.color.white));
        //chart.setHoleRadius(30f);  //半径
        //chart.setTransparentCircleRadius(64f); // 半透明圈
        chart.setHoleRadius(0);  //实心圆
        chart.setDrawCenterText(false);
        chart.setDrawHoleEnabled(false);
        chart.setDrawSliceText(false);
        chart.setRotationAngle(90); // 初始旋转角度
        chart.setDescription("");
        chart.setRotationEnabled(true); // 可以手动旋转
        chart.setUsePercentValues(true);  //显示成百分比
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
        xValues.add("考勤");
        xValues.add("作业");
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        yValues.add(new Entry(50, 0));
        yValues.add(new Entry(50, 1));
        PieDataSet pieDataSet = new PieDataSet(yValues, " "/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色

        colors.add(Color.rgb(97, 221, 124));
        colors.add(Color.rgb(255, 211, 55));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);

        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }

    private void updateChart(int progress) {
        PieData data = chart.getData();
        int count = data.getDataSetCount();
        PieDataSet dataSet = data.getDataSetByIndex(0);
        Entry entry1 = dataSet.getEntryForXIndex(0);
        entry1.setVal(progress);
        Entry entry2 = dataSet.getEntryForXIndex(1);
        entry2.setVal(100-progress);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    @Override
    public void loadData() {
        CommonRequest.createGetRequest(APIs.GET_ALL_COURSE + "?" + APIs.teacherID + "=" + myApplication
                .getTeacher_id(), null, new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    courses = (ArrayList<Course>) object;
                    int length = courses.size();
                    courseNames = new String[length];
                    for (int i = 0; i < length; i++) {
                        Course course = courses.get(i);
                        courseNames[i] = course.getCourse_name();
                    }
                }
            }

            @Override
            public void onFailure(Object object) {

            }
        }, Course.class));
    }

    @Override
    public void initData(Bundle bundle) {
        myApplication = (MyApplication) getApplication();
        courses = new ArrayList<>();
        courseNames = new String[]{};
        activity = this;
    }

    @OnClick({R.id.back, R.id.courseEditView, R.id.chart, R.id.seekbar, R.id.punishEdit, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                activity.finish();
                break;
            case R.id.courseEditView:
                showDialog();
                break;
            case R.id.chart:
                break;
            case R.id.seekbar:
                break;
            case R.id.punishEdit:
                break;
            case R.id.submit:
                submit();
                break;
        }
    }

    private void submit() {
        if (courseId == 0){
            Util.showToast(activity, "请先选择课程");
            return;
        }
        if (punishEdit.getText().toString().isEmpty()){
            Util.showToast(activity, "输入不合法");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            jsonObject.put(APIs.signPercent, seekbar.getProgress());
            jsonObject.put(APIs.homeworkPercent, 100 - seekbar.getProgress());
            jsonObject.put(APIs.courseId, courseId);
            jsonObject.put(APIs.absencePunish, Integer.parseInt(punishEdit.getText().toString()));
            array.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Util.logHelper(array.toString()+"----setpercent");
        CommonRequest.createPutRequest(APIs.GET_ALL_COURSE, array.toString(), new CommomHandler(new CommomListener() {


            @Override
            public void onSuccess(Object object) {
                try {
                    JSONObject ret = new JSONObject(String.valueOf(object));
                    if (ret.getInt(APIs.status)==1){
                        Util.showToast(activity, "设置成功");
                        activity.finish();
                    }else {
                        Util.showToast(activity, "设置失败");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Object object) {
                Util.showToast(activity, "设置失败");
            }
        }));
    }

    private void showDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder
                (this);
        builder.setTitle("请选择课程");
        builder.setSingleChoiceItems(courseNames, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                courseEditView.setText(courseNames[which]);
                refreshView(which);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void refreshView(int which) {
        Course course = courses.get(which);
        signPercentage = course.getAttendances_percent_of_total_grade();
        homeworkPercentage = course.getHomeworks_percent_of_total_grade();
        courseId = course.getCourse_id();
        punishEdit.setText(course.getAbsences_deduce_point()+"");
        seekbar.setProgress(signPercentage*100/(signPercentage+homeworkPercentage));
        updateChart(signPercentage*100/(signPercentage+homeworkPercentage));
    }
}
