package com.snowdream1314.weatherhelper.main.managecity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.bean.City;
import com.snowdream1314.weatherhelper.bean.UsercenterItem;
import com.snowdream1314.weatherhelper.util.AppUtil;
import com.snowdream1314.weatherhelper.util.JsonUtil;
import com.snowdream1314.weatherhelper.viewholder.ViewHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends AppCompatActivity {

    private GridView gridView;
    private List<City> cities = new ArrayList<City>();
    private GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        initData();

        gridView = (GridView) findViewById(R.id.gv_hot_cities);
        adapter = new GridViewAdapter(AddCityActivity.this, cities);
        gridView.setAdapter(adapter);
    }

    private void initData() {
        cities.clear();

        try {
            String json = AppUtil.readJsonFromRaw(AddCityActivity.this, R.raw.hot_cities);

            Type citiesType = new TypeToken<List<City>>() {}.getType();
            cities = JsonUtil.json2Any(json, citiesType);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GridViewAdapter extends BaseAdapter {

        private Context context;
        private List<City> cities;

        public GridViewAdapter (Context context, List<City> cities) {
            this.context = context;
            this.cities = cities;
        }

        @Override
        public int getCount() { return cities.size(); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public Object getItem(int position) { return cities.get(position); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_cell_hot_cities, null);
            }

            try {
                AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) convertView.getLayoutParams();
                layoutParams.width = AppUtil.getSreenWidth(AddCityActivity.this)/3;
                convertView.setLayoutParams(layoutParams);

            }catch (Exception e) {
                e.printStackTrace();
            }

            TextView cityNameTextView  = (TextView) ViewHolder.get(convertView, R.id.tv_hot_city_name);

            final City city = cities.get(position);

            cityNameTextView.setText(city.getCityName() + "市");

            cityNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, city.getCityName(), Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }


    }
}
