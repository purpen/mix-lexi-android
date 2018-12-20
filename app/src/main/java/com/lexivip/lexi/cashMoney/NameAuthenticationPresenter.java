package com.lexivip.lexi.cashMoney;

import android.graphics.Bitmap;

import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.user.completeinfo.UploadTokenBean;
import com.qiniu.android.storage.UpProgressHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import javax.xml.validation.Validator;

public class NameAuthenticationPresenter implements NameAuthenticationContract.Presenter{
    private NameAuthenticationContract.View view;
    private NameAuthenticationModel model=new NameAuthenticationModel();

    public NameAuthenticationPresenter(NameAuthenticationContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(String name, String id_card, int id_card_front, int id_card_back) {
        model.loadData(name, id_card, id_card_front, id_card_back, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                CashBean cashBean=JsonUtil.fromJson(json,CashBean.class);
                if (cashBean.success){
                    view.dismissLoadingView();
                    view.setData();
                }else {
                    view.showError(cashBean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadPhoto(UploadTokenBean bean, byte[] data) {
        view.showLoadingView();
        if (bean==null){
            ToastUtil.showInfo(R.string.text_net_error);
            return;
        }
        model.uploadPhoto(data, bean, new IDataSource.UpLoadCallBack() {
            @Override
            public void onComplete(JSONArray ids) {
                view.dismissLoadingView();
                if (ids==null){
                    ToastUtil.showError("上传图片失败！");
                }else {
                    try {
                        view.setImageId(ids);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtil.showError("上传图片失败！");
                    }
                }
            }
        }, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                if (percent==1){
                    view.dismissLoadingView();
                }
            }
        });
    }

    public void getToken(){
        model.getToken(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                UploadTokenBean bean=JsonUtil.fromJson(json,UploadTokenBean.class);
                if (bean.success){
                    view.setToken(bean);
                }else{
                    view.showError(bean.status.message);
                }
                view.dismissLoadingView();
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }
}
