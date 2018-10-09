package com.thn.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.JsonUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.thn.lexi.R;
import com.thn.lexi.index.lifehouse.LifeHouseBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LifeShopPresenter implements LifeShopContract.Presenter {
    private LifeShopContract.View view;
    private LifeShopModel model=new LifeShopModel();
    public LifeShopPresenter(LifeShopContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(String rid, final int type) {
        model.loadData(rid, type, new IDataSource.HttpRequestCallBack() {
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
                switch (type){
                    case 0:
                        LifeHouseBean bean= JsonUtil.fromJson(json,LifeHouseBean.class);
                        if (bean.success){
                            view.setShopData(bean);
                        }else {
                            view.showError(Util.getString(R.string.text_net_error));
                        }
                        break;
                    case 1:
                        LifeShopSaleBean saleBean=JsonUtil.fromJson(json,LifeShopSaleBean.class);
                        if (saleBean.success){
                            view.setSaleData(saleBean);
                        }else {
                            view.showError(Util.getString(R.string.text_net_error));
                        }
                        break;
                    case 2:
                        LifeShopOrderBean orderBean=JsonUtil.fromJson(json,LifeShopOrderBean.class);
                        if (orderBean.success){
                            view.setOrderData(orderBean);
                        }else {
                            view.showError(Util.getString(R.string.text_net_error));
                        }
                        break;
                    case 3:
                        LifeShopCashBean cashBean=JsonUtil.fromJson(json,LifeShopCashBean.class);
                        if (cashBean.success){
                            view.setCashData(cashBean);
                        }else {
                            view.showError(Util.getString(R.string.text_net_error));
                        }
                        break;
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.dismissLoadingView();
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }
}
