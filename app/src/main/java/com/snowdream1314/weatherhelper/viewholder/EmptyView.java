package com.snowdream1314.weatherhelper.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;

/**
 * Created by yanglixun on 16/4/19.
 */
public class EmptyView extends LinearLayout {

    public EmptyView(Context context, int picId, String content){

        super(context);

        initView(picId, content);
    }


    public EmptyView(Context context, int picId, String content, int bgId){

        super(context);

        initView(picId, content, bgId);
    }


    private void initView(int picId, String content){

        initView(picId, content, 0);
    }

    public  void initView(int picId, String content, int bgId){

        View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_empty, this, true);

        ImageView imageView = (ImageView)view.findViewById(R.id.iv_empty_icon);
        TextView textView = (TextView)view.findViewById(R.id.tv_tip);

        imageView.setImageResource(picId);
        textView.setText(content);

        if(bgId!=0){
            view.setBackgroundResource(bgId);
        }

    }

}
