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
    public void hideShareButton();
    public void hideFeedsButton();
    public void hideBackButton();
    public void hideLeftButton();
    public void hideRightButton();
    public void hideRightText();
    public void showCirclePageIndicator(View view);
}
