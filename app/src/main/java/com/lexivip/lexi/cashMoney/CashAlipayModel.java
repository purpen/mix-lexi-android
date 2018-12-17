package com.lexivip.lexi.cashMoney;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.net.ClientParamsAPI;
import com.lexivip.lexi.net.HttpRequest;
import com.lexivip.lexi.net.URL;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class CashAlipayModel {
    void loadCash(String cash_type, String open_id, String ali_account, String ali_name, int amount, final IDataSource.HttpRequestCallBack callBack){
        LogUtil.e("ali_name:"+ali_name);
        LogUtil.e("ali_account:"+ali_account);
        HashMap<String,Object> params=ClientParamsAPI.getInvitationCash(cash_type,open_id,ali_account,ali_name,amount);
        HttpRequest.sendRequest(HttpRequest.POST, URL.LIFE_CASH_INVITATION, params, new IDataSource.HttpRequestCallBack() {
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

    void cashMoney(String ali_account, String ali_name, final IDataSource.HttpRequestCallBack callBack){
        HashMap<String,Object> params=ClientParamsAPI.getFriendCash("2",null,ali_account,ali_name);
        HttpRequest.sendRequest(HttpRequest.POST, URL.REWARD_CASH, params, new IDataSource.HttpRequestCallBack() {
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
