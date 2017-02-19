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
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseViewHolder;
import com.example.yannis.dianming.base.CustomAdapter;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.Record;

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
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeworkActivity.class);
                intent.putExtra(APIs.courseInfo, course);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView courseName, coursePlace;
    }
//    public HomeworkAdapter(List<Course> datas, Context context, int layoutID) {
//        super(datas, context, layoutID);
//        courses = datas;
//        this.context = context;
//        builder = new StringBuilder();
//    }
//
//    @Override
//    public void refresh(List<Course> mDatas) {
//        courses = mDatas;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public void convertView(BaseViewHolder holder, final Course item) {
//        Util.logHelper("get convertview");
//        holder.setText(R.id.courseName, item.getCourse_name());
//        classrooms = item.getClassroom();
////        builder.delete(0, builder.length());
////        for (String string : classrooms){
////            builder.append(string+"/");
////        }
//
////        holder.setText(R.id.coursePlace, builder.substring(0, builder.length()-1));
////        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(context, HomeworkActivity.class);
////                intent.putExtra(APIs.courseInfo, item);
////                context.startActivity(intent);
////            }
////        });
//    }
}
