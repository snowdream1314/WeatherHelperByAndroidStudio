package com.snowdream1314.weatherhelper.main.managecity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.snowdream1314.weatherhelper.util.ImeUtil;
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

    private List<ChoosedCity> choosedCities = new ArrayList<ChoosedCity>();

    private EditText searchEditText;
    private CoolWeatherDB coolWeatherDB;
    private List<City> cities = new ArrayList<City>();
    private ImageView closeImageView;
    private ImageButton searchImageButton;
    private TextView cancelTextView;
    private LinearLayout hotCitiesLinearLayout;
    private RelativeLayout searchTipsRelativeLayout;
    private ListView tipCitiesListView;
    private ListViewAdapter listViewAdapter;

    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_add_city);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        coolWeatherDB = CoolWeatherDB.getInstance(AddCityActivity.this);
        closeImageView = (ImageView) findViewById(R.id.iv_close);
        searchImageButton = (ImageButton) findViewById(R.id.ib_search);
        cancelTextView = (TextView) findViewById(R.id.tv_right);
        closeImageView.setOnClickListener(clickListener);
        searchImageButton.setOnClickListener(clickListener);
        cancelTextView.setOnClickListener(clickListener);

        hotCitiesLinearLayout = (LinearLayout) findViewById(R.id.ll_hot_cities);
        searchTipsRelativeLayout = (RelativeLayout) findViewById(R.id.rl_search_tips);

        searchEditText = (EditText) findViewById(R.id.et_search);
        searchEditText.setOnClickListener(clickListener);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchString = searchEditText.getText().toString().trim();
                searchCities();
            }
        });

        initData();

        gridView = (GridView) findViewById(R.id.gv_hot_cities);
        adapter = new GridViewAdapter(AddCityActivity.this, hotCities);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= hotCities.size()) return;

                if (position == 0) {//定位
                    Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                    intent.putExtra("isFromAddCityActivity", true);
                    intent.putExtra("getLocation", true);
                    startActivity(intent);
                }else {
                    City city = hotCities.get(position);
                    Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                    intent.putExtra("isFromAddCityActivity", true);
                    intent.putExtra("cityName", city.getCityName());
                    intent.putExtra("cityCode", city.getCityCode());
                    intent.putExtra("tab", 0);
                    startActivity(intent);
                }
            }
        });


        tipCitiesListView = (ListView) findViewById(R.id.lv_search_tip_cities);
        listViewAdapter = new ListViewAdapter(AddCityActivity.this, cities);
        tipCitiesListView.setAdapter(listViewAdapter);

    }

    private void searchCities() {
        if (!"".equals(searchString)) {
            cities = coolWeatherDB.searchCity(searchString);
            if (cities.size() != 0) {
                Log.e("city_size", String.valueOf(cities.size()));
                hotCitiesLinearLayout.setVisibility(View.GONE);
                searchTipsRelativeLayout.setVisibility(View.GONE);
                tipCitiesListView.setVisibility(View.VISIBLE);
                listViewAdapter = new ListViewAdapter(AddCityActivity.this, cities);
                tipCitiesListView.setAdapter(listViewAdapter);
                listViewAdapter.notifyDataSetChanged();
            }
        }else {
            hotCitiesLinearLayout.setVisibility(View.GONE);
            searchTipsRelativeLayout.setVisibility(View.VISIBLE);
            tipCitiesListView.setVisibility(View.GONE);
        }
    }

    private void initData() {
        hotCities.clear();

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

    private class ListViewAdapter extends BaseAdapter {

        private List<City> cities;
        private Context context;

        public ListViewAdapter (Context context, List<City> cities) {
            this.cities = cities;
            this.context = context;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() { return cities.size(); }

        @Override
        public Object getItem(int position) {
            return cities.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.add_city_cell_cities_tip, null);
            }

            TextView nameTextView = (TextView) ViewHolder.get(convertView, R.id.tv_city_name);

            City city = cities.get(position);

            nameTextView.setText(city.getCityName() + ", " + city.getProvinceName() + ", " + "中国");
            nameTextView.setTag(city);
            nameTextView.setOnClickListener(clickListener);

            return convertView;
        }

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_close:
                    choosedCities = coolWeatherDB.loadChoosedCity();
                    if (choosedCities.size() == 0) {
                        Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                        intent.putExtra("exit", true);
                        startActivity(intent);
                        finish();
                    }else {
                        finish();
                    }
                    break;
                case R.id.ib_search:
                    searchImageButton.setVisibility(View.GONE);
                    ImeUtil.showSoftKeyboard(searchEditText);
                    hotCitiesLinearLayout.setVisibility(View.GONE);
                    searchTipsRelativeLayout.setVisibility(View.VISIBLE);
                    tipCitiesListView.setVisibility(View.GONE);
                    break;
                case R.id.tv_right:
                    searchImageButton.setVisibility(View.VISIBLE);
                    hotCitiesLinearLayout.setVisibility(View.VISIBLE);
                    searchTipsRelativeLayout.setVisibility(View.GONE);
                    tipCitiesListView.setVisibility(View.GONE);
                    ImeUtil.hideSoftKeyboard(searchEditText);
                    break;
                case R.id.et_search:
                    Log.i("et_search_clicked", "true");
                    searchImageButton.setVisibility(View.GONE);
                    hotCitiesLinearLayout.setVisibility(View.GONE);
                    searchTipsRelativeLayout.setVisibility(View.VISIBLE);
                    tipCitiesListView.setVisibility(View.GONE);
                    break;
                case R.id.tv_city_name:
                    City city = (City) v.getTag();
                    Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                    intent.putExtra("isFromAddCityActivity", true);
                    intent.putExtra("cityName", city.getCityName());
                    intent.putExtra("cityCode", city.getCityCode());
                    intent.putExtra("tab", 0);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            choosedCities = coolWeatherDB.loadChoosedCity();
            if (choosedCities.size() == 0) {
                Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                intent.putExtra("exit", true);
                startActivity(intent);
                finish();
            }else {
                finish();
            }

            return false;
        }
        return super.onKeyUp(keyCode, event);
    }
}
