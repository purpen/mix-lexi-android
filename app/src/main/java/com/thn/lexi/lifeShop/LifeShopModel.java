package com.thn.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.thn.lexi.net.ClientParamsAPI;
import com.thn.lexi.net.HttpRequest;
import com.thn.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class LifeShopModel {

    private HashMap<String, Object> params;

    public void loadData(String rid, int type, final IDataSource.HttpRequestCallBack callBack){
        String url = null;
        switch (type){
            case 0:
                url= URL.SMALL_LIFE_STORE;
                params = ClientParamsAPI.getRidParams(rid);
                break;
            case 1:
                url=URL.LIFE_ORDER_INCOME_COLLECT;
                params = ClientParamsAPI.getStoreRidParams(rid);
                break;
            case 2:
                url=URL.LIFE_ORDER_COLLECT;
                params = ClientParamsAPI.getStoreRidParams(rid);
                break;
            case 3:
                url=URL.LIFE_CASH_COLLECT;
                params = ClientParamsAPI.getStoreRidParams(rid);
                break;
        }

        HttpRequest.sendRequest(HttpRequest.GET, url, params, new IDataSource.HttpRequestCallBack() {
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
