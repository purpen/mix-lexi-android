package com.thn.lexi.user.setting.userData;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.thn.lexi.net.ClientParamsAPI;
import com.thn.lexi.net.HttpRequest;
import com.thn.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class EditUserDataModel {
    public void loadData(final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getDefaultParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.USER_PROFILE_URL, params, new IDataSource.HttpRequestCallBack() {
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
    public void updateData(String username, String avatar_id, String about_me, String gender, String area_id,
                           String province_id, String city_id, String mail, String date, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getUserParams(username,avatar_id,about_me,gender,area_id,province_id,city_id,mail,date);
        HttpRequest.sendRequest(HttpRequest.PUT, URL.USER_INFO_URL, params, new IDataSource.HttpRequestCallBack() {
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
