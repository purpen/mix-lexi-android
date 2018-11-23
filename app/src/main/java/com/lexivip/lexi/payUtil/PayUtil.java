package com.lexivip.lexi.payUtil;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.alipay.sdk.app.PayTask;
import com.basemodule.tools.Constants;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.WaitingDialog;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.orderList.OrderListActivity;
import com.lexivip.lexi.pay.PayResultActivity;
import com.lexivip.lexi.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class PayUtil implements WXPayEntryActivity.PayLinstener,PayContract.View{
    private PayWXBean bean;
    private WaitingDialog dialog;
    private final int WECHATPAY = 1;
    private final int ALIPAY = 2;
    private PayPresenter presenter;
    private Application context = AppApplication.getContext();
    private Activity activity=AppApplication.getActivity();
    private String rid;
    private int pay_type;
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
            }else if (ALIPAY==msg.what){
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 返回码:
                 9000 	订单支付成功
                 8000 	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                 4000 	订单支付失败
                 6001 	用户中途取消
                 6002 	网络连接出错
                 6004 	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                 其它 	其它支付错误
                 */
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                LogUtil.e("返回的支付结果："+resultStatus);
                if (TextUtils.equals(resultStatus, "9000")||TextUtils.equals(resultStatus, "6004")) {
                    //ToastUtil.showError("支付成功");
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    startPyaResult();
                }else if (TextUtils.equals(resultStatus, "6001")){
                    ToastUtil.showError("取消支付");
                    startOrder();
                }else if (TextUtils.equals(resultStatus, "4000")){
                    ToastUtil.showError("支付失败");
                    startOrder();
                }else {
                    ToastUtil.showError("支付异常");
                    startOrder();
                }
            }
        }
    };

    public PayUtil(WaitingDialog dialog, String rid, int pay_type, int type) {
        this.dialog = dialog;
        this.rid=rid;
        this.pay_type=pay_type;//1.微信支付 2.支付宝支付
        presenter=new PayPresenter(this);
        presenter.loadWXPayOrder(rid,pay_type,type);//type字段：0.订单支付 1.订单列表支付
    }

    @Override
    public void paySuccess(int code) {
        if (-1==code){
            ToastUtil.showError("支付异常");
            startOrder();
        }else if (-2==code){
            ToastUtil.showError("取消支付");
            startOrder();
        }else {
            startPyaResult();
        }
    }

    private void startPyaResult(){
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra(PayResultActivity.class.getSimpleName(), rid);
        intent.putExtra(PayResultActivity.class.getName(), pay_type);
        context.startActivity(intent);
    }

    private void startOrder(){
        Intent intent = new Intent(context, OrderListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
    public void getPayOrder(final PayWXBean bean) {
        this.bean=bean;
        if (1==pay_type) {
            handler.sendEmptyMessage(WECHATPAY);
        }else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(activity);
                    Map<String, String> result = alipay.payV2(bean.data.order_string, true);
                    Message msg = new Message();
                    msg.what = ALIPAY;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            };
            Thread payThread = new Thread(runnable);
            payThread.start();
        }
    }

    @Override
    public void setPresenter(PayContract.Presenter presenter) {
        setPresenter(presenter);
    }
}
