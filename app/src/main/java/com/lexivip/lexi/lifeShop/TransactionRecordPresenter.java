package com.lexivip.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class TransactionRecordPresenter implements TransactionRecordContract.Presenter {
    TransactionRecordModel model=new TransactionRecordModel();
    private TransactionRecordContract.View view;

    public TransactionRecordPresenter(TransactionRecordContract.View view) {
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
                view.dismissLoadingView();
                LifeShopSaleBean bean=JsonUtil.fromJson(json,LifeShopSaleBean.class);
                if (bean.success) {
                    view.setData(bean);
                }else{
                    view.showError(Util.getString(R.string.text_net_error));
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadFragmentData(String date_range, String rid, String status, String page) {
        model.loadFragmentData(date_range, rid, status, page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                view.dismissLoadingView();
                TransactionRecordBean bean=JsonUtil.fromJson(json,TransactionRecordBean.class);
                if (bean.success){
                    if (bean.data.transactions.isEmpty()){
                        view.loadMoreEnd();
                    }else{
                        view.loadMoreComplete();
                        view.setFragmentData(bean);
                    }
                }else{
                    view.showError(Util.getString(R.string.text_net_error));
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
