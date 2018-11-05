package com.lexivip.lexi.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.basemodule.tools.LogUtil;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private static PayLinstener mPayListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay);
        api = AppApplication.msgApi;
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    public static void registerPayResultListener(PayLinstener payListener){

        mPayListener = payListener;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.e(baseResp.errStr+baseResp.transaction+"::::"+baseResp.errCode);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            mPayListener.paySuccess(baseResp.errCode);
        }
        finish();
    }
    public interface PayLinstener {
        void paySuccess(int code);
    }

}
