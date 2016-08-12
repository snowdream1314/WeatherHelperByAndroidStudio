package com.snowdream1314.weatherhelper.main.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.PullRequestMoreFragment;
import com.snowdream1314.weatherhelper.base.TitleLayoutFragment;
import com.snowdream1314.weatherhelper.bean.AddressComponent;
import com.snowdream1314.weatherhelper.bean.RespWeather;
import com.snowdream1314.weatherhelper.util.CoolWeatherDB;
import com.snowdream1314.weatherhelper.util.JsonUtil;
import com.snowdream1314.weatherhelper.util.Utility;
import com.snowdream1314.weatherhelper.util.WHRequest;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetailFragment extends PullRequestMoreFragment implements WHRequest.WHRequestDelegate{

    private View rootView;
    private CoolWeatherDB coolWeatherDB;

    private boolean initial = false;
    private RespWeather weather;

    private TextView weatherTextView, tempTextView, windLevelTextView, humidityTextView, todayAQITextView, todayTempTextView, todayWeatherTextView,
                        tomorrowAQITextView, tomorrowTempTextView, tomorrowWeatherTextView;
    private ImageView windImageView,todayWeatherImageView, tomorrowWeatherImageView;

    private LinearLayout weatherLinearLayout;

    public WeatherDetailFragment() {
        // Required empty public constructor
    }

    public static WeatherDetailFragment instance(RespWeather weather) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        fragment.weather = weather;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.weather_detail_layout, null);
            coolWeatherDB = CoolWeatherDB.getInstance(getContext());

            initViews();

            //下拉刷新
            initRefreshLayout(rootView);

        }
        return rootView;
    }
    
    private void initViews() {

        Log.i("detail_fragment", "initView");

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
        weatherLinearLayout = (LinearLayout) rootView.findViewById(R.id.ll_weather_layout);

        windImageView = (ImageView) rootView.findViewById(R.id.iv_trend_wind);
        todayWeatherImageView = (ImageView) rootView.findViewById(R.id.iv_today_weather);
        tomorrowWeatherImageView = (ImageView) rootView.findViewById(R.id.iv_tomorrow_weather);

        initViewsData();

    }

    private void initViewsData() {
        weatherTextView.setText(weather.getForecastWeathers().get(0).getDays().get(0).getType());
        tempTextView.setText(weather.getForecastWeathers().get(0).getHigh().replace("高温","").replace("℃","°").replace(" ", ""));
        windLevelTextView.setText(weather.getFengli());
        humidityTextView.setText(weather.getShidu());

        weatherLinearLayout.setBackgroundResource(R.mipmap.bg_na);

        if(weather.getForecastWeathers().get(0).getDays().get(0).getType().equals(weather.getForecastWeathers().get(0).getDays().get(0).getType())) {
            todayWeatherTextView.setText(weather.getForecastWeathers().get(0).getDays().get(0).getType());
        }else {
            todayWeatherTextView.setText(weather.getForecastWeathers().get(0).getDays().get(0).getType() + "转" + weather.getForecastWeathers().get(0).getDays().get(1).getType());
        }
        todayTempTextView.setText(weather.getForecastWeathers().get(0).getHigh().replace("高温","").replace("℃","") + "/" + weather.getForecastWeathers().get(0).getLow().replace("低温",""));

        if (weather.getForecastWeathers().get(1).getDays().get(0).getType().equals(weather.getForecastWeathers().get(1).getDays().get(1).getType())) {
            tomorrowWeatherTextView.setText(weather.getForecastWeathers().get(1).getDays().get(0).getType());
        }else {
            tomorrowWeatherTextView.setText(weather.getForecastWeathers().get(1).getDays().get(0).getType() + "转" + weather.getForecastWeathers().get(1).getDays().get(1).getType());
        }
        tomorrowTempTextView.setText(weather.getForecastWeathers().get(1).getHigh().replace("高温","").replace("℃","") + "/" + weather.getForecastWeathers().get(1).getLow().replace("低温",""));

        if (weather.getEnvironment() != null) {
            todayAQITextView.setText(weather.getEnvironment().getQuality());
            tomorrowAQITextView.setText(weather.getEnvironment().getQuality());
        }

        setWeatherImageView();
    }

    private void setWeatherImageView() {
        try {
            List<HashMap<String , String>> weatherMaps = new ArrayList<HashMap<String, String>>();
            weatherMaps =  JsonUtil.json2HashMapList(readJsonFromRaw(R.raw.weather));
            for(HashMap<String, String> map : weatherMaps) {
                if (map.containsKey(weather.getForecastWeathers().get(0).getDays().get(0).getType())) {
                    todayWeatherImageView.setImageResource(getResources().getIdentifier(getContext().getPackageName() + ":" + map.get("icon"), null, null));
                    weatherLinearLayout.setBackgroundResource(getResources().getIdentifier(getContext().getPackageName() + ":" + map.get("background_day"), null, null));

                }
                if (map.containsKey(weather.getForecastWeathers().get(1).getDays().get(0).getType())) {
                    tomorrowWeatherImageView.setImageResource(getResources().getIdentifier(getContext().getPackageName() + ":" + map.get("icon"), null, null));
                }
            }

            List<HashMap<String , String>> windMaps = new ArrayList<HashMap<String, String>>();
            windMaps =  JsonUtil.json2HashMapList(readJsonFromRaw(R.raw.wind));
            for( HashMap<String, String> map : windMaps) {
                if(map.containsKey(weather.getFengxiang())) {
                    windImageView.setImageResource(getResources().getIdentifier(getContext().getPackageName() + ":" + map.get("icon"), null, null));
                }
            }


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initWindDirection() {
        String wind = weather.getFengli();

    }

    private void loadData(String cityCode) {
        if (!isLoading) {
            isLoading = true;
            WHRequest request = new WHRequest(getContext());
            request.setDelegate(WeatherDetailFragment.this);
            request.queryWeather(cityCode);
        }
    }

    @Override
    public void reloadData() {
        loadData(getCityCode(weather.getCity()));
    }


    @Override
    public void requestSuccess(WHRequest req, String data) {

        if (req.tag == WHRequest.Req_Tag.Tag_Weather) {
            weather = Utility.handleWeatherXMLResponse(getContext(), data);
            initViewsData();
            isLoading = false;
            setRefreshing(false);
        }
    }

    @Override
    public void  requestFail(WHRequest req, String message) {
        isLoading = false;
        setRefreshing(false);
        Log.i("requestFail", message);
    }


    private String getCityCode(String cityName) {
        return coolWeatherDB.loadCity(cityName).getCityCode();
    }

    private String readJsonFromRaw(int file) {
        try {
            InputStream is  = getResources().openRawResource(file);
            byte [] buffer = new byte[is.available()] ;
            is.read(buffer);
            is.close();
            String json = new String(buffer);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
