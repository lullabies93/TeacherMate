package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.bean.StudentSignDetail;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/2/21.
 */

public class StudentDetailAdapter extends BaseAdapter {
    public ArrayList<StudentSignDetail> studentSignDetails;
    private Context context;
    private LayoutInflater inflater;

    public StudentDetailAdapter(ArrayList<StudentSignDetail> studentSignDetails, Context context) {
        this.studentSignDetails = studentSignDetails;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return studentSignDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return studentSignDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.trouble_student_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudentSignDetail item = studentSignDetails.get(position);
        holder.studentName.setText(item.getStudent_name());
        holder.studentNumber.setText("学号:" + item.getStudent_number());
        holder.absence.setText(String.valueOf(item.getAttendance_detail().getAbsence_count()));
        holder.late.setText(String.valueOf(item.getAttendance_detail().getLate_count()));
        holder.leave.setText(String.valueOf(item.getAttendance_detail().getVacate_count()));
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.studentName)
        TextView studentName;
        @InjectView(R.id.studentNumber)
        TextView studentNumber;
        @InjectView(R.id.leave)
        TextView leave;
        @InjectView(R.id.late)
        TextView late;
        @InjectView(R.id.absence)
        TextView absence;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
