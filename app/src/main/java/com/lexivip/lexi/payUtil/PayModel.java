package com.lexivip.lexi.payUtil;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class PayModel {
    public void loadWXPayOrder(String rid, int pay_type, int type, final IDataSource.HttpRequestCallBack callBack){
        String url;
        if (type==0){
            url=URL.WX_PAY;
        }else {
            url=URL.WX_PAY_ORDER;
        }
        LogUtil.e("支付方式："+pay_type);
        HashMap<String,Object> params=ClientParamsAPI.getWXPayParams(rid,pay_type);
        HttpRequest.sendRequest(HttpRequest.POST, url, params, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
               callBack.onStart();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                callBack.onSuccess(json);
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                callBack.onFailure(e);
            }
        });
    }
}
