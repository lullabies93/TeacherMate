package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.SignActivity;
import com.example.yannis.dianming.activity.SignDetailActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course2;
import com.example.yannis.dianming.bean.Record;
import com.example.yannis.dianming.network.CommomHandler;
import com.example.yannis.dianming.network.CommomListener;
import com.example.yannis.dianming.network.CommonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannis on 2017/1/12.
 */

public class CourseAdapter extends BaseAdapter {

    public List<Course2> courses;
    private Context context;
    private MyApplication myApplication;
    private String url;

    public CourseAdapter(List<Course2> courses, Context context) {
        this.courses = courses;
        this.context = context;
        myApplication = (MyApplication) context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.course_item, null);
            holder = new ViewHolder();
            holder.courseName = (TextView) convertView.findViewById(R.id.course_name);
            holder.courseTime = (TextView) convertView.findViewById(R.id.course_time);
            holder.coursePlace = (TextView) convertView.findViewById(R.id.course_place);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Course2 course2 = courses.get(position);
        holder.coursePlace.setText(course2.getClassroom());
        holder.courseName.setText(course2.getCourse_name());
        holder.courseTime.setText(course2.getSection());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String section = course2.getSection();
                section = section.substring(1, section.length() - 1);
                Util.logHelper(section + "--section");
                final int sectionLength = Integer.parseInt(section.split("-")[1]) - Integer.parseInt(section
                        .split("-")[0]) + 1;
                //判断是否已经点过名
                url = ConstantValues.GET_RECORD_ID + "?" + ConstantValues.courseId + "=" + course2
                        .getCourse_id() + "&" + ConstantValues
                        .weekday + "=" + course2.getWeekday() + "&" + ConstantValues.week + "=" +
                        myApplication.getWeek();
                Util.logHelper(url);
                CommonRequest.createGetRequest(url, null, new CommomHandler(new CommomListener() {
                    @Override
                    public void onSuccess(Object object) {
                        if (object instanceof ArrayList) {
                            ArrayList<Record> records = (ArrayList<Record>) object;
                            int size = records.size();
                            Intent intent = new Intent();
                            if (size > 0) {
                                intent.setClass(context, SignDetailActivity.class);
                                Record record = records.get(size-1);
                                intent.putExtra(ConstantValues.recordId, record.getAttendance_record_id());
                                intent.putExtra(ConstantValues.courseId, record.getCourse_id());
                                intent.putExtra(ConstantValues.courseName, record.getCourse_name());
                                intent.putExtra(ConstantValues.weekday, course2.getWeekday());
                                intent.putExtra(ConstantValues.sectionLength, record.getSection_length());
                                intent.putExtra("sign_nickname", record.getName());
                            } else {
                                intent.setClass(context, SignActivity.class);
                                intent.putExtra(ConstantValues.weekday, course2.getWeekday());
                                intent.putExtra(ConstantValues.courseID, course2.getCourse_id());//int型
                                intent.putExtra(ConstantValues.courseName, course2.getCourse_name());//string
                                intent.putExtra(ConstantValues.sectionLength, sectionLength);//int
                            }
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Object object) {

                    }
                }, Record.class));


            }
        });
        return convertView;
    }

    private boolean hasCalled(Course2 course2) {
        String url = ConstantValues.GET_RECORD_ID + "?" + ConstantValues.courseId + "=" + course2
                .getCourse_id() + "&" + ConstantValues
                .weekday + "=" + course2.getWeekday() + "&" + ConstantValues.week + "=" + myApplication
                .getWeek();
        CommonRequest.createGetRequest(url, null, new CommomHandler(new CommomListener() {
            @Override
            public void onSuccess(Object object) {
                if (object instanceof ArrayList) {
                    ArrayList<Record> records = (ArrayList<Record>) object;
                    if (records.size() > 0) {

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Object object) {

            }
        }, Record.class));
        return false;
    }

    class ViewHolder {
        TextView courseName, coursePlace, courseTime;
    }
}
