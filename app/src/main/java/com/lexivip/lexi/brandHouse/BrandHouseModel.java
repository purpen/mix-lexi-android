package com.lexivip.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;
import com.lexivip.lexi.user.login.UserProfileUtil;

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
    public void loadGoodsData(String rid, String page, String cid, String min_price, String max_price, String sort_type, String sort_newest
    , final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getBrandHouseGoodsParams(rid,page,cid,min_price,max_price,sort_type,sort_newest);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GET_PRODUCTS_BY_STORE, params, new IDataSource.HttpRequestCallBack() {
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
    public void loadArticelData(){

    }
    public void loadCouponsData(String rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getStoreRidParams(rid);
        String url;
        if (UserProfileUtil.isLogin()) {
            url = URL.SHOP_STORE_LOGIN_COUPONS;
        } else {
            url = URL.SHOP_STORE_UNLOGIN_COUPONS;
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
    public void clickGetCoupon(String storeId, String code, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getClickCouponsParams(storeId, code);
        HttpRequest.sendRequest(HttpRequest.GET, URL.CLICK_GET_COUPON, params, new IDataSource.HttpRequestCallBack() {
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
    public void loadGoodsClassify(String sid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getBrandHouseGoodsClassifyParams(sid);
        HttpRequest.sendRequest(HttpRequest.GET, URL.BRAND_CATEGORIES, params, new IDataSource.HttpRequestCallBack() {
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
    public void followStore(String rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getRidParams(rid);
        HttpRequest.sendRequest(HttpRequest.POST, URL.FOCUS_BRAND_PAVILION, params, new IDataSource.HttpRequestCallBack() {
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
    public void unFollowStore(String rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getRidParams(rid);
        HttpRequest.sendRequest(HttpRequest.POST, URL.UNFOCUS_BRAND_PAVILION, params, new IDataSource.HttpRequestCallBack() {
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
    public void loadArticel(String rid, String page, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getBrandHouseArticelParams(rid,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.BRAND_RECORD, params, new IDataSource.HttpRequestCallBack() {
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
