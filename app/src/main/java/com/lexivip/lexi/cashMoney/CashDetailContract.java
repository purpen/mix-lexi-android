package com.lexivip.lexi.cashMoney;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class CashDetailContract {
    interface View extends BaseView<Presenter> {
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setData(CashDetailBean bean);
    }
    interface Presenter extends BasePresenter {
        void loadData(String rid);
    }
}
