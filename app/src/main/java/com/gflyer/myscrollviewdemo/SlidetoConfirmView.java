package com.gflyer.myscrollviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Gflyer on 2017/8/28.
 */

public class SlidetoConfirmView extends View {


    public SlidetoConfirmView(Context context) {
        this(context,null);
    }

    public SlidetoConfirmView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidetoConfirmView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
