package com.gflyer.myscrollviewdemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gflyer.myscrollviewdemo.adapter.RecycleAdapter;
import com.gflyer.myscrollviewdemo.beans.RecycleData;

import java.util.ArrayList;

/**
 * Created by Gflyer on 2017/7/21.
 */

public class RecycleActivity extends AppCompatActivity {

    private RecyclerView rec_data;
    private ArrayList<RecycleData> list;
    private RecycleAdapter adapter;
    private ExpandedLinearLayoutManager layoutManager;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        initData();
        initView();

    }

    private void initView() {
        layoutManager = new ExpandedLinearLayoutManager(this,ExpandedLinearLayoutManager.VERTICAL,false);
        if (list!=null&&list.size()>0) {
            adapter = new RecycleAdapter(list);
        }
        scrollView = (NestedScrollView) findViewById(R.id.nsc_recycle);
        scrollView.smoothScrollTo(0,0);
        rec_data = (RecyclerView) findViewById(R.id.rec_data);
        rec_data.setNestedScrollingEnabled(false);
        //rec_data.smoothScrollTo(0,0);
        rec_data.setLayoutManager(layoutManager);
        rec_data.setAdapter(adapter);

    }

    private void initData() {
        list = new ArrayList<>();
        for (int i=0;i<10;i++) {
            RecycleData data=new RecycleData("员工贷");
            list.add(data);
        }
    }

}
