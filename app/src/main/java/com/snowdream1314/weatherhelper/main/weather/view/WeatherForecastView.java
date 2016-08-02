package com.snowdream1314.weatherhelper.main.weather.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.bean.RespWeather;
import com.snowdream1314.weatherhelper.bean.RespWeatherForecastWeather;

import java.util.List;

/**
 * Created by xxq on 2016/8/2.
 */
public class WeatherForecastView extends LinearLayout{

    private Activity activity;
    private View weatherForecastView;
    private RespWeather weather;

    public WeatherForecastView(Activity activity, RespWeather weather) {
        super(activity);

        this.activity = activity;
        this.weather = weather;

        initView();
    }

    private void initView() {

        if (weatherForecastView == null) {
            weatherForecastView = LayoutInflater.from(getContext()).inflate(R.layout.layout_weather_forecast, null);

        }

        LinearLayout weatherForecastListLayout = (LinearLayout) weatherForecastView.findViewById(R.id.ll_weather_forecast_list);

        LinearLayout linearLayout = null;
        for (RespWeatherForecastWeather forecastWeather : weather.getForecastWeathers()) {
            linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        }
    }
}
