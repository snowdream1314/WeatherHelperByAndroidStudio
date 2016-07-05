package com.snowdream1314.weatherhelper.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
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
        }
    }

    @Override
    public void setTitleLayoutSubTitle(View view, String title) {
        TextView textView = (TextView) findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(title);
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
    public void showLeftButton(View view, View.OnClickListener clickListener) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_left);
        imageButton.setOnClickListener(clickListener);
        imageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRightButton(View view, int resId, View.OnClickListener clickListener) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_right);
        imageButton.setImageResource(resId);
        imageButton.setOnClickListener(clickListener);
        imageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRightText(View view, String text, View.OnClickListener clickListener) {
        TextView textView = (TextView) findViewById(R.id.tv_right);
        textView.setText(text);
        textView.setOnClickListener(clickListener);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCirclePageIndicator(View view) {
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.cpi);
        circlePageIndicator.setVisibility(View.VISIBLE);
    }

}
