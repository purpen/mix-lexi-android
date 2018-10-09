package com.thn.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class TransactionOrderContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setData(LifeShopOrderBean bean);
        void setFragmentData(TransactionOrderBean bean);
        void loadMoreEnd();
        void loadMoreComplete();
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid);
        void loadFragmentData(String rid, String status, String page);
    }
}
