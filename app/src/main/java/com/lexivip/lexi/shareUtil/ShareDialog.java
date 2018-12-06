package com.lexivip.lexi.shareUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.basemodule.tools.GlideUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.album.ImageUtils;
import com.smart.dialog.widget.base.BottomBaseDialog;

import java.util.concurrent.ExecutionException;

public class ShareDialog extends BottomBaseDialog<ShareDialog> {
    private String url;
    private Context context;
    private View view;
    private boolean isSave;

    public ShareDialog(Context context,String url) {
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
        ImageView cancel=view.findViewById(R.id.iv_cancel);
        ImageView imageView=view.findViewById(R.id.imageView);
        Button button=view.findViewById(R.id.button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(runnable).start();
            }
        });
        GlideUtil.loadImageWithFading(url,imageView);
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
        super.setOnDismissListener(listener);
        if (isSave){
            ToastUtil.showInfo("保存图片成功！");
        }
    }
}
