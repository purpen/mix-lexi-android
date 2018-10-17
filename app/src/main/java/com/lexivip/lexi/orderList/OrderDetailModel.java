package com.lexivip.lexi.orderList;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class OrderDetailModel {
    public void getData(String rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> param= ClientParamsAPI.getDefaultParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.GET_ORDER+"/"+rid, param, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                callBack.onStart();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("订单详情的数据："+json);
                callBack.onSuccess(json);
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                callBack.onFailure(e);
            }
        });
    }
}
