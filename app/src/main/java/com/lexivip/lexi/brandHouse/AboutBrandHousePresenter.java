package com.lexivip.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AboutBrandHousePresenter implements AboutBrandHouseContract.presenter {
    private AboutBrandHouseModel model=new AboutBrandHouseModel();
    private AboutBrandHouseContract.View view;

    public AboutBrandHousePresenter(AboutBrandHouseContract.View view) {
        this.view = view;
    }

    @Override
    public void loadOwnerData(String rid) {
        model.loadOwnerData(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("作者的："+json);
                view.dismissLoadingView();
                AboutBrandHouseOwnerBean bean=JsonUtil.fromJson(json,AboutBrandHouseOwnerBean.class);
                if (bean.success){
                    view.setOwnerData(bean);
                }else {
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadDetailData(String rid) {
        model.loadDetailData(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("文章的："+json);
                view.dismissLoadingView();
                AboutBrandHouseDetailBean bean=JsonUtil.fromJson(json,AboutBrandHouseDetailBean.class);
                if (bean.success){
                    view.setDetailData(bean);
                }else {
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
