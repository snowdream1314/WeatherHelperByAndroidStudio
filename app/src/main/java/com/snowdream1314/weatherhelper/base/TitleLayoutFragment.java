package com.snowdream1314.weatherhelper.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class TitleLayoutFragment extends Fragment implements TitleLayout{

    protected CirclePageIndicator circlePageIndicator;

    @Override
    public void setTitleLayoutTitle(View view, String title) {
        TextView textView = (TextView) view.findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTitleLayoutSubTitle(View view, String title) {
        TextView textView = (TextView) view.findViewById(R.id.tv_subTitle);
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
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.ib_back);
        if (clickListener == null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        } else {
            imageButton.setOnClickListener(clickListener);
        }
        imageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLeftButton(View view, View.OnClickListener clickListener) {
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.ib_left);
        imageButton.setOnClickListener(clickListener);
        imageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRightButton(View view, int resId, View.OnClickListener clickListener) {
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.ib_right);
        imageButton.setImageResource(resId);
        imageButton.setOnClickListener(clickListener);
        imageButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRightText(View view, String text, View.OnClickListener clickListener) {
        TextView textView = (TextView) view.findViewById(R.id.tv_right);
        textView.setText(text);
        textView.setOnClickListener(clickListener);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showShareButton(View view, View.OnClickListener clickListener) {
        showShareButton(view, 0, clickListener);
    }

    @Override
    public void showShareButton(View view, int resId, View.OnClickListener clickListener) {
        ImageButton share = (ImageButton) view.findViewById(R.id.ib_share);
        if (resId == 0) {
            share.setImageResource(R.drawable.selector_share_btn);
        }else {
            share.setImageResource(resId);
        }
        share.setOnClickListener(clickListener);
        share.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFeedsButton(View view, View.OnClickListener clickListener) {
        showFeedsButton(view, 0, clickListener);
    }

    @Override
    public void showFeedsButton(View view, int resId, View.OnClickListener clickListener) {
        ImageButton feeds = (ImageButton) view.findViewById(R.id.ib_feeds);
        if (resId == 0) {
            feeds.setImageResource(R.drawable.selector_feeds);
        }else {
            feeds.setImageResource(resId);
        }
        feeds.setOnClickListener(clickListener);
        feeds.setVisibility(View.VISIBLE);
    }


    @Override
    public void showCirclePageIndicator(View view) {
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.cpi);
        circlePageIndicator.setVisibility(View.VISIBLE);
    }


}
