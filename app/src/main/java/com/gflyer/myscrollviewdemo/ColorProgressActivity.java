package com.gflyer.myscrollviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Gflyer on 2017/8/10.
 */

public class ColorProgressActivity extends AppCompatActivity {

    private ColorProgressView cp_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorprogress);
        initView();
    }

    private void initView() {
        cp_view = (ColorProgressView) findViewById(R.id.cp_view);
        cp_view.setTipText("资产总额(元)");
        cp_view.setSumText("30,200.00");
        //cp_view.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        cp_view.setRoundPer(new float[]{0.2f,0.5f,0.3f});
        cp_view.startProgressAnim(3000);
    }
}
