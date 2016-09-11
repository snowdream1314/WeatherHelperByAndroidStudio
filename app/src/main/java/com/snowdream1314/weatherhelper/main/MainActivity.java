package com.snowdream1314.weatherhelper.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
//    private ChangeTabBroadcastReceiver changeTabBroadcastReceiver;
    private boolean onResumeCalled = false;
    private int changeTabIndex = -1;

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

//        FragmentTabHost.TabSpec group = tabHost.newTabSpec("圈子").setIndicator(initView("圈子", R.drawable.selector_tabhost_image_group));
//        tabHost.addTab(group, UserCenterFragment.class, null);

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


    @Override
    public void onResume() {
        super.onResume();
        Log.e("mainactivity:","resume");
        if (getIntent().getBooleanExtra("exit", false)) {
            finish();
        }

//        onResumeCalled = true;

        changeTabIndex();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("onNewIntent", " called");
        setIntent(intent);
        getIntent().putExtras(intent);//将最新的intent共享出去
        changeTabIndex();

    }

    @Override
    public void onPause() {
        super.onPause();
        onResumeCalled = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        this.unregisterReceiver(changeTabBroadcastReceiver);
    }

    private void changeTabIndex() {
        changeTabIndex = getIntent().getIntExtra("tab", -1);
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

    //双击退出程序
    private static boolean isExit = false;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){

            if(!isExit){
                isExit = true;
                Toast.makeText(this, "再按一次后退键退出程序!", Toast.LENGTH_SHORT).show();
                new CountDownTimer(2200,2200){

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        isExit = false;
                    }
                }.start();
            }else{
                finish();
            }

            return false;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("mainactivity-->", "onActivityResult");
        if (resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent();
            intent.putExtra("update", true);
//            intent.putExtra("del_position", data.getIntExtra("del_position", -1));
            intent.putExtra("isFromManageCityActivity", true);
            getIntent().putExtras(intent);
        }
    }

}
