package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.HomeworkDetailActivity;
import com.example.yannis.dianming.base.APIs;
import com.example.yannis.dianming.base.BaseViewHolder;
import com.example.yannis.dianming.base.CustomAdapter;
import com.example.yannis.dianming.bean.HomeworkRecord;

import java.util.List;

/**
 * Created by yannis on 2017/1/18.
 */

public class HomeworkListAdapter extends BaseAdapter {
    public List<HomeworkRecord> records;
    private Context context;

    public HomeworkListAdapter(List datas, Context context) {
        this.records = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.homework_list_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.homeworkName);
            holder.week = (TextView) convertView.findViewById(R.id.homeworkWeek);
            holder.desc = (TextView) convertView.findViewById(R.id.homeworkDesc);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HomeworkRecord record = records.get(position);
        holder.name.setText(record.getName());
        holder.week.setText("第" + record.getWeek() + "周");
        holder.desc.setText(record.getVariety() == 0?"平时作业" : "考试成绩");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeworkDetailActivity.class);
                intent.putExtra(APIs.homeworkItem, record);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView name, week, desc;
    }
}
