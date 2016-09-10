package com.snowdream1314.weatherhelper.util;

import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.snowdream1314.weatherhelper.R;
import com.snowdream1314.weatherhelper.constant.WHConstant;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by xxq on 2016/9/10.
 */
public class ShareUtil {

    private FragmentActivity activity;

    public ShareUtil(FragmentActivity activity) {
        this.activity = activity;
    }

    public void share(String title, String content) {
        UMImage umImage = new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.mipmap.icon));
        share(title, content, WHConstant.Share_Url, umImage);
    }

    public void share(String title, String content, String url) {
        UMImage umImage = new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.mipmap.icon));
        share(title, content, url, umImage);
    }

    public void share(String title, String content, String url, UMImage umImage) {

        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                        SHARE_MEDIA.SINA, SHARE_MEDIA.QQ
                };
        new ShareAction(activity).setDisplayList( displaylist )
                .withText( title )
                .withTitle(content)
                .withTargetUrl(url)
                .withMedia( umImage )
                .setListenerList(umShareListener)
                .open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(activity, "分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(activity, "分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(activity, "分享取消啦", Toast.LENGTH_SHORT).show();
        }
    };

    public void shareWithSinglePlatform(String title, String content, String url, UMImage umImage, SHARE_MEDIA share_media){

        new ShareAction(activity)
                .setPlatform(share_media)
                .withTitle(title)
                .withText(content)
                .withTargetUrl(url)
                .withMedia(umImage)
                .setListenerList(umShareListener)
                .share();

    }
}
