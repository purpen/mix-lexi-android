package com.lexivip.lexi.lifeShop;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

import java.util.Map;

public class PutForwardContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void setData(LifeShopCashBean bean);
        void setCashRecent(PutForwardBean bean);
    }
    interface Presenter extends BasePresenter{
        void loadData(String rid);
        void loadRecentData(String rid);
        void loadCash();
        void bindWX(Map<String,String> data);
    }
}
