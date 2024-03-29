package com.lexivip.lexi.cashMoney;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class CashAlipayConract {
    interface View extends BaseView<Presenter> {
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setCashResult(CashMoneyBean bean);
    }
    interface Presenter extends BasePresenter {
        void loadCash(String cash_type, String open_id, String ali_account, String ali_name, int amount);
        void cashFriend(String ali_account, String ali_name);
    }
}
