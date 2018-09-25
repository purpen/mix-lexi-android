package com.thn.lexi.orderList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class InquiryDialog {
    private String message;
    private Context mContext;

    public InquiryDialog(String message, Context mContext, final ImagePopwindowInterface imginterface) {
        this.message = message;
        this.mContext = mContext;
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);  //先得到构造器
        builder.setMessage(message); //设置内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imginterface.getCheck(true);
                dialog.dismiss(); //关闭dialog
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imginterface.getCheck(false);
                dialog.dismiss();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }
    interface ImagePopwindowInterface {
        void getCheck(boolean isCheck);
    }

}
