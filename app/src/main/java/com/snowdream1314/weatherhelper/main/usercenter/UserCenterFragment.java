package com.snowdream1314.weatherhelper.main.usercenter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

    private ImageView phoneImageView, wechartImageView, qqImageView;
    private TextView tipTextView;

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

        initHeaderView();
        initData();


        mListView = (ListView) rootView.findViewById(R.id.lv_usercenter_items);
        myAdapter = new MyAdapter(getContext(), items);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(itemClickListener);

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private void initHeaderView() {
        phoneImageView = (ImageView) rootView.findViewById(R.id.iv_login_phone);
        wechartImageView = (ImageView) rootView.findViewById(R.id.iv_login_wechat);
        qqImageView = (ImageView) rootView.findViewById(R.id.iv_login_qq);
        tipTextView = (TextView) rootView.findViewById(R.id.tv_login_tip);

        phoneImageView.setOnClickListener(clickListener);
        wechartImageView.setOnClickListener(clickListener);
        qqImageView.setOnClickListener(clickListener);
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_login_phone:
                    break;
                case R.id.iv_login_wechat:
                    break;
                case R.id.iv_login_qq:
                    break;
            }
        }
    };

    private ListView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0://公告
                    break;
                case 1://圈子
                    break;
                case 2://活动中心
                    break;
                case 3://皮肤
                    break;
                case 4://个性助手
                    break;
                case 5://商城
                    break;
                case 6://空气果
                    break;
                case 7://设置
                    break;
                case 8://清理缓存
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
            imageView.setImageResource(getResources().getIdentifier(getContext().getPackageName() + ":" + item.getIcon(), null, null));

            if (item.getNote() == null || "".equals(item.getNote())) {
                noteTextView.setVisibility(View.GONE);
            }else {
                noteTextView.setText(item.getNote());
                noteTextView.setVisibility(View.VISIBLE);
            }

            if (position == 0 || position == 4 || position == 6 || position == 7 || position == 8) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemLayout.getLayoutParams();
                params.setMargins(0, 0, 0, AppUtil.dip2px(getActivity(),20));
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
