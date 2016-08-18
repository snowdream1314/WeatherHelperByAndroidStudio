package com.snowdream1314.weatherhelper.main.usercenter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;

/**
 * Created by xxq on 2016/8/18.
 */
public class HeaderView extends RelativeLayout{

    private FragmentActivity activity;

    public HeaderView(FragmentActivity activity) {
        super(activity);
        this.activity = activity;

        initView();
    }

    private void initView() {

        View view = LayoutInflater.from(activity).inflate(R.layout.layout_usercenter_header_view, null);

        ImageView phoneImageView = (ImageView) findViewById(R.id.iv_login_phone);
        ImageView wechartImageView = (ImageView) findViewById(R.id.iv_login_wechat);
        ImageView qqImageView = (ImageView) findViewById(R.id.iv_login_qq);
        TextView tipTextView = (TextView) findViewById(R.id.tv_login_tip);

        phoneImageView.setOnClickListener(clickListener);
        wechartImageView.setOnClickListener(clickListener);
        qqImageView.setOnClickListener(clickListener);

        this.addView(view);
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
}
