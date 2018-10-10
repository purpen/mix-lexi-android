package com.thn.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class TransactionRecordContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setData(LifeShopSaleBean bean);
        void setFragmentData(TransactionRecordBean bean);
        void loadMoreEnd();
        void loadMoreComplete();
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid);
        void loadFragmentData(String date_range,String rid,String status,String page);
    }
}
