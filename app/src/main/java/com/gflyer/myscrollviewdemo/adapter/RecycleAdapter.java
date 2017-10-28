package com.gflyer.myscrollviewdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gflyer.myscrollviewdemo.R;
import com.gflyer.myscrollviewdemo.beans.RecycleData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gflyer on 2017/7/21.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {
    private ArrayList<RecycleData> list;

    public RecycleAdapter(ArrayList<RecycleData> list){
        this.list=list;
        System.out.println(list.size());

    }
    //xml->view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consume_finance,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.im_consume_module.setImageResource(R.mipmap.ic_home_banner_bg);
        System.out.println(list.get(position).getTitle());
        holder.tv_consume_title.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private  TextView tv_consume_title;
        private  ImageView im_consume_module;

        public MyViewHolder(View itemView) {
            super(itemView);
            im_consume_module = (ImageView) itemView.findViewById(R.id.im_consume_module);
            tv_consume_title = (TextView) itemView.findViewById(R.id.tv_consume_title);
        }
    }
}
