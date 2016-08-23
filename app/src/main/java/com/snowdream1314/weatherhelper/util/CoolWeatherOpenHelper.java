package com.snowdream1314.weatherhelper.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

	//City建表语句
	public static final String CREATE_CITY = "create table City ("
			+ "id integer primary key autoincrement, "
			+ "city_name text, "
			+ "city_pyname text,"
			+ "city_code text, "
			+ "city_num text, "
			+ "province_name text,"
			+ "province_id text)";

	//ChoosedCity建表语句
	public static final String CREATE_CHOOSEDCITY = "create table ChoosedCity ("
			+ "id integer primary key autoincrement, "
			+ "choosedcity_name text, "
			+ "choosedcity_subname text, "
			+ "choosedcity_code text, "
			+ "choosedcity_tempLow text, "
			+ "choosedcity_tempHigh text, "
			+ "choosedcity_weather text, "
			+ "choosedcity_imageID integer)";

	public CoolWeatherOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CITY);//创建City表
		db.execSQL(CREATE_CHOOSEDCITY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
