package com.snowdream1314.weatherhelper.main.weather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.TitleLayoutFragment;
import com.snowdream1314.weatherhelper.bean.AddressComponent;
import com.snowdream1314.weatherhelper.constant.WHConstant;
import com.snowdream1314.weatherhelper.util.CoolWeatherDB;
import com.snowdream1314.weatherhelper.util.MySharedPreference;
import com.snowdream1314.weatherhelper.util.Utility;
import com.snowdream1314.weatherhelper.util.WHRequest;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends TitleLayoutFragment implements WHRequest.WHRequestDelegate{

    private View rootView;

    private ViewPager viewPager;
    private List<WeatherDetailFragment> fragments = new ArrayList<WeatherDetailFragment>();

    public LocationClient mLocationClient = null;
    public BDLocationListener mLocationListener = new MyLocationListener();

    private CoolWeatherDB coolWeatherDB;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(mLocationListener);
        coolWeatherDB = CoolWeatherDB.getInstance(getContext());

        MySharedPreference sp = new MySharedPreference(getContext());
        if (!sp.getKeyBoolean(WHConstant.SharePreference_FirstLoadDB)) {
            sp.setKeyBoolean(WHConstant.SharePreference_FirstLoadDB, true);
            initCoolWeatherDB();
        }
    }

    private void initCoolWeatherDB() {
        coolWeatherDB = CoolWeatherDB.getInstance(getContext());
        Log.i("city_list",readXMLFromRaw(R.raw.city_list));
        Utility.handleCitiesResponse(coolWeatherDB, readXMLFromRaw(R.raw.city_list));
    }

    private String readXMLFromRaw(int file) {
        try {
            InputStream is  = getResources().openRawResource(file);
            byte [] buffer = new byte[is.available()] ;
            is.read(buffer);
            is.close();
            String xml = new String(buffer);
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_weather, null);

            View view = rootView;

            initLocation();
            mLocationClient.start();

            showLeftButton(view, clickListener);
            showShareButton(view, clickListener);
            showFeedsButton(view, clickListener);

            viewPager = (ViewPager) rootView.findViewById(R.id.vp_weather);
            WeatherAdapter adapter = new WeatherAdapter(getFragmentManager(), fragments);
            viewPager.setAdapter(adapter);

            if (fragments.size() > 1) {
                showCirclePageIndicator(view);
                circlePageIndicator.setViewPager(viewPager);
            }
        }
        return rootView;

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
            String cityCode = coolWeatherDB.loadCity(location.getCity().replace("市","")).getCityCode();
            setTitleLayoutTitle(rootView, location.getCity() + location.getDistrict());
            setTitleLayoutSubTitle(rootView, location.getStreet() + location.getStreetNumber().split("号")[0] + "号");
            WHRequest request = new WHRequest(getContext());
            request.setDelegate(WeatherFragment.this);
            request.queryWeather(cityCode);
        }
    }

    @Override
    public void requestSuccess(WHRequest req, String data) {
        Log.i("requestSuccess", data);

        if (req.tag == WHRequest.Req_Tag.Tag_Weather) {
            Log.i("weather_data", data);

            try {
        //            JSONObject jsonObject = new JSONObject(data);
        //            JSONObject result = jsonObject.getJSONObject("result");
        //
        //            AddressComponent addressComponent = new AddressComponent();
        //
        //            addressComponent.setCity(result.getJSONObject("addressComponent").getString("city"));
        //            addressComponent.setDistrict(result.getJSONObject("addressComponent").getString("district"));
        //            addressComponent.setStreet(result.getJSONObject("addressComponent").getString("street"));
        //
        //            WeatherDetailFragment fragment = WeatherDetailFragment.instance(addressComponent);
        //            fragments.add(fragment);
                WeatherDetailFragment fragment = WeatherDetailFragment.instance(Utility.handleWeatherXMLResponse(getContext(), data));
                fragments.add(fragment);

                WeatherAdapter adapter = new WeatherAdapter(getFragmentManager(), fragments);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(pageChangeListener);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void  requestFail(WHRequest req, String message) {
        Log.i("requestFail", message);
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("click", "click");
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class WeatherAdapter extends FragmentPagerAdapter {

        private List<? extends Fragment> fragments;

        public WeatherAdapter (FragmentManager fm, List<? extends Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


    }

}
