package com.lexivip.lexi.receiveVoucher;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ReceiveVoucherPresenter implements ReceiveVoucherContract.Presenter {
    private ReceiveVoucherModel model=new ReceiveVoucherModel();
    private ReceiveVoucherContract.View view;

    public ReceiveVoucherPresenter(ReceiveVoucherContract.View view) {
        this.view = view;
    }

    @Override
    public void loadClass() {
        model.loadClass(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(@NotNull String json) {
                VoucherCategoriesBean bean=JsonUtil.fromJson(json,VoucherCategoriesBean.class);
                if (bean.success){
                    view.getCategories(bean);
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
    public void loadNotice() {
        model.loadNotice(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("动态"+json);
                VoucherNoticeBean bean=JsonUtil.fromJson(json,VoucherNoticeBean.class);
                if (bean.success){
                    view.dismissLoadingView();
                    view.getNotice(bean);
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
