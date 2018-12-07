package com.lexivip.lexi.shareUtil;
import android.app.Activity;
import android.support.annotation.NonNull;
import com.basemodule.tools.AppManager;
import com.basemodule.tools.Constants;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.BasePresenter;
import com.lexivip.lexi.ImageSizeConfig;
import com.lexivip.lexi.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

public class ShareUtil implements ShareContract.View{
    private WaitingDialog dialog = new WaitingDialog(AppManager.getAppManager().currentActivity());
    private Activity context;
    private String rid;
    private int type;
    private SharePresenter presenter=new SharePresenter(this);
    private String scene;
    private UMImage image;
    private String url;
    private String content;
    private String pageUrl;
    private String title;

    public ShareUtil(Activity context,String rid, int type) {
        this.context = context;
        this.rid = rid;
        this.type = type;
    }

    public ShareUtil(Activity context,int type) {
        this.context = context;
        this.type = type;
    }

    public ShareUtil(Activity context,String rid, int type, String scene) {
        this.context = context;
        this.rid = rid;
        this.type = type;// 1=品牌馆, 2=生活馆, 4=分享商品
        this.scene = scene;
        presenter.loadShareImage(type,rid,scene);
    }

    public ShareUtil(Activity context,String rid, String scene) {
        this.context = context;
        this.rid = rid;
        this.scene = scene;
        presenter.loadShareWindow(rid,scene);
    }
    public ShareUtil(Activity context,String url,String title,String content,String pageUrl,String imageURl) {
        this.context = context;
        this.url = url;
        this.content=content;
        this.pageUrl=pageUrl;
        this.title=title;
        //资源文件
        image = new UMImage(context, imageURl+ImageSizeConfig.SIZE_SM);
        LogUtil.e("图片链接地址"+imageURl);
        new ShareAction(context)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)
                .addButton(Util.getString(R.string.text_save_poster),"save","icon_goods_image_save","icon_goods_image_save")
                .setShareboardclickCallback(shareBoardlistener)
                .setCallback(shareListener)
                .open();
    }

    private ShareBoardlistener shareBoardlistener=new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media!=null){
                UMMin umMin = new UMMin(url);//兼容低版本的网页链接
                // 小程序消息封面图片
                umMin.setThumb(image);
                // 小程序消息title
                umMin.setTitle(title);
                // 小程序消息描述
                umMin.setDescription(content);
                //小程序页面路径
                umMin.setPath(pageUrl);
                // 小程序原始id,在微信平台查询
                umMin.setUserName(Constants.AUTHAPPID);

                /*if (share_media==SHARE_MEDIA.WEIXIN){
                    LogUtil.e("微信好友");
                    new ShareAction(context)
                            .withMedia(umMin)
                            .setPlatform(share_media)
                            .share();
                }else{*/
                    LogUtil.e("微信朋友圈"+share_media.toString());
                    UMWeb  web = new UMWeb(url);
                    web.setTitle(title);//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(content);//描述
                    new ShareAction(context)
                            .withMedia(web)
                            .setPlatform(share_media)
                            .share();
                //}
            }else {
                if (snsPlatform.mKeyword.equals("save")){

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
