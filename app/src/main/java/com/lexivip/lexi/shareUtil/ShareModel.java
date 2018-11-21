package com.lexivip.lexi.shareUtil;

import android.graphics.Bitmap;

import com.basemodule.tools.Constants;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class ShareModel {
    void loadImageRid(String rid, int type, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getRidParams(rid);
        String url;
        if (type==0){
            url=URL.SHARE_BRAND;
        }else {
            url=URL.SHARE_LIFE;
        }
        HttpRequest.sendRequest(HttpRequest.POST, url, params, new IDataSource.HttpRequestCallBack() {
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
    void loadImage(int type, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getDefaultParams();
        String url;
        if (type==0){
            url=URL.SHARE_INVITE;
        }else {
            url=URL.SHARE_VOUCHER;
        }
        HttpRequest.sendRequest(HttpRequest.POST, url, params, new IDataSource.HttpRequestCallBack() {
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
    void loadShareImage(int type, String rid, String scene, final IDataSource.HttpRequestCallBack callBack){
        String path;
        if (4==type){
            path=Constants.PRODUCTPATH;
        }else {
            path=Constants.HOMEPATH;
        }
        HashMap<String,Object> params=ClientParamsAPI.getShareImage(Constants.AUTHAPPID,path,type,rid,scene);
        HttpRequest.sendRequest(HttpRequest.POST, URL.SHARE_POSTER, params, new IDataSource.HttpRequestCallBack() {
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
    void loadShareWindow(String rid, String scene, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getShareWindow(Constants.AUTHAPPID,Constants.WINDOWPATH,rid,scene);
        HttpRequest.sendRequest(HttpRequest.POST, URL.SHARE_WINDOW, params, new IDataSource.HttpRequestCallBack() {
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
