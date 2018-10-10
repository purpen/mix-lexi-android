package com.basemodule.tools;
import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.widget.ImageView;

import com.thn.basemodule.R;


/**
 * Created by taihuoniao on 2016/1/21.
 */
public class WaitingDialog extends Dialog {
    private int resBigLoading           = R.drawable.loading_image;
    private int resInfo                 = R.drawable.img_thn_default_info;
    private int resSuccess              = R.drawable.img_thn_default_success;
    private int resError                = R.drawable.img_thn_default_error;
    private ImageView ivBigLoading;

    public WaitingDialog(Activity activity) {
        this(activity, R.style.THN_custom_progress_dialog);
    }

    private WaitingDialog(Activity activity, int theme) {
        super(activity, R.style.THN_custom_progress_dialog);
        this.setContentView(R.layout.view_thn_default_progress);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
//        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        initViews();
    }
    private void initViews() {
        ivBigLoading = (ImageView) findViewById(R.id.ivBigLoading);
        GlideUtil.loadImage(resBigLoading, ivBigLoading);
    }



    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }
}
