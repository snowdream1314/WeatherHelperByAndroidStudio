package com.snowdream1314.weatherhelper.base;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.util.AppUtil;
import com.viewpagerindicator.CirclePageIndicator;

public class TitleLayoutActivity extends Activity implements TitleLayout{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setTitleLayoutTitle(View view, String title) {
        TextView textView = (TextView) findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTitleLayoutSubTitle(View view, String title) {
        TextView textView = (TextView) findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showBackButton(View view) {
        showBackButton(view, null);
    }

    @Override
    public void showBackButton(View view, View.OnClickListener clickListener) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_back);
        if (clickListener == null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            imageButton.setOnClickListener(clickListener);
        }
        imageButton.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideBackButton(View view) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_back);
        imageButton.setVisibility(View.GONE);
    }

    @Override
    public void showLeftButton(View view, View.OnClickListener clickListener) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_left);
        imageButton.setOnClickListener(clickListener);
        imageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLeftButton(View view) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_left);
        imageButton.setVisibility(View.GONE);
    }

    @Override
    public void showRightButton(View view, int resId, View.OnClickListener clickListener) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_right);
        imageButton.setImageResource(resId);
        imageButton.setOnClickListener(clickListener);
        imageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRightButton(View view) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_right);
        imageButton.setVisibility(View.GONE);
    }

    @Override
    public void showRightText(View view, String text, View.OnClickListener clickListener) {
        TextView textView = (TextView) findViewById(R.id.tv_right);
        textView.setText(text);
        textView.setOnClickListener(clickListener);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRightText(View view) {
        TextView textView = (TextView) findViewById(R.id.tv_right);
        textView.setVisibility(View.GONE);
    }

    @Override
    public void showShareButton(View view, View.OnClickListener clickListener) {
        showShareButton(view, 0, clickListener);
    }

    @Override
    public void showShareButton(View view, int resId, View.OnClickListener clickListener) {
        ImageButton share = (ImageButton) findViewById(R.id.ib_share);
        if (resId == 0) {
            share.setImageResource(R.drawable.selector_share_btn);
        }else {
            share.setImageResource(resId);
        }
        share.setOnClickListener(clickListener);
        share.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShareButton(View view) {
        ImageButton share = (ImageButton) findViewById(R.id.ib_share);
        share.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFeedsButton(View view, View.OnClickListener clickListener) {
        showFeedsButton(view, 0, clickListener);
    }

    @Override
    public void showFeedsButton(View view, int resId, View.OnClickListener clickListener) {
        ImageButton feeds = (ImageButton) findViewById(R.id.ib_feeds);
        if (resId == 0) {
            feeds.setImageResource(R.drawable.selector_feeds);
        }else {
            feeds.setImageResource(resId);
        }
        feeds.setOnClickListener(clickListener);
        feeds.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFeedsButton(View view) {
        ImageButton feeds = (ImageButton) findViewById(R.id.ib_feeds);
        feeds.setVisibility(View.GONE);
    }


    @Override
    public void showCirclePageIndicator(View view) {
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.cpi);
        circlePageIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCirclePageIndicator(View view) {
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.cpi);
        circlePageIndicator.setVisibility(View.GONE);
    }

    @Override
    public void setTitleLayoutParams(View view, int backgroundColor, int height) {
        RelativeLayout titleLayout = (RelativeLayout) findViewById(R.id.rl_layout_title);
        titleLayout.setBackgroundColor(backgroundColor);

        if (height != 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titleLayout.getLayoutParams();
            params.height = params.height + height;
            titleLayout.setLayoutParams(params);
        }
    }

}
