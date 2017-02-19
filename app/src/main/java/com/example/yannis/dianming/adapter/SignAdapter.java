package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.BaseViewHolder;
import com.example.yannis.dianming.base.CustomAdapter;
import com.example.yannis.dianming.bean.Student;
import com.example.yannis.dianming.bean.StudentSignState;
import com.example.yannis.dianming.widget.SwipeListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannis on 2017/1/12.
 */

public class SignAdapter extends CustomAdapter<StudentSignState> {

    public List<StudentSignState> students;
    private int[] pics ;

    private int currentIndex;


    public SignAdapter(List datas, Context context, int layoutID) {
        super(datas, context, layoutID);
        students = datas;
        pics = new int[]{R.mipmap.green_status, R.mipmap.yellow_status, R.mipmap.blue_status, R.mipmap.red_status};
        currentIndex = 0;
    }

    @Override
    public void refresh(List mDatas) {

        students = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convertView(BaseViewHolder holder, StudentSignState item) {
        holder.setText(R.id.studentName, item.getStudent_name());
        holder.setText(R.id.studentNo, item.getStudent_number());

        holder.setImageRes(R.id.state, pics[item.getStatus()]);
    }


}
