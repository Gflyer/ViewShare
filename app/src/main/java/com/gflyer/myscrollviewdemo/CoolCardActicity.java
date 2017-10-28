package com.gflyer.myscrollviewdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gflyer.myscrollviewdemo.adapter.CardAdapter;
import com.gflyer.myscrollviewdemo.beans.CardData;
import com.gflyer.myscrollviewdemo.utils.FastBlurUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gflyer on 2017/8/3.
 */

public class CoolCardActicity extends AppCompatActivity {

    private ViewPager vp_card;
    private List<CardData> cardList;
    private CardAdapter mCardAdapter;
    private int scaleRatio = 35;//增大scaleRatio缩放比，使用一样更小的bitmap去虚化可以得到更好的模糊效果，而且有利于占用内存的减小；
    private int blurRadius = 7;//增大blurRadius，可以得到更高程度的虚化，不过会导致CPU更加intensive
    private CoolCardRelativeLayout coolRl;
    private Animation exitAnim;
    private Animation enterAnim;
    private ImageView im_bg_pic;
    // private Bitmap originalBitmap;
    private TransitionDrawable transitionDrawable;
    private Drawable[] drawables;
    private List<ImageView> imageList;
    private LinearLayout layout_ll_ind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coolcard);
        initData();
        initView();
        initInd();
        setListener();
    }

    private void initInd() {
        if (layout_ll_ind!=null){
            layout_ll_ind.removeAllViews();
        }else {
            return;
        }
        for (int i = 0; i < cardList.size(); i++) {
            ImageView indView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//为小圆点左右添加间距  
            params.leftMargin = 10;
            params.rightMargin = 10;
//手动给小圆点一个大小  
            params.height = 30;
            params.width = 30;
            if (i==0){
                indView.setImageResource(R.drawable.ind_pressed);
            }else {
                indView.setImageResource(R.drawable.ind_default);
            }
            layout_ll_ind.addView(indView,params);
            imageList.add(indView);
        }
    }

    private void setListener() {
        if (vp_card != null) {
            vp_card.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), cardList.get(position).getId());

                    Drawable oldDrawable = im_bg_pic.getBackground();
                    Drawable oldBitmapDrawable = null;
                    if (oldDrawable == null) {
                        //为空时，设置默认
                    } else if (oldDrawable instanceof TransitionDrawable) {
                        oldBitmapDrawable = ((TransitionDrawable) oldDrawable).getDrawable(1);
                    } else {
                        oldBitmapDrawable = oldDrawable;
                    }
                    drawables = new Drawable[]{oldBitmapDrawable, new BitmapDrawable(getResources(), getBlurBitmap(originalBitmap))};
                    transitionDrawable = new TransitionDrawable(drawables);
                    im_bg_pic.setBackground(transitionDrawable);
                    transitionDrawable.startTransition(800);

                    for (int i=0;i<cardList.size();i++){
                        if (position%cardList.size()==i){
                            imageList.get(i).setImageResource(R.drawable.ind_pressed);
                        }else {
                            imageList.get(i).setImageResource(R.drawable.ind_default);
                        }
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private Bitmap getBlurBitmap(Bitmap origin) {
        int originalWidth = origin.getWidth();
        int originalHeight = origin.getHeight();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(origin, originalWidth / scaleRatio, originalHeight / scaleRatio, false);
        return FastBlurUtils.doBlur(scaledBitmap, blurRadius, true);

    }

    private void initData() {
        imageList=new ArrayList<ImageView>();
        cardList = new ArrayList<>();
        CardData cd1 = new CardData(R.mipmap.first);
        CardData cd2 = new CardData(R.mipmap.second);
        CardData cd3 = new CardData(R.mipmap.first);
        cardList.add(cd1);
        cardList.add(cd2);
        cardList.add(cd3);

    }

    private void initView() {
        layout_ll_ind = (LinearLayout) findViewById(R.id.layout_ll_ind);

        vp_card = (ViewPager) findViewById(R.id.vp_card);
        coolRl = (CoolCardRelativeLayout) findViewById(R.id.layout_rl_bg_card);
        im_bg_pic = (ImageView) findViewById(R.id.im_bg_pic);
        //设置动画
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.enteralpha);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.exitalpha);
//        exitAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                if(originalBitmap!=null){
//                   // im_bg_pic.setImageBitmap(getBlurBitmap(originalBitmap));
//                    im_bg_pic.setBackground(new BitmapDrawable(getResources(),getBlurBitmap(originalBitmap)));
//                    im_bg_pic.startAnimation(enterAnim);
//                }
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        Bitmap firstBitmap = BitmapFactory.decodeResource(getResources(), cardList.get(0).getId());
        //coolRl.setBackground(new BitmapDrawable(getResources(), getBlurBitmap(firstBitmap)));
        im_bg_pic.setBackground(new BitmapDrawable(getResources(), getBlurBitmap(firstBitmap)));
        vp_card.setPageMargin(110);//设置页间距离，数值要计算？？
        vp_card.setOffscreenPageLimit(3);//设置预加载页数
        vp_card.setPageTransformer(false, new MyTransformer());//设置动画
        mCardAdapter = new CardAdapter(this, cardList);
        vp_card.setAdapter(mCardAdapter);
    }

    //设置缩放透明动画
    public class MyTransformer implements ViewPager.PageTransformer {
        public static final float MIN_ALPHA = 0.6f;
        public static final float MIN_SCALE = 0.9f;

        @Override
        public void transformPage(View page, float position) {
            System.out.println(position);
            if (position < -1 || position > 1) {
                page.setAlpha(MIN_ALPHA);
                page.setScaleX(MIN_SCALE);
                page.setScaleY(MIN_SCALE);
            } else if (position >= -1 && position <= 1) {
                if (position < 0) {
                    float scale = 1 + 0.10f * position;
                    page.setScaleX(scale);
                    page.setScaleY(scale);
                    page.setAlpha(MIN_ALPHA + (position + 1) * (1 - MIN_ALPHA));
                } else {
                    float scale = 1 - 0.10f * position;
                    page.setScaleX(scale);
                    page.setScaleY(scale);
                    page.setAlpha(MIN_ALPHA + (1 - position) * (1 - MIN_ALPHA));
                }
            }
        }
    }
}
