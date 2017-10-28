package com.gflyer.myscrollviewdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gflyer.myscrollviewdemo.utils.ProgressUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_layout_title;
    private MyScrollView sc_view;
    private TextView tv_test;
    private ImageView iv_bg;
    private Button btn_open;
    private CircleProgressView pro_view;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println(msg.arg1);
            pro_view.setProgress(msg.arg1, true);
        }
    };
    private Button btn_recycle;
    private Button btn_card;
    private Button btn_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getScreenHeight();

        ProgressUtils.startAnimation(99.3, pro_view);
    }

    private void getScreenHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        height = metrics.densityDpi;
//        System.out.println(height);
    }

    private void initView() {
        ll_layout_title = (LinearLayout) findViewById(R.id.ll_layout_title);
        sc_view = (MyScrollView) findViewById(R.id.sc_view);
        tv_test = (TextView) findViewById(R.id.tv_test);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_recycle = (Button) findViewById(R.id.btn_recycle);
        btn_card = (Button) findViewById(R.id.btn_card);
        btn_circle = (Button) findViewById(R.id.btn_circle);

        pro_view = (CircleProgressView) findViewById(R.id.pro_view);
        btn_card.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        btn_recycle.setOnClickListener(this);
        btn_circle.setOnClickListener(this);
//        ll_layout_title.setBackgroundColor(Color.BLUE);
        ll_layout_title.getBackground().setAlpha(0);
        sc_view.setMyScrollViewListener(new MyScrollView.MyScrollViewListener() {
            @Override
            public void scrollViewonChange(MyScrollView myScrollView, int l, int t, int oldl, int oldt) {
                int height = iv_bg.getMeasuredHeight();
//                System.out.println(iv_bg);
                if (t > 0) {
                    if (t > height) {
                        ll_layout_title.getBackground().setAlpha(255);
                    } else {
                        Double tt = Double.valueOf(t);
                        Double height_l = Double.valueOf(height);
                        Double alpha = (tt / height_l) * 255;
                        System.out.println(alpha.intValue());
                        ll_layout_title.getBackground().setAlpha(alpha.intValue());
                    }
                } else {
                    ll_layout_title.getBackground().setAlpha(0);
                }
//               System.out.println("l:"+l+"----"+"t:"+t+"----"+"ol:"+oldl+"----"+"ot:"+oldt);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open:
                startActivity(new Intent(this, RefreshActivity.class));
                break;
            case R.id.btn_recycle:
                startActivity(new Intent(this,RecycleActivity.class));
                break;
            case R.id.btn_card:
                startActivity(new Intent(this,CoolCardActicity.class));
                break;
            case R.id.btn_circle:
                startActivity(new Intent(this,ColorProgressActivity.class));
                break;
        }
    }
}
