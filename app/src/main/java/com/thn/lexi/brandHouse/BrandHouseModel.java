package com.thn.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.thn.lexi.net.ClientParamsAPI;
import com.thn.lexi.net.HttpRequest;
import com.thn.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class BrandHouseModel {
    public void loadData(String rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getRidParams(rid);
        HttpRequest.sendRequest(HttpRequest.GET, URL.BRAND_HOUSE, params, new IDataSource.HttpRequestCallBack() {
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

    public void loadNoticeData(String rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getRidParams(rid);
        HttpRequest.sendRequest(HttpRequest.GET, URL.BRAND_HOUSE_NOTICE, params, new IDataSource.HttpRequestCallBack() {
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
    public void loadGoodsData(){

    }
    public void loadArticelData(){

    }
}
