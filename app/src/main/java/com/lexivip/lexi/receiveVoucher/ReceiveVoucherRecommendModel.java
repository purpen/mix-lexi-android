package com.lexivip.lexi.receiveVoucher;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;
import com.lexivip.lexi.user.login.UserProfileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class ReceiveVoucherRecommendModel {
   public void  loadBrand(String store_category, String page, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getVoucherParams(store_category,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.VOUCHER_SHARE, params, new IDataSource.HttpRequestCallBack() {
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
    public void  loadBrandRecommend(String store_category, String page, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getVoucherRecommendParams(store_category,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.VOUCHER_SHARE, params, new IDataSource.HttpRequestCallBack() {
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
    public void loadOfficical(final IDataSource.HttpRequestCallBack callBack){
        HashMap<String, Object> params;
       if (UserProfileUtil.isLogin()){
           params=ClientParamsAPI.getRidParams(UserProfileUtil.getUserId());
       }else {
           params = ClientParamsAPI.getDefaultParams();
       }
        HttpRequest.sendRequest(HttpRequest.GET, URL.VOUCHER_RECOMMEND, params, new IDataSource.HttpRequestCallBack() {
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
    public void loadGoods(String store_category, String rid,String page, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getVoucherGoodsParams(store_category,rid,page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.VOUCHER_SINGLE, params, new IDataSource.HttpRequestCallBack() {
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
    public void loadImage(final IDataSource.HttpRequestCallBack callBack){
       String rid;
       if (UserProfileUtil.isLogin()){
           rid="/"+UserProfileUtil.getUserId();
       }else {
           rid="";
       }
        HashMap<String,Object> params=ClientParamsAPI.getDefaultParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.VOUCHER_IMAGE+rid , params, new IDataSource.HttpRequestCallBack() {
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
    public void receiveVoucher(String rid, String store_rid, final IDataSource.HttpRequestCallBack callBack) {
        HashMap<String,Object> params=ClientParamsAPI.getClickCouponsParams(store_rid,rid);
        HttpRequest.sendRequest(HttpRequest.POST, URL.CLICK_GET_COUPON, params, new IDataSource.HttpRequestCallBack() {
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
    public void receiveOfficial(String rid, final IDataSource.HttpRequestCallBack callBack) {
        HashMap<String,Object> params=ClientParamsAPI.getReceiveVoucherOfficialParams(rid);
        HttpRequest.sendRequest(HttpRequest.POST, URL.VOUCHER_OFFICIAL_RECEIVE, params, new IDataSource.HttpRequestCallBack() {
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
