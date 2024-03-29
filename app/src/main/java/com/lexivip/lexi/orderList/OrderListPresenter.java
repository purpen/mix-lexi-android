package com.lexivip.lexi.orderList;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.ToastUtil;
import com.basemodule.tools.Util;
import com.basemodule.tools.WaitingDialog;
import com.lexivip.lexi.JsonUtil;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.AppApplication;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OrderListPresenter implements OrderListContract.Presenter {
    private OrderListModel model=new OrderListModel();
    private OrderListContract.View view;

    public OrderListPresenter(OrderListContract.View view) {
        this.view = view;
    }

    @Override
    public void getData(int status, int page, final WaitingDialog dialog) {
        LogUtil.e("现在调用："+status+"页面："+page);
        model.getData(status, page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("订单数据："+json);
                MyOrderListBean bean= JsonUtil.fromJson(json,MyOrderListBean.class);
                //view.dismissLoadingView();
                dialog.dismiss();
                if (bean.isSuccess()){
                    if (bean.getData().getOrders().isEmpty()){
                        view.loadMoreEnd();
                    }else{
                        view.loadMoreComplete();
                        view.addData(bean.getData().getOrders());
                    }
                }else{
                    //view.dismissLoadingView();
                    view.showError(bean.getStatus().getMessage());
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.loadMoreFail();
                //view.dismissLoadingView();
                dialog.dismiss();
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
                view.dismissLoadingView();
                MyOrderListBean bean=JsonUtil.fromJson(json,MyOrderListBean.class);
                if (bean.isSuccess()) {
                    view.getDelete();
                }else{
                    view.showError(AppApplication.getContext().getString(R.string.text_net_error));
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
    public void finishOrder(String rid) {
        model.finishOrder(rid, new IDataSource.HttpRequestCallBack() {
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
                MyOrderListBean bean=JsonUtil.fromJson(json,MyOrderListBean.class);
                if (bean.isSuccess()) {
                    view.getFinish();
                }else{
                    view.showError(AppApplication.getContext().getString(R.string.text_net_error));
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
    public void isMerge(String rid) {
        model.isMerge(rid, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("支付订单："+json);
                view.dismissLoadingView();
                MergeBean bean=JsonUtil.fromJson(json,MergeBean.class);
                if (bean.success){
                    view.getMerge(bean);
                }else {
                    ToastUtil.showError(bean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                ToastUtil.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
