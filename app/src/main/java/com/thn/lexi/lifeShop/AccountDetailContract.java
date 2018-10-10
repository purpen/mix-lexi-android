package com.thn.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class AccountDetailContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setData(AccountDetailBean bean);
        void setDetailData(AccountDetailOrderBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid,String record_id);
        void loadDetailData(String rid,String orderId);
    }
}
