package com.lexivip.lexi.shareUtil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BasePresenter;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

public class ShareUtil implements ShareContract.View{
    private Activity context = AppApplication.getActivity();
    private WaitingDialog dialog;
    private String rid;
    private int type;
    private SharePresenter presenter=new SharePresenter(this);
    private String scene;

    public ShareUtil(Activity context, WaitingDialog dialog, String rid, int type) {
        this.context = context;
        this.dialog = dialog;
        this.rid = rid;
        this.type = type;
    }

    public ShareUtil(Activity context, WaitingDialog dialog, int type) {
        this.context = context;
        this.dialog = dialog;
        this.type = type;
    }

    public ShareUtil(Activity context, WaitingDialog dialog, String rid, int type, String scene) {
        this.context = context;
        this.dialog = dialog;
        this.rid = rid;
        this.type = type;// 1=品牌馆, 2=生活馆, 4=分享商品
        this.scene = scene;
        presenter.loadShareImage(type,rid,scene);
    }

    public ShareUtil(Activity context, WaitingDialog dialog, String rid, String scene) {
        this.context = context;
        this.dialog = dialog;
        this.rid = rid;
        this.scene = scene;
        presenter.loadShareWindow(rid,scene);
    }
    /*public ShareUtil() {
        UMImage image = new UMImage(context, R.drawable.ic_camera);//资源文件
        new ShareAction(context)
                .withText("hello")
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                //.setShareContent()
                //.setShareboardclickCallback(shareBoardlistener)
                .setCallback(shareListener)
                .open();
    }*/

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

    @Override
    public void setImage(String imageUrl) {
        LogUtil.e("图片地址："+imageUrl);
        ShareDialog shareDialog=new ShareDialog(context,imageUrl);
        shareDialog.show();
    }

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.dismiss();
    }

    @Override
    public void showError(@NonNull String error) {
        if (dialog!=null){
            dialog.dismiss();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        setPresenter(presenter);
    }
}
