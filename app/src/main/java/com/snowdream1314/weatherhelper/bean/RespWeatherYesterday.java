package com.snowdream1314.weatherhelper.bean;

/**
 * Created by xxq on 2016/7/29.
 */
public class RespWeatherYesterday {

    private String date_1;
    private String high_1;
    private String low_1;

    private day_1 day_1 = new day_1();
    private night_1 night_1 = new night_1();

    public String getDate_1() { return date_1; }

    public void setDate_1(String date_1) { this.date_1 = date_1; }

    public String getHigh_1() { return high_1; }

    public void setHigh_1(String high_1) { this.high_1 = high_1; }

    public String getLow_1() { return low_1; }

    public void setLow_1(String low_1) { this.low_1 = low_1; }

    public day_1 getDay_1() { return day_1; }

    public void  setDay_1(day_1 day_1) { this.day_1 = day_1; }

    public night_1 getNight_1() { return night_1; }

    public void setNight_1(night_1 night_1) { this.night_1 = night_1; }

    public class day_1 {
        private String type_1;
        private String fx_1;
        private String fl_1;

        public String getType_1() { return type_1; }

        public void setType_1(String type_1) { this.type_1 = type_1; }

        public String getFx_1() { return fx_1; }

        public void setFx_1(String fx_1) { this.fx_1 = fx_1; }

        public String getFl_1() { return fl_1; }

        public void setFl_1(String fl_1) { this.fl_1 = fl_1; }
    }

    public class night_1 {
        private String type_1;
        private String fx_1;
        private String fl_1;

        public String getType_1() { return type_1; }

        public void setType_1(String type_1) { this.type_1 = type_1; }

        public String getFx_1() { return fx_1; }

        public void setFx_1(String fx_1) { this.fx_1 = fx_1; }

        public String getFl_1() { return fl_1; }

        public void setFl_1(String fl_1) { this.fl_1 = fl_1; }
    }
}
