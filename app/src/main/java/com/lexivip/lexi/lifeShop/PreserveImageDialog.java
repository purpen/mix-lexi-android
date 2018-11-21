package com.lexivip.lexi.lifeShop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.basemodule.tools.ToastUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.album.ImageUtils;

public class PreserveImageDialog extends Dialog {
    private Activity activity;
    public PreserveImageDialog(@NonNull Context context, final Activity activity) {
        super(context);
        this.activity=activity;
        LayoutInflater inflater =  (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.dialog_preserve_image,null);
        //initView(view);
        setContentView(view);
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM);
        //WindowManager m = getWindowManager();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;//自适应高度
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;//自适应宽度
        dialogWindow.setAttributes(lp);
        //背景设置透明
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // 单击dialog之外的地方，可以dismiss掉dialog。
       // setCanceledOnTouchOutside(true);
        Button button=view.findViewById(R.id.button);
        final Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.preserve_image, null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.saveBitmap(bitmap,activity);
                dismiss();
                ToastUtil.showInfo("保存图片成功！");
            }
        });
    }

    public PreserveImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PreserveImageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
