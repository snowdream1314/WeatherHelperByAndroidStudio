package com.snowdream1314.weatherhelper.main.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.bean.AddressComponent;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetailFragment extends Fragment {

    private View rootView;

    //显示城市名
    private TextView cityNameText;

    //显示AQI/PM25指数
    private TextView aqi;
    private TextView pm25;
    private TextView quality;

    //感冒指数
//	private TextView suggest;
    private TextView ganMao;

    //显示发布时间
    private TextView publishText;

    //显示天气描述信息
    private TextView weatherDespText;
    private TextView shidu;
    private TextView sunRise;
    private TextView sunSet;

    //显示当前温度
    private TextView tempNow;

    //显示风向，风力
    private TextView fengXiang;
    private TextView fengLi;

    //显示气温
    private TextView tempLowText;
    private TextView tempHighText;

    //显示当前日期
//	private TextView currentDateText;
    private TextView currentDate;

    //显示更换城市按钮
    private Button switchCity;

    //显示更新天气按钮
    private Button refreshWeather;

    //天气预报
    private TextView fore_date1,fore_date1_weather,fore_date1_tempLow,fore_date1_tempHigh,fore_date1_fx,fore_date1_fl,
            fore_date2,fore_date2_weather,fore_date2_tempLow,fore_date2_tempHigh,fore_date2_fx,fore_date2_fl,
            fore_date3,fore_date3_weather,fore_date3_tempLow,fore_date3_tempHigh,fore_date3_fx,fore_date3_fl,
            fore_date4,fore_date4_weather,fore_date4_tempLow,fore_date4_tempHigh,fore_date4_fx,fore_date4_fl;

    private String cityName;
    private AddressComponent addressComponent;

    public WeatherDetailFragment() {
        // Required empty public constructor
    }

    public static WeatherDetailFragment instance(AddressComponent addressComponent) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        fragment.addressComponent = addressComponent;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.weather_detail_layout, null);

            initViews();

        }
        return rootView;
    }
    
    private void initViews() {
        cityNameText = (TextView) rootView.findViewById(R.id.tv_title);
        aqi = (TextView) rootView.findViewById(R.id.aqi);
        pm25 = (TextView) rootView.findViewById(R.id.pm25);
        publishText = (TextView) rootView.findViewById(R.id.publish_text);
        weatherDespText = (TextView) rootView.findViewById(R.id.weather_desp);
        tempLowText = (TextView) rootView.findViewById(R.id.tempLow);
        tempHighText = (TextView) rootView.findViewById(R.id.tempHigh);
        tempNow = (TextView) rootView.findViewById(R.id.temp_now);
        fengXiang = (TextView) rootView.findViewById(R.id.feng_xiang);
        fengLi = (TextView) rootView.findViewById(R.id.feng_li);
        shidu = (TextView) rootView.findViewById(R.id.shidu_value);
        quality = (TextView) rootView.findViewById(R.id.quality);
        sunRise = (TextView) rootView.findViewById(R.id.sunrise_time);
        sunSet = (TextView) rootView.findViewById(R.id.sunset_time);
        ganMao = (TextView) rootView.findViewById(R.id.ganmao);

        fore_date1 = (TextView) rootView.findViewById(R.id.fore_date1);
        fore_date1_weather = (TextView) rootView.findViewById(R.id.fore_date1_weather);
        fore_date1_tempLow = (TextView) rootView.findViewById(R.id.fore_date1_tempLow);
        fore_date1_tempHigh = (TextView) rootView.findViewById(R.id.fore_date1_tempHigh);
//		fore_date1_fx = (TextView) findViewById(R.id.fore_date1_fx);
        fore_date1_fl = (TextView) rootView.findViewById(R.id.fore_date1_fl);

        fore_date2 = (TextView) rootView.findViewById(R.id.fore_date2);
        fore_date2_weather = (TextView) rootView.findViewById(R.id.fore_date2_weather);
        fore_date2_tempLow = (TextView) rootView.findViewById(R.id.fore_date2_tempLow);
        fore_date2_tempHigh = (TextView) rootView.findViewById(R.id.fore_date2_tempHigh);
//		fore_date2_fx = (TextView) findViewById(R.id.fore_date2_fx);
        fore_date2_fl = (TextView) rootView.findViewById(R.id.fore_date2_fl);

        fore_date3 = (TextView) rootView.findViewById(R.id.fore_date3);
        fore_date3_weather = (TextView) rootView.findViewById(R.id.fore_date3_weather);
        fore_date3_tempLow = (TextView) rootView.findViewById(R.id.fore_date3_tempLow);
        fore_date3_tempHigh = (TextView) rootView.findViewById(R.id.fore_date3_tempHigh);
//		fore_date3_fx = (TextView) findViewById(R.id.fore_date3_fx);
        fore_date3_fl = (TextView) rootView.findViewById(R.id.fore_date3_fl);

        fore_date4 = (TextView) rootView.findViewById(R.id.fore_date4);
        fore_date4_weather = (TextView) rootView.findViewById(R.id.fore_date4_weather);
        fore_date4_tempLow = (TextView) rootView.findViewById(R.id.fore_date4_tempLow);
        fore_date4_tempHigh = (TextView) rootView.findViewById(R.id.fore_date4_tempHigh);
//		fore_date4_fx = (TextView) findViewById(R.id.fore_date4_fx);
        fore_date4_fl = (TextView) rootView.findViewById(R.id.fore_date4_fl);

//		currentDateText = (TextView) findViewById(R.id.current_date);
        currentDate = (TextView) rootView.findViewById(R.id.date);
//		switchCity = (Button) rootView.findViewById(R.id.switch_city);
//		refreshWeather = (Button) rootView.findViewById(R.id.refresh_weather);
    }

    public String getTitle() {
        return (addressComponent.getCity() + addressComponent.getDistrict());
    }

    public String getSubTitle() {
        if (addressComponent.getStreet() != null) {
            return addressComponent.getStreet();
        }
        return "";
    }

}
