package com.thn.lexi.lifeShop;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.thn.lexi.R;

public class PutMoneyDialog extends Dialog {
    public PutMoneyDialog(@NonNull Context context) {
        super(context);
        LayoutInflater inflater =  (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.dialog_put_money,null);
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
    }

    public PutMoneyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PutMoneyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
