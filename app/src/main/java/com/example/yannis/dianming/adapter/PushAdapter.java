package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.DBEntity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/2/21.
 */

public class PushAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<DBEntity> data;
    private LayoutInflater inflater;

    public PushAdapter(Context context, ArrayList<DBEntity> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.push_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DBEntity entity = data.get(position);
        holder.classNumber.setText("班级:"+entity.getClass_number());
        holder.studentNumber.setText("学号:"+entity.getStudent_number());
        holder.studentName.setText(entity.getStudent_name());
        holder.exceptions.setText(String.valueOf(entity.getException_times()));
        Util.logHelper(entity.getStudent_name()+":"+entity.isSolved());
        if (entity.isSolved()&&(holder.mark.getVisibility()==View.GONE)){
            holder.mark.setVisibility(View.VISIBLE);
        }else if (!entity.isSolved()&&holder.mark.getVisibility()==View.VISIBLE){
            holder.mark.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.studentName)
        TextView studentName;
        @InjectView(R.id.classNumber)
        TextView classNumber;
        @InjectView(R.id.studentNumber)
        TextView studentNumber;
        @InjectView(R.id.exception_times)
                TextView exceptions;
        @InjectView(R.id.mark)
        ImageView mark;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
