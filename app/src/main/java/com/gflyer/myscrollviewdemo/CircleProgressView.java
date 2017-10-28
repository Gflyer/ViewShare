package com.gflyer.myscrollviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;

/**
 * Created by Gflyer on 2017/7/18.
 */

public class CircleProgressView extends View {

    private int roundBgColor;//圆环背景颜色
    private int roundProgressColor;//圆环填充颜色
    private float roundWidth;//圆环宽度，默认2px
    private float textSize;//字体大小
    private double max;//最大进度
    private Paint paint;
    private double progress = 100;
    private int center;
    private int radius = 50;
    /**
     * 是否使用小数
     */
    private boolean isDouble = false;
    /**
     * 默认大小
     */
    private int defaultWith = 120;
    /**
     * 默认大小
     */
    private int defaultHeight = 120;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        initAttrs(context, attrs);

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        roundBgColor = typedArray.getColor(R.styleable.CircleProgressView_roundBgColor, Color.WHITE);
        roundProgressColor = typedArray.getColor(R.styleable.CircleProgressView_roundProgressColor, Color.YELLOW);
        roundWidth = typedArray.getDimension(R.styleable.CircleProgressView_roundWidth, 2);
        textSize = typedArray.getDimension(R.styleable.CircleProgressView_textSize, 30);//22
        max = typedArray.getInteger(R.styleable.CircleProgressView_max, 100);

        typedArray.recycle();//记得回收
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println(getMeasuredHeight() + "---" + getMeasuredWidth());
        if (getMeasuredHeight() <= 0) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) getLayoutParams();
            mlp.height = defaultHeight;
            mlp.width = defaultWith;
            setLayoutParams(mlp);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画大圆环
         */
        //圆心坐标
        center = getWidth() / 2;
        //圆的半径
        paint.setColor(roundBgColor);
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(center, center, radius, paint);

        /**
         * 画进度百分比(文字)
         */

        paint.setStrokeWidth(0);
        paint.setColor(R.color.progress_content_txt);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);//设置字体
        if (isDouble) {
            double percent = countShe(progress / max * 100);
            Paint.FontMetrics fm = paint.getFontMetrics();
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(percent + "%", center,center + (fm.descent - fm.ascent) / 2 - fm.descent, paint);
        } else {
            int percent = (int) (progress / max * 100);
            Paint.FontMetrics fm = paint.getFontMetrics();
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(percent + "%", center,center + (fm.descent - fm.ascent) / 2 - fm.descent, paint);
        }

        /**
         * 画进度(圆环)
         */
        paint.setColor(roundProgressColor);
        paint.setStyle(Paint.Style.STROKE);//设置空心
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, -90, (float) (360 * progress / max), false, paint);

    }

    /**
     * 四舍五入保留一位小数
     *
     * @param d
     * @return
     */
    private double countShe(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 设置值
     *
     * @param max
     */
    public void setMax(double max) {
        this.max = max;
    }

    public double getMax() {
        return max;
    }

    public void setProgress(double progress, boolean isDouble) {
        this.isDouble = isDouble;
        this.progress = progress;
        invalidate();
    }

    public double getProgress() {
        return progress;
    }


}
