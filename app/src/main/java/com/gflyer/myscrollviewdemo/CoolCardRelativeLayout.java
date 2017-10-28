package com.gflyer.myscrollviewdemo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by Gflyer on 2017/8/4.
 */

public class CoolCardRelativeLayout extends RelativeLayout {
    public CoolCardRelativeLayout(Context context) {
        this(context,null);
    }

    public CoolCardRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CoolCardRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ViewPager vp= (ViewPager) getChildAt(1);
        if(vp!=null) {
            return vp.onTouchEvent(event);
        }else {
            return super.onTouchEvent(event);
        }
    }
}
