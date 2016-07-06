package com.snowdream1314.weatherhelper.bean;

import java.io.Serializable;

/**
 * Created by xxq on 2016/7/6.
 */
public class AddressComponent implements Serializable{

    private String city;
    private String direction;
    private String distance;
    private String district;
    private String province;
    private String street;
    private String street_num;

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getDirection() { return direction; }

    public void setDirection(String direction) { this.direction = direction; }

    public String getDistance() { return distance; }

    public void setDistance(String distance) { this.distance = distance; }

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    public String getProvince() { return province; }

    public void setProvince(String province) { this.province = province; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getStreet_num() { return street_num; }

    public void setStreet_num(String street_num) { this.street_num = street_num; }
}
