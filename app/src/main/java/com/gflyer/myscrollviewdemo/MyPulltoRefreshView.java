package com.gflyer.myscrollviewdemo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Gflyer on 2017/7/12.
 */

public class MyPulltoRefreshView extends ViewGroup {
    private View refreshView;
    private ImageView im_logo;
    private TextView tv_refresh;
    private int refreshViewHeight;
    private float lY = -1;
    private float nY;
    private int selfViewHeight;
    private View myView;
    private int myPaddingBottom;
    private int myPaddingLeft;
    private int myPaddingRight;
    private int myPaddingTop;
    private int currentHeight = 0;
    private float lSc_y;
    private float nSc_y;
    private int canRefresh;
    private float dSc_y;
    private float cha1 = 0;
    private double total_cha1 = 0;
    private int drawHeight;
    private int drawWidth;
    private Matrix matrix;
    private Bitmap bitmap;
    private int bitHeight;
    private int bitWidth;
    private LayoutParams params;
    private boolean lay = true;
    private int mH;
    private int curCha = 0;
    private boolean moving_up = false;
    private boolean duo = false;
    private int add=0;
    private int sub=0;

    public MyPulltoRefreshView(Context context) {
        this(context, null);
    }

    public MyPulltoRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPulltoRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initSelf();
        initHeadView();
        initImg();
    }

    private void initImg() {
        if (im_logo != null) {
            Drawable drawable = im_logo.getDrawable();
            drawHeight = drawable.getIntrinsicHeight();
            drawWidth = drawable.getIntrinsicWidth();
            System.out.println("drawHeight:---" + drawHeight);
            System.out.println("drawWidth:---" + drawWidth);
            matrix = new Matrix();
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_tishi);
            bitHeight = bitmap.getHeight();
            bitWidth = bitmap.getWidth();
            // LayoutParams params=new LayoutParams(bitWidth,bitHeight);
            //im_logo.setLayoutParams(params);
            im_logo.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_tishi));
        }
    }

    private void initSelf() {
        selfViewHeight = this.getMeasuredHeight();
    }

    private void initHeadView() {
        refreshView = View.inflate(getContext(), R.layout.refresh_layout, null);
        im_logo = (ImageView) refreshView.findViewById(R.id.im_logo);
//        tv_refresh = (TextView) refreshView.findViewById(R.id.tv_refresh);

        //提前测量高度
        refreshView.measure(0, 0);//0代表不约束子控件测量
        refreshViewHeight = refreshView.getMeasuredHeight();//获取下拉刷新控件高度
        System.out.println("下拉控件高度——————" + refreshViewHeight);
//        refreshView.setPadding(0,-300,0,0);//设置内边距把view隐藏起来
        addView(refreshView, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        System.out.println(count);
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            currentHeight += childView.getMeasuredHeight() + childView.getPaddingTop();
        }
//        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
//        mlp.height = currentHeight;
//        mlp.width = getMeasuredWidth();
//        setLayoutParams(mlp);
//        getChildAt(0).setPadding(0,-refreshViewHeight,0,0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ScrollView secChild = (ScrollView) getChildAt(1);
        canRefresh = secChild.getScrollY();
//        System.out.println("sc:"+secChild.getScrollY());
        if (!isEnabled()) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lSc_y = ev.getY();
                System.out.println("dSc_Y:" + dSc_y);
                break;
            case MotionEvent.ACTION_MOVE:
                nSc_y = ev.getY();
                System.out.println("nSc_y:" + nSc_y);
                float cha = nSc_y - lSc_y;
//                System.out.println("cha:"+cha);
                lSc_y = nSc_y;
                if (canRefresh <= 0) {
                    if (cha <0) {
                        return false;
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("触控按下");
                break;
            case MotionEvent.ACTION_MOVE:
                params = im_logo.getLayoutParams();
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                System.out.println("手指：" + pointerId);
                if (pointerId != 0 && pointerId != 1) {
                    break;
                }
                if (!moving_up) {
                    //System.out.println("触控");
                    nY = event.getY();
                    if (lY != -1) {
                        if (!duo) {
                            cha1 = nY - lY;
                        } else {
                            cha1 = 0;
                            duo = false;
                        }
                        total_cha1 += (double) cha1;
                        //System.out.println("total_cha1:--" + total_cha1);
                        System.out.println("cha1:--" + cha1);
                        if (total_cha1 >= 0&&curCha>=0) {
                            double per = total_cha1 / (double) 500;
                            //double damp = Math.pow(2, -per);
                            //int damp1 = countShe(damp);
                            System.out.println("per:--" + per);
                            if (per < 1) {
                                getChildAt(0).offsetTopAndBottom((int) (cha1));
                                getChildAt(1).offsetTopAndBottom((int) (cha1));
                                System.out.println("per" + "0");
                                curCha += (int) (cha1);
                                //System.out.println("total_cha1:--" + total_cha1);
                                System.out.println("curCha:--" + curCha);
                                params.height = (int) (per * drawHeight+1);
                                params.width = (int) (per * drawWidth+1);
                                lay = false;
                                im_logo.setLayoutParams(params);
                                if (cha1>0){
                                    add += (int)cha1;
                                }else {
                                    sub += (int)cha1;
                                }
                            } else {
                                //保留两位小数
                                //countTwo(per);
                                params.height = drawHeight;
                                params.width  = drawWidth ;
                                lay = false;
                                im_logo.setLayoutParams(params);
                                double damp = Math.pow(2, -per);
                                System.out.println("per" + "1");
                                //getChildAt(0).offsetTopAndBottom((int) (cha1 * damp));
                                //getChildAt(1).offsetTopAndBottom((int) (cha1 * damp));
                                curCha += (int) (cha1 * damp);
                                if (cha1>0){
                                    add += (int) (cha1* damp);
                                }else {
                                    sub += (int) (cha1* damp);
                                }
                            }

                        } else {
                            //if (curCha > 0) {
                            //getChildAt(0).offsetTopAndBottom(-curCha);
                            //getChildAt(1).offsetTopAndBottom(-curCha);
                            curCha = 0;
                            add=0;
                            sub=0;
                            System.out.println("curCha:--" + "之灵");

                            //}
                        }

                    }
                    lY = nY;
                    System.out.println("add:"+add+"---"+"sub:"+sub+" --curCha:"+curCha);
                }

                break;
            case MotionEvent.ACTION_UP:
                lY = -1;
                if (curCha > 0) {  //total_cha1
                    moving_up = true;
                    View v1 = getChildAt(0);
                    View v2 = getChildAt(1);
//                    v1.offsetTopAndBottom(-curCha);
//                    v2.offsetTopAndBottom(-curCha);
//                    ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(v1,"translationY",-curCha);
//                    objectAnimator.setDuration(800);
                    //v1.offsetTopAndBottom(-curCha);
//                    objectAnimator.start();
//                    TranslateAnimation ta=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0
//                    ,Animation.RELATIVE_TO_SELF,0,Animation.ABSOLUTE,-curCha);
//                    int time=(int) (800*(double)curCha/500);
//                    ta.setDuration(time);
//                    ta.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            v1.offsetTopAndBottom(-curCha);
//                            v2.offsetTopAndBottom(-curCha);
//                            curCha = 0;
//                            //total_cha1 = 0;
//                            params.height = 5;
//                            params.width = 5;
//                            im_logo.setLayoutParams(params);
//                            moving_up = false;
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
                    startRefreshViewAnimation(curCha, v1, v2, true);
                    startRefreshViewAnimation(curCha, v1, v2, false);
                    //v1.startAnimation(ta);
                    //ObjectAnimator animator=ObjectAnimator.ofFloat(v2,"translationY",-curCha);
                    //animator.setDuration(800);

                    //animator.start();
                    //v2.startAnimation(ta);
                }
                total_cha1 = 0;
                //params.height = 5;
                //params.width = 5;
                //im_logo.setLayoutParams(params);
                //curCha = 0;
                //moving_up = false;
                System.out.println(1);
                System.out.println("触控抬起");
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                duo = true;
                System.out.println("多点触控按下");


                break;
            case MotionEvent.ACTION_POINTER_UP:
                duo = true;
                System.out.println("多点触控抬起");
                break;
        }
        return super.onTouchEvent(event);

    }

    private int countShe(double d) {
        if (d >= 0) {
            d = d + 0.5;
            return (int) d;
        } else {
            d = d - 0.5;
            return (int) d;
        }
    }

    private void startRefreshViewAnimation(int dis, final View myReView, final View scView, final boolean reView) {
        TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                , Animation.RELATIVE_TO_SELF, 0, Animation.ABSOLUTE, -curCha);
        int time = (int) (700 * (double) dis / 500);
        ta.setDuration(time);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (reView) {
                    myReView.offsetTopAndBottom(-curCha);
                    //scView.offsetTopAndBottom(-curCha);
                    //MyPulltoRefreshView.this.offsetTopAndBottom(-curCha);
                    //curCha = 0;
//                    params.height = 1;
//                    params.width = 1;
//                    im_logo.setLayoutParams(params);
                    //moving_up = false;
                } else {
                    scView.offsetTopAndBottom(-curCha);
                    moving_up = false;
                    curCha = 0;
                    params.height = 1;
                    params.width = 1;
                    im_logo.setLayoutParams(params);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (reView) {
            //ta.setDuration(time);
            myReView.startAnimation(ta);
        } else {
            //ta.setDuration(time);
            scView.startAnimation(ta);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (lay) {
            int layHeight = 0;
            int childCount = getChildCount();

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                int cHeight = child.getMeasuredHeight();
                child.layout(l, layHeight, r, layHeight + cHeight);
                layHeight += cHeight;
            }
            getChildAt(0).offsetTopAndBottom(-refreshViewHeight);
            getChildAt(1).offsetTopAndBottom(-refreshViewHeight);
        } else {
            int mH = getChildAt(0).getMeasuredHeight();
            int v2height = getChildAt(1).getMeasuredHeight();
            getChildAt(0).layout(l, -mH + curCha, r, curCha);
            getChildAt(1).layout(l, curCha, r, v2height + curCha);
        }

    }
}