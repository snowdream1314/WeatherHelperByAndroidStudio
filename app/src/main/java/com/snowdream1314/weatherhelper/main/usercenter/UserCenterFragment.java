package com.snowdream1314.weatherhelper.main.usercenter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.bean.UsercenterItem;
import com.snowdream1314.weatherhelper.util.AppUtil;
import com.snowdream1314.weatherhelper.util.JsonUtil;
import com.snowdream1314.weatherhelper.viewholder.ViewHolder;

import org.json.JSONArray;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCenterFragment extends Fragment {

    private View rootView;
    private ListView mListView;
    private MyAdapter myAdapter;
    private List<UsercenterItem> items = new ArrayList<UsercenterItem>();


    public UserCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_user_center, container, false);
        }

        initData();

        mListView = (ListView) rootView.findViewById(R.id.lv_usercenter_items);
        myAdapter = new MyAdapter(getContext(), items);
        mListView.setAdapter(myAdapter);

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void initData() {
        items.clear();

        try {
            String json = AppUtil.readJsonFromRaw(getContext(), R.raw.usercenter_setting_config);

            Type itemsType = new TypeToken<List<UsercenterItem>>() {}.getType();
            items = JsonUtil.json2Any(json, itemsType);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


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

            UsercenterItem item = items.get(position);

            titleTextView.setText(item.getTitle());
            imageView.setImageResource(getResources().getIdentifier(getContext().getPackageName() + ":" + item.getIcon(), null, null));

            if (item.getNote() == null || "".equals(item.getNote())) {
                noteTextView.setVisibility(View.GONE);
            }else {
                noteTextView.setText(item.getNote());
                noteTextView.setVisibility(View.VISIBLE);
            }

            return convertView;
        }
    }

}
