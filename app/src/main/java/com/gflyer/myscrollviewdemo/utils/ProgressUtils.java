package com.gflyer.myscrollviewdemo.utils;

import android.os.Handler;
import android.os.Message;

import com.gflyer.myscrollviewdemo.CircleProgressView;

import java.math.BigDecimal;

/**
 * 自定义进度动画工具类
 * Created by Gflyer on 2017/7/19.
 */

public class ProgressUtils {

    private static CircleProgressView myView;
    private static Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            double myProgress = 0;
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x00:
                    myProgress = (double) msg.obj;
                    myView.setProgress(myProgress, true);
                    break;
                case 0x01:
                    myProgress = (double) msg.obj;
                    myView.setProgress(myProgress, false);
                    break;
            }
        }
    };


    /**
     * 开始动画
     *
     * @param progress 当前总进度
     * @param view     对应view对象
     */
    public static void startAnimation(final double progress, final CircleProgressView view) {
        if (view != null) {
            myView = view;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (double i = 0; i <= (progress / view.getMax() * 100); i += 0.1) {
                        i = countShe(i);
                        if (i < 10) {
                            Message msg = Message.obtain();
                            msg.what = 0x00;
                            msg.obj = i;
                            myHandler.sendMessage(msg);
                        } else {
                            Message msg = Message.obtain();
                            msg.what = 0x01;
                            msg.obj = i;
                            myHandler.sendMessage(msg);
                        }
                        if ((progress / view.getMax() * 100) < 10) {
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Thread.sleep(2);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }).start();
        }
    }

    private static double countShe(double d) {
        BigDecimal bigDecimal = new BigDecimal(d);
        return bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
