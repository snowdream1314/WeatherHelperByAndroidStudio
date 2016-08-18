package com.snowdream1314.weatherhelper.bean;

import java.util.List;

/**
 * Created by xxq on 2016/7/29.
 */
public class RespWeatherForecastWeather {

    private String date;
    private String high;
    private String low;

    private day day = new day();
    private night night = new night();
    private List<day> days;

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getHigh() { return high; }

    public void setHigh(String high) { this.high = high; }

    public String getLow() { return low; }

    public void setLow(String low) { this.low = low; }

    public day getDay() { return day; }

    public void setDay(day day) { this.day = day; }

    public night getNight() { return  night; }

    public void setNight(night night) { this.night = night; }

    public List<day> getDays() { return days; }

    public void setDays(List<day> days) { this.days = days; }

    public class day {
        private String type;
        private String fengxiang;
        private String fengli;

        public String getType() { return type; }

        public void setType(String type) { this.type = type; }

        public String getFengxiang() { return fengxiang; }

        public void setFengxiang(String fengxiang) { this.fengxiang = fengxiang; }

        public String getFengli() { return fengli; }

        public void setFengli(String fengli) { this.fengli = fengli; }
    }

    public class night {
        private String type;
        private String fengxiang;
        private String fengli;

        public String getType() { return type; }

        public void setType(String type) { this.type = type; }

        public String getFengxiang() { return fengxiang; }

        public void setFengxiang(String fengxiang) { this.fengxiang = fengxiang; }

        public String getFengli() { return fengli; }

        public void setFengli(String fengli) { this.fengli = fengli; }
    }
}
