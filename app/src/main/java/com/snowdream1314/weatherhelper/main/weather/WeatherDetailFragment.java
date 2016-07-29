package com.snowdream1314.weatherhelper.main.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.TitleLayoutFragment;
import com.snowdream1314.weatherhelper.bean.AddressComponent;
import com.snowdream1314.weatherhelper.util.WHRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetailFragment extends Fragment implements WHRequest.WHRequestDelegate{

    private View rootView;

    private String cityName;
    private AddressComponent addressComponent;
    private boolean initial = false;

    private TextView weatherTextView, tempTextView, windLevelTextView, humidityTextView, todayAQITextView, todayTempTextView, todayWeatherTextView,
                        tomorrowAQITextView, tomorrowTempTextView, tomorrowWeatherTextView;
    private ImageView windImageView,todayWeatherImageView, tomorrowWeatherImageView;

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

        weatherTextView = (TextView) rootView.findViewById(R.id.tv_weather);
        tempTextView = (TextView) rootView.findViewById(R.id.tv_temp);
        windLevelTextView = (TextView) rootView.findViewById(R.id.tv_trend_wind_level);
        humidityTextView = (TextView) rootView.findViewById(R.id.tv_humidity);
        todayAQITextView = (TextView) rootView.findViewById(R.id.tv_today_aqi);
        todayTempTextView = (TextView) rootView.findViewById(R.id.tv_today_temp);
        todayWeatherTextView = (TextView) rootView.findViewById(R.id.tv_today_weather);
        tomorrowAQITextView = (TextView) rootView.findViewById(R.id.tv_tomorrow_aqi);
        tomorrowTempTextView = (TextView) rootView.findViewById(R.id.tv_tomorrow_temp);
        tomorrowWeatherTextView = (TextView) rootView.findViewById(R.id.tv_tomorrow_weather);

        windImageView = (ImageView) rootView.findViewById(R.id.iv_trend_wind);
        todayWeatherImageView = (ImageView) rootView.findViewById(R.id.iv_today_weather);
        tomorrowWeatherImageView = (ImageView) rootView.findViewById(R.id.iv_tomorrow_weather);


    }

    private void initData(String cityCode) {
        if (!initial) {
            initial = true;
            WHRequest request = new WHRequest(getContext());
            request.setDelegate(WeatherDetailFragment.this);
            request.queryWeather(cityCode);
        }
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


    @Override
    public void requestSuccess(WHRequest req, String data) {

        if (req.tag == WHRequest.Req_Tag.Tag_Weather) {

        }
    }

    @Override
    public void  requestFail(WHRequest req, String message) {
        Log.i("requestFail", message);
    }

}
