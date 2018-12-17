package com.lexivip.lexi.cashMoney;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class CashRecordContract {
    interface View extends BaseView<CashAlipayConract.Presenter> {
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setData(CashRecordBean bean);
        void loadMoreEnd();
        void loadMoreComplete();
        void loadMoreFail();
    }
    interface Presenter extends BasePresenter {
        void loadData(int type);
    }
}
