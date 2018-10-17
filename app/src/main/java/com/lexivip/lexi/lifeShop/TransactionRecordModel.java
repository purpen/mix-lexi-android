package com.lexivip.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class TransactionRecordModel {
    public void loadData(String rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params= ClientParamsAPI.getStoreRidParams(rid);
        HttpRequest.sendRequest(HttpRequest.GET, URL.LIFE_ORDER_INCOME_COLLECT, params, new IDataSource.HttpRequestCallBack() {
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
    public void loadFragmentData(String date_range, String rid, String status, String page, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getTransactionRecordParams(date_range,rid,status,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.LIFE_TRANSACTION, params, new IDataSource.HttpRequestCallBack() {
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
