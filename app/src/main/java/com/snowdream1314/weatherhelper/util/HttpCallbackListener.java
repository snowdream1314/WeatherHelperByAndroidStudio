package com.snowdream1314.weatherhelper.util;

public interface HttpCallbackListener {

	void onFinish(String response);
	void onError(Exception e);
}
