package com.lexivip.lexi.shareUtil;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SharePresenter implements ShareContract.Presenter {
    private ShareContract.View view;
    private ShareModel model=new ShareModel();

    public SharePresenter(ShareContract.View view) {
        this.view = view;
    }

    @Override
    public void loadImageUrl(int type) {
        model.loadImage(type, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                ShareBean shareBean=JsonUtil.fromJson(json,ShareBean.class);
                if (shareBean.success){
                    view.setImage(shareBean.data.image_url);
                    view.dismissLoadingView();
                }else {
                    view.dismissLoadingView();
                    view.showError(shareBean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadImageRidUrl(int type, String rid) {
        model.loadImageRid(rid, type, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                ShareBean shareBean=JsonUtil.fromJson(json,ShareBean.class);
                if (shareBean.success){
                    view.setImage(shareBean.data.image_url);
                    view.dismissLoadingView();
                }else {
                    view.dismissLoadingView();
                    view.showError(shareBean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadShareImage(int type, String rid, String scene) {
        model.loadShareImage(type, rid, scene, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                ShareBean shareBean=JsonUtil.fromJson(json,ShareBean.class);
                if (shareBean.success){
                    view.setImage(shareBean.data.image_url);
                    view.dismissLoadingView();
                }else {
                    view.dismissLoadingView();
                    view.showError(shareBean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadShareWindow(String rid, String scene) {
        model.loadShareWindow(rid, scene, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                ShareBean shareBean=JsonUtil.fromJson(json,ShareBean.class);
                if (shareBean.success){
                    view.setImage(shareBean.data.image_url);
                    view.dismissLoadingView();
                }else {
                    view.dismissLoadingView();
                    view.showError(shareBean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
