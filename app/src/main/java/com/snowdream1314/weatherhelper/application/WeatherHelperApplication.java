package com.snowdream1314.weatherhelper.application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by xxq on 2016/8/5.
 */
public class WeatherHelperApplication extends Application{

    private static WeatherHelperApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LeakCanary.install(this);
    }

    public static WeatherHelperApplication getInstance(){
        return instance;
    }
}
