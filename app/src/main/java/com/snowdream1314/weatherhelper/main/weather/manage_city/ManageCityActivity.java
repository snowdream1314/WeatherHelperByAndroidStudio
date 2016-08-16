package com.snowdream1314.weatherhelper.main.weather.manage_city;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.TitleLayoutActivity;

public class ManageCityActivity extends TitleLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_city);

        setTitleLayoutTitle(null, "城市管理");
        showBackButton(null);
        showShareButton(null, R.mipmap.add_normal, clickListener);
        showFeedsButton(null, R.mipmap.edit, clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_feeds://管理
                    break;
                case R.id.ib_share://添加
                    break;

            }
        }
    };
}
