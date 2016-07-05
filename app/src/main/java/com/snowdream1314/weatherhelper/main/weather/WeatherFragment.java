package com.snowdream1314.weatherhelper.main.weather;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.base.TitleLayoutFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends TitleLayoutFragment {

    private View rootView;

    private ViewPager viewPager;
    private List<WeatherDetailFragment> fragments = new ArrayList<WeatherDetailFragment>();
    private WeatherAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_weather, null);

            View view = rootView;
            setTitleLayoutTitle(view, "杭州");
            setTitleLayoutSubTitle(view, "周二");
            showLeftButton(view, clickListener);
            showRightButton(view, R.mipmap.btn_more, clickListener);

            for (int i = 0; i < 2; i++) {
                WeatherDetailFragment fragment = WeatherDetailFragment.instance();
                fragments.add(fragment);
            }

            viewPager = (ViewPager) rootView.findViewById(R.id.vp_weather);
            adapter = new WeatherAdapter(getFragmentManager(), fragments);
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(pageChangeListener);

            if (fragments.size() > 1) {
                showCirclePageIndicator(view);
                circlePageIndicator.setViewPager(viewPager);
            }
        }
        return rootView;

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("click", "click");
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setTitleLayoutTitle(rootView, "杭州"+ String.valueOf(position));
            setTitleLayoutSubTitle(rootView, "周二" + String.valueOf(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class WeatherAdapter extends FragmentPagerAdapter {

        private List<? extends Fragment> fragments;

        public WeatherAdapter (FragmentManager fm, List<? extends Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }


    }

}
