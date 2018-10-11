package com.thn.lexi.brandHouse;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;
import com.thn.lexi.AppApplication;
import com.thn.lexi.JsonUtil;
import com.thn.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BrandHousePresenter implements BrandHouseContract.Presenter {
    private BrandHouseModel model=new BrandHouseModel();
    private BrandHouseContract.View view;

    public BrandHousePresenter(BrandHouseContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(String rid) {
        model.loadData(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                BrandHouseBean bean=JsonUtil.fromJson(json,BrandHouseBean.class);
                if (bean.success){
                    view.setData(bean);
                }else{
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadNoticeData(String rid) {
        model.loadNoticeData(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                BrandHouseNoticeBean bean=JsonUtil.fromJson(json,BrandHouseNoticeBean.class);
                if (bean.success){
                    view.setNoticeData(bean);
                }else{
                    view.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }
}
