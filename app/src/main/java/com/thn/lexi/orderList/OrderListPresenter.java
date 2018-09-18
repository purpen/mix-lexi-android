package com.thn.lexi.orderList;

import android.graphics.Bitmap;

import com.basemodule.ui.IDataSource;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OrderListPresenter implements OrderListContract.Presenter {
    private OrderListModel model=new OrderListModel();
    private OrderListContract.View view;

    public OrderListPresenter(OrderListContract.View view) {
        this.view = view;
    }

    @Override
    public void getData(int status, int page) {
        model.getData(status, page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(@NotNull String json) {

            }

            @Override
            public void onFailure(@NotNull IOException e) {

            }
        });
    }
}
