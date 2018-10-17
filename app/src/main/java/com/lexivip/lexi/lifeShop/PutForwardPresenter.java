package com.lexivip.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PutForwardPresenter implements PutForwardContract.Presenter{
    private PutForwardModel model=new PutForwardModel();
    private PutForwardContract.View view;

    public PutForwardPresenter(PutForwardContract.View view) {
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
                LifeShopCashBean bean=JsonUtil.fromJson(json,LifeShopCashBean.class);
                if (bean.success){
                    view.setData(bean);
                }else {
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
    public void loadRecentData(String rid) {
        model.loadRecentData(rid, new IDataSource.HttpRequestCallBack() {
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
                PutForwardBean bean=JsonUtil.fromJson(json,PutForwardBean.class);
                if (bean.success){
                    view.setCashRecent(bean);
                }else {
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
