package com.lexivip.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.index.lifehouse.LifeHouseBean;

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
                            view.showError(bean.status.message);
                        }
                        break;
                    case 1:
                        LifeShopSaleBean saleBean=JsonUtil.fromJson(json,LifeShopSaleBean.class);
                        if (saleBean.success){
                            view.setSaleData(saleBean);
                        }else {
                            view.showError(saleBean.status.message);
                        }
                        break;
                    case 2:
                        LifeShopOrderBean orderBean=JsonUtil.fromJson(json,LifeShopOrderBean.class);
//                        LogUtil.e(json);
                        if (orderBean.success){
                            view.setOrderData(orderBean);
                        }else {
                            view.showError(orderBean.status.message);
                        }
                        break;
                    case 3:
                        LifeShopCashBean cashBean=JsonUtil.fromJson(json,LifeShopCashBean.class);
//                        LogUtil.e(json);
                        if (cashBean.success){
                            view.setCashData(cashBean);
                        }else {
                            view.showError(cashBean.status.message);
                        }
                        break;
                    case 4:
                        LifeShopFriendBean friendBean=JsonUtil.fromJson(json,LifeShopFriendBean.class);
                        if (friendBean.success){
                            view.setFriendData(friendBean);
                        }else {
                            view.showError(friendBean.status.message);
                        }
                        break;
                    case 5:
                        LifeShopRewardBean rewardBean=JsonUtil.fromJson(json,LifeShopRewardBean.class);
                        if (rewardBean.success){
                            view.setRewardData(rewardBean);
                        }else {
                            view.showError(rewardBean.status.message);
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
