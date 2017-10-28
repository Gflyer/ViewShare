package com.gflyer.myscrollviewdemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Gflyer on 2017/8/10.
 */

public class ColorProgressView extends View {
    private int colorProgressView_roundBgColor;//圆环背景颜色
    private int colorProgressView_proColor_fir;//进度条第一种颜色
    private int colorProgressView_proColor_sec;//进度条第二种颜色
    private int colorProgressView_proColor_thi;//进度条第三种颜色
    private float colorProgressView_sumTextSize;//金额数字文字大小
    private int colorProgressView_sumTextColor;//金额数字颜色
    private int colorProgressView_tipTextColor;//标题提示字体颜色
    private float colorProgressView_tipTextSize = 16;//标题提示字体大小
    /**
     * 默认控件宽高大小
     */
    private static final int DEFAULT_WIDTH_HEIGHT = 500;
    /**
     * 默认圆环厚度
     */
    private static final float DEFAULT_ROUND_WIDTH = 25;

    private float center;
    private Paint paint;
    private float colorProgressView_roundWidth;
    private TextView tip;
    private TextView sum;
    private LinearLayout textLayout;
    private float radius;
    private float mProgress;
    private float per_first = 0.1f;
    private float per_second = 0.3f;
    private float per_third = 0.6f;
    private int[] colors;
    private float[] pers;

    public ColorProgressView(Context context) {
        this(context, null);
    }

