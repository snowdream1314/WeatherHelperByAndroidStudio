package com.snowdream1314.weatherhelper.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.constant.WHConstant;
import com.snowdream1314.weatherhelper.main.usercenter.UserCenterFragment;
import com.snowdream1314.weatherhelper.main.weather.WeatherFragment;
import com.snowdream1314.weatherhelper.util.CoolWeatherDB;
import com.snowdream1314.weatherhelper.util.MySharedPreference;
import com.snowdream1314.weatherhelper.util.Utility;

import java.io.InputStream;

public class MainActivity extends FragmentActivity {

    private FragmentTabHost tabHost;
    private CoolWeatherDB coolWeatherDB;
    private ChangeTabBroadcastReceiver changeTabBroadcastReceiver;
    private boolean onResumeCalled = false;
    private int changeTabIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Rect outRect = new Rect();

        setContentView(R.layout.activity_main);

        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setPadding(0, outRect.top, 0, 0);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        initTabHost();

    }

    private void initTabHost() {
        FragmentTabHost.TabSpec weather = tabHost.newTabSpec("天气").setIndicator(initView("天气", R.drawable.selector_tabhost_image_weather));
        tabHost.addTab(weather, WeatherFragment.class, null);

//        FragmentTabHost.TabSpec group = tabHost.newTabSpec("圈子").setIndicator(initView("圈子", R.drawable.selector_tabhost_image_group));
//        tabHost.addTab(group, UserCenterFragment.class, null);

        FragmentTabHost.TabSpec user = tabHost.newTabSpec("我").setIndicator(initView("我", R.drawable.selector_tabhost_image_user));
        tabHost.addTab(user, UserCenterFragment.class, null);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WHConstant.Broadcast_ChangeTab);
        changeTabBroadcastReceiver = new ChangeTabBroadcastReceiver();
        this.registerReceiver(changeTabBroadcastReceiver, intentFilter);
    }

    private View initView(String name, int resId) {
        View view = View.inflate(this, R.layout.layout_tabhost, null);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView title = (TextView) view.findViewById(R.id.title);
        image.setImageResource(resId);
        title.setText(name);
        return view;

    }

    private class ChangeTabBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WHConstant.Broadcast_ChangeTab)) {
                int tabIndex = intent.getIntExtra("tab", -1);
                Log.i("tabIndex", String.valueOf(tabIndex));
                if (onResumeCalled) {
                    try {
                        tabHost.setCurrentTab(tabIndex);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    changeTabIndex = tabIndex;
                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("mainactivity:","resume");

        onResumeCalled = true;

        if (changeTabIndex != -1) {
            try {
                tabHost.setCurrentTab(changeTabIndex);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                changeTabIndex = -1;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onResumeCalled = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(changeTabBroadcastReceiver);
    }

}
