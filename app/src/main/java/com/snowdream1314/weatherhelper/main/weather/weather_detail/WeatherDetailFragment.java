package com.snowdream1314.weatherhelper.main.weather.weather_detail;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.PullRequestMoreFragment;
import com.snowdream1314.weatherhelper.bean.ChoosedCity;
import com.snowdream1314.weatherhelper.bean.RespWeather;
import com.snowdream1314.weatherhelper.main.weather.manage_city.ManageCityActivity;
import com.snowdream1314.weatherhelper.util.AppUtil;
import com.snowdream1314.weatherhelper.util.CoolWeatherDB;
import com.snowdream1314.weatherhelper.util.Utility;
import com.snowdream1314.weatherhelper.util.WHRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetailFragment extends PullRequestMoreFragment implements WHRequest.WHRequestDelegate{

    private View rootView;
    private CoolWeatherDB coolWeatherDB;

    private boolean initial = false;
    private boolean initZhishu = false;
    private RespWeather weather;

    private TextView weatherTextView, tempTextView, windLevelTextView, humidityTextView, todayAQITextView, todayTempTextView, todayWeatherTextView,
                        tomorrowAQITextView, tomorrowTempTextView, tomorrowWeatherTextView, zhishuNameTextView, zhishuDatailTextView;
    private ImageView windImageView,todayWeatherImageView, tomorrowWeatherImageView;

    private LinearLayout weatherLinearLayout, zhishuLinearLayout;
    private RelativeLayout titleLayout;

    private String title, subTitle;
    private String cityCode;
    private int zhishuNum = -1;

    private AlphaAnimation toLight, toDark;

    public WeatherDetailFragment() {
        // Required empty public constructor
    }

    public static WeatherDetailFragment instance(RespWeather weather, String title, String subTitle, String cityCode) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        fragment.weather = weather;
        fragment.title = title;
        fragment.subTitle = subTitle;
        fragment.cityCode = cityCode;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("detailfragment-->", "onCreateView");

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.weather_detail_layout, null);
            coolWeatherDB = CoolWeatherDB.getInstance(getContext());

            titleLayout = (RelativeLayout) rootView.findViewById(R.id.rl_title);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titleLayout.getLayoutParams();
            params.height = params.height + AppUtil.getStatusHeight(getContext());
            titleLayout.setLayoutParams(params);

            showLeftButton(rootView, clickListener);
            showShareButton(rootView, clickListener);
            showFeedsButton(rootView, clickListener);

            setTitleLayoutTitle(rootView, title);
            setTitleLayoutSubTitle(rootView, subTitle);

            initViews();
            initViewsData();
            initZhishu();

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
        tomorrowTempTextView = (TextView) rootView.findViewById(R.id.tv_tomorrow_temp);
        tomorrowWeatherTextView = (TextView) rootView.findViewById(R.id.tv_tomorrow_weather);
        weatherLinearLayout = (LinearLayout) rootView.findViewById(R.id.ll_weather_layout);
        zhishuLinearLayout = (LinearLayout) rootView.findViewById(R.id.ll_zhishu);
        zhishuDatailTextView = (TextView) rootView.findViewById(R.id.tv_zhishu_detail);
        zhishuNameTextView = (TextView) rootView.findViewById(R.id.tv_zhishu_title);

        windImageView = (ImageView) rootView.findViewById(R.id.iv_trend_wind);
        todayWeatherImageView = (ImageView) rootView.findViewById(R.id.iv_today_weather);
        tomorrowWeatherImageView = (ImageView) rootView.findViewById(R.id.iv_tomorrow_weather);

    }

    private void initViewsData() {

        if (weather == null) return;

        weatherTextView.setText(weather.getForecastWeathers().get(0).getDays().get(0).getType());

        windLevelTextView.setText(weather.getFengli());
        humidityTextView.setText(weather.getShidu());
        tempTextView.setText(weather.getWendu() + "°");

        if(weather.getForecastWeathers().get(0).getDays().get(0).getType().equals(weather.getForecastWeathers().get(0).getDays().get(0).getType())) {
            todayWeatherTextView.setText(weather.getForecastWeathers().get(0).getDays().get(0).getType());
        }else {
            todayWeatherTextView.setText(weather.getForecastWeathers().get(0).getDays().get(0).getType() + "转" + weather.getForecastWeathers().get(0).getDays().get(1).getType());
        }
        todayTempTextView.setText(weather.getForecastWeathers().get(0).getHigh().replace("高温","").replace("℃","") + " / " + weather.getForecastWeathers().get(0).getLow().replace("低温",""));

        if (weather.getForecastWeathers().get(1).getDays().get(0).getType().equals(weather.getForecastWeathers().get(1).getDays().get(1).getType())) {
            tomorrowWeatherTextView.setText(weather.getForecastWeathers().get(1).getDays().get(0).getType());
        }else {
            tomorrowWeatherTextView.setText(weather.getForecastWeathers().get(1).getDays().get(0).getType() + "转" + weather.getForecastWeathers().get(1).getDays().get(1).getType());
        }
        tomorrowTempTextView.setText(weather.getForecastWeathers().get(1).getHigh().replace("高温","").replace("℃","") + " / " + weather.getForecastWeathers().get(1).getLow().replace("低温",""));

        if (weather.getEnvironment() != null) {
            todayAQITextView.setText( " " + weather.getEnvironment().getAqi() + " " + weather.getEnvironment().getQuality() + " ");
        }

        setWeatherImageView();
    }

    private void setWeatherImageView() {
        try {
            JSONArray jsonArray = new JSONArray(readJsonFromRaw(R.raw.weather));
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("weather").equals(weather.getForecastWeathers().get(0).getDays().get(0).getType())) {
                    Log.i("json_weather-->", jsonObject.getString("weather"));
                    int resId = getResources().getIdentifier(getContext().getPackageName() + ":" + jsonObject.getString("icon"), null, null);
                    todayWeatherImageView.setImageResource(resId);
                    todayWeatherImageView.setTag(resId);
                    weatherLinearLayout.setBackgroundResource(getResources().getIdentifier(getContext().getPackageName() + ":" + jsonObject.getString("background_day"), null, null));

                }
                if (jsonObject.getString("weather").equals(weather.getForecastWeathers().get(1).getDays().get(0).getType())) {
                    tomorrowWeatherImageView.setImageResource(getResources().getIdentifier(getContext().getPackageName() + ":" + jsonObject.getString("icon"), null, null));
                }
            }

            JSONArray winds = new JSONArray(readJsonFromRaw(R.raw.wind));
            for(int i = 0; i < winds.length(); i++) {
                JSONObject jsonObject = winds.getJSONObject(i);
                if(jsonObject.getString("wind").equals(weather.getFengxiang())) {
                    Log.i("json_wind-->", jsonObject.getString("wind"));
                    windImageView.setImageResource(getResources().getIdentifier(getContext().getPackageName() + ":" + jsonObject.getString("icon"), null, null));
                }
            }
            saveCity();