    public ColorProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initData();
        initView(context);
    }

    private void initData() {
        colors = new int[]{colorProgressView_proColor_fir, colorProgressView_proColor_sec, colorProgressView_proColor_thi};
        pers = new float[]{per_first, per_second, per_third};
    }


    private void initView(Context context) {
        //init Radius
        radius = DEFAULT_WIDTH_HEIGHT / 2 - colorProgressView_roundWidth;


        //initCircle
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);//设置圆环空心
        paint.setStrokeWidth(colorProgressView_roundWidth);
        paint.setAntiAlias(true);//抗锯齿


        //initTextView
        if (colorProgressView_tipTextColor == 0) {
            colorProgressView_tipTextColor = ContextCompat.getColor(context, R.color.col_pro_tip);
        }
        sum = new TextView(context);
        tip = new TextView(context);
        sum.setTextSize(colorProgressView_sumTextSize);
        tip.setTextSize(colorProgressView_tipTextSize);
        tip.setTextColor(colorProgressView_tipTextColor);
        sum.setTextColor(colorProgressView_sumTextColor);
        tip.setGravity(Gravity.CENTER_HORIZONTAL);
        sum.setGravity(Gravity.CENTER_HORIZONTAL);

        //initLayout
        textLayout = new LinearLayout(context);
        textLayout.setOrientation(LinearLayout.VERTICAL);
        textLayout.setGravity(Gravity.CENTER);
        textLayout.addView(tip);
        textLayout.addView(sum);

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorProgressView);
        colorProgressView_roundBgColor = typedArray.getColor(R.styleable.ColorProgressView_clo_roundBgColor, Color.WHITE);
        colorProgressView_proColor_fir = typedArray.getColor(R.styleable.ColorProgressView_proColor_fir, ContextCompat.getColor(context, R.color.col_pro_def_first));
        colorProgressView_proColor_sec = typedArray.getColor(R.styleable.ColorProgressView_proColor_sec, ContextCompat.getColor(context, R.color.col_pro_def_second));
        colorProgressView_proColor_thi = typedArray.getColor(R.styleable.ColorProgressView_proColor_thi, ContextCompat.getColor(context, R.color.col_pro_def_txt_third));
        colorProgressView_sumTextSize = typedArray.getDimension(R.styleable.ColorProgressView_clo_textSize, 25);
        colorProgressView_sumTextColor = typedArray.getColor(R.styleable.ColorProgressView_clo_textColor, ContextCompat.getColor(context, R.color.col_pro_def_txt_third));
        colorProgressView_roundWidth = typedArray.getDimension(R.styleable.ColorProgressView_clo_roundWidth, DEFAULT_ROUND_WIDTH);
        typedArray.recycle();//记得回收
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置warp_content的大小
        setMeasuredDimension(myMeasure(widthMeasureSpec), myMeasure(heightMeasureSpec));
    }

    private int myMeasure(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = DEFAULT_WIDTH_HEIGHT;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        center = getWidth() / 2;

        //draw BgCircle
        paint.setColor(colorProgressView_roundBgColor);
        canvas.drawCircle(center, center, radius, paint);


        checkRect(colors.length,canvas);


        //draw Text
        textLayout.measure(canvas.getWidth(), getHeight());
        textLayout.layout(0, 0, canvas.getWidth(), getHeight());



        textLayout.draw(canvas);

    }

    public void checkRect(int count_sum,Canvas canvas){
        float per_sum=0;
        for (int j=0;j<count_sum;j++){
            if (mProgress>100*per_sum&&mProgress<=100*(per_sum+pers[j])){
                paintRect(j+1,canvas);
            }
            per_sum+=pers[j];
        }
    }


    public void paintRect(int count, Canvas canvas) {
        float per_sum = 0;


        paint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < count; i++) {
            if (pers[i]!=0) {
                if (i == count - 1) {
                    paint.setColor(colors[i]);
                    RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
                    canvas.drawArc(oval, -90 + (360 * per_sum), (360 * (mProgress / 100 - per_sum)), false, paint);
                    break;
                }
                paint.setColor(colors[i]);
                RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
                canvas.drawArc(oval, -90 + (360 * per_sum), (360 * pers[i]), false, paint);
                per_sum += pers[i];
            }
        }

    }

    /**
     * 设置进度条颜色
     *
     * @param colors 规定颜色数组长度为3，否则抛出异常
     */
    public void setRoundColor(int[] colors) {
        if (colors.length != 3) {
            throw new ColorProgressException("color index unqualified");
        }
        this.colors = colors;
        colorProgressView_proColor_fir = colors[0];
        colorProgressView_proColor_sec = colors[1];
        colorProgressView_proColor_thi = colors[2];
    }

    /**
     * 获取进度条颜色
     *
     * @return 返回颜色数组
     */
    public int[] getRoundColor() {
        return new int[]{colorProgressView_proColor_fir, colorProgressView_proColor_sec, colorProgressView_proColor_thi};
    }

    /**
     * 设置颜色进度值
     *
     * @param pers 进度值数组，规定长度为3且和为1，否则抛出异常
     */
    public void setRoundPer(float[] pers) {
        if (pers.length != 3 || (pers[0] + pers[1] + pers[2]) != 1) {
            throw new ColorProgressException("color count index unqualified");
        }
        this.pers = pers;
        per_first = pers[0];
        per_second = pers[1];
        per_third = pers[2];
    }

    /**
     * 获取颜色进度值
     *
     * @return 颜色进度值数组，否则抛出异常
     */
    public float[] getRoundPer() {
        return new float[]{per_first, per_second, per_third};
    }

    public void startProgressAnim(int duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", 100);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.start();
    }

    public float getProgress() {
        return mProgress;
    }

    public synchronized void setProgress(float progress) {
        this.mProgress = progress < 100 ? progress : 100;
        invalidate();
    }

    public synchronized void setTipText(String tipText) {
        tip.setText(tipText);
        invalidate();
    }

    public String getTipText() {
        return tip.getText().toString();
    }

    public synchronized void setSumText(String sumText) {
        sum.setText(sumText);
        invalidate();
    }

    public String getSumText() {
        return sum.getText().toString();
    }

    public void setSumTextColor(int textColor) {
        this.colorProgressView_sumTextColor = textColor;
        sum.setTextColor(textColor);
        invalidate();
    }

    public void setSumTextSize(float textsize) {
        this.colorProgressView_sumTextSize = textsize;
        sum.setTextSize(textsize);
        invalidate();
    }

    public void setTipTextSize(float textsize) {
        this.colorProgressView_tipTextSize = textsize;
        sum.setTextSize(textsize);
        invalidate();
    }


    public void setTipTextColor(int textColor) {
        this.colorProgressView_tipTextColor = textColor;
        tip.setTextColor(textColor);
        invalidate();
    }

    public void setRoundBgColor(int roundBgColorolor) {
        this.colorProgressView_roundBgColor = roundBgColorolor;
        invalidate();
    }


    class ColorProgressException extends IllegalArgumentException {
        public ColorProgressException(String msg) {
            super(msg);
        }
    }
}
