package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.bean.Course;
import com.example.yannis.dianming.bean.HomeworkType;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/2/20.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private LayoutInflater inflater;
    public HashMap<Course, ArrayList<HomeworkType>> data;
    public ArrayList<Course> courses;
    public ArrayList<HomeworkType> homeworkTypes;

    public ExpandableAdapter(Context context, ArrayList<Course> courses, HashMap<Course,
            ArrayList<HomeworkType>> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        this.courses = courses;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(courses.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(courses.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(courses.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.parent_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isExpanded){
            holder.arrow.setImageResource(R.mipmap.down);
        }else {
            holder.arrow.setImageResource(R.mipmap.up);
        }
        Course course = courses.get(groupPosition);
        holder.name.setText(course.getCourse_name());
        String places = "";
        ArrayList<String> clasrooms = (ArrayList<String>) course.getClassroom();
        for (String string: clasrooms){
            places = places+string+"/";
        }
        holder.place.setText(places.substring(0, places.length()-1));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        homeworkTypes = data.get(courses.get(groupPosition));
        HomeworkType homeworkType = homeworkTypes.get(childPosition);
        holder.name.setText(homeworkType.getName());
        holder.percent.setText(homeworkType.getPercent_of_total_grade()+"%");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolder {
        @InjectView(R.id.courseName)
        TextView name;
        @InjectView(R.id.coursePlace)
        TextView place;
        @InjectView(R.id.arrow)
        ImageView arrow;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ChildViewHolder {
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.percent)
        TextView percent;

        ChildViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
