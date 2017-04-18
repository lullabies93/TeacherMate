package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.HomeworkActivity;
import com.example.yannis.dianming.base.ConstantValues;
import com.example.yannis.dianming.bean.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannis on 2017/1/17.
 */

public class HomeworkAdapter extends BaseAdapter {

    public List<Course> courses;
    private List<String> classrooms;
    private StringBuilder builder;
    private Context context;

    public HomeworkAdapter(List<Course> courses, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.work_course_item, null);
            holder = new ViewHolder();
            holder.courseName = (TextView) convertView.findViewById(R.id.courseName);
            holder.coursePlace = (TextView) convertView.findViewById(R.id.coursePlace);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Course course = courses.get(position);
        holder.courseName.setText(course.getCourse_name());
        String places = "";
        ArrayList<String> clasrooms = (ArrayList<String>) course.getClassroom();
        for (String string: clasrooms){
            places = places+string+"/";
        }
        holder.coursePlace.setText(places.substring(0, places.length()-1));
        return convertView;
    }

    class ViewHolder{
        TextView courseName, coursePlace;
    }
}
