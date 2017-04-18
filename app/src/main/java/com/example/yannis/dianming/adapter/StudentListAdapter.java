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
 * Created by yannis on 2017/2/16.
 */

public class StudentListAdapter extends BaseAdapter {
    public ArrayList<Student> students;
    //public List<String> grades;
    private Context context;
    private LayoutInflater inflater;

    public StudentListAdapter(ArrayList<Student> students, Context context) {
        this.students = students;
        //this.grades = grades;
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
            convertView = inflater.inflate(R.layout.student_item, null);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Student student = students.get(position);
        holder.name.setText(student.getStudent_name());
        holder.number.setText(student.getStudent_number());
        return convertView;
    }


    class ViewHolder {
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.number)
        TextView number;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
