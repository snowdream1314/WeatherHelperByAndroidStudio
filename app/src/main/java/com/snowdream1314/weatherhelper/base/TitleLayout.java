package com.snowdream1314.weatherhelper.base;

import android.view.View;

/**
 * Created by xxq on 2016/7/5.
 */
public interface TitleLayout {
    public void setTitleLayoutTitle(View view, String title);
    public void setTitleLayoutSubTitle(View view, String title);
    public void showBackButton(View view);
    public void showBackButton(View view, View.OnClickListener clickListener);
    public void showLeftButton(View view, View.OnClickListener clickListener);
    public void showRightButton(View view, int resId, View.OnClickListener clickListener);
    public void showRightText(View view, String text, View.OnClickListener clickListener);
    public void showShareButton(View view, View.OnClickListener clickListener);
    public void showFeedsButton(View view, View.OnClickListener clickListener);
    public void showShareButton(View view, int resId, View.OnClickListener clickListener);
    public void showFeedsButton(View view, int resId, View.OnClickListener clickListener);
    public void hideShareButton(View view);
    public void hideFeedsButton(View view);
    public void hideBackButton(View view);
    public void hideLeftButton(View view);
    public void hideRightButton(View view);
    public void hideRightText(View view);
    public void showCirclePageIndicator(View view);
    public void hideCirclePageIndicator(View view);

    public void setTitleLayoutParams(View view, int backgroundColor, int height);

}
