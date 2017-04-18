package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.bean.StudentSignState;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/2/16.
 */

public class SignRecordAdapter extends BaseAdapter {

    public List<StudentSignState> students;
    private int[] pics;
    private Context context;

    public SignRecordAdapter(List<StudentSignState> students, Context context) {
        this.students = students;
        this.context = context;
        pics = new int[]{R.mipmap.green_status, R.mipmap.red_status, R.mipmap.blue_status, R.mipmap
                .yellow_status};

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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sign_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudentSignState studentSignState = students.get(position);
        holder.studentName.setText(studentSignState.getCourse_name());
        holder.studentNo.setText(studentSignState.getCreate_time().substring(0, 10));
        holder.state.setImageResource(pics[studentSignState.getStatus()]);
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.studentName)
        TextView studentName;
        @InjectView(R.id.studentNo)
        TextView studentNo;
        @InjectView(R.id.state)
        ImageView state;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
