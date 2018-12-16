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
    private String pageUrl;
    private UMMin umMin;
    private UMWeb web;
    private String shareImageUrl;
    private String marketUrl;
    private String price=null;
    private boolean isFriend;

    public ShareUtil(Activity context) {
        this.context = context;
    }

    public void shareWindow(String weburl,String pageUrl,String imageURl,String title,String content,String rid ,String scene){
        this.rid = rid;
        this.scene = scene;
        type=1;
        image = new UMImage(context, imageURl+ImageSizeConfig.SIZE_SM);
        setUmMin(weburl+rid,pageUrl+"?rid="+rid,title,content);
        setUmWeb(weburl+rid,title,content);
        LogUtil.e("图片链接地址"+imageURl);
        setSaveImage();
    }

    public void shareFriendInvitation(String weburl,int imageURl,String title,String content){
        isFriend=true;
        image = new UMImage(context, imageURl);
        setUmWeb(weburl,title,content);
        setUmShare();
    }

    public void shareInvitation(String weburl,String pageUrl,int imageURl,String title,String content,String scene){
        this.scene = scene;
        type=2;
        this.pageUrl=pageUrl;
        image = new UMImage(context, imageURl);
        setUmMin(weburl+rid,pageUrl+rid,title,content);
        setUmWeb(weburl+rid,title,content);
        LogUtil.e("图片链接地址"+imageURl);
        setSaveImage();
        //presenter.loadShareInvitation(scene);
    }

    public void shareGoods(String weburl,String pageUrl,String imageURl,String title,String content,String rid ,String scene,int types){
        this.scene = scene;
        this.rid=rid;
        type=types;
        this.pageUrl=pageUrl;
        image = new UMImage(context, imageURl);
        setUmMin(weburl+rid,pageUrl+rid,title,content);
        setUmWeb(weburl+rid,title,content);
        LogUtil.e("图片链接地址"+imageURl);
        setSaveImage();
    }

    public void shareGoods(String pageUrl,String rid ,String scene,String price,int types){
        /*this.rid = rid;
        this.types = types;// 1=品牌馆, 2=生活馆, 4=分享商品
        this.scene = scene;
        type=3;
        image = new UMImage(context, imageURl+ImageSizeConfig.SIZE_SM);
        setUmMin(weburl,pageUrl,title,content);
        setUmWeb(weburl,title,content);
        LogUtil.e("图片链接地址"+imageURl);
        setSaveImage();*/
        this.price=price;
        presenter.loadShareImage(pageUrl,types,rid,scene);
        presenter.loadShareMarket(rid,2);
    }

    public void shareLife(String pageUrl,String rid ,String scene,int types){
        presenter.loadShareImage(pageUrl,types,rid,scene);
        presenter.loadShareMarket(rid,1);
    }

    public void shareBrand(String pageUrl,String rid ,String scene,int types){
        presenter.loadShareImage(pageUrl,types,rid,scene);
        presenter.loadShareMarket(rid,4);
    }

    public void shareNoImage(String weburl,String pageUrl,String imageURl,String title,String content){
        LogUtil.e("分享链接："+weburl);
        image = new UMImage(context, imageURl+ImageSizeConfig.SIZE_SM);
        setUmMin(weburl,pageUrl,title,content);
        setUmWeb(weburl,title,content);
        LogUtil.e("图片链接地址"+imageURl);
        setUmShare();
    }

    private void setUmMin(String weburl,String pageUrl,String title,String content){
        //兼容低版本的网页链接
        umMin = new UMMin(weburl);
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
    }

    private void setUmWeb(String weburl,String title,String content){
        web = new UMWeb(weburl);
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(content);//描述
    }

    private void setSaveImage(){
        new ShareAction(context)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)
                .addButton(Util.getString(R.string.text_save_poster),"save","icon_goods_image_save","icon_goods_image_save")
                .setShareboardclickCallback(shareBoardlistener)
                .setCallback(shareListener)
                .open();
    }
    private void setUmShare(){
        new ShareAction(context)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA)
                .setShareboardclickCallback(shareBoardlistener)
                .setCallback(shareListener)
                .open();
    }

    private ShareBoardlistener shareBoardlistener=new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media!=null){
                /*if (share_media==SHARE_MEDIA.WEIXIN){
                    LogUtil.e("微信好友");
                    new ShareAction(context)
                            .withMedia(umMin)
                            .setPlatform(share_media)
                            .share();
                }else{*/
                    LogUtil.e("微信朋友圈"+share_media.toString());
                    new ShareAction(context)
                            .withMedia(web)
                            .setPlatform(share_media)
                            .setCallback(shareListener)
                            .share();
                //}
            }else {
                if (snsPlatform.mKeyword.equals("save")){
                    switch (type){
                        case 1:
                            presenter.loadShareWindow(rid,scene);
                            break;
                        case 2:
                            presenter.loadShareInvitation(scene);
                            break;
                        case 4:
                            presenter.loadShareImage(pageUrl,type,rid,scene);
                            break;
                    }
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
            if (isFriend)
            presenter.loadFriend();
        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.e("回调成功了"+platform.toString());
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
        shareImageUrl=imageUrl;
        if (type==1||type==2||type==4) {
            ShareDialog shareDialog = new ShareDialog(context, imageUrl);
            shareDialog.show();
        }
        if (marketUrl!=null){
            ShareImageDialog imageDialog = new ShareImageDialog(context, marketUrl, shareImageUrl,price);
            imageDialog.show();
        }
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
    public void setMarket(String marketUrl) {
        this.marketUrl=marketUrl;
        if (shareImageUrl!=null) {
            ShareImageDialog imageDialog = new ShareImageDialog(context, marketUrl, shareImageUrl,price);
            imageDialog.show();
        }
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        setPresenter(presenter);
    }
}
