package com.lexivip.lexi.lifeShop;

import android.content.Context;
import android.view.View;

import com.flyco.dialog.widget.base.BottomBaseDialog;
import com.lexivip.lexi.R;

public class PayDialog extends BottomBaseDialog<PayDialog> {

    private View view;

    public PayDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        view = View.inflate(getContext(), R.layout.dialog_order_pay,null);
        return view;
    }

    @Override
    public void setUiBeforShow() {

    }
}
