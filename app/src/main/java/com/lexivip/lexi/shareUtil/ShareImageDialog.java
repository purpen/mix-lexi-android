package com.lexivip.lexi.shareUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.album.ImageUtils;
import com.smart.dialog.widget.base.BottomBaseDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.concurrent.ExecutionException;

public class ShareImageDialog extends BottomBaseDialog {
    private String marketUrl;
    private String imageUrl;
    private View view;
    private Activity activity;
    private String price;
    private ImageView imageView;
    private ImageView imageView1;

    public ShareImageDialog(Activity context,String price) {
        super(context);
        activity=context;
        this.price=price;
    }

    public void setMarketUrl(String marketUrl){
        this.marketUrl=marketUrl;
        GlideUtil.loadImageWithFading(marketUrl,imageView);
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
        GlideUtil.loadImageWithFading(imageUrl,imageView1);
    }

    @Override
    public View onCreateView() {
        view = View.inflate(getContext(), R.layout.dialog_share_goods_bottom,null);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        LogUtil.e("marketUrl:"+marketUrl);
        LogUtil.e("imageUrl:"+imageUrl);
        imageView = view.findViewById(R.id.imageView1);
        imageView1 = view.findViewById(R.id.imageView2);
        LinearLayout ll_title=view.findViewById(R.id.ll_title);
        TextView textViewPrice=view.findViewById(R.id.textViewPrice);
        TextView textViewWechatShare=view.findViewById(R.id.textViewWechatShare);
        TextView textViewSavePoster=view.findViewById(R.id.textViewSavePoster);
        TextView textViewCancel=view.findViewById(R.id.textViewCancel);
        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (price!=null){
            //ll_title.setVisibility(View.VISIBLE);
            textViewPrice.setText(price);
        }else {
            ll_title.setVisibility(View.GONE);
        }
        textViewSavePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(imageUrl)) {
                    new Thread(runnable).start();
                }
            }
        });
        textViewWechatShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(imageUrl)) {
                    final UMImage image = new UMImage(activity, imageUrl);
                    UMImage thumb =  new UMImage(activity, imageUrl);
                    image.setThumb(thumb);
                    new ShareAction(activity)
                            .setPlatform(SHARE_MEDIA.WEIXIN)
                            .withMedia(image)
                            .setCallback(shareListener)
                            .share();
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
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Bitmap bitmap = null;
            try {
                bitmap = GlideUtil.downLoadOriginalBitmap(imageUrl, activity);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (bitmap!=null){
                LogUtil.e("图片大小："+bitmap.getByteCount());
                ImageUtils.saveBitmap(bitmap,activity);
                //isSave=true;
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
        //LogUtil.e("保存图片"+isSave);
        //if (isSave){
            ToastUtil.showInfo("保存图片成功！");
        //}
    }
}
