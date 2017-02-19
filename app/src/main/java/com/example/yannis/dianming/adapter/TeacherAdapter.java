package com.example.yannis.dianming.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.yannis.dianming.base.BaseViewHolder;
import com.example.yannis.dianming.base.CustomAdapter;

import java.util.List;

/**
 * Created by yannis on 2016/12/15.
 */

public class TeacherAdapter<T> extends CustomAdapter<T> {

    private List<T> mDatas;

    public TeacherAdapter(List<T> datas, Context context, int layoutID) {
        super(datas, context, layoutID);
    }

    @Override
    public void refresh(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public void convertView(BaseViewHolder holder, Object item) {

    }
}
