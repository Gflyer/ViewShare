package com.gflyer.myscrollviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Gflyer on 2017/7/11.
 */

public class MyScrollView extends ScrollView {
    private MyScrollViewListener myScrollViewListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    public interface MyScrollViewListener {
        void scrollViewonChange(MyScrollView myScrollView,int l,int t,int oldl,int oldt);
    }

    public void setMyScrollViewListener(MyScrollViewListener myScrollViewListener) {
        this.myScrollViewListener = myScrollViewListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        System.out.println("y---:"+getScrollY());
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (myScrollViewListener!=null){
            myScrollViewListener.scrollViewonChange(this,l,t,oldl,oldt);
        }
    }
}
