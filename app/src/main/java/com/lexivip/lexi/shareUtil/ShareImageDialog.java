package com.lexivip.lexi.shareUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
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

    public ShareImageDialog(Activity context, String marketUrl, String imageUrl,String price) {
        super(context);
        activity=context;
        this.marketUrl=marketUrl;
        this.imageUrl=imageUrl;
        this.price=price;
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
        ImageView imageView=view.findViewById(R.id.imageView1);
        ImageView imageView1=view.findViewById(R.id.imageView2);
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
        GlideUtil.loadImageWithFading(marketUrl,imageView);
        GlideUtil.loadImageWithFading(imageUrl,imageView1);
        if (price!=null){
            //ll_title.setVisibility(View.VISIBLE);
            textViewPrice.setText(price);
        }else {
            ll_title.setVisibility(View.GONE);
        }
        textViewSavePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(runnable).start();
            }
        });
        textViewWechatShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UMImage image = new UMImage(activity, imageUrl);
                new ShareAction(activity)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(image)
                        .setCallback(shareListener)
                        .share();
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
