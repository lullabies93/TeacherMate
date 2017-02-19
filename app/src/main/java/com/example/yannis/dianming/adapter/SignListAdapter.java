package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.activity.LoginActivity;
import com.example.yannis.dianming.base.Util;
import com.example.yannis.dianming.bean.Student;
import com.example.yannis.dianming.widget.SwipeListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yannis on 2017/1/15.
 */

public class SignListAdapter extends BaseAdapter {

    public ArrayList<Student> students;
    private int[] pics;
    private int[] currentIndexs;
    private Context context;
    private LayoutInflater inflater;

    public SignListAdapter(ArrayList<Student> students, Context context) {
        this.students = students;
        this.context = context;
        inflater = LayoutInflater.from(context);
        pics = new int[]{R.mipmap.green_status, R.mipmap.blue_status, R.mipmap.red_status};
        currentIndexs = new int[150];

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
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.sign_list_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.studentName);
            holder.no = (TextView) view.findViewById(R.id.studentNo);
            holder.iv = (ImageView) view.findViewById(R.id.state);

            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }
        // Util.logHelper("position :" + position);
        final Student student = students.get(position);
        holder.name.setText(student.getStudent_name());
        holder.no.setText(student.getStudent_number());
        holder.iv.setImageResource(pics[student.getStatus()]);
        currentIndexs[position] = student.getStatus();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentIndex = currentIndexs[position];
                currentIndex = (currentIndex + 1) % 3;
                holder.iv.setImageResource(pics[currentIndex]);
                currentIndexs[position] = currentIndex;
                student.setStatus(currentIndex);


            }
        });


        return view;
    }


    private class ViewHolder {
        TextView name;
        TextView no;
        ImageView iv;
    }
}
