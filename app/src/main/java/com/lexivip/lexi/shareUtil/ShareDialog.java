package com.lexivip.lexi.shareUtil;

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
import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.lexivip.lexi.R;
import com.lexivip.lexi.album.ImageUtils;

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
        ImageView imageView=view.findViewById(R.id.imageView);
        int w=ScreenUtil.getScreenWidth()-DimenUtil.getDimensionPixelSize(R.dimen.dp60);
        int h= (int) (w*1.53);
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
