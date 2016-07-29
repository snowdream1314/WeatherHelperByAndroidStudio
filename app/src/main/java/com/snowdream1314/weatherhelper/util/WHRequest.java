package com.snowdream1314.weatherhelper.util;

import android.content.Context;
import android.util.Log;

import com.snowdream1314.weatherhelper.constant.WHConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xxq on 2016/7/6.
 */
public class WHRequest implements DataUtil.NetReceiveDelete{

    public enum Req_Tag{
        Tag_City,
        Tag_Weather
    }

    public interface WHRequestDelegate {
        public void requestFail(WHRequest req, String msg);
        public void requestSuccess(WHRequest req, String result);
    }

    private Context mContext;
    private WHRequestDelegate delegate;
    public Req_Tag tag;

    public WHRequest(Context context) {
        this.mContext = context;
    }

    public WHRequestDelegate getDelegate() { return delegate; }

    public void setDelegate(WHRequestDelegate delegate) { this.delegate = delegate; }


    /************************ method *******************************/
    public void queryLocationCity(double latitude, double longitude) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("output", "json");
        params.put("location", latitude + "," + longitude);
        params.put("key", WHConstant.BDAPI_KEY);
        request(WHConstant.GeoCoder_Url, params, Req_Tag.Tag_City);
    }

    public void queryWeather(String cityCode) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("citykey", cityCode);
        request(WHConstant.Weather_APi, params, Req_Tag.Tag_Weather);
    }


    private void request(String url, Map<String, String> params, Req_Tag req_tag) {
        tag =  req_tag;

        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        sb.deleteCharAt(sb.length() - 1);

        Log.i("request_url", sb.toString());

        DataUtil dataUtil = new DataUtil(mContext);
        dataUtil.setDelegate(this);
        dataUtil.GETRequest(sb.toString(), params);
    }

    @Override
    public void receiveSuccess(DataUtil dataUtil, String result) {
        Log.i("receiveSuccess", result);
        delegate.requestSuccess(this, result);
    }

    @Override
    public void receiveFail(DataUtil dataUtil, String message) {
        Log.i("receiveFail", message);
        delegate.requestFail(this, message);
    }

}
