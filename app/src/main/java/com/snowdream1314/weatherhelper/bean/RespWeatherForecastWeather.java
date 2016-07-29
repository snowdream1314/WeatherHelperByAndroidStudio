package com.snowdream1314.weatherhelper.bean;

/**
 * Created by xxq on 2016/7/29.
 */
public class RespWeatherForecastWeather {

    private String date;
    private String high;
    private String low;

    private day day;
    private night night;

    private String getDate() { return date; }

    private void setDate(String date) { this.date = date; }

    private String getHigh() { return high; }

    private void setHigh(String high) { this.high = high; }

    private String getLow() { return low; }

    private void setLow(String low) { this.low = low; }

    private day getDay() { return day; }

    private void setDay(day day) { this.day = day; }

    private night getNight() { return  night; }

    private void setNight(night night) { this.night = night; }

    public class day {
        private String type;
        private String fengxiang;
        private String fengli;

        private String getType() { return type; }

        private void setType(String type) { this.type = type; }

        private String getFengxiang() { return fengxiang; }

        private void setFengxiang(String fengxiang) { this.fengxiang = fengxiang; }

        private String getFengli() { return fengli; }

        private void setFengli(String fengli) { this.fengli = fengli; }
    }

    public class night {
        private String type;
        private String fengxiang;
        private String fengli;

        private String getType() { return type; }

        private void setType(String type) { this.type = type; }

        private String getFengxiang() { return fengxiang; }

        private void setFengxiang(String fengxiang) { this.fengxiang = fengxiang; }

        private String getFengli() { return fengli; }

        private void setFengli(String fengli) { this.fengli = fengli; }
    }
}
