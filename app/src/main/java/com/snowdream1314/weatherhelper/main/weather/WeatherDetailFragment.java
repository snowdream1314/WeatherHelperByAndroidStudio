package com.snowdream1314.weatherhelper.main.weather;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.TitleLayoutFragment;
import com.snowdream1314.weatherhelper.bean.AddressComponent;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetailFragment extends Fragment {

    private View rootView;

    private String cityName;
    private AddressComponent addressComponent;

    private TextView weatherTextView, tempTextView, windLevelTextView, humidityTextView;

    public WeatherDetailFragment() {
        // Required empty public constructor
    }

    public static WeatherDetailFragment instance(AddressComponent addressComponent) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        fragment.addressComponent = addressComponent;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.weather_detail_layout, null);

            initViews();

        }
        return rootView;
    }
    
    private void initViews() {
    }

    public String getTitle() {
        return (addressComponent.getCity() + addressComponent.getDistrict());
    }

    public String getSubTitle() {
        if (addressComponent.getStreet() != null) {
            return addressComponent.getStreet();
        }
        return "";
    }

}
