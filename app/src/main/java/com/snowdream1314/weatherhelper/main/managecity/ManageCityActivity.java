package com.snowdream1314.weatherhelper.main.managecity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.TitleLayoutActivity;
import com.snowdream1314.weatherhelper.bean.ChoosedCity;
import com.snowdream1314.weatherhelper.util.AppUtil;
import com.snowdream1314.weatherhelper.util.CoolWeatherDB;
import com.snowdream1314.weatherhelper.viewholder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ManageCityActivity extends TitleLayoutActivity {

    private RelativeLayout titleLayout;
    private ListView mListView;
    private Adapter adapter;
    private List<ChoosedCity> choosedCities = new ArrayList<ChoosedCity>();
    private CoolWeatherDB coolWeatherDB;

    private boolean manageClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_manage_city);
        coolWeatherDB = CoolWeatherDB.getInstance(ManageCityActivity.this);

        setTitleLayoutTitle(null, "城市管理");
        showBackButton(null);
        showShareButton(null, R.mipmap.add_normal, clickListener);
        showFeedsButton(null, R.mipmap.edit, clickListener);
        setTitleLayoutParams(null, Color.parseColor("#FF996699"), AppUtil.getStatusHeight(ManageCityActivity.this));

        initDatas();

        mListView = (ListView) findViewById(R.id.lv_cities);
        adapter = new Adapter(ManageCityActivity.this, choosedCities);
        mListView.setAdapter(adapter);

    }

    private void initDatas() {
        choosedCities.clear();
        choosedCities = coolWeatherDB.loadChoosedCity();

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_feeds://管理
                    manageClick = !manageClick;
                    if (manageClick) {
                        showFeedsButton(null, R.mipmap.ok, clickListener);
                        hideBackButton(null);
                        hideShareButton(null);
                    }else {
                        showFeedsButton(null, R.mipmap.edit, clickListener);
                        showShareButton(null, R.mipmap.add_normal, clickListener);
                        showBackButton(null);
                    }
                    adapter.notifyDataSetChanged();

                    break;
                case R.id.ib_share://添加
                    break;

            }
        }
    };

    private class Adapter extends BaseAdapter {

        private Context context;
        private List<ChoosedCity> choosedCities;

        public Adapter (Context context, List<ChoosedCity> choosedCities) {
            this.context = context;
            this.choosedCities = choosedCities;
        }

        @Override
        public int getCount() {
            return choosedCities.size();
        }

        @Override
        public Object getItem(int position) {
            return choosedCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup  parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_cell_choosed_cities, null);
            }

            ImageView cityWeatherImageView = (ImageView) ViewHolder.get(convertView, R.id.iv_city_weather);
            TextView cityNameTextView = (TextView) ViewHolder.get(convertView, R.id.tv_city_name);
            TextView cityTempTextView = (TextView) ViewHolder.get(convertView, R.id.tv_city_temp);
            final TextView deleteTextView = (TextView) ViewHolder.get(convertView, R.id.tv_delete);


            final ChoosedCity choosedCity = choosedCities.get(position);

            cityNameTextView.setText(choosedCity.getName());
            cityTempTextView.setText(choosedCity.getTempHigh().replace("℃","") + " /" + choosedCity.getTempLow());
            cityWeatherImageView.setImageResource(choosedCity.getImageId());

            if (manageClick) {
                cityTempTextView.setVisibility(View.INVISIBLE);
                deleteTextView.setVisibility(View.VISIBLE);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position >= choosedCities.size()) return;
                        choosedCities.remove(position);
                        coolWeatherDB.delChoosedCity(choosedCity);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            else {
                cityTempTextView.setVisibility(View.VISIBLE);
                deleteTextView.setVisibility(View.GONE);
                deleteTextView.setOnClickListener(null);
            }

            return convertView;
        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_delete:
                        break;
                    case R.id.tv_delete:
                        break;
                }
            }
        };
    }
}
