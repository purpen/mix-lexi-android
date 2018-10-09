package com.thn.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class AccountStatementContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setData(AccountStatementBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid,String page);
    }
}
