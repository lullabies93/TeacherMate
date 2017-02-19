package com.example.yannis.dianming.widget;

import android.app.Fragment;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.BaseFragment;

/**
 * Created by yannis on 2017/1/10.
 */

public class TabItem {
    private int image_normal, image_checked;
    private int title;
    private String title_string;

    private Context context;

    private View view;
    private ImageView imageView;
    private TextView textView;

    private Class<? extends BaseFragment> fragmentClass;

    public TabItem(Context context, int image_normal, int image_checked, int title, Class<? extends BaseFragment> fragmentClass) {
        this.context = context;
        this.image_normal = image_normal;
        this.image_checked = image_checked;
        this.title = title;
        this.fragmentClass = fragmentClass;
    }

    public Class<? extends BaseFragment> getFragmentClass() {
        return fragmentClass;
    }

    public String getTitle_string() {
        if (title == 0){
            return "";
        }
        if (TextUtils.isEmpty(title_string)){
            title_string = context.getString(title);
        }
        return title_string;
    }

    public View getView() {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
            imageView = (ImageView) view.findViewById(R.id.tab_iv_image);
            textView = (TextView) view.findViewById(R.id.tab_tv_text);
            textView.setText(getTitle_string());
            imageView.setImageResource(image_normal);
        }
        return view;
    }

    public void setChecked(boolean checked){
        if (imageView != null){
            if (checked){
                imageView.setImageResource(image_checked);
            }else{
                imageView.setImageResource(image_normal);
            }

        }
        if (textView != null){
            if (checked){
                textView.setTextColor(context.getResources().getColor(R.color.maincolor));
            }else{
                textView.setTextColor(context.getResources().getColor(R.color.lightblack));
            }
        }
    }
}
