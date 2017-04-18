package com.example.yannis.dianming.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;

/**
 * Created by yannis on 2017/2/23.
 */

public class SettingBar extends RelativeLayout {

    private TextView textView;
    private ImageView logo, arrow;
    private RelativeLayout.LayoutParams textParams, logoParam, arrowParam;
    private String text;
    private float size, left, right;
    private int imageSize;
    private int logoSrc, arrowSrc;
    private int textColor;

    public SettingBar(Context context) {
        this(context, null);
    }

    public SettingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingBar);
        text = ta.getString(R.styleable.SettingBar_text);
        size = ta.getDimension(R.styleable.SettingBar_textSize, 0);
        left = ta.getDimension(R.styleable.SettingBar_marginLeft, 0);
        right = ta.getDimension(R.styleable.SettingBar_marginRight, 0);
        logoSrc = ta.getResourceId(R.styleable.SettingBar_logo, 0);
        arrowSrc = ta.getResourceId(R.styleable.SettingBar_arrow, 0);
        textColor = ta.getColor(R.styleable.SettingBar_textColor, 0);
        imageSize = (int) ta.getDimension(R.styleable.SettingBar_imageSize, 0);
        ta.recycle();

        setBackgroundColor(getResources().getColor(R.color.white));

        textView = new TextView(context);
        logo = new ImageView(context);
        arrow = new ImageView(context);
        textView.setId(R.id.title_id);
        logo.setId(R.id.logo_id);
        arrow.setId(R.id.arrow_id);

        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textView.setTextColor(textColor);

        logo.setImageResource(logoSrc);
        arrow.setImageResource(arrowSrc);
        textParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        logoParam = new RelativeLayout.LayoutParams(imageSize,
                imageSize);
        arrowParam = new RelativeLayout.LayoutParams(imageSize,
                imageSize);

        logoParam.addRule(ALIGN_PARENT_LEFT, TRUE);
        logoParam.addRule(CENTER_VERTICAL, TRUE);
        logoParam.leftMargin = (int) left;
        logoParam.topMargin = (int) left;
        textParams.addRule(CENTER_VERTICAL, TRUE);
        textParams.addRule(RIGHT_OF, R.id.logo_id);
        textParams.leftMargin = (int) left;
        arrowParam.addRule(ALIGN_PARENT_RIGHT, TRUE);
        arrowParam.rightMargin = (int) right;
        arrowParam.addRule(CENTER_VERTICAL, TRUE);

        addView(textView, textParams);
        addView(logo, logoParam);
        addView(arrow, arrowParam);

    }
}
