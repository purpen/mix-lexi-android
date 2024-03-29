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

public class OrderListModel implements IDataSource {
    public void getData(int status, int page, final HttpRequestCallBack callBack){
        HashMap<String,Object> map= ClientParamsAPI.getOrderList(String.valueOf(status),page);
        HttpRequest.sendRequest(HttpRequest.GET, URL.GET_ORDER,map, new HttpRequestCallBack() {
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
    public void deleteOrder(String rid, final HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getOrderParams(rid);
        HttpRequest.sendRequest(HttpRequest.DELETE, URL.DELETE_ORDER, params, new HttpRequestCallBack() {
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
    public void finishOrder(String rid, final HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getOrderParams(rid);
        HttpRequest.sendRequest(HttpRequest.POST, URL.ORDER_FINISH, params, new HttpRequestCallBack() {
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
    public void isMerge(String rid, final HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getRidParams(rid);
        HttpRequest.sendRequest(HttpRequest.POST, URL.IS_MERGE, params, new HttpRequestCallBack() {
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
