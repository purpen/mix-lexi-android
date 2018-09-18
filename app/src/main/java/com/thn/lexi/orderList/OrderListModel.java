package com.thn.lexi.orderList;

import android.graphics.Bitmap;
import android.net.Uri;

import com.basemodule.ui.IDataSource;
import com.thn.lexi.net.HttpRequest;
import com.thn.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class OrderListModel implements IDataSource {
    public void getData(int status, int page, final HttpRequestCallBack callBack){
        HashMap<String,String> map=new HashMap<>();
        map.put("status",String.valueOf(status));
        map.put("page",String.valueOf(page));
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
}
