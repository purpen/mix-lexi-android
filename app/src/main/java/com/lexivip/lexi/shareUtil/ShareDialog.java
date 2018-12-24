package com.lexivip.lexi.shareUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.basemodule.tools.DimenUtil;
import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ScreenUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lexivip.lexi.R;
import com.lexivip.lexi.album.ImageUtils;
import com.smart.dialog.widget.base.BottomBaseDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShareDialog extends BottomBaseDialog<ShareDialog> {
    private String url;
    private Activity context;
    private View view;
    private boolean isSave;
    private List<ShareTypeBean> list;

    public ShareDialog(Activity context, String url) {
        super(context);
        this.context=context;
        this.url=url;
    }

    @Override
    public View onCreateView() {
        view = View.inflate(context, R.layout.dialog_share,null);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        ImageView imageView=view.findViewById(R.id.imageView);
        int w=ScreenUtil.getScreenWidth()-DimenUtil.getDimensionPixelSize(R.dimen.dp100);
        int h= (int) (w*1.77);
        ViewGroup.LayoutParams layoutParams=new RelativeLayout.LayoutParams(w,h);
        imageView.setLayoutParams(layoutParams);
        Button button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        GlideUtil.loadImageWithFading(url,imageView);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);//new Thread(runnable).start();
        setData();
        AdapterShare adapterShare=new AdapterShare(R.layout.item_share,list);
        recyclerView.setAdapter(adapterShare);
        final UMImage image = new UMImage(context, url);
        UMImage thumb =  new UMImage(context, url);
        image.setThumb(thumb);
        adapterShare.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        new Thread(runnable).start();
                        break;
                    case 1:
                        new ShareAction(context)
                                .setPlatform(SHARE_MEDIA.WEIXIN)
                                .withMedia(image)
                                .setCallback(shareListener)
                                .share();
                        break;
                    case 2:
                        new ShareAction(context)
                                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                .withMedia(image)
                                .setCallback(shareListener)
                                .share();
                        break;
                    case 3:
                        new ShareAction(context)
                                .setPlatform(SHARE_MEDIA.SINA)
                                .withMedia(image)
                                .setCallback(shareListener)
                                .share();
                        break;
                    case 4:
                        new ShareAction(context)
                                .setPlatform(SHARE_MEDIA.QQ)
                                .withMedia(image)
                                .setCallback(shareListener)
                                .share();
                        break;
                    case 5:
                        new ShareAction(context)
                                .setPlatform(SHARE_MEDIA.QZONE)
                                .withMedia(image)
                                .setCallback(shareListener)
                                .share();
                        break;
                }
            }
        });
    }

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
            ToastUtil.showError("分享失败");
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

    private void setData(){
        list=new ArrayList<>();
        ShareTypeBean shareTypeBean0=new ShareTypeBean();
        shareTypeBean0.image=R.mipmap.icon_share_save;
        shareTypeBean0.name=Util.getString(R.string.text_preserve_image);
        list.add(shareTypeBean0);
        ShareTypeBean shareTypeBean1=new ShareTypeBean();
        shareTypeBean1.image=R.mipmap.icon_share_wechat;
        shareTypeBean1.name=Util.getString(R.string.text_wx);
        list.add(shareTypeBean1);
        ShareTypeBean shareTypeBean2=new ShareTypeBean();
        shareTypeBean2.image=R.mipmap.icon_share_timeline;
        shareTypeBean2.name=Util.getString(R.string.text_time_line);
        list.add(shareTypeBean2);
        ShareTypeBean shareTypeBean3=new ShareTypeBean();
        shareTypeBean3.image=R.mipmap.icon_share_weibo;
        shareTypeBean3.name=Util.getString(R.string.text_login_sina);
        list.add(shareTypeBean3);
        ShareTypeBean shareTypeBean4=new ShareTypeBean();
        shareTypeBean4.image=R.mipmap.icon_share_qq;
        shareTypeBean4.name=Util.getString(R.string.text_login_qq);
        list.add(shareTypeBean4);
        ShareTypeBean shareTypeBean5=new ShareTypeBean();
        shareTypeBean5.image=R.mipmap.icon_share_qq_space;
        shareTypeBean5.name=Util.getString(R.string.text_qq_space);
        list.add(shareTypeBean5);
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Bitmap bitmap = null;
            try {
                bitmap = GlideUtil.downLoadOriginalBitmap(url, context);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (bitmap!=null){
                LogUtil.e("图片大小："+bitmap.getByteCount());
                ImageUtils.saveBitmap(bitmap,context);
                isSave=true;
                dismiss();
            }else {
                return;
            }
        }
    };

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        LogUtil.e("保存图片了");
        super.setOnDismissListener(listener);
        LogUtil.e("保存图片"+isSave);
        if (isSave){
            ToastUtil.showInfo("保存图片成功！");
        }
    }
}
