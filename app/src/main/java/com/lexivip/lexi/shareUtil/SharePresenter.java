package com.lexivip.lexi.shareUtil;

import android.graphics.Bitmap;

import com.basemodule.tools.AppManager;
import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SharePresenter implements ShareContract.Presenter  {
    private ShareContract.View view;
    private ShareModel model=new ShareModel();
    private WaitingDialog dialog=new WaitingDialog(AppManager.getAppManager().currentActivity());

    public SharePresenter(ShareContract.View view) {
        this.view = view;
    }

    @Override
    public void loadShareImage(String pageUrl,int type, String rid, String scene) {
        model.loadShareImage(pageUrl,type, rid, scene, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("分享的缇欧："+json);
                ShareBean shareBean=JsonUtil.fromJson(json,ShareBean.class);
                if (shareBean.success){
                    view.setImage(shareBean.data.image_url);
                    dialog.dismiss();
                }else {
                    dialog.dismiss();
                    view.showError(shareBean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                dialog.dismiss();
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

    @Override
    public void loadShareInvitation(String scene) {
        model.loadShareInvitation(scene, new IDataSource.HttpRequestCallBack() {
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
    public void loadShareMarket(String rid, int type) {
        model.loadShareMarket(rid, type, new IDataSource.HttpRequestCallBack() {
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
                    view.setMarket(shareBean.data.image_url);
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
    public void loadFriend() {
        model.loadFriend(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(@NotNull String json) {
                ShareBean shareBean=JsonUtil.fromJson(json,ShareBean.class);
                if (shareBean.success){
                }else {
                    view.showError(shareBean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
            }
        });
    }

    @Override
    public void loadBrand(String rid) {
        model.loadBrand(rid, new IDataSource.HttpRequestCallBack() {
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
    public void loadInvitation() {
        model.loadInvitation(new IDataSource.HttpRequestCallBack() {
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
