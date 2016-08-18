package com.snowdream1314.weatherhelper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.snowdream1314.weatherhelper.bean.City;
import com.snowdream1314.weatherhelper.bean.JsonBean;
import com.snowdream1314.weatherhelper.bean.RespWeather;
import com.snowdream1314.weatherhelper.bean.RespWeatherAlarm;
import com.snowdream1314.weatherhelper.bean.RespWeatherEnvironment;
import com.snowdream1314.weatherhelper.bean.RespWeatherForecastWeather;
import com.snowdream1314.weatherhelper.bean.RespWeatherYesterday;
import com.snowdream1314.weatherhelper.bean.RespWeatherZhishu;

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
	public static RespWeather handleWeatherXMLResponse(Context context, String response) {
        RespWeather weather = new RespWeather();
        RespWeatherAlarm alarm = new RespWeatherAlarm();;
        RespWeatherYesterday yesterday = new RespWeatherYesterday();
        RespWeatherEnvironment environment = new RespWeatherEnvironment();;
        RespWeatherForecastWeather forecastWeather = new RespWeatherForecastWeather();
        List<RespWeatherForecastWeather> forecastWeathers = new ArrayList<RespWeatherForecastWeather>();
        RespWeatherZhishu zhishu = new RespWeatherZhishu();
        List<RespWeatherZhishu> zhishus = new ArrayList<RespWeatherZhishu>();
        RespWeatherYesterday.day_1 day_1 = yesterday.getDay_1();
        RespWeatherYesterday.night_1 night_1 = yesterday.getNight_1();
        RespWeatherForecastWeather.day day = forecastWeather.getDay();
        RespWeatherForecastWeather.night night= forecastWeather.getNight();
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
                        Log.i("START_DOCUMENT", "START_DOCUMENT");
						weatherDatas = new ArrayList<String>();
						break;
					case XmlPullParser.START_TAG:
						if ("city".equals(nodeName)) {
                            weather.setCity(xmlPullParser.nextText());
						}
						else if ("updatetime".equals(nodeName)) {
                            weather.setUpdatetime(xmlPullParser.nextText());
						}
						else if ("wendu".equals(nodeName)) {
                            weather.setWendu(xmlPullParser.nextText());
						}
						else if ("fengli".equals(nodeName)) {
                            weather.setFengli(xmlPullParser.nextText());
						}
						else if ("shidu".equals(nodeName)) {
                            weather.setShidu(xmlPullParser.nextText());
						}
						else if ("fengxiang".equals(nodeName)) {
                            weather.setFengxiang(xmlPullParser.nextText());
						}
						else if ("sunrise_1".equals(nodeName)) {
                            weather.setSunrise(xmlPullParser.nextText());
						}
						else if ("sunset_1".equals(nodeName)) {
                            weather.setSunset(xmlPullParser.nextText());
						}
                        else if ("environment".equals(nodeName)) {
                            environment = new RespWeatherEnvironment();
                        }
						else if ("aqi".equals(nodeName)) {
                            environment.setAqi(xmlPullParser.nextText());
						}
						else if ("pm25".equals(nodeName)) {
                            environment.setPm25(xmlPullParser.nextText());
						}
						else if ("suggest".equals(nodeName)) {
                            environment.setSuggest(xmlPullParser.nextText());
						}
						else if ("quality".equals(nodeName)) {
                            environment.setQuality(xmlPullParser.nextText());
						}
						else if ("MajorPollutants".equals(nodeName)) {
                            environment.setMajorPollutants(xmlPullParser.nextText());
						}
                        else if ("o3".equals(nodeName)) {
                            environment.setO3(xmlPullParser.nextText());
                        }
                        else if ("co".equals(nodeName)) {
                            environment.setCo(xmlPullParser.nextText());
                        }
                        else if ("pm10".equals(nodeName)) {
                            environment.setPm10(xmlPullParser.nextText());
                        }
                        else if ("so2".equals(nodeName)) {
                            environment.setSo2(xmlPullParser.nextText());
                        }
                        else if ("no2".equals(nodeName)) {
                            environment.setNo2(xmlPullParser.nextText());
                        }
                        else if ("time".equals(nodeName)) {
                            environment.setTime(xmlPullParser.nextText());
                        }
                        else if ("alarm".equals(nodeName)) {
                            alarm = new RespWeatherAlarm();
                        }
                        else if ("cityKey".equals(nodeName)) {
                            alarm.setCityKey(xmlPullParser.nextText());
                        }
                        else if ("cityName".equals(nodeName)) {
                            alarm.setCityName(xmlPullParser.nextText());
                        }
                        else if ("alarmType".equals(nodeName)) {
                            alarm.setAlarmType(xmlPullParser.nextText());
                        }
                        else if ("alarmDegree".equals(nodeName)) {
                            alarm.setAlarmDegree(xmlPullParser.nextText());
                        }
                        else if ("alarmText".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            alarm.setAlarmText(xmlPullParser.nextText());
                        }
                        else if ("alarm_details".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            alarm.setAlarmDetails(xmlPullParser.nextText());
                        }
                        else if ("standard".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            alarm.setStandard(xmlPullParser.nextText());
                        }
                        else if ("suggest".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            alarm.setSuggest(xmlPullParser.nextText());
                        }
                        else if ("imgUrl".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            alarm.setImgUrl(xmlPullParser.nextText());
                        }
                        else if ("time".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            alarm.setTime(xmlPullParser.nextText());
                        }

                        else if ("yesterday".equals(nodeName)) {
                            Log.i("yesterday", "yesterday");
//                            eventType = xmlPullParser.next();
                            yesterday = new RespWeatherYesterday();
                        }
                        else if ("date_1".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            yesterday.setDate_1(xmlPullParser.nextText());
                        }
                        else if ("high_1".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            yesterday.setHigh_1(xmlPullParser.nextText());
                        }
                        else if ("low_1".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            yesterday.setLow_1(xmlPullParser.nextText());
                        }
                        else if ("day_1".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            day_1 = new RespWeatherYesterday().getDay_1();
                            night_1 = new RespWeatherYesterday().getNight_1();
                        }
                        else if ("type_1".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            String type_1 = xmlPullParser.nextText();
                            day_1.setType_1(type_1);
                            night_1.setType_1(type_1);
                        }
                        else if ("fx_1".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            String fx_1 = xmlPullParser.nextText();
                            day_1.setFx_1(fx_1);
                            night_1.setFx_1(fx_1);
                        }
                        else if ("fl_1".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            String fl_1 = xmlPullParser.nextText();
                            day_1.setFl_1(fl_1);
                            night_1.setFl_1(fl_1);
                        }

                        else if ("weather".equals(nodeName)) {
                            Log.i("forecast", "forecast");
//                            eventType = xmlPullParser.next();
                            forecastWeather = new RespWeatherForecastWeather();
                        }
                        else if ("date".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            forecastWeather.setDate(xmlPullParser.nextText());
                        }
                        else if ("high".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            forecastWeather.setHigh(xmlPullParser.nextText());
                        }
                        else if ("low".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            forecastWeather.setLow(xmlPullParser.nextText());
                        }
                        else if ("day".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            day = new RespWeatherForecastWeather().getDay();
                            night = new RespWeatherForecastWeather().getNight();
                        }
                        else if ("type".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            String type = xmlPullParser.nextText();
                            day.setType(type);
                            night.setType(type);
                        }
                        else if ("fengxiang".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            String fengxiang = xmlPullParser.nextText();
                            day.setFengxiang(fengxiang);
                            night.setFengxiang(fengxiang);
                        }
                        else if ("fengli".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            String fengli = xmlPullParser.nextText();
                            day.setFengli(fengli);
                            night.setFengli(fengli);
                        }

                        else if ("zhishu".equals(nodeName)) {
                            Log.i("zhishu", "zhishu");
//                            eventType = xmlPullParser.next();
                            zhishu = new RespWeatherZhishu();
                        }
                        else if ("name".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            zhishu.setName(xmlPullParser.nextText());
                        }
                        else if ("value".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            zhishu.setValue(xmlPullParser.nextText());
                        }
                        else if ("detail".equals(nodeName)) {
//                            eventType = xmlPullParser.next();
                            zhishu.setDetail(xmlPullParser.nextText());
                        }

						break;

					case XmlPullParser.END_TAG:

                        if ("environment".equals(nodeName)) {
                            weather.setEnvironment(environment);
                        }else if ("alarm".equals(nodeName)) {
                            weather.setAlarm(alarm);
                        }
                        else if ("day_1".equals(nodeName)) {
                            yesterday.setDay_1(day_1);
                        }
                        else if ("night_1".equals(nodeName)) {
                            yesterday.setNight_1(night_1);
                        }
                        else if ("yesterday".equals(nodeName)) {
                            weather.setYesterday(yesterday);
                        }
                        else if ("day".equals(nodeName)) {
                            forecastWeather.setDay(day);
                        }
                        else if ("night".equals(nodeName)) {
                            forecastWeather.setNight(night);
                        }
                        else if ("weather".equals(nodeName)) {
                            forecastWeathers.add(forecastWeather);
                        }
                        else if ("forecast".equals(nodeName)) {
                            weather.setForecastWeathers(forecastWeathers);
                        }
                        else if ("zhishu".equals(nodeName)) {
                            zhishus.add(zhishu);
                        }
                        else if ("zhishus".equals(nodeName)) {
                            weather.setZhishus(zhishus);
                        }
                        else if ("resp".equals(nodeName)) {
                            //将获得的数据存入SharedPreferences
//                            saveWeatherXml(context, pref, weatherDatas);
//                            saveRespWeather(context, weather);
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
        return weather;
	}

    private static void saveRespWeather(Context context, RespWeather weather) {
        MySharedPreference preference = new MySharedPreference(context);
        preference.setKeyStr("weather", JsonUtil.toJson(weather));
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