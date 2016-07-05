package com.snowdream1314.weatherhelper.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.main.usercenter.UserCenterFragment;
import com.snowdream1314.weatherhelper.main.weather.WeatherFragment;

public class MainActivity extends FragmentActivity {

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        initTabHost();
    }

    private void initTabHost() {
        FragmentTabHost.TabSpec weather = tabHost.newTabSpec("天气").setIndicator(initView("天气", R.drawable.selector_tabhost_image_weather));
        tabHost.addTab(weather, WeatherFragment.class, null);

        FragmentTabHost.TabSpec group = tabHost.newTabSpec("圈子").setIndicator(initView("圈子", R.drawable.selector_tabhost_image_group));
        tabHost.addTab(group, UserCenterFragment.class, null);

        FragmentTabHost.TabSpec user = tabHost.newTabSpec("我").setIndicator(initView("我", R.drawable.selector_tabhost_image_user));
        tabHost.addTab(user, UserCenterFragment.class, null);
    }

    private View initView(String name, int resId) {
        View view = View.inflate(this, R.layout.layout_tabhost, null);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView title = (TextView) view.findViewById(R.id.title);
        image.setImageResource(resId);
        title.setText(name);
        return view;

    }
}
