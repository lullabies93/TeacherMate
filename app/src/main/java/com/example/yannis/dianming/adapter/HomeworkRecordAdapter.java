package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.bean.HomeworkGrade;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/2/16.
 */

public class HomeworkRecordAdapter extends BaseAdapter {
    public ArrayList<HomeworkGrade> gradeArrayList;
    private Context context;
    private LayoutInflater inflater;

    public HomeworkRecordAdapter(ArrayList<HomeworkGrade> gradeArrayList, Context context) {
        this.gradeArrayList = gradeArrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return gradeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return gradeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grade_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HomeworkGrade grade = gradeArrayList.get(position);
        holder.studentName.setText(grade.getCourse_name());
        holder.studentScore.setText(String.valueOf(grade.getScore()));
        holder.studentNumber.setText(grade.getCourse_number());
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.studentName)
        TextView studentName;
        @InjectView(R.id.studentScore)
        TextView studentScore;
        @InjectView(R.id.studentNumber)
        TextView studentNumber;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
