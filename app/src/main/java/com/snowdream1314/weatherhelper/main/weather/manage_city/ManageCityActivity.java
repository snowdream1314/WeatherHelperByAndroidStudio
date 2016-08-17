package com.snowdream1314.weatherhelper.main.weather.manage_city;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarContextView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

    public static final int Type_Delete = -1;
    public static final int Type_Normal = 1;

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


        titleLayout = (RelativeLayout) findViewById(R.id.rl_title);
        titleLayout.setBackgroundColor(Color.parseColor("#FF996699"));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titleLayout.getLayoutParams();
        params.height = params.height + AppUtil.getStatusHeight(ManageCityActivity.this);
        titleLayout.setLayoutParams(params);

        setTitleLayoutTitle(null, "城市管理");
        showBackButton(null);
        showShareButton(null, R.mipmap.add_normal, clickListener);
        showFeedsButton(null, R.mipmap.edit, clickListener);

        initDatas();

        mListView = (ListView) findViewById(R.id.lv_citys);
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
                    }else {
                        showFeedsButton(null, R.mipmap.edit, clickListener);
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
        public int getItemViewType(int position) {
            if (manageClick) {
                return Type_Delete;
            }else {
                return Type_Normal;
            }
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup  parent) {

            int type  = getItemViewType(position);

            if (type == Type_Normal) {

                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.layout_cell_choosed_cities, null);
                }

                ImageView cityWeatherImageView = (ImageView) ViewHolder.get(convertView, R.id.iv_city_weather);
                TextView cityNameTextView = (TextView) ViewHolder.get(convertView, R.id.tv_city_name);
                TextView cityTempTextView = (TextView) ViewHolder.get(convertView, R.id.tv_city_temp);

                ChoosedCity choosedCity = choosedCities.get(position);

                cityNameTextView.setText(choosedCity.getName());
                cityTempTextView.setText(choosedCity.getTempHigh().replace("℃","") + " /" + choosedCity.getTempLow());
                cityWeatherImageView.setImageResource(choosedCity.getImageId());

            }else if (type == Type_Delete){
                Log.i("getView", "Type_Delete");

//                if (convertView ==  null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.layout_cell_delete_city, null);
//                }

                ImageView cityWeatherImageView = (ImageView) ViewHolder.get(convertView, R.id.iv_city_weather);
                final ImageView deleteImageView = (ImageView) ViewHolder.get(convertView, R.id.iv_delete);
                TextView cityNameTextView = (TextView) ViewHolder.get(convertView, R.id.tv_city_name);
                final TextView deleteTextView = (TextView) ViewHolder.get(convertView, R.id.tv_delete);

                final ChoosedCity choosedCity = choosedCities.get(position);

                cityNameTextView.setText(choosedCity.getName());
                cityWeatherImageView.setImageResource(choosedCity.getImageId());

                deleteImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position >= choosedCities.size()) return;
                        choosedCities.remove(position);
                        coolWeatherDB.delChoosedCity(choosedCity);
                        adapter.notifyDataSetChanged();
                    }
                });
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
