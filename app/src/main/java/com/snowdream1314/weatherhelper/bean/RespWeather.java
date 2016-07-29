package com.snowdream1314.weatherhelper.bean;

import java.util.List;

/**
 * Created by xxq on 2016/7/29.
 */
public class RespWeather {

    private String city;
    private String updatetime;
    private String wendu;
    private String fengli;
    private String shidu;
    private String fengxiang;
    private String sunrise;
    private String sunset;

    private RespWeatherEnvironment environment;
    private RespWeatherYesterday yesterday;
    private List<RespWeatherForecastWeather> forecastWeathers;
    private List<RespWeatherZhishu> zhishus;

    private String getCity() { return city; }

    private void setCity(String city) { this.city = city; }

    private String getUpdatetime() { return updatetime; }

    private void setUpdatetime(String updatetime) { this.updatetime = updatetime; }

    private String getWendu() { return wendu; }

    private void setWendu(String wendu) { this.wendu = wendu; }

    private String getFengli() { return fengli; }

    private void setFengli(String fengli) { this.fengli = fengli; }

    private String getShidu() { return shidu; }

    private void setShidu(String shidu) { this.shidu = shidu; }

    private String getFengxiang() { return fengxiang; }

    private void setFengxiang(String fengxiang) { this.fengxiang = fengxiang; }

    private String getSunrise() { return  sunrise; }

    private void setSunrise(String sunrise) { this.sunrise = sunrise; }

    private String getSunset() { return sunset; }

    private void setSunset(String sunset) { this.sunset = sunset; }

    private RespWeatherEnvironment getEnvironment() { return environment; }

    private void setEnvironment(RespWeatherEnvironment environment) { this.environment = environment; }

    private RespWeatherYesterday getYesterday() { return yesterday; }

    private void setYesterday(RespWeatherYesterday yesterday) { this.yesterday = yesterday; }

    private List<RespWeatherForecastWeather> getForecastWeathers() { return forecastWeathers; }

    private void setForecastWeathers(List<RespWeatherForecastWeather> forecastWeathers) { this.forecastWeathers = forecastWeathers; }

    private List<RespWeatherZhishu> getZhishus() { return zhishus; }

    private void setZhishus(List<RespWeatherZhishu> zhishus) { this.zhishus = zhishus; }
}
