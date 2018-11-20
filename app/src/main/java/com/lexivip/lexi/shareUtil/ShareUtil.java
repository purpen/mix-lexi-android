package com.lexivip.lexi.shareUtil;

import android.app.Activity;
import android.app.Application;

import com.basemodule.tools.LogUtil;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

public class ShareUtil {
    private Activity context = AppApplication.getActivity();
    public ShareUtil() {
        UMImage image = new UMImage(context, R.drawable.ic_camera);//资源文件
        new ShareAction(context)
                .withText("hello")
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                //.setShareContent()
                //.setShareboardclickCallback(shareBoardlistener)
                .setCallback(shareListener)
                .open();
    }

    private ShareBoardlistener shareBoardlistener=new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            UMImage image = new UMImage(context, R.drawable.ic_camera);//资源文件
            if (share_media!=null){
                if (share_media==SHARE_MEDIA.WEIXIN){
                    new ShareAction(context)
                            .withText("hello")
                            //.withMedia(image)
                            .withMedia(image)
                            //.setShareboardclickCallback(shareBoardlistener)
                            .share();
                            //.setCallback(shareListener);
                            //.open();
                }else if (share_media==SHARE_MEDIA.WEIXIN_CIRCLE){
                    new ShareAction(context)
                            .withText("hello")
                            //.withMedia(image)
                            //.setShareboardclickCallback(shareBoardlistener)
                            .share();
                            //.setCallback(shareListener);
                            //.open();
                }
            }
        }
    };

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            LogUtil.e("回调开始了");
        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.e("回调成功了");
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            LogUtil.e("回调失败:"+t.toString());
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            LogUtil.e("回调取消了");
        }
    };
}
