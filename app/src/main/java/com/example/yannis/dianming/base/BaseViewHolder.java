package com.example.yannis.dianming.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yannis on 2016/12/15.
 */

public class BaseViewHolder {

    private SparseArray<View> mViews;
    private Context mContext;
    private int mPosition;
    private View mConvertView;
    private int mLayoutID;

    private BaseViewHolder(Context context, int position, ViewGroup parent, int layoutID) {
        this.mContext = context;
        this.mPosition = position;
        this.mLayoutID = layoutID;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(mContext).inflate(layoutID, parent, false);
        mConvertView.setTag(this);
    }

    public static BaseViewHolder getHolder(Context context, int position, ViewGroup parent, View convertView, int layoutID){
        BaseViewHolder holder = null;
        if (convertView == null){
            holder = new BaseViewHolder(context, position, parent, layoutID);
        }else{
            holder = (BaseViewHolder) convertView.getTag();
        }
        holder.mPosition = position;
        return holder;
    }

    public <T extends View> T getView(int viewId)
    {

        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }

    public BaseViewHolder setText(int id, String text){
        TextView tv = getView(id);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setImageRes(int id, int resID){
        ImageView iv = getView(id);
        iv.setImageResource(resID);
        return this;
    }


}
