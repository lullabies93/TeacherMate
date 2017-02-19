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
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseViewHolder;
import com.example.yannis.dianming.base.CustomAdapter;
import com.example.yannis.dianming.base.MyApplication;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.Course2;

import java.util.List;

/**
 * Created by yannis on 2017/1/12.
 */

public class CourseAdapter extends BaseAdapter {

    public List<Course2> courses;
    private Context context;

    public CourseAdapter(List<Course2> courses, Context context) {
        this.courses = courses;
        this.context = context;
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
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.course_item, null);
            holder = new ViewHolder();
            holder.courseName = (TextView) convertView.findViewById(R.id.course_name);
            holder.courseTime = (TextView) convertView.findViewById(R.id.course_time);
            holder.coursePlace = (TextView) convertView.findViewById(R.id.course_place);
            convertView.setTag(holder);
        }else{
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
                section = section.substring(1, section.length()-1);
                Util.logHelper(section+"--section");
                int sectionLength = Integer.parseInt(section.split("-")[1]) - Integer.parseInt(section.split("-")[0])+1;

                Intent intent = new Intent(context, SignActivity.class);
                intent.putExtra(APIs.courseID, course2.getCourse_id());//int型
                intent.putExtra(APIs.courseName, course2.getCourse_name());//string
                intent.putExtra(APIs.sectionLength, sectionLength);//int
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class  ViewHolder{
        TextView courseName,coursePlace, courseTime;
    }
}
