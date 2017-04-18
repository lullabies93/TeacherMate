package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.bean.HomeworkType;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/2/19.
 */

public class HomeworkClassAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<HomeworkType> homeworkClass;
    private LayoutInflater inflater;

    public HomeworkClassAdapter(Context context, ArrayList<HomeworkType> homeworkClass) {
        this.context = context;
        this.homeworkClass = homeworkClass;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return homeworkClass.size();
    }

    @Override
    public Object getItem(int position) {
        return homeworkClass.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.homework_type_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HomeworkType homeworkType = homeworkClass.get(position);
        holder.typeName.setText(homeworkType.getName());
        holder.typePercent.setText(String.valueOf(homeworkType.getPercent_of_total_grade()));
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.typeName)
        TextView typeName;
        @InjectView(R.id.typePercent)
        TextView typePercent;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
