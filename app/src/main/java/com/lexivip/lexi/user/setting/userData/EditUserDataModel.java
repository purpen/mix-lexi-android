package com.lexivip.lexi.user.setting.userData;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

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
    public void loadToken(final IDataSource.HttpRequestCallBack callBack){
        HashMap<String, Object> params = ClientParamsAPI.getDefaultParams();
        HttpRequest.sendRequest(HttpRequest.GET, URL.UPLOAD_TOKEN, params, new IDataSource.HttpRequestCallBack() {
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
    public void uploadImage(UploadTokenBean bean, byte[] data, final IDataSource.UpLoadCallBack callBack, final UpProgressHandler handler){
        LogUtil.e("上传图片开始");
        String token = bean.data.up_token;
        HashMap<String, String> map = new HashMap<>();
        map.put("x:user_id", bean.data.user_id);
        map.put("x:directory_id", bean.data.directory_id);
        UploadOptions uploadOptions = new UploadOptions(map, "image/jpeg", false, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                handler.progress(key,percent);
            }
        }, null);
        LogUtil.e("文件大小："+data.length);
        AppApplication.getUploadManager().put(data, null, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                LogUtil.e("CompleteInfoModel", "$key,\r\n $info,\r\n $res");
                //res包含hash、key等信息，具体字段取决于上传策略的设置
                if (info.isOK()) {
                    try {
                        callBack.onComplete(response.getJSONArray("ids"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogUtil.e("CompleteInfoModel", "qiniu Upload Success");
                } else {
                    callBack.onComplete(null);
                    LogUtil.e("CompleteInfoModel", "qiniu Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    ToastUtil.showError("上传图片失败！");
                }


            }
        }, uploadOptions);
    }
    public void loadCity(String country, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String, Object> map=ClientParamsAPI.getCountry(country);
        HttpRequest.sendRequest(HttpRequest.GET, URL.PROVINCES_CITY_COUNTRY, map, new IDataSource.HttpRequestCallBack() {
            public void onStart() {
                callBack.onStart();
            }

            public void onSuccess(@NotNull String json) {
                callBack.onSuccess(json);
            }

            public void onFailure(@NotNull IOException e) {
                callBack.onFailure(e);
            }

            public void onSuccess(@NotNull Bitmap json) {

            }
        });
    }
}
