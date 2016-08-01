package com.snowdream1314.weatherhelper.bean;

/**
 * Created by xxq on 2016/8/1.
 */
public class RespWeatherAlarm {

    private String cityKey;
    private String cityName;
    private String alarmType;
    private String alarmDegree;
    private String alarmText;
    private String alarmDetails;
    private String standard;
    private String suggest;
    private String imgUrl;
    private String time;

    public String getCityKey() { return cityKey; }

    public void setCityKey(String cityKey) { this.cityKey = cityKey; }

    public String getCityName() { return cityName; }

    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getAlarmType() { return alarmType; }

    public void setAlarmType(String alarmType) { this.alarmType = alarmType; }

    public String getAlarmDegree() { return alarmDegree; }

    public void setAlarmDegree(String alarmDegree) { this.alarmDegree = alarmDegree; }

    public String getAlarmText() { return alarmText; }

    public void setAlarmText(String alarmText) { this.alarmText = alarmText; }

    public String getAlarmDetails() { return alarmDetails; }

    public void setAlarmDetails(String alarmDetails) { this.alarmDetails = alarmDetails; }

    public String getStandard() { return standard; }

    public void setStandard(String standard) { this.standard = standard; }

    public String getSuggest() { return suggest; }

    public void setSuggest(String suggest) { this.suggest = suggest; }

    public String getImgUrl() { return imgUrl; }

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }
}
