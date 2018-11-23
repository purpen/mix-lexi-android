package com.lexivip.lexi.orderList;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class LogisticsModel {
    public void getData(String logistic_code, String kdn_company_code,String order_rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> param= ClientParamsAPI.getLogistics(logistic_code,kdn_company_code,order_rid);
        HttpRequest.sendRequest(HttpRequest.POST, URL.GET_LOGISTICS, param, new IDataSource.HttpRequestCallBack() {
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
