package com.lexivip.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class RewardContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void loadMoreEnd();
        void loadMoreComplete();
        void loadMoreFail();
        void showError(String error);
        void setData(RewardBean bean);
        void setReward(LifeShopRewardBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(int page);
        void loadReward();
    }
}
