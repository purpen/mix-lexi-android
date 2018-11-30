package com.lexivip.lexi.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.base.BaseDialog;
import com.lexivip.lexi.R;

public class InquiryDialog extends BaseDialog<InquiryDialog> {
    private Context context;
    private View view;
    private String title;
    private String content;
    private String left;
    private String right;
    private InquiryInterface inquiryInterface;

    public InquiryDialog(Context context, String title, String content, String left, String right,InquiryInterface inquiryInterface) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
        this.left = left;
        this.right = right;
        this.inquiryInterface=inquiryInterface;
    }

    public InquiryDialog(Context context, String content, String left, String right,InquiryInterface inquiryInterface) {
        super(context);
        this.context = context;
        this.content = content;
        this.left = left;
        this.right = right;
        this.inquiryInterface=inquiryInterface;
    }

    public InquiryDialog(Context context, String content) {
        super(context);
        this.content = content;
    }

    @Override
    public View onCreateView() {
        view = View.inflate(context, R.layout.dialog_inquiry,null);
        return view;
    }

    @Override
    public void setUiBeforShow() {
        TextView tv_title=view.findViewById(R.id.tv_title);
        TextView tv_content=view.findViewById(R.id.tv_content);
        Button bt_left=view.findViewById(R.id.bt_left);
        Button bt_right=view.findViewById(R.id.bt_right);
        LinearLayout linearLayout=view.findViewById(R.id.linearLayout);
        Button button=view.findViewById(R.id.button);
        tv_content.setText(content);
        if (TextUtils.isEmpty(title)){
            tv_title.setVisibility(View.GONE);
        }else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(left)) {
            bt_left.setText(left);
            bt_right.setText(right);
            bt_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inquiryInterface.getCheck(true);
                    dismiss();
                }
            });
            bt_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inquiryInterface.getCheck(false);
                    dismiss();
                }
            });
        }else {
            linearLayout.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }



    public interface InquiryInterface {
        void getCheck(boolean isCheck);
    }
}
