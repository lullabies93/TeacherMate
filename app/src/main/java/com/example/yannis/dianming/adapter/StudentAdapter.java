package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.bean.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/1/24.
 */

public class StudentAdapter extends BaseAdapter {

    public ArrayList<Student> students;
    public List<String> grades;
    private Context context;
    private LayoutInflater inflater;

    public StudentAdapter(ArrayList<Student> students, Context context, List<String> grades) {
        this.students = students;
        this.grades = grades;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grade_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Student student = students.get(position);
        holder.studentName.setText(student.getStudent_name());
        holder.studentNumber.setText(student.getStudent_number());
        holder.studentScore.setText(grades.get(position));

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.studentName)
        TextView studentName;
        @InjectView(R.id.studentNumber)
        TextView studentNumber;
        @InjectView(R.id.studentScore)
        TextView studentScore;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
