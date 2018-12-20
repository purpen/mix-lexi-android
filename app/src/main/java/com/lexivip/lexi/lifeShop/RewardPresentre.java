package com.lexivip.lexi.lifeShop;

import android.graphics.Bitmap;

import com.basemodule.tools.LogUtil;
import com.basemodule.tools.Util;
import com.basemodule.ui.IDataSource;
import com.lexivip.lexi.JsonUtil;
import com.lexivip.lexi.R;
import com.lexivip.lexi.cashMoney.CashCountBean;
import com.lexivip.lexi.cashMoney.CashMoneyBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RewardPresentre implements RewardContract.Presenter {
    private RewardContract.View view;
    private RewardModel model = new RewardModel();

    public RewardPresentre(RewardContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData(int page) {
        model.loadData(page, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("奖励"+json);
                RewardBean bean = JsonUtil.fromJson(json, RewardBean.class);
                if (bean.success) {
                    if (bean.data.rewards.isEmpty()) {
                        view.loadMoreEnd();
                    } else {
                        view.loadMoreComplete();
                        view.dismissLoadingView();
                        view.setData(bean);
                    }
                } else {
                    view.loadMoreFail();
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
    public void loadReward() {
        model.loadReward(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LifeShopRewardBean rewardBean = JsonUtil.fromJson(json, LifeShopRewardBean.class);
                if (rewardBean.success) {
                    view.dismissLoadingView();
                    view.setReward(rewardBean);
                } else {
                    view.showError(rewardBean.status.message);
                }
            }

            @Override
            public void onFailure(@NotNull IOException e) {
                view.showError(Util.getString(R.string.text_net_error));
            }
        });
    }

    @Override
    public void loadCashCount() {
        model.loadCashCount(new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                LogUtil.e("次数："+json);
                CashCountBean bean = JsonUtil.fromJson(json, CashCountBean.class);
                if (bean.success) {
                    view.dismissLoadingView();
                    view.setCashCount(bean.data);
                } else {
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
    public void cashMoney(String open_id) {
        model.cashMoney(open_id, new IDataSource.HttpRequestCallBack() {
            @Override
            public void onSuccess(@NotNull Bitmap json) {

            }

            @Override
            public void onStart() {
                view.showLoadingView();
            }

            @Override
            public void onSuccess(@NotNull String json) {
                CashMoneyBean bean = JsonUtil.fromJson(json, CashMoneyBean.class);
                if (bean.success) {
                    view.dismissLoadingView();
                    view.setCash(bean);
                } else {
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
