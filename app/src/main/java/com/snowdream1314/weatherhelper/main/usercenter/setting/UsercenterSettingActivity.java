package com.snowdream1314.weatherhelper.main.usercenter.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.TitleLayoutActivity;
import com.snowdream1314.weatherhelper.bean.UsercenterItem;
import com.snowdream1314.weatherhelper.util.AppUtil;
import com.snowdream1314.weatherhelper.util.JsonUtil;
import com.snowdream1314.weatherhelper.viewholder.ViewHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserCenterSettingActivity extends TitleLayoutActivity {

    private ListView mListView;
    private MyAdapter myAdapter;
    private List<UsercenterItem> items = new ArrayList<UsercenterItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_usercenter_setting);

        setTitleLayoutTitle(null, "设置");
        showBackButton(null);
        setTitleLayoutParams(null, Color.parseColor("#993366"), AppUtil.getStatusHeight(UserCenterSettingActivity.this));

        initData();

        mListView = (ListView) findViewById(R.id.lv_settings);
        myAdapter = new MyAdapter(UserCenterSettingActivity.this, items);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(itemClickListener);
    }

    private void initData() {
        items.clear();

        try {
            String json = AppUtil.readJsonFromRaw(UserCenterSettingActivity.this, R.raw.usercenter_setting_config);

            Type itemsType = new TypeToken<List<UsercenterItem>>() {}.getType();
            items = JsonUtil.json2Any(json, itemsType);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ListView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0://账号管理
                    break;
                case 1://消息通知
                    break;
                case 2://个性功能
                    break;
                case 3://通用
                    break;
                case 4://赏个好评
                    break;
                case 5://意见反馈
                    break;
                case 6://检查新版本
                    break;
                case 7://使用教程
                    break;
                case 8://关于
                    break;

            }
        }
    };


    private class MyAdapter extends BaseAdapter {

        private Context context;
        private List<UsercenterItem> items;

        public MyAdapter(Context context, List<UsercenterItem> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.usercenter_cell_item, null);
            }

            TextView titleTextView = (TextView) ViewHolder.get(convertView, R.id.tv_usercenter_item_title);
            TextView noteTextView = (TextView) ViewHolder.get(convertView, R.id.tv_usercenter_item_note);
            ImageView imageView = (ImageView) ViewHolder.get(convertView, R.id.iv_usercenter_item_image);
            RelativeLayout itemLayout = (RelativeLayout) ViewHolder.get(convertView, R.id.rl_usercenter_item);

            UsercenterItem item = items.get(position);

            titleTextView.setText(item.getTitle());
            imageView.setImageResource(getResources().getIdentifier(UserCenterSettingActivity.this.getPackageName() + ":" + item.getIcon(), null, null));

            if (item.getNote() == null || "".equals(item.getNote())) {
                noteTextView.setVisibility(View.GONE);
            }else {noteTextView.setText(item.getNote());
                noteTextView.setVisibility(View.VISIBLE);
            }

            if (position == 0) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
                params.setMargins(0, AppUtil.dip2px(UserCenterSettingActivity.this,20), 0, AppUtil.dip2px(UserCenterSettingActivity.this,20));
                itemLayout.setLayoutParams(params);

            }else if (position == 3){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
                params.setMargins(0, 0, 0, AppUtil.dip2px(UserCenterSettingActivity.this,20));
                itemLayout.setLayoutParams(params);
            }else {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                itemLayout.setLayoutParams(params);
            }

            return convertView;
        }
    }

}
