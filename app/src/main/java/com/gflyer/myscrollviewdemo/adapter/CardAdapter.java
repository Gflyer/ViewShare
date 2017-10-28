package com.gflyer.myscrollviewdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gflyer.myscrollviewdemo.R;
import com.gflyer.myscrollviewdemo.beans.CardData;
import com.gflyer.myscrollviewdemo.utils.FastBlurUtils;

import java.util.List;

/**
 * Created by Gflyer on 2017/8/3.
 */

public class CardAdapter extends PagerAdapter {
    private List<CardData> cardList;
    private Context context;
    private LayoutInflater inflater;
    //private int scaleRatio = 50;//增大scaleRatio缩放比，使用一样更小的bitmap去虚化可以得到更好的模糊效果，而且有利于占用内存的减小；
    //private int blurRadius = 8;//增大blurRadius，可以得到更高程度的虚化，不过会导致CPU更加intensive

    public CardAdapter(Context context, List<CardData> cardList) {
        this.context = context;
        this.cardList = cardList;
        inflater = LayoutInflater.from(context);

    }

    //获取当前窗体界面数
    @Override
    public int getCount() {
        return cardList.size();
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewGroup.LayoutParams params = container.getLayoutParams();
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), cardList.get(position).getId());
        params.width = originalBitmap.getWidth();
        params.height = originalBitmap.getHeight();
        container.setLayoutParams(params);
        View view = inflater.inflate(R.layout.item_card, container, false);
        ImageView myCard = (ImageView) view.findViewById(R.id.im_card);
        myCard.setImageResource(cardList.get(position).getId());
        //System.out.println("width1123:"+myCard.getWidth()+"----height"+myCard.getHeight());
        container.addView(view);
        return view;
    }

    //这个方法，是从ViewGroup中移出当前View
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
