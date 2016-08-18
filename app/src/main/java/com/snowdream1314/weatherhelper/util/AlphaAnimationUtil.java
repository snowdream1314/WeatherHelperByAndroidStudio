package com.snowdream1314.weatherhelper.util;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by xxq on 2016/8/18.
 */
public class AlphaAnimationUtil {

    private AlphaAnimation toLight, toDark;

    public AlphaAnimationUtil() {

    }

    public void initAnimation() {
        toLight = new AlphaAnimation(1, 0);//透明度动画
        toLight.setDuration(2000);//动画持续时间
        toLight.setFillAfter(true);//执行完停留在执行完的状态
        toLight.setStartOffset(1000);//开始前等待时间
        toLight.setRepeatCount(0);//动画重复次数

        toDark = new AlphaAnimation(0, 1);
        toDark.setDuration(1500);//动画持续时间
        toDark.setFillAfter(true);//执行完停留在执行完的状态
        toDark.setStartOffset(1000);//开始前等待时间
        toDark.setRepeatCount(0);//动画重复次数


        toLight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        toDark.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
