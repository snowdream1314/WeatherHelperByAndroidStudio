package com.snowdream1314.weatherhelper.main.managecity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.TitleLayoutActivity;
import com.snowdream1314.weatherhelper.bean.ChoosedCity;
import com.snowdream1314.weatherhelper.bean.City;
import com.snowdream1314.weatherhelper.bean.UsercenterItem;
import com.snowdream1314.weatherhelper.constant.WHConstant;
import com.snowdream1314.weatherhelper.main.MainActivity;
import com.snowdream1314.weatherhelper.main.weather.WeatherFragment;
import com.snowdream1314.weatherhelper.util.AppUtil;
import com.snowdream1314.weatherhelper.util.CoolWeatherDB;
import com.snowdream1314.weatherhelper.util.JsonUtil;
import com.snowdream1314.weatherhelper.util.WHRequest;
import com.snowdream1314.weatherhelper.viewholder.ViewHolder;
import com.squareup.haha.perflib.Main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends TitleLayoutActivity{

    private GridView gridView;
    private List<City> hotCities = new ArrayList<City>();
    private GridViewAdapter adapter;

    private EditText searchEditText;
    private CoolWeatherDB coolWeatherDB;
    private List<City> cities = new ArrayList<City>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        coolWeatherDB = CoolWeatherDB.getInstance(AddCityActivity.this);

        showBackButton(null);

        searchEditText = (EditText) findViewById(R.id.et_search);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchString = searchEditText.getText().toString().trim();
                Log.e("searchString-->", searchString);
                cities = coolWeatherDB.searchCity(searchString);
                Log.e("search_result-->", String.valueOf(cities.size()));
                if (cities.size() != 0)  {
                    Log.e("search_city-->", cities.get(0).getCityName());
                }
            }
        });

        initData();

        gridView = (GridView) findViewById(R.id.gv_hot_cities);
        adapter = new GridViewAdapter(AddCityActivity.this, hotCities);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= cities.size()) return;

                if (position == 0) {//定位
                    Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                    intent.putExtra("isFromAddCityActivity", true);
                    intent.putExtra("getLocation", true);
                    startActivity(intent);
                }else {
                    City city = cities.get(position);
                    Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                    intent.putExtra("isFromAddCityActivity", true);
                    intent.putExtra("cityName", city.getCityName());
                    intent.putExtra("cityCode", city.getCityCode());
                    intent.putExtra("tab", 0);
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
        cities.clear();

        try {
            String json = AppUtil.readJsonFromRaw(AddCityActivity.this, R.raw.hot_cities);

            Type citiesType = new TypeToken<List<City>>() {}.getType();
            hotCities = JsonUtil.json2Any(json, citiesType);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GridViewAdapter extends BaseAdapter {

        private Context context;
        private List<City> hotCities;

        public GridViewAdapter (Context context, List<City> hotCities) {
            this.context = context;
            this.hotCities = hotCities;
        }

        @Override
        public int getCount() { return hotCities.size(); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public Object getItem(int position) { return hotCities.get(position); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_cell_hot_cities, null);
            }

            try {
//                AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) convertView.getLayoutParams();
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(AppUtil.getSreenWidth(AddCityActivity.this)/3, ViewGroup.LayoutParams.MATCH_PARENT);
//                layoutParams.width = AppUtil.getSreenWidth(AddCityActivity.this)/3;
                convertView.setLayoutParams(params);

            }catch (Exception e) {
                e.printStackTrace();
            }

            TextView cityNameTextView  = (TextView) ViewHolder.get(convertView, R.id.tv_hot_city_name);

            final City city = hotCities.get(position);

            if (position == 0) {
                cityNameTextView.setText(city.getCityName());
            }else {
                cityNameTextView.setText(city.getCityName() + "市");
            }

            return convertView;
        }

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_back:

                    break;
            }
        }
    };
}
