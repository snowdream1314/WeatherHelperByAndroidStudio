package com.snowdream1314.weatherhelper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.snowdream1314.weatherhelper.bean.City;
import com.snowdream1314.weatherhelper.bean.JsonBean;
import com.snowdream1314.weatherhelper.bean.RespWeather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Utility {

	private static String xmlData;
	private static String xmlDatas;
	private static List<String> weatherDatas;

    //解析本地城市列表
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response) {
		parseXMLWithPull(response.toString());
		if (xmlDatas.length() > 0) {
			String[] items = xmlDatas.split(";");
			if (items != null && items.length > 0) {
				for (String item : items) {
					String[] array = item.split(",");
					//只处理国内的
					if (array[0].startsWith("101")) {
						City city= new City();
						city.setCityCode(array[0]);
						city.setCityNum(array[0].substring(5, 7));
						city.setCityName(array[1]);
						city.setCityPyName(array[2]);
						city.setProvinceName(array[3]);
						//将解析出来的数据存储到City表
						coolWeatherDB.saveCity(city);
					}
				}
				return true;
			}
		}
		return false;
	}

	//解析省市列表XML信息
	public static void parseXMLWithPull(String response) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response));
			int eventType = xmlPullParser.getEventType();
			String d1 = "";
			String d2 = "";
			String d3 = "";
			String d4 = "";
			xmlData = null;
			xmlDatas = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = xmlPullParser.getName();
				switch (eventType) {
					//开始解析节点
					case XmlPullParser.START_TAG:
						if ("d".equals(nodeName)) {
							d1 = xmlPullParser.getAttributeValue(0);
							d2 = xmlPullParser.getAttributeValue(1);
							d3 = xmlPullParser.getAttributeValue(2);
							d4 = xmlPullParser.getAttributeValue(3);
							xmlData = d1 + "," + d2 + "," + d3 + "," + d4;
							if (xmlData != null && d1.startsWith("101")) {
								xmlDatas = xmlDatas + ";" + xmlData;
							}
						}
						break;

					case XmlPullParser.END_TAG:
						if ("c".equals(nodeName)) {
//						responseText.setText(texts);
						}
						break;

					default:
						break;
				}
				eventType = xmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//解析服务器返回的XML天气数据
	public static void handleWeatherXMLResponse(Context context, String response, SharedPreferences pref) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response));
			int eventType = xmlPullParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String nodeName = xmlPullParser.getName();
				switch (eventType) {
					//开始解析XML节点
					case XmlPullParser.START_DOCUMENT:
                        RespWeather weather = new RespWeather();
						weatherDatas = new ArrayList<String>();
						break;
					case XmlPullParser.START_TAG:
						if ("city".equals(nodeName)) {
//							cityName = xmlPullParser.nextText();
//							weatherDatas.add(cityName);
						}
						if ("updatetime".equals(nodeName)) {
//							updateTime = xmlPullParser.nextText();
//							weatherDatas.add(updateTime);
						}
						if ("wendu".equals(nodeName)) {
//							tempNow = xmlPullParser.nextText();
//							weatherDatas.add(tempNow.concat("°"));
						}
						if ("fengli".equals(nodeName)) {
//							fengLi = xmlPullParser.nextText();
//							weatherDatas.add(fengLi);
						}
						if ("shidu".equals(nodeName)) {
//							shidu = xmlPullParser.nextText();
//							weatherDatas.add(shidu);
						}
						if ("fengxiang".equals(nodeName)) {
//							fengXiang = xmlPullParser.nextText();
//							weatherDatas.add(fengXiang);
						}
						if ("sunrise_1".equals(nodeName)) {
//							sunrise_1 = xmlPullParser.nextText();
//							weatherDatas.add(sunrise_1);
						}
						if ("sunset_1".equals(nodeName)) {
//							sunset_1 = xmlPullParser.nextText();
//							weatherDatas.add(sunset_1);
						}
						if ("aqi".equals(nodeName)) {
//							aqi = xmlPullParser.nextText();
//							weatherDatas.add(aqi);
						}
						if ("pm25".equals(nodeName)) {
//							pm25 = xmlPullParser.nextText();
//							weatherDatas.add(pm25);
						}
						if ("suggest".equals(nodeName)) {
//							suggest = xmlPullParser.nextText();
//							weatherDatas.add(suggest);
						}
						if ("quality".equals(nodeName)) {
//							quality = xmlPullParser.nextText();
//							weatherDatas.add(quality);
						}
						if ("MajorPollutants".equals(nodeName)) {
//							MajorPollutants = xmlPullParser.nextText();
//							weatherDatas.add(MajorPollutants);
						}

//					if ("weather".equals(nodeName)) {
//						weather =new Weather();
//					}
//					if ("date".equals(nodeName)) {
//						weather.setDate(xmlPullParser.nextText());
//					}
//					if ("high".equals(nodeName)) {
//						weather.setHigh(xmlPullParser.nextText());
//					}
//					if ("low".equals(nodeName)) {
//						weather.setLow(xmlPullParser.nextText());
//					}
//					if ("type".equals(nodeName)) {
//						weather.getDay().setType(xmlPullParser.nextText());
//					}
//					if ("fengxiang".equals(nodeName)) {
//						weather.getDay().setFengXiang(xmlPullParser.nextText());
//					}
//					if ("fengli".equals(nodeName)) {
//						weather.getDay().setFengLi(xmlPullParser.nextText());
//					}
//					if ("type".equals(nodeName)) {
//						weather.getNight().setType(xmlPullParser.nextText());
//					}
//					if ("fengxiang".equals(nodeName)) {
//						weather.getNight().setFengXiang(xmlPullParser.nextText());
//					}
//					if ("fengli".equals(nodeName)) {
//						weather.getNight().setFengLi(xmlPullParser.nextText());
//					}

//					if ("zhishu".equals(nodeName)) {
//						weatherZhiShu = new WeatherZhiShu();
//					}
//					if ("name".equals(nodeName)) {
//						weatherZhiShu.setName(xmlPullParser.nextText());
//					}
//					if ("value".equals(nodeName)) {
//						weatherZhiShu.setValue(xmlPullParser.nextText());
//					}
//					if ("detail".equals(nodeName)) {
//						weatherZhiShu.setDetail(xmlPullParser.nextText());
//					}
						break;

					case XmlPullParser.END_TAG:
						if ("resp".equals(nodeName)) {
							//将获得的数据存入SharedPreferences
//						saveWeatherXml(context, cityName, updateTime, tempNow, fengLi, fengXiang, shidu, sunrise_1,
//								sunset_1, aqi, pm25, suggest, quality, MajorPollutants, weatherList, zhiShus);
							saveWeatherXml(context, pref, weatherDatas);
						}
						break;

					default:
						break;
				}
				eventType = xmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//将服务器返回的所有XML天气信息存储到SharedPreferences文件中
	public static void saveWeatherXml(Context context, SharedPreferences pref, List<String> weatherDatas) {
//		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		SharedPreferences.Editor editor = pref.edit();
//		editor.putString("city_name", weatherDatas.get(0));
		editor.putString("updatetime", weatherDatas.get(1));
//		editor.putString("tempNow", weatherDatas.get(2));
		editor.putString("feng_li_now", weatherDatas.get(3));
		editor.putString("shidu", weatherDatas.get(4));
		editor.putString("feng_xiang_now", weatherDatas.get(5));
		editor.putString("sunrise_1", weatherDatas.get(6));
		editor.putString("sunset_1", weatherDatas.get(7));
//		editor.putString("aqi", weatherDatas.get(8));
		editor.putString("pm25", weatherDatas.get(9));
		editor.putString("suggest", weatherDatas.get(10));
		editor.putString("quality", weatherDatas.get(11));
		editor.putString("MajorPollutants", weatherDatas.get(12));
		editor.commit();
	}

	//解析服务器返回的JSON数据，并存储到本地
