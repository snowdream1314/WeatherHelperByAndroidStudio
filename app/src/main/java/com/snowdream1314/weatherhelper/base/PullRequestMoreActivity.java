package com.snowdream1314.weatherhelper.base;

import android.view.View;

import com.snowdream1314.weatherhelper.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by xxq on 2016/8/22.
 */
public abstract class PullRequestMoreActivity extends TitleLayoutActivity{

    protected int current_page = 0;
    protected int total_page = 0;
    protected boolean isLoading = false;


    public abstract void reloadData();

    protected PtrClassicFrameLayout ptrRefreshLayout;

    protected void initRefreshLayout(View view) {
        initPTRRefreshLayout(R.id.ptrfl_refresh_layout, new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                reloadData();
            }
        });
    }

    protected void initRefreshLayout(PtrHandler handler){
        initPTRRefreshLayout(R.id.ptrfl_refresh_layout, handler);
    }

    private void initPTRRefreshLayout(int resId, PtrHandler handler){

        if(findViewById(resId)!=null){

            ptrRefreshLayout = (PtrClassicFrameLayout) findViewById(resId);
            ptrRefreshLayout.setLastUpdateTimeRelateObject(this);
            ptrRefreshLayout.setPtrHandler(handler);
            ptrRefreshLayout.disableWhenHorizontalMove(true);
        }
    }

    protected void setRefreshing(boolean refreshing){

        if(ptrRefreshLayout!=null && !refreshing){
            ptrRefreshLayout.refreshComplete();
        }
    }
}
