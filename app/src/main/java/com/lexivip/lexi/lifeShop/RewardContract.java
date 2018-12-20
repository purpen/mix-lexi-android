package com.lexivip.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.lexivip.lexi.cashMoney.CashCountBean;
import com.lexivip.lexi.cashMoney.CashMoneyBean;

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
        void setCashCount(CashCountBean.DataBean bean);
        void setCash(CashMoneyBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(int page);
        void loadReward();
        void loadCashCount();
        void cashMoney(String open_id);
    }
}
