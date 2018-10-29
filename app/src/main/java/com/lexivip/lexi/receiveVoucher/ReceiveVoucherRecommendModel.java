package com.lexivip.lexi.receiveVoucher;

import android.graphics.Bitmap;

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
    public void loadOfficical(final IDataSource.HttpRequestCallBack callBack){
        HashMap<String, Object> params;
       if (UserProfileUtil.isLogin()){
           params=ClientParamsAPI.getRidParams(UserProfileUtil.storeId());
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
    public void loadGoods(String store_category, String rid, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getVoucherGoodsParams(store_category,rid);
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
}