//	public static void handleWeatherResponse(Context context, String response) {
//		try {
//			JSONObject jsonObject = new JSONObject(response);
//			JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
//			String cityName = weatherInfo.getString("city");
//			String weatherCode = weatherInfo.getString("cityid");
//			String temp1 = weatherInfo.getString("temp1");
//			String temp2 = weatherInfo.getString("temp2");
//			String weatherDesp = weatherInfo.getString("weather");
//			String publishTime = weatherInfo.getString("ptime");
//			saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

	//解析服务器返回的JSON数据，并存储到本地
	public static void handleWeatherResponse(Context context, String response, SharedPreferences pref) {
		String fengXiang,fengLi,tempHigh,tempLow,weatherDesp,dateNow,cityName,tempNow,aqi,ganmao;
		Gson gson = new Gson();
		JsonBean jsonBean = gson.fromJson(response, JsonBean.class);
		//获取当天天气信息
		try {
			cityName = jsonBean.getData().getCity();
//			tempNow = jsonBean.getData().getWenDU().concat("°");
			tempNow = jsonBean.getData().getWenDU().concat("°");
			aqi = jsonBean.getData().getAqi();
			ganmao = jsonBean.getData().getGanMao();
			for (int i=0; i<5; i++) {
				fengXiang = jsonBean.getData().getForecast().get(i).getFengXiang();
				fengLi = jsonBean.getData().getForecast().get(i).getFengLi();
				if (fengLi.contains("微风")) {
					fengLi = fengLi.replace("级", "");
				}
				tempHigh = jsonBean.getData().getForecast().get(i).getHigh().replace("高温 ", "");
				tempLow = jsonBean.getData().getForecast().get(i).getLow().replace("低温 ", "");
				weatherDesp = jsonBean.getData().getForecast().get(i).getType();
				dateNow = jsonBean.getData().getForecast().get(i).getDate();
				saveWeatherInfo(context, pref, cityName, tempNow, aqi, ganmao, fengXiang, fengLi, tempHigh, tempLow, weatherDesp, dateNow, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//将服务器返回的所有天气信息存储到SharedPreferences文件中
	public static void saveWeatherInfo(Context context, SharedPreferences pref, String cityName, String tempNow, String aqi, String ganmao, String fengXiang, String fengLi, String tempHigh, String tempLow, String weatherDesp, String dateNow, int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
//		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("aqi", aqi);
		editor.putString("ganmao", ganmao);
//		editor.putString("weather_code", weatherCode);
		editor.putString("weather_code", "");
		editor.putString("tempNow", tempNow);
		editor.putString("tempLow".concat("_" + Integer.toString(i)), tempLow.replace("℃",""));
		editor.putString("tempHigh".concat("_" + Integer.toString(i)), tempHigh);
		editor.putString("feng_xiang".concat("_" + Integer.toString(i)), fengXiang);
		editor.putString("feng_li".concat("_" + Integer.toString(i)), fengLi);
		editor.putString("weatherDesp".concat("_" + Integer.toString(i)), weatherDesp);
//		editor.putString("publish_time", publishTime);
		editor.putString("publish_time".concat("_" + Integer.toString(i)), dateNow);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}
}