package com.snowdream1314.weatherhelper.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snowdream1314.weatherhelper.bean.ChoosedCity;
import com.snowdream1314.weatherhelper.bean.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxq on 2016/7/29.
 */
public class CoolWeatherDB {

    //数据库名
    public static final String DB_NAME = "cool_weather";

    //数据库版本
    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    //构造方法私有化
    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    //获取CoolWeatherDB的实例,synchronized 关键字用来给对象和方法或者代码块加锁,当它锁定一个方法或者一个代码块的时候，同一时刻最多只有一个线程执行这段代码.
    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    //将City实例存储到数据库
    public void saveCity(City city) {
        try {
            synchronized (db) {
                if (city != null) {
                    ContentValues values = new ContentValues();
                    values.put("city_name", city.getCityName());
                    values.put("city_pyname",city.getCityPyName());
                    values.put("city_code", city.getCityCode());
                    values.put("city_num", city.getCityNum());
                    values.put("province_name", city.getProvinceName());
                    values.put("province_id", city.getProvinceId());
                    db.insert("City", null, values);
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
        }

    }

    public List<City> loadCities(String provinceName) {
        List<City> list = new ArrayList<City>();
        try {
            synchronized (db) {
                Cursor cursor = db.query("City", null, "province_name= ?", new String[] {provinceName}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        City city = new City();
                        city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                        city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                        city.setCityNum(cursor.getString(cursor.getColumnIndex("city_num")));
                        city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                        city.setProvinceId(cursor.getString(cursor.getColumnIndex("province_id")));
                        city.setProvinceName(provinceName);
                        list.add(city);
                    } while (cursor.moveToNext());
                }
                cursor.close();
//                db.close();
                return list;
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
            return list == null ? null : list;
        }
    }

    public City loadCity(String cityName) {
        City city = new City();
        try {
            synchronized (db) {
                Cursor cursor = db.query("City", null, "city_name= ?", new String[] {cityName}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                    city.setCityNum(cursor.getString(cursor.getColumnIndex("city_num")));
                    city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                    city.setProvinceId(cursor.getString(cursor.getColumnIndex("province_id")));
                    city.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                    cursor.close();
//                    db.close();
                    return city;
                }
                else {
                    cursor.close();
//                    db.close();
                    return null;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
            return city == null ? null : city;
        }
    }

    public List<City> searchCity(String cityName) {
        List<City> list = new ArrayList<City>();
        try {
            synchronized (db) {
                Cursor cursor = db.query("City", null, "city_name like ?", new String[] {"%" + cityName + "%"}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        City city = new City();
                        city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                        city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                        city.setCityNum(cursor.getString(cursor.getColumnIndex("city_num")));
                        city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                        city.setProvinceId(cursor.getString(cursor.getColumnIndex("province_id")));
                        city.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                        list.add(city);
                    } while (cursor.moveToNext());
                }
                cursor.close();
//                db.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
            return list;
        }
    }

    //将choosedcity实例存储到数据库
    public void saveChoosedCity(ChoosedCity choosedCity) {
        try {
            synchronized (db) {
                if (choosedCity != null) {
                    ContentValues values = new ContentValues();
                    values.put("choosedcity_name", choosedCity.getName());
                    values.put("choosedcity_subname", choosedCity.getSubName());
                    values.put("choosedcity_code", choosedCity.getCode());
                    values.put("choosedcity_tempLow", choosedCity.getTempLow());
                    values.put("choosedcity_tempHigh", choosedCity.getTempHigh());
                    values.put("choosedcity_weather", choosedCity.getWeather());
                    values.put("choosedcity_imageID", choosedCity.getImageId());

                    Cursor cursor = db.query("ChoosedCity", null, "choosedcity_code=?", new String[] {choosedCity.getCode()}, null, null, null);
                    if (cursor.getCount() == 0) {
                        db.insert("ChoosedCity", null, values);
                    }else {
                        db.update("ChoosedCity",values, "choosedcity_code=?", new String[] {choosedCity.getCode()});
                    }
                    cursor.close();
//                    db.close();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
        }

    }

    //从数据库获取choosedcity表中的数据
    public List<ChoosedCity> loadChoosedCity() {
        List<ChoosedCity> list = new ArrayList<ChoosedCity>();
        try {
            synchronized(db) {
                Cursor cursor = db.query("ChoosedCity", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        ChoosedCity choosedCity = new ChoosedCity();
                        choosedCity.setCode(cursor.getString(cursor.getColumnIndex("choosedcity_code")));
                        choosedCity.setName(cursor.getString(cursor.getColumnIndex("choosedcity_name")));
                        choosedCity.setSubName(cursor.getString(cursor.getColumnIndex("choosedcity_subname")));
                        choosedCity.setTempLow(cursor.getString(cursor.getColumnIndex("choosedcity_tempLow")));
                        choosedCity.setTempHigh(cursor.getString(cursor.getColumnIndex("choosedcity_tempHigh")));
                        choosedCity.setWeather(cursor.getString(cursor.getColumnIndex("choosedcity_weather")));
                        choosedCity.setImageId(cursor.getInt(cursor.getColumnIndex("choosedcity_imageID")));
                        list.add(choosedCity);
                    } while (cursor.moveToNext());
                }
                cursor.close();
//                db.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
        }
        return list;
    }

    //判断数据库中是否已经存在
    public boolean isChoosedCityExist(String cityCode) {
        synchronized(db) {
            Cursor cursor = db.query("ChoosedCity", null, "choosedcity_code=?", new String[]{cityCode}, null, null, null);
            if (cursor.getCount() == 0) {
                cursor.close();
//                db.close();
                return false;
            }else {
                cursor.close();
//                db.close();
                return true;
            }
        }
    }


    //删除choosedcity表中的数据
    public void delChoosedCity(ChoosedCity choosedCity) {
        try {
            synchronized (db) {
                db.delete("ChoosedCity", "choosedcity_code=?", new String[] {choosedCity.getCode()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
        }
    }

    //更新choosedcity表中的数据
    public void updateChoosedCity(ChoosedCity choosedCity) {
        try {
            synchronized (db) {
                if (choosedCity != null) {
                    ContentValues values = new ContentValues();
                    values.put("choosedcity_name", choosedCity.getName());
                    values.put("choosedcity_subname", choosedCity.getSubName());
                    values.put("choosedcity_code", choosedCity.getCode());
                    values.put("choosedcity_tempLow", choosedCity.getTempLow());
                    values.put("choosedcity_tempHigh", choosedCity.getTempHigh());
                    values.put("choosedcity_weather", choosedCity.getWeather());
                    values.put("choosedcity_imageID", choosedCity.getImageId());
                    db.update("ChoosedCity",values, "choosedcity_code=?", new String[] {choosedCity.getCode()});
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
//            if (db != null && db.isOpen()) {
//                db.close();
//            }
        }
    }

}
