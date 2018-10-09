package com.thn.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.JsonUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.thn.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AccountDetailPresenter implements AccountDetailContract.Presenter {
    private AccountDetailModel model=new AccountDetailModel();
    private AccountDetailContract.View view;

    public AccountDetailPresenter(AccountDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(String rid, String record_id) {
        model.laodData(rid, record_id, new IDataSource.HttpRequestCallBack() {
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
                AccountDetailBean bean=JsonUtil.fromJson(json,AccountDetailBean.class);
                if (bean.success){
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
    public void loadDetailData(String rid, String orderId) {
        model.loadDetailOrder(rid, orderId, new IDataSource.HttpRequestCallBack() {
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
                AccountDetailOrderBean bean=JsonUtil.fromJson(json,AccountDetailOrderBean.class);
                if (bean.success){
                    view.setDetailData(bean);
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
