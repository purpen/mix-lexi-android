package com.thn.lexi.orderList;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.thn.lexi.net.ClientParamsAPI;
import com.thn.lexi.net.HttpRequest;
import com.thn.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class LogisticsModel {
    public void getData(String logistic_code, String kdn_company_code, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> param= ClientParamsAPI.getLoginParams(logistic_code,kdn_company_code);
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
