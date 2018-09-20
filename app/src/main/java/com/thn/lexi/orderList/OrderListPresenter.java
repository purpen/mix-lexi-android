package com.thn.lexi.orderList;

import android.graphics.Bitmap;
import android.util.Log;

import com.basemodule.tools.JsonUtil;
import com.basemodule.tools.LogUtil;
import com.basemodule.ui.IDataSource;
import com.thn.lexi.AppApplication;
import com.thn.lexi.R;
import com.thn.lexi.order.OrderListBean;

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
        LogUtil.e("类型:"+status);
        model.getData(status, page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("订单数据："+json);
                MyOrderListBean bean= JsonUtil.fromJson(json,MyOrderListBean.class);
                view.dismissLoadingView();
                if (bean.isSuccess()){
                    if (bean.getData().getOrders().isEmpty()){
                        view.loadMoreEnd();
                    }else{
                        view.loadMoreComplete();
                        view.addData(bean.getData().getOrders());
                    }
                }else{
                    view.showError(bean.getStatus().getMessage());
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
    public void deleteOrder(String rid) {
        model.deleteOrder(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                MyOrderListBean bean=JsonUtil.fromJson(json,MyOrderListBean.class);
                view.getDelete(bean);
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(AppApplication.getContext().getString(R.string.text_net_error));
            }
        });
    }
}
