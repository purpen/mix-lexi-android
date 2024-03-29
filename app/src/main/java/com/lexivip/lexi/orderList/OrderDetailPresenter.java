package com.lexivip.lexi.orderList;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.lexivip.lexi.JsonUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OrderDetailPresenter implements OrderDetailContract.Presenter {
    private OrderDetailModel model=new OrderDetailModel();
    private OrderDetailContract.View view;

    public OrderDetailPresenter(OrderDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getData(String rid) {
        model.getData(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("订单详情："+json);
                OrderDetailBean bean= JsonUtil.fromJson(json,OrderDetailBean.class);
                view.getData(bean);
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
