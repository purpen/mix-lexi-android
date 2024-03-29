package com.lexivip.lexi.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.lexivip.lexi.R;
import com.lexivip.lexi.coupon.FragmentUserCoupon;
import com.lexivip.lexi.coupon.UserCouponActivity;
import com.smart.dialog.widget.base.BaseDialog;

public class CouponFinishDialog extends BaseDialog<CouponFinishDialog> {

    private Button bt_left;
    private Button bt_right;

    public CouponFinishDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        View view=View.inflate(getContext(), R.layout.dialog_coupon_finish,null);
        bt_left = view.findViewById(R.id.bt_left);
        bt_right = view.findViewById(R.id.bt_right);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        bt_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserCouponActivity.class);
                intent.putExtra(UserCouponActivity.class.getSimpleName(), FragmentUserCoupon.PAGE_LX);
                getContext().startActivity(intent);
            }
        });
    }
}
