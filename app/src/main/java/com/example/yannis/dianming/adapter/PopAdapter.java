package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.bean.Record;

import java.util.ArrayList;

/**
 * Created by yannis on 2017/1/17.
 */

public class PopAdapter extends BaseAdapter {

    public ArrayList<Record> records;
    private Context context;
    private LayoutInflater inflater;

    public PopAdapter(ArrayList<Record> records, Context context) {
        this.records = records;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.pop_item, null);
            holder = new ViewHolder();
            holder.popText = (TextView) convertView.findViewById(R.id.popString);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.popText.setText(records.get(position).getName());

        return convertView;
    }

    class ViewHolder{
        TextView popText;
    }
}
