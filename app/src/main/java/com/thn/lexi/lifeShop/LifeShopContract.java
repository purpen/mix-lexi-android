package com.thn.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.thn.lexi.index.lifehouse.LifeHouseBean;

public class LifeShopContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setShopData(LifeHouseBean bean);
        void setOrderData(LifeShopOrderBean bean);
        void setCashData(LifeShopCashBean bean);
        void setSaleData(LifeShopSaleBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid,int type);
    }
}
