package com.lexivip.lexi.orderList;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;

public class OrderDetailContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void getData(OrderDetailBean bean);
    }
    interface Presenter extends BasePresenter{
        void getData(String rid);
    }
}
