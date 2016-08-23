package com.snowdream1314.weatherhelper.bean;

public class ChoosedCity {
	
	private String name;
	private String subName;
	private String code;
	private int imageId;
	private String tempLow;
	private String tempHigh;
	private String weather;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getSubName() { return subName; }

	public void setSubName(String subName) { this.subName = subName; }
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getWeather() {
		return weather;
	}
	
	public void setWeather(String weather) {
		this.weather = weather;
	}
	
	public int getImageId() {
		return imageId;
	}
	
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
	public String getTempLow() {
		return tempLow;
	}
	
	public void setTempLow(String tempLow) {
		this.tempLow = tempLow;
	}
	
	public String getTempHigh() {
		return tempHigh;
	}
	
	public void setTempHigh(String tempHigh) {
		this.tempHigh = tempHigh;
	}
}

