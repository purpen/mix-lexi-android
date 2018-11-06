package com.lexivip.lexi.payUtil;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import com.basemodule.tools.Constants;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.pay.PayResultActivity;
import com.lexivip.lexi.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;

public class PayUtil implements WXPayEntryActivity.PayLinstener,PayContract.View{
    private PayWXBean bean;
    private WaitingDialog dialog;
    private final int WECHATPAY = 1;
    private PayPresenter presenter;
    private Application context = AppApplication.getContext();
    private String rid;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==WECHATPAY){
                LogUtil.e("调起微信");
                PayReq request = new PayReq();
                request.appId =Constants.WX_ID; // 公众账号ID
                request.partnerId =bean.data.mch_id;// 商户号
                request.prepayId = bean.data.prepay_id; // 预支付交易会话ID
                request.packageValue = "Sign=WXPay"; // 扩展字段 暂填写固定值Sign=WXPay
                request.nonceStr = bean.data.nonce_str; // 随机字符串
                request.timeStamp = bean.data.current_at; // 时间戳
                request.sign = bean.data.sign; // 签名sign
                AppApplication.msgApi.sendReq(request);
                WXPayEntryActivity.registerPayResultListener(PayUtil.this);
            }
        }
    };

    public PayUtil(WaitingDialog dialog, String rid, int pay_type, int type) {
        this.dialog = dialog;
        this.rid=rid;
        presenter=new PayPresenter(this);
        presenter.loadWXPayOrder(rid,pay_type,type);
    }

    @Override
    public void paySuccess(int code) {
        Intent intent=new Intent(context,PayResultActivity.class);
        intent.putExtra("rid",rid);
        context.startActivity(intent);
    }

    @Override
    public void showLoadingView() {
        dialog.show();
    }

    @Override
    public void dismissLoadingView() {
        dialog.dismiss();
    }

    @Override
    public void showError(@NonNull String error) {
        if (dialog!=null){
            dialog.dismiss();
        }
        ToastUtil.showError(error);
    }

    @Override
    public void getPayOrder(PayWXBean bean) {
        this.bean=bean;
        handler.sendEmptyMessage(WECHATPAY);
    }

    @Override
    public void setPresenter(PayContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
