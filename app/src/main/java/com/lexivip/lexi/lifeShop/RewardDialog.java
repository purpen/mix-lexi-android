package com.lexivip.lexi.lifeShop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lexivip.lexi.R;
import com.smart.dialog.widget.base.BaseDialog;

public class RewardDialog extends BaseDialog<RewardDialog> {

    private View view;
    private InquiryInterface inquiryInterface;

    public RewardDialog(Context context,InquiryInterface inquiryInterface) {
        super(context);
        this.inquiryInterface=inquiryInterface;
    }

    @Override
    public View onCreateView() {
        view = View.inflate(getContext(), R.layout.dialog_reward,null);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        ImageView imageView=view.findViewById(R.id.tv_cancel);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        LinearLayout ll_wx=view.findViewById(R.id.ll_wx);
        LinearLayout ll_alipay=view.findViewById(R.id.ll_alipay);
        ll_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquiryInterface.getType(1);
                dismiss();
            }
        });
        ll_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquiryInterface.getType(2);
                dismiss();
            }
        });
    }

    public interface InquiryInterface {
        void getType(int type);
    }
}
