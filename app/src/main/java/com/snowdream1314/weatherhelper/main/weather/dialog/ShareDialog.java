package com.snowdream1314.weatherhelper.main.weather.dialog;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.bean.ShareOpt;
import com.snowdream1314.weatherhelper.constant.WHConstant;
import com.snowdream1314.weatherhelper.util.AppUtil;
import com.snowdream1314.weatherhelper.util.ShareUtil;
import com.snowdream1314.weatherhelper.viewholder.ViewHolder;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxq on 2016/9/9.
 */
public class ShareDialog extends Dialog{

    private FragmentActivity activity;
    private Dialog dialog;
    private GridView gridView;
    private List<ShareOpt> shareOpts = new ArrayList<ShareOpt>();
    private GridViewAdapter gridViewAdapter;

    public ShareDialog(FragmentActivity activity) {
        super(activity);
        this.activity = activity;

        init();
    }

    public ShareDialog(FragmentActivity activity, int theme) {
        super(activity, theme);
        this.activity = activity;

        init();
    }

    private void init() {
        dialog = new Dialog(activity, R.style.share_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        initData();

        View view = LayoutInflater.from(activity).inflate(R.layout.layout_cell_share_dialog, null);

        gridView = (GridView) view.findViewById(R.id.gv_share_opts);
        gridViewAdapter = new GridViewAdapter(activity, shareOpts);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://微信
                        ShareUtil shareUtil = new ShareUtil(activity);
                        String title = "天气助手分享";
                        UMImage umImage = new UMImage(activity, AppUtil.screenShot(activity));
                        shareUtil.shareWithSinglePlatform(title, title, WHConstant.Share_Url, umImage, SHARE_MEDIA.WEIXIN);
                        break;
                    case 1://朋友圈
                        shareUtil = new ShareUtil(activity);
                        title = "天气助手分享";
                        umImage = new UMImage(activity, AppUtil.screenShot(activity));
                        shareUtil.shareWithSinglePlatform(title, title, WHConstant.Share_Url, umImage, SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case 2://QQ
                        shareUtil = new ShareUtil(activity);
                        title = "天气助手分享";
                        umImage = new UMImage(activity, AppUtil.screenShot(activity));
                        shareUtil.shareWithSinglePlatform(title, title, WHConstant.Share_Url, umImage, SHARE_MEDIA.QQ);
                        break;
                    case 3://sina
                        shareUtil = new ShareUtil(activity);
                        title = "天气助手分享";
                        umImage = new UMImage(activity, AppUtil.screenShot(activity));
                        shareUtil.shareWithSinglePlatform(title, title, WHConstant.Share_Url, umImage, SHARE_MEDIA.SINA);
                        break;
                }
            }
        });

        dialog.setContentView(view);
        dialog.show();

    }

    private void initData() {
        shareOpts.clear();

        ShareOpt wxfriend = new ShareOpt();
        wxfriend.setId("1");
        wxfriend.setImageId(R.mipmap.share_wxfriend_normal);
        wxfriend.setName("微信");
        shareOpts.add(wxfriend);

        ShareOpt wxgroup = new ShareOpt();
        wxgroup.setId("2");
        wxgroup.setImageId(R.mipmap.share_wxgroup_normal);
        wxgroup.setName("朋友圈");
        shareOpts.add(wxgroup);

        ShareOpt qq = new ShareOpt();
        qq.setId("3");
        qq.setImageId(R.mipmap.share_qq_normal);
        qq.setName("QQ");
        shareOpts.add(qq);

        ShareOpt sina = new ShareOpt();
        sina.setId("4");
        sina.setImageId(R.mipmap.share_sina_normal);
        sina.setName("新浪");
        shareOpts.add(sina);
    }

    private class GridViewAdapter extends BaseAdapter {

        private List<ShareOpt> shareOpts;
        private FragmentActivity activity;

        public GridViewAdapter(FragmentActivity activity, List<ShareOpt> shareOpts) {
            this.activity = activity;
            this.shareOpts = shareOpts;
        }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public int getCount() { return shareOpts.size(); }

        @Override
        public Object getItem(int position) { return shareOpts.get(position); }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.layout_cell_share_dilog_gridview, null);
            }

            ShareOpt shareOpt = shareOpts.get(position);

            ImageView imageView = (ImageView) ViewHolder.get(convertView, R.id.iv_share);
            TextView textView = (TextView) ViewHolder.get(convertView, R.id.tv_share);

            imageView.setImageResource(shareOpt.getImageId());
            textView.setText(shareOpt.getName());

            return convertView;
        }
    }
}
