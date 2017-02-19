package com.example.yannis.dianming.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.yannis.dianming.base.Util;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by yannis on 2017/1/13.
 */

public class ScrollView extends HorizontalScrollView {

    //屏幕宽度、菜单宽度、一半菜单宽度
    private int mScreenWidth, mScrollerWidth, halfScreenWidth, scrollerHeight;

    private Context mContext;

    private LinearLayout leftScroller, centerScroller, rightScroller;

    //第一次绘制
    private boolean isFirst = true;

    private float down_x, up_x;

    public ScrollView(Context context) {
        this(context, null);
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScreenWidth = Util.getScreenWidth(mContext);
        halfScreenWidth = mScreenWidth/2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isFirst){
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            leftScroller = (LinearLayout) wrapper.getChildAt(0);
            rightScroller = (LinearLayout) wrapper.getChildAt(1);
            centerScroller = (LinearLayout) wrapper.getChildAt(2);
            mScrollerWidth = halfScreenWidth;
            leftScroller.getLayoutParams().width = mScrollerWidth;
            centerScroller.getLayoutParams().width = mScrollerWidth;
            rightScroller.getLayoutParams().width = mScrollerWidth;
            scrollerHeight = centerScroller.getMeasuredHeight();

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            //isFirst = false;
            this.scrollTo(mScrollerWidth/2, 0);
//            leftScroller.layout(0, 0, halfScreenWidth, scrollerHeight);
//            centerScroller.layout(mScrollerWidth/2, 0, (int) (halfScreenWidth * 1.5), scrollerHeight);
//            rightScroller.layout(halfScreenWidth, 0, mScreenWidth, scrollerHeight);
            //this.scrollTo(mScrollerWidth/2, 0);
            ViewHelper.setAlpha(leftScroller, (float) 0.5);
            ViewHelper.setAlpha(rightScroller, (float) 0.5);

//            ViewHelper.setPivotX(leftScroller, mScrollerWidth);
//            ViewHelper.setPivotY(leftScroller, leftScroller.getHeight()/2);
//            ViewHelper.setPivotX(rightScroller, mScrollerWidth);
//            ViewHelper.setPivotY(rightScroller, rightScroller.getHeight()/2);

            ViewHelper.setScaleX(leftScroller, (float) 0.7);
            ViewHelper.setScaleX(rightScroller, (float) 0.7);
            ViewHelper.setScaleY(leftScroller, (float) 0.7);
            ViewHelper.setScaleY(rightScroller, (float) 0.7);

            ViewHelper.setTranslationX(leftScroller, mScrollerWidth/2);
            ViewHelper.setTranslationX(centerScroller, -mScrollerWidth);
            ViewHelper.setTranslationX(rightScroller, mScrollerWidth/2);



        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //左边滚动透明度0.5-1.0，右边滚动透明度0.5-0.0，中间滚动透明度1.0-0.5
        int translation = Math.abs(l-oldl);
        if (isFirst == false){
            float param = translation*1.0f/(mScrollerWidth/2);  //0-1
            float leftAlpha = 0.5f + param * 0.5f;
            float centerAlpha = 1.0f - 0.5f * param;
            float rightAlpha = 0.5f - 0.5f *param;
            //left缩放范围0.7-1.0，right缩放范围0.7-0.0，center缩放范围1.0-0.7
            float leftScale = 0.7f + 0.3f * param;
            float centerScale = 1.0f - 0.3f * param;
            float rightScale = 0.7f - 0.7f * param;
            //偏移

            ViewHelper.setAlpha(leftScroller, leftAlpha);
            ViewHelper.setAlpha(centerScroller, centerAlpha);
            ViewHelper.setAlpha(rightScroller, rightAlpha);

            ViewHelper.setTranslationX(centerScroller, l);
            ViewHelper.setTranslationX(leftScroller, l);
            ViewHelper.setTranslationX(rightScroller, l);

            ViewHelper.setScaleX(leftScroller, leftScale);
            ViewHelper.setScaleX(centerScroller, centerScale);
            ViewHelper.setScaleX(rightScroller, rightScale);
            ViewHelper.setScaleY(leftScroller, leftScale);
            ViewHelper.setScaleY(centerScroller, centerScale);
            ViewHelper.setScaleY(rightScroller, rightScale);
        }else {
            isFirst = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){

        }
        return super.onTouchEvent(ev);
    }
}
