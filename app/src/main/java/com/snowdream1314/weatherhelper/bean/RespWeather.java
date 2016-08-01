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
    private RespWeatherAlarm alarm;
    private RespWeatherYesterday yesterday;
    private List<RespWeatherForecastWeather> forecastWeathers;
    private List<RespWeatherZhishu> zhishus;

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getUpdatetime() { return updatetime; }

    public void setUpdatetime(String updatetime) { this.updatetime = updatetime; }

    public String getWendu() { return wendu; }

    public void setWendu(String wendu) { this.wendu = wendu; }

    public String getFengli() { return fengli; }

    public void setFengli(String fengli) { this.fengli = fengli; }

    public String getShidu() { return shidu; }

    public void setShidu(String shidu) { this.shidu = shidu; }

    public String getFengxiang() { return fengxiang; }

    public void setFengxiang(String fengxiang) { this.fengxiang = fengxiang; }

    public String getSunrise() { return  sunrise; }

    public void setSunrise(String sunrise) { this.sunrise = sunrise; }

    public String getSunset() { return sunset; }

    public void setSunset(String sunset) { this.sunset = sunset; }

    public RespWeatherEnvironment getEnvironment() { return environment; }

    public void setEnvironment(RespWeatherEnvironment environment) { this.environment = environment; }

    public RespWeatherAlarm getAlarm() { return alarm; }

    public void setAlarm(RespWeatherAlarm alarm) { this.alarm = alarm; }

    public RespWeatherYesterday getYesterday() { return yesterday; }

    public void setYesterday(RespWeatherYesterday yesterday) { this.yesterday = yesterday; }

    public List<RespWeatherForecastWeather> getForecastWeathers() { return forecastWeathers; }

    public void setForecastWeathers(List<RespWeatherForecastWeather> forecastWeathers) { this.forecastWeathers = forecastWeathers; }

    public List<RespWeatherZhishu> getZhishus() { return zhishus; }

    public void setZhishus(List<RespWeatherZhishu> zhishus) { this.zhishus = zhishus; }
}