//            for (int i = 0; i < weather.getZhishus().size(); i++) {
//                Log.i("zhishu:", weather.getZhishus().get(i).getName() + "-->" + weather.getZhishus().get(i).getValue());
//            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initZhishu() {
        if (weather == null) return;
        if (weather.getZhishus().size() == 0) return;

        if (!initZhishu) {
            initZhishu = true;
            zhishuNum = 0;
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

            zhishuNameTextView.setText(weather.getZhishus().get(zhishuNum).getName() + "：" + weather.getZhishus().get(zhishuNum).getValue());
            zhishuDatailTextView.setText(weather.getZhishus().get(zhishuNum).getDetail());
            zhishuLinearLayout.setAnimation(toLight);
            toLight.startNow();

            toLight.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    zhishuNum += 1;
                    if (zhishuNum == 11) zhishuNum = 0;
                    zhishuLinearLayout.setAnimation(toDark);
                    zhishuNameTextView.setText(weather.getZhishus().get(zhishuNum).getName() + "：" + weather.getZhishus().get(zhishuNum).getValue());
                    zhishuDatailTextView.setText(weather.getZhishus().get(zhishuNum).getDetail());
                    toDark.startNow();
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
                    zhishuLinearLayout.setAnimation(toLight);
                    toLight.startNow();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    private void saveCity() {
        ChoosedCity choosedCity = new ChoosedCity();
        choosedCity.setTempLow(weather.getForecastWeathers().get(0).getLow().replace("低温",""));
        choosedCity.setTempHigh(weather.getForecastWeathers().get(0).getHigh().replace("高温",""));
        choosedCity.setName(title);
        choosedCity.setCode(cityCode);
        choosedCity.setWeather(weather.getForecastWeathers().get(0).getDays().get(0).getType());
        choosedCity.setImageId((int)todayWeatherImageView.getTag());
        coolWeatherDB.saveChoosedCity(choosedCity);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("click", "click");
            switch (v.getId()) {
                case R.id.ib_left://城市管理
                    Intent cityManageIntent = new Intent(getActivity(), ManageCityActivity.class);
                    startActivity(cityManageIntent);
                    break;
                case R.id.ib_feeds://消息
                    break;
                case R.id.ib_share://分享
                    break;
            }
        }
    };

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
        loadData(cityCode);
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

    @Override
    public void onPause() {
        super.onPause();
        Log.i("WeatherDetailFragment->", "onPause");
        toLight.cancel();
        toDark.cancel();
    }

}
