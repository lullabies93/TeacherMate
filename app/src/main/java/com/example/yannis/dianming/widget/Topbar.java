package com.example.yannis.dianming.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yannis.dianming.R;

/**
 * Created by yannis on 2017/1/9.
 */

public class Topbar extends RelativeLayout {

    private TextView leftTv, rightTv1, rightTv2;
    private String leftStr, rightStr1, rightStr2;
    private int textColor;
    //private Drawable leftDrawable;
    private float leftMargin, rightMargin1, rightMargin2, leftSize, rightSize;

    private LayoutParams leftParams, rightParams1, rightParams2;

    private TopBarClickListener listener;

    public interface TopBarClickListener{
        void leftClick();
        void right1Click();
        void right2Click();
    }

    public void setOnTopBarClickListener(TopBarClickListener listener){
        this.listener = listener;
    }

    public Topbar(Context context) {
        this(context, null);
    }

    public Topbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Topbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        leftStr = typedArray.getString(R.styleable.TopBar_leftText);
        rightStr1 = typedArray.getString(R.styleable.TopBar_rightText1);
        rightStr2 = typedArray.getString(R.styleable.TopBar_rightText2);
        textColor = typedArray.getColor(R.styleable.TopBar_textColor, 0);
        leftMargin = typedArray.getDimension(R.styleable.TopBar_leftMargin, 0);
        rightMargin1 = typedArray.getDimension(R.styleable.TopBar_rightMargin1, 0);
        rightMargin2 = typedArray.getDimension(R.styleable.TopBar_rightMargin2, 0);
        //leftDrawable = typedArray.getDrawable(R.styleable.TopBar_leftDrawable);
        leftSize = typedArray.getDimension(R.styleable.TopBar_leftSize, 0);
        rightSize = typedArray.getDimension(R.styleable.TopBar_rightSize, 0);
        typedArray.recycle();

        leftTv = new TextView(context);
        rightTv1 = new TextView(context);
        rightTv2 = new TextView(context);
        leftTv.setText(leftStr);
        rightTv1.setText(rightStr1);
        rightTv2.setText(rightStr2);
        leftTv.setTextColor(textColor);
        rightTv1.setTextColor(textColor);
        rightTv2.setTextColor(textColor);
        leftTv.setTextSize(leftSize);
        rightTv1.setTextSize(rightSize);
        rightTv2.setTextSize(rightSize);
        leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightParams2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        leftParams.addRule(CENTER_VERTICAL, TRUE);
        leftParams.leftMargin = (int)leftMargin;
        addView(leftTv, leftParams);
        rightParams2.addRule(ALIGN_PARENT_RIGHT, TRUE);
        rightParams2.addRule(CENTER_VERTICAL, TRUE);
        rightParams2.rightMargin = (int)rightMargin2;
        addView(rightTv2, rightParams2);
        rightParams1.addRule(CENTER_VERTICAL, TRUE);
        rightTv2.setId(R.id.rightTv2_id);
        rightTv1.setId(R.id.rightTv1_id);
        rightParams1.addRule(LEFT_OF, R.id.rightTv2_id);
        rightParams1.rightMargin = (int)rightMargin1;
        addView(rightTv1, rightParams1);

        setBackgroundColor(getResources().getColor(R.color.white));

        leftTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });
        rightTv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.right1Click();
            }
        });
        rightTv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.right2Click();
            }
        });
    }
}
