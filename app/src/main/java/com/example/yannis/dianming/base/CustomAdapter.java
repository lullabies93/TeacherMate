package com.example.yannis.dianming.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yannis on 2016/12/15.
 */

public abstract class CustomAdapter<T> extends BaseAdapter {

    protected List<T> datas;
    protected Context context;
    protected int layoutID;

    public CustomAdapter(List<T> datas, Context context, int layoutID) {
        this.datas = datas;
        this.context = context;
        this.layoutID = layoutID;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        BaseViewHolder holder = BaseViewHolder.getHolder(context, position, parent, convertView, layoutID);
        convertView(holder, datas.get(position));
        return holder.getConvertView();
    };

    public abstract void refresh(List<T> mDatas);
    public abstract void convertView(BaseViewHolder holder, T item);
}
