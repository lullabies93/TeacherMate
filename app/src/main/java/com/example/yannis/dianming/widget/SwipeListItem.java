package com.example.yannis.dianming.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.yannis.dianming.base.Util;

/**
 * Created by yannis on 2017/1/11.
 */

public class SwipeListItem extends HorizontalScrollView {

    //屏幕宽度、菜单宽度、一半菜单宽度
    private int mScreenWidth, mMenuWidth;

    private Context mContext;

    //菜单是否已拉出
    private boolean isOpen = false;

    //第一次绘制
    private boolean isFirst = true;

    private float down_x, up_x;

    public SwipeListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        mScreenWidth = Util.getScreenWidth(mContext);
    }

    public SwipeListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeListItem(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isFirst){
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            ViewGroup content = (ViewGroup) wrapper.getChildAt(0);
            ViewGroup menu = (ViewGroup) wrapper.getChildAt(1);
            mMenuWidth = mScreenWidth*2/3;
            Util.logHelper("menuWidth" + mMenuWidth);
            //halfMenuWidth = mMenuWidth/3;
            menu.getLayoutParams().width = mMenuWidth;
            content.getLayoutParams().width = mScreenWidth;
            Util.logHelper("mScreenWidth" + mScreenWidth);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(0, 0);
            isFirst = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        int x=getScrollX();
        switch (action) {
            case MotionEvent.ACTION_UP:{
                if(x<mMenuWidth/2){
                    smoothScrollTo(0, 0);
                    isOpen = false;
                    Util.logHelper("right");
                }else {
                    Util.logHelper("left");
                    smoothScrollTo(mMenuWidth, 0);
                    isOpen = true;
                }
            }return true;
        }
        return super.onTouchEvent(event);
    }

    public boolean isOpen(){
        return isOpen;
    }

    public void openMenu(){
        if (!isOpen){
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = true;
        }
    }

    public void closeMenu(){
        if (isOpen){
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }
}
