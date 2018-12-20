package com.lexivip.lexi.cashMoney;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
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

public class NameAuthenticationModel {
    void loadData(String name, String id_card, int id_card_front, int id_card_back, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getAuthentication(name,id_card,id_card_front,id_card_back);
        HttpRequest.sendRequest(HttpRequest.POST, URL.AUTHENTICATION, new IDataSource.HttpRequestCallBack() {
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

    public void uploadPhoto(byte[] data, UploadTokenBean bean, final IDataSource.UpLoadCallBack callBack, final UpProgressHandler handler) {
        String token = bean.data.up_token;
        HashMap<String, String> map = new HashMap<>();
        map.put("x:user_id", bean.data.user_id);
        map.put("x:directory_id", bean.data.directory_id);
        UploadOptions uploadOptions = new UploadOptions(map, "image/jpeg", false,  new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                handler.progress(key,percent);
            }
        }, null);
        LogUtil.e("文件大小："+data.length);
        AppApplication.getUploadManager().put(data, null, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                LogUtil.i("CompleteInfoModel", "$key,\r\n $info,\r\n $res");
                //res包含hash、key等信息，具体字段取决于上传策略的设置
                if (info.isOK()) {
                    try {
                        callBack.onComplete(response.getJSONArray("ids"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogUtil.i("CompleteInfoModel", "qiniu Upload Success");
                } else {
                    callBack.onComplete(null);
                    LogUtil.i("CompleteInfoModel", "qiniu Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }


            }
        }, uploadOptions);
    }
    public void getToken(final IDataSource.HttpRequestCallBack callBack) {
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
}
