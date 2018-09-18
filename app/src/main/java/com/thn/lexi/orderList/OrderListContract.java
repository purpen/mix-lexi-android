package com.thn.lexi.orderList;

import com.basemodule.ui.BasePresenter;
import com.basemodule.ui.BaseView;
import com.thn.lexi.order.OrderListBean;

public class OrderListContract {
    interface View extends BaseView<Presenter>{
        void showLoadingView();
        void dismissLoadingView();
        void showError(String error);
        void getData(OrderListBean dataBean);
    }
    interface Presenter extends BasePresenter {
        void getData(int status,int page);
    }
}
